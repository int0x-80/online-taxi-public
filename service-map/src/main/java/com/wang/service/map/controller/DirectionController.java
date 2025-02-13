package com.wang.service.map.controller;

import com.wang.common.dto.ForecastPriceDto;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.DirectionResponse;
import com.wang.service.map.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-17:PM1:37
 */
@RestController
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    @PostMapping("/direction/driving")
    public ResponseResult<DirectionResponse> driving(@RequestBody ForecastPriceDto forecastPriceDto) {
        String depLongitude = forecastPriceDto.getDepLongitude();
        String depLatitude = forecastPriceDto.getDepLatitude();
        String destLongitude = forecastPriceDto.getDestLongitude();
        String destLatitude = forecastPriceDto.getDestLatitude();
        System.out.println("forecastPriceDto:" + forecastPriceDto);
        return directionService.driving(depLongitude, depLatitude, destLongitude, destLatitude);

    }
}
