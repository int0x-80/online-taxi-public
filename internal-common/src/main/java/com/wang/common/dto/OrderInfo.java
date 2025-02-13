package com.wang.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wang Yinuo
 * @since 2025-02-01
 */

@Data
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long passengerId;

    private String passengerPhone;

    private Long driverId;

    private String driverPhone;

    private Long carId;

    private String address;

    private LocalDateTime orderTime;

    private LocalDateTime departTime;

    private String departure;

    private String depLongitude;

    private String depLatitude;

    private String destination;

    private String destLongitude;

    private String destLatitude;

    private Integer encrypt;

    private int fareVersion;

    private String fareType;

    private String receiveOrderCarLongitude;

    private String receiveOrderCarLatitude;

    private LocalDateTime receiveOrderTime;

    private String licenseId;

    private String vehicleNo;

    private LocalDateTime toPickUpPassengerTime;

    private String toPickUpPassengerLangitude;

    private String toPickUpPassengerLatitude;

    private String toPickUpPassengerAddress;

    private LocalDateTime driverArrivedDepartureTime;

    private LocalDateTime pickUpPassengerTime;

    private String pickUpPassengerLangitude;

    private String pickUpPassengerLatitude;

    private LocalDateTime passengerGetoffTime;

    private String passengerGetoffLongitude;

    private String passengerGetoffLatitude;

    private LocalDateTime cancelTime;

    private Integer cancelOperator;

    private Integer cancelTypeCode;

    private Long driveMile;

    private Long driveTime;

    private Integer orderStatus;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;
}
