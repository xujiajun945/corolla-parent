package com.xujiajun.corolla.score.config;

import com.xujiajun.corolla.message.OrderMessage;
import com.xujiajun.corolla.score.compose.service.GoodsService;
import com.xujiajun.corolla.score.compose.service.MsgConsumeGoodsService;
import com.xujiajun.corolla.util.JacksonUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
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
	private MsgConsumeGoodsService msgConsumeGoodsService;

	@Autowired
	private GoodsService goodsService;

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
				List<Long> goodsIdList = orderMessage.getGoodsIdList();
				// 查询当前消息是否被消费过
				boolean flag = msgConsumeGoodsService.checkOrderMsgHasConsumed(orderMsgId);
				if (!flag) {
					// 商品减库存
					goodsService.modifyGoodsStockMq(orderMsgId, goodsIdList);
				}
				// todo xujiajun
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});
		pushConsumer.start();
		return pushConsumer;
	}
}
