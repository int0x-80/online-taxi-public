package com.wang.service.driver.user.service;

import com.wang.common.dto.ResponseResult;
import com.wang.service.driver.user.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-10:PM12:48
 */

@Service

public class CityDriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    public ResponseResult<Boolean> isAvailableDriver(String cityCode) {
        Integer i = driverUserMapper.selectDriverUserByCityCode(cityCode);
        System.out.println(i);
        if (i > 0) {
            return ResponseResult.success(true);
        }

        return ResponseResult.success(false);
    }
}
