package com.smhj.PaymentService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import com.smhj.PaymentService.dtos.OrderDto;
import com.smhj.PaymentService.models.*;
import com.smhj.PaymentService.paymentgateway.PaymentGateway;
import com.smhj.PaymentService.repositories.OrderRepository;
import com.smhj.PaymentService.repositories.PaymentRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


@Service
public class PaymentService {

    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentGateway paymentGateway, PaymentRepository paymentRepository,
                          OrderRepository orderRepository, OrderRepository orderRepository1){
        this.paymentGateway = paymentGateway;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Method to create the order and generate a payment link;
     */
    public String createOrder(BigDecimal amount, String currency, Long userId) throws RazorpayException {
        String receipt = "rcpt_" + userId + "_" + System.currentTimeMillis();
        Order razorpayOrder = paymentGateway.createOrder(receipt, amount, currency);
        OrderEntity order = razorpayOrderToOrderDto(razorpayOrder.toJson(), userId, receipt);
        orderRepository.save(order);
        return order.getOrderId();
    }

    public OrderEntity razorpayOrderToOrderDto(JSONObject razorpayOrderObj, Long userId, String receipt){
        OrderEntity order = new OrderEntity();
        order.setAmount(razorpayOrderObj.getBigDecimal("amount"));
        order.setOrderStatus(parseOrderStatus(razorpayOrderObj.getString("status")));
        order.setCurrency(parseCurrency("INR"));
        order.setOrderId(razorpayOrderObj.getString("id"));
        order.setReceipt(receipt);
        order.setMetadata(razorpayOrderObj.toString());
        order.setUserId(userId);
        return order;
    }

    /**
     *  List of all the orders
     */
    public List<OrderDto> getAllOrders() throws RazorpayException, JsonProcessingException {
        List<Order> orderList = paymentGateway.getOrders();

        List<OrderDto> orderDtoList = new LinkedList<>();
        for(Order order : orderList){
            orderDtoList.add(rozorpayOrderToOrderDto(order));
        }
        return orderDtoList;
    }

    public OrderDto getOrderByOrderId(String orderId) throws RazorpayException, JsonProcessingException {
        return rozorpayOrderToOrderDto(paymentGateway.getOrderById(orderId));
    }

    public OrderDto rozorpayOrderToOrderDto(Order razorpayOrder) throws JsonProcessingException {
        OrderDto orderDto = new OrderDto();
        JSONObject jsonObject = razorpayOrder.toJson();
        orderDto.setId(jsonObject.getString("id"));
        orderDto.setOrderStatus(jsonObject.getString("status"));
        orderDto.setAmount(jsonObject.getLong("amount"));
        orderDto.setCurrency(jsonObject.getString("currency"));
        orderDto.setReceipt(jsonObject.getString("receipt"));
        return orderDto;
    }

    /**
     * Parse the status string into the Status enum.
     */
    private PaymentStatus parseStatus(String status) {
        try {
            return PaymentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status received: " + status, e);
        }
    }


    /**
     * Parse the status string into the Status enum.
     */
    private OrderStatus parseOrderStatus(String status) {
        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status received: " + status, e);
        }
    }

    /**
     * Parse the currency string into the Currency enum.
     */
    private Currency parseCurrency(String currency) {
        try {
            return Currency.valueOf(currency.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status received: " + currency, e);
        }
    }


}
