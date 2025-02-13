package com.wang.service.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-01:PM8:09
 */

@RestController
public class DemoController {

    @GetMapping("/demo")
    public String demo() {
        return "demo";
    }
}
