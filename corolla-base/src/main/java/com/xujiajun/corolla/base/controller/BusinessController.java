package com.xujiajun.corolla.base.controller;

import com.xujiajun.core.entity.ResponseData;
import com.xujiajun.corolla.base.compose.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@RestController
@RequestMapping(value = "/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    /**
     * 用户下单
     *
     * @param userId 用户id
     * @param goodsIdList 用户选中的商品的id集合
     * @return 通用状态码
     */
    @PostMapping(value = "/{userId:\\d+}/order/traditional")
    public ResponseData userOrdered(@PathVariable("userId") Long userId, @RequestBody List<Long> goodsIdList) {
        businessService.userPostOrder(userId, goodsIdList);
        return new ResponseData();
    }

    /**
     * 用户下单 mq方式
     *
     * @param userId 用户id
     * @param goodsIdList 用户选中的商品id集合
     * @return 通用状态码
     */
    @PostMapping(value = "/{userId:\\d+}/order/mq")
    public ResponseData userOrderedMq(@PathVariable("userId") Long userId, @RequestBody List<Long> goodsIdList) {
        businessService.userPostOrderMq(userId, goodsIdList);
        return new ResponseData();
    }
}
