package com.wang.service.map.service;

import com.wang.common.dto.ResponseResult;
import com.wang.common.response.ServiceResponse;
import com.wang.common.response.TerminalResponse;
import com.wang.service.map.remote.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM3:00
 */
@Service
public class ServiceMapService {

    @Autowired
    private ServiceClient serviceClient;

    public ResponseResult<ServiceResponse>  add(String name) {
        System.out.println("name:" + name);
        return serviceClient.add(name);
    }
}
