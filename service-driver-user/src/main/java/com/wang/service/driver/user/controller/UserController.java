package com.wang.service.driver.user.controller;

import com.wang.common.constant.DriverCarConstant;
import com.wang.common.dto.DriverUser;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.DriverUserExistResponse;
import com.wang.common.response.OrderDriverResponse;
import com.wang.service.driver.user.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-25:PM9:59
 */

@RestController
public class UserController {

    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/addUser")
    public ResponseResult addUsers(@RequestBody DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        return driverUserService.addUser(driverUser);
    }

    @PutMapping("/updateUser")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        return driverUserService.updateUser(driverUser);
    }

    @GetMapping("/check_driver/{driverPhone}")
    public ResponseResult getUser(@PathVariable("driverPhone") String driverPhone) {
        ResponseResult<DriverUser> userByPhone = driverUserService.getUserByPhone(driverPhone);
        DriverUser driverUser1 = userByPhone.getData();
        DriverUserExistResponse driverUserExistResponse = new DriverUserExistResponse();
        if (driverUser1 == null) {
            driverUserExistResponse.setIsExists(DriverCarConstant.DRIVER_NOT_EXIST);
            driverUserExistResponse.setDriverPhone(null);
        } else {
            driverUserExistResponse.setIsExists(DriverCarConstant.DRIVER_EXIST);
            driverUserExistResponse.setDriverPhone(driverUser1.getDriverPhone());
        }

        return ResponseResult.success(driverUserExistResponse);
    }


    @GetMapping("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId) {
        return driverUserService.getAvailableDriver(carId);
    }

}
