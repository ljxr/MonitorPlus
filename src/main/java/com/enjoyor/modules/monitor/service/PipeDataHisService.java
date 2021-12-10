package com.enjoyor.modules.monitor.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.FlowReportFormEntity;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.PipeData;
import com.enjoyor.modules.monitor.entity.PipeDataDTO;
import com.enjoyor.modules.monitor.entity.PipeDataHis;
import com.enjoyor.modules.monitor.entity.SeatEntity;

public interface PipeDataHisService {
		
	//按片区id获取点位id，点位名称，压力，瞬时流量，累计流量
	List<PipeDataHis> queryHisData(String siteId, String type, String startTime, String endTime);
	
	//统计分析导出管网excel
	void exportExcel(String areaId, String areaName, String time, HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException;
	
	//小流量报表返回列表
	List<FlowReportFormEntity> queryFlowReportForm(String areaId, String areaName, String time);
}

