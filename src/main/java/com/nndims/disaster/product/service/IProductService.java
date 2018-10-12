/**
 * 
 */
package com.nndims.disaster.product.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.nndims.disaster.product.domain.DisasterDto;
import com.nndims.disaster.product.service.base.IBaseService;

/**
 * @author muxl
 * @date 2018年10月8日
 * @time 下午6:13:01
 */
@Transactional
abstract public interface IProductService extends IBaseService {

	abstract public List<DisasterDto> findDisasterList(Long stime, Long etime);

	abstract public void findReportData(List<String> reportIds, List<String> indexItemCodes, Integer level);

	abstract public void asyncExportProduct(List<String> reportIds, List<String> indexItemCodes, Integer level);

	abstract public void syncExportProduct(List<String> reportIds, List<String> indexItemCodes, Integer level);

}
