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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nndims.disaster.product.domain.DisasterDto;
import com.nndims.disaster.product.domain.base.Params;
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
			@RequestBody  Params Params,
			HttpServletRequest req, HttpServletResponse resp
			){
			Map<String,Object> map =service.findReportData(Params.getReportIds(),
					Params.getIndexItemCodes(),
					Params.getLevel(),
					Params.getFlag(),
					Params.getStartTime(),
					Params.getEndtime(),
					req, 
					resp,
					Params.getIndex());
		return map;
	}
	
	@RequestMapping(value="/getDisasterList",method = RequestMethod.POST)
	@CrossOrigin
	@ResponseBody
	public Object getDisasterList(
			@RequestBody Params Params){
	Map<String,Object> map =new HashMap();
	List<DisasterDto> list= null;
	String stime =Params.getStime();
	String etime =Params.getEtime();
	Long stime1=Long.parseLong(stime);
	Long etime1=Long.parseLong(etime);
	Integer flag =Params.getFlag();
	Integer currentPage =Params.getCurrentPage();
	Integer pageSize =Params.getPageSize();
	int count =0;
	try {
		if(flag ==0){//本月以来
			list = service.findDisasterList(stime1,
					etime1,
					( currentPage-1)*pageSize+1 ,currentPage*pageSize,
					Params.getIds());
			 count =service.findDisasterListCountByMonth(stime1, etime1, currentPage, pageSize, Params.getIds());
			
		}else{ //本年以来
			list = service.findDisasterListByYear(stime1, etime1,( currentPage-1)*pageSize+1 ,currentPage*pageSize,Params.getIds());
			 count =service.findDisasterListCountByYear(stime1, etime1, currentPage, pageSize, Params.getIds());
		}
		if(list == null){
			map.put("result", "没有数据");
			map.put("data", null);
		}else{
		map.put("result", "success");
		map.put("data", list);
		map.put("count", count);
		}
	} catch (Exception e) {
		map.put("result", "fail");
		
		e.printStackTrace();
	}
		return map;
	}
	
	@RequestMapping("/getProName")
	@CrossOrigin
	@ResponseBody
	public Object getProName(){
		Map<String,Object> map =new HashMap();
		List<Map<String,Object>> list= new ArrayList();
		Map<String,Object> map1 =new HashMap();
		try {
			list = service.getProName();
			map.put("result", "success");
			/*map1.put("CIVILREGIONALISMNAME", "全国");
			map1.put("CREDITIONRELATIONID", "ALL_SELECT");
			list.add(map1);*/
			map.put("data", list);
			
		} catch (Exception e) {
			map.put("result", "fail");
			
			e.printStackTrace();
		}
		return list;
	}
	
}
