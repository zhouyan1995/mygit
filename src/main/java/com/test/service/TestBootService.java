package com.test.service;

import java.util.List;

import com.test.model.User;

import org.springframework.stereotype.Service;

@Service
public interface TestBootService {
	public List<User> getUser ();
	 
}
