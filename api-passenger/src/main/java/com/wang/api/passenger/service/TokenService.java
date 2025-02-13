package com.wang.api.passenger.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.wang.common.constant.CommonStatusEnum;
import com.wang.common.constant.IdentityConstant;
import com.wang.common.constant.TokenConstants;
import com.wang.common.dto.ResponseResult;
import com.wang.common.dto.TokenResult;
import com.wang.common.response.TokenResponse;
import com.wang.common.utils.JwtUtils;
import com.wang.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-15:PM1:27
 */
@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult refreshToken(String refreshToken) {
        TokenResult tokenResult = JwtUtils.checkToken(refreshToken);
        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        String passengerPhone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();

        String refreshTokenKey = RedisUtils.generateTokenKey(passengerPhone, identity, TokenConstants.REFRESH_TOKEN);
        String refreshTokenRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);

        if (StringUtils.isBlank(refreshTokenRedis) || (!refreshTokenRedis.trim().equals(refreshToken.trim()))) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        String accessToken = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN);
        String refreshTokenNew = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN);

        String accessTokenKey = RedisUtils.generateTokenKey(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);

        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshTokenNew, 31, TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
