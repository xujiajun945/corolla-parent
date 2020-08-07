package com.xujiajun.corolla.base.compose.manager;

import com.xujiajun.corolla.base.dal.dao.UserMapper;
import com.xujiajun.corolla.base.feign.GoodsClient;
import com.xujiajun.corolla.exception.CorollaException;
import com.xujiajun.corolla.model.Goods;
import com.xujiajun.corolla.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
@Component
public class UserManager {

	@Autowired
	private GoodsClient goodsClient;

	@Autowired
	private UserMapper userMapper;

	public void modifyUserAccountAndScore(Long userId, List<Long> goodsIdList) {
		User user = userMapper.getById(userId);
		List<Goods> goodsList = goodsClient.listByIds(goodsIdList);
		BigDecimal totalPrice = goodsList.stream()
			.map(Goods::getPrice)
			.reduce(BigDecimal::add)
			.orElse(BigDecimal.ZERO);
		// 判断用户余额是否足够
		if (user.getAccount().compareTo(totalPrice) < 0) {
			throw new CorollaException(500, "余额不足, 下单失败!");
		}
		BigDecimal totalScore = goodsList.stream()
			.map(Goods::getScore)
			.reduce(BigDecimal::add)
			.orElse(BigDecimal.ZERO);
		User updater = new User();
		updater.setId(userId);
		updater.setAccount(user.getAccount().subtract(totalPrice));
		updater.setScore(user.getScore().add(totalScore));
		userMapper.updateById(updater);
	}

	public void unModifyUserAccountAndScore(Long userId, List<Long> goodsIdList) {
		User user = userMapper.getById(userId);
		List<Goods> goodsList = goodsClient.listByIds(goodsIdList);
		BigDecimal totalPrice = goodsList.stream()
			.map(Goods::getPrice)
			.reduce(BigDecimal::add)
			.orElse(BigDecimal.ZERO);
		BigDecimal totalScore = goodsList.stream()
			.map(Goods::getScore)
			.reduce(BigDecimal::add)
			.orElse(BigDecimal.ZERO);
		User updater = new User();
		updater.setId(userId);
		updater.setAccount(user.getAccount().add(totalPrice));
		updater.setScore(user.getScore().subtract(totalScore));
		userMapper.updateById(updater);
	}

}
