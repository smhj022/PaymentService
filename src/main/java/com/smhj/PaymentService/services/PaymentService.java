package com.smhj.PaymentService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import com.smhj.PaymentService.dtos.OrderDto;
import com.smhj.PaymentService.models.Currency;
import com.smhj.PaymentService.models.PaymentModel;
import com.smhj.PaymentService.models.Status;
import com.smhj.PaymentService.paymentgateway.PaymentGateway;
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

    public PaymentService(PaymentGateway paymentGateway, PaymentRepository paymentRepository){
        this.paymentGateway = paymentGateway;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Method to generate payment link and save the payment details for the order
     */
    public String initiatePayment(String orderId, BigDecimal amount, String currency) throws RazorpayException {
        // Generate Razorpay payment object
        JSONObject razorpayPaymentObj = paymentGateway.generatePaymentObject(orderId, amount, currency).toJson();

        // Map Razorpay response to PaymentModel and save to data base
        PaymentModel paymentModel = createPaymentModel(orderId, amount, razorpayPaymentObj);

        // Save payment details to the repository
        paymentRepository.save(paymentModel);

        // Return the short payment URL
        return razorpayPaymentObj.getString("short_url");
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
     * Build a PaymentModel instance from Razorpay response and input details.
     */
    private PaymentModel createPaymentModel(String orderId, BigDecimal amount, JSONObject razorpayPaymentObj) {
        PaymentModel paymentModel = new PaymentModel();

        paymentModel.setPaymentStatus(parseStatus(razorpayPaymentObj.getString("status")));
        paymentModel.setCurrency(parseCurrency("INR"));

        paymentModel.setRazorpayPaymentId(razorpayPaymentObj.getString("id"));
        paymentModel.setAmount(amount);
        paymentModel.setOrderId(orderId);
        paymentModel.setMetadata(razorpayPaymentObj.toString());
        paymentModel.setUserId(1L);

        return paymentModel;
    }

    /**
     * Parse the status string into the Status enum.
     */
    private Status parseStatus(String status) {
        try {
            return Status.valueOf(status.toUpperCase());
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
