package com.xujiajun.corolla.base.handler;

import com.xujiajun.core.constant.BaseResponseCode;
import com.xujiajun.core.entity.ResponseData;
import com.xujiajun.core.exception.ResponseException;
import com.xujiajun.corolla.exception.CorollaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xujiajun
 * @since 2020/7/27
 */
@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 是否宽松解析时间 false则严格解析 true宽松解析
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseData handleHttpMessageNotReadableExceptionException(HttpMessageNotReadableException e) {
		log.error(BaseResponseCode.BAD_REQUEST.getMessage(), e);
		return new ResponseData(BaseResponseCode.BAD_REQUEST);
	}

	/**
	 * 404 - Not Fount
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseData handleNoHandlerFoundExceptionException(NoHandlerFoundException e) {
		log.error(BaseResponseCode.NOT_FOUND.getMessage(), e);
		return new ResponseData(BaseResponseCode.NOT_FOUND);
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseData handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error(BaseResponseCode.METHOD_NOT_ALLOWED.getMessage(), e);
		return new ResponseData(BaseResponseCode.METHOD_NOT_ALLOWED);
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseData handleException(Exception e) {
		log.error(BaseResponseCode.INTERNAL_SERVER_ERROR.getMessage(), e);
		return new ResponseData(BaseResponseCode.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseData handleHttpMediaTypeNotSupportedException(Exception e) {
		log.error(BaseResponseCode.UNSUPPORTED_MEDIA_TYPE.getMessage(), e);
		return new ResponseData(BaseResponseCode.UNSUPPORTED_MEDIA_TYPE);
	}

	/**
	 * 业务自定义异常
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ResponseException.class)
	public ResponseData handleResponseException(ResponseException e) {
		log.error(BaseResponseCode.BAD_REQUEST.getMessage(), e);
		return new ResponseData(e.getCode(), e.getMessage());
	}

	/**
	 * 业务自定义异常
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CorollaException.class)
	public ResponseData handleCorollaException(CorollaException e) {
		log.error(BaseResponseCode.BAD_REQUEST.getMessage(), e);
		return new ResponseData(e.getCode(), e.getMessage());
	}
}
