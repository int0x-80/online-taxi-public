package com.wang.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wang Yinuo
 * @since 2025-01-27
 */

@Data
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String tid;

    private String trid;

    private String address;

    private String vehicleNo;

    private String plateColor;

    private Integer seat2;

    private String brand;

    private String model;

    private String vehicleType;

    private String ownerName;

    private String vehicleColor;

    private String engineId;

    private String vin;

    private LocalDate certifyDateA;

    private String fueType;

    private String enginDisplace;

    private String transAgency;

    private String transArea;

    private LocalDate transDateStart;

    private LocalDate transDateEnd;

    private LocalDate certifyDateB;

    private Integer fixState;

    private LocalDate nextFixDate;

    private String checkState;

    private String feePrintId;

    private String gpsBrand;

    private String gpsModel;

    private LocalDate gpsInstallDate;

    private LocalDate registerDate;

    private Integer commercialType;

    private String fareType;

    private Integer state;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;
}
