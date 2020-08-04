package com.xujiajun.corolla.score.compose.service.impl;

import com.xujiajun.corolla.model.Goods;
import com.xujiajun.corolla.score.compose.service.GoodsService;
import com.xujiajun.corolla.score.dal.dao.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void modifyGoodsStock(List<Long> goodsIdList) {
		List<Goods> goodsList = goodsMapper.listByIds(goodsIdList);
		for (Goods goods : goodsList) {
			Goods updater = new Goods();
			updater.setId(goods.getId());
			updater.setStock(goods.getStock() - 1);
			goodsMapper.updateById(updater);
		}
	}
}
