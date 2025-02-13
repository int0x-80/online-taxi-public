package com.wang.api.driver.controller;

import com.wang.common.dto.Car;
import com.wang.common.dto.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM6:23
 */

@RestController
public class CarController {

    @PostMapping("/addCar")
    public ResponseResult addCar(@RequestBody Car car) {
        return null;
    }
}
