package com.xujiajun.corolla.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujiajun
 * @since 2020/7/27
 */
@Slf4j
public class JacksonUtils {

	private static final ObjectMapper PARSER = new ObjectMapper();

	static {
		// Include.NON_NULL 属性为NULL 不序列化
		PARSER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 反序列化时出现未知属性, 不失败
		PARSER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	private JacksonUtils() {
		throw new RuntimeException("util class can not be init !");
	}

	public static Object parseObject(String json) {
		return parseObject(json, Object.class);
	}

	public static <T> T parseObject(String json, Class<T> clazz) {
		T result = null;
		try {
			result = PARSER.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			LogUtils.auto(log, "反序列化失败", e);
		}
		return result;
	}

	public static <T> List<T> parseArray(String json, Class<T> clazz) {
		List<T> result = null;
		CollectionType collectionType = PARSER.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
		try {
			result = PARSER.readValue(json, collectionType);
		} catch (JsonProcessingException e) {
			LogUtils.auto(log, "反序列化失败", e);
		}
		return result;
	}

	public static <T> T parse(String json, Class<T> clazz, Class<?>... classes) {
		T result = null;
		JavaType javaType = PARSER.getTypeFactory().constructParametricType(clazz, classes);
		try {
			result = PARSER.readValue(json, javaType);
		} catch (JsonProcessingException e) {
			LogUtils.auto(log, "反序列化失败", e);
		}
		return result;
	}

	public static <T> T parse(String json, TypeReference<T> typeReference) {
		T result = null;
		try {
			result = PARSER.readValue(json, typeReference);
		} catch (JsonProcessingException e) {
			LogUtils.auto(log, "反序列化失败", e);
		}
		return result;
	}

	public static String toJson(Object object) {
		String result = null;
		try {
			result = PARSER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			LogUtils.auto(log, "序列化失败", e);
		}
		return result;
	}
}
