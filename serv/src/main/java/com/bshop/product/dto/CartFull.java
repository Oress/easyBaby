package com.bshop.product.dto;

import java.util.List;

public class CartFull {
    public List<CartItemFull> available;
    public Double totalPrice;

    public CartFull(List<CartItemFull> available) {
        this.available = available;
    }
}
