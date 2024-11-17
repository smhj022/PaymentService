package com.smhj.PaymentService.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentRequestDto {
    private long orderId;
    private long amount;
}
