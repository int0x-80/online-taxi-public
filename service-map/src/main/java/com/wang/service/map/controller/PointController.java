package com.wang.service.map.controller;

import com.wang.common.dto.ResponseResult;
import com.wang.common.request.PointRequest;
import com.wang.service.map.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM5:35
 */

@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;
    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest) {
        return pointService.upload(pointRequest);
    }
}
