package com.wang.api.passenger.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.wang.api.passenger.remote.ServicePassengerUserClient;
import com.wang.api.passenger.remote.ServiceVerificationCodeClient;
import com.wang.common.constant.CommonStatusEnum;
import com.wang.common.constant.IdentityConstant;
import com.wang.common.constant.TokenConstants;
import com.wang.common.dto.ResponseResult;
import com.wang.common.request.VerificationCodeDTO;
import com.wang.common.response.NumberCodeResponse;
import com.wang.common.response.TokenResponse;
import com.wang.common.utils.JwtUtils;
import com.wang.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: WangYinuo
 * @create: 2025-01-02:PM1:42
 */

@Service
public class VerificationService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult verificationCode(String passengerPhone) {
        // 短信服务
        ResponseResult<NumberCodeResponse> responseResult = serviceVerificationCodeClient.numberCode(6);
        int numberCode = responseResult.getData().getNumberCode();
        // redis
        String key = RedisUtils.generateKeyByPhone(passengerPhone, IdentityConstant.PASSENGER_IDENTITY);
        stringRedisTemplate.opsForValue().set(key, String.valueOf(numberCode), 60 * 2);
        // 调用三方短信服务
        return ResponseResult.success();
    }

    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        String key = RedisUtils.generateKeyByPhone(passengerPhone, IdentityConstant.PASSENGER_IDENTITY);
        String codeRedis = stringRedisTemplate.opsForValue().get(key);

        if (StringUtils.isBlank(codeRedis)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue(), null);
        }

        if (!verificationCode.trim().equals(codeRedis.trim())) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue(), null);
        }
        // 判断用户是否存在
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.user(verificationCodeDTO);

        String accessToken = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN);
        String refreshToken = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN);

        String accessTokenKey = RedisUtils.generateTokenKey(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);

        String refreshTokenKey = RedisUtils.generateTokenKey(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
