package com.wang.service.price.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.common.dto.PriceRule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: Wang Yinuo
 * @create: 2025-01-21:PM1:39
 */

@Repository
public interface PriceRuleMapper extends BaseMapper<PriceRule> {

}
