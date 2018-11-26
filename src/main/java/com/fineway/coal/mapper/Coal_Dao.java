package com.fineway.coal.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
@Component(value="coal_Dao")
public interface Coal_Dao {

	List<Map<String, Object>> getselect(Map<String, Object> para_map);

	boolean getinsert(Map<String, Object> para_map);

	int select_name(String company_name);

	boolean update(Map<String, Object> para_map);

	int get_page(Map<String, Object> para_map);

	boolean getinsert_id(Map<String, Object> para_map);

}
