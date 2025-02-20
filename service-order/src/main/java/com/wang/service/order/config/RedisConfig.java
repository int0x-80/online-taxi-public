package com.wang.service.order.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-18:PM12:43
 */
@Component
public class RedisConfig {

    private String protocol = "redis://";

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(protocol + host + ":" + port).setDatabase(0);
        return Redisson.create(config);
    }
}
