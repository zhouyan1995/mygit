package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.model.User;

@Mapper
public interface UserMapper {

	User getUserById(String id);

	int updateUserById(User user);

	int insertUser(User user);

	int deleteUserById(String id);
	
	List<User> getUser();
	
	int getUserCount();

}
