package com.bshop.product.dto;

import com.bshop.ProductImage.dto.ProductImageFull;
import com.bshop.inventoryItem.dto.InventoryItemFull;
import com.bshop.productCharValue.dto.ProductCharValueFull;

import java.util.List;

public class ProductFull extends ProductShort {
    public List<ProductCharValueFull> productCharValues;
    public List<Integer> categoryIds;
    public List<InventoryItemFull> inventoryItems;
    public List<ProductImageFull> images;
}
