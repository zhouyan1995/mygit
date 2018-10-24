package com.nndims.disaster.product.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.nndims.disaster.product.domain.DisasterDto;

@Transactional
public interface ProductServie {
	
	/*查询指标项*/
    List<Map<String,Object>> getIndex();
    	
    /*查询灾害列表*/
    List<DisasterDto> findDisasterList(
			@Param("stime") Long stime, @Param("etime") Long etime,Integer currentPage,Integer pageSize,List<String>ids)
			;
    /*查询灾害列表 本年以来*/
    List<DisasterDto> findDisasterListByYear(
			@Param("stime") Long stime, @Param("etime") Long etime,Integer currentPage,Integer pageSize,List<String>ids)
			;
    /*查询灾害数值*/
	 Map<String, Object> findReportData(
			 List<String> reportIds,
			 List<String> indexItemCodes, Integer level, Integer flag,//1今年以来  0本月以来
			 String startTime, String endtime,HttpServletRequest req, HttpServletResponse resp,
			 List<Map<String,Object>> index);
	 /*查询省的名称*/
   	 List<Map<String, Object>> getProName();
   	int findDisasterListCountByYear(	@Param("stime") Long stime, @Param("etime") Long etime,Integer currentPage,Integer pageSize,List<String>ids);
   	 int findDisasterListCountByMonth(	@Param("stime") Long stime, @Param("etime") Long etime,Integer currentPage,Integer pageSize,List<String>ids);
	
}
