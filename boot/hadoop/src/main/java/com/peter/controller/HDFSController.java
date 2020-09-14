package com.peter.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.peter.bean.ServiceInfo;
import com.peter.message.Result;
import com.peter.service.HDFSUtil;
import com.peter.service.HdfsService;
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
    private HdfsService hdfsService;
    @Qualifier("hdfsUtilImpl")
    @Autowired
    private HDFSUtil hdfsUtil;

    @GetMapping("test")
    @HystrixCommand(commandKey = "hdfsTest")
    public Result test(){
        Result test = Result.success("hdfs connection test");
        return test;
    }
}
