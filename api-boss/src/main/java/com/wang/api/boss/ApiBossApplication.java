package com.wang.api.boss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-25:PM10:18
 */

@EnableFeignClients
@SpringBootApplication
public class ApiBossApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiBossApplication.class, args);
    }
}
