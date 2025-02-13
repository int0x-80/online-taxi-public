package com.wang.service.passenger.user.service;

import com.wang.common.constant.CommonStatusEnum;
import com.wang.common.dto.PassengerUser;
import com.wang.common.dto.ResponseResult;
import com.wang.service.passenger.user.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: WangYinuo
 * @create: 2025-01-07:PM1:32
 */

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult user(String passengerPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);

        if (passengerUsers.isEmpty()) {
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setPassengerGender((byte) 1);
            passengerUser.setPassengerName("passengers");
            passengerUser.setState((byte) 1);
            passengerUser.setGmtCreate(LocalDateTime.now());
            passengerUser.setGtmModified(LocalDateTime.now());
            passengerUserMapper.insert(passengerUser);
        }
        return ResponseResult.success();
    }

    public ResponseResult getUserByPhone(String passengerPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        if (passengerUsers.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXIST.getCode(), CommonStatusEnum.USER_NOT_EXIST.getValue());
        }
        PassengerUser passengerUser = passengerUsers.get(0);

        return ResponseResult.success(passengerUser);
    }
}