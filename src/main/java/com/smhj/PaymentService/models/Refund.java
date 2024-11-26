package com.smhj.PaymentService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "refunds")
public class Refund extends BaseModel{

    @Column(name = "razorpay_refund_id", unique = true)
    private String razorpayRefundId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;
}