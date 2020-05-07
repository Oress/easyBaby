package com.bshop.preorder.dto;

import com.bshop.payment.model.PaymentType;
import com.bshop.preorder.model.TimePeriodType;
import com.bshop.preorderItem.dto.PreorderItemFull;
import com.bshop.shipment.model.ShipmentType;

import java.util.Date;
import java.util.List;

public class PreorderFull {
    public Integer id;
    public List<PreorderItemFull> preorderItems;
    public String address;
    public String phone;
    public String name;
    public ShipmentType shipmentType;
    public PaymentType paymentType;
    public Date createdDate;
    public TimePeriodType timePeriodType;
    public Integer count;
}
