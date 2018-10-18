/**
 * 
 */
package com.nndims.disaster.product.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nndims.disaster.product.api.base.ABBaseApi;
import com.nndims.disaster.product.domain.base.Result;
import com.nndims.disaster.product.service.IProductService;

/**
 * @author muxl
 * @date 2018年10月8日
 * @time 下午6:13:16
 */
abstract public class ProductApi extends ABBaseApi implements IProductApi {

	abstract IProductService getProductService();

	@Override
	@ResponseBody
	public Result findDisasterList(@Validated FindDisasterListParams params, BindingResult errors) {
		try {
			errorHandler(errors);
			return Result.success(getProductService().findDisasterList(params.getStime(), params.getEtime()));
		} catch (Exception ex) {
			return Result.failure(ex.getMessage());
		}
	}

	@Override
	@ResponseBody
	public Map<String, Object> asyncExportProduct(@Valid @RequestBody ExportProductParams params, BindingResult errors,HttpServletRequest req,HttpServletResponse resp) {
		Map<String, Object> retmap =new HashMap();
		try {
			
			Integer level = (params.getLevel() == 2 || params.getLevel() == 3 || params.getLevel() == 4) ? params
					.getLevel() : 1;
		List<Map<String,Object>> list =getProductService().asyncExportProduct(params.getReportIds(), params.getIndexItemCodes(), level,params.getFlag(),params.getStarttime(),params.getEndtime()
					,req, resp
					);
		 retmap = list.get(0);
		
			
		} catch (Exception ex) {
			
		}
		return retmap;
	}

	@Override
	public Result syncExportProduct(@Valid @RequestBody ExportProductParams params, BindingResult errors) {
		try {
			errorHandler(errors);
			// authCode(params.getKey(), params.getCode());
			// return Result.success(reporterService.findReportersByCode(params.getCode()));
			return null;
		} catch (Exception ex) {
			return Result.failure(ex.getMessage());
		}
	}
}
