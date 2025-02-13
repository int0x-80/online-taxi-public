package com.wang.service.driver.user.service;

import com.wang.common.dto.Car;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.TerminalResponse;
import com.wang.common.response.TrackResponse;
import com.wang.service.driver.user.mapper.CarMapper;
import com.wang.service.driver.user.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-27:PM2:51
 */

@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);
        car.setTrid("0");

        int insert = carMapper.insert(car);
        ResponseResult<TerminalResponse> responseResult = serviceMapClient.addTerminal(car.getVehicleNo(), car.getId().toString());
        String tid = responseResult.getData().getTid();
        car.setTid(tid);

        ResponseResult<TrackResponse> responseResult1 = serviceMapClient.addTrack(tid);
        TrackResponse data = responseResult1.getData();
        car.setTrid(data.getTrid());

        carMapper.updateById(car);

        return ResponseResult.success(insert);
    }

    public ResponseResult<Car> getCar(Long carId) {
        Map<String, Object> carMap = new HashMap<>();
        carMap.put("id", carId);
        List<Car> cars = carMapper.selectByMap(carMap);
        Car car = cars.get(0);

        return ResponseResult.success(car);
    }
}
