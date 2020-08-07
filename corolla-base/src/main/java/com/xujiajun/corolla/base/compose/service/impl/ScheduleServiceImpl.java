package com.xujiajun.corolla.base.compose.service.impl;

import com.xujiajun.corolla.base.compose.service.ScheduleService;
import com.xujiajun.corolla.base.dal.dao.OrderMsgMapper;
import com.xujiajun.corolla.constant.MqTagEnum;
import com.xujiajun.corolla.model.OrderMsg;
import com.xujiajun.corolla.util.LogUtils;
import com.xujiajun.corolla.util.MqProducerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/6
 */
@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private OrderMsgMapper orderMsgMapper;

	@Autowired
	private MqProducerClient producerClient;

	@Autowired
	private ScheduleService _this;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doSchedule() {
		// 查询所有的记录
		List<OrderMsg> orderMsgList = orderMsgMapper.listAllEnabled();
		// 发送消息
		for (OrderMsg orderMsg : orderMsgList) {
			try {
				_this.sendMessage(orderMsg);
			} catch (Exception e) {
				LogUtils.auto(log, "消息发送失败, id: " + orderMsg.getId(), e);
			}
		}
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
	@Override
	public void sendMessage(OrderMsg orderMsg) {
		String content = orderMsg.getContent();
		producerClient.send(MqTagEnum.USER_ORDERED.getTag(), content);
		orderMsgMapper.removeById(orderMsg.getId());
	}

}
