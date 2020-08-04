package com.xujiajun.corolla.base.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
