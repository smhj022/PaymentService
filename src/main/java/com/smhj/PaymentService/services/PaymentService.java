package com.smhj.PaymentService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import com.smhj.PaymentService.dtos.OrderDto;
import com.smhj.PaymentService.paymentgateway.PaymentGateway;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PaymentService {

    private PaymentGateway paymentGateway;

    public PaymentService(PaymentGateway paymentGateway){
        this.paymentGateway = paymentGateway;
    }

    public String initiatePayment(Long orderId, Long amount) throws RazorpayException {
        return paymentGateway.generatePaymentLink(orderId, amount);
    }

    public List<OrderDto> getAllOrders() throws RazorpayException, JsonProcessingException {
        List<Order> orderList = paymentGateway.getOrders();

        List<OrderDto> orderDtoList = new LinkedList<>();
        for(Order order : orderList){
            orderDtoList.add(rozorpayOrderToOrderDto(order));
        }
        return orderDtoList;
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
}
