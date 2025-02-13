package com.wang.api.passenger.service;

import com.wang.api.passenger.remote.ServicePassengerUserClient;
import com.wang.common.constant.CommonStatusEnum;
import com.wang.common.dto.PassengerUser;
import com.wang.common.dto.ResponseResult;
import com.wang.common.dto.TokenResult;
import com.wang.common.request.VerificationCodeDTO;
import com.wang.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-16:PM1:35
 */
@Service
public class UserService {

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUserByAccessToken(String accessToken) {
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);

        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        String phone = tokenResult.getPhone();
        ResponseResult user = servicePassengerUserClient.getUser(phone);

        PassengerUser passengerUser = new PassengerUser();
        passengerUser.setPassengerName("wang");
        passengerUser.setProfilePhoto("123");
        return ResponseResult.success(passengerUser);
    }

}
