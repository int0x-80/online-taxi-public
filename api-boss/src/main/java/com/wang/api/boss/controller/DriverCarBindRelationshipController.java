package com.wang.api.boss.controller;

import com.wang.api.boss.service.DriverCarBindRelationshipService;
import com.wang.common.dto.DriverCarBindingRelationship;
import com.wang.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-30:PM3:09
 */

@RestController
@RequestMapping("/driver-car-binding-relationship")
public class DriverCarBindRelationshipController {

    @Autowired
    private DriverCarBindRelationshipService driverCarBindRelationshipService;

    @PostMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {
        return driverCarBindRelationshipService.bind(driverCarBindingRelationship);
    }

    @PostMapping("/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {
        return driverCarBindRelationshipService.unbind(driverCarBindingRelationship);
    }
}
