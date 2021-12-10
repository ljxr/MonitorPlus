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


import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.enjoyor.common.utils.ExcelBook;
import com.enjoyor.common.utils.RegionUtils;
import com.enjoyor.modules.monitor.entity.DispatchLog;
import com.enjoyor.modules.monitor.entity.DispatchLogQueryDTO;
import com.enjoyor.modules.monitor.entity.FlowNightEntity;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.PipeData;
import com.enjoyor.modules.monitor.entity.PipeDataDTO;
import com.enjoyor.modules.monitor.entity.PipeDataHis;
import com.enjoyor.modules.monitor.entity.SGSiteDTO;
import com.enjoyor.modules.monitor.entity.SeatEntity;
import com.enjoyor.modules.monitor.service.DispatchLogService;
import com.enjoyor.modules.monitor.service.FlowNightService;
import com.enjoyor.modules.monitor.service.MonitorDataService;
import com.enjoyor.modules.monitor.service.PipeDataHisService;
import com.enjoyor.modules.monitor.service.PipeDataService;
import com.enjoyor.modules.sys.controller.AbstractController;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.sys.entity.SysVideoEntity;
import com.enjoyor.modules.sys.service.SysSiteService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.event.ListSelectionEvent;

@RestController
@RequestMapping("/monitor/flowNight")
public class FlowNightController extends AbstractController {
	
	@Autowired
	private FlowNightService flowNightService;
	
	@RequestMapping("/querySGSiteList")
	@ResponseBody
	public Map<String, Object> querySGSiteList(){		
		List<SGSiteDTO> siteList = flowNightService.querySGSiteList();
		List<String> areaNameList = new ArrayList<String>();
		List<String> areaIdList = new ArrayList<String>();
		//片区列表
		List<SGSiteDTO> areaList = new ArrayList<SGSiteDTO>();
		for(SGSiteDTO site : siteList){
			if(!areaNameList.contains(site.getAreaName())){
				areaNameList.add(site.getAreaName());
				String areaName = site.getAreaName();
				if(!areaIdList.contains(site.getAreaId())){
					areaIdList.add(site.getAreaId());
					String areaId = site.getAreaId();
					
					SGSiteDTO area = new SGSiteDTO();
					area.setAreaName(areaName);
					area.setAreaId(areaId);
					area.setSonList(new ArrayList<SGSiteDTO>());
					areaList.add(area);
				}
			}
		}
		//向片区中添加点位
		for(SGSiteDTO area : areaList){
			for(SGSiteDTO site : siteList){
				if(site.getAreaName().equals(area.getAreaName())){
					area.getSonList().add(site);
				}
			}
		}
		Map<String, Object> map =new HashMap<String, Object>();	
		map.put("list", areaList);
		map.put("code", 0);
		return map;
	}
	
	@RequestMapping("/querySGSiteListByDma")
	@ResponseBody
	public Map<String, Object> querySGSiteListByDma(){		
		List<SGSiteDTO> siteList = flowNightService.querySGSiteListByDma();
		List<String> areaNameList = new ArrayList<String>();
		List<String> areaIdList = new ArrayList<String>();
		//片区列表
		List<SGSiteDTO> areaList = new ArrayList<SGSiteDTO>();
		for(SGSiteDTO site : siteList){
			if(!areaNameList.contains(site.getAreaName())){
				areaNameList.add(site.getAreaName());
				String areaName = site.getAreaName();
				if(!areaIdList.contains(site.getAreaId())){
					areaIdList.add(site.getAreaId());
					String areaId = site.getAreaId();
					
					SGSiteDTO area = new SGSiteDTO();
					area.setAreaName(areaName);
					area.setAreaId(areaId);
					area.setOrderNum(site.getOrderNum());
					area.setSonList(new ArrayList<SGSiteDTO>());
					areaList.add(area);
				}
			}
		}
		//向片区中添加点位
		for(SGSiteDTO area : areaList){
			for(SGSiteDTO site : siteList){
				if(site.getAreaName().equals(area.getAreaName())){
					area.getSonList().add(site);
				}
			}
		}
		Map<String, Object> map =new HashMap<String, Object>();	
		map.put("list", areaList);
		map.put("code", 0);
		return map;
	}

	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(@RequestBody Map<String, Object> params){		
		String siteId = (String)params.get("siteId");
		String beginTime = (String)params.get("beginTime");
		String endTime = (String)params.get("endTime");
		Map<String, Object> map =new HashMap<String, Object>();	
		if(null == siteId || "".equals(siteId)){
			map.put("msg", "缺少点位ID参数");
			map.put("code", 0);
			return map;
		}
		if(null == beginTime || "".equals(beginTime)){
			map.put("msg", "缺少起始时间参数");
			map.put("code", 0);
			return map;
		}
		if(null == endTime || "".equals(endTime)){
			map.put("msg", "缺少结束时间参数");
			map.put("code", 0);
			return map;
		}
		try {
			List<FlowNightEntity> list = flowNightService.query(siteId, beginTime, endTime);
			map.put("list", list);		
			map.put("code", 1);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 0);
			return map;
		} 
	}
	
	@RequestMapping("/download")
	@ResponseBody
	public void download(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<>();
		String siteId = request.getParameter("siteId");
		String stime = request.getParameter("beginTime");
		String etime = request.getParameter("endTime");
		params.put("siteId", siteId);
		params.put("beginTime", stime);
		params.put("endTime", etime);
		Map<String, Object> map = this.query(params);
		
		ExcelBook.excelFlowNight(map,request, response);
	}
}

