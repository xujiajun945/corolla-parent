package com.xujiajun.corolla.base.config;

import com.xujiajun.corolla.base.dal.dao.OrderMsgMapper;
import com.xujiajun.corolla.model.OrderMsg;
import com.xujiajun.corolla.util.JacksonUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
@Configuration
@ConditionalOnBean(value = {RocketMqProperties.class})
public class RocketMqConfiguration {

	@Autowired
	private OrderMsgMapper orderMsgMapper;

	@Bean(destroyMethod = "shutdown")
	public DefaultMQProducer producer(@Qualifier("rocketMqProperties") RocketMqProperties properties) throws MQClientException {
		DefaultMQProducer producer = new DefaultMQProducer(properties.getGroup());
		producer.setNamesrvAddr(properties.getNamesrv());
		producer.start();
		return producer;
	}

	@Bean(destroyMethod = "shutdown")
	public DefaultMQPushConsumer pushConsumer(@Qualifier("rocketMqProperties") RocketMqProperties properties) throws MQClientException {
		DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(properties.getGroup());
		pushConsumer.setNamesrvAddr(properties.getNamesrv());
		pushConsumer.subscribe(properties.getTopic(), "*");
		pushConsumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
			for (MessageExt messageExt : list) {
				String tags = messageExt.getTags();
				byte[] body = messageExt.getBody();
				String json = new String(body);
				Map<String, Object> data = JacksonUtils.parse(json, Map.class, String.class, Object.class);
				Integer status = (Integer) data.get("status");
				Long orderMsgId = (Long) data.get("orderMsgId");
				OrderMsg orderMsg = new OrderMsg();
				orderMsg.setId(orderMsgId);
				if ("OrderReturn".equals(tags)) {
					orderMsg.setOrderReturn(status);
					orderMsgMapper.updateById(orderMsg);
				}
				if ("GoodsReturn".equals(tags)) {
					orderMsg.setGoodsReturn(status);
					orderMsgMapper.updateById(orderMsg);
					if (status == 2) {
						// 补偿 todo xujiajun
					}
				}
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
		pushConsumer.start();
		return pushConsumer;
	}

}
