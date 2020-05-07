package com.bshop.productCategory.assembler;

import com.bshop.productCategory.model.ProductCategory;
import com.bshop.productCategory.dto.ProductCategoryFull;

public class ProductCategoryAssembler {
    public ProductCategoryFull assembleFullDto(ProductCategory model) {
        ProductCategoryFull result = new ProductCategoryFull();

        result.id = model.getId();
        result.title = model.getTitle();
        result.parentId = model.getParent() != null ? model.getParent().getId() : null;
//        if (model.getChildren() != null) {
//            result.children = model.getChildren().stream().map(this::assembleFullDto).collect(Collectors.toList());
//        }

        return result;
    }

    public ProductCategory assembleModel(ProductCategoryFull dto) {
        ProductCategory result = new ProductCategory();

        result.setId(dto.id);
        result.setTitle(dto.title);
//        result.setParent(assembleModel(dto.);
//        if (dto.children != null) {
//            result.setChildren(dto.children.stream().map(this::assembleModel).collect(Collectors.toList()));
//        }

        return result;
    }
}
