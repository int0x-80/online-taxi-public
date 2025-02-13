package com.wang.api.passenger.remote;

import com.wang.common.dto.ResponseResult;
import com.wang.common.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: WangYinuo
 * @create: 2025-01-09:PM1:31
 */

@FeignClient(name = "service-passenger-user")
public interface ServicePassengerUserClient {

    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public ResponseResult user(@RequestBody VerificationCodeDTO verificationCodeDTO);

    @RequestMapping(method = RequestMethod.GET, value = "/getUser")
    public ResponseResult getUser(@PathVariable("phone") String phone);
}
