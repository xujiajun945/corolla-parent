package com.xujiajun.corolla.order.compose.service;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param userId 用户id
     * @param goodsIdList 用户选择的商品id集合
     */
    void createOrder(Long userId, List<Long> goodsIdList);
}
