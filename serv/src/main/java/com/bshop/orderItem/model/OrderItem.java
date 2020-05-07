package com.bshop.orderItem.model;

import com.bshop.inventoryItem.model.InventoryItem;
import com.bshop.order.model.Order;

import javax.persistence.*;

@Entity(name = "ORDERITEM")
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "ORDITM_ID")
    private Integer id;

    @OneToOne
    private InventoryItem inventoryItem;

    @Column(name = "ORDITM_PRICE", precision = 2)
    private Double price;

    @ManyToOne
    private Order order;

    public OrderItem() {
    }

    public OrderItem(Integer id, InventoryItem inventoryItem, Double price, Order order) {
        this.id = id;
        this.inventoryItem = inventoryItem;
        this.price = price;
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
