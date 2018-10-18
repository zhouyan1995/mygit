/**
 * 
 */
package com.nndims.disaster.product.service.base;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author muxl
 * @date 2018年10月8日
 * @time 下午6:21:08
 */
abstract public class ABBaseService implements IBaseService {

	public List<Map<String, Object>> findReportData(List<String> reportIds,
			List<String> indexItemCodes, Integer level,Integer flag,
			String starttime, String endtime,HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		return null;
	}
}
