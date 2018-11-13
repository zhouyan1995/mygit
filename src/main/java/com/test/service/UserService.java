package com.test.service;

import java.util.List;

import com.test.model.User;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
	 User getUser (String id);
	 int updateUser(User user);
     int deleteUser(User user);
     int insertUser(User user);
}
