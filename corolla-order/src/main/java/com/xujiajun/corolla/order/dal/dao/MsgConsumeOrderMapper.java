package com.xujiajun.corolla.order.dal.dao;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
public interface MsgConsumeOrderMapper {

	/**
	 * 根据订单消息id查询存在的条数
	 *
	 * @param orderMsgId 订单消息id
	 * @return 存在的条数
	 */
	Integer countOrderMsgId(Long orderMsgId);
}
