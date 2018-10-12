/**
 * 
 */
package com.nndims.disaster.product.mapper.base;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author muxl
 * @date 2018年4月10日
 * @time 下午7:54:14
 */
public class ProductProvider {

	public String findReportersByCode(String code) {
		SQL sql = new SQL();
		sql.SELECT("a.user_id, a.user_name, a.userage, a.id_num, a.company, a.title, a.post, a.responsibilities");
		sql.SELECT("a.mobile_phone, a.office_phone, a.mobile_phone, a.create_time");
		sql.SELECT("getparentStrLowToHigh(a.civilregionalismid) as fullcivilregionalismname");
		sql.SELECT("(case when a.sex ='1' then '男' when a.sex ='2' then '女' end ) as sex");
		sql.SELECT("(case when a.education ='1' then '本科以下' when a.education ='2' then '本科' when a.education ='3' then '研究生及以上' end) as education");
		sql.FROM("disasteruser a");
		sql.JOIN("civilregionalism b on a.civilregionalismid=b.civilregionalismid");
		sql.WHERE("b.civilregionalismcode = #{aid}");
		return sql.toString();
	}

	public String findCertByUid(String uid) {
		SQL sql = new SQL();
		sql.SELECT("bb.trainid, bb.certificateid, bb.train_comment, bb.train_starttime, bb.train_days");
		sql.FROM("train_info bb");
		sql.WHERE("bb.user_id = #{uid}");
		return sql.toString();
	}
}
