package com.wang.common.dto;

import lombok.Data;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-17:PM1:00
 */

@Data
public class ForecastPriceDto {

    private String cityCode;
    private String vehicleType;
    private String depLongitude;
    private String depLatitude;
    private String destLongitude;
    private String destLatitude;
}
