package com.test.common.exception;

import com.test.common.BaseController;
import com.test.common.exception.error.ErrorCodeProperties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessException extends RuntimeException {
	private static final Logger logger = LoggerFactory
			.getLogger(BaseController.class);

	private String code = StringUtils.EMPTY;
	private String message = StringUtils.EMPTY;

	public BusinessException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BusinessException(Exception e) {
		super(e);
		if (e instanceof BusinessException) {
			this.code = ((BusinessException) e).getCode();
			this.message = e.getMessage();
		}
	}

	/**
	 * 根据错误编码抛出业务异常
	 */
	public BusinessException(String code) {
		this(code, ErrorCodeProperties.init().getErrorMessage(code));
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
