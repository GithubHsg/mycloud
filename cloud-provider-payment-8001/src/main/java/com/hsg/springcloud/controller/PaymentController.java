package com.hsg.springcloud.controller;

import com.hsg.springcloud.entities.CommonResult;
import com.hsg.springcloud.entities.Payment;
import com.hsg.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody Payment payment){
        log.info("插入：{}", payment.toString());
        int result = paymentService.create(payment);
        log.info("---插入结果：{}", result);

        if (result > 0){
            return new CommonResult(200, "插入成功", result);
        }else{
            return new CommonResult(500, "插入失败");
        }
    }

    @GetMapping(value = "/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        payment.setServerPort(serverPort);
        log.info("---查询结果：{}", payment.toString());

        if (payment != null){
            return new CommonResult(200, "查询成功", payment);
        }else{
            return new CommonResult(500, "查询失败");
        }
    }

    @GetMapping(value = "/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("******element: {}", element);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance :instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }

        return this.discoveryClient;
    }


    /**
     * 自定义的Ribbon轮询算法测试接口
     * @Author:Hsg
     * @Date: 2021/2/14 下午1:08
     */
    @GetMapping("/getPaymentLB")
    public String getPaymentLB(){
        return serverPort;
    }

    /**
    * 超时接口，测试openfeign超时控制
    * @Author:Hsg
    * @Date: 2021/2/14 下午3:36
    */
    @GetMapping(value = "/feign/timeout")
    public String paymentFeignTimeout(){
        log.info("测试openfeign超时控制");
        /*try{
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }*/
        return serverPort;

    }

}
