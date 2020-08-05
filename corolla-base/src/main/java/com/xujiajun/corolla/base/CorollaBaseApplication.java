package com.xujiajun.corolla.base;

import com.xujiajun.corolla.base.config.FeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xujiajun
 * @since 2020/7/27
 */
@ServletComponentScan
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
public class CorollaBaseApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CorollaBaseApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CorollaBaseApplication.class);
	}
}
