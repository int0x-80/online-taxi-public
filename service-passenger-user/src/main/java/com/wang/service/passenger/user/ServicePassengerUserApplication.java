package com.wang.service.passenger.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: WangYinuo
 * @create: 2025-01-07:PM1:18
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.wang.service.passenger.user.mapper")
public class ServicePassengerUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicePassengerUserApplication.class, args);
    }
}
