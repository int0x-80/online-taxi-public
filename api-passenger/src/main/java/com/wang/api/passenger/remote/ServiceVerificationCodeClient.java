package com.wang.api.passenger.remote;

import com.wang.common.dto.ResponseResult;
import com.wang.common.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: WangYinuo
 * @create: 2025-01-06:PM12:38
 */

@FeignClient(name = "service-verificationcode")
public interface ServiceVerificationCodeClient {

    @RequestMapping(method = RequestMethod.GET, value = "/numberCode/{size}")
    ResponseResult<NumberCodeResponse> numberCode(@PathVariable("size") Integer size);
}
