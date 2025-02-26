package com.wang.common.response;

import lombok.Data;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-11:PM1:50
 */
@Data
public class OrderDriverResponse {
    private Long driverId;
    private String driverPhone;
    private Long carId;
    // private String receiveOrderCarLatitude;
    // private String receiveOrderCarLongitude;
    private String licenseId;
    private String vehicleNo;
    private String vehicleType;
}
