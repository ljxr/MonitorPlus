package com.enjoyor.modules.monitor.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.PipeData;
import com.enjoyor.modules.monitor.entity.PipeDataDTO;
import com.enjoyor.modules.monitor.entity.PipeDataHis;
import com.enjoyor.modules.monitor.entity.SeatEntity;

public interface PipeDataService {
	   
	   List<PipeData> findAll();//获取所有管网数据	
	   
	   List<PipeDataDTO> findByArea(String areaId);//按片区id获取点位id，点位名称，压力，瞬时流量，累计流量
	   
	   List<PipeDataDTO> findPsByArea(String areaId);//按片区id获取点位id，点位名称，压力，瞬时流量，累计流量 。。。。。

	   PipeData queryPipeIns(String pointNum);
}

