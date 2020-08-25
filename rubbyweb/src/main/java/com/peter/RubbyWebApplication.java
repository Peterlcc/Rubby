package com.peter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lcc
 * @date 2020/8/24 下午4:56
 */
@MapperScan(basePackages = "com.peter.mapper")
@SpringBootApplication
public class RubbyWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(RubbyWebApplication.class,args);
    }
}
