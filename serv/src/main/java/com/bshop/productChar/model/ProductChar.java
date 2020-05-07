package com.bshop.productChar.model;


import javax.persistence.*;

@Entity(name="PRODCHAR")
public class ProductChar {
    @Id
    @GeneratedValue
    @Column(name = "PROD_CH_ID")
    private Integer id;

    @Column(name = "PROD_CH_TITLE")
    private String title;

//    @Column(name = "PROD_DESCR")
//    private String description;

    @Column(name = "PROD_CH_TYPE")
    @Enumerated(value = EnumType.STRING)
    private ProductCharType type;

    public ProductChar() {
    }

    public ProductChar(Integer id, String title, ProductCharType type) {
        this.id = id;
        this.title = title;
        this.type = type;
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

    public ProductCharType getType() {
        return type;
    }

    public void setType(ProductCharType type) {
        this.type = type;
    }
}
