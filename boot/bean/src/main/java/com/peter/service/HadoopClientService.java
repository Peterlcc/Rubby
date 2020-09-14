package com.peter.service;

import com.peter.component.HadoopClientFallbackFactory;
import com.peter.message.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lcc
 * @date 2020/9/1 上午10:49
 */
@FeignClient(value = "rubby-hadoop",fallbackFactory = HadoopClientFallbackFactory.class)
public interface HadoopClientService {
    @GetMapping("/hdfs/test")
    Result test();

    @GetMapping("/platform/list")
    Result platforms();

    @GetMapping("/yarn/list")
    Result yarns();
}
