package com.xujiajun.corolla.order.compose.service.impl;

import com.xujiajun.corolla.order.compose.service.MsgConsumeOrderService;
import com.xujiajun.corolla.order.dal.dao.MsgConsumeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
@Service
public class MsgConsumeOrderServiceImpl implements MsgConsumeOrderService {

	@Autowired
	private MsgConsumeOrderMapper msgConsumeOrderMapper;

	@Override
	public boolean checkOrderMsgHasConsumed(Long orderMsgId) {
		Integer total = msgConsumeOrderMapper.countOrderMsgId(orderMsgId);
		return total > 0;
	}
}
