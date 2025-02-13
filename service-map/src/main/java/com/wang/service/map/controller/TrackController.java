package com.wang.service.map.controller;

import com.wang.common.dto.ResponseResult;
import com.wang.service.map.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM4:48
 */

@RestController
@RequestMapping("/track")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @PostMapping("/add")
    public ResponseResult addTrack(@RequestParam String tid) {
        ResponseResult responseResult = trackService.addTrack(tid);
        return ResponseResult.success(responseResult.getData());
    }
}
