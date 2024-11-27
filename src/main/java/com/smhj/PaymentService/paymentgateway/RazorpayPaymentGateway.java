package com.smhj.PaymentService.paymentgateway;

import com.razorpay.Order;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.time.Instant;
import java.util.List;
import java.util.Objects;


@Component
@Primary
public class RazorpayPaymentGateway implements PaymentGateway{

    private RazorpayClient razorpayClient;

    public RazorpayPaymentGateway(RazorpayClient razorpayClient){
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String generatePaymentLink(Long orderId, Long amount) throws RazorpayException {

        Instant now = Instant.now();

        // Add 20 minutes to the current timestamp
        Instant futureTime = now.plusSeconds(20 * 60);

        // Convert to epoch seconds
        long epochTime = futureTime.getEpochSecond();
        
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",1000);
        paymentLinkRequest.put("currency","INR");
//        paymentLinkRequest.put("accept_partial",true);
//        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by", futureTime.getEpochSecond());
        paymentLinkRequest.put("reference_id", orderId.toString());
        paymentLinkRequest.put("description","Payment for test");
        JSONObject customer = new JSONObject();
        customer.put("name","+919876543210");
        customer.put("contact","Suyash Mahajan");
        customer.put("email","smhj@gmail.com");
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("notify",notify);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
//        notes.put("policy_name","Jeevan Bima");
//        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","http://localhost:8080/payments/sampleAPI");
        paymentLinkRequest.put("callback_method","get");


        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);


        return payment.get("short_url");
    }

    @Override
    public List<Order> getOrders() throws RazorpayException {
        return razorpayClient.orders.fetchAll();
    }
}
