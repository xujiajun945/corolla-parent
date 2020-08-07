package com.xujiajun.corolla.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xujiajun
 * @since 2020/8/7
 */
@Setter
@Getter
@ToString
public class ReturnMessage implements Serializable {

	private static final long serialVersionUID = 3649967250782238440L;

	/**
	 * 订单消息id
	 */
	private Long orderMsgId;

	/**
	 * 状态
	 */
	private Integer status;
}
