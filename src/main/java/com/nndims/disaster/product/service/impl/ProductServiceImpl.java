package com.nndims.disaster.product.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
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
				}else if(list.get(i).get("PNAME").equals("农作物牲畜")){
					list.get(i).put("PNAME", "农作物受灾情况");
				}
				else if(list.get(i).get("PNAME").equals("倒损房屋") 
				|| list.get(i).get("PNAME").equals("直接经济损失")){
					list.get(i).put("PNAME", "损失情况");
				}
			}
			
		} catch (Exception e) {
			
			
			e.printStackTrace();
		}
		
		
		return list;
	}

	@Override
	public List<DisasterDto> findDisasterList(Long stime, Long etime,Integer currentPage,Integer pageSize,List<String>ids) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<DisasterDto> list =mapper.findDisasterList(new Date(stime), new Date(etime), DISASTER_VALIDITY, DISASTER_STATE_ID, PRIV_LEVELS[0],
				Arrays.asList(FLOWACTION_STATUS_CODES),currentPage,pageSize,ids);
		return list;
	}
	protected static final Integer DISASTER_VALIDITY = 0;
	protected static final String DISASTER_STATE_ID = "0";
	protected static final String INDEXITEM_DATA_TYPE = "NUMBER";
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
			//String dataType =Utils.getConfig("dataType");//数据类型
			Map map =new HashMap();
			try {
				//数据
				List<Map<String,Object>> list =mapper.findReportData(reportIds, indexItemCodes, assertLevel(level), level, INDEXITEM_DATA_TYPE);
				//灾害类型
				List<Map<String,Object>> listType =mapper.findReportDataType(reportIds, indexItemCodes, assertLevel(level), level, INDEXITEM_DATA_TYPE);
				//合计数据
				List<Map<String,Object>> Alllist =mapper.findAllReportData(reportIds, indexItemCodes, assertLevel(level), level, INDEXITEM_DATA_TYPE);
				//合计数据 合计行
				List<Map<String,Object>> AllListValue =mapper.getAllReportValue(reportIds, indexItemCodes, assertLevel(level), level, INDEXITEM_DATA_TYPE);
				//分省合计行的值
				List<Map<String,Object>> listValue =mapper.findReportDataValue(reportIds, indexItemCodes, assertLevel(level), level, INDEXITEM_DATA_TYPE);
				if(list != null && listType !=null){
					//创建excel
					HSSFWorkbook wb = new HSSFWorkbook();
					//用到的样式
					HSSFCellStyle cellStyle = wb.createCellStyle();  //样式 居中 （普通）
					HSSFCellStyle cellStyle1 = wb.createCellStyle();  //样式 靠右 无边框 (时间)
					HSSFCellStyle cellStyle5 = wb.createCellStyle();  //样式 字体 标题 无边框
					HSSFCellStyle cellStyle6 = wb.createCellStyle();  //样式 字体  有边框 水平 垂直 区中 (地区)
					HSSFCellStyle cellStyle7 = wb.createCellStyle();  //样式 字体 自动换行 (指标)
					//靠右
					cellStyle1.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 右
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
				  //设置居中
				    cellStyle5.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
				    cellStyle5.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
				  //设置居中
				    cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
				    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
				    
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
							if(index.get(q).get("PNAME").equals("受灾人口情况")){
								sz++;
							}else if(index.get(q).get("PNAME").equals("农作物受灾情况")){
								nz++;
							}else if(index.get(q).get("PNAME").equals("损失情况")){
								ss++;
							}
						}
					}
					int sencodCount =0;//中间数值  确定第二行
					if(indexItemCodes.size() %2 ==0){
						sencodCount =indexItemCodes.size() /2;
					}else{
						sencodCount =indexItemCodes.size() /2-1;
					}
					
					//受灾人口是万人次
					for(int u=0;u<list.size();u++){
						if(list.get(u).get("INDEXITEMNAME").toString().contains("因旱需生活救助人口") || 
								list.get(u).get("INDEXITEMNAME").toString().contains("受灾人口")	||
								list.get(u).get("INDEXITEMNAME").toString().contains("因灾死亡人口") ||
								list.get(u).get("INDEXITEMNAME").toString().contains("因灾失踪人口") ||
								list.get(u).get("INDEXITEMNAME").toString().contains("因灾伤病人口") ||
								list.get(u).get("INDEXITEMNAME").toString().contains("紧急转移安置人口") ||
								list.get(u).get("INDEXITEMNAME").toString().contains("被困人口") ||
								list.get(u).get("INDEXITEMNAME").toString().contains("需紧急生活救助人口") ||
								list.get(u).get("INDEXITEMNAME").toString().contains("需过渡性救助人口")
								){
							Double d =Double.valueOf(list.get(u).get("REPORTITEMVALUE").toString())/10000;
							list.get(u).put("REPORTITEMVALUE", d);
						}
						
					}
					if(listValue != null ){
						//受灾人口是万人次
						for(int u=0;u<listValue.size();u++){
							if(listValue.get(u).get("INDEXITEMNAME").toString().contains("因旱需生活救助人口") || 
									listValue.get(u).get("INDEXITEMNAME").toString().contains("受灾人口")	||
									listValue.get(u).get("INDEXITEMNAME").toString().contains("因灾死亡人口") ||
									listValue.get(u).get("INDEXITEMNAME").toString().contains("因灾失踪人口") ||
									listValue.get(u).get("INDEXITEMNAME").toString().contains("因灾伤病人口") ||
									listValue.get(u).get("INDEXITEMNAME").toString().contains("紧急转移安置人口") ||
									listValue.get(u).get("INDEXITEMNAME").toString().contains("被困人口") ||
									listValue.get(u).get("INDEXITEMNAME").toString().contains("需紧急生活救助人口") ||
									listValue.get(u).get("INDEXITEMNAME").toString().contains("需过渡性救助人口")
									){
								Double d =Double.valueOf(listValue.get(u).get("REPORTITEMVALUE").toString())/10000;
								listValue.get(u).put("REPORTITEMVALUE", d);
							}
							
						}
					}
					//开始确定 多少个sheet页 加上合计的sheet页
					if(list != null){
						
						for(int w =0 ;w<listType.size()+1;w++){
							// 使用wb创建HSSFSheet对象，对应一页
							HSSFSheet sheet = wb.createSheet();
							for (int r =1;r<indexItemCodes.size();r++){
								sheet.setColumnWidth(r, 1800); //行距大小
							}
							cellStyle7.setWrapText(true);//设置自动换行 指标 
							if(w==0){
								wb.setSheetName(0, "合计"); //第一个sheet名称
							}else{
							wb.setSheetName(w, listType.get(w-1).get("DISASTERNAME").toString());//sheet名称  有序的
							}
							//创建第一行
							HSSFRow row = sheet.createRow(0);//创建sheet的第一行
							HSSFCell cell = row.createCell(0); //第一列
							if(w==0){
								String name= flag==1 ? "今年以来灾情报表(合计)" : "本月以来灾情快报(合计)";
								cell.setCellValue(name);
								cell.setCellStyle(cellStyle5);
							}
							else{
								String name= flag==1 ? "今年以来灾情报表("+listType.get(w-1).get("DISASTERNAME").toString()+")" : "本月以来灾情快报("+listType.get(w-1).get("DISASTERNAME").toString()+")";
								cell.setCellValue(name);
								cell.setCellStyle(cellStyle5);//居中无边框
							}
							sheet.addMergedRegion(//合并
									new CellRangeAddress(
											0, //起始行
											0,//结束行
											0,//起始列
											indexItemCodes.size()//结束列
											)
										);
							//第二行
							HSSFRow row2 = sheet.createRow(1);//创建sheet的第一行
							HSSFCell cell1 = row2.createCell(0); //第一列
							HSSFCell cell2 = row2.createCell(1); //第二列
							HSSFCell celln = row2.createCell(sencodCount+1); //最后一列
							cell1.setCellValue("填报单位");
							cell2.setCellValue("民政部国家减灾中心");
							celln.setCellValue("起止日期:"+startTime);
							celln.setCellStyle(cellStyle1); //靠右
							sheet.addMergedRegion(//合并民政部国家减灾中心
									new CellRangeAddress(
											1, //起始行
											1,//结束行
											1,//起始列
											sencodCount//结束行
											)
									
										);
							sheet.addMergedRegion(//合并起止日期
									new CellRangeAddress(
											1, //起始行
											1,//结束行
											sencodCount+1,//起始列
											indexItemCodes.size()//结束列
											)
									
										);
							
							//第三行
							HSSFRow row3 = sheet.createRow(2);//创建sheet的第一行
							for(int ii=0;ii<=indexItemCodes.size();ii++){
								if(ii==0 || ii==1 ||  ii==sz+1 || ii==sz+nz+1){
									
								}else{
								//三行的列
								HSSFCell cell31_12 = row3.createCell(ii); 
								cell31_12.setCellStyle(cellStyle); //边框
								}
								
							}
							HSSFCell cell3_1 = row3.createCell(0); //第一列
							cell3_1.setCellValue("地区");
							cell3_1.setCellStyle(cellStyle6); //居中 边框
							sheet.addMergedRegion(//合并
									new CellRangeAddress(
											2, //起始行
											4,//结束行
											0,//起始列
											0//结束行
											)
									
										);
							HSSFCell cell3_2 = row3.createCell(1); //第二列
							cell3_2.setCellValue("受灾人口情况");
							cell3_2.setCellStyle(cellStyle6); //居中 边框
							if(sz!=1){
							sheet.addMergedRegion(//合并 人口受灾情况
									new CellRangeAddress(
											2, //起始行
											2,//结束行
											1,//起始列
											sz//结束行
											)
									
										);}
							//设置边框
							
							HSSFCell cell3_n = row3.createCell(sz+1); //第n列
							cell3_n.setCellValue("农作物受灾情况");
							cell3_n.setCellStyle(cellStyle6); //居中 边框
							if(nz>1){
							sheet.addMergedRegion(//合并 农作物受灾情况
									new CellRangeAddress(
											2, //起始行
											2,//结束行
											sz+1,//起始列
											sz+nz//结束行
											)
									
										);}
							//设置边框
							HSSFCell cell3_n_1 = row3.createCell(sz+nz+1); //第n列
							cell3_n_1.setCellValue("损失情况");
							cell3_n_1.setCellStyle(cellStyle6); //居中 边框
							if(sz+nz+1<indexItemCodes.size()){
							sheet.addMergedRegion(//合并 农作物受灾情况
									new CellRangeAddress(
											2, //起始行
											2,//结束行
											sz+nz+1,//起始列
											indexItemCodes.size()//结束行
											)
									
										);}
							//设置边框
							HSSFRow	 row40 = sheet.createRow(39);//创建sheet的第40行
							HSSFCell cell401 = row40.createCell(0); 
							HSSFCell cell402 = row40.createCell(1); 
							cell401.setCellValue("编制部门:");
							cell402.setCellValue("数据中心");
							
							//第四行 第五行
							int sz1=0;
							int nz1=0;
							int e1=0;
							for(int x =3 ;x<5;x++){
								HSSFRow	 row4 = sheet.createRow(x);
								for(int c =1;c<=indexItemCodes.size();c++){
									HSSFCell cell4_1 = row4.createCell(c); //人口
									if(x==3){
										cell4_1.setCellStyle(cellStyle7); //居中 边框
									}else{
										cell4_1.setCellStyle(cellStyle); //居中 边框
									}
								}
							}
							
							List<String> add =new ArrayList();
							List<String> add1 =new ArrayList();
							for(int e =0;e<indexItemCodes.size()+1;e++){
								add.add(e+"");
							}
							for(int e =0;e<indexItemCodes.size();e++){
								
									if(index.get(e).get("PNAME").equals("受灾人口情况")){
										sheet.getRow(3).getCell(e1+1).setCellValue(index.get(e).get("NAME").toString());
										sheet.getRow(4).getCell(e1+1).setCellValue("(万"+index.get(e).get("INDEXITEMUNIT").toString()+")");
										add.set(e1+1, index.get(e).get("NAME").toString());
										e1++;
									}else if(index.get(e).get("PNAME").equals("农作物受灾情况")){
										sheet.getRow(3).getCell(sz+1+sz1).setCellValue(index.get(e).get("NAME").toString());
										sheet.getRow(4).getCell(sz+1+sz1).setCellValue("("+index.get(e).get("INDEXITEMUNIT").toString()+")");
										add.set(sz+1+sz1, index.get(e).get("NAME").toString());
										sz1++;
									}else if(index.get(e).get("PNAME").equals("损失情况")){
										sheet.getRow(3).getCell(nz+sz+nz1+1).setCellValue(index.get(e).get("NAME").toString());
										sheet.getRow(4).getCell(nz+sz+nz1+1).setCellValue("("+index.get(e).get("INDEXITEMUNIT").toString()+")");
										add.set(nz+sz+nz1+1, index.get(e).get("NAME").toString());
										nz1++;
									}
								}
							for(int e =0;e<indexItemCodes.size();e++){
								add1.add(add.get(e+1));
								/*System.out.println(add1.get(e)+"---1111");*/
							}
							
							/*}*/
							//剩下的excel
							for(int r=5;r<39;r++){
								HSSFRow	 rowr = sheet.createRow(r);
								for(int t=0;t<=indexItemCodes.size();t++){
									HSSFCell cellt = rowr.createCell(t); //损失
									if(t==0){
										cellt.setCellStyle(cellStyle6);
									}else{
										cellt.setCellStyle(cellStyle);
									}
								}
							}
							//填数
							HSSFCell cell6_1 = sheet.getRow(5).getCell(0);//列
							cell6_1.setCellValue("合计");
							for(int y =7;y<39;y++){//省份
								sheet.getRow(y).getCell(0).setCellValue(new HSSFRichTextString(Utils.getPro().get(y-7)));
							}
							
							//查询数据
							//合计
							//if(listType.get(w).get("DISASTERNAME").toString().)
							if(w==0){//总计不分载种
								//受灾人口是万人次
								for(int u=0;u<Alllist.size();u++){
									if(Alllist.get(u).get("INDEXITEMNAME").toString().contains("因旱需生活救助人口") || 
											Alllist.get(u).get("INDEXITEMNAME").toString().contains("受灾人口")	||
											Alllist.get(u).get("INDEXITEMNAME").toString().contains("因灾死亡人口") ||
											Alllist.get(u).get("INDEXITEMNAME").toString().contains("因灾失踪人口") ||
											Alllist.get(u).get("INDEXITEMNAME").toString().contains("因灾伤病人口") ||
											Alllist.get(u).get("INDEXITEMNAME").toString().contains("紧急转移安置人口") ||
											Alllist.get(u).get("INDEXITEMNAME").toString().contains("被困人口") ||
											Alllist.get(u).get("INDEXITEMNAME").toString().contains("需紧急生活救助人口") ||
											Alllist.get(u).get("INDEXITEMNAME").toString().contains("需过渡性救助人口")
											){
										Double d =Double.valueOf(Alllist.get(u).get("REPORTITEMVALUE").toString())/10000;
										Alllist.get(u).put("REPORTITEMVALUE", d);
									}
									
								}
								for(int u=0;u<AllListValue.size();u++){
									if(AllListValue.get(u).get("INDEXITEMNAME").toString().contains("因旱需生活救助人口") || 
											AllListValue.get(u).get("INDEXITEMNAME").toString().contains("受灾人口")	||
											AllListValue.get(u).get("INDEXITEMNAME").toString().contains("因灾死亡人口") ||
											AllListValue.get(u).get("INDEXITEMNAME").toString().contains("因灾失踪人口") ||
											AllListValue.get(u).get("INDEXITEMNAME").toString().contains("因灾伤病人口") ||
											AllListValue.get(u).get("INDEXITEMNAME").toString().contains("紧急转移安置人口") ||
											AllListValue.get(u).get("INDEXITEMNAME").toString().contains("被困人口") ||
											AllListValue.get(u).get("INDEXITEMNAME").toString().contains("需紧急生活救助人口") ||
											AllListValue.get(u).get("INDEXITEMNAME").toString().contains("需过渡性救助人口")
											){
										Double d =Double.valueOf(AllListValue.get(u).get("REPORTITEMVALUE").toString())/10000;
										AllListValue.get(u).put("REPORTITEMVALUE", d);
									}
									
								}
								//设置合计行的值
								for(int f =0;f<AllListValue.size();f++){
									for(int b =0; b<add1.size();b++){//多少个指标名称就多少列
										if(AllListValue.get(f).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72_2 = sheet.getRow(5).getCell(b+1);//列
												cell72_2.setCellValue(Double.valueOf(AllListValue.get(f).get("REPORTITEMVALUE").toString()));//设置值
											break;
										}
									}
								}
								if(Alllist != null){
									for(int j=0;j<Alllist.size();j++){
										if(Alllist.get(j).get("PROVNAME").toString().contains("兵团"))//是兵团的 
										{
										for(int b =0; b<index.size();b++){//多少个指标名称就多少列
											if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet.getRow(38).getCell(b+1);//列
													cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
												
											//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										
										else if(Alllist.get(j).get("PROVNAME").toString().contains("新疆"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(37).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
												}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("宁夏"))//是天津的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(36).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("青海"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(35).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("甘肃"))//是北京的 
										{
										
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(34).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("陕西"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(33).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("西藏"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(32).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("云南"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(31).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("贵州"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(30).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("四川"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(29).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("重庆"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(28).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("海南"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(27).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("广西"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(26).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("广东"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(25).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("湖南"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(24).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("湖北"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(23).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("河南"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(22).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("山东"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(21).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("江西"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(20).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("福建"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(19).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("安徽"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(18).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("浙江"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(17).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("江苏"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(16).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("上海"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(15).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("黑龙江"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(14).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("吉林"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(13).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("辽宁"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(12).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("内蒙古"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(11).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("山西"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(10).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("河北"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(9).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("天津"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(8).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
										else if(Alllist.get(j).get("PROVNAME").toString().contains("北京"))//是北京的 
										{
											for(int b =0; b<index.size();b++){//多少个指标名称就多少列
												if(Alllist.get(j).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
													HSSFCell cell72_2 = sheet.getRow(7).getCell(b+1);//列
														cell72_2.setCellValue(Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//设置值
													
												//	u1.addValue1(b+1, Double.valueOf(Alllist.get(j).get("REPORTITEMVALUE").toString()));//计算总值
													break;
											}
										}
									}
								}
							}
							
						}
						else{
							//合计的值
							for(int c =0;c<listValue.size();c++){
								//分灾种
								if(listValue.get(c).get("DISASTERNAME").toString().contains(listType.get(w-1).get("DISASTERNAME").toString()))//是同一个灾害类型{
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(listValue.get(c).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(5).getCell(b+1);//列
											cell72.setCellValue(Double.valueOf(listValue.get(c).get("REPORTITEMVALUE").toString()));//设置值
											break;
										}
									}	
								}
							
							
							
								//这是分灾种
							for(int a =0;a<list.size();a++){
								//北京
								if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("北京")//是北京的 
										){
										
										for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
											if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
												HSSFCell cell72 = sheet.getRow(7).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.keepTwoBit(list.get(a).get("REPORTITEMVALUE")+""));//设置值
												
													cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
												
												
											//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
								
								//天津
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("天津")//是天津的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(8).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//河北
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("河北")//是河北的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(9).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//山西
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("山西")//是山西的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(10).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//内蒙古
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("内蒙古")//是内蒙古的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(11).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//辽宁
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("辽宁")//是辽宁的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(12).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//吉林
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("吉林")//是吉林的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(13).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//黑龙江
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("黑龙江")//是黑龙江的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(14).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//上海
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("上海")//是上海的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(15).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//江苏
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("江苏")//是江苏的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(16).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//浙江
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("浙江")//是浙江的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(17).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//安徽
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("安徽")//是安徽的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(18).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//福建
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("福建")//是福建的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(19).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//江西
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("江西")//是江西的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(20).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//山东
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("山东")//是山东的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(21).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//河南
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("河南")//是河南的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(22).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//湖北
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("湖北")//是湖北的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(23).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//湖南
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("湖南")//是湖南的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(24).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//广东
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("广东")//是广东的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(25).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//广西
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("广西")//是广西的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(26).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//海南
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("海南")//是海南的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(27).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//重庆
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("重庆")//是重庆的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(28).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//四川
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("四川")//是四川的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(29).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//贵州
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("贵州")//是贵州的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(30).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//云南
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("云南")//是云南的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(31).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//西藏
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("西藏")//是西藏的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(32).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//陕西
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("陕西")//是陕西的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(33).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//甘肃
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("甘肃")//是甘肃的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(34).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//青海
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("青海")//是青海的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(35).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//宁夏
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("宁夏")//是宁夏的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(36).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//新疆
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("新疆")//是新疆的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(37).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								
								//兵团
								else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(w-1).get("DISASTERNAME").toString()) &&//是同一个灾害类型
										list.get(a).get("PROVNAME").toString().contains("兵团")//是兵团的 
										){
									for(int b =0; b<index.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(add1.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(38).getCell(b+1);//列
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
										//	u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
											}
										}
									}
								}
							
							
						}
					}
						
						
					}
					//转io
					// 把wb转化成excel文件
					String name1="";
					//文件地址
					String path =Utils.getConfig("excelPath");
					//判断文件路劲是否存在
					File file =new File(path+"year\\");
					File file1 =new File(path+"month\\");
					if(!file.exists()){
						file.mkdirs();
					}
					if(!file1.exists()){
						file1.mkdirs();
					}

					//文件名称
					String excelName="";
					if(flag ==1){
						name1="今年以来灾情报表";
						 excelName= endtime+"_"+name1+new Date().getTime();
						OutputStream os = new FileOutputStream(path+"year\\"+excelName+".xls");
						map.put("path",path+"year\\"+excelName+".xls");
						wb.write(os);
						os.close();	
					}else{
						name1="本月以来灾情报表";
						 excelName= endtime+"_"+name1+new Date().getTime();
						OutputStream os = new FileOutputStream(path+"month\\"+excelName+".xls");
						map.put("path", path+"month\\"+excelName+".xls");
						wb.write(os);
						os.close();
					}
					
					map.put("result", "success");
					map.put("flag", 1); //下载成功
					map.put("message", "下载成功");
					
				}else{
					map.put("result", "没有数据");
					map.put("flag", 2);
				}
				
				
			} catch (Exception e) {
				map.put("result", "fail");
				map.put("flag", 1); //下载成功
				map.put("message", "下载失败");
				e.printStackTrace();
			}
			
			
		
		
		return map;
	}

	@Override
	public List<DisasterDto> findDisasterListByYear(Long stime, Long etime,
			Integer currentPage, Integer pageSize,List<String>ids) {
		List<DisasterDto> list =mapper.findDisasterListByYear(new Date(stime), new Date(etime), DISASTER_VALIDITY, DISASTER_STATE_ID, PRIV_LEVELS[0],
				Arrays.asList(FLOWACTION_STATUS_CODES),currentPage,pageSize,ids);
		return list;
	}

	@Override
	public List<Map<String, Object>> getProName() {
		// TODO Auto-generated method stub
		return mapper.getProName();
	}

	@Override
	public int findDisasterListCountByYear(Long stime, Long etime,
			Integer currentPage,
			Integer pageSize, List<String> ids) {
		// TODO Auto-generated method stub
		return mapper.findDisasterListCountByYear(new Date(stime), new Date(etime), DISASTER_VALIDITY, DISASTER_STATE_ID, PRIV_LEVELS[0],
				Arrays.asList(FLOWACTION_STATUS_CODES),currentPage,pageSize,ids);
				
	}

	@Override
	public int findDisasterListCountByMonth(Long stime, Long etime,
			 Integer currentPage,
			Integer pageSize, List<String> ids) {
		// TODO Auto-generated method stub
		return mapper.findDisasterListCountByMonth(new Date(stime), new Date(etime), DISASTER_VALIDITY, DISASTER_STATE_ID, PRIV_LEVELS[0],
				Arrays.asList(FLOWACTION_STATUS_CODES),currentPage,pageSize,ids);
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
