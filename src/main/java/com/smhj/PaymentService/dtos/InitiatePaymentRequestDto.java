package com.smhj.PaymentService.dtos;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InitiatePaymentRequestDto {
    private String orderId;
    private BigDecimal amount;
    private String currency;
}
