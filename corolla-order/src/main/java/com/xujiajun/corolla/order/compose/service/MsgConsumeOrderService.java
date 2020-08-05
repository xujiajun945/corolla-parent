package com.xujiajun.corolla.order.compose.service;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
public interface MsgConsumeOrderService {

	/**
	 * 查询订单消息是否已被消费过
	 *
	 * @param orderMsgId 订单消息id
	 * @return true: 已经被消费  false: 未被消费
	 */
	boolean checkOrderMsgHasConsumed(Long orderMsgId);
}
