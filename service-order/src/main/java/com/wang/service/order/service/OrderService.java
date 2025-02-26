package com.wang.service.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.common.constant.CommonStatusEnum;
import com.wang.common.constant.DriverCarConstant;
import com.wang.common.constant.IdentityConstant;
import com.wang.common.constant.OrderConstant;
import com.wang.common.dto.Car;
import com.wang.common.dto.OrderInfo;
import com.wang.common.dto.PriceRule;
import com.wang.common.dto.ResponseResult;
import com.wang.common.request.OrderRequest;
import com.wang.common.response.OrderDriverResponse;
import com.wang.common.response.TerminalResponse;
import com.wang.common.response.TrsearchResponse;
import com.wang.common.utils.RedisUtils;
import com.wang.service.order.config.RedisConfig;
import com.wang.service.order.mapper.OrderMapper;
import com.wang.service.order.remote.ServiceDriverUserClient;
import com.wang.service.order.remote.ServiceMapClient;
import com.wang.service.order.remote.ServicePriceClient;
import com.wang.service.order.remote.ServiceSsePushClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.util.random.CachedRandomPropertySourceAutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private ServiceSsePushClient serviceSsePushClient;


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
        if (isPassengerOrderOngoing(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_CAN_NOT_CREATE.getCode(), CommonStatusEnum.ORDER_CAN_NOT_CREATE.getValue());
        }


        OrderInfo orderInfo = new OrderInfo();

        BeanUtils.copyProperties(orderRequest, orderInfo);
        orderInfo.setOrderStatus(OrderConstant.ORDER_START);

        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        orderMapper.insert(orderInfo);

        for (int i = 0; i < 6; i++) {
            int result = dispatchRealTimeOrder(orderInfo);
            if (result == 1) {
                break;
            }
            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            }
        }
        return ResponseResult.success();
    }

    public int dispatchRealTimeOrder(OrderInfo orderInfo) {
        int result = 0;
        String longitude = orderInfo.getDepLongitude();
        String latitude = orderInfo.getDepLatitude();

        String center = latitude + "," + longitude;
        for (int radius = 1; radius <= 5; radius += 2) {
            ResponseResult<List<TerminalResponse>> around = serviceMapClient.around(center, radius * 1000 + "");
            List<TerminalResponse> aroundList = around.getData();

            for (TerminalResponse data : aroundList) {

                Long carId = data.getCarId();
                if (carId == 0) {
                    continue;
                }
                String carLongitude = data.getLongitude();
                String carLatitude = data.getLatitude();
                ResponseResult<OrderDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                OrderDriverResponse driverData = availableDriver.getData();
                Long driverId = driverData.getDriverId();
                String driverPhone = driverData.getDriverPhone();
                String licenseId = driverData.getLicenseId();
                String vehicleTypeFromCar = driverData.getVehicleType();
                String vehicleNo = driverData.getVehicleNo();

                String vehicleType = orderInfo.getVehicleType();

                if (!vehicleType.trim().equals(vehicleTypeFromCar.trim())) {
                    continue;
                }

                // synchronized ((driverId + "").intern()) {
                String intern = (driverId + "").intern();
                RedissonClient redissonClient = redisConfig.redissonClient();
                RLock lock = redissonClient.getLock(intern);
                lock.lock();
                if (isDriverOrderGoing(driverId) > 0) {
                    lock.unlock();
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
                JSONObject jsonObjectDriver = new JSONObject();
                jsonObjectDriver.put("passengerId", orderInfo.getPassengerId());
                jsonObjectDriver.put("passengerPhone", orderInfo.getPassengerPhone());
                jsonObjectDriver.put("departure", orderInfo.getDeparture());
                jsonObjectDriver.put("depLongitude", orderInfo.getDepLongitude());
                jsonObjectDriver.put("depLatitude", orderInfo.getDepLatitude());
                jsonObjectDriver.put("destination", orderInfo.getDestination());
                jsonObjectDriver.put("destLongitude", orderInfo.getDestLongitude());
                jsonObjectDriver.put("destLatitude", orderInfo.getDestLatitude());

                serviceSsePushClient.push(driverId, IdentityConstant.DRIVER_IDENTITY, jsonObjectDriver.toString());

                JSONObject jsonObjectUser = new JSONObject();
                jsonObjectUser.put("driverId", orderInfo.getDriverId());
                jsonObjectUser.put("driverPhone", orderInfo.getDriverPhone());
                jsonObjectUser.put("vehicleNo", orderInfo.getVehicleNo());

                ResponseResult<Car> carData = serviceDriverUserClient.getCarByDriverId(orderInfo.getCarId());
                Car car = carData.getData();

                jsonObjectUser.put("brand", car.getBrand());
                jsonObjectUser.put("model", car.getModel());
                jsonObjectUser.put("plateColor", car.getPlateColor());
                jsonObjectUser.put("receiveOrderCarLongitude", orderInfo.getReceiveOrderCarLongitude());
                jsonObjectUser.put("receiveOrderCarLatitude", orderInfo.getReceiveOrderCarLatitude());

                serviceSsePushClient.push(orderInfo.getPassengerId(), IdentityConstant.PASSENGER_IDENTITY, jsonObjectUser.toString());

                lock.unlock();
                result = 1;
                break;
            }
        }
        return result;
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

    public ResponseResult toPickUpPassenger(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        LocalDateTime toPickUpPassengerTime = orderRequest.getToPickUpPassengerTime();
        String toPickUpPassengerLongitude = orderRequest.getToPickUpPassengerLongitude();
        String toPickUpPassengerLatitude = orderRequest.getToPickUpPassengerLatitude();
        String toPickUpPassengerAddress = orderRequest.getToPickUpPassengerAddress();
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);

        OrderInfo orderInfo = orderMapper.selectOne(queryWrapper);
        orderInfo.setToPickUpPassengerAddress(toPickUpPassengerAddress);
        orderInfo.setToPickUpPassengerTime(toPickUpPassengerTime);
        orderInfo.setToPickUpPassengerLongitude(toPickUpPassengerLongitude);
        orderInfo.setToPickUpPassengerLatitude(toPickUpPassengerLatitude);
        orderInfo.setOrderStatus(OrderConstant.ORDER_TO_PICK_UP_PASSENGER);

        orderMapper.updateById(orderInfo);
        return ResponseResult.success();
    }

    public ResponseResult arrivedDeparture(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderMapper.selectOne(queryWrapper);
        orderInfo.setOrderStatus(OrderConstant.ORDER_DRIVER_ARRIVE_DEPARTURE);
        orderInfo.setDriverArrivedDepartureTime(LocalDateTime.now());
        orderMapper.updateById(orderInfo);
        return ResponseResult.success();
    }

    public ResponseResult pickUpPassenger(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        String pickUpPassengerLongitude = orderRequest.getPickUpPassengerLongitude();
        String pickUpPassengerLatitude = orderRequest.getPickUpPassengerLatitude();
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderMapper.selectOne(queryWrapper);
        orderInfo.setOrderStatus(OrderConstant.ORDER_PICK_UP_PASSENGER);
        orderInfo.setPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setPickUpPassengerLongitude(pickUpPassengerLongitude);
        orderInfo.setPickUpPassengerLatitude(pickUpPassengerLatitude);
        orderMapper.updateById(orderInfo);

        return ResponseResult.success();
    }

    public ResponseResult arrivedDestination(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        String passengerGetOffLongitude = orderRequest.getPassengerGetOffLongitude();
        String pickUpPassengerLatitude = orderRequest.getPassengerGetOffLatitude();
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderMapper.selectOne(queryWrapper);

        orderInfo.setOrderStatus(OrderConstant.ORDER_ARRIVE_DESTINATION);
        orderInfo.setPassengerGetOffLongitude(passengerGetOffLongitude);
        orderInfo.setPassengerGetOffLatitude(pickUpPassengerLatitude);
        orderInfo.setPassengerGetOffTime(LocalDateTime.now());

        ResponseResult<Car> carByDriverId = serviceDriverUserClient.getCarByDriverId(orderInfo.getCarId());
        Car data = carByDriverId.getData();

        ResponseResult<TrsearchResponse> trsearch = serviceMapClient.trsearch(data.getTid(),
                orderInfo.getPickUpPassengerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli(),
                LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        TrsearchResponse data1 = trsearch.getData();
        orderInfo.setDriveMile(data1.getDriverMail());
        orderInfo.setDriveTime(data1.getDriverTime());

        Long driveMile = orderInfo.getDriveMile();
        Long driveTime = orderInfo.getDriveTime();

        ResponseResult<Double> responseResult = servicePriceClient.calculatePrice(driveMile, driveTime, orderInfo.getAddress(), orderInfo.getVehicleType());

        orderInfo.setPrice(responseResult.getData());
        orderMapper.updateById(orderInfo);

        return ResponseResult.success();
    }
}
