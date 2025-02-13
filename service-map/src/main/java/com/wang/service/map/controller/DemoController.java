package com.wang.service.map.controller;

import com.wang.common.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-17:PM1:34
 */

@RestController
public class DemoController {

    @GetMapping("/demo")
    public ResponseResult demo() {
        return ResponseResult.success("demo");
    }
}
