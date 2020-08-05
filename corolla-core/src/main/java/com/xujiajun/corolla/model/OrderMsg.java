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
public class OrderMsg implements Serializable {

	private static final long serialVersionUID = 5177507364724669614L;

	private Long id;

	private String content;

	private Integer orderReturn;

	private Integer goodsReturn;

	private Boolean deleted;

	private Date createTime;

	private Date updateTime;
}
