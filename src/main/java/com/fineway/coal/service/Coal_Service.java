package com.fineway.coal.service;

import java.util.List;
import java.util.Map;

public interface Coal_Service {

	List<Map<String, Object>> getselect(Map<String, Object> para_map);

	boolean getinsert(Map<String, Object> para_map);

	int select_name(String company_name);

	boolean update(Map<String, Object> para_map);

	int get_page(Map<String, Object> para_map);

	boolean getinsert_id(Map<String, Object> para_map);

}
