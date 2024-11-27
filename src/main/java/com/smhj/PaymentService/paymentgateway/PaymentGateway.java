package com.smhj.PaymentService.paymentgateway;

import com.razorpay.Order;
import com.razorpay.RazorpayException;

import java.util.List;

public interface PaymentGateway {

    String generatePaymentLink(Long orderId, Long amount) throws RazorpayException;

    List<Order> getOrders() throws RazorpayException;
}
