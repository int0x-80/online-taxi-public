package com.wang.service.map.controller;

import com.wang.common.dto.ResponseResult;
import com.wang.service.map.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-24:PM8:53
 */

@RestController
public class DicDistrictController {

    @Autowired
    private DicDistrictService dicDistrictService;

    @GetMapping("/initDicDistrict")
    public ResponseResult initDicDistrict() {
        return dicDistrictService.initDicDistrict("100000");
    }
}
