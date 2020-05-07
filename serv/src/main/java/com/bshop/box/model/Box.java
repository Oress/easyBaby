package com.bshop.box.model;

import com.bshop.product.model.Product;
import com.bshop.productCharValue.model.ProductCharValue;
import org.hibernate.type.ImageType;

import javax.persistence.*;
import java.util.List;

@Entity(name = "BOX")
public class Box {
    @Id
    @GeneratedValue
    @Column(name = "BOX_ID")
    private Integer id;

    @Column(name = "BOX_TITLE")
    private String title;

    @Column(name = "BOX_DESCR")
    private String description;

    @Column(name = "BOX_PRICE", precision = 2)
    private Double price;

    @Column(name = "BOX_IMG")
    private ImageType image;

    @OneToMany
    private List<ProductCharValue> productChars;

    @OneToMany
    private List<Product> content;

    public Box() {
    }

    public Box(Integer id, String title, String description, Double price, ImageType image, List<ProductCharValue> productChars, List<Product> content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.productChars = productChars;
        this.content = content;
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

    public List<ProductCharValue> getProductChars() {
        return productChars;
    }

    public void setProductChars(List<ProductCharValue> productChars) {
        this.productChars = productChars;
    }

    public List<Product> getContent() {
        return content;
    }

    public void setContent(List<Product> content) {
        this.content = content;
    }

    public ImageType getImage() {
        return image;
    }

    public void setImage(ImageType image) {
        this.image = image;
    }
}
