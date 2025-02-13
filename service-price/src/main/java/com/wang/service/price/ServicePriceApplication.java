package com.wang.service.price;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-17:PM1:19
 */

@MapperScan("com.wang.service.price.mapper")
@EnableFeignClients
@SpringBootApplication
public class ServicePriceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicePriceApplication.class, args);
    }
}
