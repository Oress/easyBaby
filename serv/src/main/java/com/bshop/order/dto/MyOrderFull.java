package com.bshop.order.dto;

import com.bshop.payment.dto.PaymentInfoFull;
import com.bshop.shipment.dto.ShipmentInfoFull;

import java.util.List;

public class MyOrderFull {
    public Integer id;
    public Double total;
    public List<MyOrderEntryFull> orderItems;
    public PaymentInfoFull paymentInfo;
    public ShipmentInfoFull shipmentInfo;
}
