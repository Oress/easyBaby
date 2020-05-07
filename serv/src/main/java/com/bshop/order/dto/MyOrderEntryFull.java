package com.bshop.order.dto;

import com.bshop.ProductImage.dto.ProductImageFull;
import com.bshop.orderItem.dto.OrderItemFull;
import com.bshop.payment.dto.PaymentInfoFull;
import com.bshop.product.dto.CartItemFull;
import com.bshop.shipment.dto.ShipmentInfoFull;

import java.util.List;

public class MyOrderEntryFull {
    public Integer productId;
    public String productTitle;
    public ProductImageFull image;
    public Integer count;
    public List<String> barcodes;
}
