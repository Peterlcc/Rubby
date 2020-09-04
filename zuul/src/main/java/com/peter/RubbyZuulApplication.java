package com.peter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author lcc
 * @date 2020/9/3 21:31
 */
@EnableZuulProxy
@SpringBootApplication
public class RubbyZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(RubbyZuulApplication.class,args);
    }
}
