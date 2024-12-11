package com.smhj.PaymentService.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayException;
import com.smhj.PaymentService.dtos.CreateOrderDto;
import com.smhj.PaymentService.dtos.OrderDto;
import com.smhj.PaymentService.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderDto requestDto) throws RazorpayException {
        return new ResponseEntity<>(paymentService.createOrder(requestDto.getAmount(),
                requestDto.getCurrency(), requestDto.getUserId()), HttpStatus.OK);
    }

    @GetMapping("/checkout/{orderId}")
    public ResponseEntity<String> checkoutOrder(@PathVariable("orderId") String orderId) throws RazorpayException {
        return new ResponseEntity<>(paymentService.checkoutOrder(orderId), HttpStatus.OK);
    }


    @GetMapping("/ordersList")
    public ResponseEntity<List<OrderDto>> getOrdersDetails() throws RazorpayException, JsonProcessingException {
        return new ResponseEntity<>(paymentService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") String orderId) throws JsonProcessingException, RazorpayException {
        return new ResponseEntity<>(paymentService.getOrderByOrderId(orderId), HttpStatus.OK);
    }

    @PostMapping("/sampleAPI")
    public String sampleAPI() {
        return "Hello from payment Service";
    }
}
