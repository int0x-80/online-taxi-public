package com.wang.service.driver.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-25:AM11:19
 */

@EnableFeignClients
@SpringBootApplication
@MapperScan("com.wang.service.driver.user.mapper")
public class ServiceDriverUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDriverUserApplication.class, args);
    }
}
