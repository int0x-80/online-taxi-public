package com.wang.api.passenger.remote;

import com.wang.common.dto.ResponseResult;
import com.wang.common.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-01:PM8:17
 */

@FeignClient("service-order")
public interface ServiceOrderClient {

    @RequestMapping(method = RequestMethod.POST, value ="/order/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest);

    @RequestMapping(method = RequestMethod.GET, value ="/test-real-time-order/{orderId}")
    public String test(@PathVariable("orderId") long orderId);
}
