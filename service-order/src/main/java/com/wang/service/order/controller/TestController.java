package com.wang.service.order.controller;

import com.wang.common.dto.OrderInfo;
import com.wang.service.order.mapper.OrderMapper;
import com.wang.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-14:PM1:00
 */

@RestController
public class TestController {

    @Autowired
    private OrderMapper orderMapper;


    @Autowired
    private OrderService orderService;

    @GetMapping("/test-real-time-order/{orderId}")
    public String test(@PathVariable("orderId") long orderId) {
        System.out.println(orderId);
        OrderInfo orderInfo = orderMapper.selectById(orderId);
        orderService.dispatchRealTimeOrder(orderInfo);
        return "test";
    }
}
