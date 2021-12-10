package com.enjoyor.modules.sys.service.imp;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enjoyor.modules.sys.dao.SysSiteDao;
import com.enjoyor.modules.sys.entity.SysDictionaryEntity;
import com.enjoyor.modules.sys.entity.SysRegionEntity;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.sys.entity.SysVideoEntity;
import com.enjoyor.modules.sys.service.SysSiteService;


/**
 * 点位 
 */
@Service("sysSiteService")
public class SysSiteServiceImpl implements SysSiteService {
	@Autowired
	private SysSiteDao sysSiteDao;

	@Override
	public SysSiteEntity queryByUserName(String name) {
		return sysSiteDao.queryByUserName(name);
	}

	@Override
	public SysSiteEntity queryObject(String id) {
		return sysSiteDao.queryObject(id);
	}

	@Override
	public List<SysSiteEntity> queryList(Map<String, Object> map) {
		return sysSiteDao.queryList(map);
	}

	@Override
	public List<SysSiteEntity> parentName(Map<String, Object> map) {
		return sysSiteDao.parentName(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysSiteDao.queryTotal(map);
	}
	@Transactional
	@Override
	public void save(SysSiteEntity dis) {
		sysSiteDao.save(dis);
	}
	@Transactional
	@Override
	public void update(SysSiteEntity dis) {
		sysSiteDao.update(dis);
	}
	@Transactional
	@Override
	public void deleteBatch(String[] id) {
		sysSiteDao.deleteBatch(id);
	}

	@Override
	public List<String> queryAreaIdList(String parentId) {
		return sysSiteDao.queryAreaIdList(parentId);
	}

	@Override
	public List<SysSiteEntity> querySite(int id) {
		return sysSiteDao.querySite(id);
	}

	@Override
	public List<SysDictionaryEntity> querySiteType() {
		return sysSiteDao.querySiteType();
	}

	@Override
	public List<SysDictionaryEntity> querySiteStatus() {
		return sysSiteDao.querySiteStatus();
	}

	@Override
	public List<SysSiteEntity> checkSiteId(String siteId) {
		return sysSiteDao.checkSiteId(siteId);
	}

	@Override
	public List<SysDictionaryEntity> queryFactory() {
		return sysSiteDao.queryFactory();
	}

	@Override
	public List<SysSiteEntity> queryFacSite(List<String> list) {
		return sysSiteDao.queryFacSite(list);
	}
	
	@Override
	public List<SysSiteEntity> queryFacSite4Ps(List<String> list) {
		return sysSiteDao.queryFacSite4Ps(list);
	}
	
	@Override
	public List<SysSiteEntity> queryFacSite4Ws(List<String> list) {
		return sysSiteDao.queryFacSite4Ws(list);
	}

	@Override
	public List<SysSiteEntity> queryParentSite(String parentId, List<String> list) {
		return sysSiteDao.queryParentSite(parentId, list);
	}

	@Override
	public SysSiteEntity querySiteName(String siteId) {
		return sysSiteDao.querySiteName(siteId);
	}

	@Override
	public List<SysVideoEntity> queryVideoInfo(String siteId,List<String> list) {
		return sysSiteDao.queryVideoInfo(siteId,list);
	}

	@Override
	public List<SysSiteEntity> queryfpSite(String type,List<String> list) {
		return sysSiteDao.queryfpSite(type,list);
	}
	
	@Override
	public List<SysSiteEntity> queryfpSite2(String type,String areaId,List<String> list) {
		return sysSiteDao.queryfpSite2(type,areaId,list);
	}

	@Override
	public String queryNameById(String siteId) {
		return sysSiteDao.queryNameById(siteId);
	}

	@Override
	public List<SysSiteEntity> querySiteByParent(String parentId,List<String> list, String dicValue) {
		return sysSiteDao.querySiteByParent(parentId, list, dicValue);
	}

	@Override
	public List<SysRegionEntity> queryAreaInfo(List<String> list) {
		return sysSiteDao.queryAreaInfo(list);
	}

	@Override
	public List<SysSiteEntity> querySiteByArea(String areaId,List<String> list) {
		return sysSiteDao.querySiteByArea(areaId,list);
	}
	
	@Override
	public List<SysSiteEntity> querySiteByAreaType(String areaId, List<String> dicValue, List<String> list) {
		return sysSiteDao.querySiteByAreaType(areaId, dicValue, list);
	}

	@Override
	public int queryInsData(String siteId, List<String> list) {
		return sysSiteDao.queryInsData(siteId, list);
	}
	
	@Override
	public List<SysSiteEntity> querySewageImpExp(String siteParentId) {
		return sysSiteDao.querySewageImpExp(siteParentId);
	}
	
	@Override
	public List<SysSiteEntity> queryS03Site() {
		return sysSiteDao.queryS03Site();
	}
	
	//查询所有点位Id与点位名称
	@Override
	public List<SysSiteEntity> queryAllSite() {
		return sysSiteDao.queryAllSite();
	}
	
	@Override
	public List<SysSiteEntity> queryPsbzSite(String type,String areaId) {
		return sysSiteDao.queryPsbzSite(type,areaId);
	}

	@Override
	public List<SysSiteEntity> queryList(String siteId) {
		return sysSiteDao.queryList(siteId);
	}
	
	@Override
	public List<SysSiteEntity> querySysSiteAndDmaUser(SysSiteEntity siteParam){
		return sysSiteDao.querySysSiteAndDmaUser(siteParam);
	}
	
	@Override
	public int count(SysSiteEntity siteParam){
		return sysSiteDao.count(siteParam);
	}
	
	@Override
	public SysSiteEntity querySingleSiteBySiteId(String siteId){
		return sysSiteDao.querySingleSiteBySiteId(siteId);
	}

	@Override
	public List<SysSiteEntity> querySiteByS04(String siteParentId) {
		return sysSiteDao.querySiteByS04(siteParentId);
	}

	@Override
	public List<SysSiteEntity> queryS02Site() {
		return sysSiteDao.queryS02Site();
	}

	@Override
	public List<SysSiteEntity> queryOutWaterSite(String siteParentId) {
		return sysSiteDao.queryOutWaterSite(siteParentId);
	}
	
	@Override
	public List<SysSiteEntity> querySewageFactory() {
		return sysSiteDao.querySewageFactory();
	}
	
	@Override
	public List<SysSiteEntity> queryAllWsc(){
		return sysSiteDao.queryAllWsc();
	}
	
}
