package com.smhj.PaymentService.paymentgateway;

import com.razorpay.Order;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;

import java.math.BigDecimal;
import java.util.List;

public class StripePaymentGateway implements PaymentGateway{

    @Override
    public PaymentLink generatePaymentObject(String orderId, BigDecimal amount, String currency) throws RazorpayException {
        return null;
    }

    @Override
    public List<Order> getOrders() throws RazorpayException {
        return null;
    }

    @Override
    public Order getOrderById(String orderId) throws RazorpayException {
        return null;
    }
}
