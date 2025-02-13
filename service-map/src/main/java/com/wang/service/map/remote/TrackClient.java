package com.wang.service.map.remote;

import com.wang.common.constant.AMapConfigConstant;
import com.wang.common.dto.ResponseResult;
import com.wang.common.response.TrackResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-31:PM3:31
 */

@Service
public class TrackClient {

    @Value("${amap.key}")
    private String key;

    @Value("${amap.sid}")
    private String sid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult<TrackResponse> add(String tid) {
        StringBuilder sb = new StringBuilder();
        sb.append(AMapConfigConstant.TRACK_ADD_URL);
        sb.append("?");
        sb.append("key=").append(key);
        sb.append("&");
        sb.append("sid=").append(sid);
        sb.append("&");
        sb.append("tid=").append(tid);

        ResponseEntity<String> directionEntity = restTemplate.postForEntity(sb.toString(),null,  String.class);

        String body = directionEntity.getBody();
        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        String trid = data.getString("trid");

        TrackResponse trackResponse = new TrackResponse();
        if (data.has("trname")) {
            trackResponse.setTrname(data.getString("trname"));
        }
        trackResponse.setTrid(trid);
        return ResponseResult.success(trackResponse);
    }
}
