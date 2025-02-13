package com.wang.common.dto;

import lombok.Data;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-24:PM8:46
 */

@Data
public class DicDistrict {

    private String addressCode;
    private String addressName;
    private String parentAddressCode;
    private int level;
}
