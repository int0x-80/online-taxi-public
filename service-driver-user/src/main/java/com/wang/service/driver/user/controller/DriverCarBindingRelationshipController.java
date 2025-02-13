package com.wang.service.driver.user.controller;


import com.wang.common.constant.DriverCarConstant;
import com.wang.common.dto.DriverCarBindingRelationship;
import com.wang.common.dto.ResponseResult;
import com.wang.service.driver.user.service.DriverCarBindingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wang Yinuo
 * @since 2025-01-29
 */
@RestController
@RequestMapping("/driver-car-binding-relationship")
public class DriverCarBindingRelationshipController {

    @Autowired
    private DriverCarBindingRelationshipService driverCarBindingRelationshipService;

    @PostMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {
        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setState(DriverCarConstant.DRIVER_CAR_BIND);
        driverCarBindingRelationship.setBindingTime(now);
        driverCarBindingRelationship.setUnBindingTime(null);
        return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);
    }

    @PostMapping("/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {
        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        driverCarBindingRelationship.setState(DriverCarConstant.DRIVER_CAR_UNBIND);
        return driverCarBindingRelationshipService.unbind(driverCarBindingRelationship);
    }
}
