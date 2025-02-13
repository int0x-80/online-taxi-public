package com.wang.api.driver.controller;

import com.wang.api.driver.service.UserService;
import com.wang.common.dto.DriverUser;
import com.wang.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-27:PM1:56
 */

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/updateUser")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        return userService.updateUser(driverUser);
    }
}
