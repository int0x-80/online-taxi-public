package com.wang.service.map.controller;

import com.wang.common.dto.ResponseResult;
import com.wang.common.response.ServiceResponse;
import com.wang.common.response.TerminalResponse;
import com.wang.service.map.service.ServiceMapService;
import com.wang.service.map.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM3:30
 */

@RestController
@RequestMapping("/terminal")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    @PostMapping("/add")
    public ResponseResult<TerminalResponse> add(@RequestParam String name,@RequestParam String desc) {
        ResponseResult<TerminalResponse> add = terminalService.add(name, desc);
        TerminalResponse data = add.getData();
        return ResponseResult.success(data);
    }

    @PostMapping("/around")
    public ResponseResult<List<TerminalResponse>> around(@RequestParam("center") String center, @RequestParam("radius") String radius) {
        ResponseResult<List<TerminalResponse>> add = terminalService.around(center, radius);
        List<TerminalResponse> data = add.getData();
        return ResponseResult.success(data);
    }
}
