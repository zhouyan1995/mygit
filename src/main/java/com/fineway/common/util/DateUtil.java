package com.fineway.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static Date getDateStr(String str){
		Date date=null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
//public static void mian(String[] args) {
//	Date date=DateUtil.getDateStr("2017-09-09 09:09:09");
	//System.out.println(date);
//}

}
