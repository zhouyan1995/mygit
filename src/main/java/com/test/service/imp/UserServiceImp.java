package com.test.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.test.common.exception.BaseException;
import com.test.common.exception.UserNotExistsException;
import com.test.mapper.UserMapper;
import com.test.model.User;
import com.test.service.UserService;
@Service
@Transactional
public class UserServiceImp implements UserService {

		@Autowired
		private UserMapper userMapper;
	

	@Override
	public User getUserById(String id) {
		User user =userMapper.getUserById(id);
		if (user ==null){
			throw new UserNotExistsException(); //用户不存在
		}
		return userMapper.getUserById(id);
	}


	@Override
	public int updateUserById(User user) {
		int count =this.userMapper.updateUserById(user);
		if(count <=0){
			throw new BaseException();//处理失败
		}
		return count;
	}


	@Override
	public int insertUser(User user) {
		int count =this.userMapper.insertUser(user);
		if(count <=0){
			throw new BaseException(); //处理失败
		}
		return count;
	}


	@Override
	public int deleteUserById(String id) {
		int count =this.userMapper.deleteUserById(id);
		if(count <=0){
			throw new BaseException(); //处理失败
		}
		return count;
	}


	


	@Override
	public List<User> getUser(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<User> listUser=userMapper.getUser();
		return listUser;
	}


	@Override
	public int getUserCount() {
		
		return userMapper.getUserCount();
	}
	

}
