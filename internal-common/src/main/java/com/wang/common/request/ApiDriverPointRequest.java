package com.wang.common.request;

import com.wang.common.dto.PointDto;
import lombok.Data;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM6:19
 */

@Data
public class ApiDriverPointRequest {

    private Long carId;
    private PointDto[] points;
}
