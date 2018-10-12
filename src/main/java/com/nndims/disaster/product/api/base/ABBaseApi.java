/**
 * 
 */
package com.nndims.disaster.product.api.base;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import com.nndims.disaster.product.config.AuthKeyProperties;

/**
 * @author muxl
 * @date 2018年10月8日
 * @time 下午6:12:21
 */
abstract public class ABBaseApi implements IBaseApi {

	@Autowired
	private AuthKeyProperties authKey;

	protected void authKey(String key) {
		if (key == null || authKey.get(key) == null) {
			throw new RuntimeException("用户认证失败！");
		}
	}

	protected void authCode(String key, String code) {
		authKey(key);
		if (code == null || code.length() < 2) {
			throw new RuntimeException("行政区划编码错误！");
		}
		if (code.substring(0, 2).equals(getRegCode(key).substring(0, 2)) == false) {
			throw new RuntimeException("用户认证失败！");
		}
	}

	protected String getRegCode(String key) {
		return (String) authKey.get(key);
	}

	protected void errorHandler(BindingResult bErrors) throws ValidationException {
		StringBuilder builder = new StringBuilder();
		List<ObjectError> errors = bErrors.getAllErrors();
		if (errors == null || errors.isEmpty()) {
			return;
		}
		for (int i = 0; i < errors.size(); i++) {
			if (i != 0) {
				builder.append(" ");
			}
			builder.append((i + 1) + "、" + errors.get(i).getDefaultMessage());
		}
		throw new ValidationException(builder.toString());
	}

	@ControllerAdvice
	static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

		public JsonpAdvice() {
			super("callback");
		}
	}
}
