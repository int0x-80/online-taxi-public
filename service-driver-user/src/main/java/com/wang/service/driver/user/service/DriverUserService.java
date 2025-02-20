package com.wang.service.driver.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.common.constant.CommonStatusEnum;
import com.wang.common.constant.DriverCarConstant;
import com.wang.common.dto.*;
import com.wang.common.response.OrderDriverResponse;
import com.wang.service.driver.user.mapper.CarMapper;
import com.wang.service.driver.user.mapper.DriverCarBindingRelationshipMapper;
import com.wang.service.driver.user.mapper.DriverUserMapper;
import com.wang.service.driver.user.mapper.DriverWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wang.common.constant.CommonStatusEnum.DRIVER_NOT_EXIST;
import static com.wang.common.constant.DriverCarConstant.DRIVER_STATE_VALID;
import static com.wang.common.constant.DriverCarConstant.DRIVER_WORK_STOP;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-25:AM11:34
 */

@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    @Autowired
    private DriverWorkStatusMapper driverWorkStatusMapper;

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    @Autowired
    private CarMapper carMapper;

    public ResponseResult getDriverUserByDriverPhone() {
        DriverUser driverUser = driverUserMapper.selectById(1);
        return ResponseResult.success(driverUser);
    }

    public ResponseResult addUser(DriverUser driverUser) {
        int insert = driverUserMapper.insert(driverUser);

        DriverWorkStatus driverWorkStatus = new DriverWorkStatus();
        driverWorkStatus.setDriverId(driverUser.getId());
        driverWorkStatus.setWorkStatus(DRIVER_WORK_STOP);
        driverWorkStatus.setGmtCreate(LocalDateTime.now());
        driverWorkStatus.setGmtModified(LocalDateTime.now());
        driverWorkStatusMapper.insert(driverWorkStatus);

        return ResponseResult.success(insert);
    }

    public ResponseResult updateUser(DriverUser driverUser) {
        int insert = driverUserMapper.updateById(driverUser);
        return ResponseResult.success(insert);
    }

    public ResponseResult<DriverUser> getUserByPhone(String driverPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_phone", driverPhone);
        map.put("state", DRIVER_STATE_VALID);
        List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);
        if (driverUsers.isEmpty()) {
            return ResponseResult.fail(DRIVER_NOT_EXIST.getCode(), DRIVER_NOT_EXIST.getValue());
        }

        return ResponseResult.success(driverUsers.get(0));
    }

    public ResponseResult<OrderDriverResponse> getAvailableDriver(Long carId) {
        System.out.println("carid:"+ carId);
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper();
        queryWrapper.eq("car_id", carId);
        queryWrapper.eq("state", DriverCarConstant.DRIVER_CAR_BIND);

        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(queryWrapper);
        Long driverId = driverCarBindingRelationship.getDriverId();

        QueryWrapper<DriverWorkStatus> queryWrapperWorkStatus = new QueryWrapper();
        queryWrapperWorkStatus.eq("driver_id", driverId);
        queryWrapperWorkStatus.eq("work_status", DriverCarConstant.DRIVER_WORK_START);

        Integer count = driverWorkStatusMapper.selectCount(queryWrapperWorkStatus);
        if(count <= 0) {
            return ResponseResult.fail(CommonStatusEnum.NO_AVAILABLE_DRIVER.getCode(), CommonStatusEnum.NO_AVAILABLE_DRIVER.getValue());
        } else {
            QueryWrapper<DriverUser> queryWrapperDriverUser = new QueryWrapper();
            queryWrapperDriverUser.eq("id", driverId);
            DriverUser driverUser = driverUserMapper.selectOne(queryWrapperDriverUser);

            QueryWrapper<Car> queryWrapperCar = new QueryWrapper();
            queryWrapperCar.eq("id", carId);
            Car car = carMapper.selectOne(queryWrapperCar);

            OrderDriverResponse orderDriverResponse = new OrderDriverResponse();
            orderDriverResponse.setDriverPhone(driverUser.getDriverPhone());
            orderDriverResponse.setCarId(carId);
            orderDriverResponse.setDriverId(driverId);
            orderDriverResponse.setVehicleNo(car.getVehicleNo());
            orderDriverResponse.setLicenseId(driverUser.getLicenseId());
            return ResponseResult.success(orderDriverResponse);
        }
    }
}
