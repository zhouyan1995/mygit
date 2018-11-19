package com.test.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.common.exception.BaseException;
import com.test.common.exception.UserNotExistsException;
import com.test.mapper.UserMapper;
import com.test.model.User;
import com.test.service.UserService;
@Service
@Transactional
public class UserServiceImp implements UserService {

		@Autowired
		UserMapper mapper;
	

	@Override
	public User getUser(String id) {
		User user =mapper.getUser(id);
		if (user ==null){
			throw new UserNotExistsException(); //用户不存在
		}
		return mapper.getUser(id);
	}


	@Override
	public int updateUser(User user) {
		int count =mapper.updateUser(user);
		if(count >0){
			throw new BaseException("000"); //处理成功
		}
		return count;
	}
	

}
