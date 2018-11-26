package com.fineway.gthbtable.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jodd.util.URLDecoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fineway.common.util.GetUUID;
import com.fineway.gthbtable.model.GtHbTable;
import com.fineway.gthbtable.serivce.GtHbTableService;

@Controller
@RequestMapping("/GT")
public class GtHbTableController {

	@Resource
	private GtHbTableService service;
	/**
	 * serviceName：
	 * serviceType：
	 * serviceAddr：
	 * http:
	 * @param 
	 * @return 
	 * @author zy
	 */
	@RequestMapping("/searchGT")
	@ResponseBody
	public Object searchGT(String REPORTTIME,Integer currentPage,Integer pageSize,String QYMC,String PARENTID){
		Map retMap =new HashMap();
		Map map =new HashMap();
		if(QYMC == "" || QYMC ==null){
			QYMC="";
		}else{
			String s;
			try {
				//s = URLEncoder.encode(QYMC, "UTF-8");
				//s=URLDecoder.decode(QYMC,"UTF-8");//还原
				s=new String(QYMC.getBytes("iso-8859-1"),"utf-8");
				//s=URLDecoder.decode(s,"UTF-8");//还原
				
				QYMC =s.trim();//去空格
				System.out.println(QYMC);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//编码
			
		}
		
		
		//判断
		if(currentPage == null || pageSize == null ){
			
		 retMap.put("result", false);
		}else{
			map.put("PARENTID", PARENTID);
			map.put("currentPage", (currentPage-1)*pageSize+1);//当前页
			map.put("pageSize", pageSize*currentPage);//每页条数
			if( REPORTTIME ==""&&  QYMC ==""){
				//查询所有
				List<GtHbTable> list =service.getGTAll(map);
				retMap.put("list", list);
			} else if (REPORTTIME!="" &&  QYMC ==""){
				//有时间条件查询所有
				map.put("REPORTTIME", REPORTTIME);//报表时间
				List<GtHbTable> list =service.getGtList(map);
				retMap.put("list", list);//数据
			}else if(REPORTTIME=="" && QYMC !="" ){
				// //有企业名称条件查询
				
				map.put("QYMC", QYMC);
				List<GtHbTable> list =service.getGTForName(map);
				retMap.put("list", list);//数据
			}else{
				//俩个条件都成立
				map.put("REPORTTIME", REPORTTIME);//报表时间
				map.put("QYMC", QYMC);
				List<GtHbTable> list =service.getGTForNT(map);
				retMap.put("list", list);//数据
			}
			int count =service.getGTCount(map);
		
			retMap.put("totalCount", count);//总条数
			retMap.put("result", true);
		}
		
		
		return retMap;
	}
	
	public static void main(String[] args) {
		System.out.println(""=="");
	}
	
	@RequestMapping(value = "/addGT", method = { RequestMethod.POST })
	@ResponseBody
	public Object insertGT(@RequestBody GtHbTable gtHbTable){
		Map retMap =new HashMap();
		String GTHCID =gtHbTable.getGthcid();
		if(GTHCID == null || GTHCID ==""){
			          //新增数据
						gtHbTable.setHcstate("1");//已核查					
						gtHbTable.setHcisnew("1");//最新
						gtHbTable.setCreatetime(new Date());
						int count =service.insertGT(gtHbTable);
						retMap.put("result", true);					
			
		}else{
			
			//修改的  
			gtHbTable.setHcstate("1");//已核查
			gtHbTable.setHcisnew("1");//最新
			gtHbTable.setCreatetime(new Date());
			int count =service.insertGT(gtHbTable);
			if(count >0){
				retMap.put("result", true);
			}else{
				retMap.put("result", false);
			}
		}
	
		
		return retMap;
		
	}
	@RequestMapping("/CheckName")
	@ResponseBody
	public Object CheckName(String QYMC){
		Map retMap =new HashMap();
		if(QYMC ==null || QYMC.equals("")){
			retMap.put("error", "QYMC不为空");
		
		}else{
		try {
			String	s=URLDecoder.decode(QYMC,"UTF-8");//还原
			QYMC=s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			int g=service.searchName(QYMC);
			if(g==0){
				retMap.put("result", 1);//可以核查
			}else{
				retMap.put("result", 0);//重名
			}
		}
			return retMap;
	}
	
}
