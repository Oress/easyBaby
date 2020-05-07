package com.bshop.user.assembler;

import com.bshop.contactInfo.assembler.ContactAssembler;
import com.bshop.user.dto.UserFull;
import com.bshop.user.model.User;

public class UserAssembler {
    ContactAssembler assembler = new ContactAssembler();


    public UserFull assembleShort(User model) {
        UserFull result = new UserFull();

        result.id = model.getId();
        result.username = model.getUsername();
        result.isStaff = model.getStaff();

        return result;
    }

    public UserFull assembleFull(User model) {
        UserFull result = new UserFull();

        result.id = model.getId();
        result.username = model.getUsername();
        result.isStaff = model.getStaff();
        result.contactInfo = assembler.assebleFullDto(model.getContactInfo());

        return result;
    }
}
