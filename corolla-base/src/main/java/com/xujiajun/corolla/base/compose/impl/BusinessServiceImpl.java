package com.xujiajun.corolla.base.compose.impl;

import com.xujiajun.corolla.base.compose.BusinessService;
import com.xujiajun.corolla.base.feign.GoodsClient;
import com.xujiajun.corolla.base.feign.OrderClient;
import com.xujiajun.corolla.base.feign.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private UserClient userClient;

    @Autowired
    private GoodsClient goodsClient;

    @Override
    public void userPostOrder(Long userId, List<Long> goodsIdList) {
        // 用户扣款
        userClient.modifyUserAccount(userId, goodsIdList);
        // 创建一个订单
        orderClient.createOrder(userId, goodsIdList);
        // 商品减库存
        goodsClient.modifyGoodsStock(goodsIdList);
        // 用户增加商品积分
        userClient.modifyUserScore(userId, goodsIdList);
    }
}
