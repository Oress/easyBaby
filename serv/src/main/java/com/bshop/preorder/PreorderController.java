package com.bshop.preorder;

import com.bshop.inventoryItem.model.InventoryItem;
import com.bshop.order.dto.MyOrderEntryFull;
import com.bshop.order.dto.MyOrderFull;
import com.bshop.order.model.Order;
import com.bshop.order.model.OrderRepository;
import com.bshop.orderItem.model.OrderItem;
import com.bshop.preorder.assembler.PreorderAssembler;
import com.bshop.preorder.dto.PreorderFull;
import com.bshop.preorder.dto.PreorderRequest;
import com.bshop.preorder.model.Preorder;
import com.bshop.preorder.model.PreorderRepository;
import com.bshop.preorderItem.model.PreorderItem;
import com.bshop.preorderItem.model.PreorderItemRepository;
import com.bshop.product.model.Product;
import com.bshop.user.model.User;
import com.bshop.user.model.UserRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/preorder")
public class PreorderController {
    OrderRepository orderRepository;
    PreorderRepository preorderRepository;
    PreorderItemRepository preorderItemRepository;
    UserRepository userRepository;

    PreorderAssembler preorderAssembler = new PreorderAssembler();

    public PreorderController(OrderRepository orderRepository, PreorderRepository preorderRepository, PreorderItemRepository preorderItemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.preorderRepository = preorderRepository;
        this.preorderItemRepository = preorderItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public void makePreorderFromOrder(@RequestBody PreorderRequest preorderRequest) {
        Order order = orderRepository.findById(preorderRequest.orderId).get();

        Preorder preorder = new Preorder();

        preorderRepository.save(preorder);

        preorder.setAddress(order.getShipment().getAddress());
        preorder.setName(order.getShipment().getRecipientName());
        preorder.setPhone(order.getShipment().getRecipientPhone());
        preorder.setPaymentType(order.getPayment().getType());
        preorder.setShipmentType(order.getShipment().getType());
        preorder.setRecipient(order.getRecipient());

        preorder.setTimePeriodType(preorderRequest.timePeriod.timePeriod);
        preorder.setCount(preorderRequest.timePeriod.count);

        preorder.setRecipient(order.getRecipient());


        List<PreorderItem> items = Lists.newArrayList();

        Map<Product, List<InventoryItem>> map = Maps.newHashMap();
        for (OrderItem item : order.getOrderItems()) {
            InventoryItem iitem = item.getInventoryItem();
            Product prod = iitem.getProduct();
            List<InventoryItem> iitems = map.get(prod);
            if (iitems == null) {
                map.put(prod, Lists.newArrayList(iitem));
            } else {
                iitems.add(iitem);
            }
        }
        for (Map.Entry<Product, List<InventoryItem>> pair: map.entrySet()) {
            Product prod = pair.getKey();
            int len  = pair.getValue().size();

            PreorderItem preorderItem = new PreorderItem();
            preorderItem.setPreorder(preorder);
            preorderItem.setProduct(prod);
            preorderItem.setCount(len);
            items.add(preorderItem);
        }
        preorderItemRepository.saveAll(items);
        preorder.setPreorderItems(items);
        preorderRepository.save(preorder);
    }


    @Transactional
    @RequestMapping(path = "my", method = RequestMethod.GET)
    public List<PreorderFull> getMyPreorders() {
        List<PreorderFull> result;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).get();
        result = user.getPreorders().stream().map(preorderAssembler::assembleFullDto).collect(Collectors.toList());
        return result;
    }
}
