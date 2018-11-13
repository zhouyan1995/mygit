package com.test.common;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultSet implements Serializable{

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	/**
	 * 返回结果集
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private Object data;
	public ResultSet() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ResultSet [code=" + code + ", message=" + message + ", data="
				+ data + "]";
	}
	public ResultSet(String code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
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
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
