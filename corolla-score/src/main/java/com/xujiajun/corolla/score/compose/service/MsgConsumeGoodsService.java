package com.xujiajun.corolla.score.compose.service;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
public interface MsgConsumeGoodsService {

	/**
	 * 查看订单消息是否被消费过
	 *
	 * @param orderMsgId 订单消息id
	 * @return true: 已被消费过  false: 未被消费过
	 */
	boolean checkOrderMsgHasConsumed(Long orderMsgId);
}
