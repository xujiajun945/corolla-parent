package com.xujiajun.corolla.base.compose.service;

import com.xujiajun.corolla.model.User;

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

}
