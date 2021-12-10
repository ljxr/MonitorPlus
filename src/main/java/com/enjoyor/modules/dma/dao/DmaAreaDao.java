package com.enjoyor.modules.dma.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.dma.entity.DmaAreaDTO;
import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaAreaTreeDTO;
import com.enjoyor.modules.dma.entity.DmaFlowNightWarnEntity;

public interface DmaAreaDao {

	int save(DmaAreaEntity de);
	
	int update(DmaAreaEntity de);
	
	int delete(@Param("id")int id);
	
	List<DmaAreaEntity> queryAreaList(DmaAreaEntity de);
	
	int queryAreaCount(DmaAreaEntity de);
	
	List<DmaAreaEntity> queryAreaParent();
	
	List<DmaAreaEntity> queryAll();
	
	List<DmaAreaEntity> queryAreaChild(@Param("dareaParentId")String dareaParentId);
	
	int updateByDareaId(DmaAreaEntity de);
	
	int queryCountAreaId(@Param("dareaId")String dareaId);
	
	List<DmaAreaDTO> querySonList(@Param("dareaParentId")String dareaParentId, @Param("time")String time);
	
	List<DmaFlowNightWarnEntity> queryDmaFlowNightWarnList(@Param("dareaId")String dareaId, @Param("time")String time);
	
	List<DmaAreaDTO> queryDmaWaterHis(@Param("dareaId")String dareaId, @Param("beginTime")String beginTime, @Param("endTime")String endTime);
	
	List<DmaAreaTreeDTO> queryAllAreaYesterdayWater(@Param("time")String time);
}
