package com.hsg.springcloud.service;


public interface PaymentService {

    String paymentInfoOk();

    String paymentInfoTimeout();

    String paymentInfoTimeout2();

    String paymentCircuitBreaker(Integer id);
}
