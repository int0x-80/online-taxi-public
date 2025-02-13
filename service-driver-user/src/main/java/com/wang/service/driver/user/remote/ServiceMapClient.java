package com.wang.service.driver.user.remote;

import com.wang.common.dto.ResponseResult;
import com.wang.common.response.TerminalResponse;
import com.wang.common.response.TrackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM3:47
 */

@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST, value ="/terminal/add")
    public ResponseResult<TerminalResponse> addTerminal(@RequestParam("name") String name, @RequestParam("desc") String desc);

    @RequestMapping(method = RequestMethod.POST, value ="/track/add")
    public ResponseResult<TrackResponse> addTrack(@RequestParam String tid);
}
