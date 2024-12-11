package com.smhj.PaymentService.paymentgateway;

import com.razorpay.Order;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.smhj.PaymentService.models.OrderEntity;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentGateway {

    PaymentLink generatePaymentObject(OrderEntity order) throws RazorpayException;

    List<Order> getOrders() throws RazorpayException;
    Order getOrderById(String orderId) throws RazorpayException;

    Order createOrder(String receipt, BigDecimal amount, String currency) throws RazorpayException;
}
