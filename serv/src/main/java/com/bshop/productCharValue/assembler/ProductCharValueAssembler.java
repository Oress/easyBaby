package com.bshop.productCharValue.assembler;

import com.bshop.productCharValue.dto.ProductCharValueFull;
import com.bshop.productCharValue.model.ProductCharValue;

public class ProductCharValueAssembler {
    public ProductCharValueFull assebleFullDto(ProductCharValue model) {
        ProductCharValueFull result = new ProductCharValueFull();

        result.id = model.getId();
        result.productCharId = model.getProductChar().getId();
        result.value = model.getValue();

        return result;
    }

    public ProductCharValue assemblePartialModel(ProductCharValueFull dto) {
        ProductCharValue result = new ProductCharValue();

        return result;
    }
}
