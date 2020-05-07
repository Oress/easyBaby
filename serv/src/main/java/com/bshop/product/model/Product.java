package com.bshop.product.model;

import com.bshop.ProductImage.model.ProductImage;
import com.bshop.inventoryItem.model.InventoryItem;
import com.bshop.productCategory.model.ProductCategory;
import com.bshop.productCharValue.model.ProductCharValue;
import com.bshop.user.model.User;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "PROD_ID")
    private Integer id;

    @Column(name = "PROD_TITLE")
    private String title;

    @Lob
    @Nationalized
    @Column(name = "PROD_DESCR")
    private String description;

    @Column(name = "PROD_PRICE", precision = 2)
    private Double price;

    @Column(name = "PROD_REGDT")
    private Date registrationDate;

    @ManyToOne
    private User addedBy;

    @OneToMany
    private List<ProductImage> images;

    @OneToMany
    private List<ProductCharValue> productCharValues;

    @ManyToMany
    private List<ProductCategory> productCategories;

    @OneToMany(mappedBy = "product")
    private List<InventoryItem> inventoryItems;

    public Product() {
    }

    public Product(Integer id, String title, String description, Double price, Date registrationDate, User addedBy, List<ProductImage> images, List<ProductCharValue> productCharValues, List<ProductCategory> productCategories, List<InventoryItem> inventoryItems) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.registrationDate = registrationDate;
        this.addedBy = addedBy;
        this.images = images;
        this.productCharValues = productCharValues;
        this.productCategories = productCategories;
        this.inventoryItems = inventoryItems;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public List<ProductCharValue> getProductCharValues() {
        return productCharValues;
    }

    public void setProductCharValues(List<ProductCharValue> productCharValues) {
        this.productCharValues = productCharValues;
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
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

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}
