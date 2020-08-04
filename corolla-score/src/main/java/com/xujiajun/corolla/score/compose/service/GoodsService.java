package com.xujiajun.corolla.score.compose.service;

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
}
