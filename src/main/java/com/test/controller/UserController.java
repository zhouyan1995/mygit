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
}
