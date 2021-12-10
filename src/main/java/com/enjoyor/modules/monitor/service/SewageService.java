package com.enjoyor.modules.monitor.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.DispatchLog;
import com.enjoyor.modules.monitor.entity.DispatchLogQueryDTO;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalQueryDTO;
import com.enjoyor.modules.monitor.entity.PipeData;
import com.enjoyor.modules.monitor.entity.PipeDataDTO;
import com.enjoyor.modules.monitor.entity.PipeDataHis;
import com.enjoyor.modules.monitor.entity.ReportWsEntity;
import com.enjoyor.modules.monitor.entity.SeatEntity;

public interface SewageService {

	List<MonitorTotalEntity> findBySiteIdTime(MonitorTotalQueryDTO dto);

	List<ReportWsEntity> select(String siteId, String beginTime, String endTime) throws Exception;
}

