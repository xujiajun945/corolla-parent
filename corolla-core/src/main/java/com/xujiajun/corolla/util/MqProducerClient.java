package com.xujiajun.corolla.util;

import com.xujiajun.corolla.exception.MqException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author xujiajun
 * @since 2020/8/7
 */
@AllArgsConstructor
@Slf4j
public class MqProducerClient {

	private String topic;

	private DefaultMQProducer producer;

	public SendResult send(Message message) {
		SendResult sendResult;
		try {
			sendResult = producer.send(message);
		} catch (MQClientException | InterruptedException | RemotingException | MQBrokerException e) {
			throw new MqException("消息发送失败!", e);
		}
		log.info("消息发送成功! topic: {} tags: {} msg: {}", message.getTopic(), message.getTags(), new String(message.getBody()));
		return sendResult;
	}

	public SendResult send(String tags, String message) {
		Message msg = this.createMessage(tags, message);
		return this.send(msg);
	}

	public void sendOneway(Message message) {
		try {
			producer.sendOneway(message);
		} catch (MQClientException | RemotingException | InterruptedException e) {
			throw new MqException("消息发送失败!", e);
		}
	}

	public void sendOneway(String tags, String message) {
		Message msg = this.createMessage(tags, message);
		this.sendOneway(msg);
	}

	private Message createMessage(String tags, String message) {
		return new Message(topic, tags, message.getBytes());
	}

	public void shutdown() {
		producer.shutdown();
		log.info("MqProducerClient 实例正在被销毁...");
	}

}
