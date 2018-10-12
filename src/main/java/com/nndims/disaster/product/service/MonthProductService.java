/**
 * 
 */
package com.nndims.disaster.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author muxl
 * @date 2018年10月8日
 * @time 下午6:13:11
 */
@Service
@Transactional
public class MonthProductService extends ProductService implements IMonthProductService {

}
