package com.xujiajun.corolla.score.compose.service;

import com.xujiajun.corolla.model.Goods;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
public interface GoodsService {

	/**
	 * 修改商品库存
	 *
	 * @param goodsIdList 商品id集合
	 */
	void modifyGoodsStock(List<Long> goodsIdList);

	/**
	 * 修改商品库存 mq方式
	 *
	 * @param orderMsgId 订单消息id
	 * @param goodsIdList 商品id集合
	 */
	void modifyGoodsStockMq(Long orderMsgId, List<Long> goodsIdList);

	/**
	 * 根据id批量获取
	 *
	 * @param goodsIdList 商品id集合
	 * @return 商品集合
	 */
	List<Goods> listByIds(List<Long> goodsIdList);
}
