package com.xujiajun.corolla.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xujiajun
 * @since 2020/8/7
 */
@Setter
@Getter
@ToString
public class CorollaException extends RuntimeException {

	private static final long serialVersionUID = -7323767097255971683L;

	private Integer code;

	public CorollaException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public CorollaException(String message) {
		super(message);
	}
}
