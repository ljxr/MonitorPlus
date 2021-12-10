package com.enjoyor.modules.monitor.service.imp;

import com.enjoyor.modules.monitor.dao.FlowNightDao;
import com.enjoyor.modules.monitor.entity.DispatchLog;
import com.enjoyor.modules.monitor.entity.DispatchLogQueryDTO;
import com.enjoyor.modules.monitor.entity.FlowNightEntity;
import com.enjoyor.modules.monitor.entity.SGSiteDTO;
import com.enjoyor.modules.monitor.service.DispatchLogService;
import com.enjoyor.modules.monitor.service.FlowNightService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("flowNightService")
public class FlowNightServiceimp implements FlowNightService {

	@Autowired
	private FlowNightDao flowNightDao;

	@Override
	public List<FlowNightEntity> query(String siteId, String beginTime, String endTime) {		
		List<FlowNightEntity> list = flowNightDao.query(siteId, beginTime, endTime);	
		for(FlowNightEntity entity : list){
			entity.calcRbl();
			entity.calcYbl();
		}
		return list;
	}	
	
	@Override
	public List<SGSiteDTO> querySGSiteList() {
		return flowNightDao.querySGSiteList();	
	}	
	
	@Override
	public List<SGSiteDTO> querySGSiteListByDma() {
		return flowNightDao.querySGSiteListByDma();	
	}	
	
}
