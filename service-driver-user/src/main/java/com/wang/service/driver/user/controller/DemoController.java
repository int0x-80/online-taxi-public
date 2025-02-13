package com.wang.service.driver.user.controller;

import com.wang.common.dto.ResponseResult;
import com.wang.service.driver.user.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-25:AM11:22
 */

@RestController
public class DemoController {

    @Autowired
    private DriverUserService driverUserService;

    @GetMapping("/demo")
    public String demo() {
        return "demo";
    }

    @GetMapping("/db")
    public ResponseResult db() {
        return driverUserService.getDriverUserByDriverPhone();
    }


}
