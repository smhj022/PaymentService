package com.smhj.PaymentService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class PaymentModel extends BaseModel {

    @Column(name = "razorpay_refund_id", unique = true, nullable = false)
    private String razorpayPaymentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status paymentStatus;

}
