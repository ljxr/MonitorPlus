package com.enjoyor.modules.monitor.dao;

import java.util.List;

import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalQueryDTO;

public interface MonitorTotalDao {
	
	List<MonitorTotalEntity> findBySiteIdTime(MonitorTotalQueryDTO dto);
	
}
