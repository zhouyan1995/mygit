package com.common.JunitTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.TestAppcontion;
import com.test.mapper.UserMapper;
import com.test.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestAppcontion.class)//这里的Application是springboot的启动类名
public class TestJunit {

	@Autowired
	private UserMapper mapper;
	
	@Test
	public void test(){
		int a =mapper.updateUser(new User("1","123"));
		int a1 =mapper.updateUser(new User("2","123"));
		int a2 =mapper.updateUser(new User("3","123"));
		int a3 =mapper.updateUser(new User("4","123"));
		int a4 =mapper.updateUser(new User("5","123"));
		int a5 =mapper.updateUser(new User("6","123"));
		int a6 =mapper.updateUser(new User("7","123"));
	}
}
