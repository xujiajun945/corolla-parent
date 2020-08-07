package com.xujiajun.corolla.constant;

import lombok.Getter;

/**
 * @author xujiajun
 * @since 2020/8/7
 */
@Getter
public enum MqTagEnum {

	/** 用户下单 */
	USER_ORDERED("userOrdered", "用户下单"),

	/** 订单系统返回 */
	ORDER_RETURN("orderReturn", "订单系统返回"),

	/** 商品系统返回 */
	GOODS_RETURN("goodsReturn", "商品系统返回"),

	/** 回滚通知 */
	ROLLBACK("rollback", "回滚通知");

	private String tag;

	private String message;

	MqTagEnum(String tag, String message) {
		this.tag = tag;
		this.message = message;
	}
}
