package com.wang.api.driver.service;

import com.wang.api.driver.remote.ServiceDriverUserClient;
import com.wang.api.driver.remote.ServiceMapClient;
import com.wang.common.dto.Car;
import com.wang.common.dto.ResponseResult;
import com.wang.common.request.ApiDriverPointRequest;
import com.wang.common.request.PointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM6:21
 */

@Service
public class PointService {
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest) {
        Long carId = apiDriverPointRequest.getCarId();
        ResponseResult<Car> car = serviceDriverUserClient.getCar(carId);
        Car data = car.getData();
        String tid = data.getTid();
        String trid = data.getTrid();

        PointRequest pointRequest = new PointRequest();
        pointRequest.setTid(tid);
        pointRequest.setTrid(trid);
        pointRequest.setPoints(apiDriverPointRequest.getPoints());

        return serviceMapClient.pointUpload(pointRequest);
    }
}
