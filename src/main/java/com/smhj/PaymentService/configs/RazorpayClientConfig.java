package com.smhj.PaymentService.configs;


import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayClientConfig  {

    @Value("${RazorPayKeyID}")
    private String keyID;

    @Value("${RazorPayKeySecret}")
    private String KeyValue;

    @Bean
    public RazorpayClient getRazorpayConfig() throws RazorpayException {
        return new RazorpayClient(keyID, KeyValue);
    }
}
