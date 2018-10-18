/**
 * 
 */
package com.nndims.disaster.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nndims.disaster.product.service.IProductService;
import com.nndims.disaster.product.service.IYearProductService;

/**
 * @author muxl
 * @date 2018年10月8日
 * @time 下午6:12:29
 */
@RestController
@RequestMapping("/api/year")
@CrossOrigin
public class YearProductApi extends ProductApi implements IYearProductApi {

	@Autowired
	private IYearProductService yearProductService;

	@Override
	IProductService getProductService() {
		return this.yearProductService;
	}

	// @Override
	// public Result asyncExportProduct(@Valid @RequestBody ExportProductParams params, BindingResult errors) {
	// try {
	// errorHandler(errors);
	// // authCode(params.getKey(), params.getCode());
	// // return Result.success(reporterService.findReportersByCode(params.getCode()));
	// return null;
	// } catch (Exception ex) {
	// return Result.failure(ex.getMessage());
	// }
	// }
	//
	// @Override
	// public Result syncExportProduct(@Valid @RequestBody ExportProductParams params, BindingResult errors) {
	// try {
	// errorHandler(errors);
	// // authCode(params.getKey(), params.getCode());
	// // return Result.success(reporterService.findReportersByCode(params.getCode()));
	// return null;
	// } catch (Exception ex) {
	// return Result.failure(ex.getMessage());
	// }
	// }

	
	public static void main(String[] args) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.set(2018, 11, 1);
		System.out.println(c.getTime().getTime());
		
	}
	
}
