package com.wang.service.price.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.common.dto.PriceRule;
import com.wang.common.dto.ResponseResult;
import com.wang.service.price.mapper.PriceRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wang.common.constant.CommonStatusEnum.*;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-02:PM3:01
 */

@Service
public class PriceRuleService {

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult add(PriceRule priceRule) {
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode + "$" +vehicleType;
        priceRule.setFareType(fareType);


        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        Integer version = 0;

        if (priceRules.size() > 0) {
            return ResponseResult.fail(PRICE_IS_EXIST.getCode(), PRICE_IS_EXIST.getValue());
        }

        priceRule.setFareVersion(version + 1);

        priceRuleMapper.insert(priceRule);
        return ResponseResult.success();
    }

    public ResponseResult edit(PriceRule priceRule) {
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode + vehicleType;
        priceRule.setFareType(fareType);

        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        Integer version = 0;

        if (priceRules.size() > 0) {
            PriceRule lastPriceRule = priceRules.get(0);
            Double unitPricePerMile = lastPriceRule.getUnitPricePerMile();
            Double unitPricePerMinute = lastPriceRule.getUnitPricePerMinute();
            Integer starMile = lastPriceRule.getStartMile();
            Double starFare = lastPriceRule.getStartFare();

            if (unitPricePerMile.doubleValue() == priceRule.getUnitPricePerMile().doubleValue()
                    && unitPricePerMinute.doubleValue() == priceRule.getUnitPricePerMinute().doubleValue()
                    && starMile.intValue() == priceRule.getStartMile().intValue()
                    && starFare.doubleValue() == priceRule.getStartFare().doubleValue()) {
                return ResponseResult.fail(PRICE_NOT_CHANGED.getCode(), PRICE_NOT_CHANGED.getValue());
            }

            version = priceRules.get(0).getFareVersion();
        }

        priceRule.setFareVersion(version + 1);

        priceRuleMapper.insert(priceRule);
        return ResponseResult.success();
    }

    public ResponseResult<PriceRule> getNewest(String fareType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("fare_type", fareType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> list = priceRuleMapper.selectList(queryWrapper);

        if (list.isEmpty()) {
            return ResponseResult.fail(FARE_NOT_EXIST.getCode(), FARE_NOT_EXIST.getValue());
        }
        return ResponseResult.success(list.get(0));
    }

    public ResponseResult isNewest(String fareType, Integer fareVersion) {
        ResponseResult<PriceRule> newest = getNewest(fareType);
        if (newest.getCode().equals(FARE_NOT_EXIST.getCode())) {
            return ResponseResult.fail(FARE_NOT_EXIST.getCode(), FARE_NOT_EXIST.getValue());
        }

        PriceRule priceRule = newest.getData();
        if (priceRule.getFareVersion().intValue() != fareVersion.intValue()) {
            return ResponseResult.fail(FARE_NOT_NEWEST.getCode(), FARE_NOT_NEWEST.getValue());
        } else {
            return ResponseResult.success(true);
        }
    }

    public ResponseResult<Boolean> isExist(PriceRule priceRule) {
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();

        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if (priceRules.isEmpty()) {
            return ResponseResult.success(false);
        }
        return ResponseResult.success(true);
    }
}
