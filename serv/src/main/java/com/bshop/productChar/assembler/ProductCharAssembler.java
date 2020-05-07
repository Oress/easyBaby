package com.bshop.productChar.assembler;

import com.bshop.productChar.dto.ProductCharFull;
import com.bshop.productChar.model.ProductChar;

public class ProductCharAssembler {


    public ProductCharFull assebleFullDto(ProductChar model) {
        ProductCharFull result = new ProductCharFull();

        result.id = model.getId();
        result.title = model.getTitle();
        result.type = model.getType();

        return result;
    }

    public ProductChar assembleModel(ProductCharFull dto) {
        ProductChar result = new ProductChar();

        result.setTitle(dto.title);
        result.setType(dto.type);

        return result;
    }
}
