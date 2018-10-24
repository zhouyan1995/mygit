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
 * @date 2018年4月11日
 * @time 下午1:22:00
 */
@XmlRootElement
@JsonInclude(Include.NON_EMPTY)
public class DisasterDto extends ABBaseDto {

	private static final long serialVersionUID = -2163418568349767950L;

	/** 灾情id **/
	private String disasterId;
	/** 灾情名称 **/
	private String disasterName; //
	/** 灾情发生时间 **/
	private String disasterStartTime; //
	/** 灾情结束时间 **/
	private String disasterEndTime;
	/** 报表id **/
	private String reportId;
	/** 报表上报时间 **/
	private String reportTime; // 
	/** 报表数量 **/
	private Integer reportCount;
	/** 报表类型 **/
	private String reportStageName;
	/****/
	private String flowActionStatusName;
	
	private String civilregionalismname;
	public String getCivilregionalismname() {
		return civilregionalismname;
	}

	public void setCivilregionalismname(String civilregionalismname) {
		this.civilregionalismname = civilregionalismname;
	}

	public String getDisasterId() {
		return disasterId;
	}

	public void setDisasterId(String disasterId) {
		this.disasterId = disasterId;
	}

	public String getDisasterName() {
		return disasterName;
	}

	public void setDisasterName(String disasterName) {
		this.disasterName = disasterName;
	}

	public String getDisasterStartTime() {
		return disasterStartTime;
	}

	public void setDisasterStartTime(String disasterStartTime) {
		this.disasterStartTime = disasterStartTime;
	}

	public String getDisasterEndTime() {
		return disasterEndTime;
	}

	public void setDisasterEndTime(String disasterEndTime) {
		this.disasterEndTime = disasterEndTime;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public Integer getReportCount() {
		return reportCount;
	}

	public void setReportCount(Integer reportCount) {
		this.reportCount = reportCount;
	}

	public String getReportStageName() {
		return reportStageName;
	}

	public void setReportStageName(String reportStageName) {
		this.reportStageName = reportStageName;
	}

	public String getFlowActionStatusName() {
		return flowActionStatusName;
	}

	public void setFlowActionStatusName(String flowActionStatusName) {
		this.flowActionStatusName = flowActionStatusName;
	}

}
