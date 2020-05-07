package com.bshop.productCategory.model;

import javax.persistence.*;

@Entity(name = "PRODCATEGORY")
public class ProductCategory {
    @Id
    @GeneratedValue
    @Column(name = "PROD_CAT_ID")
    private Integer id;

    @Column(name = "PROD_CAT_TITLE")
    private String title;

    @ManyToOne
    private ProductCategory parent;

    public ProductCategory() {
    }

    public ProductCategory(Integer id, String title, ProductCategory parent) {
        this.id = id;
        this.title = title;
        this.parent = parent;
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

    public ProductCategory getParent() {
        return parent;
    }

    public void setParent(ProductCategory parent) {
        this.parent = parent;
    }
}
