package com.wang.common.request;

import com.wang.common.dto.PointDto;
import lombok.Data;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM5:36
 */

@Data
public class PointRequest {

    private String tid;
    private String trid;
    private PointDto[] points;
}
