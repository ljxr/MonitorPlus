package com.enjoyor.modules.dma.service;

import java.util.List;
import java.util.Map;

import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaSiteEntity;
import com.enjoyor.modules.dma.entity.DmaUserEntity;

public interface DmaSiteService {

	int save(DmaSiteEntity de);
	
	int update(DmaSiteEntity de);
	
	int delete(int id);
	
	int deleteByAreaId(String dareaId);
	
	List<DmaSiteEntity> querySiteList(DmaSiteEntity de);
	
    List<DmaSiteEntity> querySiteAll(DmaSiteEntity de);
    	
	int querySiteCount(DmaSiteEntity de);
	
	Map<String, Object> querySiteDayFlow(String areaId, String beginTime, String endTime);
}
