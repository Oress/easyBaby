package com.bshop.inventoryItem.model;

public enum InventoryItemStatus {
    ADDED("ADDED"),
    TOREMOVE("TOREMOVE"),
    REMOVED("REMOVED"),
    AVAILABLE("AVAILABLE"),
    ORDERED("ORDERED"),
    SOLD("SOLD");

    private String value;

    InventoryItemStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
