package com.xujiajun.corolla.base.compose.service.impl;

import com.xujiajun.corolla.base.compose.service.UserService;
import com.xujiajun.corolla.base.dal.dao.UserMapper;
import com.xujiajun.corolla.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserById(Long userId) {
		return userMapper.getById(userId);
	}
}
