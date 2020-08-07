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
public class MqException extends RuntimeException {

	private static final long serialVersionUID = -7526340340608923700L;

	private Integer code;

	public MqException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public MqException(Throwable throwable) {
		super(throwable);
	}

	public MqException(String message, Throwable cause) {
		super(message, cause);
	}
}
