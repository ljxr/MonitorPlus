package com.enjoyor.modules.monitor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.FlowNightEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.PipeData;
import com.enjoyor.modules.monitor.entity.PipeDataDTO;
import com.enjoyor.modules.monitor.entity.SGSiteDTO;

public interface FlowNightDao {
	
	List<FlowNightEntity> query(@Param("siteId")String siteId, @Param("beginTime")String beginTime, @Param("endTime")String endTime);
	
	List<SGSiteDTO> querySGSiteList();
	
	List<SGSiteDTO> querySGSiteListByDma();
	
	List<FlowNightEntity> queryFlowByArea(@Param("areaId")String areaId, @Param("time")String time);
}
