package com.wang.service.map.remote;

import com.wang.common.constant.AMapConfigConstant;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.DirectionResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-20:PM1:09
 */

@Service
public class MapDirectionClient {

    @Value("${amap.key}")
    private String key;

    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        StringBuilder sb = new StringBuilder();
        sb.append(AMapConfigConstant.DIRECTION_URL);
        sb.append("?");
        sb.append("origin=").append(depLongitude + "," + depLatitude);
        sb.append("&");
        sb.append("destination=").append(destLongitude + "," + destLatitude);
        sb.append("&");
        sb.append("extensions=").append("base");
        sb.append("&");
        sb.append("strategy=").append("0");
        sb.append("&");
        sb.append("key=").append(key);
        System.out.println(sb.toString());

        ResponseEntity<String> directionEntity = restTemplate.getForEntity(sb.toString(), String.class);
        return parseDirectionEntity(directionEntity.getBody());
    }

    private DirectionResponse parseDirectionEntity(String directionEntityBody) {
        DirectionResponse directionResponse = null;
        System.out.println(directionEntityBody);
        try {
            directionResponse = new DirectionResponse();
            JSONObject jsonObject = JSONObject.fromObject(directionEntityBody);
            if (jsonObject.has(AMapConfigConstant.STATUS) && jsonObject.getInt(AMapConfigConstant.STATUS) == 1) {
                if (jsonObject.has(AMapConfigConstant.ROUTE)) {
                    JSONObject routeObj = jsonObject.getJSONObject(AMapConfigConstant.ROUTE);
                    JSONArray pathArray = routeObj.getJSONArray(AMapConfigConstant.PATHS);
                    JSONObject pathObj = pathArray.getJSONObject(0);
                    if (pathObj.has(AMapConfigConstant.DISTANCE)) {
                        Double distance = pathObj.getDouble(AMapConfigConstant.DISTANCE);
                        directionResponse.setDistance(distance);
                    }

                    if (pathObj.has(AMapConfigConstant.DURATION)) {
                        Double duration = pathObj.getDouble(AMapConfigConstant.DURATION);
                        directionResponse.setDuration(duration);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("error");
        }
        System.out.println(directionResponse);
        return directionResponse;
    }
}
