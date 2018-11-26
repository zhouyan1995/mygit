package com.fineway.coal.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fineway.coal.mapper.Coal_Dao;
import com.fineway.coal.service.Coal_Service;


@Service
@Transactional
public class Coal_Service_Impl implements Coal_Service {
	
@Resource
private Coal_Dao coal_dao;
	
	@Override
	public List<Map<String, Object>> getselect(Map<String, Object> para_map) {
		// TODO Auto-generated method stub
		return coal_dao.getselect(para_map);
//		return null;
	}

	@Override
	public boolean getinsert(Map<String, Object> para_map) {
		
		return coal_dao.getinsert(para_map);
	}

	@Override
	public int select_name(String company_name) {
		
		return coal_dao.select_name(company_name);
	}

	@Override
	public boolean update(Map<String, Object> para_map) {
		return coal_dao.update(para_map);
	}

	@Override
	public int get_page(Map<String, Object> para_map) {
		// TODO Auto-generated method stub
		return coal_dao.get_page(para_map);
	}

	@Override
	public boolean getinsert_id(Map<String, Object> para_map) {
		// TODO Auto-generated method stub
		return coal_dao.getinsert_id(para_map);
	}

}