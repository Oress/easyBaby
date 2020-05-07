package com.bshop.payment.assembler;

import com.bshop.payment.dto.PaymentInfoFull;
import com.bshop.payment.model.Payment;

public class PaymentAssembler {

    public PaymentInfoFull assembleFullDto(Payment model) {
        PaymentInfoFull result = new PaymentInfoFull();

        result.id = model.getId();
        result.type = model.getType();
        result.done = model.getDone();

        return result;
    }

}
