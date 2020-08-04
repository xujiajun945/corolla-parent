package com.xujiajun.corolla.base.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@FeignClient(value = "corolla-order")
public interface OrderClient {

    /**
     * 创建订单
     *
     * @param userId 用户id
     * @param goodsIdList 商品id集合
     */
    @PostMapping(value = "/order/{userId}")
    void createOrder(@PathVariable("userId") Long userId, @RequestBody List<Long> goodsIdList);
}
