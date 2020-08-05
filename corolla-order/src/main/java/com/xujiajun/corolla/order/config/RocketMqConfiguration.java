package com.xujiajun.corolla.order.config;

import com.xujiajun.corolla.message.OrderMessage;
import com.xujiajun.corolla.order.compose.service.MsgConsumeOrderService;
import com.xujiajun.corolla.order.compose.service.OrderService;
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

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
@Configuration
@ConditionalOnBean(value = {RocketMqProperties.class})
public class RocketMqConfiguration {

	@Autowired
	private MsgConsumeOrderService msgConsumeOrderService;

	@Autowired
	private OrderService orderService;

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
				OrderMessage orderMessage = JacksonUtils.parseObject(new String(body), OrderMessage.class);
				Long orderMsgId = orderMessage.getOrderMsgId();
				Long userId = orderMessage.getUserId();
				List<Long> goodsIdList = orderMessage.getGoodsIdList();
				// 校验消息是否被消费过
				boolean flag = msgConsumeOrderService.checkOrderMsgHasConsumed(orderMsgId);
				if (!flag) {
					// 创建一个订单
					orderService.createOrderMq(orderMsgId, userId, goodsIdList);
				}
				// todo xujiajun 已被消费过, 需要返回一个状态
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
		pushConsumer.start();
		return pushConsumer;
	}
}
