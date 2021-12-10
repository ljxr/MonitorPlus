package com.enjoyor.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.sys.entity.SysAreaSimpleEntity;


public interface SysAreaDao extends BaseDao<SysAreaSimpleEntity> {
	
	List<SysAreaSimpleEntity> findAllArea(@Param("queryRegionId")List<String>list);	

	List<SysAreaSimpleEntity> findAllPsArea(@Param("queryRegionId")List<String>list);	
		
	List<SysAreaSimpleEntity> queryAllArea();
	
	List<SysAreaSimpleEntity> queryAreaInfo(@Param("areaParentId")String areaParentId);
}
