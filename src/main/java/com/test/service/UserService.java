package com.test.service;

import java.util.List;

import com.test.model.User;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
	User getUserById(String id);

	int updateUserById(User user);

	int insertUser(User user);

	int deleteUserById(String id);
	
	List<User> getUser(int pageNum,int pageSize);
	
	int getUserCount();
}
