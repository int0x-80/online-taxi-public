package com.wang.api.driver.service;

import com.wang.api.driver.remote.ServiceDriverUserClient;
import com.wang.common.dto.DriverUser;
import com.wang.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-27:PM1:56
 */
@Service
public class UserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult updateUser(DriverUser driverUser) {
        return serviceDriverUserClient.updateUser(driverUser);
    }
}
