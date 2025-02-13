package com.wang.service.order.remote;

import com.wang.common.dto.ResponseResult;
import com.wang.common.response.TerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-10:PM1:38
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST, value ="/terminal/around")
    public ResponseResult<List<TerminalResponse>> around(@RequestParam("center") String center, @RequestParam("radius") String radius);
}
