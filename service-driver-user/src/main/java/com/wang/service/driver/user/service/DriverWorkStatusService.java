package com.wang.service.driver.user.service;

import com.wang.common.dto.DriverWorkStatus;
import com.wang.common.dto.ResponseResult;
import com.wang.service.driver.user.mapper.DriverWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wang Yinuo
 * @since 2025-01-30
 */
@Service
public class DriverWorkStatusService {

    @Autowired
    private DriverWorkStatusMapper driverWorkStatusMapper;

    public ResponseResult changeWorkStatus(DriverWorkStatus driverWorkStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_id", driverWorkStatus.getDriverId());

        List<DriverWorkStatus> driverWorkStatuses = driverWorkStatusMapper.selectByMap(map);
        DriverWorkStatus driverWorkStatus1 = driverWorkStatuses.get(0);
        driverWorkStatus1.setWorkStatus(driverWorkStatus.getWorkStatus());

        int i = driverWorkStatusMapper.updateById(driverWorkStatus1);

        return ResponseResult.success(i);
    }


}
