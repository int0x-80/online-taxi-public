package com.wang.common.response;

import lombok.Data;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM3:36
 */
@Data
public class TerminalResponse {
    private String tid;
    private Long carId;
    String longitude;
    String latitude;
}
