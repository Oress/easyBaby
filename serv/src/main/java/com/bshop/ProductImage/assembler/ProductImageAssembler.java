package com.bshop.ProductImage.assembler;

import com.bshop.ProductImage.dto.ProductImageFull;
import com.bshop.ProductImage.model.ProductImage;

public class ProductImageAssembler {

    public ProductImageFull assembleFullDto(ProductImage model) {
        ProductImageFull result = new ProductImageFull();

        result.id = model.getId();
        result.path = model.getPath();
        result.path_sm = model.getPath_small();

        return result;
    }
}
