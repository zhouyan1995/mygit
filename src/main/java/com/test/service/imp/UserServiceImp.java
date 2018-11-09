package com.test.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.mapper.UserMapper;
import com.test.model.User;
import com.test.service.UserService;
@Service
@Transactional
public class UserServiceImp implements UserService {

		@Autowired
		UserMapper mapper;
	

	@Override
	public List<User> getUser() {
		
		return mapper.getUser();
	}


	@Override
	public int updateUser(User user) {
		
		return mapper.updateUser(user);
	}


	@Override
	public int deleteUser(User user) {
		
		return mapper.deleteUser(user);
	}


	@Override
	public int insertUser(User user) {
		
		return mapper.insertUser(user);
	}

}
