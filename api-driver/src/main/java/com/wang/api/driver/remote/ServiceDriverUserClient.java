package com.wang.api.driver.remote;

import com.wang.common.dto.Car;
import com.wang.common.dto.DriverUser;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.DriverUserExistResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-27:PM1:57
 */

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/updateUser")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);

    @RequestMapping(method = RequestMethod.GET, value = "/check_driver/{phone}")
    public ResponseResult<DriverUserExistResponse> checkDriver(@PathVariable("phone") String phone);

    @RequestMapping(method = RequestMethod.POST,value = "/getCar")
    public ResponseResult<Car> getCar(@RequestParam Long carId);
}
