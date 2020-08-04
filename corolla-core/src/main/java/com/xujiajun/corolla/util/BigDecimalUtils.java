package com.xujiajun.corolla.util;

import java.math.BigDecimal;

/**
 * @author xujiajun
 * @since 2020/7/27
 */
public class BigDecimalUtils {

	public static boolean moreThan(BigDecimal source, BigDecimal target) {
		return source.compareTo(target) > 0;
	}
}
