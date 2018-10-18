/**
 * 
 */
package com.nndims.disaster.product.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nndims.disaster.product.comm.DoubleKeepTwo;
import com.nndims.disaster.product.comm.Utils;
import com.nndims.disaster.product.domain.DisasterDto;
import com.nndims.disaster.product.mapper.IProductMapper;
import com.nndims.disaster.product.service.base.ABBaseService;

/**
 * @author muxl
 * @date 2018年10月8日
 * @time 下午6:13:16
 */
@Service
@Transactional
abstract public class ProductService extends ABBaseService implements
		IProductService {

	Logger log = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private IProductMapper productMapper;
	protected static final Integer DISASTER_VALIDITY = 0;
	protected static final String DISASTER_STATE_ID = "0";
	protected static final String INDEXITEM_DATA_TYPE = "NUMBER";
	protected static final Integer[] PRIV_LEVELS = { 1 };
	protected static final Integer[] CITY_LEVELS = { 2 };
	protected static final Integer[] COUNTY_LEVELS = { 3, 5, 6 };
	protected static final Integer[] TOWN_LEVELS = { 4 };
	protected static final String[] FLOWACTION_STATUS_CODES = { "3", "5" };

	@Override
	public List<DisasterDto> findDisasterList(Long stime, Long etime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("开始日期: " + format.format(new Date(stime)));
		log.debug("结束日期: " + format.format(new Date(etime)));
		return productMapper.findDisasterList(new Date(stime), new Date(etime),
				DISASTER_VALIDITY, DISASTER_STATE_ID, PRIV_LEVELS[0],
				Arrays.asList(FLOWACTION_STATUS_CODES));
	}

	@Override
	public List<Map<String, Object>> findReportData(List<String> reportIds,
			List<String> indexItemCodes, Integer level, Integer flag,//1今年以来  0本月以来
			String startTime, String endtime,HttpServletRequest req, HttpServletResponse resp) {
		log.debug(reportIds.toString());
		log.debug(indexItemCodes.toString());
		log.debug(level.toString());
		List<Map<String, Object>> list = productMapper.findReportData(//数据
				reportIds, indexItemCodes, assertLevel(level), level,
				INDEXITEM_DATA_TYPE);
		log.debug(list.toString());
		// 生成excel
		List<Map<String, Object>> ret =new ArrayList();
		Map map =new HashMap();
		HSSFWorkbook wb = new HSSFWorkbook();//创建excel
	//	HSSFCellStyle cellStyleCenter = Utils.initColumnHeadStyle(wb);//表头样工
		List<String> zhibiao = new ArrayList();
		HSSFCellStyle cellStyle = wb.createCellStyle();  //样式 居中
		HSSFCellStyle cellStyle1 = wb.createCellStyle();  //样式 靠右
		HSSFCellStyle cellStyle2 = wb.createCellStyle();  //样式 靠左
		HSSFCellStyle cellStyle4 = wb.createCellStyle();  //样式 没有边框
		HSSFCellStyle cellStyle5 = wb.createCellStyle();  //样式 字体 标题
		HSSFCellStyle cellStyle6 = wb.createCellStyle();  //样式 字体 
		HSSFCellStyle cellStyle7 = wb.createCellStyle();  //样式 字体 自动换行
		
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
	  //自动换行
	  	  
	  	
	  	  
	    //设置字体
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 18);//设置字体大小 标题
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		cellStyle5.setFont(font);
		
		HSSFFont font1 = wb.createFont();
		font1.setFontName("宋体");
		font1.setFontHeightInPoints((short) 10);//设置字体大小 标题
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		cellStyle6.setFont(font1);
		cellStyle7.setFont(font1);
		for(int j=0;j<indexItemCodes.size();j++){//指标中文名称
			if(indexItemCodes.get(j).equals("A008")){//前台按顺序返回
				zhibiao.add("受灾人口"); 
			}
			else if(indexItemCodes.get(j).equals("A009")){
				zhibiao.add("死亡人口");
			}
			else if(indexItemCodes.get(j).equals("A010")){
				zhibiao.add("失踪人口");
			}
			else if(indexItemCodes.get(j).equals("A012")){
				zhibiao.add("紧急转移安置人口");
			}
			else if(indexItemCodes.get(j).equals("A015")){
				zhibiao.add("需紧急生活救助人口");
			}else if(indexItemCodes.get(j).equals("A020")){
				zhibiao.add("受灾面积");
			}else if(indexItemCodes.get(j).equals("A022")){
				zhibiao.add("绝收面积");
			}else if(indexItemCodes.get(j).equals("A026")){
				zhibiao.add("倒塌房屋");
			}else if(indexItemCodes.get(j).equals("A030")){
				zhibiao.add("严重损坏房屋");
			}
			else if(indexItemCodes.get(j).equals("A034")){
				zhibiao.add("一般损坏房屋");
			}else if (indexItemCodes.get(j).equals("A043")){
				zhibiao.add("直接经济损失");
			}
		}
		int size =zhibiao.size();//指标的个数
		try {
			List<Map<String, Object>> listType = productMapper.findReportDataType(//灾害类型
					reportIds, indexItemCodes, assertLevel(level), level,
					INDEXITEM_DATA_TYPE);
			//总计
			Utils u =new Utils();
			Utils u1 =new Utils();
			HSSFSheet sheet1 =wb.createSheet("合计");
			int count=	indexItemCodes.size();//第一行合并的列数
			int sencodCount =0;
			if(count %2 ==0){
				sencodCount =count /2;
			}else{
				sencodCount =count /2-1;
			}
			if(list != null){
				//HSSFSheet sheet = wb.createSheet("合计");
				for(int i =0 ;i<listType.size();i++){//灾害类型  
					
					// 使用wb创建HSSFSheet对象，对应一页
					HSSFSheet sheet = wb.createSheet();
					for (int r =1;r<12;r++){
						sheet.setColumnWidth(r, 1800);
					}
					cellStyle7.setWrapText(true);//设置自动换行  
					wb.setSheetName(i+1,  listType.get(i).get("DISASTERNAME").toString());//sheet名称
					HSSFRow row = sheet.createRow(0);//创建sheet的第一行
					HSSFCell cell = row.createCell(0); //第一列
					String name= flag==1 ? "今年以来灾情报表("+listType.get(i).get("DISASTERNAME").toString()+")" : "本月以来灾情快报("+listType.get(i).get("DISASTERNAME").toString()+")";
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 
					cellStyle5.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 无边框
					cell.setCellValue(name);//第一行名称
					cell.setCellStyle(cellStyle5);
					
					sheet.addMergedRegion(//合并
						new CellRangeAddress(
								0, //起始行
								0,//结束行
								0,//起始列
								count//结束行
								)
							);
					//第二行
					HSSFRow	 row2 = sheet.createRow(1);//创建sheet的第二行
					HSSFCell cell1 = row2.createCell(0); //第二行第一列
					HSSFCell cell2 = row2.createCell(1); //第二行第二列
					HSSFCell cell3 = row2.createCell(sencodCount+1); //第二行第三列
					cell1.setCellValue("填报单位");
					cell2.setCellValue("民政部国家减灾中心");
					cell2.setCellStyle(cellStyle2);
					cellStyle1.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 右
					cell3.setCellValue("起止日期:"+startTime);
					cell3.setCellStyle(cellStyle1);
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
									count//结束列
									)
							
								);
					
					
					
					//第三行 
					HSSFRow	 row3 = sheet.createRow(2);//创建sheet的第三行
					HSSFRow	 row4 = sheet.createRow(3);//创建sheet的第4行
					HSSFRow	 row5 = sheet.createRow(4);//创建sheet的第4行
					HSSFCell cell31 = row3.createCell(0); //第三行第一列
					HSSFCell cell320 = row3.createCell(1); //第三行第二列
					HSSFCell cell330 = row3.createCell(2); //第三行第三列
					HSSFCell cell340 = row3.createCell(3); //第三行第三列
					HSSFCell cell350 = row3.createCell(4); //第三行第三列
					HSSFCell cell360 = row3.createCell(5); //第三行第三列
					HSSFCell cell380 = row3.createCell(7); //第三行第三列
					HSSFCell cell310 = row3.createCell(9); //第三行第三列
					HSSFCell cell3100 = row3.createCell(10); //第三行第三列
					HSSFCell cell3110 = row3.createCell(11);
					HSSFCell cell4100 = row4.createCell(0); //第三行第三列
					HSSFCell cell5110 = row5.createCell(0);
					
					cell4100.setCellStyle(cellStyle);
					cell5110.setCellStyle(cellStyle);
					
					cell320.setCellStyle(cellStyle);
					cell330.setCellStyle(cellStyle);
					cell31.setCellStyle(cellStyle);
					cell340.setCellStyle(cellStyle);
					cell350.setCellStyle(cellStyle);
					cell360.setCellStyle(cellStyle);
					cell380.setCellStyle(cellStyle);
					cell310.setCellStyle(cellStyle);
					cell3100.setCellStyle(cellStyle);
					cell3110.setCellStyle(cellStyle);
					
					HSSFCell cell30 = row3.createCell(1);
					cell30.setCellStyle(cellStyle);
					HSSFCell cell36 = row3.createCell(6);
					cell36.setCellStyle(cellStyle);
					HSSFCell cell38 = row3.createCell(8);
					cell38.setCellStyle(cellStyle);
					cell31.setCellValue("地区");
					cell31.setCellStyle(cellStyle6);
					sheet.addMergedRegion(//合并
							new CellRangeAddress(
									2, //起始行
									4,//结束行
									0,//起始列
									0//结束行
									)
							
								);
					cell30.setCellValue("人口受灾情况");
					cell30.setCellStyle(cellStyle6);
					sheet.addMergedRegion(//合并
							new CellRangeAddress(
									2, //起始行
									2,//结束行
									1,//起始列
									5//结束行
									)
							
								);
					cell36.setCellValue("农作物受灾情况");
					cell36.setCellStyle(cellStyle6);
					sheet.addMergedRegion(//合并
							new CellRangeAddress(
									2, //起始行
									2,//结束行
									6,//起始列
									7//结束行
									)
							
								);
					cell38.setCellValue("损失情况");
					cell38.setCellStyle(cellStyle6);
					sheet.addMergedRegion(//合并
							new CellRangeAddress(
									2, //起始行
									2,//结束行
									8,//起始列
									11//结束行
									)
							
								);
					HSSFRow	 row40 = sheet.createRow(39);//创建sheet的第二行
					HSSFCell cell401 = row40.createCell(0); //第三行第一列
					HSSFCell cell402 = row40.createCell(1); //第三行第一列
					cell401.setCellValue("编制部门:");
					cell402.setCellValue("数据中心");
					
					for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
						
						HSSFCell cell32 = row4.createCell(b+1);
						HSSFCell cell42 = row5.createCell(b+1);
						cell32.setCellValue(zhibiao.get(b));
						cell42.setCellStyle(cellStyle);
						cell32.setCellStyle(cellStyle7);
						
						if(zhibiao.get(b).equals("受灾人口") || zhibiao.get(b).equals("紧急转移安置人口") || zhibiao.get(b).equals("需紧急生活救助人口")){
							cell42.setCellValue("(人次)");
							
						}else if(zhibiao.get(b).equals("受灾面积") || zhibiao.get(b).equals("绝收面积")){
							cell42.setCellValue("(公顷)");
							
						}else if(zhibiao.get(b).equals("死亡人口") || zhibiao.get(b).equals("失踪人口") ){
							cell42.setCellValue("(人)");
							
						}else if(zhibiao.get(b).equals("房屋倒塌") || zhibiao.get(b).equals("严重损坏房屋")|| zhibiao.get(b).equals("一般损坏房屋") ){
							cell42.setCellValue("(间)");
							
						}
						else{
							cell42.setCellValue("(万元)");
							
						}
					}
					//中间空着的一行
					HSSFRow	 row66 = sheet.createRow(6);//创建sheet的第7行 北京
					for(int q =0 ;q<12;q++){
						//创建列数
						HSSFCell cell661 = row66.createCell(q); //第七行第一列
						cell661.setCellStyle(cellStyle);
						
					}
					
					
					//合计
					HSSFRow	 row671 = sheet.createRow(5);//创建sheet的第6行 合计
					for(int q =0 ;q<12;q++){
						//创建列数
						HSSFCell cell671 = row671.createCell(q); //第七行第一列
						cell671.setCellStyle(cellStyle6);
						if(q==0){
							cell671.setCellValue("合计");
						}
					}
					//设置列
					//数据的边框
					
					for (int ii1 = 7; ii1 < 39; ii1++) {
						HSSFRow row11 = sheet.createRow(ii1);
						for (int jjj = 0; jjj < 12; jjj++) {
							if(jjj==0){
								HSSFCell cell11 = row11.createCell(jjj);
								cell11.setCellStyle(cellStyle6);
							}else{
							HSSFCell cell11 = row11.createCell(jjj);
							cell11.setCellStyle(cellStyle);
							}
						}
					}
					//设置省份
					for(int iii =7;iii <39;iii++){
						sheet.getRow(iii).getCell(0).setCellValue(new HSSFRichTextString(Utils.getPro().get(iii-7)));
						
					}
					
					
					if(i==0){
						//sheet1
						for (int r =1;r<12;r++){
							sheet1.setColumnWidth(r, 1800);
						}
						HSSFRow row_1 = sheet1.createRow(0);//创建sheet1的第一行
						HSSFCell cell_1 = row_1.createCell(0); //第一列
						name= flag==1 ? "今年以来灾情报表(合计)" : "本月以来灾情快报(合计)";
						cell_1.setCellValue(name);//第一行名称
						cell_1.setCellStyle(cellStyle5);
						sheet1.addMergedRegion(//合并
								new CellRangeAddress(
										0, //起始行
										0,//结束行
										0,//起始列
										count//结束行
										)
								
									);
						//第二行
						HSSFRow	 row_2 = sheet1.createRow(1);//创建sheet的第二行
						HSSFCell cell1_1 = row_2.createCell(0); //第二行第一列
						HSSFCell cell2_2 = row_2.createCell(1); //第二行第二列
						HSSFCell cell3_3 = row_2.createCell(sencodCount+1); //第二行第三列
						cell1_1.setCellValue("填报单位");
						cell2_2.setCellValue("民政部国家减灾中心");
						cellStyle1.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 右
						cell3_3.setCellValue("起止日期:"+startTime);
						cell3_3.setCellStyle(cellStyle1);
						sheet1.addMergedRegion(//合并民政部国家减灾中心
								new CellRangeAddress(
										1, //起始行
										1,//结束行
										1,//起始列
										sencodCount//结束行
										)
								
									);
						sheet1.addMergedRegion(//合并起止日期
								new CellRangeAddress(
										1, //起始行
										1,//结束行
										sencodCount+1,//起始列
										count//结束列
										)
								
									);
						//第三行 sheet1
						
						HSSFRow	 row3_3 = sheet1.createRow(2);//创建sheet的第三行
						HSSFRow	 row4_4 = sheet1.createRow(3);//创建sheet的第4行
						HSSFRow	 row5_5 = sheet1.createRow(4);//创建sheet的第4行
						HSSFCell cell41_12 = row4_4.createCell(0); 
						HSSFCell cell51_12 = row5_5.createCell(0); 
						cell41_12.setCellStyle(cellStyle); //边框
						cell51_12.setCellStyle(cellStyle); //边框
						for(int ii=0;ii<12;ii++){
							if(ii==0 || ii==1 ||  ii==6 || ii==8){
								
							}else{
							//三行的列
							HSSFCell cell31_12 = row3_3.createCell(ii); 
							cell31_12.setCellStyle(cellStyle); //边框
							}
							
						}
						
						HSSFCell cell31_1 = row3_3.createCell(0); //第三行第一列
						HSSFCell cell30_0 = row3_3.createCell(1);
						HSSFCell cell36_6 = row3_3.createCell(6);
						HSSFCell cell38_8 = row3_3.createCell(8);
						cell31_1.setCellValue("地区");
						cell31_1.setCellStyle(cellStyle6); //边框
						sheet1.addMergedRegion(//合并
								new CellRangeAddress(
										2, //起始行
										4,//结束行
										0,//起始列
										0//结束行
										)
								
									);
						cell30_0.setCellValue("人口受灾情况");
						cell30_0.setCellStyle(cellStyle6);
						sheet1.addMergedRegion(//合并
								new CellRangeAddress(
										2, //起始行
										2,//结束行
										1,//起始列
										5//结束行
										)
								
									);
						cell36_6.setCellValue("农作物受灾情况");
						cell36_6.setCellStyle(cellStyle6);
						sheet1.addMergedRegion(//合并
								new CellRangeAddress(
										2, //起始行
										2,//结束行
										6,//起始列
										7//结束行
										)
								
									);
						cell38_8.setCellValue("损失情况");
						cell38_8.setCellStyle(cellStyle6);
						sheet1.addMergedRegion(//合并
								new CellRangeAddress(
										2, //起始行
										2,//结束行
										8,//起始列
										11//结束行
										)
								
									);
						HSSFRow	 row_40 = sheet1.createRow(39);//创建sheet的第二行
						HSSFCell cell40_1 = row_40.createCell(0); //第三行第一列
						HSSFCell cell40_2 = row_40.createCell(1); //第三行第一列
						cell40_1.setCellValue("编制部门:");
						cell40_2.setCellValue("数据中心");
						
						for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
							//sheet1
							HSSFCell cell32_2 = row4_4.createCell(b+1);
							HSSFCell cell42_2 = row5_5.createCell(b+1);
							cell32_2.setCellValue(zhibiao.get(b));
							cell32_2.setCellStyle(cellStyle7);
							cell42_2.setCellStyle(cellStyle);
							
							if(zhibiao.get(b).equals("受灾人口") || zhibiao.get(b).equals("紧急转移安置人口") || zhibiao.get(b).equals("需紧急生活救助人口")){
								
								cell42_2.setCellValue("(人次)");
							}else if(zhibiao.get(b).equals("受灾面积") || zhibiao.get(b).equals("绝收面积")){
								
								cell42_2.setCellValue("(公顷)");
							}else if(zhibiao.get(b).equals("死亡人口") || zhibiao.get(b).equals("失踪人口") ){
								
								cell42_2.setCellValue("(人)");
							}else if(zhibiao.get(b).equals("房屋倒塌") || zhibiao.get(b).equals("严重损坏房屋")|| zhibiao.get(b).equals("一般损坏房屋") ){
								
								cell42_2.setCellValue("(间)");
							}
							else{
								cell42_2.setCellValue("(万元)");
							}
							
						}
						//中间空着的一行
						HSSFRow	 row6_6_1 = sheet1.createRow(6);//创建sheet的第7行 空着
						
						for(int q =0 ;q<12;q++){
							//创建列数
							HSSFCell cell66_1 = row6_6_1.createCell(q); //第七行第一列
							cell66_1.setCellStyle(cellStyle);
							
						}
						
						//合计
						HSSFRow	 row6_7_1 = sheet1.createRow(5);//创建sheet的第6行 合计
						for(int q =0 ;q<12;q++){
							//创建列数
							HSSFCell cell67_1 = row6_7_1.createCell(q); //第七行第一列
							cell67_1.setCellStyle(cellStyle6);
							if(q==0){
								cell67_1.setCellValue("合计");
							}
							
						}
						//设置列
						//数据的边框
						
						for (int i_i = 7; i_i < 39; i_i++) {
							HSSFRow row1_1 = sheet1.createRow(i_i);
							for (int j_j = 0; j_j < 12; j_j++) {
								if(j_j==0){
									HSSFCell cell11 = row1_1.createCell(j_j);
									cell11.setCellStyle(cellStyle6);
								}else{
								HSSFCell cell11 = row1_1.createCell(j_j);
								cell11.setCellStyle(cellStyle);
								}
								
								//cell.setCellValue(0);
							}
						}
						
						for(int i_i_i =7;i_i_i <39;i_i_i++){
							sheet1.getRow(i_i_i).getCell(0).setCellValue(new HSSFRichTextString(Utils.getPro().get(i_i_i-7)));
						}
						
						
						
						
						
						
						//总计的sheet页
						List<Map<String, Object>> allList = productMapper.findAllReportData(//灾害总计
								reportIds, indexItemCodes, assertLevel(level), level,
								INDEXITEM_DATA_TYPE);
						
							if(allList != null){
							for (int j = 0; j < allList.size(); j++) {
										if(allList.get(j).get("PROVNAME").toString().contains("兵团"))//是兵团的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(38).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										
										else if(allList.get(j).get("PROVNAME").toString().contains("新疆"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(37).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("宁夏"))//是天津的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(36).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("青海"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(35).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("甘肃"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(34).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("陕西"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(33).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("西藏"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(32).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("云南"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(31).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("贵州"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(30).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("四川"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(29).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("重庆"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(28).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("海南"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(27).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("广西"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(26).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("广东"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(25).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("湖南"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(24).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("湖北"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(23).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("河南"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(22).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("山东"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(21).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("江西"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(20).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("福建"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(19).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("安徽"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(18).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("浙江"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(17).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("江苏"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(16).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("上海"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(15).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("黑龙江"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(14).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("吉林"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(13).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("辽宁"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(12).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("内蒙古"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(11).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("山西"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(10).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("河北"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(9).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("天津"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(8).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
										else if(allList.get(j).get("PROVNAME").toString().contains("北京"))//是北京的 
										{
										for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
											if(allList.get(j).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
												HSSFCell cell72_2 = sheet1.getRow(7).getCell(b+1);//列
											//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
												if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else if(b+1==6|| b+1==7 ){
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString())/1);//设置值
												}else{
													cell72_2.setCellValue(Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//设置值
												}
												cell72_2.setCellStyle(cellStyle);
												u1.addValue1(b+1, Double.valueOf(allList.get(j).get("REPORTITEMVALUE").toString()));//计算总值
												break;
											}
										}
									}
								}
							
							//总计
							HSSFCell cell62_2 = sheet1.getRow(5).getCell(1);
						//	HSSFCell cell62_2 = row6_6.createCell(1);//
							cell62_2.setCellValue( u1.getD1_1()==0.0  ?0 :u1.getD1_1()/1);
							cell62_2.setCellStyle(cellStyle);
							
							HSSFCell cell63_3 = sheet1.getRow(5).getCell(2);
						//	HSSFCell cell63_3 = row6_6.createCell(2);//列
							cell63_3.setCellValue(u1.getD2_2()==0.0  ?0:u1.getD2_2());
							cell63_3.setCellStyle(cellStyle);
							
							HSSFCell cell64_4 = sheet1.getRow(5).getCell(3);
						//	HSSFCell cell64_4 = row6_6.createCell(3);//列
							cell64_4.setCellValue(u1.getD3_3()==0.0   ?0:u1.getD3_3());
							cell64_4.setCellStyle(cellStyle);
							
							HSSFCell cell65_5 = sheet1.getRow(5).getCell(4);
						//	HSSFCell cell65_5 = row6_6.createCell(4);//列
							cell65_5.setCellValue(u1.getD4_4()==0.0   ?0:u1.getD4_4()/1);
							cell65_5.setCellStyle(cellStyle);
							
							HSSFCell cell66_6 = sheet1.getRow(5).getCell(5);
						//	HSSFCell cell66_6 = row6_6.createCell(5);//列
							cell66_6.setCellValue(u1.getD5_5()==0.0   ?0:u1.getD5_5()/1);
							cell66_6.setCellStyle(cellStyle);
							
							HSSFCell cell67_7 = sheet1.getRow(5).getCell(6);
						//	HSSFCell cell67_7 = row6_6.createCell(6);//列
							cell67_7.setCellValue(u1.getD6_6()==0.0   ?0:u1.getD6_6()/1);
							cell67_7.setCellStyle(cellStyle);
							
							HSSFCell cell68_8 = sheet1.getRow(5).getCell(7);
						//	HSSFCell cell68_8 = row6_6.createCell(7);//列
							cell68_8.setCellValue(u1.getD7_7()==0.0  ?0:u1.getD7_7()/1);
							cell68_8.setCellStyle(cellStyle);
							
							HSSFCell cell69_9 = sheet1.getRow(5).getCell(8);
						//	HSSFCell cell69_9 = row6_6.createCell(8);//列
							cell69_9.setCellValue(u1.getD8_8()==0.0  ?0:u1.getD8_8()/1);
							cell69_9.setCellStyle(cellStyle);
							
							HSSFCell cell610_0 = sheet1.getRow(5).getCell(9);
						//	HSSFCell cell610_0 = row6_6.createCell(9);//列
							cell610_0.setCellValue(u1.getD9_9()==0.0  ?0:u1.getD9_9()/1);
							cell610_0.setCellStyle(cellStyle);
							
							HSSFCell cell611_1 = sheet1.getRow(5).getCell(10);
							//HSSFCell cell611_1 = row6_6.createCell(10);//列
							cell611_1.setCellValue(u1.getD10_10()==0.0  ?0:u1.getD10_10()/1);
							cell611_1.setCellStyle(cellStyle);
							
							HSSFCell cell612_2 = sheet1.getRow(5).getCell(11);
							//HSSFCell cell612_2 = row6_6.createCell(11);//列
							cell612_2.setCellValue(u1.getD11_11()==0.0  ?0:u1.getD11_11());
							cell612_2.setCellStyle(cellStyle);
							
							}
						
					}
					
					
						
					
					//插入数据
					if(list.size()>0){
						for(int a =0;a<list.size();a++){
							
							
							//北京
							if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("北京")//是北京的 
									){
									
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列	
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(7).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.keepTwoBit(list.get(a).get("REPORTITEMVALUE")+""));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//天津
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("天津")//是天津的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(8).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//河北
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("河北")//是河北的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(9).getCell(b+1);//列
											//cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											
											break;
										}
									}
								}
							
							//山西
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("山西")//是山西的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(10).getCell(b+1);//列
											//cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//内蒙古
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("内蒙古")//是内蒙古的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(11).getCell(b+1);//列
											//cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//辽宁
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("辽宁")//是辽宁的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(12).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//吉林
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("吉林")//是吉林的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(13).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//黑龙江
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("黑龙江")//是黑龙江的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(14).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//上海
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("上海")//是上海的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(15).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//江苏
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("江苏")//是江苏的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(16).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//浙江
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("浙江")//是浙江的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(17).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//安徽
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("安徽")//是安徽的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(18).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//福建
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("福建")//是福建的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(19).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//江西
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("江西")//是江西的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(20).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//山东
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("山东")//是山东的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(21).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//河南
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("河南")//是河南的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(22).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//湖北
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("湖北")//是湖北的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(23).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//湖南
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("湖南")//是湖南的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(24).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//广东
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("广东")//是广东的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(25).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//广西
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("广西")//是广西的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(26).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//海南
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("海南")//是海南的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(27).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//重庆
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("重庆")//是重庆的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(28).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//四川
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("四川")//是四川的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(29).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//贵州
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("贵州")//是贵州的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(30).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//云南
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("云南")//是云南的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(31).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//西藏
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("西藏")//是西藏的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(32).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//陕西
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("陕西")//是陕西的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(33).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//甘肃
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("甘肃")//是甘肃的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(34).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//青海
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("青海")//是青海的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(35).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//宁夏
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("宁夏")//是宁夏的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(36).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//新疆
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("新疆")//是新疆的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(37).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double)list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							
							//兵团
							else if(list.get(a).get("DISASTERNAME").toString().equals(listType.get(i).get("DISASTERNAME").toString()) &&//是同一个灾害类型
									list.get(a).get("PROVNAME").toString().contains("兵团")//是兵团的 
									){
									for(int b =0; b<zhibiao.size();b++){//多少个指标名称就多少列
										if(list.get(a).get("INDEXITEMNAME").toString().contains(zhibiao.get(b))){//跟列相同
											HSSFCell cell72 = sheet.getRow(38).getCell(b+1);//列
										//	cell72.setCellValue(DoubleKeepTwo.getTwoDecimal((double) list.get(a).get("REPORTITEMVALUE")));//设置值
											if(b+1==1|| b+1==4 || b+1==5|| b+1==8 || b+1==9|| b+1==11 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else if(b+1==6|| b+1==7 ){
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString())/1);//设置值
											}else{
												cell72.setCellValue(Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//设置值
											}
											
											u.addValue(b+1, Double.valueOf(list.get(a).get("REPORTITEMVALUE").toString()));//计算总值
											break;
										}
									}
								}
							}
						
						//总计
						
						HSSFCell cell62 = sheet.getRow(5).getCell(1);
						System.out.println(u.getD1());
						System.out.println(u.getD2());
						System.out.println(u.getD3());
						System.out.println(u.getD4());
						System.out.println(u.getD5());
						System.out.println(u.getD6());
						System.out.println(u.getD7());
						System.out.println(u.getD8());
						System.out.println(u.getD9());
						System.out.println(u.getD10());
						System.out.println(u.getD11());
						cell62.setCellValue( u.getD1()==0.0  ?0 :u.getD1()/1);
						cell62.setCellStyle(cellStyle);
						HSSFCell cell63 = sheet.getRow(5).getCell(2);//列
						cell63.setCellValue(u.getD2()==0.0  ?0:u.getD2());
						cell63.setCellStyle(cellStyle);
						HSSFCell cell64 = sheet.getRow(5).getCell(3);
						cell64.setCellValue(u.getD3()==0.0   ?0:u.getD3());
						cell64.setCellStyle(cellStyle);
						HSSFCell cell65 = sheet.getRow(5).getCell(4);//列
						cell65.setCellValue(u.getD4()==0.0   ?0:u.getD4()/1);
						cell65.setCellStyle(cellStyle);
						HSSFCell cell66 = sheet.getRow(5).getCell(5);
						cell66.setCellValue(u.getD5()==0.0   ?0:u.getD5()/1);
						cell66.setCellStyle(cellStyle);
						HSSFCell cell67 = sheet.getRow(5).getCell(6);
						cell67.setCellValue(u.getD6()==0.0   ?0:u.getD6()/1);
						cell67.setCellStyle(cellStyle);
						HSSFCell cell68 = sheet.getRow(5).getCell(7);
						cell68.setCellValue(u.getD7()==0.0  ?0:u.getD7()/1);
						cell68.setCellStyle(cellStyle);
						HSSFCell cell69 = sheet.getRow(5).getCell(8);
						cell69.setCellValue(u.getD8()==0.0  ?0:u.getD8()/1);
						cell69.setCellStyle(cellStyle);
						HSSFCell cell610 = sheet.getRow(5).getCell(9);
						cell610.setCellValue(u.getD9()==0.0  ?0:u.getD9()/1);
						cell610.setCellStyle(cellStyle);
						HSSFCell cell611 = sheet.getRow(5).getCell(10);
						cell611.setCellValue(u.getD10()==0.0  ?0:u.getD10()/1);
						cell611.setCellStyle(cellStyle);
						HSSFCell cell612 = sheet.getRow(5).getCell(11);
						cell612.setCellValue(u.getD11()==0.0  ?0:u.getD11());
						cell612.setCellStyle(cellStyle);
						//置空u
						u.setD1(0.0);
						u.setD2(0.0);
						u.setD3(0.0);
						u.setD4(0.0);
						u.setD5(0.0);
						u.setD6(0.0);
						u.setD7(0.0);
						u.setD8(0.0);
						u.setD9(0.0);
						u.setD10(0.0);
						u.setD11(0.0);
						
						}
					}
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
				ret.add(map);
				}
			else{
				map.put("result", "success");
				map.put("flag", 2); //无数据
				map.put("message", "没有数据");
				ret.add(map);
			}
			

			

			
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("flag", 0);//下载失败
			map.put("message", "下载失败");
			ret.add(map);
			e.printStackTrace();
		}finally{
			
		}

		return ret;
	}

	@Override
	public List<Map<String, Object>> asyncExportProduct(List<String> reportIds,
			List<String> indexItemCodes, Integer level, Integer flag,
			String starttime, String endtime,HttpServletRequest req, HttpServletResponse resp) {
		return findReportData(reportIds, indexItemCodes, level, flag, starttime,
				endtime,req, resp);
	}

	@Override
	public void syncExportProduct(List<String> reportIds,
			List<String> indexItemCodes, Integer level, Integer flag,
			String starttime, String endtime,HttpServletRequest req, HttpServletResponse resp) {
		findReportData(reportIds, indexItemCodes, level, flag, starttime,
				endtime, req, resp);
	}

	private List<Integer> assertLevel(Integer level) {
		return Arrays.asList((level == 4) ? TOWN_LEVELS
				: (level == 3) ? COUNTY_LEVELS : (level == 2) ? CITY_LEVELS
						: PRIV_LEVELS);
	}

}
