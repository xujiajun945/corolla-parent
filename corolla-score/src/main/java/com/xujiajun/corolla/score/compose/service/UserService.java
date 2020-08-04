package com.xujiajun.corolla.score.compose.service;

import com.xujiajun.corolla.model.User;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
public interface UserService {

	/**
	 * 根据id查询用户信息
	 *
	 * @param userId 用户id
	 * @return 用户信息
	 */
	User getUserById(Long userId);

	/**
	 * 修改用户余额
	 *
	 * @param userId 用户id
	 * @param goodsIdList 商品id集合
	 */
	void modifyUserAccount(Long userId, List<Long> goodsIdList);

	/**
	 * 修改用户积分
	 *
	 * @param userId 用户id
	 * @param goodsIdList 商品id列表
	 */
	void modifyScore(Long userId, List<Long> goodsIdList);

}
