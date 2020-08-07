package com.xujiajun.corolla.order.dal.dao;

import com.xujiajun.corolla.model.MsgConsumeOrder;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
public interface MsgConsumeOrderMapper {

	/**
	 * 根据订单消息id
	 *
	 * @param orderMsgId 订单消息id
	 * @return 消费状态
	 */
	MsgConsumeOrder getByOrderMsgId(Long orderMsgId);

	/**
	 * 插入
	 *
	 * @param msgConsumeOrder 消费状态
	 */
	void insert(MsgConsumeOrder msgConsumeOrder);
}
