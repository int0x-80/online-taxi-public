package com.wang.api.driver.service;

import com.wang.api.driver.remote.ServiceOrderClient;
import com.wang.common.dto.ResponseResult;
import com.wang.common.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-25:PM1:02
 */

@Service
public class DriverOrderService {

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    public ResponseResult toPickUpPassenger(OrderRequest orderRequest) {
        return serviceOrderClient.toPickUpPassenger(orderRequest);
    }

    public ResponseResult arrivedDeparture(OrderRequest orderRequest) {
        return serviceOrderClient.arrivedDeparture(orderRequest);
    }

    public ResponseResult pickUpPassenger(OrderRequest orderRequest) {
        return serviceOrderClient.pickUpPassenger(orderRequest);
    }

    public ResponseResult arrivedDestination(OrderRequest orderRequest) {
        return serviceOrderClient.arrivedDestination(orderRequest);
    }
}
