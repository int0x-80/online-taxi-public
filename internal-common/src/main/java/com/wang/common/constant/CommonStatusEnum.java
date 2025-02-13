package com.wang.common.constant;

import lombok.Getter;

/**
 * @author: WangYinuo
 * @create: 2025-01-03:PM1:37
 */


public enum CommonStatusEnum {
    VERIFICATION_CODE_ERROR("1099", "验证码错误"),
    TOKEN_ERROR("1100", "Token error"),
    USER_NOT_EXIST("1200", "User not exist"),
    FARE_NOT_EXIST("1300", "Fare not exist"),
    PRICE_IS_EXIST("1301", "Price is exist"),
    PRICE_NOT_CHANGED("1302", "Price not change"),
    FARE_NOT_NEWEST("1303", "Fare not newest"),
    PRICE_RULE_NOT_EXISTS("1304", "price rule not exists"),
    MAP_DISTRICT_ERROR("1400", "Map district error"),
    CAR_DRIVER_BIND_ERROR("1500", "Car driver bind error"),
    CAR_BIND_ERROR("1501", "Car driver unbind error"),
    DRIVER_BIND_ERROR("1502", "Car driver unbind error"),
    DRIVER_CAR_UNBIND_ERROR("1503", "Car driver unbind error"),
    NO_AVAILABLE_DRIVER("1505", "No available driver"),
    DRIVER_NOT_EXIST("1600", "Driver not exist"),
    BLACK_DEVICE("1601","black device"),

    ORDER_CAN_NOT_CREATE("1700", "Order can not create, have an available order"),
    SUCCESS("1", "success"),
    FAIL("0", "fail");




    @Getter
    private String code;
    @Getter
    private String value;

    CommonStatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }


}
