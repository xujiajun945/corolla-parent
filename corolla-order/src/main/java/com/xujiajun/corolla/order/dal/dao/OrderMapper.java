package com.xujiajun.corolla.order.dal.dao;

import com.xujiajun.corolla.model.Order;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
public interface OrderMapper {

    /**
     * 添加记录
     *
     * @param order 订单信息
     */
    void insert(Order order);
}
