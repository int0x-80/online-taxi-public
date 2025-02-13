package com.wang.service.verificationcode.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wang.common.dto.ResponseResult;
import com.wang.service.verificationcode.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Soundbank;

/**
 * @author: WangYinuo
 * @create: 2025-01-03:PM1:21
 */

@RestController
public class NumberController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") Integer size) {
        System.out.println(size);

        // 生成验证码
        int code = 123321;
        NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
        numberCodeResponse.setNumberCode(String.valueOf(code));

        return ResponseResult.success(numberCodeResponse);
    }
}
