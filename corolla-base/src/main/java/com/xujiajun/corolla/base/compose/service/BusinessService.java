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
}
