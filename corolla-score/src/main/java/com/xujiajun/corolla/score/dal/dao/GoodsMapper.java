package com.xujiajun.corolla.score.dal.dao;

import com.xujiajun.corolla.model.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
public interface GoodsMapper {

	/**
	 * 根据id集合批量查询
	 *
	 * @param goodsIdList 商品id集合
	 * @return 批量信息
	 */
	List<Goods> listByIds(@Param("goodsIdList") List<Long> goodsIdList);

	/**
	 * 根据id更新
	 *
	 * @param goods 商品信息
	 */
	void updateById(Goods goods);
}
