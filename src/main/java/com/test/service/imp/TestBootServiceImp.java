package com.test.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.dao.TestBootMapper;
import com.test.model.User;
import com.test.service.TestBootService;
@Service
@Transactional
public class TestBootServiceImp implements TestBootService {

		@Autowired
		TestBootMapper mapper;
	

	@Override
	public List<User> getUser() {
		
		return mapper.getUser();
	}

}
