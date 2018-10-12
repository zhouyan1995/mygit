/**
 * 
 */
package com.nndims.disaster.product.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nndims.disaster.product.domain.DisasterDto;
import com.nndims.disaster.product.mapper.IProductMapper;
import com.nndims.disaster.product.service.base.ABBaseService;

/**
 * @author muxl
 * @date 2018年10月8日
 * @time 下午6:13:16
 */
@Service
@Transactional
abstract public class ProductService extends ABBaseService implements IProductService {

	Logger log = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private IProductMapper productMapper;
	protected static final Integer DISASTER_VALIDITY = 0;
	protected static final String DISASTER_STATE_ID = "0";
	protected static final String INDEXITEM_DATA_TYPE = "NUMBER";
	protected static final Integer[] PRIV_LEVELS = { 1 };
	protected static final Integer[] CITY_LEVELS = { 2 };
	protected static final Integer[] COUNTY_LEVELS = { 3, 5, 6 };
	protected static final Integer[] TOWN_LEVELS = { 4 };
	protected static final String[] FLOWACTION_STATUS_CODES = { "3", "5" };

	@Override
	public List<DisasterDto> findDisasterList(Long stime, Long etime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("开始日期: " + format.format(new Date(stime)));
		log.debug("结束日期: " + format.format(new Date(etime)));
		return productMapper.findDisasterList(new Date(stime), new Date(etime), DISASTER_VALIDITY, DISASTER_STATE_ID,
				PRIV_LEVELS[0], Arrays.asList(FLOWACTION_STATUS_CODES));
	}

	@Override
	public void findReportData(List<String> reportIds, List<String> indexItemCodes, Integer level) {
		log.debug(reportIds.toString());
		log.debug(indexItemCodes.toString());
		log.debug(level.toString());
		productMapper.findReportData(reportIds, indexItemCodes, assertLevel(level), level, INDEXITEM_DATA_TYPE);
	}

	@Override
	public void asyncExportProduct(List<String> reportIds, List<String> indexItemCodes, Integer level) {
		findReportData(reportIds, indexItemCodes, level);
	}

	@Override
	public void syncExportProduct(List<String> reportIds, List<String> indexItemCodes, Integer level) {
		findReportData(reportIds, indexItemCodes, level);
	}

	private List<Integer> assertLevel(Integer level) {
		return Arrays.asList((level == 4) ? TOWN_LEVELS : (level == 3) ? COUNTY_LEVELS : (level == 2) ? CITY_LEVELS
				: PRIV_LEVELS);
	}

}
