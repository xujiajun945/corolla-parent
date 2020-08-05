package com.xujiajun.corolla.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
@Setter
@Getter
@ToString
public class OrderMessage implements Serializable {

	private static final long serialVersionUID = 634932494611159166L;

	private Long orderMsgId;

	private Long userId;

	private List<Long> goodsIdList;
}
