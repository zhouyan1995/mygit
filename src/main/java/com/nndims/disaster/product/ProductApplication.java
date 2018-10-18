/**
 * 
 */
package com.nndims.disaster.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author muxl
 * @date 2018年10月8日
 * @time 上午11:01:24
 */
@SpringBootApplication
@MapperScan(basePackages = { "com.nndims.disaster.product.mapper" })
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

}
