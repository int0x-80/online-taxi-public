package com.wang.service.map.remote;

import com.wang.common.constant.AMapConfigConstant;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.ServiceResponse;
import com.wang.common.response.TerminalResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM3:02
 */

@Service
public class ServiceClient {

    @Value("${amap.key}")
    private String key;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult<ServiceResponse>  add(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(AMapConfigConstant.SERVICE_ADD_URL);
        sb.append("?");
        sb.append("key=").append(key);
        sb.append("&");
        sb.append("name=").append(name);

        ResponseEntity<String> directionEntity = restTemplate.postForEntity(sb.toString(),null,  String.class);
        String body = directionEntity.getBody();
        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        String id = data.getString("sid");
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setSid(id);

        return ResponseResult.success(serviceResponse);
    }
}
