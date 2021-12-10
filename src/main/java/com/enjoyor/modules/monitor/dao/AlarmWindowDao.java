package com.enjoyor.modules.monitor.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.AlarmIgnoreEntity;
import com.enjoyor.modules.monitor.entity.AlarmTotalEntity;
import com.enjoyor.modules.monitor.entity.AlarmTypeFromDic;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.SeatEntity;
import com.enjoyor.modules.monitor.entity.ThresholdAndName;

/**
 * 
 * 
 */
public interface AlarmWindowDao {
	
	//查询所有忽略
	List<AlarmIgnoreEntity> selectAllIgnore();
	
	//报警弹窗
	List<AlarmTotalEntity> alarmWindow(@Param("date")String date);
	
	//插入忽略
	Boolean insertIgnore(AlarmIgnoreEntity entity);
	
	//删除过期忽略
	Boolean deleteOverdue(String date);
	
}
