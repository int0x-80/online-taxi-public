package com.wang.api.driver.interceptor;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.wang.common.constant.TokenConstants;
import com.wang.common.dto.ResponseResult;
import com.wang.common.dto.TokenResult;
import com.wang.common.utils.JwtUtils;
import com.wang.common.utils.RedisUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-14:PM12:49
 */

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String msg = "";

        String authorization = request.getHeader("Authorization");
        TokenResult tokenResult = JwtUtils.checkToken(authorization);

        if (tokenResult == null) {
            result = false;
            msg = "token error";
        } else {
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String token = stringRedisTemplate.opsForValue().get(RedisUtils.generateTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN));
            if (StringUtils.isBlank(token) || !token.equals(authorization)) {
                result = false;
                msg = "token error";
            }
        }

        if (!result) {
            PrintWriter printWriter = response.getWriter();
            printWriter.print(JSONObject.fromObject(ResponseResult.fail(msg)).toString());
            printWriter.flush();
            printWriter.close();
            return false;
        }
        return result;
    }
}