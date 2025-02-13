package com.wang.service.map.remote;

import com.wang.common.constant.AMapConfigConstant;
import com.wang.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-24:PM9:05
 */

@Service
public class MapDicDistrictClient {

    @Value("${amap.key}")
    private String key;

    @Autowired
    private RestTemplate restTemplate;

    public String getDicDistrict(String keyWord) {

        StringBuilder sb = new StringBuilder();

        sb.append(AMapConfigConstant.DISTRICT_URL);
        sb.append("?");
        sb.append("keywords=").append(keyWord);
        sb.append("&");
        sb.append("subdistrict=3");
        sb.append("&");
        sb.append("key=").append(key);

        ResponseEntity<String> directionEntity = restTemplate.getForEntity(sb.toString(), String.class);

        return directionEntity.getBody();
    }
}
