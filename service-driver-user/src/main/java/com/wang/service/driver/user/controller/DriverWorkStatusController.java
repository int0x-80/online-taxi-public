package com.wang.service.driver.user.controller;


import com.wang.common.dto.DriverWorkStatus;
import com.wang.common.dto.ResponseResult;
import com.wang.service.driver.user.service.DriverWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wang Yinuo
 * @since 2025-01-30
 */
@RestController
public class DriverWorkStatusController {

    @Autowired
    private DriverWorkStatusService driverWorkStatusService;

    @PostMapping("/driver-user-work-status")
    public ResponseResult addDriverWorkStatus(@RequestBody DriverWorkStatus driverWorkStatus) {
        return driverWorkStatusService.changeWorkStatus(driverWorkStatus);
    }

}
