package com.test.common;

import java.io.Serializable;

public class BaseResponseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BaseResponseVo(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public BaseResponseVo() {
		super();

	}

}
