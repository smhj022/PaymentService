package com.smhj.PaymentService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "refunds")
public class RefundModel extends BaseModel{

//    @Column(name = "razorpay_refund_id", unique = true)
//    private String razorpayRefundId;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private PaymentStatus refundStatus;
//
//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "payment_id", nullable = false)
//    private PaymentEntity payment;


}
