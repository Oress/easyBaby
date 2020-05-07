package com.bshop.order.dto;

import com.bshop.orderItem.dto.OrderItemFull;
import com.bshop.payment.dto.PaymentInfoFull;
import com.bshop.product.dto.CartItemFull;
import com.bshop.shipment.dto.ShipmentInfoFull;

import java.util.List;

public class OrderFull {
    public Integer id;
    public List<CartItemFull> cartItems;
    public List<OrderItemFull> orderItems;
    public PaymentInfoFull paymentInfo;
    public ShipmentInfoFull shipmentInfo;
}
