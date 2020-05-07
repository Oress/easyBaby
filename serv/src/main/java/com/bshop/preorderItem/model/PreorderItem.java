package com.bshop.preorderItem.model;

import com.bshop.preorder.model.Preorder;
import com.bshop.product.model.Product;

import javax.persistence.*;

@Entity(name = "PREORDERITEM")
public class PreorderItem {
    @Id
    @GeneratedValue
    @Column(name = "PREORDERITEM_ID")
    private Integer id;

    @OneToOne
    private Product product;

    @Column(name = "PREORDERITEM_COUNT")
    private Integer count;

    @ManyToOne
    private Preorder preorder;

    public PreorderItem() {
    }

    public PreorderItem(Integer id, Product product, Integer count, Preorder preorder) {
        this.id = id;
        this.product = product;
        this.count = count;
        this.preorder = preorder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Preorder getPreorder() {
        return preorder;
    }

    public void setPreorder(Preorder preorder) {
        this.preorder = preorder;
    }
}
