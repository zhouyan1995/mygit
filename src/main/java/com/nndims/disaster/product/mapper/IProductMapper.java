/**
 * 
 */
package com.nndims.disaster.product.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nndims.disaster.product.domain.DisasterDto;

/**
 * @author muxl
 * @date 2018年4月10日
 * @time 下午4:44:38
 */
@Mapper
@Transactional
abstract public interface IProductMapper {

	abstract public List<DisasterDto> findDisasterList(
			@Param("stime") Date stime, @Param("etime") Date etime,
			@Param("disasterValidity") Integer disasterValidity,
			@Param("disasterStateId") String disasterStateId,
			@Param("level") Integer level,
			@Param("flowActionStatusCodes") List<String> flowActionStatusCodes);

	abstract public List<Map<String, Object>> findReportData(
			@Param("reportIds") List<String> reportIds,
			@Param("indexItemCodes") List<String> indexItemCodes,
			@Param("levels") List<Integer> levels,
			@Param("level") Integer level, @Param("dataType") String dataType);

	abstract public List<Map<String, Object>> findReportDataType(
			@Param("reportIds") List<String> reportIds,
			@Param("indexItemCodes") List<String> indexItemCodes,
			@Param("levels") List<Integer> levels,
			@Param("level") Integer level, @Param("dataType") String dataType);
	abstract public List<Map<String, Object>> findAllReportData(
			@Param("reportIds") List<String> reportIds,
			@Param("indexItemCodes") List<String> indexItemCodes,
			@Param("levels") List<Integer> levels,
			@Param("level") Integer level, @Param("dataType") String dataType);
	
}
