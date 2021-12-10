/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.enjoyor.modules.monitor.controller;

import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.enjoyor.common.utils.RedisUtils;
import com.enjoyor.modules.monitor.entity.AlarmEntity;
import com.enjoyor.modules.monitor.entity.AlarmIgnoreEntity;
import com.enjoyor.modules.monitor.entity.AlarmTotalEntity;
import com.enjoyor.modules.monitor.entity.AlarmTypeFromDic;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.PipeDataEntity;
import com.enjoyor.modules.monitor.entity.PipeDataHis;
import com.enjoyor.modules.monitor.entity.ThresholdAndName;
import com.enjoyor.modules.monitor.service.MonitorService;
import com.enjoyor.modules.monitor.service.PipeDataHisService;
import com.enjoyor.modules.sys.controller.AbstractController;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.sys.service.SysSiteService;

import net.sf.json.JSONArray;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/monitor/data")
public class MonitorController extends AbstractController {

	@Autowired
	private MonitorService monitorService;

	@Autowired
	private SysSiteService sysSiteService;

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private PipeDataHisService pipeDataHisService;

	/*
	 * 请求历史数据---图表
	 * 
	 */
	@RequiresPermissions("mon:his:list")
	@RequestMapping("/queryHisData")
	@ResponseBody
	public JSONArray queryHisData(@RequestBody Map<String, Object> params) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String siteId1 = (String) params.get("siteId1");
		String site_Id1 = siteId1.split(",")[0];
		String type1 = siteId1.split(",")[1];
		String siteId2 = (String) params.get("siteId2");
		String site_Id2 = siteId2.split(",")[0];
		String type2 = siteId2.split(",")[1];
		String stime = (String) params.get("stime");
		String etime = (String) params.get("etime");
		if (null == stime || "".equals(stime)) {
			stime = sdf2.format(Calendar.getInstance().getTime());
		}
		if (null == etime || "".equals(etime)) {
			etime = sdf1.format(Calendar.getInstance().getTime());
		}

		Map<String, Object> params2 = new HashMap<String, Object>();
		// 该点位在时间范围内的数据数量
		int totalnum = monitorService.queryHisCount(site_Id1, stime, etime);
		if ("FLS".equals(type1) || "FLC".equals(type1)) {
			List<Map<String, Object>> mt = new ArrayList<Map<String, Object>>();
			List<SysSiteEntity> site = sysSiteService.querySiteByS04(site_Id1);
			if (null != site && 0 != site.size()) {
				for (SysSiteEntity sysSiteEntity : site) {
					List<PipeDataHis> list1 = pipeDataHisService.queryHisData(sysSiteEntity.getSiteId(), type1, stime,
							etime);
					for (PipeDataHis pipeDataHis2 : list1) {
						Map<String, Object> mte = new HashMap<String, Object>();
						mte.put("TIME", sdf1.format(pipeDataHis2.getTime()));
						mte.put("SITE_ID", pipeDataHis2.getPointNum());

						if (null != pipeDataHis2.getSs()) {
							mte.put("FLOW_VALUE", pipeDataHis2.getSs());
						}
						if (null != pipeDataHis2.getJlj()) {
							mte.put("TOTALFLOW_VALUE", pipeDataHis2.getJlj());
						}
						mt.add(mte);
					}
					params2.put(site_Id1 + type1, mt);
				}
			} else {
				List<MonitorTotalEntity> list1 = monitorService.queryHisData2(site_Id1, type1, stime, etime, 0,
						totalnum);
				params2.put(site_Id1 + type1, list1);
			}
		}

		int totalnum2 = monitorService.queryHisCount(site_Id2, stime, etime);
		if ("FLS".equals(type2) || "FLC".equals(type2)) {

			List<Map<String, Object>> mt = new ArrayList<Map<String, Object>>();
			// 查询该点位的管网子点位
			List<SysSiteEntity> site = sysSiteService.querySiteByS04(site_Id2);
			if (null != site && 0 != site.size()) {
				for (SysSiteEntity sysSiteEntity : site) {
					List<PipeDataHis> list2 = pipeDataHisService.queryHisData(sysSiteEntity.getSiteId(), type2, stime,
							etime);
					for (PipeDataHis pipeDataHis2 : list2) {
						Map<String, Object> mte = new HashMap<String, Object>();
						mte.put("TIME", sdf1.format(pipeDataHis2.getTime()));
						mte.put("SITE_ID", pipeDataHis2.getPointNum());

						if (null != pipeDataHis2.getSs()) {
							mte.put("FLOW_VALUE", pipeDataHis2.getSs());
						}
						if (null != pipeDataHis2.getJlj()) {
							mte.put("TOTALFLOW_VALUE", pipeDataHis2.getJlj());
						}
						mt.add(mte);
					}
					params2.put(site_Id2 + type2, mt);
				}
			} else {
				List<MonitorTotalEntity> list2 = monitorService.queryHisData2(site_Id2, type2, stime, etime, 0,
						totalnum2);
				params2.put(site_Id2 + type2, list2);
			}
		}
		if (!"FLS".equals(type1) && !"FLC".equals(type1)) {
			List<MonitorTotalEntity> list1 = monitorService.queryHisData2(site_Id1, type1, stime, etime, 0, totalnum);
			params2.put(site_Id1 + type1, list1);
		}
		if (!"FLS".equals(type2) && !"FLC".equals(type2)) {
			List<MonitorTotalEntity> list2 = monitorService.queryHisData2(site_Id2, type2, stime, etime, 0, totalnum2);
			params2.put(site_Id2 + type2, list2);
		}

		JSONArray str = JSONArray.fromObject(params2);
		return str;
	}

	/*
	 * 请求历史数据---列表
	 * 
	 */
	@RequiresPermissions("mon:his:list")
	@RequestMapping("/queryHisDatalb")
	public Map<String, Object> queryHisDatalb(@RequestParam Map<String, Object> params) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		int currentpage = Integer.parseInt((String) params.get("page"));
		int limit = Integer.parseInt((String) params.get("limit"));

		int offset = (currentpage * limit) - limit + 1;
		int limit2 = currentpage * limit;

		String siteId1 = (String) params.get("siteId");
		String site_Id1 = siteId1.split(",")[0];
		String type1 = siteId1.split(",")[1];
		String stime = (String) params.get("stime");
		String etime = (String) params.get("etime");
		if (null == stime || "".equals(stime)) {
			stime = sdf2.format(Calendar.getInstance().getTime());
		}
		if (null == etime || "".equals(etime)) {
			etime = sdf1.format(Calendar.getInstance().getTime());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if ("FLS".equals(type1) || "FLC".equals(type1)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<SysSiteEntity> site = sysSiteService.querySiteByS04(site_Id1);
			if (null != site && 0 != site.size()) {
				for (SysSiteEntity sysSiteEntity : site) {
					List<Map<String, Object>> mt = new ArrayList<Map<String, Object>>();
					List<PipeDataHis> list = pipeDataHisService.queryHisData(sysSiteEntity.getSiteId(), type1, stime,
							etime);
					for (PipeDataHis pipeDataHis : list) {
						Map<String, Object> mte = new HashMap<String, Object>();
						mte.put("TIME", sdf.format(pipeDataHis.getTime()));
						mte.put("SITE_ID", pipeDataHis.getPointNum());

						if (null != pipeDataHis.getSs()) {
							mte.put("FLOW_VALUE", pipeDataHis.getSs());
						}
						if (null != pipeDataHis.getJlj()) {
							mte.put("TOTALFLOW_VALUE", pipeDataHis.getJlj());
						}
						mt.add(mte);
					}
					List<Map<String, Object>> pageList = new ArrayList<>();
					if (mt != null && mt.size() > 0) {
						int fromIndex = (currentpage - 1) * limit;
						int toIndex = currentpage * limit;
						if (toIndex > mt.size()) {
							toIndex = mt.size();
						}
						pageList = mt.subList(fromIndex, toIndex);
					}
					map.put("count", mt.size());
					map.put("data", pageList);
				}
			} else {
				List<MonitorTotalEntity> list1 = monitorService.queryHisData(site_Id1, type1, stime, etime, offset,
						limit2);
				int totalnum = monitorService.queryHisCount(site_Id1, stime, etime);
				map.put("data", list1);
				map.put("count", totalnum);
			}
		} else {

			List<MonitorTotalEntity> list1 = monitorService.queryHisData(site_Id1, type1, stime, etime, offset, limit2);
			int totalnum = monitorService.queryHisCount(site_Id1, stime, etime);
			map.put("data", list1);
			map.put("count", totalnum);
		}
		map.put("code", 0);
		map.put("message", "");

		// JSONArray str =JSONArray.fromObject(map);

		return map;
	}

	/*
	 * 请求报警历史数据
	 */
	@RequiresPermissions("mon:alarm:list")
	@RequestMapping("/queryAlarmData")
	public Map<String, Object> queryAlarmData(@RequestParam Map<String, Object> params) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		int currentpage = Integer.parseInt((String) params.get("page"));
		int offset = (currentpage * 10) - 9;
		int limit = currentpage * 10;
		String stime = (String) params.get("stime");
		String etime = (String) params.get("etime");
		if (null == stime || "".equals(stime)) {
			stime = sdf2.format(Calendar.getInstance().getTime());
		}
		if (null == etime || "".equals(etime)) {
			etime = sdf1.format(Calendar.getInstance().getTime());
		}
		String siteId = (String) params.get("siteId");
		String level = (String) params.get("level");
		String type = (String) params.get("type");
		String level1 = "0";
		String level2 = "0";
		if ("1".equals(level)) {
			level1 = "2";
			level2 = "4";
		} else if ("2".equals(level)) {
			level1 = "1";
			level2 = "5";
		} else if ("4".equals(level)) {
			level1 = "6";
			level2 = "6";
		} else {
			level1 = "0";
			level2 = "0";
		}

		List<AlarmTotalEntity> list = monitorService.queryAlarmData(siteId, level1, level2, type, stime, etime, offset,
				limit);
		List<AlarmEntity> list2 = new ArrayList<AlarmEntity>();
		for (AlarmTotalEntity alarmTotalEntity : list) {
			AlarmEntity ae = new AlarmEntity();
			ae.setTime(alarmTotalEntity.getTime());
			ae.setSiteName(alarmTotalEntity.getSiteName());
			if (null != alarmTotalEntity.getCodAlarm() && !"".equals(alarmTotalEntity.getCodAlarm())) {
				if (!"6".equals(alarmTotalEntity.getCodAlarm().split(",")[1])) {
					ae.setParam("COD");
					ae.setValue(alarmTotalEntity.getCodAlarm().split(",")[0]);
					if ("2".equals(alarmTotalEntity.getCodAlarm().split(",")[1])) {
						ae.setType("报警下限");
						ae.setLevel("异常");
						ae.setLow(alarmTotalEntity.getCodAlarm().split(",")[2]);
					} else if ("1".equals(alarmTotalEntity.getCodAlarm().split(",")[1])) {
						ae.setType("故障下限");
						ae.setLevel("故障");
						ae.setLower(alarmTotalEntity.getCodAlarm().split(",")[2]);
					} else if ("4".equals(alarmTotalEntity.getCodAlarm().split(",")[1])) {
						ae.setType("报警上限");
						ae.setLevel("异常");
						ae.setHigh(alarmTotalEntity.getCodAlarm().split(",")[2]);
					} else if ("5".equals(alarmTotalEntity.getCodAlarm().split(",")[1])) {
						ae.setType("故障上限");
						ae.setLevel("故障");
						ae.setHigher(alarmTotalEntity.getCodAlarm().split(",")[2]);
					}
				} else {
					ae.setParam("COD");
					ae.setLevel("超时");
				}
			} else if (null != alarmTotalEntity.getFlsAlarm() && !"".equals(alarmTotalEntity.getFlsAlarm())) {
				if (!"6".equals(alarmTotalEntity.getFlsAlarm().split(",")[1])) {
					ae.setParam("流量");
					ae.setValue(alarmTotalEntity.getFlsAlarm().split(",")[0]);
					if ("2".equals(alarmTotalEntity.getFlsAlarm().split(",")[1])) {
						ae.setType("报警下限");
						ae.setLevel("异常");
						ae.setLow(alarmTotalEntity.getFlsAlarm().split(",")[2]);
					} else if ("1".equals(alarmTotalEntity.getFlsAlarm().split(",")[1])) {
						ae.setType("故障下限");
						ae.setLevel("故障");
						ae.setLower(alarmTotalEntity.getFlsAlarm().split(",")[2]);
					} else if ("4".equals(alarmTotalEntity.getFlsAlarm().split(",")[1])) {
						ae.setType("报警上限");
						ae.setLevel("异常");
						ae.setHigh(alarmTotalEntity.getFlsAlarm().split(",")[2]);
					} else if ("5".equals(alarmTotalEntity.getFlsAlarm().split(",")[1])) {
						ae.setType("故障上限");
						ae.setLevel("故障");
						ae.setHigher(alarmTotalEntity.getFlsAlarm().split(",")[2]);
					}
				} else {
					ae.setParam("流量");
					ae.setLevel("超时");
				}
			} else if (null != alarmTotalEntity.getHoclAlarm() && !"".equals(alarmTotalEntity.getHoclAlarm())) {
				if (!"6".equals(alarmTotalEntity.getHoclAlarm().split(",")[1])) {
					ae.setParam("余氯");
					ae.setValue(alarmTotalEntity.getHoclAlarm().split(",")[0]);
					if ("2".equals(alarmTotalEntity.getHoclAlarm().split(",")[1])) {
						ae.setType("报警下限");
						ae.setLevel("异常");
						ae.setLow(alarmTotalEntity.getHoclAlarm().split(",")[2]);
					} else if ("1".equals(alarmTotalEntity.getHoclAlarm().split(",")[1])) {
						ae.setType("故障下限");
						ae.setLevel("故障");
						ae.setLower(alarmTotalEntity.getHoclAlarm().split(",")[2]);
					} else if ("4".equals(alarmTotalEntity.getHoclAlarm().split(",")[1])) {
						ae.setType("报警上限");
						ae.setLevel("异常");
						ae.setHigh(alarmTotalEntity.getHoclAlarm().split(",")[2]);
					} else if ("5".equals(alarmTotalEntity.getHoclAlarm().split(",")[1])) {
						ae.setType("故障上限");
						ae.setLevel("故障");
						ae.setHigher(alarmTotalEntity.getHoclAlarm().split(",")[2]);
					}
				} else {
					ae.setParam("余氯");
					ae.setLevel("超时");
				}
			} else if (null != alarmTotalEntity.getPhAlarm() && !"".equals(alarmTotalEntity.getPhAlarm())) {
				if (!"6".equals(alarmTotalEntity.getPhAlarm().split(",")[1])) {
					ae.setParam("PH");
					ae.setValue(alarmTotalEntity.getPhAlarm().split(",")[0]);
					if ("2".equals(alarmTotalEntity.getPhAlarm().split(",")[1])) {
						ae.setType("报警下限");
						ae.setLevel("异常");
						ae.setLow(alarmTotalEntity.getPhAlarm().split(",")[2]);
					} else if ("1".equals(alarmTotalEntity.getPhAlarm().split(",")[1])) {
						ae.setType("故障下限");
						ae.setLevel("故障");
						ae.setLower(alarmTotalEntity.getPhAlarm().split(",")[2]);
					} else if ("4".equals(alarmTotalEntity.getPhAlarm().split(",")[1])) {
						ae.setType("报警上限");
						ae.setLevel("异常");
						ae.setHigh(alarmTotalEntity.getPhAlarm().split(",")[2]);
					} else if ("5".equals(alarmTotalEntity.getPhAlarm().split(",")[1])) {
						ae.setType("故障上限");
						ae.setLevel("故障");
						ae.setHigher(alarmTotalEntity.getPhAlarm().split(",")[2]);
					}
				} else {
					ae.setParam("PH");
					ae.setLevel("超时");
				}
			} else if (null != alarmTotalEntity.getPtoutAlarm() && !"".equals(alarmTotalEntity.getPtoutAlarm())) {
				if (!"6".equals(alarmTotalEntity.getPtoutAlarm().split(",")[1])) {
					ae.setParam("压力");
					ae.setValue(alarmTotalEntity.getPtoutAlarm().split(",")[0]);
					if ("2".equals(alarmTotalEntity.getPtoutAlarm().split(",")[1])) {
						ae.setType("报警下限");
						ae.setLevel("异常");
						ae.setLow(alarmTotalEntity.getPtoutAlarm().split(",")[2]);
					} else if ("1".equals(alarmTotalEntity.getPtoutAlarm().split(",")[1])) {
						ae.setType("故障下限");
						ae.setLevel("故障");
						ae.setLower(alarmTotalEntity.getPtoutAlarm().split(",")[2]);
					} else if ("4".equals(alarmTotalEntity.getPtoutAlarm().split(",")[1])) {
						ae.setType("报警上限");
						ae.setLevel("异常");
						ae.setHigh(alarmTotalEntity.getPtoutAlarm().split(",")[2]);
					} else if ("5".equals(alarmTotalEntity.getPtoutAlarm().split(",")[1])) {
						ae.setType("故障上限");
						ae.setLevel("故障");
						ae.setHigher(alarmTotalEntity.getPtoutAlarm().split(",")[2]);
					}
				} else {
					ae.setParam("压力");
					ae.setLevel("超时");
				}
			} else if (null != alarmTotalEntity.getSsAlarm() && !"".equals(alarmTotalEntity.getSsAlarm())) {
				if (!"6".equals(alarmTotalEntity.getSsAlarm().split(",")[1])) {
					ae.setParam("SS");
					ae.setValue(alarmTotalEntity.getSsAlarm().split(",")[0]);
					if ("2".equals(alarmTotalEntity.getSsAlarm().split(",")[1])) {
						ae.setType("报警下限");
						ae.setLevel("异常");
						ae.setLow(alarmTotalEntity.getSsAlarm().split(",")[2]);
					} else if ("1".equals(alarmTotalEntity.getSsAlarm().split(",")[1])) {
						ae.setType("故障下限");
						ae.setLevel("故障");
						ae.setLower(alarmTotalEntity.getSsAlarm().split(",")[2]);
					} else if ("4".equals(alarmTotalEntity.getSsAlarm().split(",")[1])) {
						ae.setType("报警上限");
						ae.setLevel("异常");
						ae.setHigh(alarmTotalEntity.getSsAlarm().split(",")[2]);
					} else if ("5".equals(alarmTotalEntity.getSsAlarm().split(",")[1])) {
						ae.setType("故障上限");
						ae.setLevel("故障");
						ae.setHigher(alarmTotalEntity.getSsAlarm().split(",")[2]);
					}
				} else {
					ae.setParam("SS");
					ae.setLevel("超时");
				}
			} else if (null != alarmTotalEntity.getTnAlarm() && !"".equals(alarmTotalEntity.getTnAlarm())) {
				if (!"6".equals(alarmTotalEntity.getTnAlarm().split(",")[1])) {
					ae.setParam("总氮");
					ae.setValue(alarmTotalEntity.getTnAlarm().split(",")[0]);
					if ("2".equals(alarmTotalEntity.getTnAlarm().split(",")[1])) {
						ae.setType("报警下限");
						ae.setLevel("异常");
						ae.setLow(alarmTotalEntity.getTnAlarm().split(",")[2]);
					} else if ("1".equals(alarmTotalEntity.getTnAlarm().split(",")[1])) {
						ae.setType("故障下限");
						ae.setLevel("故障");
						ae.setLower(alarmTotalEntity.getTnAlarm().split(",")[2]);
					} else if ("4".equals(alarmTotalEntity.getTnAlarm().split(",")[1])) {
						ae.setType("报警上限");
						ae.setLevel("异常");
						ae.setHigh(alarmTotalEntity.getTnAlarm().split(",")[2]);
					} else if ("5".equals(alarmTotalEntity.getTnAlarm().split(",")[1])) {
						ae.setType("故障上限");
						ae.setLevel("故障");
						ae.setHigher(alarmTotalEntity.getTnAlarm().split(",")[2]);
					}
				} else {
					ae.setParam("总氮");
					ae.setLevel("超时");
				}
			} else if (null != alarmTotalEntity.getTpAlarm() && !"".equals(alarmTotalEntity.getTpAlarm())) {
				if (!"6".equals(alarmTotalEntity.getTpAlarm().split(",")[1])) {
					ae.setParam("总磷");
					ae.setValue(alarmTotalEntity.getTpAlarm().split(",")[0]);
					if ("2".equals(alarmTotalEntity.getTpAlarm().split(",")[1])) {
						ae.setType("报警下限");
						ae.setLevel("异常");
						ae.setLow(alarmTotalEntity.getTpAlarm().split(",")[2]);
					} else if ("1".equals(alarmTotalEntity.getTpAlarm().split(",")[1])) {
						ae.setType("故障下限");
						ae.setLevel("故障");
						ae.setLower(alarmTotalEntity.getTpAlarm().split(",")[2]);
					} else if ("4".equals(alarmTotalEntity.getTpAlarm().split(",")[1])) {
						ae.setType("报警上限");
						ae.setLevel("异常");
						ae.setHigh(alarmTotalEntity.getTpAlarm().split(",")[2]);
					} else if ("5".equals(alarmTotalEntity.getTpAlarm().split(",")[1])) {
						ae.setType("故障上限");
						ae.setLevel("故障");
						ae.setHigher(alarmTotalEntity.getTpAlarm().split(",")[2]);
					}
				} else {
					ae.setParam("总磷");
					ae.setLevel("超时");
				}
			} else if (null != alarmTotalEntity.getTurAlarm() && !"".equals(alarmTotalEntity.getTurAlarm())) {
				if (!"6".equals(alarmTotalEntity.getTurAlarm().split(",")[1])) {
					ae.setParam("浊度");
					ae.setValue(alarmTotalEntity.getTurAlarm().split(",")[0]);
					if ("2".equals(alarmTotalEntity.getTurAlarm().split(",")[1])) {
						ae.setType("报警下限");
						ae.setLevel("异常");
						ae.setLow(alarmTotalEntity.getTurAlarm().split(",")[2]);
					} else if ("1".equals(alarmTotalEntity.getTurAlarm().split(",")[1])) {
						ae.setType("故障下限");
						ae.setLevel("故障");
						ae.setLower(alarmTotalEntity.getTurAlarm().split(",")[2]);
					} else if ("4".equals(alarmTotalEntity.getTurAlarm().split(",")[1])) {
						ae.setType("报警上限");
						ae.setLevel("异常");
						ae.setHigh(alarmTotalEntity.getTurAlarm().split(",")[2]);
					} else if ("5".equals(alarmTotalEntity.getTurAlarm().split(",")[1])) {
						ae.setType("故障上限");
						ae.setLevel("故障");
						ae.setHigher(alarmTotalEntity.getTurAlarm().split(",")[2]);
					}
				} else {
					ae.setParam("浊度");
					ae.setLevel("超时");
				}
			}
			list2.add(ae);
		}
		int totalNum = monitorService.queryAlarmCount(siteId, level1, level2, type, stime, etime);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("count", totalNum);
		map.put("message", "");
		map.put("data", list2);

		// JSONArray str =JSONArray.fromObject(list);
		return map;
	}

	/*
	 * 请求报警实时数据
	 * 
	 */
	@RequestMapping("/queryAlarmInsData")
	@ResponseBody
	public JSONArray queryAlarmInsData(@RequestBody Map<String, Object> params) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		// String tag=(String)params.get("tag");
		List<PipeDataEntity> pd = new ArrayList<PipeDataEntity>();
		Set<String> keys = redisUtils.getAll();
		for (String string : keys) {
			if (!"Threshold".equals(string)) {
				String re = redisUtils.get(string);
				PipeDataEntity infoDo = JSON.parseObject(re, PipeDataEntity.class);
				if (!sdf2.format(Calendar.getInstance().getTime()).equals(sdf2.format(infoDo.getTime()))) {
					continue;
				}
				String yl = "";
				if (null != infoDo.getYlAlarm() && !"".equals(infoDo.getYlAlarm())) {
					yl = infoDo.getYlAlarm().split(",")[0];
				}
				String cho = "";
				if (null != infoDo.getChoAlarm() && !"".equals(infoDo.getChoAlarm())) {
					cho = infoDo.getChoAlarm().split(",")[0];
				}
				String ph = "";
				if (null != infoDo.getPhAlarm() && !"".equals(infoDo.getPhAlarm())) {
					ph = infoDo.getPhAlarm().split(",")[0];
				}
				String tur = "";
				if (null != infoDo.getTurAlarm() && !"".equals(infoDo.getTurAlarm())) {
					tur = infoDo.getTurAlarm().split(",")[0];
				}
				String cod = "";
				if (null != infoDo.getCodAlarm() && !"".equals(infoDo.getCodAlarm())) {
					cod = infoDo.getCodAlarm().split(",")[0];
				}
				String tn = "";
				if (null != infoDo.getTnAlarm() && !"".equals(infoDo.getTnAlarm())) {
					tn = infoDo.getTnAlarm().split(",")[0];
				}
				String tp = "";
				if (null != infoDo.getTpAlarm() && !"".equals(infoDo.getTpAlarm())) {
					tp = infoDo.getTpAlarm().split(",")[0];
				}
				String ss = "";
				if (null != infoDo.getSsAlarm() && !"".equals(infoDo.getSsAlarm())) {
					ss = infoDo.getSsAlarm().split(",")[0];
				}
				if ("3".equals(yl) || "3".equals(cho) || "3".equals(ph) || "3".equals(tur) || "3".equals(cod)
						|| "3".equals(tn) || "3".equals(tp) || "3".equals(ss)) {
					continue;
				}
				if ("".equals(yl) && "".equals(cho) && "".equals(ph) && "".equals(tur) && "".equals(cod)
						&& "".equals(tn) && "".equals(tp) && "".equals(ss)) {
					continue;
				}
				infoDo.setTime2(sdf1.format(infoDo.getTime()));
				pd.add(infoDo);
			}
		}
		JSONArray str = JSONArray.fromObject(pd);
		return str;
	}

	@RequestMapping("/findAlarmData")
	@ResponseBody
	public Map<String, Object> findAlarmData(@RequestParam Map<String, Object> params) {
		int pageNum = Integer.valueOf((String) params.get("page"));
		int pageSize = Integer.valueOf((String) params.get("limit"));
		String areaId = (String) params.get("areaId");
		String levelName = (String) params.get("levelName"); // 报警等级:"异常","故障","超时"
		List<String> levelList = new ArrayList<>();
		switch (levelName) {
		case "异常":
			levelList.add("2");
			levelList.add("4");
			break;
		case "故障":
			levelList.add("1");
			levelList.add("5");
			break;
		case "超时":
			levelList.add("9");
			break;
		default:
			break;
		}
		String type = (String) params.get("type"); // 检测参数:"流量","压力","COD"....
		String siteType = (String) params.get("siteType"); // 点位类型:"gsc","gsbz"....
		String systemType = (String) params.get("systemType");
		String wscSiteId = ""; // 污水专用参数
		String siteTypeList = "";
		String typeColumn = null;
		if (systemType.equals("gs")) {
			switch (siteType) {
			case "gsc":
				siteTypeList = "('S02')";
				break;
			case "gsbz":
				siteTypeList = "('S03')";
				break;
			case "gsgw":
				siteTypeList = "('S04','S05','S06')";
				break;
			default:
				break;
			}
		}
		if (systemType.equals("ps")) {
			switch (siteType) {
			case "psgw":
				siteTypeList = "('P02','P03')";
				break;
			case "psbz":
				siteTypeList = "('P01')";
				break;
			default:
				break;
			}
		}
		if (systemType.equals("ws")) {
			switch (siteType) {
			case "tzhwsc":
				wscSiteId = "2-WC-TZH";
				break;
			case "cbwsc":
				wscSiteId = "4-WC-CB";
				break;
			case "jswsc":
				wscSiteId = "35-WC-JS";
				break;
			default:
				break;
			}
		}
		switch (type) {
		case "流量":
			typeColumn = "FLS_ALARM";
			break;
		case "压力":
			typeColumn = "PTOUT_ALARM";
			break;
		case "COD":
			typeColumn = "COD_ALARM";
			break;
		case "PH":
			typeColumn = "PH_ALARM";
			break;
		case "余氯":
			typeColumn = "HOCL_ALARM";
			break;
		case "浊度":
			typeColumn = "TUR_ALARM";
			break;
		case "SS":
			typeColumn = "SS_ALARM";
			break;
		case "氨氮":
			typeColumn = "TN_ALARM";
			break;
		case "总磷":
			typeColumn = "TP_ALARM";
			break;
		default:
			break;
		}

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String beginTime = (String) params.get("stime");
		String endTime = (String) params.get("etime");
		long nh = 1000 * 60 * 60;
		if (null == beginTime || "".equals(beginTime)) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			Date beginTimeDate = calendar.getTime();
			beginTime = sdf1.format(beginTimeDate);
		}
		if (null == endTime || "".equals(endTime)) {
			endTime = sdf1.format(Calendar.getInstance().getTime());
		}
		// 时间跨度过大，直接返回提示
		Date beginDate = null;
		Date endDate = null;
		try {
			beginDate = sdf1.parse(beginTime);
			endDate = sdf1.parse(endTime);
		} catch (ParseException px) {
			px.printStackTrace();
		}
		long diff = endDate.getTime() - beginDate.getTime();
		long hour = diff / nh;
		System.out.println();
		// if(hour > 48) {
		// Map<String,Object> map = new HashMap<String, Object>();
		// map.put("message", "时间跨度只可选择两天内");
		// return map;
		// }

		// siteId和siteName列表
		List<SysSiteEntity> siteNameList = sysSiteService.queryAllSite();
		// 从字典表查询报警等级和报警类型
		List<AlarmTypeFromDic> alarmTypeFromDicList = monitorService.queryAlarmTypeFromDic();
		// 指标阈值和指标名字列表
		List<ThresholdAndName> thresholdAndNameList = monitorService.queryThreAndDic();
		// AlarmTotal表筛选片区后的list
		List<AlarmTotalEntity> alarmList = monitorService.listAlarm(systemType, wscSiteId, siteTypeList, areaId,
				typeColumn, beginTime, endTime);
		// 最终list
		List<AlarmEntity> list1 = new ArrayList<>();
		// 循环AlarmTotal表的数据，并生成多条不同水质指标的对象
		for (AlarmTotalEntity item : alarmList) {
			List<AlarmEntity> alarmEntityList = makeAlarmList(item, siteNameList, thresholdAndNameList,
					alarmTypeFromDicList);
			list1.addAll(alarmEntityList);
		}
		// 筛选 监测参数 与 报警等级
		Iterator<AlarmEntity> iterator = list1.iterator();
		if (null != type && !"".equals(type)) {
			while (iterator.hasNext()) {
				if (!iterator.next().getParam().equals(type)) {
					iterator.remove();
				}
			}
		}
		List<AlarmEntity> list2 = new ArrayList<>();
		list2.addAll(list1);
		Iterator<AlarmEntity> iterator2 = list2.iterator();
		if (null != levelList && 0 != levelList.size()) {
			while (iterator2.hasNext()) {
				if (!levelList.contains(iterator2.next().getLevel())) {
					iterator2.remove();
				}
			}
		}
		List<AlarmEntity> list = new ArrayList<>();
		list.addAll(list2);
		// 分页
		int totalPage = 0;
		if (list.size() % pageSize == 0) {
			totalPage = list.size() / pageSize;
		} else {
			totalPage = list.size() / pageSize + 1;
		}
		List<AlarmEntity> pageList = new ArrayList<>();
		if (list != null && list.size() > 0) {
			int fromIndex = (pageNum - 1) * pageSize;
			int toIndex = pageNum * pageSize;
			if (toIndex > list.size()) {
				toIndex = list.size();
			}
			pageList = list.subList(fromIndex, toIndex);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("message", "");
		map.put("data", pageList);
		map.put("count", list.size());
		return map;
	}

	private List<AlarmEntity> makeAlarmList(AlarmTotalEntity item, List<SysSiteEntity> siteNameList,
			List<ThresholdAndName> thresholdAndNameList, List<AlarmTypeFromDic> alarmTypeFromDicList) {
		List<AlarmEntity> list = new ArrayList<>();
		if (null != item.getFlsAlarm()) {
			list.add(makeAlarmEntity("流量", item, siteNameList, thresholdAndNameList, alarmTypeFromDicList));
		}
		if (null != item.getPtoutAlarm()) {
			list.add(makeAlarmEntity("压力", item, siteNameList, thresholdAndNameList, alarmTypeFromDicList));
		}
		if (null != item.getCodAlarm()) {
			list.add(makeAlarmEntity("COD", item, siteNameList, thresholdAndNameList, alarmTypeFromDicList));
		}
		if (null != item.getPhAlarm()) {
			list.add(makeAlarmEntity("PH", item, siteNameList, thresholdAndNameList, alarmTypeFromDicList));
		}
		if (null != item.getHoclAlarm()) {
			list.add(makeAlarmEntity("余氯", item, siteNameList, thresholdAndNameList, alarmTypeFromDicList));
		}
		if (null != item.getTurAlarm()) {
			list.add(makeAlarmEntity("浊度", item, siteNameList, thresholdAndNameList, alarmTypeFromDicList));
		}
		if (null != item.getSsAlarm()) {
			list.add(makeAlarmEntity("SS", item, siteNameList, thresholdAndNameList, alarmTypeFromDicList));
		}
		if (null != item.getTnAlarm()) {
			list.add(makeAlarmEntity("氨氮", item, siteNameList, thresholdAndNameList, alarmTypeFromDicList));
		}
		if (null != item.getTpAlarm()) {
			list.add(makeAlarmEntity("总磷", item, siteNameList, thresholdAndNameList, alarmTypeFromDicList));
		}
		return list;
	}

	private AlarmEntity makeAlarmEntity(String type, AlarmTotalEntity item, List<SysSiteEntity> siteNameList,
			List<ThresholdAndName> thresholdAndNameList, List<AlarmTypeFromDic> alarmTypeFromDicList) {
		AlarmEntity alarmEntity = new AlarmEntity();
		alarmEntity.setId(item.getId());
		alarmEntity.setTime(item.getTime());
		for (SysSiteEntity siteEntity : siteNameList) {
			if (item.getSiteId().equals(siteEntity.getSiteId())) {
				alarmEntity.setSiteName(siteEntity.getName());
			}
		}
		alarmEntity.setSiteId(item.getSiteId());
		alarmEntity.setParam(type);
		switch (type) {
		case "流量":
			alarmEntity.setValue(item.getFlsAlarm().split(",")[0]);
			alarmEntity.setLevel(item.getFlsAlarm().split(",")[1]);
			if ("4".equals(alarmEntity.getLevel())) {
				alarmEntity.setHigh(item.getFlsAlarm().split(",")[2]);
			}
			if ("2".equals(alarmEntity.getLevel())) {
				alarmEntity.setLow(item.getFlsAlarm().split(",")[2]);
			}
			break;
		case "压力":
			alarmEntity.setValue(item.getPtoutAlarm().split(",")[0]);
			alarmEntity.setLevel(item.getPtoutAlarm().split(",")[1]);
			break;
		case "COD":
			alarmEntity.setValue(item.getCodAlarm().split(",")[0]);
			alarmEntity.setLevel(item.getCodAlarm().split(",")[1]);
			break;
		case "PH":
			alarmEntity.setValue(item.getPhAlarm().split(",")[0]);
			alarmEntity.setLevel(item.getPhAlarm().split(",")[1]);
			break;
		case "余氯":
			alarmEntity.setValue(item.getHoclAlarm().split(",")[0]);
			alarmEntity.setLevel(item.getHoclAlarm().split(",")[1]);
			break;
		case "浊度":
			alarmEntity.setValue(item.getTurAlarm().split(",")[0]);
			alarmEntity.setLevel(item.getTurAlarm().split(",")[1]);
			break;
		case "SS":
			alarmEntity.setValue(item.getSsAlarm().split(",")[0]);
			alarmEntity.setLevel(item.getSsAlarm().split(",")[1]);
			break;
		case "氨氮":
			alarmEntity.setValue(item.getTnAlarm().split(",")[0]);
			alarmEntity.setLevel(item.getTnAlarm().split(",")[1]);
			break;
		case "总磷":
			alarmEntity.setValue(item.getTpAlarm().split(",")[0]);
			alarmEntity.setLevel(item.getTpAlarm().split(",")[1]);
			break;
		default:
			break;
		}
		// 循环字典表，set报警类型（故障下限，报警下限...）
		for (AlarmTypeFromDic alarmTypeFromDic : alarmTypeFromDicList) {
			if (alarmEntity.getLevel().equals(alarmTypeFromDic.getDicValue())) {
				alarmEntity.setType(alarmTypeFromDic.getDicSubitem()); // 故障，报警
			}
		}
		// 压力type文案修正
		if ("压力".equals(type)) {
			type = "供水压力";
		}
		// 循环阈值表，set上下限
		for (ThresholdAndName thresholdAndName : thresholdAndNameList) {
			if (type.equals(thresholdAndName.getDicSubitem()) && null == thresholdAndName.getSiteId()) {
				alarmEntity.setLow(String.valueOf(thresholdAndName.getLowValue()));
				alarmEntity.setLower(String.valueOf(thresholdAndName.getLowerValue()));
				alarmEntity.setHigh(String.valueOf(thresholdAndName.getHighValue()));
				alarmEntity.setHigher(String.valueOf(thresholdAndName.getHigherValue()));
			}
		}
		for (ThresholdAndName thresholdAndName : thresholdAndNameList) {
			if (item.getSiteId().equals(thresholdAndName.getSiteId())
					&& type.equals(thresholdAndName.getDicSubitem())) {
				alarmEntity.setLow(String.valueOf(thresholdAndName.getLowValue()));
				alarmEntity.setLower(String.valueOf(thresholdAndName.getLowerValue()));
				alarmEntity.setHigh(String.valueOf(thresholdAndName.getHighValue()));
				alarmEntity.setHigher(String.valueOf(thresholdAndName.getHigherValue()));
			}
		}
		BigDecimal bd = new BigDecimal(alarmEntity.getValue());
		alarmEntity.setValue(bd.setScale(3, RoundingMode.HALF_UP).toString());
		return alarmEntity;
	}

	@RequestMapping("/alarmWindow")
	@ResponseBody
	public Map<String, Object> alarmWindow() throws ParseException {
		// 获取6小时内alarmtotal表报警数据
		int hours = 6;
		List<AlarmTotalEntity> dataList = monitorService.alarmWindow(hours);

		// siteId和siteName列表
		List<SysSiteEntity> siteNameList = sysSiteService.queryAllSite();
		// 从字典表查询报警等级和报警类型
		List<AlarmTypeFromDic> alarmTypeFromDicList = monitorService.queryAlarmTypeFromDic();
		// 指标阈值和指标名字列表
		List<ThresholdAndName> thresholdAndNameList = monitorService.queryThreAndDic();
		// 最终list
		List<AlarmEntity> list1 = new ArrayList<>();
		// 循环AlarmTotal表的数据，并生成多条不同水质指标的对象
		for (AlarmTotalEntity item : dataList) {
			List<AlarmEntity> alarmEntityList = makeAlarmList(item, siteNameList, thresholdAndNameList,
					alarmTypeFromDicList);
			list1.addAll(alarmEntityList);
		}
		// 忽略 报警
		List<AlarmIgnoreEntity> ignoreList = monitorService.selectAllIgnore();
		Iterator<AlarmEntity> iterator = list1.iterator();
		while (iterator.hasNext()) {
			AlarmEntity alarmEntity = iterator.next();
			for (AlarmIgnoreEntity ignoreEntity : ignoreList) {
				if (alarmEntity.getSiteId().equals(ignoreEntity.getSiteId())
						&& alarmEntity.getParam().equals(ignoreEntity.getType())) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date entityDate = simpleDateFormat.parse(alarmEntity.getTime());
					Date ignoreDate = simpleDateFormat.parse(ignoreEntity.getEndTime());
					boolean before = entityDate.before(ignoreDate);
					if (before) {
						iterator.remove();
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list1);
		return map;
	}

	// 不在提示报警
	@RequestMapping("/insertIgnore")
	@ResponseBody
	public Boolean insertIgnore(@RequestBody Map<String, Object> params) {
		String siteId = (String) params.get("siteId");
		String type = (String) params.get("type");
		String hours = (String) params.get("hours");
		if ("999".equals(hours)) {
			Calendar calendar = Calendar.getInstance();
			int nowHours = calendar.get(calendar.HOUR_OF_DAY);
			hours = String.valueOf(24 - nowHours);
		}
		return monitorService.insertIgnore(siteId, type, hours);
	}

}
