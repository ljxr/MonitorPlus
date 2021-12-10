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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyor.modules.monitor.entity.FlowReportFormEntity;
import com.enjoyor.modules.monitor.entity.PipeData;
import com.enjoyor.modules.monitor.entity.PipeDataDTO;
import com.enjoyor.modules.monitor.entity.PipeDataHis;
import com.enjoyor.modules.monitor.service.PipeDataHisService;
import com.enjoyor.modules.monitor.service.PipeDataService;
import com.enjoyor.modules.sys.controller.AbstractController;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.sys.service.SysSiteService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/pipe/data")
public class PipeDataController extends AbstractController {

	@Autowired
	private SysSiteService sysSiteService;

	@Autowired
	private PipeDataService pipeDataService;

	@Autowired
	private PipeDataHisService pipeDataHisService;

	@RequestMapping("/findAll")
	@ResponseBody
	public List<PipeData> findAll() {
		List<PipeData> list = new ArrayList();
		try {
			list = pipeDataService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 供水管网地图页面 右侧实时数据列表
	// 根据area_id查询, 片区下 泵站,流量 压力 水质监测点 的实时数据（areaId为“”则显示全部片区的数据）
	@RequiresPermissions("mon:pipe:list")
	@RequestMapping("/findByArea")
	@ResponseBody
	public List<PipeDataDTO> findByArea(@RequestBody String areaId) {
		List<PipeDataDTO> list = new ArrayList();
		try {
			list = pipeDataService.findByArea(areaId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@RequiresPermissions("mon:pipe:list")
	@RequestMapping("/findPsByArea")
	@ResponseBody
	public List<PipeDataDTO> findPsByArea(@RequestBody String areaId) {
		List<PipeDataDTO> list = new ArrayList();
		try {
			list = pipeDataService.findPsByArea(areaId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@RequiresPermissions("mon:pipe:list")
	@RequestMapping("/findHisData")
	@ResponseBody
	public JSONArray queryHisData(@RequestBody Map<String, Object> params) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String stime = (String) params.get("stime");
		String etime = (String) params.get("etime");
		if (null == stime || "".equals(stime)) {
			stime = sdf2.format(Calendar.getInstance().getTime());
		}
		if (null == etime || "".equals(etime)) {
			etime = sdf1.format(Calendar.getInstance().getTime());
		}
		Map<String, Object> params2 = new HashMap<String, Object>();

		String siteId1 = (String) params.get("siteId1");
		String siteId2 = (String) params.get("siteId2");

		if (!Objects.equals(siteId1, null) && !Objects.equals(siteId1, "")) {
			String site_Id1 = siteId1.split(",")[0];
			String type1 = siteId1.split(",")[1];
			List<PipeDataHis> list1 = pipeDataHisService.queryHisData(site_Id1, type1, stime, etime);
			params2.put(site_Id1 + type1, list1);
		}
		if (!Objects.equals(siteId2, null) && !Objects.equals(siteId2, "")) {
			String site_Id2 = siteId2.split(",")[0];
			String type2 = siteId2.split(",")[1];
			List<PipeDataHis> list2 = pipeDataHisService.queryHisData(site_Id2, type2, stime, etime);
			params2.put(site_Id2 + type2, list2);
		}
		JSONArray str = JSONArray.fromObject(params2);
		return str;
	}

	@RequestMapping("/pageHisData")
	@ResponseBody
	public JSONObject pageHisData(@RequestParam Map<String, Object> params) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String siteId = (String) params.get("siteId");
		int pageSize = Integer.parseInt((String) params.get("limit"));
		int pageNum = Integer.parseInt((String) params.get("page"));
		if (null == siteId || 0 == pageSize || 0 == pageNum) {
			return null;
		}

		String site_Id = siteId.split(",")[0];
		String type = siteId.split(",")[1];

		String stime = (String) params.get("stime");
		String etime = (String) params.get("etime");
		if (null == stime || "".equals(stime)) {
			stime = sdf2.format(Calendar.getInstance().getTime());
		}
		if (null == etime || "".equals(etime)) {
			etime = sdf1.format(Calendar.getInstance().getTime());
		}
		List<PipeDataHis> list = pipeDataHisService.queryHisData(site_Id, type, stime, etime);
		Map<String, Object> params2 = new HashMap<String, Object>();

		int totalPage = 0;
		if (list.size() % pageSize == 0) {
			totalPage = list.size() / pageSize;
		} else {
			totalPage = list.size() / pageSize + 1;
		}
		List<PipeDataHis> pageList = new ArrayList<>();
		if (list != null && list.size() > 0) {
			int fromIndex = (pageNum - 1) * pageSize;
			int toIndex = pageNum * pageSize;
			if (toIndex > list.size()) {
				toIndex = list.size();
			}
			pageList = list.subList(fromIndex, toIndex);
		}

		params2.put("code", 0);
		params2.put("data", pageList);
		params2.put("count", list.size());
		// params2.put("totalPage", totalPage);
		params2.put("message", "");
		JSONObject str = JSONObject.fromObject(params2);
		return str;
	}

	/**
	 * 供水泵站主界面
	 * 查询siteId的出水口流量监测点（S04）的实时数据(PIPE_DATA)
	 */
	@RequestMapping("/queryPipePump")
	public Map<String, Object> queryPipePump(@RequestBody String siteId) {
		// String siteId=(String)params.get("siteId");
		Map<String, Object> map = new HashMap<String, Object>();

		List<SysSiteEntity> list1 = sysSiteService.querySiteByS04(siteId);
		if (null != list1 && 0 != list1.size()) {
			for (SysSiteEntity sysSiteEntity : list1) {
				PipeData pd = pipeDataService.queryPipeIns(sysSiteEntity.getSiteId());
				if(null != pd){
					map.put("FLS", pd.getSs());
					map.put("FLC", pd.getJlj());
					map.put("YL", pd.getYl());
				}			
			}
		}

		return map;
	}

	/**
	 * 查询泵站流量计子点位
	 */
	@ResponseBody
	@RequestMapping("/queryPipePumpSite")
	public JSONArray queryPipePumpSite(@RequestBody String siteId) {

		List<SysSiteEntity> list1 = sysSiteService.querySiteByS04(siteId);

		JSONArray str = JSONArray.fromObject(list1);
		return str;
	}

	/**
	 * excel 导出夜间小流量
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/exportExcel", method = { RequestMethod.GET })
	public Map<String, Object> exportExcel(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String areaId = request.getParameter("areaId");
		String areaName = request.getParameter("areaName");
		String time = request.getParameter("time");
		pipeDataHisService.exportExcel(areaId, areaName, time, request, response);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		return map;
	}

	/**
	 * 查询夜间小流量
	 */
	@ResponseBody
	@RequestMapping("/queryFlowReportForm")
	public Map<String, Object> queryFlowReportForm(@RequestParam Map<String, Object> params) {

		String areaId = (String) params.get("areaId");
		String areaName = (String) params.get("areaName");
		String time = (String) params.get("time");

		List<FlowReportFormEntity> list = pipeDataHisService.queryFlowReportForm(areaId, areaName, time);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		map.put("code", 0);
		return map;
	}
}
