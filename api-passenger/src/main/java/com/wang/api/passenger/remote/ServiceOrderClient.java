package com.wang.api.passenger.remote;

import com.wang.common.dto.ResponseResult;
import com.wang.common.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-01:PM8:17
 */

@FeignClient("service-order")
public interface ServiceOrderClient {

    @RequestMapping(method = RequestMethod.POST, value ="/order/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest);
}
