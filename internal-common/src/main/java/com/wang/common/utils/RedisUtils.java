package com.wang.common.utils;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-14:PM1:27
 */
public class RedisUtils {

    public static String BLACK_DEVICE_PREFIX = "black-device-";
    public static String generateKeyByPhone(String phoneNumber, String identity) {
        String verifcationCodePrefix = "verification-code-";
        return verifcationCodePrefix +identity + "-" + phoneNumber;
    }

    public static String generateTokenKey(String phoneNumber, String identity, String tokenType) {
        String tokenCodePrefix = "token-";
        return tokenCodePrefix + phoneNumber + "-" + identity + "-" + tokenType;
    }


}
