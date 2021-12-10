package com.enjoyor.modules.sys.service;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.sys.entity.SysDictionaryEntity;
import com.enjoyor.modules.sys.entity.SysRegionEntity;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.sys.entity.SysVideoEntity;


/**
 * 点位
 * 
 */
public interface SysSiteService {

	/**
	 * 根据点位名，查询点位信息
	 */
	SysSiteEntity queryByUserName(String name);
	
	SysSiteEntity queryObject(String id);
	
	/**
	 * 查询点位列表
	 */
	List<SysSiteEntity> queryList(Map<String, Object> map);
	
	List<SysSiteEntity> queryList(String siteId);
	
	List<SysSiteEntity> parentName(Map<String, Object> map);
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存点位信息
	 */
	void save(SysSiteEntity dis);
	
	/**
	 * 修改点位信息
	 */
	void update(SysSiteEntity dis);
	
	/**
	 * 删除点位信息
	 */
	void deleteBatch(String[] id);
	
	List<String> queryAreaIdList(String parentId);
	
	List<SysSiteEntity> querySite(int id);
	
	List<SysDictionaryEntity> querySiteType();
	
	List<SysDictionaryEntity> querySiteStatus();
	
	List<SysDictionaryEntity> queryFactory();
	
	List<SysSiteEntity> checkSiteId(String siteId);
	
	/**
	 * 查询泵站水厂点位
	 */
	List<SysSiteEntity> queryFacSite(List<String> list);
	
	/**
	 * 查询排水管网点位
	 */
	List<SysSiteEntity> queryFacSite4Ps(List<String> list);
	
	/**
	 * 查询污水管网点位
	 */
	List<SysSiteEntity> queryFacSite4Ws(List<String> list);
	
	List<SysSiteEntity> queryParentSite(String parentId, List<String> list);
	
	SysSiteEntity querySiteName(String siteId);
	
	List<SysVideoEntity> queryVideoInfo(String siteId,List<String> list);
	
	List<SysSiteEntity> queryfpSite(String type,List<String> list);
	
	List<SysSiteEntity> queryfpSite2(String type,String areaId,List<String> list);
	
	String queryNameById(String siteId);
	
	List<SysSiteEntity> querySiteByParent(String parentId, List<String> list, String dicValue);
	
	List<SysRegionEntity> queryAreaInfo(List<String> list);
	
	List<SysSiteEntity> querySiteByAreaType(String areaId, List<String> dicValue, List<String> list);
	
	List<SysSiteEntity> querySiteByArea(String areaId,List<String> list);
	
	int queryInsData(String siteId,List<String> list);

	List<SysSiteEntity> querySewageImpExp(String siteParentId);
	
	List<SysSiteEntity> queryS03Site();
	
	//查询所有点位Id与点位名称
	List<SysSiteEntity> queryAllSite();
	
	List<SysSiteEntity> queryPsbzSite(String type,String areaId);
	
	List<SysSiteEntity> querySysSiteAndDmaUser(SysSiteEntity siteParam);

	int count(SysSiteEntity siteParam);
	
	SysSiteEntity querySingleSiteBySiteId (String siteId);
	
	List<SysSiteEntity> querySiteByS04(String siteParentId);
	
	List<SysSiteEntity> queryOutWaterSite(String siteParentId);
	
	List<SysSiteEntity> queryS02Site();//所有水厂
	
	// 查询所有污水厂，不包括子点位
	List<SysSiteEntity> querySewageFactory();
	
	List<SysSiteEntity> queryAllWsc();

	}
