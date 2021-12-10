package com.enjoyor.modules.dma.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaSiteData;
import com.enjoyor.modules.dma.entity.DmaSiteEntity;
import com.enjoyor.modules.dma.entity.DmaUserEntity;

public interface DmaSiteDao {

	int save(DmaSiteEntity de);
	
	int update(DmaSiteEntity de);
	
	int delete(@Param("id")int id);
	
	int deleteByAreaId(@Param("dareaId")String dareaId);
	
	List<DmaSiteEntity> querySiteList(DmaSiteEntity de);
	
	List<DmaSiteEntity> querySiteAll(DmaSiteEntity de);
	
	int querySiteCount(DmaSiteEntity de);
	
	List<DmaSiteData> querySiteDayFlow(@Param("siteId")String siteId, @Param("beginTime")String beginTime, @Param("endTime")String endTime);
}
