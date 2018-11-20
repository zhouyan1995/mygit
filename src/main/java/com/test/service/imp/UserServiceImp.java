package com.test.service.imp;

import java.util.List;

import javax.annotation.Resource;

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
		private UserMapper mapper;
	

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
		int count =this.mapper.updateUser(user);
		if(count <=0){
			throw new BaseException();//处理失败
		}
		return count;
	}


	@Override
	public int insertUser(User user) {
		int count =this.mapper.insertUser(user);
		if(count <=0){
			throw new BaseException(); //处理失败
		}
		return count;
	}


	@Override
	public int deleteUser(String id) {
		int count =this.mapper.deleteUser(id);
		if(count <=0){
			throw new BaseException("000"); //处理失败
		}
		return count;
	}
	

}
