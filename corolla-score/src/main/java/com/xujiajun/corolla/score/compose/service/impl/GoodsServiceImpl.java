package com.xujiajun.corolla.score.compose.service.impl;

import com.xujiajun.corolla.model.Goods;
import com.xujiajun.corolla.score.compose.service.GoodsService;
import com.xujiajun.corolla.score.config.RocketMqProperties;
import com.xujiajun.corolla.score.dal.dao.GoodsMapper;
import com.xujiajun.corolla.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private DefaultMQProducer producer;

	@Autowired
	private RocketMqProperties properties;

	@Autowired
	private GoodsMapper goodsMapper;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void modifyGoodsStock(List<Long> goodsIdList) {
		List<Goods> goodsList = goodsMapper.listByIds(goodsIdList);
		for (Goods goods : goodsList) {
			Goods updater = new Goods();
			updater.setId(goods.getId());
			updater.setStock(goods.getStock() - 1);
			goodsMapper.updateById(updater);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void modifyGoodsStockMq(Long orderMsgId, List<Long> goodsIdList) {
		// 修改库存完成, 发送消息给base服务
		Map<String, Object> data = new HashMap<>(2);
		data.put("status", 1);
		data.put("orderMsgId", orderMsgId);
		try {
			this.modifyGoodsStock(goodsIdList);
		} catch (Exception e) {
			log.error("业务异常, 触发回滚补偿");
			data.put("status", 2);
		}
		Message message = new Message(properties.getTopic(), "GoodsReturn", JacksonUtils.toJson(data).getBytes());
		try {
			producer.send(message);
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			log.error("消息发送失败!", e);
			throw new RuntimeException("消息发送失败!");
		}
	}

	@Override
	public List<Goods> listByIds(List<Long> goodsIdList) {
		return goodsMapper.listByIds(goodsIdList);
	}
}
