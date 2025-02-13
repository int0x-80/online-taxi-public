package com.wang.service.verificationcode;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: WangYinuo
 * @create: 2025-01-03:PM1:09
 */

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceVerificationCodeApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ServiceVerificationCodeApplication.class, args);
    }
}
