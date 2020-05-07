package com.bshop.preorder.model;

import com.bshop.payment.model.PaymentType;
import com.bshop.preorderItem.model.PreorderItem;
import com.bshop.shipment.model.ShipmentType;
import com.bshop.user.model.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "PREORDER")
public class Preorder {
    @Id
    @GeneratedValue
    @Column(name = "PREORDER_ID")
    private Integer id;

    @OneToMany(mappedBy = "preorder")
    private List<PreorderItem> preorderItems;

    @ManyToOne
    private User recipient;

    private String address;

    private String phone;

    private String name;

    @Enumerated(EnumType.STRING)
    private ShipmentType shipmentType;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private TimePeriodType timePeriodType;

    private Integer count;

    @CreationTimestamp
    @Column(name = "ORD_CREATDT")
    private Date createdDate;

    public Preorder() {
    }

    public Preorder(Integer id, List<PreorderItem> preorderItems, User recipient, String address, String phone, String name, ShipmentType shipmentType, PaymentType paymentType, TimePeriodType timePeriodType, Integer count, Date createdDate) {
        this.id = id;
        this.preorderItems = preorderItems;
        this.recipient = recipient;
        this.address = address;
        this.phone = phone;
        this.name = name;
        this.shipmentType = shipmentType;
        this.paymentType = paymentType;
        this.timePeriodType = timePeriodType;
        this.count = count;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<PreorderItem> getPreorderItems() {
        return preorderItems;
    }

    public void setPreorderItems(List<PreorderItem> preorderItems) {
        this.preorderItems = preorderItems;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public TimePeriodType getTimePeriodType() {
        return timePeriodType;
    }

    public void setTimePeriodType(TimePeriodType timePeriodType) {
        this.timePeriodType = timePeriodType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
