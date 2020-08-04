package com.xujiajun.corolla.score.compose.service.impl;

import com.xujiajun.core.constant.BaseResponseCode;
import com.xujiajun.core.exception.ResponseException;
import com.xujiajun.corolla.model.Goods;
import com.xujiajun.corolla.model.User;
import com.xujiajun.corolla.score.compose.service.UserService;
import com.xujiajun.corolla.score.dal.dao.GoodsMapper;
import com.xujiajun.corolla.score.dal.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private GoodsMapper goodsMapper;

	@Override
	public User getUserById(Long userId) {
		return userMapper.getById(userId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void modifyUserAccount(Long userId, List<Long> goodsIdList) {
		User user = userMapper.getById(userId);
		if (user == null) {
			throw new ResponseException(BaseResponseCode.BAD_REQUEST);
		}
		// 汇总商品的总价
		BigDecimal total = goodsMapper.listByIds(goodsIdList)
			.stream()
			.map(Goods::getPrice)
			.reduce(BigDecimal::add)
			.orElse(BigDecimal.ZERO);
		// 用户减少余额
		User updater = new User();
		updater.setId(userId);
		updater.setAccount(user.getAccount().subtract(total));
		userMapper.updateById(updater);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void modifyScore(Long userId, List<Long> goodsIdList) {
		User user = userMapper.getById(userId);
		if (user == null) {
			throw new ResponseException(BaseResponseCode.BAD_REQUEST);
		}
		// 汇总用户应该添加的积分
		BigDecimal totalToAdd = goodsMapper.listByIds(goodsIdList)
			.stream()
			.map(Goods::getScore)
			.reduce(BigDecimal::add)
			.orElse(BigDecimal.ZERO);
		// 用户添加积分
		User updater = new User();
		updater.setId(userId);
		updater.setScore(user.getScore().add(totalToAdd));
		userMapper.updateById(updater);
	}
}
