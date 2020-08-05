package com.xujiajun.corolla.order.compose.service.impl;

import com.xujiajun.corolla.model.Order;
import com.xujiajun.corolla.model.OrderGoods;
import com.xujiajun.corolla.order.compose.service.OrderService;
import com.xujiajun.corolla.order.config.RocketMqProperties;
import com.xujiajun.corolla.order.dal.dao.OrderGoodsMapper;
import com.xujiajun.corolla.order.dal.dao.OrderMapper;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private DefaultMQProducer producer;

    @Autowired
    private RocketMqProperties properties;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrder(Long userId, List<Long> goodsIdList) {
        Date current = new Date(System.currentTimeMillis());
        Order order = new Order();
        order.setUserId(userId);
        order.setCreateTime(current);
        order.setUpdateTime(current);
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
        this.createOrder(userId, goodsIdList);
        // 创建订单成功, 发送消息通知base服务
        Map<String, Object> data = new HashMap<>(2);
        data.put("status", 1);
        data.put("orderMsgId", orderMsgId);
        Message message = new Message(properties.getTopic(), "OrderReturn", JacksonUtils.toJson(data).getBytes());
        try {
            producer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            log.error("消息发送失败!", e);
            throw new RuntimeException("消息发送失败!");
        }
    }
}
