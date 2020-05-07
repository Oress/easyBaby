package com.bshop.product.assembler;

import com.bshop.ProductImage.assembler.ProductImageAssembler;
import com.bshop.inventoryItem.assembler.InventoryItemAssembler;
import com.bshop.product.dto.ProductFull;
import com.bshop.product.dto.ProductShort;
import com.bshop.product.model.Product;
import com.bshop.productCategory.model.ProductCategory;
import com.bshop.productCharValue.assembler.ProductCharValueAssembler;

import java.util.stream.Collectors;

public class ProductAssembler {
    private ProductCharValueAssembler productCharValueAssembler = new ProductCharValueAssembler();
    private InventoryItemAssembler inventoryItemAssembler = new InventoryItemAssembler();
    private ProductImageAssembler productImageAssembler = new ProductImageAssembler();

    public ProductFull mapModelToProductFull(Product model) {
        ProductFull result = new ProductFull();
        result.id = model.getId();
        result.title = model.getTitle();
        result.description = model.getDescription();
        result.price = model.getPrice();
        if (model.getProductCharValues() != null) {
            result.productCharValues = model.getProductCharValues().stream().map(productCharValueAssembler::assebleFullDto).collect(Collectors.toList());
        }
        if (model.getProductCategories() != null) {
            result.categoryIds = model.getProductCategories().stream().map(ProductCategory::getId).collect(Collectors.toList());
        }
        if (model.getInventoryItems() != null) {
            result.inventoryItems = model.getInventoryItems().stream().map(inventoryItemAssembler::assebleFullDto).collect(Collectors.toList());
        }
        if (model.getImages() != null) {
            result.images = model.getImages().stream().map(productImageAssembler::assembleFullDto).collect(Collectors.toList());
        }
        return result;
    }

    public ProductShort mapModelToProductShort(Product model) {
        ProductShort result = new ProductShort();
        result.id = model.getId();
        result.title = model.getTitle();
        result.description = model.getDescription();
        result.price = model.getPrice();
        result.addedById = model.getAddedBy() != null ? model.getAddedBy().getId() : null;
        result.registrationDate = model.getRegistrationDate();
        if(model.getImages().size() > 0)
        {
            result.image = productImageAssembler.assembleFullDto(model.getImages().get(0));
        }
        return result;
    }

    public Product mapFullToPartialModel(ProductFull dto) {
        Product result = new Product();
        result.setTitle(dto.title);
        result.setDescription(dto.description);
        result.setPrice(dto.price);
//        result.setProductCharValues();
//        result.setImages();

        return result;
    }
}
