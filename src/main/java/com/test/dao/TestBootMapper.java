package com.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.model.User;

@Mapper
public interface TestBootMapper {

     List<User> getUser (); 
}
