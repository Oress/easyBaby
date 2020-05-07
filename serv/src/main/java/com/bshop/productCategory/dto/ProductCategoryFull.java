package com.bshop.productCategory.dto;

import java.util.List;

public class ProductCategoryFull {
    public Integer id;
    public String title;
    public List<ProductCategoryFull> children;
//    public ProductCategoryFull parent;
    public Integer parentId;
}
