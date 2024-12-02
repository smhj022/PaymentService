package com.smhj.PaymentService.dtos;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOrderDto {
    private Long userId;
    private BigDecimal amount;
    private String currency;
}
