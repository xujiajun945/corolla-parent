package com.xujiajun.corolla.base.compose.service.impl;

import com.xujiajun.corolla.base.compose.manager.UserManager;
import com.xujiajun.corolla.base.compose.service.BusinessService;
import com.xujiajun.corolla.base.dal.dao.OrderMsgMapper;
import com.xujiajun.corolla.base.feign.GoodsClient;
import com.xujiajun.corolla.base.feign.OrderClient;
import com.xujiajun.corolla.constant.MessageStatusEnum;
import com.xujiajun.corolla.constant.MqTagEnum;
import com.xujiajun.corolla.message.OrderMessage;
import com.xujiajun.corolla.message.RollbackMessage;
import com.xujiajun.corolla.model.OrderMsg;
import com.xujiajun.corolla.util.JacksonUtils;
import com.xujiajun.corolla.util.MqProducerClient;
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

    @Autowired
    private MqProducerClient producerClient;

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

    @Transactional(rollbackFor = Exception.class)
	@Override
	public void updateOrderMsgStatus(String tags, Long orderMsgId, Integer status) {
        OrderMsg updater = new OrderMsg();
        updater.setId(orderMsgId);
        if (MqTagEnum.ORDER_RETURN.getTag().equals(tags)) {
            updater.setOrderReturn(status);
        }
        if (MqTagEnum.GOODS_RETURN.getTag().equals(tags)) {
            updater.setGoodsReturn(status);
        }
        orderMsgMapper.updateById(updater);
        if (MessageStatusEnum.FAILED.getStatus().equals(status)) {
            OrderMsg ori = orderMsgMapper.getById(orderMsgId);
            OrderMessage orderMessage = JacksonUtils.parseObject(ori.getContent(), OrderMessage.class);
            // 查询用户的订单
            userManager.unModifyUserAccountAndScore(orderMessage.getUserId(), orderMessage.getGoodsIdList());
            // 通知回滚
            RollbackMessage rollbackMessage = new RollbackMessage();
            rollbackMessage.setOrderMsgId(orderMsgId);
            producerClient.send(MqTagEnum.ROLLBACK.getTag(), JacksonUtils.toJson(rollbackMessage));
        }
	}
}
