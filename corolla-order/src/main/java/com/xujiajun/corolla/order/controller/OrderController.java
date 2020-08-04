package com.xujiajun.corolla.order.controller;

import com.xujiajun.core.entity.ResponseData;
import com.xujiajun.corolla.order.compose.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/{userId:\\d+}")
    public ResponseData createOrder(@PathVariable Long userId, @RequestBody List<Long> goodsIdList) {
        orderService.createOrder(userId, goodsIdList);
        return new ResponseData();
    }
}
