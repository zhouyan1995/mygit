/**
 * 
 */
package com.nndims.disaster.product.domain;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nndims.disaster.product.domain.base.ABBaseDto;

/**
 * @author muxl
 * @date 2018年4月10日
 * @time 下午5:48:49
 */
@XmlRootElement
@JsonInclude(Include.NON_EMPTY)
public class ReporterDto extends ABBaseDto {

	private static final long serialVersionUID = -8831176203043368478L;
	private String user_id;
	private String user_name;
	private String userage;
	private String id_num;
	private String company;
	private String title;
	private String post;
	private String responsibilities;
	private String mobile_phone;
	private String office_phone;
	private String create_time;
	private String fullcivilregionalismname;
	private String sex;
	private String education;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUserage() {
		return userage;
	}

	public void setUserage(String userage) {
		this.userage = userage;
	}

	public String getId_num() {
		return id_num;
	}

	public void setId_num(String id_num) {
		this.id_num = id_num;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public String getOffice_phone() {
		return office_phone;
	}

	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getFullcivilregionalismname() {
		return fullcivilregionalismname;
	}

	public void setFullcivilregionalismname(String fullcivilregionalismname) {
		this.fullcivilregionalismname = fullcivilregionalismname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

}
