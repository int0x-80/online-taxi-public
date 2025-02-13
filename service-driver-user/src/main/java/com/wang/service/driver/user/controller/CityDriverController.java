package com.wang.service.driver.user.controller;

import com.wang.common.dto.ResponseResult;
import com.wang.service.driver.user.service.CityDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-10:PM12:51
 */

@RestController
@RequestMapping("/city-driver")
public class CityDriverController {

    @Autowired
    private CityDriverUserService cityDriverUserService;

    @GetMapping("/is-available-driver")
    public ResponseResult<Boolean> isAvailableDriver(String cityCode) {
        return cityDriverUserService.isAvailableDriver(cityCode);
    }

}
