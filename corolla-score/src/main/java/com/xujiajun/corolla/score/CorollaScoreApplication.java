package com.xujiajun.corolla.score;

import com.xujiajun.corolla.score.config.FeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
public class CorollaScoreApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CorollaScoreApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CorollaScoreApplication.class);
    }
}
