package com.enjoyor.modules.dma.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.dma.entity.DmaAreaDTO;
import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaAreaTreeDTO;
import com.enjoyor.modules.dma.entity.DmaFlowNightWarnEntity;

public interface DmaAreaService {

	int save(DmaAreaEntity de);
	
	int update(DmaAreaEntity de);
	
	int delete(int id);
	
	List<DmaAreaEntity> queryAreaList(DmaAreaEntity de);
	
	int queryAreaCount(DmaAreaEntity de);
	
    List<DmaAreaEntity> queryAreaParent();
	
	List<DmaAreaEntity> queryAreaChild(String dareaParentId);
	
	List<DmaAreaEntity> queryAllWaterArea();
	
	int updateByDareaId(DmaAreaEntity de);
	
	int queryCountAreaId(String dareaId);
	
    List<DmaAreaEntity> queryAreaTree();
    
    List<DmaAreaDTO> querySonList(String dareaParentId);
    
    List<DmaFlowNightWarnEntity> queryDmaFlowNightWarnList(String dareaId);
    
    List<DmaAreaDTO> queryDmaWaterHis(String dareaParentId, String beginTime, String endTime);
    
    String dmalslCalcMethod(String dareaId);
    
    List<DmaAreaTreeDTO> queryDmaTree();
}
