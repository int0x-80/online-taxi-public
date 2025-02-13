package com.wang.api.passenger.remote;

import com.wang.common.dto.ForecastPriceDto;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.ForecastPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-22:PM1:16
 */

@FeignClient("service-price")
public interface ServicePriceClient {

    @RequestMapping(method = RequestMethod.POST, value = "/forecast-price")
    public ResponseResult<ForecastPriceResponse> forecastPrice(@RequestBody ForecastPriceDto forecastPriceDto);
}
