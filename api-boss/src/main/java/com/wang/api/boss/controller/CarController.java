package com.wang.api.boss.controller;

import com.wang.api.boss.service.CarService;
import com.wang.common.dto.Car;
import com.wang.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-30:PM2:40
 */
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/addCar")
    public ResponseResult addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }
}
