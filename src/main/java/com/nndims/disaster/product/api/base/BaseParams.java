/**
 * 
 */
package com.nndims.disaster.product.api.base;


/**
 * @author muxl
 * @date 2018年4月11日
 * @time 下午3:51:24
 */
public class BaseParams {

	// @NotEmpty(message = "{key.required}")
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
