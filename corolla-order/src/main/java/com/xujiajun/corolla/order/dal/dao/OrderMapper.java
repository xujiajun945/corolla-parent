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

	/**
	 * 根据订单消息移除
	 *
	 * @param orderMsgId 订单消息id
	 */
	void removeByOrderMsgId(Long orderMsgId);

	/**
	 * 根据订单消息id查询
	 *
	 * @param orderMsgId 订单消息id
	 * @return 订单信息
	 */
	Order getByOrderMsgId(Long orderMsgId);
}
