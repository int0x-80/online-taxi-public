package com.wang.service.driver.user.controller;

import com.wang.common.dto.Car;
import com.wang.common.dto.ResponseResult;
import com.wang.service.driver.user.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-27:PM2:50
 */

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/addCar")
    public ResponseResult addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @PostMapping("/getCar")
    public ResponseResult<Car> getCar(Long carId) {
        return carService.getCar(carId);
    }

}
