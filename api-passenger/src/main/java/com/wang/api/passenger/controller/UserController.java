package com.wang.api.passenger.controller;

import com.wang.api.passenger.service.UserService;
import com.wang.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-16:PM1:34
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseResult getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return userService.getUserByAccessToken(token);
    }

}
