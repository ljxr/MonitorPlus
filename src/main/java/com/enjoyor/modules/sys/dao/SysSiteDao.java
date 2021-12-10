package com.enjoyor.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.sys.entity.SysDictionaryEntity;
import com.enjoyor.modules.sys.entity.SysRegionEntity;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.sys.entity.SysVideoEntity;


public interface SysSiteDao extends BaseDao<SysSiteEntity> {
	
	List<SysDictionaryEntity> queryFlowThreshold(@Param("thresholdId")String thresholdId,@Param("thresholdOrdernum")String thresholdOrdernum);

	SysSiteEntity queryByUserName(String name);
	
	List<SysSiteEntity> selectuser(@Param("siteName") String name);
	
	List<SysSiteEntity> checkSiteId(@Param("siteId") String siteId);
	
	List<SysSiteEntity> parentName(Map<String, Object> map);
	
	List<String> queryAreaIdList(String parentId);	
	
	List<SysSiteEntity> querySite(int id);
	
	List<SysDictionaryEntity> querySiteType();
	
	List<SysDictionaryEntity> querySiteStatus();
	
	List<SysDictionaryEntity> queryFactory();
	
	/*
	 * 查询泵站水厂点位
	 */
	List<SysSiteEntity> queryFacSite(@Param("queryRegionId")List<String> list);
	
	/*
	 * 查询排水管网点位
	 */
	List<SysSiteEntity> queryFacSite4Ps(@Param("queryRegionId")List<String> list);
	
	/*
	 * 查询污水厂点位
	 */
	List<SysSiteEntity> queryFacSite4Ws(@Param("queryRegionId")List<String> list);
	
	/*
	 * 查询泵站水厂点位
	 */
	List<SysSiteEntity> queryfpSite(@Param("type")String type,@Param("queryRegionId")List<String> list);
	
	List<SysSiteEntity> queryfpSite2(@Param("type")String type,@Param("areaId")String areaId,@Param("queryRegionId")List<String> list);
	
	/*
	 * 查询泵站水厂点位
	 */
	List<SysSiteEntity> queryParentSite(@Param("parentId")String parentId, @Param("queryRegionId")List<String> list);
	
	SysSiteEntity querySiteName(String siteId);
	
	/*
	 * 查询视频信息
	 */
	List<SysVideoEntity> queryVideoInfo(@Param("siteId")String siteId,@Param("queryRegionId")List<String> list);
	
	String queryNameById(String siteId);
	
	List<SysSiteEntity> querySiteByParent(@Param("parentId")String parentId,@Param("queryRegionId")List<String> list,@Param("dicValue")String dicValue);
	
	List<SysRegionEntity> queryAreaInfo(@Param("queryRegionId")List<String> list);
	
	List<SysSiteEntity> querySiteByArea(@Param("areaId")String areaId,@Param("queryRegionId")List<String> list);	
	
	List<SysSiteEntity> querySiteByAreaType(@Param("areaId")String areaId, @Param("dicValue")List<String> dicValue, @Param("queryRegionId")List<String> list);
	
	int queryInsData(@Param("siteId")String siteId,@Param("queryRegionId")List<String> list);
	
	List<SysSiteEntity> querySewageImpExp(@Param("siteParentId")String siteParentId);
	
	List<SysSiteEntity> queryS03Site();
	
	//查询所有点位Id与点位名称
	List<SysSiteEntity> queryAllSite();
	
	List<SysSiteEntity> queryPsbzSite(@Param("type")String type,@Param("areaId")String areaId);
	
	SysSiteEntity querySingleSiteBySiteId(String siteId);
	
	List<SysSiteEntity> querySysSiteAndDmaUser(SysSiteEntity siteParam);
	
	int count(SysSiteEntity siteParam);
	
	// 查询点位下是否有流量监测点子点位
	List<SysSiteEntity> querySiteByS04(@Param("parentId")String parentId);
	
	List<SysSiteEntity> queryOutWaterSite(@Param("parentId")String parentId);
	
	List<SysSiteEntity> queryByAreaId(@Param("areaId")String areaId);
	
	List<SysSiteEntity> queryS02Site();//所有水厂
	
	void updateSiteStatus(@Param("siteStatus")String siteStatus, @Param("queryRegionId")List<String> siteIdList);
	
	List<SysSiteEntity> queryByStatus(@Param("siteStatus")String siteStatus);
	
	String queryDicValue(@Param("siteId")String siteId);
	
	// 查询所有污水厂，不包括子点位
	List<SysSiteEntity> querySewageFactory();
	
	List<SysSiteEntity> queryAllWsc();
}
