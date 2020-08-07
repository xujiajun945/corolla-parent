package com.xujiajun.corolla.score.dal.dao;

import com.xujiajun.corolla.model.MsgConsumeGoods;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
public interface MsgConsumeGoodsMapper {

	/**
	 * 根据订单消息id查询
	 *
	 * @param orderMsgId 订单消息id
	 * @return 消费状态
	 */
	MsgConsumeGoods getByOrderMsgId(Long orderMsgId);

	/**
	 * 插入
	 *
	 * @param msgConsumeGoods 消费状态
	 */
	void insert(MsgConsumeGoods msgConsumeGoods);
}
