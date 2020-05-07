package com.bshop.product.dto;

public class CartItemFull {
    public Integer id;
    public Integer productId;
    public String productTitle;
    public String image;
    public Integer count;
    public Double pricePerItem;
    public Double totalPrice;
    public Boolean isAvailable = Boolean.TRUE;

    public CartItemFull(Integer id, Integer productId, String productTitle, String image, Integer count, Double pricePerItem, Double totalPrice) {
        this.id = id;
        this.productId = productId;
        this.image = image;
        this.productTitle = productTitle;
        this.count = count;
        this.pricePerItem = pricePerItem;
        this.totalPrice = totalPrice;
    }
}
