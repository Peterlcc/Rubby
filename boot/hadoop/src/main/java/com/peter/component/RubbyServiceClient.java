package com.peter.component;

import com.peter.bean.ServiceInfo;
import com.peter.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lcc
 * @date 2020/9/14 下午5:41
 */
@Component
public class RubbyServiceClient {
    //服务发现
    @Autowired
    private DiscoveryClient discoveryClient;

    public Map<String, List<ServiceInfo>> services(){
        List<String> services = discoveryClient.getServices();
        System.out.println(services);
        Map<String, List<ServiceInfo>> serviceInfoMap=new HashMap<>();
        for (String service : services) {
            serviceInfoMap.put(service,new ArrayList<>());
        }
        for (String service : services) {
            List<ServiceInfo> serviceInfoList = serviceInfoMap.get(service);
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                serviceInfoList.add(new ServiceInfo()
                        .setName(service)
                        .setHost(instance.getHost())
                        .setPort(instance.getPort())
                        .setUri(instance.getUri().toString()));
            }
        }
        return serviceInfoMap;
    }
}
