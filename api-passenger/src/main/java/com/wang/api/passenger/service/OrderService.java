package com.wang.api.passenger.service;

import com.wang.api.passenger.remote.ServiceOrderClient;
import com.wang.common.dto.ResponseResult;
import com.wang.common.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-01:PM7:46
 */
@Service
public class OrderService {

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    public ResponseResult addOrder(OrderRequest orderRequest) {


        return serviceOrderClient.addOrder(orderRequest);
    }
}
