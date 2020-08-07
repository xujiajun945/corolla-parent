package com.xujiajun.corolla.score.rocketmq;

import com.xujiajun.corolla.message.OrderMessage;
import com.xujiajun.corolla.score.compose.service.GoodsService;
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
public class OrderGoodsListener implements MessageListenerConcurrently {

	@Autowired
	private GoodsService goodsService;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext context) {
		for (MessageExt messageExt : messageExtList) {
			byte[] body = messageExt.getBody();
			log.info("接受到消息 topic: {} tags: {} msg: {}", messageExt.getTopic(), messageExt.getTags(), new String(body));
			OrderMessage orderMessage = JacksonUtils.parseObject(new String(body), OrderMessage.class);
			Long orderMsgId = orderMessage.getOrderMsgId();
			List<Long> goodsIdList = orderMessage.getGoodsIdList();
			goodsService.modifyGoodsStockMq(orderMsgId, goodsIdList);
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
}
