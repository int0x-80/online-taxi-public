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
 * @since 2025-01-30
 */
@Data
public class DriverWorkStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private Long driverId;

    private Integer workStatus;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;
}
