package com.wang.service.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.common.constant.CommonStatusEnum;
import com.wang.common.constant.DriverCarConstant;
import com.wang.common.constant.OrderConstant;
import com.wang.common.dto.Car;
import com.wang.common.dto.OrderInfo;
import com.wang.common.dto.PriceRule;
import com.wang.common.dto.ResponseResult;
import com.wang.common.request.OrderRequest;
import com.wang.common.response.OrderDriverResponse;
import com.wang.common.response.TerminalResponse;
import com.wang.common.utils.RedisUtils;
import com.wang.service.order.mapper.OrderMapper;
import com.wang.service.order.remote.ServiceDriverUserClient;
import com.wang.service.order.remote.ServiceMapClient;
import com.wang.service.order.remote.ServicePriceClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wang Yinuo
 * @since 2025-02-01
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceMapClient serviceMapClient;


    public ResponseResult add(OrderRequest orderRequest) {

        ResponseResult<Boolean> availableDriver = serviceDriverUserClient.isAvailableDriver(orderRequest.getAddress());
        if (availableDriver.getData() == null || !availableDriver.getData()) {
            return ResponseResult.fail(CommonStatusEnum.NO_AVAILABLE_DRIVER.getCode(), CommonStatusEnum.NO_AVAILABLE_DRIVER.getValue());
        }

        // 判断计价规则
        ResponseResult<Boolean> isNewest = servicePriceClient.isPriceNewest(orderRequest.getFareType(), orderRequest.getFareVersion());

        if (isNewest.getData() == null || !isNewest.getData()) {
            return ResponseResult.fail(CommonStatusEnum.FARE_NOT_NEWEST.getCode(), CommonStatusEnum.FARE_NOT_NEWEST.getValue());
        }

        if (isBlackDevice(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.BLACK_DEVICE.getCode(), CommonStatusEnum.BLACK_DEVICE.getValue());
        }

        if (isPriceRuleExists(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getValue());
        }

        // 查询正在进行的订单
        if(isPassengerOrderOngoing(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_CAN_NOT_CREATE.getCode(), CommonStatusEnum.ORDER_CAN_NOT_CREATE.getValue());
        }



        OrderInfo orderInfo = new OrderInfo();

        BeanUtils.copyProperties(orderRequest, orderInfo);
        orderInfo.setOrderStatus(OrderConstant.ORDER_START);

        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        dispatchRealTimeOrder(orderInfo);

        orderMapper.insert(orderInfo);
        return ResponseResult.success();
    }

    public void dispatchRealTimeOrder(OrderInfo orderInfo) {
        String longitude = orderInfo.getDepLongitude();
        String latitude = orderInfo.getDepLatitude();

        String center = latitude + "," + longitude;
        for (int radius = 1; radius <= 5; radius += 2) {
            ResponseResult<List<TerminalResponse>> around = serviceMapClient.around(center, radius* 1000 + "");
            List<TerminalResponse> aroundList = around.getData();

            for (TerminalResponse data : aroundList) {
                Long carId = data.getCarId();
                String carLongitude = data.getLongitude();
                String carLatitude = data.getLatitude();

                ResponseResult<OrderDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                OrderDriverResponse driverData = availableDriver.getData();
                Long driverId = driverData.getDriverId();
                if(isDriverOrderGoing(driverId) > 0) {
                    continue;
                }

                orderInfo.setDriverId(driverId);
                orderInfo.setCarId(driverData.getCarId());
                orderInfo.setDriverPhone(driverData.getDriverPhone());
                orderInfo.setReceiveOrderCarLatitude(carLatitude);
                orderInfo.setReceiveOrderCarLongitude(carLongitude);
                orderInfo.setReceiveOrderTime(LocalDateTime.now());
                orderInfo.setVehicleNo(driverData.getVehicleNo());
                orderInfo.setLicenseId(driverData.getLicenseId());
                orderInfo.setOrderStatus(OrderConstant.ORDER_DRIVER_RECEIVE_ORDER);

                orderMapper.updateById(orderInfo);

                break;
            }
        }
        System.out.println("fail");
    }

    private boolean isPriceRuleExists(OrderRequest orderRequest) {
        String fareType = orderRequest.getFareType();
        String cityCode = fareType.substring(0, fareType.indexOf("$"));
        String fareVersion = fareType.substring(fareType.indexOf("$") + 1);
        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setFareVersion(Integer.parseInt(fareVersion));
        ResponseResult<Boolean> priceExist = servicePriceClient.isPriceExist(priceRule);
        return priceExist.getData();
    }

    public boolean isBlackDevice(OrderRequest orderRequest) {

        String deviceId = orderRequest.getDeviceId();
        String deviceKey = RedisUtils.BLACK_DEVICE_PREFIX + deviceId;
        Boolean hasKey = stringRedisTemplate.hasKey(deviceKey);
        if (hasKey) {
            String value = stringRedisTemplate.opsForValue().get(deviceKey);
            int countKey = Integer.parseInt(value);
            if (countKey > 2) {
                return true;
            } else {
                stringRedisTemplate.opsForValue().increment(deviceKey);
            }
        } else {
            stringRedisTemplate.opsForValue().setIfAbsent(deviceKey, "1", 1L, TimeUnit.HOURS);
        }

        return false;
    }

    public boolean isPassengerOrderOngoing(OrderRequest orderRequest) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id", orderRequest.getPassengerId());
        queryWrapper.and(wrapper -> wrapper
                .eq("order_status", OrderConstant.ORDER_START)
                .or().eq("order_status", OrderConstant.ORDER_DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstant.ORDER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstant.ORDER_DRIVER_ARRIVE_DEPARTURE)
                .or().eq("order_status", OrderConstant.ORDER_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstant.ORDER_ARRIVE_DESTINATION)
                .or().eq("order_status", OrderConstant.ORDER_START_PAY)
        );

        int count = orderMapper.selectCount(queryWrapper);
        if (count > 0) {
            return true;
        }

        return false;
    }

    private int isDriverOrderGoing(Long driverId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverId);
        queryWrapper.and(wrapper -> wrapper
                .eq("order_status", OrderConstant.ORDER_DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstant.ORDER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstant.ORDER_DRIVER_ARRIVE_DEPARTURE)
                .or().eq("order_status", OrderConstant.ORDER_PICK_UP_PASSENGER)
        );

        return orderMapper.selectCount(queryWrapper);
    }
}
