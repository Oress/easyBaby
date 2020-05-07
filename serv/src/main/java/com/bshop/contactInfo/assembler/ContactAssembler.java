package com.bshop.contactInfo.assembler;

import com.bshop.contactInfo.dto.ContactInfoFull;
import com.bshop.contactInfo.model.ContactInfo;

public class ContactAssembler {

    public ContactInfoFull assebleFullDto(ContactInfo model) {
        ContactInfoFull result = new ContactInfoFull();

        result.id = model.getId();
        result.firstName = model.getFirstName();
        result.lastName = model.getLastName();
        result.middleName = model.getMiddleName();
        result.email = model.getEmail();
        result.phone1 = model.getPhone1();
        result.phone2 = model.getPhone2();
        result.dob = model.getDob();

        return result;
    }

    public ContactInfo assembleModel(ContactInfoFull dto) {
        ContactInfo result = new ContactInfo();

        result.setEmail(dto.email);
        result.setFirstName(dto.firstName);
        result.setLastName(dto.lastName);
        result.setMiddleName(dto.middleName);
        result.setPhone1(dto.phone1);
        result.setPhone2(dto.phone2);

        return result;
    }


}
