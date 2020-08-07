package com.xujiajun.corolla.base.rocketmq;

import com.xujiajun.corolla.base.compose.service.BusinessService;
import com.xujiajun.corolla.constant.MqTagEnum;
import com.xujiajun.corolla.message.ReturnMessage;
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
public class ReturnMessageListener implements MessageListenerConcurrently {

	@Autowired
	private BusinessService businessService;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext context) {
		for (MessageExt messageExt : messageExtList) {
			String tags = messageExt.getTags();
			if (tags.equals(MqTagEnum.GOODS_RETURN.getTag()) || tags.equals(MqTagEnum.ORDER_RETURN.getTag())) {
				byte[] body = messageExt.getBody();
				log.info("接受到消息 topic: {} tags: {} msg: {}", messageExt.getTopic(), tags, new String(body));
				ReturnMessage returnMessage = JacksonUtils.parseObject(new String(body), ReturnMessage.class);
				Integer status = returnMessage.getStatus();
				Long orderMsgId = returnMessage.getOrderMsgId();
				businessService.updateOrderMsgStatus(tags, orderMsgId, status);
			}
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
}
