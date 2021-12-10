package com.enjoyor.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.sys.entity.SysAreaSimpleEntity;
import com.enjoyor.modules.sys.entity.SysDictionaryEntity;


public interface SysDictionaryDao extends BaseDao<SysDictionaryEntity> {
	
	List<SysDictionaryEntity> queryDicList(@Param("parentId")String parentId);
}
