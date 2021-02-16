package com.hsg.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class OrderOpenfeignHystrixMain80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderOpenfeignHystrixMain80.class, args);
    }
}
