package com.enjoyor.common.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.enjoyor.modules.monitor.service.MonitorService;

@Component
public class Schedule {

	@Autowired
	MonitorService monitorService;	
	
	@Scheduled(cron="0 */5 * * * ?")
	private void updateSiteStatus(){
		monitorService.updateSiteStatus();
		System.out.println("点位状态更新");
	}
	
}
