package com.xujiajun.corolla.score.controler;

import com.xujiajun.core.entity.ResponseData;
import com.xujiajun.corolla.model.User;
import com.xujiajun.corolla.score.compose.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	/**
	 * 修改用户账户余额
	 *
	 * @param userId 用户id
	 * @param goodsIdList 用户选择的商品id集合
	 * @return 通用状态
	 */
	@PutMapping(value = "/{userId:\\d+}/account_modify")
	public ResponseData modifyUserAccount(@PathVariable Long userId, @RequestBody List<Long> goodsIdList) {
		userService.modifyUserAccount(userId, goodsIdList);
		return new ResponseData();
	}

	/**
	 * 修改用户积分
	 *
	 * @param userId 用户id
	 * @param goodsIdList 用户选择的商品id集合
	 * @return 通用状态
	 */
	@PutMapping(value = "/{userId:\\d+}/score_modify")
	public ResponseData modifyUserScore(@PathVariable Long userId, @RequestBody List<Long> goodsIdList) {
		userService.modifyScore(userId, goodsIdList);
		return new ResponseData();
	}
}
