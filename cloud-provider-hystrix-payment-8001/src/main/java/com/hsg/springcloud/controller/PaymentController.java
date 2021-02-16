package com.hsg.springcloud.controller;

import com.hsg.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@Slf4j
@RequestMapping("/hystrixPayment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/ok")
    public String paymentInfoOk(){
        return paymentService.paymentInfoOk();
    }

    @GetMapping(value = "/timeout")
    public String paymentInfoTimeOut(){

        return paymentService.paymentInfoTimeout();
    }

    @GetMapping(value = "/timeout2")
    public String paymentInfoTimeOut2(){

        return paymentService.paymentInfoTimeout2();
    }

    @GetMapping(value = "/circuitBreaker/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){

        return paymentService.paymentCircuitBreaker(id);
    }

}
