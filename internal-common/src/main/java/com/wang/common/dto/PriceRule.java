package com.wang.common.dto;

import lombok.Data;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-21:PM1:40
 */

@Data
public class PriceRule {
    private String cityCode;

    private String vehicleType;

    private Double startFare;

    private Integer startMile;

    private Double unitPricePerMile;

    private Double unitPricePerMinute;

    private String fareType;

    private Integer fareVersion;
}
