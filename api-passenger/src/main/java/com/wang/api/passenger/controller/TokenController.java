package com.wang.api.passenger.controller;

import com.wang.api.passenger.service.TokenService;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-15:PM1:26
 */
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse) {

        String refreshToken = tokenResponse.getRefreshToken();
        System.out.println(refreshToken);
        return tokenService.refreshToken(refreshToken);
    }
}
