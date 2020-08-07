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
public class RollbackMessage implements Serializable {

	private static final long serialVersionUID = -1897603643252483514L;

	private Long orderMsgId;
}
