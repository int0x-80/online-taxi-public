package com.wang.service.map.service;

import com.wang.common.constant.AMapConfigConstant;
import com.wang.common.constant.CommonStatusEnum;
import com.wang.common.dto.DicDistrict;
import com.wang.common.dto.ResponseResult;
import com.wang.service.map.mapper.DicDistrictMapper;
import com.wang.service.map.remote.MapDicDistrictClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-24:PM8:54
 */
@Service
public class DicDistrictService {

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;
    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    public ResponseResult initDicDistrict(String keyWord) {
        String dicDistrictBody = mapDicDistrictClient.getDicDistrict(keyWord);

        JSONObject dicDistrictJson = JSONObject.fromObject(dicDistrictBody);
        if (dicDistrictJson.has(AMapConfigConstant.STATUS) && dicDistrictJson.getInt(AMapConfigConstant.STATUS) != 1) {
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(), CommonStatusEnum.MAP_DISTRICT_ERROR.getValue());
        }

        if (dicDistrictJson.has(AMapConfigConstant.DISTRICT)) {
            JSONArray districtsJsonJSONArray = dicDistrictJson.getJSONArray(AMapConfigConstant.DISTRICT);
            for (int i = 0; i < districtsJsonJSONArray.size(); i++) {
                JSONObject districtJson = districtsJsonJSONArray.getJSONObject(i);

                String addressCode = districtJson.getString(AMapConfigConstant.ADDRESSCODE);
                String addressName = districtJson.getString(AMapConfigConstant.NAME);
                String parentAddressCode = "0";
                String level = districtJson.getString(AMapConfigConstant.LEVEL);

                insertDicDistrict(addressCode, addressName, parentAddressCode, level);

                JSONArray provincesJsonJSONArray = districtJson.getJSONArray(AMapConfigConstant.DISTRICT);
                for (int p = 0; p < provincesJsonJSONArray.size(); p++) {
                    JSONObject provincesDistrictJson = provincesJsonJSONArray.getJSONObject(p);

                    String provincesAddressCode = provincesDistrictJson.getString(AMapConfigConstant.ADDRESSCODE);
                    String provincesAddressName = provincesDistrictJson.getString(AMapConfigConstant.NAME);
                    String provincesParentAddressCode = addressCode;
                    String provincesLevel = provincesDistrictJson.getString(AMapConfigConstant.LEVEL);

                    insertDicDistrict(provincesAddressCode, provincesAddressName, provincesParentAddressCode, provincesLevel);

                    JSONArray cityJsonJSONArray = provincesDistrictJson.getJSONArray(AMapConfigConstant.DISTRICT);
                    for (int c = 0; c < cityJsonJSONArray.size(); c++) {
                        JSONObject cityDistrictJson = cityJsonJSONArray.getJSONObject(c);

                        String cityAddressCode = cityDistrictJson.getString(AMapConfigConstant.ADDRESSCODE);
                        String cityAddressName = cityDistrictJson.getString(AMapConfigConstant.NAME);
                        String cityParentAddressCode = provincesAddressCode;
                        String cityLevel = cityDistrictJson.getString(AMapConfigConstant.LEVEL);

                        insertDicDistrict(cityAddressCode, cityAddressName, cityParentAddressCode, cityLevel);

                        JSONArray ddJsonJSONArray = cityDistrictJson.getJSONArray(AMapConfigConstant.DISTRICT);
                        for (int dd = 0; dd < ddJsonJSONArray.size(); dd++) {

                            JSONObject ddDistrictJson = ddJsonJSONArray.getJSONObject(dd);

                            String ddAddressCode = ddDistrictJson.getString(AMapConfigConstant.ADDRESSCODE);
                            String ddAddressName = ddDistrictJson.getString(AMapConfigConstant.NAME);
                            String ddParentAddressCode = cityAddressCode;
                            String ddLevel = ddDistrictJson.getString(AMapConfigConstant.LEVEL);
                            if (ddAddressCode.equals(ddParentAddressCode)) {
                                continue;
                            }
                            insertDicDistrict(ddAddressCode, ddAddressName, ddParentAddressCode, ddLevel);
                        }
                    }
                }

            }


        }

        return null;
    }

    public void insertDicDistrict(String addressCode, String addressName, String parentAddressCode, String level) {
        DicDistrict dicDistrict = new DicDistrict();
        dicDistrict.setAddressCode(addressCode);
        dicDistrict.setAddressName(addressName);
        dicDistrict.setParentAddressCode(parentAddressCode);
        dicDistrict.setLevel(generateLevel(level));

        dicDistrictMapper.insert(dicDistrict);
    }

    public int generateLevel(String level) {
        if (level == null) {
            return 0;
        }

        if (level.equals("country")) {
            return 0;
        } else if (level.equals("province")) {
            return 1;
        } else if (level.equals("city")) {
            return 2;
        } else if (level.equals("district")) {
            return 3;
        } else {
            return 0;
        }
    }
}
