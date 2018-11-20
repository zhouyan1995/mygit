package com.test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@SpringBootApplication//组合注解
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) //启动springboot中的自动配置项目
@EnableTransactionManagement(proxyTargetClass = true)//开启事务管理的注解
@ComponentScan(basePackages={"com.test"})//设置注入Controller Service
/*@MapperScan(basePackages = { "com.test.mapper" })*/
public class TestApplication {
		public static void main(String[] args) {
			SpringApplication.run(TestApplication.class, args);
			
	}
		
}
