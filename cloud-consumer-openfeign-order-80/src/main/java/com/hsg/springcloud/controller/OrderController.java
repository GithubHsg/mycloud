package com.hsg.springcloud.controller;

import com.hsg.springcloud.entities.CommonResult;
import com.hsg.springcloud.entities.Payment;
import com.hsg.springcloud.service.OpenFeignPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@Slf4j
@RequestMapping("/consumer/order")
public class OrderController {

    @Resource
    private OpenFeignPaymentService openFeignPaymentService;

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        log.info("获取");
        return openFeignPaymentService.getPaymentById(id);
    }

    @GetMapping("/feign/timeout")
    public String paymentFeignTimeout(){
        //openfeign-ribbon客户端默认等待一秒钟
        log.info("测试openfeign超时控制");
        return openFeignPaymentService.paymentFeignTimeout();
    }
}
