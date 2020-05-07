package com.bshop.preorder.assembler;

import com.bshop.preorder.dto.PreorderFull;
import com.bshop.preorder.model.Preorder;
import com.bshop.preorderItem.assembler.PreorderItemAssembler;

import java.util.stream.Collectors;

public class PreorderAssembler {
    PreorderItemAssembler preorderItemAssembler = new PreorderItemAssembler();


    public PreorderFull assembleFullDto(Preorder model) {
        PreorderFull result = new PreorderFull();
        result.id = model.getId();
        result.address = model.getAddress();
        result.name = model.getName();
        result.createdDate = model.getCreatedDate();
        result.paymentType = model.getPaymentType();
        result.phone = model.getPhone();
        result.shipmentType = model.getShipmentType();
        result.count = model.getCount();
        result.timePeriodType = model.getTimePeriodType();

        result.preorderItems = model.getPreorderItems().stream()
                .map(preorderItemAssembler::assembleFullDto)
                .collect(Collectors.toList());

        return result;
    }

}
