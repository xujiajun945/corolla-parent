package com.xujiajun.corolla.order.dal.dao;

import com.xujiajun.corolla.model.OrderGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
public interface OrderGoodsMapper {

	/**
	 * 批量插入
	 *
	 * @param orderGoodsList 订单-商品信息列表
	 */
	void batchInsert(@Param("orderGoodsList") List<OrderGoods> orderGoodsList);
}
