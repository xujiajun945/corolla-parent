package com.xujiajun.corolla.order.compose.service.impl;

import com.xujiajun.corolla.model.Order;
import com.xujiajun.corolla.model.OrderGoods;
import com.xujiajun.corolla.order.compose.service.OrderService;
import com.xujiajun.corolla.order.dal.dao.OrderGoodsMapper;
import com.xujiajun.corolla.order.dal.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@Service
public class OrderServiceImpl implements OrderService {

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
}
