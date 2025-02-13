package com.wang.common.request;

import lombok.Data;

/**
 * @author: WangYinuo
 * @create: 2025-01-02:PM1:39
 */

@Data
public class VerificationCodeDTO {

    private String passengerPhone;
    private String driverPhone;
    private String verificationCode;
}
