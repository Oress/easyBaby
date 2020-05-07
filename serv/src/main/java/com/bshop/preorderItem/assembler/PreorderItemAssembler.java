package com.bshop.preorderItem.assembler;

import com.bshop.ProductImage.assembler.ProductImageAssembler;
import com.bshop.preorderItem.dto.PreorderItemFull;
import com.bshop.preorderItem.model.PreorderItem;
import com.bshop.product.model.Product;

public class PreorderItemAssembler {
    ProductImageAssembler productImageAssembler = new ProductImageAssembler();


    public PreorderItemFull assembleFullDto(PreorderItem model) {
        PreorderItemFull result = new PreorderItemFull();
        result.id = model.getId();
        result.count = model.getCount();

        Product product = model.getProduct();
        result.productTitle = product.getTitle();
        result.productId = product.getId();
        result.productPrice = product.getPrice();

        if (product.getImages().size() != 0) {
            result.image = productImageAssembler.assembleFullDto(product.getImages().get(0));
        }

        result.totalPrice = result.productPrice * result.count;

        return result;
    }

}
