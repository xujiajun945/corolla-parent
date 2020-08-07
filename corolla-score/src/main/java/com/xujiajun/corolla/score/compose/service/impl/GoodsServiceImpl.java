package com.xujiajun.corolla.score.compose.service.impl;

import com.xujiajun.corolla.constant.MessageStatusEnum;
import com.xujiajun.corolla.constant.MqTagEnum;
import com.xujiajun.corolla.message.ReturnMessage;
import com.xujiajun.corolla.model.Goods;
import com.xujiajun.corolla.model.MsgConsumeGoods;
import com.xujiajun.corolla.score.compose.service.GoodsService;
import com.xujiajun.corolla.score.dal.dao.GoodsMapper;
import com.xujiajun.corolla.score.dal.dao.MsgConsumeGoodsMapper;
import com.xujiajun.corolla.util.JacksonUtils;
import com.xujiajun.corolla.util.MqProducerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private MqProducerClient producer;

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private MsgConsumeGoodsMapper msgConsumeGoodsMapper;

	@Autowired
	private GoodsService _this;

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
	@Override
	public void modifyGoodsStock(List<Long> goodsIdList) {
		List<Goods> goodsList = goodsMapper.listByIds(goodsIdList);
		for (Goods goods : goodsList) {
			Long stock = goods.getStock();
			if (stock <= 0) {
				throw new RuntimeException("商品:" + goods.getName() + " 库存不足, 下单失败!");
			}
			Goods updater = new Goods();
			updater.setId(goods.getId());
			updater.setStock(goods.getStock() - 1);
			goodsMapper.updateById(updater);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void modifyGoodsStockMq(Long orderMsgId, List<Long> goodsIdList) {
		Date current = new Date(System.currentTimeMillis());

		ReturnMessage returnMessage = new ReturnMessage();
		returnMessage.setOrderMsgId(orderMsgId);
		// 查询当前消息是否被消费过
		MsgConsumeGoods msgConsumeGoods = msgConsumeGoodsMapper.getByOrderMsgId(orderMsgId);
		int status = MessageStatusEnum.OK.getStatus();
		if (msgConsumeGoods == null) {
			try {
				self().modifyGoodsStock(goodsIdList);
			} catch (Exception e) {
				log.error("业务异常, 触发回滚补偿");
				status = MessageStatusEnum.FAILED.getStatus();
			} finally {
				// 记录操作
				MsgConsumeGoods updater = new MsgConsumeGoods();
				updater.setOrderMsgId(orderMsgId);
				updater.setStatus(status);
				updater.setCreateTime(current);
				updater.setUpdateTime(current);
				msgConsumeGoodsMapper.insert(updater);
			}
		} else {
			// 已被消费过, 需要返回一个状态
			status = msgConsumeGoods.getStatus();
		}
		returnMessage.setStatus(status);
		producer.send(MqTagEnum.GOODS_RETURN.getTag(), JacksonUtils.toJson(returnMessage));
	}

	@Override
	public List<Goods> listByIds(List<Long> goodsIdList) {
		return goodsMapper.listByIds(goodsIdList);
	}

	private GoodsService self() {
		return (GoodsService) AopContext.currentProxy();
	}
}
