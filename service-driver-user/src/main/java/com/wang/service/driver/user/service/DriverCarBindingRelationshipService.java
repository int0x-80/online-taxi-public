package com.wang.service.driver.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.common.constant.CommonStatusEnum;
import com.wang.common.constant.DriverCarConstant;
import com.wang.common.dto.DriverCarBindingRelationship;
import com.wang.common.dto.ResponseResult;
import com.wang.service.driver.user.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wang Yinuo
 * @since 2025-01-29
 */
@Service
public class DriverCarBindingRelationshipService {

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
        queryWrapper.eq("state", DriverCarConstant.DRIVER_CAR_BIND);

        Integer i = driverCarBindingRelationshipMapper.selectCount(queryWrapper);

        if (i.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEnum.CAR_DRIVER_BIND_ERROR.getCode(), CommonStatusEnum.CAR_DRIVER_BIND_ERROR.getValue());
        }

        QueryWrapper<DriverCarBindingRelationship> queryWrapperDriver = new QueryWrapper<>();
        queryWrapperDriver.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapperDriver.eq("state", DriverCarConstant.DRIVER_CAR_BIND);

        i = driverCarBindingRelationshipMapper.selectCount(queryWrapperDriver);

        if (i.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_ERROR.getCode(), CommonStatusEnum.CAR_DRIVER_BIND_ERROR.getValue());
        }

        QueryWrapper<DriverCarBindingRelationship> queryWrapperCar = new QueryWrapper<>();
        queryWrapperCar.eq("car_id", driverCarBindingRelationship.getDriverId());
        queryWrapperCar.eq("state", DriverCarConstant.DRIVER_CAR_BIND);

        i = driverCarBindingRelationshipMapper.selectCount(queryWrapperCar);

        if (i.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEnum.CAR_BIND_ERROR.getCode(), CommonStatusEnum.CAR_DRIVER_BIND_ERROR.getValue());
        }

        QueryWrapper<DriverCarBindingRelationship> queryWrapperStat = new QueryWrapper<>();
        queryWrapperStat.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapperStat.eq("car_id", driverCarBindingRelationship.getCarId());
        queryWrapperStat.eq("state", DriverCarConstant.DRIVER_CAR_UNBIND);
        i = driverCarBindingRelationshipMapper.selectCount(queryWrapper);

        if (i.intValue() > 0) {
            driverCarBindingRelationship.setState(DriverCarConstant.DRIVER_CAR_BIND);
            driverCarBindingRelationshipMapper.updateById(driverCarBindingRelationship);
        } else {
            driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
        }

        return ResponseResult.success();
    }

    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
        Map<String, Object> map = new HashMap();
        map.put("driver_id", driverCarBindingRelationship.getDriverId());
        map.put("car_id", driverCarBindingRelationship.getCarId());
        map.put("state", DriverCarConstant.DRIVER_CAR_BIND);

        List<DriverCarBindingRelationship> driverCarBindingRelationshipList = driverCarBindingRelationshipMapper.selectByMap(map);
        if (driverCarBindingRelationshipList.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_UNBIND_ERROR.getCode(), CommonStatusEnum.DRIVER_CAR_UNBIND_ERROR.getValue());
        }
        DriverCarBindingRelationship driverCarBindingRelationshipDB = driverCarBindingRelationshipList.get(0);
        driverCarBindingRelationshipDB.setState(DriverCarConstant.DRIVER_CAR_UNBIND);
        driverCarBindingRelationshipDB.setUnBindingTime(LocalDateTime.now());
        int i = driverCarBindingRelationshipMapper.updateById(driverCarBindingRelationshipDB);
        return ResponseResult.success(i);
    }
}