package com.wang.service.map.service;

import com.wang.common.dto.ResponseResult;
import com.wang.common.response.DirectionResponse;
import com.wang.service.map.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-17:PM1:39
 */
@Service
public class DirectionService {

    @Autowired
    private MapDirectionClient mapDirectionClient;

    public ResponseResult<DirectionResponse> driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude)  {
        DirectionResponse direction = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);
        return ResponseResult.success(direction);
    }
}
