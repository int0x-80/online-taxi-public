package com.wang.common.utils;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-19:PM1:04
 */
public class SsePrefixUtil {

    public static final String SPERATOR = "#";

    public static String generateKey(long userId, String identity) {
        return userId + SPERATOR + identity;
    }
}
