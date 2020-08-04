package com.xujiajun.corolla.order;

import com.xujiajun.corolla.order.config.FeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xujiajun
 * @since 2020/7/30
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
public class CorollaOrderApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CorollaOrderApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CorollaOrderApplication.class);
	}
}
