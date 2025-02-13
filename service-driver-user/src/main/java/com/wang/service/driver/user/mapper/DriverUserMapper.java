package com.wang.service.driver.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.common.dto.DriverUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-25:AM11:33
 */

@Repository
public interface DriverUserMapper extends BaseMapper<DriverUser> {

    public Integer selectDriverUserByCityCode(@Param("cityCode") String cityCode);

}
