package com.bshop.productCharValue.model;

import com.bshop.product.model.Product;
import com.bshop.productChar.model.ProductChar;

import javax.persistence.*;

@Entity(name="PRODCHARVAL")
public class ProductCharValue {
    @Id
    @GeneratedValue
    @Column(name = "PROD_CHV_ID")
    private Integer id;

    @ManyToOne
    private ProductChar productChar;

    @Column(name = "PROD_CHV_VAL")
    private String value;

    @ManyToOne
    private Product product;

    public ProductCharValue() {
    }

    public ProductCharValue(Integer id, ProductChar productChar, String value, Product product) {
        this.id = id;
        this.productChar = productChar;
        this.value = value;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductChar getProductChar() {
        return productChar;
    }

    public void setProductChar(ProductChar productChar) {
        this.productChar = productChar;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
