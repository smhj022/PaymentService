package com.smhj.PaymentService.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends BaseModel {

    @Column(name = "razorpay_refund_id", unique = true, nullable = false)
    private String razorpayPaymentId;

}
