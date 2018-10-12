package com.nndims.disaster.product.domain.base;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 结果集
 * @author muxl
 * @date 2018年10月8日
 * @time 下午8:14:08
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Result extends ABBaseDto {

	private static final long serialVersionUID = 7652176489413783501L;
	private boolean success = false;
	private Integer errorCode;
	private String errorMsg;
	private String promptMsg;
	private Object resultData;

	private Result(boolean success, int errorCode, String errorMsg) {
		this.success = success;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	private Result(boolean success, String promptMsg, String errorMsg, Object resultData) {
		this.success = success;
		this.errorMsg = errorMsg;
		this.promptMsg = promptMsg;
		this.resultData = resultData;
	}

	/**
	 * 成功结果集
	 * @param result
	 * @param promptMsg
	 * @return
	 */
	public static final Result success(Object result, String promptMsg) {
		return new Result(true, promptMsg, null, result);
	}

	/**
	 * 成功结果集
	 * @return
	 */
	public static final Result success() {
		return new Result(true, "操作成功！", null, null);
	}

	/**
	 * 成功结果集
	 * @param result
	 * @return
	 */
	public static final Result success(Object result) {
		return new Result(true, "操作成功！", null, result);
	}

	/**
	 * 失败结果集
	 * @param errorMsg
	 * @return
	 */
	public static final Result failure(String errorMsg) {
		return new Result(false, "操作失败！", errorMsg, null);
	}

	/**
	 * 失败结果集
	 * @param promptMsg
	 * @param errorMsg
	 * @return
	 */
	public static final Result failure(String promptMsg, String errorMsg) {
		return new Result(false, promptMsg, errorMsg, null);
	}

	/**
	 * 失败结果集
	 * @param errorCode
	 * @return
	 */
	public static final Result failure(Integer errorCode) {
		return new Result(false, errorCode, null);
	}

	/**
	 * 失败结果集
	 * @param errorCode
	 * @param errorMsg
	 * @return
	 */
	public static final Result failure(Integer errorCode, String errorMsg) {
		return new Result(false, errorCode, errorMsg);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}

	public String getPromptMsg() {
		return promptMsg;
	}

	public void setPromptMsg(String promptMsg) {
		this.promptMsg = promptMsg;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

}
