package com.bshop.product.model;

import java.util.List;

public interface CustomProductRepository {
    List<Product> findByCategoryId(Integer id);
}
