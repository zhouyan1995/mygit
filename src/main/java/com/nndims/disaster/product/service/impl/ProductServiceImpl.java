package com.nndims.disaster.product.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nndims.disaster.product.comm.Utils;
import com.nndims.disaster.product.domain.DisasterDto;
import com.nndims.disaster.product.mapper.ProductMapper;
import com.nndims.disaster.product.service.ProductServie;
@Service
@Transactional
public class ProductServiceImpl implements ProductServie {
	Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private ProductMapper mapper;
	
	@Override
	public List<Map<String, Object>> getIndex() {
		List<Map<String, Object>> list=null;
		try {
			 list =mapper.getIndex();
			for(int i=0;i<list.size();i++){
				if(list.get(i).get("PNAME").equals("受灾人口")){
					list.get(i).put("PNAME", "受灾人口情况");
				}
				else if(list.get(i).get("PNAME").equals("倒损房屋") 
				|| list.get(i).get("PNAME").equals("直接经济损失")){
					list.get(i).put("PNAME", "损失情况");
				}else if(list.get(i).get("PNAME").equals("农作物牲畜")){
					list.get(i).put("PNAME", "农作物受灾情况");
				}
			}
			
		} catch (Exception e) {
			
			
			e.printStackTrace();
		}
		
		
		return list;
	}

	@Override
	public List<DisasterDto> findDisasterList(Date stime, Date etime,
			Integer disasterValidity, String disasterStateId, Integer level,
			List<String> flowActionStatusCodes) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected static final Integer[] PRIV_LEVELS = { 1 };
	protected static final Integer[] CITY_LEVELS = { 2 };
	protected static final Integer[] COUNTY_LEVELS = { 3, 5, 6 };
	protected static final Integer[] TOWN_LEVELS = { 4 };
	protected static final String[] FLOWACTION_STATUS_CODES = { "3", "5" };
	private List<Integer> assertLevel(Integer level) {
		return Arrays.asList((level == 4) ? TOWN_LEVELS
				: (level == 3) ? COUNTY_LEVELS : (level == 2) ? CITY_LEVELS
						: PRIV_LEVELS);
	}

	@Override
	public Map<String, Object> findReportData(List<String> reportIds,
			List<String> indexItemCodes, Integer level, Integer flag,
			String startTime, String endtime, HttpServletRequest req,
			HttpServletResponse resp, List<Map<String, Object>> index) {
			String dataType =Utils.getConfig("dataType");//数据类型
			Map map =new HashMap();
			try {
				//数据
				List<Map<String,Object>> list =mapper.findReportData(reportIds, indexItemCodes, assertLevel(level), level, dataType);
				//灾害类型
				List<Map<String,Object>> listType =mapper.findReportDataType(reportIds, indexItemCodes, assertLevel(level), level, dataType);
				//合计数据
				List<Map<String,Object>> Alllist =mapper.findAllReportData(reportIds, indexItemCodes, assertLevel(level), level, dataType);
				
				if(list != null && listType !=null){
					//创建excel
					HSSFWorkbook wb = new HSSFWorkbook();
					//用到的样式
					HSSFCellStyle cellStyle = wb.createCellStyle();  //样式 居中 （普通）
					HSSFCellStyle cellStyle1 = wb.createCellStyle();  //样式 靠右 无边框 (时间)
					HSSFCellStyle cellStyle5 = wb.createCellStyle();  //样式 字体 标题 无边框
					HSSFCellStyle cellStyle6 = wb.createCellStyle();  //样式 字体  有边框 水平 垂直 区中 (地区)
					HSSFCellStyle cellStyle7 = wb.createCellStyle();  //样式 字体 自动换行 (指标)
					
					//设置边框
					cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
					cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
					cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
					cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
					//设置边框
					cellStyle6.setBorderBottom(HSSFCellStyle.BORDER_THIN);
					cellStyle6.setBorderLeft(HSSFCellStyle.BORDER_THIN);
					cellStyle6.setBorderRight(HSSFCellStyle.BORDER_THIN);
					cellStyle6.setBorderTop(HSSFCellStyle.BORDER_THIN);
					//设置居中
				    cellStyle6.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
				    cellStyle6.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中  
					
				  //设置边框
				  	cellStyle7.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				  	cellStyle7.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				  	cellStyle7.setBorderRight(HSSFCellStyle.BORDER_THIN);
				  	cellStyle7.setBorderTop(HSSFCellStyle.BORDER_THIN);
				  //设置居中
				  	cellStyle7.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
				  	cellStyle7.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中  
				    //设置字体
					HSSFFont font = wb.createFont();
					font.setFontName("宋体");
					font.setFontHeightInPoints((short) 18);//设置字体大小 标题
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
					cellStyle5.setFont(font);
					
					HSSFFont font1 = wb.createFont();
					font1.setFontName("宋体");
					font1.setFontHeightInPoints((short) 10);//设置字体大小 (指标，省份)
					font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
					cellStyle6.setFont(font1);
					cellStyle7.setFont(font1);
					//指标个数 确定列数
					int zhibiao = indexItemCodes.size();
					int sz=0;//人口受灾情况  指标个数
					int nz=0;//农作物受灾情况 指标个数
					int ss=0;//损失情况 指标个数
					
					//确定父指标各个指标个数
					if(index != null){		
						for(int q =0;q<index.size();q++){
							if(index.get(q).get("PNAME").equals("人口受灾情况")){
								sz++;
							}else if(index.get(q).get("PNAME").equals("农作物受灾情况")){
								nz++;
							}else if(index.get(q).get("PNAME").equals("损失情况")){
								ss++;
							}
						}
					}
					//开始确定 多少个sheet页
					
				}else{
					map.put("result", "没有数据");
					map.put("flag", 2);
				}
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			
		
		
		return null;
	}
	

	

	/**
	 * serviceName：
	 * serviceType：
	 * serviceAddr：
	 * http:
	 * @param 
	 * @return 
	 * @author zy
	 */
}
