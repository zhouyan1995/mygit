/**
 * 
 */
package com.nndims.disaster.product.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author muxl
 * @date 2018年4月11日
 * @time 下午2:41:36
 */
@Configuration
@ConfigurationProperties
@PropertySource(value = "classpath:auth_key.properties", ignoreResourceNotFound = true)
public class AuthKeyProperties {

	private Map<String, String> auth_key = new HashMap<String, String>(0);

	public String get(String key) {
		return auth_key.get(key);
	}

	public Map<String, String> getAuth_key() {
		return auth_key;
	}

	public void setAuth_key(Map<String, String> auth_key) {
		this.auth_key = auth_key;
	}

}
