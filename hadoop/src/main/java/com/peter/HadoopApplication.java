package com.peter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author lcc
 * @date 2020/8/24 下午5:35
 */
@MapperScan(basePackages = "com.peter.mapper")
@EnableEurekaClient
@EnableDiscoveryClient
//@EnableCircuitBreaker
@SpringBootApplication
public class HadoopApplication {
    public static void main(String[] args) {
        SpringApplication.run(HadoopApplication.class,args);
    }
}
