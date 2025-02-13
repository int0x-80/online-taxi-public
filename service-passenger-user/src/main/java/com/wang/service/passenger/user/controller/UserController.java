package com.wang.service.passenger.user.controller;

import com.wang.common.dto.ResponseResult;
import com.wang.common.request.VerificationCodeDTO;
import com.wang.service.passenger.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: WangYinuo
 * @create: 2025-01-07:PM1:28
 */
@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult user(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        return userService.user(passengerPhone);
    }

    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String phone) {
        return userService.getUserByPhone(phone);
    }
}
