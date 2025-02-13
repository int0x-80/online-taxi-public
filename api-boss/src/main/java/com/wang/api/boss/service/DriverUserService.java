package com.wang.api.boss.service;

import com.wang.api.boss.remote.ServiceDriverUserClient;
import com.wang.common.dto.DriverUser;
import com.wang.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-25:PM10:25
 */

@Service
public class DriverUserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addDriverUser(DriverUser driverUser) {
        return serviceDriverUserClient.addUsers(driverUser);
    }

    public ResponseResult updateUser(DriverUser driverUser) {
        return serviceDriverUserClient.updateUser(driverUser);
    }

}
