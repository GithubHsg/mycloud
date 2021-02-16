package com.hsg.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface OpenFeignPaymentService {

    @GetMapping(value = "/hystrixPayment/ok")
    String paymentInfoOk();

    @GetMapping(value = "/hystrixPayment/timeout")
    String paymentInfoTimeOut();

}
