package com.test.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.model.User;
import com.test.service.TestBootService;

@Controller
public class TestBootController {

	
	private final Logger log = org.slf4j.LoggerFactory
			.getLogger(TestBootController.class);

	@Autowired
	TestBootService service;


	
	@RequestMapping("/getUser")
	@ResponseBody
	public Object testBoot() {
		List<User> list =service.getUser();
		return list;

	}
	
}
