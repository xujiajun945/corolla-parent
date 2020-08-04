package com.xujiajun.corolla.score.config;

import com.xujiajun.core.entity.ResponseData;
import com.xujiajun.core.exception.ResponseException;
import com.xujiajun.corolla.util.JacksonUtils;
import feign.*;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author xujiajun
 * @since 2020/8/4
 */
public class FeignConfiguration {

    private static final long CONNECT_TIMEOUT_MILLIS = 2000L;

    private static final long READ_TIMEOUT_MILLIS = 60000L;

    @Bean
    public Request.Options options() {
        return new Request.Options(CONNECT_TIMEOUT_MILLIS, TimeUnit.SECONDS, READ_TIMEOUT_MILLIS, TimeUnit.SECONDS, true);
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }

//    @Bean
    public Decoder decoder() {
        return new ClassicDecoder();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Deprecated
    public static class ClassicDecoder implements Decoder {

        @Override
        public Object decode(Response response, Type type) throws IOException, FeignException {
            // 获取原始的返回内容
            String json = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            return JacksonUtils.parseObject(json, type.getClass());
        }

    }

    /**
     * 自定义错误
     */
    @Slf4j
    public static class UserErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;
            try {
                // 获取原始的返回内容
                String json = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
                ResponseData responseData = JacksonUtils.parseObject(json, ResponseData.class);

                // 若状态码为400,抛出ResponseException
                if (response.status() == HttpStatus.BAD_REQUEST.value()) {
                    exception = new ResponseException(responseData.getCode(), responseData.getMessage());
                }

            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
            return exception;
        }
    }
}
