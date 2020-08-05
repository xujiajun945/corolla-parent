package com.xujiajun.corolla.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
@Setter
@Getter
@ToString
public class OrderMsgHis implements Serializable {

	private static final long serialVersionUID = -2507706359996137675L;

	private Long id;

	private Long orderMsgId;

	private Date createTime;

	private Date updateTime;
}
