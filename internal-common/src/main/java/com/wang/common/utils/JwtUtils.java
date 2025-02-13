package com.wang.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wang.common.dto.TokenResult;
import com.wang.common.response.TokenResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: WangYinuo
 * @create: 2025-01-13:PM1:03
 */
public class JwtUtils {

    private static final String SIGN = "123abc";
    private static final String JWT_KEY_PHONE = "passengerPhone";
    private static final String JWT_KEY_IDENTITY = "identity";

    private static final String JWT_TOKEN_TYPE = "jwt-token-type";

    private static final String JWT_TOKEN_TIME = "jwt-token-time";

    public static String generatorToken(String passengerPhone, String identity, String tokenType) {
        Map<String, String> map = new HashMap<>();

        map.put(JWT_KEY_PHONE, passengerPhone);
        map.put(JWT_KEY_IDENTITY, identity);
        map.put(JWT_TOKEN_TYPE, tokenType);
        map.put(JWT_TOKEN_TIME, String.valueOf(System.currentTimeMillis()));

        // Calendar calendar = Calendar.getInstance();
        // calendar.add(Calendar.DATE, 1);
        // Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();
        map.forEach(
                (k, v) -> {
                    builder.withClaim(k, v);
                }
        );
        // builder.withExpiresAt(date);
        return builder.sign(Algorithm.HMAC256(SIGN));
    }

    public static TokenResult parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String passengerPhone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(passengerPhone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    public static TokenResult checkToken(String token) {
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);
            return tokenResult;
        } catch (Exception e) {
            return null;
        }
    }
}
