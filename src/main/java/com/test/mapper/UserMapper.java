package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.model.User;

@Mapper
public interface UserMapper {

     User getUser (String id); 
     int updateUser(User user);
     int deleteUser(User user);
     int insertUser(User user);
}
