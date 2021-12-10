package com.enjoyor.modules.monitor.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.DispatchLog;
import com.enjoyor.modules.monitor.entity.DispatchLogQueryDTO;
import com.enjoyor.modules.monitor.entity.FlowNightEntity;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.PipeData;
import com.enjoyor.modules.monitor.entity.PipeDataDTO;
import com.enjoyor.modules.monitor.entity.PipeDataHis;
import com.enjoyor.modules.monitor.entity.SGSiteDTO;
import com.enjoyor.modules.monitor.entity.SeatEntity;

public interface FlowNightService {

	   List<FlowNightEntity> query(String siteId, String beginTime, String endTime);
	   
	   List<SGSiteDTO> querySGSiteList();
	   
	   List<SGSiteDTO> querySGSiteListByDma();
}

