package com.wang.service.passenger.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: WangYinuo
 * @create: 2025-01-07:PM1:19
 */

@RestController
public class DemoController {

    @GetMapping("/demo")
    public String demo() {
        return "demo";
    }
}
