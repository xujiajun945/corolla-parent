package com.xujiajun.corolla.score.compose.service.impl;

import com.xujiajun.corolla.score.compose.service.MsgConsumeGoodsService;
import com.xujiajun.corolla.score.dal.dao.MsgConsumeGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
@Service
public class MsgConsumeGoodsServiceImpl implements MsgConsumeGoodsService {

	@Autowired
	private MsgConsumeGoodsMapper msgConsumeGoodsMapper;

	@Override
	public boolean checkOrderMsgHasConsumed(Long orderMsgId) {
		Integer total = msgConsumeGoodsMapper.countByOrderMsgId(orderMsgId);
		return total > 0;
	}
}
