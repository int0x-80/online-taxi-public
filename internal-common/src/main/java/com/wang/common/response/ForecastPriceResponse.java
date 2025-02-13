package com.wang.common.response;

import lombok.Data;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-17:PM1:04
 */
@Data
public class ForecastPriceResponse {
    private Double price;
    private String cityCode;
    private String vehicleType;
    private String fareType;
    private Integer fareVersion;
}
