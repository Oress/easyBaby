package com.bshop.inventoryItem.assembler;

import com.bshop.inventoryItem.dto.InventoryItemFull;
import com.bshop.inventoryItem.model.InventoryItem;

public class InventoryItemAssembler {
    public InventoryItemFull assebleFullDto(InventoryItem model) {
        InventoryItemFull result = new InventoryItemFull();

        result.id = model.getId();
        result.barcode = model.getBarcode();
        result.status = model.getStatus();
        result.registrationDate = model.getRegistrationDate();
        result.productId = model.getProduct().getId();
        result.addedById = model.getAddedBy().getId();

        return result;
    }

    public InventoryItem assemblePartialModel(InventoryItemFull dto) {
        InventoryItem result = new InventoryItem();

        result.setBarcode(dto.barcode);
        result.setStatus(dto.status);
        result.setRegistrationDate(dto.registrationDate);
//        result.setStatus();
//        result.setAddedBy();
//        result.setProduct();

        return result;
    }

}
