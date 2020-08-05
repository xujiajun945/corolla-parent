package com.xujiajun.corolla.base.controller;

import com.xujiajun.core.entity.ResponseData;
import com.xujiajun.corolla.base.compose.service.UserService;
import com.xujiajun.corolla.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/{userId:\\d+}")
	public ResponseData getUser(@PathVariable Long userId) {
		User user = userService.getUserById(userId);
		return new ResponseData(user);
	}

}
