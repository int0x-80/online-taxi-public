package com.wang.common.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-01:PM7:40
 */

@Data
public class OrderRequest {

    private Long orderId;
    private Long passengerId;
    private String passengerPhone;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;
    private String departure;
    private String depLongitude;
    private String depLatitude;
    private String destination;
    private String destLongitude;
    private String destLatitude;
    private Integer encrypt;
    private String fareType;
    private Integer fareVersion;
    private String deviceId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime toPickUpPassengerTime;
    private String toPickUpPassengerLongitude;
    private String toPickUpPassengerLatitude;
    private String toPickUpPassengerAddress;
    private LocalDateTime pickUpPassengerTime;
    private String pickUpPassengerLongitude;
    private String pickUpPassengerLatitude;
    private LocalDateTime passengerGetOffTime;
    private String passengerGetOffLongitude;
    private String passengerGetOffLatitude;
}
