package com.test.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.test.model.User;

@Mapper
public interface UserMapper {

	User getUser(String id);

	int updateUser(User user);

	int insertUser(User user);

	int deleteUser(String id);

}
