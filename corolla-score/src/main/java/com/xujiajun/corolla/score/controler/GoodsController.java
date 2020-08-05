package com.xujiajun.corolla.score.controler;

import com.xujiajun.core.entity.ResponseData;
import com.xujiajun.corolla.model.Goods;
import com.xujiajun.corolla.score.compose.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@RestController
@RequestMapping(value = "/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;

	/**
	 * 修改商品库存
	 *
	 * @param goodsIdList 商品id集合
	 * @return 通用返回状态
	 */
	@PutMapping(value = "/modify_stock")
	public ResponseData modifyGoodsStock(@RequestBody List<Long> goodsIdList) {
		goodsService.modifyGoodsStock(goodsIdList);
		return new ResponseData();
	}

	/**
	 * 批量查询
	 *
	 * @param goodsIdList 商品id集合
	 * @return 商品列表
	 */
	@GetMapping
	public ResponseData listByIds(@RequestParam List<Long> goodsIdList) {
		List<Goods> goodsList = goodsService.listByIds(goodsIdList);
		return new ResponseData(goodsList);
	}
}
