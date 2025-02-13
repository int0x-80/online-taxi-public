package com.wang.service.map.remote;

import com.wang.common.constant.AMapConfigConstant;
import com.wang.common.dto.PointDto;
import com.wang.common.dto.ResponseResult;
import com.wang.common.request.PointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM5:38
 */
@Service
public class PointClient {

    @Value("${amap.key}")
    private String key;

    @Value("${amap.sid}")
    private String sid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult upload(PointRequest pointRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(AMapConfigConstant.POINT_UPLOAD_URL);
        sb.append("?");
        sb.append("key=").append(key);
        sb.append("&");
        sb.append("sid=").append(sid);
        sb.append("&");
        sb.append("tid=").append(pointRequest.getTid());
        sb.append("&");
        sb.append("trid=").append(pointRequest.getTrid());
        sb.append("&");
        sb.append("points=");
        PointDto[] points = pointRequest.getPoints();
        sb.append("%5B");
        for (PointDto pointDto : points) {
            sb.append("%7B");
            String location = pointDto.getLocation();
            String locatetime = pointDto.getLocatetime();
            sb.append("%22location%22").append("%3A").append("%22" + location + "%22");
            sb.append("%2C");
            sb.append("%22locatetime%22").append("%3A").append("%22" + locatetime + "%22");
            sb.append("%7D");

        }
        sb.append("%5D");

        ResponseEntity<String> directionEntity = restTemplate.postForEntity(URI.create(sb.toString()),null,  String.class);

        String body = directionEntity.getBody();
        System.out.println(body);

        return ResponseResult.success();
    }
}
