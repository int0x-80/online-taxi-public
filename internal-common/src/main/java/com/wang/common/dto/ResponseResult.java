package com.wang.common.dto;

import com.wang.common.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: WangYinuo
 * @create: 2025-01-03:PM1:39
 */

@Data
@Accessors(chain = true)
public class ResponseResult<T> {

    private String code;
    private String message;
    private T data;
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<T>().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue());
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue()).setData(data);
    }

    public static <T> ResponseResult<T> fail(T data) {
        return new ResponseResult<T>().setData(data);
    }

    public static <T> ResponseResult<T> fail(String code, String message) {
        return new ResponseResult<T>().setCode(code).setMessage(message);
    }

    public static <T> ResponseResult<T> fail(String code,String message, T data) {
        return new ResponseResult<T>().setCode(code).setMessage(message).setData(data);
    }
}
