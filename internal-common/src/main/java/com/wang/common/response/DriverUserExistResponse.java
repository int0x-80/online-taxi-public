package com.wang.common.response;

import lombok.Data;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-30:PM4:05
 */

@Data
public class DriverUserExistResponse {
    private Integer isExists;
    private String driverPhone;
}
