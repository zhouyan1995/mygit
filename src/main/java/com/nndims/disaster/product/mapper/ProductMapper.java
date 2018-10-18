package com.nndims.disaster.product.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nndims.disaster.product.domain.DisasterDto;

@Mapper
@Transactional
public interface ProductMapper {
			/*查询指标项*/
           List<Map<String,Object>> getIndex();
           	
           /*查询灾害列表*/
           List<DisasterDto> findDisasterList(
       			@Param("stime") Date stime, @Param("etime") Date etime,
       			@Param("disasterValidity") Integer disasterValidity,
       			@Param("disasterStateId") String disasterStateId,
       			@Param("level") Integer level,
       			@Param("flowActionStatusCodes") List<String> flowActionStatusCodes);
           /*查询灾害数值*/
       	 List<Map<String, Object>> findReportData(
       			@Param("reportIds") List<String> reportIds,
       			@Param("indexItemCodes") List<String> indexItemCodes,
       			@Param("levels") List<Integer> levels,
       			@Param("level") Integer level, @Param("dataType") String dataType);
       	 /*查询灾害类型*/
       	 List<Map<String, Object>> findReportDataType(
       			@Param("reportIds") List<String> reportIds,
       			@Param("indexItemCodes") List<String> indexItemCodes,
       			@Param("levels") List<Integer> levels,
       			@Param("level") Integer level, @Param("dataType") String dataType);
       	/*查询合计各省数值*/
       	 List<Map<String, Object>> findAllReportData(
       			@Param("reportIds") List<String> reportIds,
       			@Param("indexItemCodes") List<String> indexItemCodes,
       			@Param("levels") List<Integer> levels,
       			@Param("level") Integer level, @Param("dataType") String dataType);
}
