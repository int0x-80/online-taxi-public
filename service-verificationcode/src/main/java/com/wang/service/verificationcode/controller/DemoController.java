package com.wang.service.verificationcode.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: WangYinuo
 * @create: 2025-01-03:PM1:10
 */

@RestController
public class DemoController {

    @GetMapping("/demo")
    public String demo() {
        return "demo";
    }
}
