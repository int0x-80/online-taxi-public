package com.wang.service.map.remote;

import com.wang.common.constant.AMapConfigConstant;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.ServiceResponse;
import com.wang.common.response.TerminalResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM3:31
 */

@Service
public class TerminalClient {

    @Value("${amap.key}")
    private String key;

    @Value("${amap.sid}")
    private String sid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult<TerminalResponse> add(String name, String desc) {
        StringBuilder sb = new StringBuilder();
        sb.append(AMapConfigConstant.TERMINAL_ADD_URL);
        sb.append("?");
        sb.append("key=").append(key);
        sb.append("&");
        sb.append("sid=").append(sid);
        sb.append("&");
        sb.append("name=").append(name);
        sb.append("&");
        sb.append("desc=").append(desc);

        ResponseEntity<String> directionEntity = restTemplate.postForEntity(sb.toString(),null,  String.class);

        String body = directionEntity.getBody();
        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        String tid = data.getString("tid");
        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);
        return ResponseResult.success(terminalResponse);
    }

    public ResponseResult<List<TerminalResponse>> around(String center, String radius) {

        StringBuilder sb = new StringBuilder();
        sb.append(AMapConfigConstant.TERMINAL_ROUND_URL);
        sb.append("?");
        sb.append("key=").append(key);
        sb.append("&");
        sb.append("sid=").append(sid);
        sb.append("&");
        sb.append("center=").append(center);
        sb.append("&");
        sb.append("radius=").append(radius);

        System.out.println(sb);
        ResponseEntity<String> directionEntity = restTemplate.postForEntity(sb.toString(),null,  String.class);
        String body = directionEntity.getBody();
        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray result = data.getJSONArray("results");
        List list = new ArrayList();

        for (int i = 0; i < result.size(); i++) {
            JSONObject jsonObject1 = result.getJSONObject(i);
            String carId = "0";
            if (jsonObject1.has("desc")) {
                carId = jsonObject1.getString("desc");
            }
            String longitude = "";
            String latitude = "";
            if (jsonObject1.has("location")) {
                JSONObject location = jsonObject1.getJSONObject("location");
                longitude = location.getString("longitude");
                latitude = location.getString("latitude");

            }
            String tid = jsonObject1.getString("tid");

            TerminalResponse terminalResponse = new TerminalResponse();
            terminalResponse.setTid(tid);
            terminalResponse.setCarId(Long.valueOf(carId));
            terminalResponse.setLatitude(latitude);
            terminalResponse.setLongitude(longitude);
            list.add(terminalResponse);
        }

        return ResponseResult.success(list);
    }
}
