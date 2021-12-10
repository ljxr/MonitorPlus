package com.enjoyor.modules.monitor.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.AlarmIgnoreEntity;
import com.enjoyor.modules.monitor.entity.AlarmTotalEntity;
import com.enjoyor.modules.monitor.entity.AlarmTypeFromDic;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.SeatEntity;
import com.enjoyor.modules.monitor.entity.ThresholdAndName;

public interface MonitorService {

	//List<MonitorTotalEntity> queryHisData(String type,String siteId,String stime,String etime);
	
	List<MonitorTotalEntity> queryHisData(String siteId,String type,String stime,String etime,int offset,int limit2);
	
	List<MonitorTotalEntity> queryHisData2(String siteId,String type,String stime,String etime,int offset,int limit2);
	
	int queryHisCount(String siteId,String stime,String etime);
	
	List<AlarmTotalEntity> queryAlarmData(String siteId,String level1,String level2,String type,String stime,String etime,int offset,int limit);

	int queryAlarmCount(String siteId,String level1,String level2,String type,String stime,String etime);
	
	List<AlarmTotalEntity> queryInsAlarm(String stime,String etime);
	
	List<AlarmTotalEntity> listAlarm(String systemType, String wscSiteId, String siteTypeList, String areaId, String typeColumn, String beginTime, String endTime);
	
	//从字典表和阈值表 查询 监测参数的报警阈值和名称
	List<ThresholdAndName> queryThreAndDic();
	
	//从字典表查询报警等级和报警类型
	List<AlarmTypeFromDic> queryAlarmTypeFromDic();
	
	//查询水厂某个时刻的流量数据
	MonitorTotalEntity queryTodayMonitor(String time,String siteId);
	
	//查询水厂一天中最小时刻
	List<MonitorTotalEntity> queryMinTimeMonitor(String time);
	
	MonitorTotalEntity queryMinTimeMonitor2(String time,String siteId);
	
	// 查询所有忽略
	List<AlarmIgnoreEntity> selectAllIgnore();
	
	//报警弹窗
	List<AlarmTotalEntity> alarmWindow(int hours);
	
	//添加报警忽略
	Boolean insertIgnore(String siteId, String type, String hours);
	
	//根据报警数据，更新点位状态
	void updateSiteStatus();

}

