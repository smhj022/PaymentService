package com.smhj.PaymentService.paymentgateway;

import com.razorpay.RazorpayException;

public interface PaymentGateway {

    String generatePaymentLink(Long orderId, Long amount) throws RazorpayException;
}
