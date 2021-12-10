package com.enjoyor.modules.monitor.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.enjoyor.common.utils.RedisUtils;
import com.enjoyor.common.utils.WebSocket;
import com.enjoyor.modules.monitor.dao.AlarmWindowDao;
import com.enjoyor.modules.monitor.dao.MonitorDao;
import com.enjoyor.modules.monitor.dao.MonitorDataDao;
import com.enjoyor.modules.monitor.entity.AlarmIgnoreEntity;
import com.enjoyor.modules.monitor.entity.AlarmTotalEntity;
import com.enjoyor.modules.monitor.entity.AlarmTypeFromDic;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.PipeDataEntity;
import com.enjoyor.modules.monitor.entity.SeatEntity;
import com.enjoyor.modules.monitor.entity.ThresholdAndName;
import com.enjoyor.modules.monitor.service.MonitorDataService;
import com.enjoyor.modules.monitor.service.MonitorService;
import com.enjoyor.modules.sys.dao.SysSiteDao;
import com.enjoyor.modules.sys.entity.SysSiteEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service("monitorService")
public class MonitorServiceimp implements MonitorService {

	@Autowired
	MonitorDao monitorDao;
	@Autowired
	AlarmWindowDao alarmWindowDao;
	@Autowired
	SysSiteDao sysSiteDao;

	@Override
	public List<MonitorTotalEntity> queryHisData(String siteId, String type, String stime, String etime, int offset,
			int limit2) {
		String sql = "";
		switch (type) {
		case "TUR":
			sql = "select * from (select rownum r,site_id,turbidity_value,time from monitortotal where site_id='"
					+ siteId + "' and time between '" + stime + "' and '" + etime
					+ "' order by time asc) where r between " + offset + " and " + limit2;
			break;
		case "PH":
			sql = "select * from (select rownum r,site_id,ph_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "HOCL":
			sql = "select * from (select rownum r,site_id,chlorine_value,time from monitortotal where site_id='"
					+ siteId + "' and time between '" + stime + "' and '" + etime
					+ "' order by time asc) where r between " + offset + " and " + limit2;
			break;
		case "COD":
			sql = "select * from (select rownum r,site_id,cod_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "TN":
			sql = "select * from (select rownum r,site_id,tn_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "TP":
			sql = "select * from (select rownum r,site_id,tp_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "SS":
			sql = "select * from (select rownum r,site_id,ss_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "RAIN":
			sql = "select * from (select rownum r,site_id,rain,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "TEMPERATURE":
			sql = "select * from (select rownum r,site_id,temperature,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "PTIN":
			sql = "select * from (select rownum r,site_id,pressure_value,time from monitortotal where site_id='"
					+ siteId + "' and time between '" + stime + "' and '" + etime
					+ "' order by time asc) where r between " + offset + " and " + limit2;
			break;
		case "FLS":
			sql = "select * from (select rownum r,site_id,flow_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "FLC":
			sql = "select * from (select rownum r,site_id,totalflow_value,time from monitortotal where site_id='"
					+ siteId + "' and time between '" + stime + "' and '" + etime
					+ "' order by time asc) where r between " + offset + " and " + limit2;
			break;
		case "LEVEL":
			sql = "select * from (select rownum r,site_id,lt_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "PTOUT":
			sql = "select * from (select rownum r,site_id,pressure_outvalue,time from monitortotal where site_id='"
					+ siteId + "' and time between '" + stime + "' and '" + etime
					+ "' order by time asc) where r between " + offset + " and " + limit2;
			break;
		case "PTSET":
			sql = "select * from (select rownum r,site_id,pressure_setvalue,time from monitortotal where site_id='"
					+ siteId + "' and time between '" + stime + "' and '" + etime
					+ "' order by time asc) where r between " + offset + " and " + limit2;
			break;
		case "LTSET":
			sql = "select * from (select rownum r,site_id,lt_setvalue,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "START":
			sql = "select * from (select rownum r,site_id,pump_start,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "STOP":
			sql = "select * from (select rownum r,site_id,pump_stop,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "FAULT":
			sql = "select * from (select rownum r,site_id,pump_fault,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "Ar":
			sql = "select * from (select rownum r,site_id,pump_a,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		case "Fr":
			sql = "select * from (select rownum r,site_id,pump_r,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where r between "
					+ offset + " and " + limit2;
			break;
		default:
			sql = "select * from MONITORTOTAL where 1=2";
			break;
		}

		return monitorDao.queryHisData(sql);
	}

	@Override
	public List<AlarmTotalEntity> queryAlarmData(String siteId, String level1, String level2, String type, String stime,
			String etime, int offset, int limit) {
		boolean str = siteId.contains(",");
		String siteId1 = siteId;
		String siteId2 = "";
		String siteId3 = "";
		String siteId4 = "";
		if (str) {
			if (siteId.split(",").length > 3) {
				siteId1 = siteId.split(",")[0];
				siteId2 = siteId.split(",")[1];
				siteId3 = siteId.split(",")[2];
				siteId4 = siteId.split(",")[3];
			} else if (siteId.split(",").length > 2) {
				siteId1 = siteId.split(",")[0];
				siteId2 = siteId.split(",")[1];
				siteId3 = siteId.split(",")[2];
			} else if (siteId.split(",").length > 1) {
				siteId1 = siteId.split(",")[0];
				siteId2 = siteId.split(",")[1];
			} else {
				siteId1 = siteId;
			}
		}

		String sql = "";
		switch (type) {
		case "TUR":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,tur_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (tur_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or tur_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			} else {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,tur_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and time between '" + stime + "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			}
		case "PH":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,ph_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (ph_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or ph_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			} else {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,ph_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and time between '" + stime + "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			}
		case "HOCL":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,hocl_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (hocl_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or hocl_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			} else {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,hocl_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and time between '" + stime + "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			}
		case "COD":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,cod_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (cod_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or cod_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			} else {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,cod_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and time between '" + stime + "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			}
		case "TN":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,tn_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (tn_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or tn_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			} else {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,tn_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and time between '" + stime + "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			}
		case "TP":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,tp_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (tp_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or tp_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			} else {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,tp_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and time between '" + stime + "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			}
		case "SS":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,ss_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (ss_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or ss_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			} else {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,ss_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and time between '" + stime + "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			}
		case "FLS":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,fls_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (fls_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or fls_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			} else {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,fls_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and time between '" + stime + "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			}
		case "PTOUT":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,ptout_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (ptout_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or ptout_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			} else {
				sql = "select c.* from (select s.site_name,b.*,rownum rn from(select site_id,ptout_alarm,time from alarmtotal where (site_id='"
						+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and time between '" + stime + "' and '" + etime
						+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
						+ offset + " and " + limit;
				break;
			}
		case "3":
			sql = "select c.* from (select s.site_name,b.*,rownum rn from(select * from alarmtotal where (site_id='"
					+ siteId1 + "' or site_id='" + siteId2 + "' or site_id='" + siteId3 + "' or site_id='" + siteId4
					+ "') and time between '" + stime + "' and '" + etime
					+ "' order by time asc) b left join \"BASESYS\".sys_site s on b.site_id=s.site_id) c where c.rn between "
					+ offset + " and " + limit;
			break;
		default:
			sql = "select * from alarmtotal where 1=2";
			break;
		}
		return monitorDao.queryAlarmData(sql);
	}

	@Override
	public int queryAlarmCount(String siteId, String level1, String level2, String type, String stime, String etime) {
		boolean str = siteId.contains(",");
		String siteId1 = siteId;
		String siteId2 = "";
		String siteId3 = "";
		String siteId4 = "";
		if (str) {
			if (siteId.split(",").length > 3) {
				siteId1 = siteId.split(",")[0];
				siteId2 = siteId.split(",")[1];
				siteId3 = siteId.split(",")[2];
				siteId4 = siteId.split(",")[3];
			} else if (siteId.split(",").length > 2) {
				siteId1 = siteId.split(",")[0];
				siteId2 = siteId.split(",")[1];
				siteId3 = siteId.split(",")[2];
			} else {
				siteId1 = siteId.split(",")[0];
				siteId2 = siteId.split(",")[1];
			}
		}

		String sql = "";
		switch (type) {
		case "TUR":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (tur_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or tur_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			} else {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4 + "') and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			}
		case "PH":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (ph_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or ph_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			} else {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4 + "') and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			}
		case "HOCL":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (hocl_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or hocl_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			} else {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4 + "') and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			}
		case "COD":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (cod_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or cod_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			} else {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4 + "') and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			}
		case "TN":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (tn_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or tn_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			} else {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4 + "') and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			}
		case "TP":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (tp_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or tp_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			} else {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4 + "') and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			}
		case "SS":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (ss_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or ss_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			} else {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4 + "') and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			}
		case "FLS":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (fls_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or fls_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			} else {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4 + "') and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			}
		case "PTOUT":
			if (!"0".equals(level1) && !"0".equals(level2)) {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4
						+ "') and (ptout_alarm like concat('%',concat('," + level1 + "','%')) "
						+ "or ptout_alarm like concat('%',concat('," + level2 + "','%'))) and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			} else {
				sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
						+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4 + "') and time between '" + stime
						+ "' and '" + etime + "'";
				break;
			}
		case "3":
			sql = "select count(*) from alarmtotal where (site_id='" + siteId1 + "' or site_id='" + siteId2
					+ "' or site_id='" + siteId3 + "' or site_id='" + siteId4 + "') and time between '" + stime
					+ "' and '" + etime + "'";
			break;
		default:
			sql = "select count(*) from alarmtotal where 1=2";
			break;
		}
		return monitorDao.queryAlarmCount(sql);
	}

	@Override
	public List<AlarmTotalEntity> queryInsAlarm(String stime, String etime) {
		// TODO Auto-generated method stub
		return monitorDao.queryInsAlarm(stime, etime);
	}

	/*
	 * @Override public List<MonitorTotalEntity> queryHisData(String type,String
	 * siteId, String stime, String etime) { // TODO Auto-generated method stub
	 * return monitorDao.queryHisData(type,siteId, stime, etime); }
	 */

	@Override
	public List<AlarmTotalEntity> listAlarm(String systemType, String wscSiteId, String siteTypeList, String areaId,
			String typeColumn, String beginTime, String endTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("select *");
		sql.append(" from MONITOR.ALARMTOTAL");
		sql.append(" where 1=1");
		// 供水分支
		if (systemType.equals("gs")) {
			if (!"".equals(areaId) && !"".equals(siteTypeList)) {
				sql.append(" and SITE_ID in (select site_id from BASESYS.SYS_SITE where area_id = '" + areaId
						+ "' and DIC_VALUE in " + siteTypeList + ")");
			} else if ("".equals(areaId) && !"".equals(siteTypeList)) {
				sql.append(" and SITE_ID in (select site_id from BASESYS.SYS_SITE where DIC_VALUE in " + siteTypeList
						+ ")");
			} else {
				sql.append(
						" and SITE_ID in (select site_id from BASESYS.SYS_SITE where DIC_VALUE in ('S02','S03','S04','S05','S06'))");
			}
		}
		// 排水分支
		if (systemType.equals("ps")) {
			if ("".equals(areaId) && !"".equals(siteTypeList)) {
				sql.append(" and SITE_ID in (select site_id from BASESYS.SYS_SITE where DIC_VALUE in " + siteTypeList
						+ ")");
			} else {
				sql.append(
						" and SITE_ID in (select site_id from BASESYS.SYS_SITE where DIC_VALUE in ('P01','P02','P03'))");
			}
		}
		// 污水分支
		if (systemType.equals("ws")) {
			if (!"".equals(wscSiteId)) {
				sql.append(" and SITE_ID in (select site_id from BASESYS.SYS_SITE where site_id = '" + wscSiteId
						+ "' or SITE_PARENTID = '" + wscSiteId + "' )");
			} else {
				sql.append(
						" and SITE_ID in (select site_id from BASESYS.SYS_SITE where site_id in ('2-WC-TZH', '4-WC-CB', '35-WC-JS') or SITE_PARENTID in ('2-WC-TZH', '4-WC-CB', '35-WC-JS'))");
			}
		}
		if (null != typeColumn && !"".equals(typeColumn)) {
			sql.append(" and " + typeColumn + " is not null");
		}
		sql.append(" and TIME between '" + beginTime + "' and '" + endTime + "' ");
		// sql.append(" and TO_DATE('"+ beginTime +"','YYYY-MM-DD HH24:MI:SS') <
		// TO_DATE(TIME,'YYYY-MM-DD HH24:MI:SS')\n" +
		// " and TO_DATE('"+ endTime +"','YYYY-MM-DD HH24:MI:SS') >
		// TO_DATE(TIME,'YYYY-MM-DD HH24:MI:SS')");
		System.out.println(sql);
		String finalSql = new String(sql);
		return monitorDao.listAlarm(finalSql);
	}

	@Override
	public List<ThresholdAndName> queryThreAndDic() {
		return monitorDao.queryThreAndDic();
	}

	@Override
	public List<AlarmTypeFromDic> queryAlarmTypeFromDic() {
		return monitorDao.queryAlarmTypeFromDic();
	}

	@Override
	public int queryHisCount(String siteId, String stime, String etime) {
		// TODO Auto-generated method stub
		return monitorDao.queryHisCount(siteId, stime, etime);
	}

	@Override
	public List<MonitorTotalEntity> queryHisData2(String siteId, String type, String stime, String etime, int offset,
			int limit2) {
		String sql = "";
		switch (type) {
		case "TUR":
			sql = "select * from (select site_id,turbidity_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "PH":
			sql = "select * from (select site_id,ph_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "HOCL":
			sql = "select * from (select site_id,chlorine_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "COD":
			sql = "select * from (select site_id,cod_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "TN":
			sql = "select * from (select site_id,tn_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "TP":
			sql = "select * from (select site_id,tp_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "SS":
			sql = "select * from (select site_id,ss_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "RAIN":
			sql = "select * from (select site_id,rain,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "TEMPERATURE":
			sql = "select * from (select site_id,temperature,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "PTIN":
			sql = "select * from (select site_id,pressure_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "FLS":
			sql = "select * from (select site_id,flow_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "FLC":
			sql = "select * from (select site_id,totalflow_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "LEVEL":
			sql = "select * from (select site_id,lt_value,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "PTOUT":
			sql = "select * from (select site_id,pressure_outvalue,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "PTSET":
			sql = "select * from (select site_id,pressure_setvalue,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "LTSET":
			sql = "select * from (select site_id,lt_setvalue,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "START":
			sql = "select * from (select site_id,pump_start,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "STOP":
			sql = "select * from (select site_id,pump_stop,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "FAULT":
			sql = "select * from (select site_id,pump_fault,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "Ar":
			sql = "select * from (select site_id,pump_a,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		case "Fr":
			sql = "select * from (select site_id,pump_r,time from monitortotal where site_id='" + siteId
					+ "' and time between '" + stime + "' and '" + etime + "' order by time asc) where rownum between "
					+ offset + " and " + limit2;
			break;
		default:
			sql = "select * from MONITORTOTAL where 1=2";
			break;
		}

		return monitorDao.queryHisData(sql);
	}

	@Override
	public MonitorTotalEntity queryTodayMonitor(String time, String siteId) {
		// TODO Auto-generated method stub
		return monitorDao.queryTodayMonitor(time, siteId);
	}

	@Override
	public List<MonitorTotalEntity> queryMinTimeMonitor(String time) {
		// TODO Auto-generated method stub
		return monitorDao.queryMinTimeMonitor(time);
	}

	@Override
	public MonitorTotalEntity queryMinTimeMonitor2(String time, String siteId) {
		// TODO Auto-generated method stub
		return monitorDao.queryMinTimeMonitor2(time, siteId);
	}

	@Override
	public List<AlarmIgnoreEntity> selectAllIgnore() {
		return alarmWindowDao.selectAllIgnore();
	}

	@Override
	public List<AlarmTotalEntity> alarmWindow(int hours) {
		Date date = new Date();
		date.setTime(date.getTime() - hours * 60 * 60 * 1000);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stringDate = simpleDateFormat.format(date);
		return alarmWindowDao.alarmWindow(stringDate);
	}

	@Override
	public Boolean insertIgnore(String siteId, String type, String hours) {
		AlarmIgnoreEntity entity = new AlarmIgnoreEntity();
		entity.setSiteId(siteId);
		entity.setType(type);

		Date date = new Date();
		int intHours = Integer.parseInt(hours);
		date.setTime(date.getTime() + intHours * 60 * 60 * 1000);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = simpleDateFormat.format(date);
		entity.setEndTime(now);

		// 点击忽略时，顺便删除过期忽略
		Date nowDate = new Date();
		alarmWindowDao.deleteOverdue(simpleDateFormat.format(nowDate));
		return alarmWindowDao.insertIgnore(entity);
	}

	@Override
	public void updateSiteStatus() {
		// 30分钟内报警数据
		Date date = new Date();
		date.setTime(date.getTime() - 30 * 60 * 1000);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stringDate = simpleDateFormat.format(date);
		List<AlarmTotalEntity> alarmList = alarmWindowDao.alarmWindow(stringDate);
		List<String> alarmSiteIdList = alarmList.stream().map(AlarmTotalEntity::getSiteId).collect(Collectors.toList());
		// 更新报警点位的 site_status 为 “报警”
		if (0 < alarmSiteIdList.size()) {
			sysSiteDao.updateSiteStatus("3", alarmSiteIdList);
		}
		// 查询sysSite表中状态为 “报警”的点位
		List<SysSiteEntity> alarmStatusSiteList = sysSiteDao.queryByStatus("3");
		List<String> alarmStatusSiteIdList = alarmStatusSiteList.stream().map(SysSiteEntity::getSiteId)
				.collect(Collectors.toList());
		// 需要被更新site_status为“正常”的siteList
		List<String> updateSiteIdList = new ArrayList<>();
		for (String alarmStatusSiteId : alarmStatusSiteIdList) {
			Boolean exist = false;
			for (String alarmSiteId : alarmSiteIdList) {
				if (alarmSiteId.equals(alarmStatusSiteId)) {
					exist = true;
				}
			}
			if (exist == false) {
				updateSiteIdList.add(alarmStatusSiteId);
			}
		}
		// 更新没有最新报警记录的点位的 site_status 为 “正常”
		if (0 < updateSiteIdList.size()) {
			sysSiteDao.updateSiteStatus("1", updateSiteIdList);
		}
	}

}
