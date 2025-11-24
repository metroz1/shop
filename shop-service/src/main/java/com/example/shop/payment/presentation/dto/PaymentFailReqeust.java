package com.example.shop.payment.presentation.dto;

import com.example.shop.payment.application.dto.PaymentFailureCommand;

public record PaymentFailReqeust(
        String orderId,
        String paymentKey,
        String code,
        String message,
        Long amount,
        String rawPayload
) {

    public PaymentFailureCommand toCommand() {
        return new PaymentFailureCommand(orderId, paymentKey, code, message, amount, rawPayload);
    }

}
