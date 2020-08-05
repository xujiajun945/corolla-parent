package com.xujiajun.corolla.base.dal.dao;

import com.xujiajun.corolla.model.User;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
public interface UserMapper {

    /**
     * 通过主键查询
     *
     * @param id id
     * @return 用户信息
     */
    User getById(Long id);

	/**
	 * 根据id更新
	 *
	 * @param user 要更新的用户信息
	 */
	void updateById(User user);
}
