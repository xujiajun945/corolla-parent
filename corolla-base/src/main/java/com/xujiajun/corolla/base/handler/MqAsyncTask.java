package com.xujiajun.corolla.base.handler;

import com.xujiajun.corolla.base.config.RocketMqProperties;
import com.xujiajun.corolla.base.dal.dao.OrderMsgMapper;
import com.xujiajun.corolla.model.OrderMsg;
import com.xujiajun.corolla.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
@Component
@EnableScheduling
@Slf4j
public class MqAsyncTask {

	@Autowired
	private MqAsyncTask _this;

	@Autowired
	private OrderMsgMapper orderMsgMapper;

	@Autowired
	private DefaultMQProducer producer;

	@Autowired
	private RocketMqProperties properties;

	@Async
	@Scheduled(cron = "0/3 * * * * *")
	@Transactional(rollbackFor = Exception.class)
	public void clearSchedule() {
		// 查询所有的记录
		List<OrderMsg> orderMsgList = orderMsgMapper.listAllEnabled();
		// 发送消息
		for (OrderMsg orderMsg : orderMsgList) {
			try {
				_this.sendMessage(orderMsg);
			} catch (RuntimeException e) {
				LogUtils.auto(log, "消息发送失败, id: " + orderMsg.getId(), e);
			}
		}
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
	public void sendMessage(OrderMsg orderMsg) {
		String content = orderMsg.getContent();
		Message message = new Message(properties.getTopic(), properties.getTags(), content.getBytes());
		try {
			producer.send(message);
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			LogUtils.auto(log, "消息发送失败!", e);
			throw new RuntimeException("消息发送失败!");
		}
		orderMsgMapper.removeById(orderMsg.getId());
	}

}
