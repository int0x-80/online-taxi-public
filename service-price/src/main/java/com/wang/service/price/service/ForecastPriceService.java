package com.wang.service.price.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.common.constant.CommonStatusEnum;
import com.wang.common.dto.ForecastPriceDto;
import com.wang.common.dto.PriceRule;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.DirectionResponse;
import com.wang.common.response.ForecastPriceResponse;
import com.wang.common.utils.BigDecimalUtil;
import com.wang.service.price.mapper.PriceRuleMapper;
import com.wang.service.price.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-17:PM1:21
 */

@Service
public class ForecastPriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult forecastPrice(String depLongitude,
                                        String depLatitude,
                                        String destLongitude,
                                        String destLatitude,
                                        String cityCode,
                                        String vehicleType) {

        ForecastPriceDto forecastPriceDto = new ForecastPriceDto();
        forecastPriceDto.setDepLongitude(depLongitude);
        forecastPriceDto.setDepLatitude(depLatitude);
        forecastPriceDto.setDestLongitude(destLongitude);
        forecastPriceDto.setDestLatitude(destLatitude);
        forecastPriceDto.setCityCode(cityCode);
        forecastPriceDto.setVehicleType(vehicleType);

        ResponseResult<DirectionResponse> directionResponseResponseResult = serviceMapClient.driving(forecastPriceDto);
        Double distance = directionResponseResponseResult.getData().getDistance();
        Double duration = directionResponseResponseResult.getData().getDuration();


        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);

        if (priceRules.isEmpty()) {
            return ResponseResult.fail(CommonStatusEnum.FARE_NOT_EXIST.getCode(), CommonStatusEnum.FARE_NOT_EXIST.getValue());
        }
        PriceRule priceRule = priceRules.get(0);
        double price = calculatorPrice(distance, duration, priceRule);

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        forecastPriceResponse.setCityCode(cityCode);
        forecastPriceResponse.setVehicleType(vehicleType);
        forecastPriceResponse.setFareType(priceRule.getFareType());
        forecastPriceResponse.setFareVersion(priceRule.getFareVersion());

        return ResponseResult.success(forecastPriceResponse);
    }

    private double calculatorPrice(double distance, double duration, PriceRule priceRule) {
        double price = 0;

        double starFare = priceRule.getStartFare();
        price = BigDecimalUtil.add(price, starFare);

        double distanceKm = BigDecimalUtil.divide(distance, 1000);

        Integer starMile = priceRule.getStartMile();
        double distanceSubDouble = BigDecimalUtil.subtract(distanceKm, starMile);

        double mile = distanceSubDouble > 0 ? distanceSubDouble : 0;

        double unitPricePerMile = priceRule.getUnitPricePerMile();
        double distancePrice = BigDecimalUtil.multiply(mile, unitPricePerMile);
        price = BigDecimalUtil.add(price, distancePrice);

        double timeMinuteDecimal = BigDecimalUtil.divide(duration, 60);

        double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        double timePrice = BigDecimalUtil.multiply(unitPricePerMinute, timeMinuteDecimal);
        price = BigDecimalUtil.add(price, timePrice);

        BigDecimal priceBigDecimal = new BigDecimal(price);
        price = priceBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        return price;
    }

    public ResponseResult<Double> calculatorPrice(Long distance, Long duration, String cityCode, String vehicleType) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);

        if (priceRules.isEmpty()) {
            return ResponseResult.fail(CommonStatusEnum.FARE_NOT_EXIST.getCode(), CommonStatusEnum.FARE_NOT_EXIST.getValue());
        }

        PriceRule priceRule = priceRules.get(0);
        double price = calculatorPrice(distance, duration, priceRule);
        return ResponseResult.success(price);
    }
}
