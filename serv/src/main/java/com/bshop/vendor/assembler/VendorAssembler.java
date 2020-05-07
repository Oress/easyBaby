package com.bshop.vendor.assembler;

import com.bshop.address.assembler.AddressAssembler;
import com.bshop.contactInfo.assembler.ContactAssembler;
import com.bshop.vendor.dto.VendorFull;
import com.bshop.vendor.model.Vendor;

import java.util.stream.Collectors;

public class VendorAssembler {
    ContactAssembler contactAssembler = new ContactAssembler();
    AddressAssembler addressAssembler = new AddressAssembler();

    public VendorFull assembleFullDto(Vendor model) {
        VendorFull result = new VendorFull();

        result.id = model.getId();
        result.title = model.getTitle();
        result.description = model.getDescription();

        result.addresses = model.getAddresses().stream().map(addressAssembler::assembleFullDto).collect(Collectors.toList());
        result.contacts = model.getContats().stream().map(contactAssembler::assebleFullDto).collect(Collectors.toList());

        return result;
    }

    public Vendor assembleModel(VendorFull dto) {
        Vendor result = new Vendor();

        result.setTitle(dto.title);
        result.setDescription(dto.description);

        if (dto.addresses != null) {
            result.setAddresses(dto.addresses.stream().map(addressAssembler::assembleModel).collect(Collectors.toList()));
        }

        if (dto.contacts != null) {
            result.setContats(dto.contacts.stream().map(contactAssembler::assembleModel).collect(Collectors.toList()));
        }
        return result;
    }
}
