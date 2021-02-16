package com.hsg.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.hsg.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@DefaultProperties(defaultFallback = "paymentInfoTimeoutHandlerDefault",commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
})
public class PaymentServiceImpl implements PaymentService {

    @Value("${server.port}")
    private String serverPort;

    public String paymentInfoOk(){
        return "线程池：" + Thread.currentThread().getName() + "  paymentInfo_ok，serverPort：" + serverPort;
    }


    /**
    * 服务降级
    * 一旦paymentInfoTimeout方法调用失败后(可以是超时或者方法异常了，@HystrixProperty设定3秒超时，超时后算失败)
    * 会自动调用@HystrixCommand注释标注好的fallbackMethod指定方法
    * @Author:Hsg
    * @Date: 2021/2/15 下午5:23
    */
    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfoTimeout(){
        try{
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "  paymentInfo_timeout，serverPort：" + serverPort;
    }


    //这个用了全局服务降级
    @HystrixCommand
    public String paymentInfoTimeout2(){
        try{
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "  paymentInfo_timeout，serverPort：" + serverPort;
    }

    /**
    * 当服务不可用时，这个将作为兜底方案
    * @Author:Hsg
    * @Date: 2021/2/15 下午5:21
    */
    public String paymentInfoTimeoutHandler(){
        return "线程池：" + Thread.currentThread().getName() + "  paymentInfo_timeout_handler，serverPort：" + serverPort;
    }

    //全局默认服务降级处理方法
    public String paymentInfoTimeoutHandlerDefault(){
        return "线程池：" + Thread.currentThread().getName() + "  paymentInfoTimeoutHandlerDefault，serverPort：" + serverPort;
    }


    /**
    * 熔断
    * @Author:Hsg
    * @Date: 2021/2/16 下午3:10
    */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallBack", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启熔断机制
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //睡眠窗口期-熔断多久以后开始尝试是否恢复
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")  //失败率-达到多少触发

    })
    public String paymentCircuitBreaker(Integer id){
        String serialNUmber = IdUtil.simpleUUID();
        if(id < 1){
            throw new RuntimeException("id 不能小于 1，  流水号："+ serialNUmber);
        }

        return "----调用成功----paymentCircuitBreaker--------流水号："+ serialNUmber;
    }
    public String paymentCircuitBreakerFallBack(Integer id){
        String serialNUmber = IdUtil.simpleUUID();
        return "----调用失败----paymentCircuitBreakerFallBack--------流水号："+ serialNUmber;
    }

}
