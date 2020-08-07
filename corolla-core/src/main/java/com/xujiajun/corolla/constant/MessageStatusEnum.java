package com.xujiajun.corolla.constant;

import lombok.Getter;

/**
 * @author xujiajun
 * @since 2020/8/7
 */
@Getter
public enum MessageStatusEnum {

	/** 没有响应 */
	NO_RESPONSE(0, "没有响应"),

	/** 正常 */
	OK(1, "正常"),

	/** 失败! */
	FAILED(2, "失败!");

	private Integer status;

	private String message;

	MessageStatusEnum(Integer status, String message) {
		this.status = status;
		this.message = message;
	}
}
