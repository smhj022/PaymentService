package com.smhj.PaymentService.paymentgateway;

import com.razorpay.Order;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentGateway {

    PaymentLink generatePaymentObject(String orderId, BigDecimal amount, String currency) throws RazorpayException;

    List<Order> getOrders() throws RazorpayException;

    Order getOrderById(String orderId) throws RazorpayException;

    Order createOrder(String receipt, BigDecimal amount, String currency) throws RazorpayException;
}
