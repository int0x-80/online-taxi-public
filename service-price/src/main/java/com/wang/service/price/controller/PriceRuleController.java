package com.wang.service.price.controller;

import com.wang.common.dto.PriceRule;
import com.wang.common.dto.ResponseResult;
import com.wang.service.price.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Wang Yinuo
 * @create: 2025-02-02:PM2:58
 */

@RestController
@RequestMapping("/price-rule")
public class PriceRuleController {

    @Autowired
    private PriceRuleService priceRuleService;

    @PostMapping("/add")
    public ResponseResult add(@RequestBody PriceRule priceRule) {
        return priceRuleService.add(priceRule);
    }

    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody PriceRule priceRule) {
        return priceRuleService.edit(priceRule);
    }

    @GetMapping("/get-newest")
    public ResponseResult getNewest(@RequestParam String fareType) {
        return priceRuleService.getNewest(fareType);
    }

    @GetMapping("/is-newest")
    public ResponseResult isNewest(@RequestParam String fareType, @RequestParam Integer fareVersion) {
        return priceRuleService.isNewest(fareType, fareVersion);
    }

    @PostMapping("/is-exist")
    public ResponseResult<Boolean> isExist(@RequestBody PriceRule priceRule) {
        return priceRuleService.isExist(priceRule);
    }
}
