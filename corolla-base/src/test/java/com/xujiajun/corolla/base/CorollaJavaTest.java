package com.xujiajun.corolla.base;

import com.xujiajun.corolla.constant.MqTagEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author xujiajun
 * @since 2020/7/27
 */
@RunWith(JUnit4.class)
public class CorollaJavaTest {

	@Test
	public void name() {
		String tags = this.concatTags(MqTagEnum.GOODS_RETURN.getTag(), MqTagEnum.ORDER_RETURN.getTag());
		System.out.println(tags);
	}

	private String concatTags(String...tags) {
		return String.join(" || ", tags);
	}
}
