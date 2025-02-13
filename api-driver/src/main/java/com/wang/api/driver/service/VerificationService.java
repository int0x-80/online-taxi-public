package com.wang.api.driver.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.wang.api.driver.remote.ServiceDriverUserClient;
import com.wang.api.driver.remote.ServiceVerificationCodeClient;
import com.wang.common.constant.CommonStatusEnum;
import com.wang.common.constant.DriverCarConstant;
import com.wang.common.constant.IdentityConstant;
import com.wang.common.constant.TokenConstants;
import com.wang.common.dto.ResponseResult;
import com.wang.common.request.VerificationCodeDTO;
import com.wang.common.response.DriverUserExistResponse;
import com.wang.common.response.NumberCodeResponse;
import com.wang.common.response.TokenResponse;
import com.wang.common.utils.JwtUtils;
import com.wang.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-30:PM3:38
 */

@Service
public class VerificationService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult verificationCode(VerificationCodeDTO verificationCodeDTO) {

        ResponseResult<DriverUserExistResponse> driverUserExistResponseResponseResult = serviceDriverUserClient.checkDriver(verificationCodeDTO.getDriverPhone());
        DriverUserExistResponse data = driverUserExistResponseResponseResult.getData();
        if (data.getIsExists() == DriverCarConstant.DRIVER_NOT_EXIST) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXIST.getCode(), CommonStatusEnum.DRIVER_NOT_EXIST.getValue());
        }

        ResponseResult<NumberCodeResponse> responseResult = serviceVerificationCodeClient.numberCode(6);
        NumberCodeResponse numberCodeResponse = responseResult.getData();
        int numberCode = numberCodeResponse.getNumberCode();
        System.out.println("验证码：" + numberCode);

        String key = RedisUtils.generateKeyByPhone(verificationCodeDTO.getDriverPhone(), IdentityConstant.DRIVER_IDENTITY);
        stringRedisTemplate.opsForValue().set(key, numberCode + "",2,  TimeUnit.MINUTES);

        return ResponseResult.success();
    }

    public ResponseResult checkCode(String phone, String verificationCode) {
        String key = RedisUtils.generateKeyByPhone(phone, IdentityConstant.DRIVER_IDENTITY);
        String codeRedis = stringRedisTemplate.opsForValue().get(key);

        if (StringUtils.isBlank(codeRedis)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue(), null);
        }

        if (!verificationCode.trim().equals(codeRedis.trim())) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue(), null);
        }
        // 判断用户是否存在
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setDriverPhone(phone);
        serviceDriverUserClient.checkDriver(verificationCodeDTO.getDriverPhone());

        String accessToken = JwtUtils.generatorToken(phone, IdentityConstant.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN);
        String refreshToken = JwtUtils.generatorToken(phone, IdentityConstant.DRIVER_IDENTITY, TokenConstants.REFRESH_TOKEN);

        String accessTokenKey = RedisUtils.generateTokenKey(phone, IdentityConstant.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);

        String refreshTokenKey = RedisUtils.generateTokenKey(phone, IdentityConstant.DRIVER_IDENTITY, TokenConstants.REFRESH_TOKEN);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
