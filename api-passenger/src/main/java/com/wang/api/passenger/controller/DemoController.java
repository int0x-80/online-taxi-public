package com.wang.api.passenger.controller;

import com.wang.api.passenger.remote.ServiceOrderClient;
import com.wang.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: WangYinuo
 * @create: 2025-01-02:PM1:30
 */


@RestController
public class DemoController {


    @Autowired
    private ServiceOrderClient serviceOrderClient;

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


    @GetMapping("/test/{orderId}")
    public String test(@PathVariable("orderId") long orderId) {
        serviceOrderClient.test(orderId);
        return "test";
    }
}
