package com.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.test.common.BaseController;
import com.test.common.exception.error.ErrorCode;
import com.test.common.exception.error.ErrorCodeProperties;
import com.test.model.User;
import com.test.service.UserService;

@Controller
@RequestMapping("api/{apiVersion}/user/")
public class UserController extends BaseController{

	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	UserService service;
	
	/*Restful风格*/
	/*get 查询*/
	/*apiVersion  版本号*/
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getUser(@PathVariable String apiVersion, @PathVariable String id ,
			HttpServletResponse response, HttpServletRequest request) {
			
			Map<String,Object> map =new HashMap<String, Object>();
			User user =service.getUser(id);
			User user1 =service.getUser(id);
			map.put("user", user);
			map.put("user1", user1);			
		return response("data",map);

	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateUser(@PathVariable String apiVersion, @PathVariable String id ,
			HttpServletResponse response, HttpServletRequest request,@RequestBody User user) {
		   
			int retNumber = service.updateUser(user);
		    return response("data",retNumber);
	}
}
