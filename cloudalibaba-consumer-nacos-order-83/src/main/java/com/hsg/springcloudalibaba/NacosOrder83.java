package com.hsg.springcloudalibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosOrder83 {

    public static void main(String[] args) {
        SpringApplication.run(NacosOrder83.class, args);
    }
}
