package com.xujiajun.corolla.base.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@FeignClient(value = "corolla-score")
public interface UserClient {

	/**
	 * 修改用户的余额
	 *
	 * @param userId 用户id
	 * @param goodsIdList 商品id集合
	 */
	@PutMapping(value = "/user/{userId}/account_modify")
	void modifyUserAccount(@PathVariable("userId") Long userId, @RequestBody List<Long> goodsIdList);

	/**
	 * 修改用户的积分
	 *
	 * @param userId 用户id
	 * @param goodsIdList 商品id集合
	 */
	@PutMapping(value = "/user/{userId}/score_modify")
	void modifyUserScore(@PathVariable("userId") Long userId, @RequestBody List<Long> goodsIdList);
}
