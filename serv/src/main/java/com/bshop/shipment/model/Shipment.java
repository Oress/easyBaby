package com.bshop.shipment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "SHIPMENT")
public class Shipment {
    @Id
    @GeneratedValue
    @Column(name = "SHPM_ID")
    private Integer id;

    @Column(name = "SHPM_TYPE")
    private ShipmentType type;

    @Column(name = "SHPM_ADR")
    private String address;

    @Column(name = "SHPM_REC_NAME")
    private String recipientName;

    @Column(name = "SHPM_REC_PHN")
    private String recipientPhone;

    @Column(name = "SHPM_DONE")
    private Boolean done = Boolean.FALSE;

    public Shipment() {
    }

    public Shipment(Integer id, ShipmentType type, String address, String recipientName, String recipientPhone, Boolean done) {
        this.id = id;
        this.type = type;
        this.address = address;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.done = done;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ShipmentType getType() {
        return type;
    }

    public void setType(ShipmentType type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
