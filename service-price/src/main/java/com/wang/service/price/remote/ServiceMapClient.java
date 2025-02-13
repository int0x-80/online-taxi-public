package com.wang.service.price.remote;

import com.wang.common.dto.ForecastPriceDto;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-21:PM12:50
 */

@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.GET, value = "/direction/driving")
    public ResponseResult<DirectionResponse> driving(@RequestBody ForecastPriceDto forecastPriceDto);
}
