package com.smhj.PaymentService.controllers;

import com.razorpay.RazorpayException;
import com.smhj.PaymentService.dtos.InitiatePaymentRequestDto;
import com.smhj.PaymentService.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/")
    public String initiatePayment(@RequestBody InitiatePaymentRequestDto requestDto) throws RazorpayException {
        try {
            return paymentService.initiatePayment(requestDto.getOrderId(), requestDto.getAmount());
        } catch (Exception e){
            return e.toString();
        }

    }

    @GetMapping("/sampleAPI")
    public String sampleAPI() {
        return "Hello from payment Service";
    }
}
