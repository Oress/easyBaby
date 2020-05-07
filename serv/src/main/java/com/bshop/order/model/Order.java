package com.bshop.order.model;

import com.bshop.orderItem.model.OrderItem;
import com.bshop.payment.model.Payment;
import com.bshop.shipment.model.Shipment;
import com.bshop.user.model.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "ORDERT")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ORD_ID")
    private Integer id;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @ManyToOne
    private User recipient;

    @OneToOne
    private Payment payment;

    @OneToOne
    private Shipment shipment;

    @CreationTimestamp
    @Column(name = "ORD_CREATDT")
    private Date createdDate;

    public Order() {
    }

    public Order(Integer id, List<OrderItem> orderItems, User recipient, Payment payment, Shipment shipment, Date createdDate) {
        this.id = id;
        this.orderItems = orderItems;
        this.recipient = recipient;
        this.payment = payment;
        this.shipment = shipment;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
}
