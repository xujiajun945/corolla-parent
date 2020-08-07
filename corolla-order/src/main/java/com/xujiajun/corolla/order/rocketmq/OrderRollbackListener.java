package com.xujiajun.corolla.order.rocketmq;

import com.xujiajun.corolla.message.RollbackMessage;
import com.xujiajun.corolla.order.compose.service.OrderService;
import com.xujiajun.corolla.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/7
 */
@Component
@Slf4j
public class OrderRollbackListener implements MessageListenerConcurrently {

	@Autowired
	private OrderService orderService;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext context) {
		for (MessageExt messageExt : messageExtList) {
			byte[] body = messageExt.getBody();
			log.info("接受到消息 topic: {} tags: {} msg: {}", messageExt.getTopic(), messageExt.getTags(), new String(body));
			RollbackMessage rollbackMessage = JacksonUtils.parseObject(new String(body), RollbackMessage.class);
			orderService.removeOrderMq(rollbackMessage.getOrderMsgId());
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
}
