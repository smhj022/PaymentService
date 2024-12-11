package com.smhj.PaymentService.paymentgateway;

import com.razorpay.Order;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.smhj.PaymentService.models.OrderEntity;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;



@Component
@Primary
public class RazorpayPaymentGateway implements PaymentGateway{

    private RazorpayClient razorpayClient;

    public RazorpayPaymentGateway(RazorpayClient razorpayClient){
        this.razorpayClient = razorpayClient;
    }

    @Override
    public PaymentLink generatePaymentObject(OrderEntity order) throws RazorpayException {

        Instant now = Instant.now();

        // Add 20 minutes to the current timestamp
        Instant futureTime = now.plusSeconds(20 * 60);

        // Convert to epoch seconds
        long epochTime = futureTime.getEpochSecond();
        
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", order.getAmount());
        paymentLinkRequest.put("currency","INR");

        paymentLinkRequest.put("reference_id" , order.getOrderId());

        paymentLinkRequest.put("expire_by", futureTime.getEpochSecond());

        paymentLinkRequest.put("description","Payment for order_id" + order.getOrderId());


        // customer details
        JSONObject customer = new JSONObject();
        customer.put("name","+919876543210");
        customer.put("contact","Suyash Mahajan");
        customer.put("email","smhj@gmail.com");

        paymentLinkRequest.put("customer",customer);

        // notification details
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("notify",notify);

        paymentLinkRequest.put("reminder_enable",true);

        // callback URL
        paymentLinkRequest.put("callback_url","http://localhost:8080/payments/sampleAPI");
        paymentLinkRequest.put("callback_method","get");

        return razorpayClient.paymentLink.create(paymentLinkRequest);
    }

    @Override
    public List<Order> getOrders() throws RazorpayException {
        return razorpayClient.orders.fetchAll();
    }

    @Override
    public Order getOrderById(String orderId) throws RazorpayException {
        return razorpayClient.orders.fetch(orderId);
    }


    /**
     *
     * @param receipt -> unique identifier of the order
     * @param amount -> Total amount of order
     * @param currency -> current of amount
     * @return Order object
     * @throws RazorpayException if unable to create the object
     */
    @Override
    public Order createOrder(String receipt, BigDecimal amount, String currency) throws RazorpayException {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", receipt);
        orderRequest.put("amount", amount.multiply(BigDecimal.valueOf(100)));
        return razorpayClient.orders.create(orderRequest);
    }
}
