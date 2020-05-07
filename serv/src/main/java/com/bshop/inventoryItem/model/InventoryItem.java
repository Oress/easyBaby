package com.bshop.inventoryItem.model;

import com.bshop.product.model.Product;
import com.bshop.user.model.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "INVITEM")
public class InventoryItem {
    @Id
    @GeneratedValue
    @Column(name = "IITM_ID")
    private Integer id;

    @Column(name = "IITM_BRCD")
    private String barcode;

    @ManyToOne
    private Product product;

    @OneToOne
    private User addedBy;

    @CreationTimestamp
    @Column(name = "IITM_REGDT")
    private Date registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "IITM_STATUS")
    private InventoryItemStatus status = InventoryItemStatus.AVAILABLE;

    public InventoryItem() {
    }

    public InventoryItem(Integer id, String barcode, Product product, User addedBy, Date registrationDate, InventoryItemStatus status) {
        this.id = id;
        this.barcode = barcode;
        this.product = product;
        this.addedBy = addedBy;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public InventoryItemStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryItemStatus status) {
        this.status = status;
    }
}
