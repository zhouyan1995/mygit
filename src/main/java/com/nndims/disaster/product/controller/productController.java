package com.nndims.disaster.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nndims.disaster.product.service.ProductServie;

@Controller
public class productController {

	@Autowired
	private ProductServie service;
	/**
	 * 
	 * serviceName：
	 * serviceType：
	 * serviceAddr：
	 * http:
	 * @param 
	 * @return 
	 * @author zy
	 */
	
	@RequestMapping("/getIndex")
	@CrossOrigin
	@ResponseBody
	public Object getIndex(){
	Map<String,Object> map =new HashMap();
	List<Map<String,Object>> list= new ArrayList();
	try {
		list = service.getIndex();
		if(list == null){
			map.put("result", "没有数据");
			map.put("data", null);
		}else{
		map.put("result", "success");
		map.put("data", list);
		}
	} catch (Exception e) {
		map.put("result", "fail");
		
		e.printStackTrace();
	}
		return map;
	}
	@RequestMapping(value="/getExcel",method = RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public Object getExcel(
			@RequestBody List<String> reportIds,
			@RequestBody List<String> indexItemCodes, Integer level, Integer flag,//1今年以来  0本月以来
			String startTime, String endtime,HttpServletRequest req, HttpServletResponse resp,@RequestBody List<Map<String,Object>> index
			){
		return null;
	}
	
	
}
