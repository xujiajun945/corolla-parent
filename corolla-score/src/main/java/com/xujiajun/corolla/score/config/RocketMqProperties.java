package com.xujiajun.corolla.score.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xujiajun
 * @since 2020/8/5
 */
@ConfigurationProperties(prefix = "corolla.rocket-mq")
@Component
@Setter
@Getter
@ToString
public class RocketMqProperties {

	/**
	 * namesrv地址
	 */
	private String namesrv;

	/**
	 * mq消费者使用的组
	 */
	private String consumerGroup;

	/**
	 * mq生产者使用的组
	 */
	private String producerGroup;

	/**
	 * mq使用的topic
	 */
	private String topic;
}
