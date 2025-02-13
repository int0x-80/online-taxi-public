package com.wang.service.order.controller;

import com.wang.common.dto.ResponseResult;
import com.wang.common.request.OrderRequest;
import com.wang.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import com.wang.common.constant.HeaderConstant;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-01:PM8:14
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        // String deviceId = request.getHeader(HeaderConstant.DEVICE_ID);
        // orderRequest.setDeviceId(deviceId);
        return orderService.add(orderRequest);
    }
}
