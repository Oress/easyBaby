package com.bshop.ProductImage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "PRODIMG")
public class ProductImage {
    @Id
    @GeneratedValue
    @Column(name = "PRODIMG_ID")
    private Integer id;

    @Column(name = "PRODIMG_PATH")
    private String path;

    @Column(name = "PRODIMG_PATH_SM")
    private String path_small;

    public ProductImage() {
    }

    public ProductImage(Integer id, String path, String path_small) {
        this.id = id;
        this.path = path;
        this.path_small = path_small;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath_small() {
        return path_small;
    }

    public void setPath_small(String path_small) {
        this.path_small = path_small;
    }
}
