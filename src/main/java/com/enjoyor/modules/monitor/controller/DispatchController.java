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
import com.enjoyor.common.utils.RegionUtils;
import com.enjoyor.modules.monitor.entity.DispatchLog;
import com.enjoyor.modules.monitor.entity.DispatchLogQueryDTO;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.PipeData;
import com.enjoyor.modules.monitor.entity.PipeDataDTO;
import com.enjoyor.modules.monitor.entity.PipeDataHis;
import com.enjoyor.modules.monitor.entity.SeatEntity;
import com.enjoyor.modules.monitor.service.DispatchLogService;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.event.ListSelectionEvent;

@RestController
@RequestMapping("/dispatch")
public class DispatchController extends AbstractController {
	
	@Autowired
	private DispatchLogService dispatchLogService;

	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryDispatch(@RequestParam Map<String, Object> params){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String siteId = (String)params.get("siteId");
		String beginTime = (String)params.get("stime");
		String endTime = (String)params.get("etime");
		Date now = new Date(); 
		String nowTime = dateFormat.format(now);
		if("".equals(beginTime) || null == beginTime){
			beginTime = nowTime.substring(0, dateFormat.format(now).length()-8) + "00:00:00";
		}
		if("".equals(endTime) || null == endTime){			
			endTime = nowTime.substring(0, dateFormat.format(now).length()-8) + "23:59:59";
		}
		int pageNo=Integer.parseInt((String)params.get("page"));	
		int pageSize=Integer.parseInt((String)params.get("limit"));			
		DispatchLogQueryDTO dto = new DispatchLogQueryDTO();
		try {
			dto.setSiteId(siteId);
			dto.setBeginTime(format.parse(beginTime));
			dto.setEndTime(format.parse(endTime));
			dto.setContentType((String)params.get("contentType"));
			if(null == (String)params.get("contentType") || "".equals((String)params.get("contentType"))){
				dto.setContentType("全部");
			}
			dto.setOffset(1+pageSize*(pageNo-1));
			dto.setLimit(pageSize*pageNo);
			List<DispatchLog> list = dispatchLogService.query(dto);
			Integer count = dispatchLogService.countAll(dto);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("data", list);
			map.put("count", count);
			map.put("code", 0);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("code", 1);
			return map;
		} 
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public int addDispatch(@RequestBody DispatchLog dispatchLog){
			try {
				dispatchLogService.add(dispatchLog);
			} catch (Exception e) {
				e.printStackTrace();
				return 1;
			} 
			return 0;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public int updateDispatch(@RequestBody DispatchLog dispatchLog){
			try {
				dispatchLogService.update(dispatchLog);
			} catch (Exception e) {
				e.printStackTrace();
				return 1;
			}  
			return 0;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public int deleteDispatch(@RequestBody List<Integer> idList){
			try {
				dispatchLogService.delete(idList);
			} catch (Exception e) {
				e.printStackTrace();
				return 1;
			} 
			return 0;
	}
	
	
}

