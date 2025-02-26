package com.wang.api.driver.controller;

import com.wang.api.driver.service.DriverOrderService;
import com.wang.common.dto.ResponseResult;
import com.wang.common.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-25:PM12:58
 */

@RestController("/order")
public class OrderController {

    @Autowired
    private DriverOrderService driverOrderService;

    @PostMapping("/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return driverOrderService.toPickUpPassenger(orderRequest);
    }

    @PostMapping("/arrived-departure")
    public ResponseResult arrivedDeparture(@RequestBody OrderRequest orderRequest) {
        return driverOrderService.arrivedDeparture(orderRequest);
    }

    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return driverOrderService.pickUpPassenger(orderRequest);
    }

    @PostMapping("/arrived-destination")
    public ResponseResult arrivedDestination(@RequestBody OrderRequest orderRequest) {
        return driverOrderService.arrivedDestination(orderRequest);
    }
}
