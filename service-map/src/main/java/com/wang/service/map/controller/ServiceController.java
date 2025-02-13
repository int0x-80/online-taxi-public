package com.wang.service.map.controller;

import com.wang.common.dto.ResponseResult;
import com.wang.service.map.service.ServiceMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM2:59
 */

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceMapService serviceMapService;

    @PostMapping("/add")
    public ResponseResult add(String name) {
        return serviceMapService.add(name);
    }
}
