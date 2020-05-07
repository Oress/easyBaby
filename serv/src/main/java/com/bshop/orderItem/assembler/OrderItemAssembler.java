package com.bshop.orderItem.assembler;

import com.bshop.inventoryItem.assembler.InventoryItemAssembler;
import com.bshop.orderItem.dto.OrderItemFull;
import com.bshop.orderItem.model.OrderItem;

public class OrderItemAssembler {
    InventoryItemAssembler inventoryItemAssembler = new InventoryItemAssembler();

    public OrderItemFull assembleFullDto(OrderItem model) {
        OrderItemFull result = new OrderItemFull();

        result.id = model.getId();
        result.price = model.getPrice();
        result.inventoryItem = inventoryItemAssembler.assebleFullDto(model.getInventoryItem());

        return result;
    }

}
