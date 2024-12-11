package com.smhj.PaymentService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class PaymentEntity extends BaseModel {

    @Column(name = "razorpay_payment_id", unique = true, nullable = false)
    private String razorpayPaymentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private String paymentLink;

//    @Column(nullable = false)
//    private String expiryTime;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true) // Unique to ensure one-to-one
    private OrderEntity order;

}
