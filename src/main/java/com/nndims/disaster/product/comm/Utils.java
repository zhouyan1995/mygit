package com.nndims.disaster.product.comm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class Utils {

	/**
	 * serviceName：
	 * serviceType：
	 * serviceAddr：
	 * http:
	 * @param 
	 * @return 
	 * @author zy
	 */
	Double d1=0.0;
	Double d2=0.0;
	Double d3=0.0;
	Double d4=0.0;
	Double d5=0.0;
	Double d6=0.0;
	Double d7=0.0;
	Double d8=0.0;
	Double d9=0.0;
	Double d10=0.0;
	Double d11=0.0;
	
	Double d1_1=0.0;
	Double d2_2=0.0;
	Double d3_3=0.0;
	Double d4_4=0.0;
	Double d5_5=0.0;
	Double d6_6=0.0;
	Double d7_7=0.0;
	Double d8_8=0.0;
	Double d9_9=0.0;
	Double d10_10=0.0;
	Double d11_11=0.0;
	
	public Double getD1_1() {
		return d1_1;
	}



	public void setD1_1(Double d1_1) {
		this.d1_1 = d1_1;
	}



	public Double getD2_2() {
		return d2_2;
	}



	public void setD2_2(Double d2_2) {
		this.d2_2 = d2_2;
	}



	public Double getD3_3() {
		return d3_3;
	}



	public void setD3_3(Double d3_3) {
		this.d3_3 = d3_3;
	}



	public Double getD4_4() {
		return d4_4;
	}



	public void setD4_4(Double d4_4) {
		this.d4_4 = d4_4;
	}



	public Double getD5_5() {
		return d5_5;
	}



	public void setD5_5(Double d5_5) {
		this.d5_5 = d5_5;
	}



	public Double getD6_6() {
		return d6_6;
	}



	public void setD6_6(Double d6_6) {
		this.d6_6 = d6_6;
	}



	public Double getD7_7() {
		return d7_7;
	}



	public void setD7_7(Double d7_7) {
		this.d7_7 = d7_7;
	}



	public Double getD8_8() {
		return d8_8;
	}



	public void setD8_8(Double d8_8) {
		this.d8_8 = d8_8;
	}



	public Double getD9_9() {
		return d9_9;
	}



	public void setD9_9(Double d9_9) {
		this.d9_9 = d9_9;
	}



	public Double getD10_10() {
		return d10_10;
	}



	public void setD10_10(Double d10_10) {
		this.d10_10 = d10_10;
	}



	public Double getD11_11() {
		return d11_11;
	}



	public void setD11_11(Double d11_11) {
		this.d11_11 = d11_11;
	}
	
	
	public Double getD8() {
		return d8;
	}



	public void setD8(Double d8) {
		this.d8 = d8;
	}



	public Double getD9() {
		return d9;
	}



	public void setD9(Double d9) {
		this.d9 = d9;
	}



	public Double getD10() {
		return d10;
	}



	public void setD10(Double d10) {
		this.d10 = d10;
	}



	public Double getD11() {
		return d11;
	}



	public void setD11(Double d11) {
		this.d11 = d11;
	}



	public void setD7(Double d7) {
		this.d7 = d7;
	}



	public Utils() {
		super();
		// TODO Auto-generated constructor stub
	}

	public  void addValue1(int number,Double value){
		if(value !=null){
			if(number==1){
				d1_1+=value;
			}
			else if (number==2){
				d2_2+=value;
			}
			else if (number==3){
				d3_3+=value;
			}
			else if (number==4){
				d4_4+=value;
			}
			else if (number==5){
				d5_5+=value;
			}
			else if (number==6){
				d6_6+=value;
			}
			else if (number==7){
				d7_7+=value;
			}
			else if (number==8){
				d8_8+=value;
			}
			else if (number==9){
				d9_9+=value;
			}
			else if (number==10){
				d10_10+=value;
			}
			else if (number==11){
				d11_11+=value;
			}
		}
		
		
		
		
	}
	
	public  void addValue(int number,Double value){
		if(value !=null){
			if(number==1){
				d1+=value;
				System.out.println(value+"----1");
			}
			else if (number==2){
				d2+=value;
				System.out.println(value+"----2");
			}
			else if (number==3){
				d3+=value;
				System.out.println(value+"----3");
			}
			else if (number==4){
				d4+=value;
				System.out.println(value+"----4");
			}
			else if (number==5){
				d5+=value;
				System.out.println(value+"----5");
			}
			else if (number==6){
				d6+=value;
				System.out.println(value+"----6");
			}
			else if (number==7){
				d7+=value;
				System.out.println(value+"----7");
			}
			else if (number==8){
				d8+=value;
				System.out.println(value+"----8");
			}
			else if (number==9){
				d9+=value;
				System.out.println(value+"----9");
			}
			else if (number==10){
				d10+=value;
				System.out.println(value+"----10");
			}
			else if (number==11){
				d11+=value;
				System.out.println(value+"----11");
			}
		}
		
		
		
		
	}
	
	public Double getD1() {
		return d1;
	}

	public void setD1(Double d1) {
		this.d1 = d1;
	}

	public Double getD2() {
		return d2;
	}

	public void setD2(Double d2) {
		this.d2 = d2;
	}

	public Double getD3() {
		return d3;
	}

	public void setD3(Double d3) {
		this.d3 = d3;
	}

	public Double getD4() {
		return d4;
	}

	public void setD4(Double d4) {
		this.d4 = d4;
	}

	public Double getD5() {
		return d5;
	}

	public void setD5(Double d5) {
		this.d5 = d5;
	}

	public Double getD6() {
		return d6;
	}

	public void setD6(Double d6) {
		this.d6 = d6;
	}

	public Double getD7() {
		return d7;
	}

	public static Double add(Double value1,Double value){
		Double d =value1+value;
		return d;
	}
	
	
	/**
	 * 获取config.properties配置文件内容
	 * @return
	 */
	public static String getConfig(String name){
		String insertlogpath = null;
		try{
		Properties props = new Properties();
		props.load(new Utils().getClass().getResourceAsStream("/config.properties"));
		insertlogpath = props.getProperty(name);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return insertlogpath; 
	}
	
	public static void main(String[] args) {
		SimpleDateFormat s =new SimpleDateFormat();
		
		System.out.println(new Date().getTime());
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;
		// ---------------------------1.初始化带边框的表头------------------------------
		for (int i = 7; i < 39; i++) {
			row = sheet.createRow(i);
			for (int j = 1; j < 12; j++) {
				cell = row.createCell(j);
				//cell.setCellStyle(cellStyleCenter);
			}
		}
		System.out.println("完成");
		
	}
	//获取省
	public static List<String> getPro() {
		List<String> list =new ArrayList();
		list.add("北京");
		list.add("天津");
		list.add("河北");
		list.add("山西");
		list.add("内蒙古");
		list.add("辽宁");
		list.add("吉林");
		list.add("黑龙江");
		list.add("上海");
		list.add("江苏");
		list.add("浙江");
		list.add("安徽");
		list.add("福建");
		list.add("江西");
		list.add("山东");
		list.add("河南");
		list.add("湖北");
		list.add("湖南");
		list.add("广东");
		list.add("广西");
		list.add("海南");
		list.add("重庆");
		list.add("四川");
		list.add("贵州");
		list.add("云南");
		list.add("西藏");
		list.add("陕西");
		list.add("甘肃");
		list.add("青海");
		list.add("宁夏");
		list.add("新疆");
		list.add("兵团");
		
		return list;
	}
}
