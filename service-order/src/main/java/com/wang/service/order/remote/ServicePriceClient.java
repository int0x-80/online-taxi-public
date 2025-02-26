package com.wang.service.order.remote;

import com.wang.common.dto.PriceRule;
import com.wang.common.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-06:PM12:40
 */

@FeignClient("service-price")
public interface ServicePriceClient {

    @RequestMapping(method = RequestMethod.GET, value ="/price-rule/is-newest")
    public ResponseResult<Boolean> isPriceNewest(@RequestParam String fareType, @RequestParam Integer fareVersion);

    @RequestMapping(method = RequestMethod.GET, value ="/price-rule/is-exist")
    public ResponseResult<Boolean> isPriceExist(@RequestBody PriceRule priceRule);

    @RequestMapping(method = RequestMethod.POST, value ="/calculate-price")
    public ResponseResult<Double> calculatePrice(@RequestParam Long distance, @RequestParam Long duration, @RequestParam String cityCode, @RequestParam String vehicleType);
}
