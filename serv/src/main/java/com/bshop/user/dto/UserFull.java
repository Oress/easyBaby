package com.bshop.user.dto;

import com.bshop.contactInfo.dto.ContactInfoFull;

public class UserFull {
    public Integer id;
    public String username;
    public Boolean isStaff;
    public byte[] image;
    public ContactInfoFull contactInfo;
}
