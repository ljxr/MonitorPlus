package com.enjoyor.modules.sys.service;


import java.util.List;
import java.util.Map;

import com.enjoyor.modules.sys.entity.SysAreaSimpleEntity;
import com.enjoyor.modules.sys.entity.SysDictionaryEntity;


/**
 * 点位
 * 
 */
public interface SysDictionaryService {

	/**
	 * 获取字典
	 */
	List<SysDictionaryEntity> queryDicList(String parentId);
}
