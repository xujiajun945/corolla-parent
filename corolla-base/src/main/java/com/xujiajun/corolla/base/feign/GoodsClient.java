package com.xujiajun.corolla.base.feign;

import com.xujiajun.corolla.model.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@FeignClient(value = "corolla-score")
public interface GoodsClient {

	/**
	 * 修改商品库存
	 *
	 * @param goodsIdList 商品id集合
	 */
	@PutMapping(value = "/goods/modify_stock")
	void modifyGoodsStock(@RequestBody List<Long> goodsIdList);

	/**
	 * 根据id批量查询商品
	 *
	 * @param goodsIdList 商品id集合
	 * @return 商品集合
	 */
	@GetMapping(value = "/goods")
	List<Goods> listByIds(@RequestParam("goodsIdList") List<Long> goodsIdList);
}
