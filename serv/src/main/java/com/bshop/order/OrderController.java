package com.bshop.order;

import com.bshop.inventoryItem.model.InventoryItem;
import com.bshop.inventoryItem.model.InventoryItemRepository;
import com.bshop.inventoryItem.model.InventoryItemStatus;
import com.bshop.order.assembler.OrderAssembler;
import com.bshop.order.dto.MyOrderFull;
import com.bshop.orderItem.model.OrderItemRepository;
import com.bshop.shipment.dto.ShipmentInfoFull;
import com.bshop.order.dto.OrderFull;
import com.bshop.order.dto.OrderSubmitResultFull;
import com.bshop.payment.dto.PaymentInfoFull;
import com.bshop.order.model.Order;
import com.bshop.order.model.OrderRepository;
import com.bshop.orderItem.model.OrderItem;
import com.bshop.payment.model.Payment;
import com.bshop.payment.model.PaymentRepository;
import com.bshop.product.dto.CartItemFull;
import com.bshop.product.model.Product;
import com.bshop.product.model.ProductRepository;
import com.bshop.shipment.model.Shipment;
import com.bshop.shipment.model.ShipmentRepository;
import com.bshop.user.model.User;
import com.bshop.user.model.UserRepository;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "order")
public class OrderController {
    private InventoryItemRepository inventoryItemRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;
    private ShipmentRepository shipmentRepository;
    private PaymentRepository paymentRepository;
    private UserRepository userRepository;

    private OrderAssembler orderAssembler = new OrderAssembler();

    public OrderController(InventoryItemRepository inventoryItemRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, ShipmentRepository shipmentRepository, PaymentRepository paymentRepository, UserRepository userRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.shipmentRepository = shipmentRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @RequestMapping(path = "/my", method = RequestMethod.GET)
    public List<MyOrderFull> getMyOrders() {
        List<MyOrderFull> result;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).get();
        result = user.getOrders().stream().map(orderAssembler::assembleMyOrderFullDto).collect(Collectors.toList());
        return result;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public OrderSubmitResultFull putAnOrder(@RequestBody OrderFull orderDto) {
        OrderSubmitResultFull result = new OrderSubmitResultFull();
        List<String> problems = Lists.newArrayList();
        result.problems = problems;

        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username).get();

            validateOrderDto(orderDto);
            Order order = new Order();
            orderRepository.save(order);

            List<OrderItem> orderItems = Lists.newArrayList();

            for (CartItemFull item : orderDto.cartItems) {
                Product product = productRepository.findById(item.productId).get();

                List<InventoryItem> inventoryItems =
                        inventoryItemRepository.findByProductId(product.getId(), PageRequest.of(0, item.count));

                if (item.count < inventoryItems.size()) {
                    throw new Exception("not enough items in inventory");
                }

                for (InventoryItem inventoryItem : inventoryItems) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setPrice(product.getPrice());
                    orderItem.setInventoryItem(inventoryItem);
                    orderItem.setOrder(order);

                    orderItems.add(orderItem);

                    inventoryItem.setStatus(InventoryItemStatus.ORDERED);
                }

                inventoryItemRepository.saveAll(inventoryItems);
            }

            orderItemRepository.saveAll(orderItems);
            order.setOrderItems(orderItems);


            ShipmentInfoFull shipmentInfo = orderDto.shipmentInfo;

            Shipment shipment = new Shipment();
            shipment.setAddress(shipmentInfo.address);
            shipment.setRecipientName(shipmentInfo.name);
            shipment.setRecipientPhone(shipmentInfo.phone);
            shipment.setType(shipmentInfo.type);
            shipmentRepository.save(shipment);


            PaymentInfoFull paymentInfo = orderDto.paymentInfo;

            Payment payment = new Payment();
            payment.setType(paymentInfo.type);
            paymentRepository.save(payment);


            order.setRecipient(user);
            order.setPayment(payment);
            order.setShipment(shipment);

            orderRepository.save(order);

            result.order = orderAssembler.assembleFullDto(order);
        } catch (Exception e) {
            problems.add(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private void validateOrderDto(@RequestBody OrderFull order) throws Exception {
        if (order == null) {
            throw new Exception("no order");
        }
        if ((order.cartItems == null || order.cartItems.size() == 0)) {
            throw new Exception("no order items");
        }
        for (CartItemFull item : order.cartItems) {
            if (item.count == null || item.count <= 0 || item.productId == null) {
                throw new Exception("Product items are invalid");
            }
        }

        if ((order.shipmentInfo == null
                || Strings.isNullOrEmpty(order.shipmentInfo.address)
                || Strings.isNullOrEmpty(order.shipmentInfo.name)
                || Strings.isNullOrEmpty(order.shipmentInfo.phone)
                || order.shipmentInfo.type == null)
        ) {
            throw new Exception("no delivery info");
        }
        if ((order.paymentInfo == null || order.paymentInfo.type == null)) {
            throw new Exception("no payment info");
        }
    }
}
