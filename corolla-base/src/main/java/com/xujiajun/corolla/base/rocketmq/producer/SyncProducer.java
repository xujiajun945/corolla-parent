package com.xujiajun.corolla.base.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 同步生产消息
 * 大量应用场景, 例如: 重要提示消息, 短信消息等
 *
 * @author xujiajun
 * @since 2020/7/29
 */
public class SyncProducer {

	public static void main(String[] args) throws Exception {
		// 使用一个生产者组名来创建生产者
		DefaultMQProducer producer = new DefaultMQProducer("group1");
		// 声明name server的地址
		producer.setNamesrvAddr("localhost:9876");
		// 启动实例
		producer.start();
		for (int i = 0; i < 100; i++) {
			// 创建一个消息实例, 声明topic, tag, message body
			String messageBody = "Hello RocketMQ " + i;
			Message msg = new Message("Topic1", "Tag1", messageBody.getBytes(RemotingHelper.DEFAULT_CHARSET));
			// 发送消息
			SendResult sendResult = producer.send(msg);
			System.out.printf("%s%n", sendResult);
		}
		producer.shutdown();
	}
}
