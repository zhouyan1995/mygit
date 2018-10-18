/**
 * 
 */
package com.nndims.disaster.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nndims.disaster.product.service.IMonthProductService;
import com.nndims.disaster.product.service.IProductService;

/**
 * @author muxl
 * @date 2018年10月8日
 * @time 下午6:13:11
 */
@RestController
@RequestMapping("/api/month")
@CrossOrigin
public class MonthProductApi extends ProductApi implements IMonthProductApi {

	@Autowired
	private IMonthProductService monthProductService;

	@Override
	IProductService getProductService() {
		return this.monthProductService;
	}

	// @Override
	// public Result asyncExportProduct(ExportProductParams params, BindingResult result) {
	// return null;
	// }
	//
	// @Override
	// public Result syncExportProduct(ExportProductParams params, BindingResult result) {
	// return null;
	// }

}
