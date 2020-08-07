package com.xujiajun.corolla.order.compose.service.impl;

import com.xujiajun.corolla.constant.MessageStatusEnum;
import com.xujiajun.corolla.constant.MqTagEnum;
import com.xujiajun.corolla.message.ReturnMessage;
import com.xujiajun.corolla.model.MsgConsumeOrder;
import com.xujiajun.corolla.model.Order;
import com.xujiajun.corolla.model.OrderGoods;
import com.xujiajun.corolla.order.compose.service.OrderService;
import com.xujiajun.corolla.order.dal.dao.MsgConsumeOrderMapper;
import com.xujiajun.corolla.order.dal.dao.OrderGoodsMapper;
import com.xujiajun.corolla.order.dal.dao.OrderMapper;
import com.xujiajun.corolla.util.JacksonUtils;
import com.xujiajun.corolla.util.MqProducerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MqProducerClient producerClient;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    @Autowired
    private MsgConsumeOrderMapper msgConsumeOrderMapper;

    @Autowired
    private OrderService _this;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Override
    public void createOrder(Long userId, List<Long> goodsIdList) {
        this.createOrder(null, userId, goodsIdList);
    }

    @Override
    public void createOrder(Long orderMsgId, Long userId, List<Long> goodsIdList) {
        Date current = new Date(System.currentTimeMillis());
        Order order = new Order();
        order.setUserId(userId);
        order.setCreateTime(current);
        order.setUpdateTime(current);
        order.setOrderMsgId(orderMsgId);
        orderMapper.insert(order);

        List<OrderGoods> orderGoodsList = goodsIdList.stream()
            .map(goodsId -> {
                OrderGoods orderGoods = new OrderGoods();
                orderGoods.setOrderId(order.getId());
                orderGoods.setGoodsId(goodsId);
                orderGoods.setDeleted(false);
                orderGoods.setCreateTime(current);
                orderGoods.setUpdateTime(current);
                return orderGoods;
            })
            .collect(Collectors.toList());
        orderGoodsMapper.batchInsert(orderGoodsList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrderMq(Long orderMsgId, Long userId, List<Long> goodsIdList) {
        Date current = new Date(System.currentTimeMillis());

        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setOrderMsgId(orderMsgId);
        // 校验消息是否被消费过
        MsgConsumeOrder msgConsumeOrder = msgConsumeOrderMapper.getByOrderMsgId(orderMsgId);
        int status = MessageStatusEnum.OK.getStatus();
        if (msgConsumeOrder == null) {
            try {
                _this.createOrder(orderMsgId, userId, goodsIdList);
            } catch (Exception e) {
                log.error("业务异常, 触发回滚通知", e);
                status = MessageStatusEnum.FAILED.getStatus();
            } finally {
                MsgConsumeOrder updater = new MsgConsumeOrder();
                updater.setOrderMsgId(orderMsgId);
                updater.setStatus(status);
                updater.setCreateTime(current);
                updater.setUpdateTime(current);
                msgConsumeOrderMapper.insert(updater);
            }
        } else {
            // 已被消费过, 需要返回一个状态
            status = msgConsumeOrder.getStatus();
        }
        returnMessage.setStatus(status);
        producerClient.send(MqTagEnum.ORDER_RETURN.getTag(), JacksonUtils.toJson(returnMessage));
    }

    @Transactional(rollbackFor = Exception.class)
	@Override
	public void removeOrderMq(Long orderMsgId) {
        Order order = orderMapper.getByOrderMsgId(orderMsgId);
        orderMapper.removeByOrderMsgId(orderMsgId);
        orderGoodsMapper.removeByOrderId(order.getId());
	}

}
