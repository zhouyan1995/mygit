package com.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
	private UserService userService;
	
	/*Restful风格*/
	/*get 查询*/
	/*apiVersion  版本号*/
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getUserById(@PathVariable String apiVersion, @PathVariable String id ,
			HttpServletResponse response, HttpServletRequest request) {
		
			User user =userService.getUserById(id);
			return response("data",user);

	}
	/*put 修改*/
	@RequestMapping(value="/put/{id}",method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> updateUserById(@PathVariable String apiVersion ,
			HttpServletResponse response, HttpServletRequest request,@RequestBody User user) {
		
			this.userService.updateUserById(user);
		    return response("result","处理成功");
	}
	
	/*post 插入*/
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> insertUser(@PathVariable String apiVersion ,
			HttpServletResponse response, HttpServletRequest request,@RequestBody User user) {
		  	
			this.userService.insertUser(user);
		    return response("result","处理成功");
	}
	
	/*delete 删除*/
	@RequestMapping(value="/delete/{id}",method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> deleteUserById(@PathVariable String apiVersion, @PathVariable String id ,
			HttpServletResponse response, HttpServletRequest request) {
		   
		    this.userService.deleteUserById(id);
		    return response("data","处理成功");
	}
	
	@RequestMapping(value="/list/{pageNum}/{pageSize}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getUser(@PathVariable String apiVersion, @PathVariable Integer pageNum ,
			@PathVariable Integer pageSize ,HttpServletResponse response, HttpServletRequest request) {
			Map<String, Object> respMap = new HashMap<String, Object>();
			/*数据*/
			List<User> listUser =userService.getUser(pageNum,pageSize);
			/*总页数*/
			int pageCount=userService.getUserCount();
			
			respMap.put("userService", listUser);
			respMap.put("pageCount", pageCount);
			return response("data",respMap);

	}
}
