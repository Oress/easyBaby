package com.bshop.shipment.assembler;

import com.bshop.shipment.dto.ShipmentInfoFull;
import com.bshop.shipment.model.Shipment;

public class ShipmentAssembler {

    public ShipmentInfoFull assembleFullDto(Shipment model) {
        ShipmentInfoFull result = new ShipmentInfoFull();

        result.id = model.getId();
        result.address = model.getAddress();
        result.name = model.getRecipientName();
        result.phone = model.getRecipientPhone();
        result.type = model.getType();

        return result;
    }

}
