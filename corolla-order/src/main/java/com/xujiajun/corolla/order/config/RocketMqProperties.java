package com.xujiajun.corolla.order.config;

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

	private String namesrv;

	private String group;

	private String topic;

	private String tags;
}
