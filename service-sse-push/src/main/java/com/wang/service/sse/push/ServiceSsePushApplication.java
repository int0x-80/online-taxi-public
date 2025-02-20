package com.wang.service.sse.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-18:PM1:09
 */

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceSsePushApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSsePushApplication.class, args);
    }
}
