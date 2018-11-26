package com.fineway.gthbtable.serivce.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fineway.common.util.GetUUID;
import com.fineway.gthbtable.mapper.GtHbTableMapper;
import com.fineway.gthbtable.model.GtHbTable;
import com.fineway.gthbtable.serivce.GtHbTableService;
@Service
@Transactional
public class GtHbTableServiceImpl implements GtHbTableService {

	@Resource
	private GtHbTableMapper mapper;

	

	@Override
	public List<GtHbTable> getGtList(Map map) {
		List<GtHbTable> list =mapper.getGtList(map);
		return list;
	}

	@Override
	public List<GtHbTable> getGTAll(Map map) {
		// 不分条件
		
		List<GtHbTable> list =mapper.getGTAll(map);
		
		return list;
	}

	@Override
	public int getGTCount(Map map) {
		
		return mapper.getGTCount(map);
	}

	@Override
	public int insertGT(GtHbTable gtHbTable) {
		int count1=0;
		int count =0;
		
		Map<String,Object> map =new HashMap();
		if(gtHbTable.getGthcid() == null || gtHbTable.getGthcid() == ""){
		
			// 直接插入
			 gtHbTable.setGthcid(GetUUID.getUUID());
 			 count1 =mapper.insertGT(gtHbTable);

			
		
		} else{
			//修改原来的数据的状态为0
			map.put("GTHCID", gtHbTable.getGthcid());
			map.put("HCISNEW", 0);
			//栗国梁 2018-06-13
			map.put("QYMC",gtHbTable.getQymc());
			map.put("REPORTTIME", gtHbTable.getReporttime());
			
		    count =mapper.UpdateHCISNEW(map);
		  //插入数据
		  		if(count >0){
		  			 gtHbTable.setGthcid(GetUUID.getUUID());
		  			 count1 =mapper.insertGT(gtHbTable);
		  		}
					}
		
		
		return count1;
	}

	@Override
	public int searchName(String QYMC) {
		// TODO Auto-generated method stub
		return mapper.searchName(QYMC);
	}

	@Override
	public int UpdateHCISNEW(Map map) {
		// TODO Auto-generated method stub
		return mapper.UpdateHCISNEW(map);
	}

	@Override
	public List<GtHbTable> getGTForName(Map map) {
		// TODO Auto-generated method stub
		return mapper.getGTForName(map);
	}

	@Override
	public List<GtHbTable> getGTForNT(Map map) {
		// TODO Auto-generated method stub
		return mapper.getGTForNT(map);
	}
	

	/**
	 * serviceName：
	 * serviceType：
	 * serviceAddr：
	 * http:
	 * @param 
	 * @return 
	 * @author zy
	 */
}
