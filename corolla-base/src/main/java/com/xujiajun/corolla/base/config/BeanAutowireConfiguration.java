package com.xujiajun.corolla.base.config;

import com.xujiajun.corolla.base.rocketmq.ReturnMessageListener;
import com.xujiajun.corolla.constant.MqTagEnum;
import com.xujiajun.corolla.util.MqProducerClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xujiajun
 * @since 2020/8/7
 */
@Configuration
@Slf4j
public class BeanAutowireConfiguration {

	@Autowired
	private RocketMqProperties properties;

	@Bean(value = "mqProducerClient", destroyMethod = "shutdown")
	public MqProducerClient mqProducerClient() throws MQClientException {
		DefaultMQProducer producer = new DefaultMQProducer(properties.getProducerGroup());
		producer.setNamesrvAddr(properties.getNamesrv());
		producer.start();
		MqProducerClient mqProducerClient = new MqProducerClient(properties.getTopic(), producer);
		log.info("实例化producer成功! group: {} namesrv: {} topic: {}", properties.getProducerGroup(), properties.getNamesrv(), properties.getTopic());
		return mqProducerClient;
	}

	@Bean(value = "returnMessageConsumer", destroyMethod = "shutdown")
	public DefaultMQPushConsumer returnMessageConsumer(@Qualifier("returnMessageListener") ReturnMessageListener returnMessageListener) {
		DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(properties.getConsumerGroup());
		pushConsumer.setNamesrvAddr(properties.getNamesrv());
		pushConsumer.setMessageModel(MessageModel.BROADCASTING);
		String tags = MqTagEnum.GOODS_RETURN.getTag() + " || " + MqTagEnum.ORDER_RETURN.getTag();
		try {
			pushConsumer.subscribe(properties.getTopic(), "*");
			pushConsumer.registerMessageListener(returnMessageListener);
			pushConsumer.start();
		} catch (MQClientException e) {
			log.error("创建消费者监听失败! tag: {}", tags, e);
		}
		log.info("创建消费者监听成功! tag: {}", tags);
		return pushConsumer;
	}

}
