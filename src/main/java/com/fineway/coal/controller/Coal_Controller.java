package com.fineway.coal.controller;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fineway.coal.service.Coal_Service;

@Controller
@RequestMapping(value = "/coal")
public class Coal_Controller {
	@Autowired
	private Coal_Service coal_service;

	@RequestMapping(value = "/select")
	@ResponseBody
	public Map<String, Object> coal_select(int page, int num,String time,String QYMC,String PARENTID) throws Exception{
		Map<String, Object> para_map = new HashMap<String, Object>();
		Map<String, Object> result_map = new HashMap<String, Object>();
		int small_number = 0;
		int big_number = 0;
		String company_name =null;
		if(QYMC!=null&&!("").equals(QYMC)){
			company_name = URLDecoder.decode(QYMC, "utf-8");
		}
		try {
			small_number = (page - 1) * num;
			big_number = page * num;
		} catch (Exception e) {
			e.printStackTrace();
			result_map.put("data", "");
			result_map.put("erro", "参数page、num不能为空");
			return result_map;
		}
		try {
			para_map.put("small_number", small_number);
			para_map.put("big_number", big_number);
			para_map.put("time",time);
			para_map.put("company_name",company_name);
			para_map.put("PARENTID", PARENTID);
			List<Map<String, Object>> result_list = coal_service.getselect(para_map);
			int total_number = coal_service.get_page(para_map);
			//int page_count = total_number/num;
			result_map.put("total_number",total_number);
			result_map.put("data", result_list);
			result_map.put("erro","");
			} catch (Exception e) {
				e.printStackTrace();
			result_map.put("data","");
			result_map.put("erro", e);
			return result_map;
		}
		return result_map;
	}
	@RequestMapping(value="/select_name")
	@ResponseBody
	public boolean coal_select_name(HttpServletRequest request,HttpServletResponse response) throws Exception{
	String company_name = URLDecoder.decode(request.getParameter("QYMC"),"utf-8");
	int result = coal_service.select_name(company_name);
	if(result==0){
		return true;
	}else{
		return false;
	}
		
	}
	
	
	
	@RequestMapping(value="/insert",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> coal_insert(HttpServletRequest request,HttpServletResponse response) throws Exception{
	Map<String, Object> para_map = new HashMap<String, Object>();
	Map<String, Object> result_map = new HashMap<String, Object>();
	//生成UUID
	//String MTHC = request.getParameter("MTHC");//id
    String CIVILREGIONALISMID = request.getParameter("CIVILREGIONALISMID");//区划id
    String QYMC =null;
    String TCMKMC=null;
    if(request.getParameter("QYMC")!=null&&!("").equals(request.getParameter("QYMC"))){
    	//QYMC=new String(request.getParameter("QYMC").getBytes("iso-8859-1"),"utf-8");
    QYMC=request.getParameter("QYMC");
         //QYMC =URLDecoder.decode(request.getParameter("QYMC"),"utf-8");//企业名称
    }
    String XYDM =request.getParameter("XYDM");//企业统一信用代码
    //if(request.getParameter("TCMKMC")!=null&&!("").equals(request.getParameter("TCMKMC"))){
    	//TCMKMC=new String(request.getParameter("TCMKMC").getBytes("iso-8859-1"),"utf-8");
    TCMKMC = request.getParameter("TCMKMC");
         //TCMKMC = URLDecoder.decode(request.getParameter("TCMKMC"),"utf-8");//退出煤矿名称
    //}
    String MKSZD =request.getParameter("MKSZD");//是否停产
    String HDNL =request.getParameter("HDNL");//核定能力
    String TCSJ =request.getParameter("TCSJ");//退出时间
    String SFFB = request.getParameter("SFFB");//井口是否关闭
    String SFYS = request.getParameter("SFYS");//是否验收
    String REPORTTIME = request.getParameter("REPORTTIME");//报表时间
    String TCHSFGS = request.getParameter("TCHSFGS");//退出后是否公示
    String SFSBBJLXH = request.getParameter("SFSBBJLXH");//企业是否在上报部级联席会议名单内
    String SFSC = request.getParameter("SFSC");//是否存在复产
    String CKXKZH = request.getParameter("CKXKZH");//采矿许可证号
    String SFZXCKXKZ = request.getParameter("SFZXCKXKZ");//采矿许可证号是否注销 
    String CKXKZZXSJ = request.getParameter("CKXKZZXSJ");//采矿许可证号注销时间
    String CKXKZZXSFGG = request.getParameter("CKXKZZXSFGG");//采矿许可证号注销是否公告
    String AQSCXKZH = request.getParameter("AQSCXKZH");//安全生产许可证号
    String SFZXAQSCXKZ = request.getParameter("SFZXAQSCXKZ");//安全生产许可证号是否注销
    String AQSCXKZZXSJ = request.getParameter("AQSCXKZZXSJ");//安全生产许可证号注销时间
    String AQSCXKZZXSFGG = request.getParameter("AQSCXKZZXSFGG");//安全生产许可证号注销是否公告
    String HZWJH = request.getParameter("HZWJH");//核准（审批）文件号
    String SFCXHZWJ = request.getParameter("SFCXHZWJ");//核准（审批）文件是否注销
    String HZWJCXSJ = request.getParameter("HZWJCXSJ");//核准（审批）文件注销时间
    String HZWJSFGG = request.getParameter("HZWJSFGG");//核准（审批）文件注销是否公告
    String SFSZHFA = request.getParameter("SFSZHFA");//是否是置换方案中的煤炭企业
    String SFRQTC = request.getParameter("SFRQTC");//是否如期退出（核减）到位
    String SFLQCZJJ = request.getParameter("SFLQCZJJ");//是否领取中央财政奖补资金
    String PARENTID = request.getParameter("PARENTID");//所属省份ID

    para_map.put("CIVILREGIONALISMID",CIVILREGIONALISMID);       
    para_map.put("QYMC", QYMC);
    para_map.put("XYDM",XYDM); 
    para_map.put("TCMKMC",TCMKMC);
    para_map.put("MKSZD",MKSZD);
    para_map.put("HDNL",HDNL);
    para_map.put("TCSJ",TCSJ);
    para_map.put("SFFB",SFFB);
    para_map.put("SFYS",SFYS);
    para_map.put("REPORTTIME",REPORTTIME);
    para_map.put("TCHSFGS",TCHSFGS);
    para_map.put("SFSBBJLXH",SFSBBJLXH);
    para_map.put("SFSC",SFSC);
    para_map.put("CKXKZH",CKXKZH);
    para_map.put("SFZXCKXKZ",SFZXCKXKZ);
    para_map.put("CKXKZZXSJ",CKXKZZXSJ);
    para_map.put("CKXKZZXSFGG",CKXKZZXSFGG);
    para_map.put("AQSCXKZH",AQSCXKZH);
    para_map.put("SFSZHFA",SFSZHFA);
    para_map.put("SFZXAQSCXKZ",SFZXAQSCXKZ);
    para_map.put("AQSCXKZZXSJ",AQSCXKZZXSJ);
    para_map.put("AQSCXKZZXSFGG",AQSCXKZZXSFGG);
    para_map.put("HZWJH",HZWJH);
    para_map.put("SFCXHZWJ",SFCXHZWJ);
    para_map.put("HZWJCXSJ",HZWJCXSJ);
    para_map.put("HZWJSFGG",HZWJSFGG);
    para_map.put("SFSZHFA",SFSZHFA);
    para_map.put("SFRQTC",SFRQTC);
	para_map.put("SFLQCZJJ",SFLQCZJJ);
	para_map.put("PARENTID",PARENTID);
	if(request.getParameter("MTHC")!=null&&!("").equals(request.getParameter("MTHC"))){
		String mthbzbid = request.getParameter("MTHC");
		boolean	  update_result = coal_service.update(para_map);
		if(update_result==false){
			result_map.put("result","修改失败！");
		    return result_map;
		}
	}
	boolean result = coal_service.getinsert(para_map);
	result_map.put("result",result);
	return 	result_map;
	}
}
