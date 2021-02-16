package com.hsg.springcloud.lib;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {

    //获取 ServiceInstance 对象
    ServiceInstance instance(List<ServiceInstance> serviceInstances);

}
