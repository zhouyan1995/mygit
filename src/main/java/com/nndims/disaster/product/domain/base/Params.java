package com.nndims.disaster.product.domain.base;

import java.util.List;
import java.util.Map;

public class Params {

	/**
	 * serviceName：
	 * serviceType：
	 * serviceAddr：
	 * http:
	 * @param 
	 * @return 
	 * @author zy
	 */
	private List<String> reportIds;
	private List<String> indexItemCodes;
	private List<Map<String,Object>> index;
	private List<String> ids;
	private Integer level;
	private Integer flag;
	private String startTime;
	private String endtime;
	private String stime;
	private Integer currentPage;
	private String etime;
	private Integer pageSize;
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<String> getReportIds() {
		return reportIds;
	}
	public void setReportIds(List<String> reportIds) {
		this.reportIds = reportIds;
	}
	public List<String> getIndexItemCodes() {
		return indexItemCodes;
	}
	public void setIndexItemCodes(List<String> indexItemCodes) {
		this.indexItemCodes = indexItemCodes;
	}
	public List<Map<String, Object>> getIndex() {
		return index;
	}
	public void setIndex(List<Map<String, Object>> index) {
		this.index = index;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
}
