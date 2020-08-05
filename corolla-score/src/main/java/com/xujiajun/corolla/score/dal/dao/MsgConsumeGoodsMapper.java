package com.xujiajun.corolla.score.dal.dao;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
public interface MsgConsumeGoodsMapper {

	/**
	 * 根据订单消息id查询条数
	 *
	 * @param orderMsgId 订单消息id
	 * @return 条数
	 */
	Integer countByOrderMsgId(Long orderMsgId);
}
