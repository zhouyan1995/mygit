package com.test.common.exception;

import com.test.common.BaseController;
import com.test.common.exception.error.ErrorCodeProperties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  class BaseException extends RuntimeException {
	private static final Logger logger = LoggerFactory
			.getLogger(BaseController.class);

	private String code = StringUtils.EMPTY;
	private String message = StringUtils.EMPTY;

	public BaseException() {
        this.code = "400";
        this.message = "处理失败";
    }
	
	public BaseException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BaseException(Exception e) {
		super(e);
		if (e instanceof BaseException) {
			this.code = ((BaseException) e).getCode();
			this.message = e.getMessage();
		}
	}

	/**
	 * 根据错误编码抛出业务异常
	 */
	public BaseException(String code) {
		this(code, ErrorCodeProperties.init().getErrorMessage(code));
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
