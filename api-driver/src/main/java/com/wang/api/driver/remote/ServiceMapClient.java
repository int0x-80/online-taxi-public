package com.wang.api.driver.remote;

import com.wang.common.dto.ResponseResult;
import com.wang.common.request.PointRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM6:59
 */

@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST, value = "/point/upload")
    ResponseResult pointUpload(@RequestBody PointRequest pointRequest);
}
