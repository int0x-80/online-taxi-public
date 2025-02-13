package com.wang.service.map.service;

import com.wang.common.dto.ResponseResult;
import com.wang.common.request.PointRequest;
import com.wang.service.map.remote.PointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM5:38
 */

@Service
public class PointService {

    @Autowired
    private PointClient pointClient;

    public ResponseResult upload(PointRequest pointRequest) {
        return pointClient.upload(pointRequest);
    }
}
