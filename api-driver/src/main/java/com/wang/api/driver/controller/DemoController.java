package com.wang.api.driver.controller;

import com.wang.common.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: WangYinuo
 * @create: 2025-01-02:PM1:30
 */


@RestController
public class DemoController {

    @GetMapping("/demo")
    public String demo() {
        return "demo";
    }

    @GetMapping("/authTest")
    public ResponseResult authTest() {
            return ResponseResult.success("AuthTest");
    }

    @GetMapping("/noAuthTest")
    public ResponseResult noAuthTest() {
        return ResponseResult.success("noAuthTest");
    }
}
