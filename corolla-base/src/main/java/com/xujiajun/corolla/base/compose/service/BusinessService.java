package com.xujiajun.corolla.base.compose.service;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
public interface BusinessService {

    /**
     * 创建订单
     *
     * @param userId 用户id
     * @param goodsIdList 用户选择的商品id集合
     */
    void userPostOrder(Long userId, List<Long> goodsIdList);

	/**
	 * 创建订单, 使用mq保证分布式事务
	 *
	 * @param userId 用户id
	 * @param goodsIdList 用户选择的商品id集合
	 */
	void userPostOrderMq(Long userId, List<Long> goodsIdList);

	/**
	 * 更新消息消费的状态
	 *
	 * @param tags 当前消息的tags
	 * @param orderMsgId 订单消息id
	 * @param status 消息状态
	 */
	void updateOrderMsgStatus(String tags, Long orderMsgId, Integer status);
}
