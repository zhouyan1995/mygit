package com.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.test.common.BaseController;
import com.test.common.ResultSet;
import com.test.common.exception.BusinessException;
import com.test.common.exception.error.ErrorCode;
import com.test.common.exception.error.ErrorCodeProperties;
import com.test.model.User;
import com.test.service.UserService;

@Controller
@RequestMapping("api/{apiVersion}/user/")
<<<<<<< HEAD
public class UserController extends BaseController{

	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	UserService service;
	
	/*Restful风格*/
	/*get 查询*/
	/*apiVersion  版本号*/
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResultSet getUser(@PathVariable String apiVersion, @PathVariable String id) {
		ResultSet result = new ResultSet();
		try {
			User user =service.getUser(id);
			int a =1/0;
			result.setCode("1");
			result.setData(user);
			result.setMessage("");
		} catch (Exception e) {
			logger.error("【系统异常】{}", e);
			result.setMessage(ErrorCodeProperties.getErrorMessage(ErrorCode.UNKNOWN_ERROR));
			result.setCode(ErrorCode.UNKNOWN_ERROR);
			e.printStackTrace();	
		}
		return result;

	}
=======
public class UserController {


        private final Logger log = org.slf4j.LoggerFactory
                        .getLogger(UserController.class);

        @Autowired
        UserService service;

        /*Restful风格*/
        /*get 查询*/
        @RequestMapping(value="/{userId}",method=RequestMethod.GET)
        @ResponseBody
        public Object getUser(@PathVariable String apiVersion, @PathVariable Long userId) {
                List<User> list =service.getUser();
                return list;
        }
        /*put 修改*/
        @RequestMapping(value="User",method=RequestMethod.PUT,produces={"application/json;charset=UTF-8"})
        @ResponseBody
        public Object updateUser(String id ,String username) {
                User u =new User();
                u.setId(id);
                u.setUsername(username);
                int result =service.updateUser(u);
                return result;

        }
        /*post 插入*/
        @RequestMapping(value="User",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
        @ResponseBody
        public Object insertUser(String id ,String username,String password) {
                User u =new User();
                u.setId(id);
                u.setUsername(username);
                u.setPasword(password);
                int result =service.insertUser(u);
                return result;

        }
        /*post 插入*/
        @RequestMapping(value="User",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
        @ResponseBody
        public Object deleteUser(String id ) {
                User u =new User();
                u.setId(id);
                int result =service.deleteUser(u);
                return result;

        }

>>>>>>> 587c6ec05666b6b2b3f58e3840f3be3fb6a0c08f
}
