package com.enjoyor.modules.monitor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.PipeData;
import com.enjoyor.modules.monitor.entity.PipeDataDTO;

public interface PipeDataDao {
	
	List<PipeData> findAll();
	
	// 流量 压力 水质实时数据
	List<PipeDataDTO> findByArea(@Param("areaId")String areaId);
	
	// 泵站实时数据
	List<PipeDataDTO> findBzByArea(@Param("areaId")String areaId);
	
	List<PipeDataDTO> findPsByArea(@Param("areaId")String areaId);
	
	PipeData queryPipeIns(@Param("pointNum")String pointNum);
}
