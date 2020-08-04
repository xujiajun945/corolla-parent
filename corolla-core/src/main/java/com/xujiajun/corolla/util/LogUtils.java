package com.xujiajun.corolla.util;

import org.slf4j.Logger;

/**
 * @author xujiajun
 * @since 2020/7/27
 */
public class LogUtils {

	private LogUtils() {
		throw new RuntimeException("util class can not be init !");
	}

	public static void auto(Logger logger, String message, Throwable e) {
		if (logger.isTraceEnabled()) {
			logger.trace(message, e);
		} else if (logger.isDebugEnabled()) {
			logger.debug(message, e);
		} else if (logger.isInfoEnabled()) {
			logger.info(message);
		} else if (logger.isWarnEnabled()) {
			logger.warn(message);
		}
	}
}
