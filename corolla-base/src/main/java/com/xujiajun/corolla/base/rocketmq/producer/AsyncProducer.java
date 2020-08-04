package com.xujiajun.corolla.base.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 异步消息生产
 * 应用于对响应时间敏感的场景, 例如业务场景
 *
 * @author xujiajun
 * @since 2020/7/29
 */
public class AsyncProducer {

	public static void main(String[] args) throws Exception {
		DefaultMQProducer producer = new DefaultMQProducer("group1");
		producer.setNamesrvAddr("192.168.18.128:9876");
		producer.start();
		// 设置异步发送消息失败后重试的时间
		producer.setRetryTimesWhenSendAsyncFailed(0);

		int messageCount = 100;
		final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
		for (int i = 0; i < messageCount; i++) {
			try {
				final int index = i;
				String messageBody = "Hello world";
				Message msg = new Message("Topic1", "Tag1", "OrderId188", messageBody.getBytes(RemotingHelper.DEFAULT_CHARSET));
				producer.send(msg, new SendCallback() {

					@Override
					public void onSuccess(SendResult sendResult) {
						countDownLatch.countDown();
						System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
					}

					@Override
					public void onException(Throwable throwable) {
						countDownLatch.countDown();
						System.out.printf("%-10d Exception %s %n", index, throwable);
						throwable.printStackTrace();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		countDownLatch.await(5, TimeUnit.SECONDS);
		producer.shutdown();
	}
}
