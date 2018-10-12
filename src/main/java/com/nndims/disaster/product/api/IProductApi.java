/**
 * 
 */
package com.nndims.disaster.product.api;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nndims.disaster.product.api.base.BaseParams;
import com.nndims.disaster.product.api.base.IBaseApi;
import com.nndims.disaster.product.domain.base.Result;

/**
 * @author muxl
 * @date 2018年10月8日
 * @time 下午6:13:01
 */
abstract public interface IProductApi extends IBaseApi {

	/**
	 * 本年以来灾情列表
	 * @param params
	 * @param result
	 * @return
	 */
	@GetMapping(value = { "/v0.1/disaster/list/get" }, produces = { MediaType.APPLICATION_JSON_VALUE })
	abstract public Result findDisasterList(FindDisasterListParams params, BindingResult result);

	public static class FindDisasterListParams extends BaseParams {

		/** 开始时间 **/
		@NotNull(message = "{stime.null}")
		private Long stime;
		/** 结束时间 **/
		@NotNull(message = "{etime.null}")
		private Long etime;

		public Long getStime() {
			return stime;
		}

		public void setStime(Long stime) {
			this.stime = stime;
		}

		public Long getEtime() {
			return etime;
		}

		public void setEtime(Long etime) {
			this.etime = etime;
		}

	};

	/**
	 * 异步导出本年以来灾情数据
	 * @param params
	 * @param result
	 * @return
	 */
	@PostMapping(value = { "/v0.1/export/async" }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	abstract public Result asyncExportProduct(ExportProductParams params, BindingResult result);

	/**
	 * 同步导出本年以来灾情数据
	 * @param params
	 * @param result
	 * @return
	 */
	@PostMapping(value = { "/v0.1/export/sync" }, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	abstract public Result syncExportProduct(ExportProductParams params, BindingResult result);

	public static class ExportProductParams extends BaseParams {

		/** 要导出的报表ID **/
		@NotEmpty(message = "{reportIds.empty}")
		private List<String> reportIds;
		/** 要导出的指标项表示 **/
		@NotEmpty(message = "{indexItemCodes.empty}")
		private List<String> indexItemCodes;
		/** 导出级别粒度:1省级、2市级、3县级、4乡镇 **/
		@NotNull(message = "{level.null}")
		private Integer level = 1;

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

		public Integer getLevel() {
			return level;
		}

		public void setLevel(Integer level) {
			this.level = level;
		}

	};
}
