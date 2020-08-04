package com.xujiajun.corolla.base.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消息消费者
 *
 * @author xujiajun
 * @since 2020/7/29
 */
public class Consumer {

	public static void main(String[] args) throws Exception {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
		consumer.setNamesrvAddr("192.168.18.128:9876");
		// 订阅一个或多个主题(topic)来消费
		consumer.subscribe("Topic1", "*");
		// 注册一个callback来执行从brokers抓取到的消息
		consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
				System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), list);
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		// 启动消费者实例
		consumer.start();
		System.out.printf("Consumer Started.%n");
	}
}
