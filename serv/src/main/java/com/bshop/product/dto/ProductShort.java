package com.bshop.product.dto;

import com.bshop.ProductImage.dto.ProductImageFull;
import java.util.Date;

public class ProductShort {
    public Integer id;
    public String title;
    public String description;
    public Double price;
    public Date registrationDate;
    public Integer addedById;
    public ProductImageFull image;
//    public Map<String, String> productCharValues;

    //    private ImageType firstImage;
}
