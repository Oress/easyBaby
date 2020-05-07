package com.bshop.order.assembler;

import com.bshop.ProductImage.assembler.ProductImageAssembler;
import com.bshop.inventoryItem.dto.InventoryItemFull;
import com.bshop.inventoryItem.model.InventoryItem;
import com.bshop.order.dto.MyOrderEntryFull;
import com.bshop.order.dto.MyOrderFull;
import com.bshop.order.dto.OrderFull;
import com.bshop.order.model.Order;
import com.bshop.orderItem.assembler.OrderItemAssembler;
import com.bshop.orderItem.dto.OrderItemFull;
import com.bshop.orderItem.model.OrderItem;
import com.bshop.payment.assembler.PaymentAssembler;
import com.bshop.product.model.Product;
import com.bshop.shipment.assembler.ShipmentAssembler;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderAssembler {
    OrderItemAssembler orderItemAssembler = new OrderItemAssembler();
    ProductImageAssembler productImageAssembler = new ProductImageAssembler();
    ShipmentAssembler shipmentAssembler = new ShipmentAssembler();
    PaymentAssembler paymentAssembler = new PaymentAssembler();


    public OrderFull assembleFullDto(Order model) {
        OrderFull result = new OrderFull();

        result.id = model.getId();
        result.orderItems = model.getOrderItems().stream().map(orderItemAssembler::assembleFullDto).collect(Collectors.toList());
        result.paymentInfo = paymentAssembler.assembleFullDto(model.getPayment());
        result.shipmentInfo = shipmentAssembler.assembleFullDto(model.getShipment());

        return result;
    }

    public MyOrderFull assembleMyOrderFullDto(Order model) {
        MyOrderFull result = new MyOrderFull();

        result.id = model.getId();
        result.orderItems = Lists.newArrayList();
        result.total = 0d;

        Map<Product, List<InventoryItem>> map = Maps.newHashMap();
        for (OrderItem item : model.getOrderItems()) {
            InventoryItem iitem = item.getInventoryItem();
            Product prod = iitem.getProduct();
            List<InventoryItem> iitems = map.get(prod);
            if (iitems == null) {
                map.put(prod, Lists.newArrayList(iitem));
            } else {
                iitems.add(iitem);
            }
            result.total += item.getPrice();
        }
        for (Map.Entry<Product, List<InventoryItem>> pair: map.entrySet()) {
            Product prod = pair.getKey();
            List<InventoryItem> iitems = pair.getValue();

            int len = iitems.size();
            List<String> barcodes = iitems.stream().map(InventoryItem::getBarcode).collect(Collectors.toList());

            MyOrderEntryFull entry = new MyOrderEntryFull();
            entry.barcodes = barcodes;
            entry.count = len;
            entry.productId = prod.getId();
            if(prod.getImages().size() != 0){
                entry.image = productImageAssembler.assembleFullDto(prod.getImages().get(0));
            }
            entry.productTitle = prod.getTitle();

            result.orderItems.add(entry);
        }


        result.paymentInfo = paymentAssembler.assembleFullDto(model.getPayment());
        result.shipmentInfo = shipmentAssembler.assembleFullDto(model.getShipment());

        return result;
    }
}
