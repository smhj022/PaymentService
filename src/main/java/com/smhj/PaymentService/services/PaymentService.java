package com.smhj.PaymentService.services;

import com.razorpay.RazorpayException;
import com.smhj.PaymentService.paymentgateway.PaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private PaymentGateway paymentGateway;

    public PaymentService(PaymentGateway paymentGateway){
        this.paymentGateway = paymentGateway;
    }

    public String initiatePayment(Long orderId, Long amount) throws RazorpayException {
        return paymentGateway.generatePaymentLink(orderId, amount);
    }
}
