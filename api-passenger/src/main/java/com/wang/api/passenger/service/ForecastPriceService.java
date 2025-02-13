package com.wang.api.passenger.service;

import com.wang.api.passenger.remote.ServicePriceClient;
import com.wang.common.dto.ForecastPriceDto;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.ForecastPriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-17:PM1:02
 */

@Service
public class ForecastPriceService {

    @Autowired
    private ServicePriceClient servicePriceClient;

    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude, String cityCode, String vehicleType) {
        ForecastPriceDto forecastPriceDto = new ForecastPriceDto();
        forecastPriceDto.setDepLongitude(depLongitude);
        forecastPriceDto.setDepLatitude(depLatitude);
        forecastPriceDto.setDestLongitude(destLongitude);
        forecastPriceDto.setDestLatitude(destLatitude);
        forecastPriceDto.setCityCode(cityCode);
        forecastPriceDto.setVehicleType(vehicleType);

        ResponseResult<ForecastPriceResponse> responseResult = servicePriceClient.forecastPrice(forecastPriceDto);


        return responseResult;
    }
}
