package com.smhj.PaymentService.paymentgateway;

import com.razorpay.Order;
import com.razorpay.RazorpayException;

import java.util.List;

public class StripePaymentGateway implements PaymentGateway{

    @Override
    public String generatePaymentLink(Long orderId, Long amount) {
        return null;
    }

    @Override
    public List<Order> getOrders() throws RazorpayException {
        return null;
    }
}
