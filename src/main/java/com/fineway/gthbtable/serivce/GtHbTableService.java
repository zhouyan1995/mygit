package com.fineway.gthbtable.serivce;

import java.util.List;
import java.util.Map;

import com.fineway.gthbtable.model.GtHbTable;

public interface GtHbTableService {
	int getGTCount(Map map) ;
     //有条件的分页查询
     List<GtHbTable> getGtList(Map map);
     //没有条件的分页查询
     List<GtHbTable> getGTAll(Map map);
     
     int  insertGT(GtHbTable gtHbTable);
     
   //查询企业名称
     int searchName(String QYMC);
     //修改状态
     int UpdateHCISNEW(Map map);
     
   //名称分页查询
     List<GtHbTable>   getGTForName (Map map);
     //名称，时间分页查询
     List<GtHbTable>  getGTForNT (Map map);
}
