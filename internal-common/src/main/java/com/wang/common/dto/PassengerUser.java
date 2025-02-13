package com.wang.common.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: WangYinuo
 * @create: 2025-01-07:PM1:53
 */

@Data
public class PassengerUser {

    private Long id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gtmModified;
    private String passengerPhone;
    private String passengerName;
    private byte passengerGender;
    private String profilePhoto;
    private byte state;
}
