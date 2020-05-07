package com.bshop.vendor.model;

import com.bshop.address.model.Address;
import com.bshop.contactInfo.model.ContactInfo;

import javax.persistence.*;
import java.util.List;

@Entity(name = "VENDOR")
public class Vendor {
    @Id
    @GeneratedValue
    @Column(name = "VNDR_ID")
    private Integer id;

    @Column(name = "VNDR_TITLE")
    private String title;

    @Column(name = "VNDR_DESCR")
    private String description;

    @OneToMany
    private List<ContactInfo> contats;

    @OneToMany
    private List<Address> addresses;

    public Vendor() {
    }

    public Vendor(Integer id, String title, String description, List<ContactInfo> contats, List<Address> addresses) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.contats = contats;
        this.addresses = addresses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ContactInfo> getContats() {
        return contats;
    }

    public void setContats(List<ContactInfo> contats) {
        this.contats = contats;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
