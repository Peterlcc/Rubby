package com.peter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lcc
 * @date 2020/8/24 下午4:56
 */
@MapperScan(basePackages = "com.peter.mapper")
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.peter"})
@SpringBootApplication
@EnableDiscoveryClient
public class RubbyWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(RubbyWebApplication.class,args);
    }
}
