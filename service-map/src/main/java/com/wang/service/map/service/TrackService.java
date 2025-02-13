package com.wang.service.map.service;

import com.wang.common.dto.ResponseResult;
import com.wang.service.map.remote.TrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM4:49
 */
@Service
public class TrackService {

    @Autowired
    private TrackClient trackClient;

    public ResponseResult addTrack(String tid) {
        return trackClient.add(tid);
    }
}
