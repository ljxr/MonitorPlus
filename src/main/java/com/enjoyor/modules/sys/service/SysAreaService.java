package com.enjoyor.modules.sys.service;

import java.util.List;

import com.enjoyor.modules.sys.entity.SysAreaSimpleEntity;

/**
 * 点位
 * 
 */
public interface SysAreaService {

	/**
	 * 返回营业所片区父子结构列表
	 */
	List<SysAreaSimpleEntity> findAllArea(List<String> list);
	
	/**
	 * 查询所有排水片区
	 */
	List<SysAreaSimpleEntity> findAllPsArea(List<String> list);

	/**
	 * 返回所有片区基本信息
	 */
	List<SysAreaSimpleEntity> queryAllArea();

	/**
	 * 根据条件返回片区相关信息
	 * 
	 * @param areaParentId
	 * @return
	 */
	List<SysAreaSimpleEntity> queryAreaInfo(String areaParentId);
}
