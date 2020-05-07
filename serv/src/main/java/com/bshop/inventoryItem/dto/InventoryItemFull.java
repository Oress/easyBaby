package com.bshop.inventoryItem.dto;

import com.bshop.inventoryItem.model.InventoryItemStatus;

import java.util.Date;

public class InventoryItemFull {
    public Integer id;
    public String barcode;
    public Integer productId;
    public Integer addedById;
    public InventoryItemStatus status;
    public Date registrationDate;
}
