package com.wang.common.response;

import lombok.Data;

/**
 * @author: WangYinuo
 * @create: 2025-01-03:PM1:50
 */

@Data
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
