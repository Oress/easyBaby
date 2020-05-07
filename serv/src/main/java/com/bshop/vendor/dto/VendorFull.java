package com.bshop.vendor.dto;


import com.bshop.address.dto.AddressFull;
import com.bshop.contactInfo.dto.ContactInfoFull;

import java.util.List;

public class VendorFull {
    public Integer id;
    public String title;
    public String description;
    public List<ContactInfoFull> contacts;
    public List<AddressFull> addresses;
}
