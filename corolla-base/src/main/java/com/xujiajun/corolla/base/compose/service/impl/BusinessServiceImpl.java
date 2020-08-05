package com.xujiajun.corolla.base.compose.service.impl;

import com.xujiajun.corolla.base.compose.manager.UserManager;
import com.xujiajun.corolla.base.compose.service.BusinessService;
import com.xujiajun.corolla.base.dal.dao.OrderMsgMapper;
import com.xujiajun.corolla.base.feign.GoodsClient;
import com.xujiajun.corolla.base.feign.OrderClient;
import com.xujiajun.corolla.message.OrderMessage;
import com.xujiajun.corolla.model.OrderMsg;
import com.xujiajun.corolla.util.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private UserManager userManager;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private OrderMsgMapper orderMsgMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void userPostOrder(Long userId, List<Long> goodsIdList) {
        // 用户扣款/加积分
        userManager.modifyUserAccountAndScore(userId, goodsIdList);
        // 创建一个订单
        orderClient.createOrder(userId, goodsIdList);
        // 商品减库存
        goodsClient.modifyGoodsStock(goodsIdList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void userPostOrderMq(Long userId, List<Long> goodsIdList) {
        Date current = new Date(System.currentTimeMillis());
        // 用户扣款
        userManager.modifyUserAccountAndScore(userId, goodsIdList);
        // 添加一个订单消息
        OrderMsg orderMsg = new OrderMsg();
        orderMsg.setCreateTime(current);
        orderMsg.setUpdateTime(current);

        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setUserId(userId);
        orderMessage.setGoodsIdList(goodsIdList);
        String json = JacksonUtils.toJson(orderMessage);
        orderMsg.setContent(json);
        orderMsgMapper.insert(orderMsg);

        Long orderMsgId = orderMsg.getId();
        orderMessage.setOrderMsgId(orderMsgId);

        orderMsg.setContent(JacksonUtils.toJson(orderMessage));
        orderMsgMapper.updateById(orderMsg);
    }
}
