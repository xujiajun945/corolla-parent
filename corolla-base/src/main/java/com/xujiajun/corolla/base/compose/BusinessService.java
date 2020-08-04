package com.xujiajun.corolla.base.compose;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
public interface BusinessService {

    /**
     * 创建订单
     *
     * @param userId 用户id
     * @param goodsIdList 用户选择的商品id集合
     */
    void userPostOrder(Long userId, List<Long> goodsIdList);
}
