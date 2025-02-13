package com.wang.service.map.service;

import com.wang.common.dto.ResponseResult;
import com.wang.common.response.TerminalResponse;
import com.wang.service.map.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM3:31
 */
@Service
public class TerminalService {

    @Autowired
    private TerminalClient terminalClient;

    public ResponseResult<TerminalResponse> add(String name, String desc) {
        return terminalClient.add(name, desc);
    }

    public ResponseResult<List<TerminalResponse>> around(String center, String radius) {
        return terminalClient.around(center, radius);
    }
}
