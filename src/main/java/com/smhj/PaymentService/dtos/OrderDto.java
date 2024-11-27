package com.smhj.PaymentService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    String id;
    Long amount;
    String currency;
    String receipt;
    String orderStatus;
}
