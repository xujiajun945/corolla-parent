package com.xujiajun.corolla.base.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 单向消息生产
 * 应用于中等可靠性的场景, 例如: 日志集合
 *
 * @author xujiajun
 * @since 2020/7/29
 */
public class OneWayProducer {

	public static void main(String[] args) throws Exception {
		DefaultMQProducer producer = new DefaultMQProducer("group1");
		producer.setNamesrvAddr("localhost:9876");
		producer.start();
		for (int i = 0; i < 100; i++) {
			String messageBody = "Hello RocketMQ " + i;
			Message msg = new Message("Topic1", "Tag1", messageBody.getBytes(RemotingHelper.DEFAULT_CHARSET));
			producer.sendOneway(msg);
		}
		Thread.sleep(5000);
		producer.shutdown();
	}
}
