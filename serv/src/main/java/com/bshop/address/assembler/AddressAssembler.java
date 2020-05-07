package com.bshop.address.assembler;

import com.bshop.address.dto.AddressFull;
import com.bshop.address.model.Address;

public class AddressAssembler {
    public AddressFull assembleFullDto(Address model) {
        AddressFull result = new AddressFull();

        result.id = model.getId();
        result.city = model.getCity();
        result.name = model.getName();
        result.house = model.getHouse();
        result.apartment = model.getApartment();

        return result;
    }

    public Address assembleModel(AddressFull dto) {
        Address result = new Address();

        result.setCity(dto.city);
        result.setName(dto.name);
        result.setHouse(dto.house);
        result.setApartment(dto.apartment);

        return result;
    }
}
