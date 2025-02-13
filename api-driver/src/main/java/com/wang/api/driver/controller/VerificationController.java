package com.wang.api.driver.controller;

import com.wang.api.driver.service.VerificationService;
import com.wang.common.dto.ResponseResult;
import com.wang.common.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: WangYinuo
 * @create: 2025-01-02:PM1:37
 */

@RestController
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        System.out.println(verificationCodeDTO);
        return verificationService.verificationCode(verificationCodeDTO);
    }

    @PostMapping("/verification-code-check")
    public ResponseResult verificationCodeCheck(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String driverPhone = verificationCodeDTO.getDriverPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        return checkCode(driverPhone, verificationCode);
    }

    public ResponseResult checkCode(String phone, String verificationCode) {
        return verificationService.checkCode(phone, verificationCode);
    }
}
