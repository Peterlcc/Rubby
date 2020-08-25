package com.peter.controller;

import com.peter.ServiceInfo;
import com.peter.message.Result;
import com.peter.service.HDFSService;
import com.peter.service.HDFSUtil;
import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lcc
 * @date 2020/8/25 下午2:20
 */
@Controller
@RequestMapping("hdfs")
@ResponseBody
public class HDFSController {
    private static final Log LOG= LogFactory.getLog(HDFSController.class);

    @Autowired
    private HDFSService hdfsService;
    @Qualifier("hdfsUtilImpl")
    @Autowired
    private HDFSUtil hdfsUtil;

    @GetMapping("test")
    public Result test(){
        Result test = Result.success("hdfs connection test");
        return test;
    }


    //服务发现
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("services")
    public Result services(){
        List<String> services = discoveryClient.getServices();
        System.out.println(services);
        Map<String, List<ServiceInfo>> serviceInfoMap=new HashMap<>();
        for (String service : services) {
            serviceInfoMap.put(service,new ArrayList<ServiceInfo>());
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
        return Result.success(serviceInfoMap);
    }

}
