package com.enjoyor.modules.monitor.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
public interface MonitorDao {
	
	//List<MonitorTotalEntity> queryHisData(@Param("type")String type,@Param("siteId")String siteId,@Param("stime")String stime,@Param("etime")String etime);//泵站水厂历史数据

	List<MonitorTotalEntity> queryHisData(@Param("sql")String sql);
	
	int queryHisCount(@Param("siteId")String siteId,@Param("stime")String stime,@Param("etime")String etime);
	
	List<AlarmTotalEntity> queryAlarmData(@Param("sql")String sql);
	
	int queryAlarmCount(@Param("sql")String sql);
	
	List<AlarmTotalEntity> queryInsAlarm(@Param("stime")String stime,@Param("etime")String etime);
	
	List<AlarmTotalEntity> listAlarm(@Param("sql")String sql);
	
	//从字典表和阈值表 查询 监测参数的报警阈值和名称
	List<ThresholdAndName> queryThreAndDic();
	
	//从字典表查询报警等级和报警类型
	List<AlarmTypeFromDic> queryAlarmTypeFromDic();
	
	//查询水厂某个时刻的流量数据
	MonitorTotalEntity queryTodayMonitor(@Param("time")String time,@Param("siteId")String siteId);
	
	//查询水厂一天中最小时刻
	List<MonitorTotalEntity> queryMinTimeMonitor(@Param("time")String time);
	
	MonitorTotalEntity queryMinTimeMonitor2(@Param("time")String time,@Param("siteId")String siteId);
	
	//报警弹窗
	List<AlarmTotalEntity> alarmWindow(@Param("date")String date);
}
