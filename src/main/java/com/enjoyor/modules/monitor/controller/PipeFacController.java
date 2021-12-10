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
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.enjoyor.common.utils.ExcelBook;
import com.enjoyor.common.utils.RegionUtils;
import com.enjoyor.modules.dma.entity.DmaFlowNightWarnEntity;
import com.enjoyor.modules.monitor.entity.AlarmTotalEntity;
import com.enjoyor.modules.monitor.entity.AnalysisEntity;
import com.enjoyor.modules.monitor.entity.AnalysisEntityResp;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.SeatEntity;
import com.enjoyor.modules.monitor.service.MonitorDataService;
import com.enjoyor.modules.monitor.service.MonitorService;
import com.enjoyor.modules.sys.controller.AbstractController;
import com.enjoyor.modules.sys.entity.SiteTreeEntity;
import com.enjoyor.modules.sys.entity.SysRegionEntity;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.sys.entity.SysVideoEntity;
import com.enjoyor.modules.sys.service.SysSiteService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/fac/data")
public class PipeFacController extends AbstractController {

	@Autowired
	private MonitorDataService monitorDataService;

	@Autowired
	private SysSiteService sysSiteService;

	@Autowired
	private MonitorService monitorService;

	/*
	 * @RequestMapping("/list")
	 * 
	 * @ResponseBody public void list(HttpServletRequest request,@RequestBody
	 * Map<String, Object> params){ HttpSession session=request.getSession();
	 * String siteId=(String)params.get("siteId");
	 * if(null!=siteId&&!"".equals(siteId)){ synchronized (this){
	 * if(null==session.getAttribute("result")){ session.setAttribute("result",
	 * true); Boolean result = (boolean)session.getAttribute("result");
	 * while(result) { try { boolean
	 * flag=monitorDataService.sendMessage(siteId,session); if (!flag) { result
	 * = false; break ; } Thread.sleep(3000); //设置暂停的时间 3 秒 } catch
	 * (InterruptedException e) { e.printStackTrace(); } } } } } }
	 */
	
	
	
	
	/**
	 * 泵站实时数据
	 * 
	 * @param request
	 * @param params
	 */
	@RequiresPermissions("mon:map:list")
	@RequestMapping("/list")
	@ResponseBody
	public void list2(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		List<String> re = RegionUtils.region();
		List<String> site = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		HttpSession session = request.getSession();
		String siteId = (String) params.get("siteId");
		String siteId2 = "";
		//if ("bigdata".equals(siteId)) {
			/*List<MonitorTotalEntity> list = monitorService.queryMinTimeMonitor(time);
			for (int i = 0; i < list.size(); i++) {
				MonitorTotalEntity monitorTotal = monitorService.queryTodayMonitor(list.get(i).getTime(),
						list.get(i).getSiteId());
				if (i == list.size() - 1) {
					siteId2 = siteId2 + monitorTotal.getSiteId() + "=" + monitorTotal.getTotalFlow() + ";bigdata";
				} else {
					siteId2 = siteId2 + monitorTotal.getSiteId() + "=" + monitorTotal.getTotalFlow() + ";";
				}
			}*/
		//} else {
			String[] s = siteId.split(";");
			for (int i = 0; i < s.length; i++) {
				if (null != s[i] && !"".equals(s[i]) && !"bigdata".equals(s[i])) {
					site.add(s[i].split(",")[0]);
				}else{
					List<MonitorTotalEntity> list = monitorService.queryMinTimeMonitor(time);
					for (int j = 0; j < list.size(); j++) {
						MonitorTotalEntity monitorTotal = monitorService.queryTodayMonitor(list.get(j).getTime(),
								list.get(j).getSiteId());
	
						if (j == list.size() - 1) {
							siteId2 = siteId2 + monitorTotal.getSiteId() + "=" + monitorTotal.getTotalFlow() + ";bigdata;";
						} else {
							siteId2 = siteId2 + monitorTotal.getSiteId() + "=" + monitorTotal.getTotalFlow() + ";";
						}
					}
				}
			}
			if (0 != site.size()) {
				for (Iterator<String> it = site.iterator(); it.hasNext();) {
					String string = it.next();
					int result = sysSiteService.queryInsData(string, re);
					if (0 == result) {
						it.remove();
					}
				}
			}
			String siteString = "";
			for (String string : site) {
				siteString = siteString + string + ";";
			}

			for (int i = 0; i < s.length; i++) {
				if (null != s[i] && !"".equals(s[i])) {
					if (-1 != siteString.indexOf(s[i].split(",")[0])) {
						siteId2 = siteId2 + s[i] + ";";
					}
				}
			}
/*			MonitorTotalEntity m = monitorService.queryMinTimeMonitor2(time, siteId3);
			if(null != m){
				MonitorTotalEntity monitorTotal = monitorService.queryTodayMonitor(m.getTime(), siteId3);
				if(null != monitorTotal){
					
				}
			}*/
		//}
		if (null != siteId && !"".equals(siteId)) {
			if (null == session.getAttribute("websocket")) {
				session.setAttribute("websocket", true);
				boolean flag = monitorDataService.sendMessage(siteId2, session);
			} else if (!(boolean) session.getAttribute("websocket")) {
				session.setAttribute("websocket", true);
				boolean flag = monitorDataService.sendMessage(siteId2, session);
			}
		}
	}
	
	
	
	/**
	 * 泵站实时数据
	 * 
	 * @param request
	 * @param params
	 *//*
	@RequiresPermissions("mon:map:list")
	@RequestMapping("/list")
	@ResponseBody
	public void list2(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		List<String> re = RegionUtils.region();
		List<String> siteIdList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		HttpSession session = request.getSession();
		String siteParam = (String) params.get("siteId");
		String resultString = "";
		//if ("bigdata".equals(siteId)) {
			List<MonitorTotalEntity> list = monitorService.queryMinTimeMonitor(time);
			for (int i = 0; i < list.size(); i++) {
				MonitorTotalEntity monitorTotal = monitorService.queryTodayMonitor(list.get(i).getTime(),
						list.get(i).getSiteId());
				if (i == list.size() - 1) {
					siteId2 = siteId2 + monitorTotal.getSiteId() + "=" + monitorTotal.getTotalFlow() + ";bigdata";
				} else {
					siteId2 = siteId2 + monitorTotal.getSiteId() + "=" + monitorTotal.getTotalFlow() + ";";
				}
			}
		//} else {
			String[] siteArray = siteParam.split(";");
			for (int i = 0; i < siteArray.length; i++) {
				if (null != siteArray[i] && !"".equals(siteArray[i]) && !"bigdata".equals(siteArray[i])) {
					siteIdList.add(siteArray[i].split(",")[0]);
				}else{
					List<MonitorTotalEntity> list = monitorService.queryMinTimeMonitor(time);
					for (int j = 0; j < list.size(); j++) {
						MonitorTotalEntity monitorTotal = monitorService.queryTodayMonitor(list.get(j).getTime(),
								list.get(j).getSiteId());
	
						if (j == list.size() - 1) {
							resultString = resultString + monitorTotal.getSiteId() + "=" + monitorTotal.getTotalFlow() + ";bigdata;";
						} else {
							resultString = resultString + monitorTotal.getSiteId() + "=" + monitorTotal.getTotalFlow() + ";";
						}
					}
				}
			}
			if (0 != siteIdList.size()) {
				// 查询点位是否存在  不存在则去除
				for (Iterator<String> it = siteIdList.iterator(); it.hasNext();) {
					String string = it.next();
					int result = sysSiteService.queryInsData(string, re);
					if (0 == result) {
						it.remove();
					}
				}
			}
			// 存在的点位的 点位ID 字符串
			String siteIdString = "";
			for (String string : siteIdList) {
				siteIdString = siteIdString + string + ";";
			}

			for (int i = 0; i < siteArray.length; i++) {
				if (null != siteArray[i] && !"".equals(siteArray[i])) {
					if (-1 != siteIdString.indexOf(siteArray[i].split(",")[0])) {
						resultString = resultString + siteArray[i] + ";";
					}
				}
			}
			MonitorTotalEntity m = monitorService.queryMinTimeMonitor2(time, siteId3);
			if(null != m){
				MonitorTotalEntity monitorTotal = monitorService.queryTodayMonitor(m.getTime(), siteId3);
				if(null != monitorTotal){
					
				}
			}
		//}
			//System.err.println("测试："+siteId2);
		if (null != siteParam && !"".equals(siteParam)) {
			if (null == session.getAttribute("websocket")) {
				session.setAttribute("websocket", true);
				boolean flag = monitorDataService.sendMessage(resultString, session);
			} else if (!(boolean) session.getAttribute("websocket")) {
				session.setAttribute("websocket", true);
				boolean flag = monitorDataService.sendMessage(resultString, session);
			}
		}
	}*/

	@RequestMapping("/listp")
	@ResponseBody
	public void listp(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		List<String> re = RegionUtils.region();
		List<String> site = new ArrayList<String>();
		HttpSession session = request.getSession();
		String siteId = (String) params.get("siteId");

		// 供水泵站首页展示所有泵站数据
		if ("".equals(siteId)) {
			// 所有泵站
			List<SysSiteEntity> bengList = sysSiteService.queryS03Site();
			// 所有泵站的子点位
			List<SysSiteEntity> bengSonList = new ArrayList<SysSiteEntity>();

			for (SysSiteEntity sysSiteEntity : bengList) {
				// 获取泵站的子点位
				List<SysSiteEntity> siteType = sysSiteService.queryParentSite(sysSiteEntity.getSiteId(), re);
				bengSonList.addAll(siteType);
			}
			// 发送给websocket的参数
			String siteIdString = "";
			for (SysSiteEntity sysSiteEntity : bengSonList) {
				if ("S14".equals(sysSiteEntity.getType())) {
					siteIdString += sysSiteEntity.getSiteId() + ",Fault;" + sysSiteEntity.getSiteId() + ",STOP;"
							+ sysSiteEntity.getSiteId() + ",RUN;" + sysSiteEntity.getSiteId() + ",Mode;";
				}
				if ("CK".equals(sysSiteEntity.getSiteId().substring(sysSiteEntity.getSiteId().length() - 2))) {
					siteIdString += sysSiteEntity.getSiteId() + ",FLC;" + sysSiteEntity.getSiteId() + ",FLS;"
							+ sysSiteEntity.getSiteId() + ",PTOUT;" + sysSiteEntity.getSiteId() + ",TUR;"
							+ sysSiteEntity.getSiteId() + ",PH;" + sysSiteEntity.getSiteId() + ",HOCL;";
				}
			}
			session.setAttribute("websocket", true);
			boolean flag = monitorDataService.sendMessage(siteIdString, session);
			return;
		}

		String[] s = siteId.split(";");
		for (int i = 0; i < s.length; i++) {
			if (null != s[i] && !"".equals(s[i])) {
				site.add(s[i].split(",")[0]);
			}
		}
		// 查询点位ID是否存在，不存在则剔除
		if (0 != site.size()) {
			for (Iterator<String> it = site.iterator(); it.hasNext();) {
				String string = it.next();
				int result = sysSiteService.queryInsData(string, re);
				if (0 == result) {
					it.remove();
				}
			}
		}
		String siteString = "";
		for (String string : site) {
			siteString = siteString + string + ";";
		}
		// siteString = "siteId1;siteId2;siteId3;"
		String siteId2 = "";
		for (int i = 0; i < s.length; i++) {
			if (null != s[i] && !"".equals(s[i])) {
				if (-1 != siteString.indexOf(s[i].split(",")[0])) {
					siteId2 = siteId2 + s[i] + ";";
				}
			}
		}

		if (null != siteId && !"".equals(siteId)) {
			if (null == session.getAttribute("websocket")) {
				session.setAttribute("websocket", true);
				boolean flag = monitorDataService.sendMessage(siteId2, session);
			} else if (!(boolean) session.getAttribute("websocket")) {
				session.setAttribute("websocket", true);
				boolean flag = monitorDataService.sendMessage(siteId2, session);
			}
		}
	}

	@RequestMapping("/closeSession")
	@ResponseBody
	public boolean closeSession(HttpServletRequest request) {
		HttpSession session = request.getSession();

		session.setAttribute("websocket", false);
		boolean flag = monitorDataService.sendMessage(null, session);
		return flag;
	}

	@RequestMapping("/querySession")
	@ResponseBody
	public String querySession(HttpServletRequest request) {
		HttpSession session = request.getSession();

		return session.getId();
	}

	@RequestMapping("/file")
	@ResponseBody
	public String file(HttpServletRequest request) {
		File file = new File("E:\\apache-tomcat-8.5.31\\webapps1\\MonitorPlus\\statics\\img\\gyt_img\\gyt_small");
		File[] files = file.listFiles();
		String fileName = "";
		for (int i = 0; i < files.length; i++) {
			if (!files[i].isDirectory()) {
				fileName = fileName + files[i].getName() + ",";
			}
		}

		return fileName;
	}

	@RequestMapping("/file2")
	@ResponseBody
	public String file2(HttpServletRequest request) {
		File file = new File("E:\\apache-tomcat-8.5.31\\webapps1\\MonitorPlus\\statics\\img\\gyt_img\\gray");
		File[] files = file.listFiles();
		String fileName = "";
		for (int i = 0; i < files.length; i++) {
			if (!files[i].isDirectory()) {
				fileName = fileName + files[i].getName() + ",";
			}
		}

		return fileName;
	}

	/**
	 * 保存工艺图设置
	 */
	@RequestMapping("/saveGyt")
	@ResponseBody
	public int saveGyt(HttpServletRequest request, @RequestBody Map<String, List<GytEntity>> params) {
		try {
			List<GytEntity> ge1 = params.get("gytbgn");
			List<GytEntity> ge2 = params.get("title");
			List<GytEntity> ge3 = params.get("information");
			List<GytEntity> ge4 = params.get("point");
			// 保存工艺图底图信息
			for (GytEntity gytEntity : ge1) {
				if (null != gytEntity && !"".equals(gytEntity)) {
					monitorDataService.saveGytbgn(gytEntity);
				}
			}
			// 保存标题信息
			for (GytEntity gytEntity : ge2) {
				if (null != gytEntity && !"".equals(gytEntity)) {
					monitorDataService.saveTitleInfo(gytEntity);
				}
			}
			// 保存数据信息
			for (GytEntity gytEntity : ge3) {
				if (null != gytEntity && !"".equals(gytEntity)) {
					GytEntity gyt1 = gytEntity.getDescription();
					GytEntity gyt2 = gytEntity.getData();
					gytEntity.setText(gyt1.getText());
					gytEntity.setDcolor(gyt1.getDcolor());
					gytEntity.setDsize(gyt1.getDsize());
					gytEntity.setDweight(gyt1.getDweight());
					gytEntity.setSiteId(gyt2.getSiteId());
					gytEntity.setTcolor(gyt2.getTcolor());
					gytEntity.setTsize(gyt2.getTsize());
					gytEntity.setTweight(gyt2.getTweight());
					gytEntity.setType(gyt2.getType());
					monitorDataService.saveInformation(gytEntity);
				}
			}
			// 保存泵信息
			for (GytEntity gytEntity : ge4) {
				if (null != gytEntity && !"".equals(gytEntity)) {
					monitorDataService.savePointInfo(gytEntity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	/**
	 * 更新工艺图设置
	 */
	@RequestMapping("/updateGyt")
	@ResponseBody
	public int updateGyt(HttpServletRequest request, @RequestBody Map<String, List<GytEntity>> params) {
		try {
			List<GytEntity> ge1 = params.get("gytbgn");
			List<GytEntity> ge2 = params.get("title");
			List<GytEntity> ge3 = params.get("information");
			List<GytEntity> ge4 = params.get("point");
			// 更新工艺图底图信息
			for (GytEntity gytEntity : ge1) {
				if (null != gytEntity && !"".equals(gytEntity)) {
					monitorDataService.updateGytbgn(gytEntity);
				}
			}
			// 更新标题信息
			for (GytEntity gytEntity : ge2) {
				if (null != gytEntity && !"".equals(gytEntity)) {
					monitorDataService.updateTitleInfo(gytEntity);
				}
			}
			// 更新数据信息
			for (GytEntity gytEntity : ge3) {
				if (null != gytEntity && !"".equals(gytEntity)) {
					GytEntity gyt1 = gytEntity.getDescription();
					GytEntity gyt2 = gytEntity.getData();
					gytEntity.setText(gyt1.getText());
					gytEntity.setDcolor(gyt1.getDcolor());
					gytEntity.setDsize(gyt1.getDsize());
					gytEntity.setDweight(gyt1.getDweight());
					gytEntity.setSiteId(gyt2.getSiteId());
					gytEntity.setTcolor(gyt2.getTcolor());
					gytEntity.setTsize(gyt2.getTsize());
					gytEntity.setTweight(gyt2.getTweight());
					gytEntity.setType(gyt2.getType());
					monitorDataService.updateInformation(gytEntity);
				}
			}
			// 更新泵信息
			for (GytEntity gytEntity : ge4) {
				if (null != gytEntity && !"".equals(gytEntity)) {
					monitorDataService.updatePointInfo(gytEntity);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	/**
	 * 删除工艺图底图就删除工艺图下的所有设置
	 */
	@RequestMapping("/deleteGyt")
	@ResponseBody
	public int deleteGyt(HttpServletRequest request, @RequestBody String siteId) {
		try {
			monitorDataService.deleteGytbgn(siteId);
			monitorDataService.deleteInformation(siteId, null);
			monitorDataService.deletePointInfo(siteId, null);
			monitorDataService.deleteTitleInfo(siteId, null);
			monitorDataService.deleteSeat(null, null, siteId);
		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	/**
	 * 删除数据信息
	 */
	@RequestMapping("/deleteInformation")
	@ResponseBody
	public int deleteInformation(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		try {
			String siteId = (String) params.get("siteId");
			String divId = (String) params.get("divId");
			monitorDataService.deleteInformation(siteId, divId);
		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	/**
	 * 删除标题信息
	 */
	@RequestMapping("/deleteTitle")
	@ResponseBody
	public int deleteTitle(@RequestBody Map<String, Object> params) {
		try {
			String siteId = (String) params.get("siteId");
			String divId = (String) params.get("divId");
			monitorDataService.deleteTitleInfo(siteId, divId);
		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	/**
	 * 删除泵信息
	 */
	@RequestMapping("/deletePoint")
	@ResponseBody
	public int deletePoint(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		try {
			String siteId = (String) params.get("siteId");
			String divId = (String) params.get("divId");
			monitorDataService.deletePointInfo(siteId, divId);
		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	/**
	 * 获取所有工艺图底图名称
	 */
	@RequestMapping("/queryGytAll")
	@ResponseBody
	public JSONArray queryGytAll(HttpServletRequest request) {

		List<GytEntity> gyt = monitorDataService.queryGytAll();

		JSONArray str = JSONArray.fromObject(gyt);

		return str;
	}

	/**
	 * 获取某个点位下工艺图信息
	 */
	@RequiresPermissions("mon:zt:info")
	@RequestMapping("/queryGyt")
	@ResponseBody
	public JSONArray queryGyt(HttpServletRequest request, @RequestBody String siteId) {

		Map<String, Object> params = new HashMap<String, Object>();
		List<GytEntity> gyt = monitorDataService.queryGyt(siteId);
		List<GytEntity> title = monitorDataService.queryTitle(siteId);
		List<GytEntity> information = monitorDataService.queryInformation(siteId);
		List<SeatEntity> seat = monitorDataService.querySeat(null, null, siteId);
		for (GytEntity gytEntity : information) {
			GytEntity g = new GytEntity();
			GytEntity g2 = new GytEntity();
			g.setText(gytEntity.getText());
			g.setDcolor(gytEntity.getDcolor());
			g.setDsize(gytEntity.getDsize());
			g.setDweight(gytEntity.getDweight());
			gytEntity.setDescription(g);

			g2.setSiteId(gytEntity.getSiteId());
			g2.setType(gytEntity.getType());
			g2.setTcolor(gytEntity.getTcolor());
			g2.setTsize(gytEntity.getTsize());
			g2.setTweight(gytEntity.getTweight());
			g2.setParentSiteId(gytEntity.getParentSiteId());
			gytEntity.setData(g2);
		}

		List<GytEntity> point = monitorDataService.queryPoint(siteId);

		params.put("gytbgn", gyt);
		params.put("title", title);
		params.put("information", information);
		params.put("point", point);
		params.put("seat", seat);

		JSONArray str = JSONArray.fromObject(params);
		// JSONObject str = JSONObject.fromObject(params);
		// String str=JSON.toJSONString(params);
		return str;
	}

	/**
	 * 保存工艺图基础信息
	 */
	@RequiresPermissions("mon:zt:save")
	@RequestMapping("/saveGytbase")
	@ResponseBody
	public void saveGytbase(@RequestBody GytBaseInfoEntity gb) {
		if (!"S".equals(gb.getSiteParentId()) && !"W".equals(gb.getSiteParentId())
				&& !"P".equals(gb.getSiteParentId())) {
			SysSiteEntity ss = sysSiteService.querySiteName(gb.getSiteParentId());
			gb.setSiteParentName(ss.getName());
			gb.setSiteType(ss.getTypeName());
		}

		if (null != gb.getSiteId() && !"".equals(gb.getSiteId())) {
			SysSiteEntity ss2 = sysSiteService.querySiteName(gb.getSiteId());
			gb.setSiteName(ss2.getName());
		} else {
			gb.setSiteName("");
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		gb.setCreateTime(df.format(new Date()));// new Date()为获取当前系统时间

		monitorDataService.saveGytbase(gb);
	}

	/**
	 * 修改工艺图基础信息
	 */
	@RequiresPermissions("mon:zt:update")
	@RequestMapping("/updateGytbase")
	@ResponseBody
	public void updateGytbase(@RequestBody GytBaseInfoEntity gb) {
		SysSiteEntity ss = sysSiteService.querySiteName(gb.getSiteParentId());
		gb.setSiteParentName(ss.getName());
		gb.setSiteType(ss.getTypeName());
		if (null != gb.getSiteId() && !"".equals(gb.getSiteId())) {
			SysSiteEntity ss2 = sysSiteService.querySiteName(gb.getSiteId());
			gb.setSiteName(ss2.getName());
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		gb.setUpdateTime(df.format(new Date()));// new Date()为获取当前系统时间

		monitorDataService.updateGytbase(gb);
	}

	/**
	 * 删除工艺图基础信息
	 */
	@RequiresPermissions("mon:zt:delete")
	@RequestMapping("/deleteGytbase")
	@ResponseBody
	public void deleteGytbase(@RequestBody int id) {
		GytBaseInfoEntity gtf = monitorDataService.queryGytbaseId(id);
		monitorDataService.deleteGytbase(id);
		if (null != gtf.getSiteId()) {
			String siteId = gtf.getSiteId();
			monitorDataService.deleteGytbgn(siteId);
			monitorDataService.deleteInformation(siteId, null);
			monitorDataService.deletePointInfo(siteId, null);
			monitorDataService.deleteTitleInfo(siteId, null);
			monitorDataService.deleteSeat(null, null, siteId);
		} else {
			String siteId = gtf.getSiteParentId();
			monitorDataService.deleteGytbgn(siteId);
			monitorDataService.deleteInformation(siteId, null);
			monitorDataService.deletePointInfo(siteId, null);
			monitorDataService.deleteTitleInfo(siteId, null);
			monitorDataService.deleteSeat(null, null, siteId);
		}
	}

	/**
	 * 查询工艺图基础信息
	 */
	@RequiresPermissions("mon:zt:list")
	@RequestMapping("/queryGytbase")
	public Map<String, Object> queryGytbase(@RequestParam Map<String, Object> params) {
		GytBaseInfoEntity gb = new GytBaseInfoEntity();
		int currentpage = Integer.parseInt((String) params.get("page"));
		int limit = Integer.parseInt((String) params.get("limit"));
		List<String> re = RegionUtils.region();
		gb.setList(re);
		gb.setGytName((String) params.get("gytName"));
		gb.setSiteParentId((String) params.get("siteParentId"));
		gb.setOffset((currentpage * limit) - (limit - 1));
		gb.setLimit(currentpage * limit);

		List<GytBaseInfoEntity> list = monitorDataService.queryGytbase(gb);
		int totalnum = monitorDataService.queryGytbaseCount(gb);
		int totalpage = 0;
		int ys = totalnum % limit;
		if (ys == 0) {
			totalpage = totalnum / limit;
		} else {
			totalpage = (totalnum + limit) / limit;
		}

		if (list.size() != 0) {
			for (GytBaseInfoEntity gytBaseInfoEntity : list) {
				gytBaseInfoEntity.setTotalNum(totalnum);
				gytBaseInfoEntity.setTotalPage(totalpage);
			}

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("count", totalnum);
		map.put("message", "");
		map.put("data", list);
		// JSONArray str =JSONArray.fromObject(map);

		return map;
	}

	/**
	 * 查询工艺图基础信息
	 */
	@ResponseBody
	@RequestMapping("/queryGytgl")
	public JSONArray queryGytgl(@RequestBody Map<String, Object> params) {
		String parentId = (String) params.get("siteParentId");
		List<GytBaseInfoEntity> list = monitorDataService.queryGytgl(parentId);

		JSONArray str = JSONArray.fromObject(list);
		return str;
	}

	/**
	 * 保存工艺图标题设置
	 */
	@RequestMapping("/saveGytTitle")
	@ResponseBody
	public int saveGytTitle(@RequestBody GytEntity ge) {
		try {
			// 保存标题信息
			monitorDataService.saveTitleInfo(ge);
		} catch (Exception e) {
			return 500;
		}
		return 200;
	}

	/**
	 * 保存工艺图信息设置
	 */
	@RequestMapping("/saveGytInformation")
	@ResponseBody
	public int saveGytInformation(@RequestBody GytEntity ge) {
		try {
			// 保存信息
			GytEntity gyt1 = ge.getDescription();
			GytEntity gyt2 = ge.getData();
			ge.setText(gyt1.getText());
			ge.setDcolor(gyt1.getDcolor());
			ge.setDsize(gyt1.getDsize());
			ge.setDweight(gyt1.getDweight());
			ge.setSiteId(gyt2.getSiteId());
			ge.setTcolor(gyt2.getTcolor());
			ge.setTsize(gyt2.getTsize());
			ge.setTweight(gyt2.getTweight());
			ge.setType(gyt2.getType());
			ge.setParentSiteId(gyt2.getParentSiteId());
			monitorDataService.saveInformation(ge);
		} catch (Exception e) {
			return 500;
		}
		return 200;
	}

	/**
	 * 保存工艺图泵设置
	 */
	@RequestMapping("/saveGytPoint")
	@ResponseBody
	public int saveGytPoint(@RequestBody GytEntity ge) {
		try {
			// 保存泵信息
			monitorDataService.savePointInfo(ge);
		} catch (Exception e) {
			return 500;
		}
		return 200;
	}

	/**
	 * 保存工艺图与点位关联信息
	 */
	@RequestMapping("/saveGytSeat")
	@ResponseBody
	public int saveGytSeat(@RequestBody List<SeatEntity> ge) {
		try {
			for (SeatEntity seatEntity : ge) {
				monitorDataService.saveSeat(seatEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	/**
	 * 查询视频信息
	 */
	@RequiresPermissions("mon:video:list")
	@RequestMapping("/queryVideoInfo")
	@ResponseBody
	public JSONArray queryVideoInfo(@RequestBody String siteId) {
		List<String> re = RegionUtils.region();
		List<SysVideoEntity> list = sysSiteService.queryVideoInfo(siteId, re);

		JSONArray str = JSONArray.fromObject(list);
		return str;
	}

	/**
	 * 新增/更新工艺图底图
	 */
	@RequestMapping("/updateGytbgn")
	@ResponseBody
	public int updateGytbgn(@RequestBody GytEntity ge) {
		try {
			List<GytEntity> list = monitorDataService.queryGyt(ge.getGytId());
			if (list.size() != 0) {
				monitorDataService.updateGytbgn(ge);
			} else {
				monitorDataService.saveGytbgn(ge);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	/**
	 * 请求工艺图底图
	 */
	@RequestMapping("/queryGytbgn")
	@ResponseBody
	public JSONArray queryGytbgn(@RequestBody String gytId) {
		List<GytEntity> list = monitorDataService.queryGyt(gytId);

		JSONArray str = JSONArray.fromObject(list);
		return str;
	}

	/**
	 * 新增/更新工艺图标题
	 */
	@RequestMapping("/updateGytTitle")
	@ResponseBody
	public int updateGytTitle(@RequestBody GytEntity ge) {
		try {
			List<GytEntity> list = monitorDataService.queryTitle2(ge.getSiteId(), ge.getDivId());
			if (list.size() != 0) {
				monitorDataService.updateTitleInfo(ge);
			} else {
				monitorDataService.saveTitleInfo(ge);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	/**
	 * 请求工艺图标题
	 */
	@RequestMapping("/queryGytTitle")
	@ResponseBody
	public JSONArray queryGytTitle(@RequestBody Map<String, Object> params) {
		String siteId = (String) params.get("siteId");
		String divId = (String) params.get("divId");
		List<GytEntity> list = monitorDataService.queryTitle2(siteId, divId);

		JSONArray str = JSONArray.fromObject(list);
		return str;
	}

	/**
	 * 新增/更新工艺图信息
	 */
	@RequestMapping("/updateGytInformation")
	@ResponseBody
	public int updateGytInformation(@RequestBody GytEntity ge) {
		try {
			List<GytEntity> list = monitorDataService.queryInformation2(ge.getGytId(), ge.getDivId());
			if (list.size() != 0) {
				monitorDataService.updateInformation(ge);
			} else {
				monitorDataService.saveInformation(ge);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	/**
	 * 请求工艺图信息
	 */
	@RequestMapping("/queryGytInformation")
	@ResponseBody
	public JSONArray queryGytInformation(@RequestBody Map<String, Object> params) {
		String gytId = (String) params.get("gytId");
		String divId = (String) params.get("divId");
		List<GytEntity> list = monitorDataService.queryInformation2(gytId, divId);

		JSONArray str = JSONArray.fromObject(list);
		return str;
	}

	/**
	 * 新增/更新工艺图泵信息
	 */
	@RequestMapping("/updateGytPoint")
	@ResponseBody
	public int updateGytPoint(@RequestBody GytEntity ge) {
		try {
			List<GytEntity> list = monitorDataService.queryPoint2(ge.getSiteId(), ge.getDivId());
			if (list.size() != 0) {
				monitorDataService.updatePointInfo(ge);
			} else {
				monitorDataService.savePointInfo(ge);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	/**
	 * 请求工艺图泵信息
	 */
	@RequestMapping("/queryGytPoint")
	@ResponseBody
	public JSONArray queryGytPoint(@RequestBody Map<String, Object> params) {
		String siteId = (String) params.get("siteId");
		String divId = (String) params.get("divId");
		List<GytEntity> list = monitorDataService.queryPoint2(siteId, divId);

		JSONArray str = JSONArray.fromObject(list);
		return str;
	}

	/**
	 * 请求工艺图关联点位信息
	 */
	@RequestMapping("/queryGytSeat")
	@ResponseBody
	public JSONArray queryGytSeat(@RequestBody Map<String, Object> params) {
		String siteId = (String) params.get("siteId");
		String divId = (String) params.get("divId");
		List<SeatEntity> list = monitorDataService.querySeat2(siteId, divId);

		JSONArray str = JSONArray.fromObject(list);
		return str;
	}

	/**
	 * 新增/更新工艺图关联信息
	 */
	@RequestMapping("/updateSeat")
	@ResponseBody
	public int updateGytSeat(@RequestBody SeatEntity se) {
		try {
			List<SeatEntity> list = monitorDataService.querySeat2(se.getSiteId(), se.getDivId());
			if (list.size() != 0) {
				monitorDataService.updateSeat(se);
			} else {
				monitorDataService.saveSeat(se);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	/**
	 * 删除工艺图关联信息
	 */
	@RequestMapping("/deleteSeat")
	@ResponseBody
	public int deleteSeat(@RequestBody Map<String, Object> params) {
		try {
			String linkParentId = (String) params.get("linkParentId");
			String linkSiteId = (String) params.get("linkSiteId");
			monitorDataService.deleteSeat(linkParentId, linkSiteId, null);
		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		}
		return 200;
	}

	
	/**
	 * 请求统计信息-供水统计导出
	 * @throws ParseException 
	 */
	@RequiresPermissions("mon:analysis:list")
	@RequestMapping(value = "downloadAnalysisFlowExcel", method = {RequestMethod.GET})
	@ResponseBody
	public void downloadAnalysisFlowExcel(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		Map<String, Object> params = new HashMap<>();
		String siteId = request.getParameter("siteId");
		String stime = request.getParameter("stime");
		String etime = request.getParameter("etime");
		String type = request.getParameter("type");
		params.put("siteId", siteId);
		params.put("stime", stime);
		params.put("etime", etime);
		params.put("type", type);
		Map<String, Object> map = this.queryAnalysisFlow(params);
		
		ExcelBook.excelArea(map,request, response);
	}
	
	/**
	 * 请求统计信息-供水统计
	 * flowhour,flowday 等表
	 * @throws ParseException 
	 */
	@RequiresPermissions("mon:analysis:list")
	@RequestMapping("/queryAnalysisFlow")
	@ResponseBody
	public Map<String, Object> queryAnalysisFlow(@RequestBody Map<String, Object> params) throws ParseException {
		String siteIdParam = (String) params.get("siteId");
		String stime = (String) params.get("stime");
		String etime = (String) params.get("etime");
		String type = (String) params.get("type");
		
		String[] siteIdArray = siteIdParam.split(",");
		
		
		String sc = "";
		for (int i = 0; i < siteIdArray.length; i++) {
			if (0 == i) {
				// 查询X水厂的出水点 dic_value='S02' and site_name like '%出%水%'
				List<SysSiteEntity> list1 = sysSiteService.queryOutWaterSite(siteIdArray[i]);
				if (null != list1 && 0 != list1.size()) {
					for (int k = 0; k < list1.size(); k++) {
						if (k == 0) {
							sc = list1.get(k).getSiteId();
						} else {
							sc = sc + "," + list1.get(k).getSiteId();
						}
					}
				}
			} else {
				List<SysSiteEntity> list1 = sysSiteService.queryOutWaterSite(siteIdArray[i]);
				if (null != list1 && 0 != list1.size()) {
					for (int k = 0; k < list1.size(); k++) {
						sc = sc + "," + list1.get(k).getSiteId();
					}
				}
			}
		}
		
		// 如果参数点位有S02的出水口，变更点位
		if(!"".equals(sc)){
			siteIdArray = sc.split(",");
		}
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<String> dateList = new ArrayList<String>();
		dateList = getDateList(type, stime, etime);
		for (String date : dateList) {
			map.put(date, "");
		}

		String siteIdString = "";
		String siteNameString = "";
		for (int i = 0; i < siteIdArray.length; i++) {
			String siteId = siteIdArray[i];
			if (null != siteId && !"".equals(siteId)) {
				List<AnalysisEntity> list = new ArrayList<AnalysisEntity>();

				List<SysSiteEntity> list1 = sysSiteService.querySiteByS04(siteId);
				if (null != list1 && 0 != list1.size()) {
					for (SysSiteEntity sysSiteEntity : list1) {
						siteId = sysSiteEntity.getSiteId();
					}
				}

				if ("hour".equals(type)) {
					list = monitorDataService.queryAnalysisHourFlow(siteId, stime, etime);
				} else if ("day".equals(type)) {
					String stime1 = stime + " 00:00:00";
					String etime1 = etime + " 00:00:00";
					list = monitorDataService.queryAnalysisDayFlow(siteId, stime1, etime1);
				} else if ("month".equals(type)) {
					String stime1 = stime + "-01 00:00:00";
					String etime1 = etime + "-01 00:00:00";
					list = monitorDataService.queryAnalysisMonthFlow(siteId, stime1, etime1);
				} else if ("year".equals(type)) {
					String stime1 = stime + "-01-01 00:00:00";
					String etime1 = etime + "-01-01 00:00:00";
					list = monitorDataService.queryAnalysisYearFlow(siteId, stime1, etime1);
				}
				if (list.size() != 0 && list != null) {
					// 循环时间序列，匹配数据时间					
					for(Map.Entry<String, Object> m : map.entrySet()){
						Boolean exist = false;
						for(AnalysisEntity analysisEntity : list) {
							if(m.getKey().equals(analysisEntity.getTime())){
								if("".equals(m.getValue())){
									map.put(m.getKey(), ZeroFormat(analysisEntity.getValue(),2));
								} else{
									String re = String.valueOf(m.getValue());	
									re = re + "," + ZeroFormat(analysisEntity.getValue(),2);
									map.put(m.getKey(), re);
								}
								exist = true;
								break;
							}
						}
						if(exist == false){
							if("".equals(m.getValue())){
								map.put(m.getKey(), " ");

							}else{
								map.put(m.getKey(), String.valueOf(m.getValue()) + ", ");
							}
						}
					}

				} else{ // 该点位没有数据时(list为空)，补齐位置
					if(0 < i){
						for(Map.Entry<String, Object> m : map.entrySet()){
							map.put(m.getKey(), String.valueOf(m.getValue()) + ", ");
						}
					}
					if(0 == i){
						for(Map.Entry<String, Object> m : map.entrySet()){
							map.put(m.getKey(), String.valueOf(m.getValue()) + " ");
						}
					}
				}

				// 按循环顺序添加点位ID和名称
				if (0 == i) {
					siteIdString = siteId;
					siteNameString = sysSiteService.queryNameById(siteId);			
				} else {
					siteIdString = siteIdString + "," + siteId;
					siteNameString = siteNameString + "," + sysSiteService.queryNameById(siteId);
				}
			}
		}
		List<AnalysisEntityResp> dataList = new ArrayList<AnalysisEntityResp>();
		
		// 已完成排序，开始计算合计
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String[] s2 = String.valueOf(entry.getValue()).split(",");
			double totalvalue = 0;
			for (String string : s2) {
				if(!"".equals(string.trim())){
					totalvalue = totalvalue + Double.valueOf(string);
				}
			}
			String tv = String.valueOf(entry.getValue()) + "," + String.valueOf(ZeroFormat(totalvalue,2));
			AnalysisEntityResp ae = new AnalysisEntityResp();
			ae.setValue1(tv);
			ae.setTime(entry.getKey());		
			dataList.add(ae);
		}

		siteNameString = siteNameString +",合计";
		
		Map<String, Object> map2 = new LinkedHashMap<String, Object>();
		map2.put("siteName", siteNameString);
		map2.put("siteId", siteIdString);
		map2.put("result", dataList);

		return map2;
	}

	/**
	 * 请求树形点位结构
	 */
	@RequestMapping("/queryTreeSite")
	public Map<String, Object> queryTreeSite() {
		List<String> re = RegionUtils.region();
		// 供水厂
		List<SiteTreeEntity> scList = new ArrayList<SiteTreeEntity>();
		List<SysSiteEntity> list = sysSiteService.queryFacSite(re);
		for (SysSiteEntity sysSiteEntity : list) {
			SiteTreeEntity st = new SiteTreeEntity();
			if ("S02".equals(sysSiteEntity.getType())) {
				st.setSiteId(sysSiteEntity.getSiteId());
				st.setName(sysSiteEntity.getName());
				st.setFlag(false);
				st.setOrderNum(sysSiteEntity.getOrderNum());
				scList.add(st);
			}
		}
		
		List<SysRegionEntity> area = sysSiteService.queryAreaInfo(re);
		// 泵站 
		List<SiteTreeEntity> bzFatherList = new ArrayList<SiteTreeEntity>();
		for (SysRegionEntity sysRegionEntity : area) {
			List<SiteTreeEntity> bzList = new ArrayList<SiteTreeEntity>();
			SiteTreeEntity st = new SiteTreeEntity();
			st.setAreaId(sysRegionEntity.getRegionId());
			st.setAreaName(sysRegionEntity.getName());
			st.setFlag(false);
			List<SysSiteEntity> site = sysSiteService.querySiteByArea(sysRegionEntity.getRegionId(), re);
			for (SysSiteEntity sysSiteEntity : site) {
				SiteTreeEntity st2 = new SiteTreeEntity();
				st2.setSiteId(sysSiteEntity.getSiteId());
				st2.setName(sysSiteEntity.getName());
				st2.setFlag(true);
				st2.setOrderNum(sysSiteEntity.getOrderNum());
				if("S03".equals(sysSiteEntity.getType())){
					bzList.add(st2);
				}
			}
			st.setTreelist(bzList);
			bzFatherList.add(st);
		}
		// 供水管网
		List<SiteTreeEntity> gsgwFatherList = new ArrayList<SiteTreeEntity>();
		for (SysRegionEntity sysRegionEntity : area) {
			List<SiteTreeEntity> gsgwList = new ArrayList<SiteTreeEntity>();
			SiteTreeEntity st = new SiteTreeEntity();
			st.setAreaId(sysRegionEntity.getRegionId());
			st.setAreaName(sysRegionEntity.getName());
			st.setFlag(false);
			List<SysSiteEntity> site = sysSiteService.querySiteByArea(sysRegionEntity.getRegionId(), re);
			for (SysSiteEntity sysSiteEntity : site) {
				SiteTreeEntity st2 = new SiteTreeEntity();
				st2.setSiteId(sysSiteEntity.getSiteId());
				st2.setName(sysSiteEntity.getName());
				st2.setFlag(true);
				st2.setOrderNum(sysSiteEntity.getOrderNum());
				if(!"S03".equals(sysSiteEntity.getType())){
					gsgwList.add(st2);
				}
			}
			st.setTreelist(gsgwList);
			gsgwFatherList.add(st);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("供水厂", scList);
		map.put("供水管网", gsgwFatherList);
		map.put("泵站", bzFatherList);
		return map;
	}

	/**
	 * 请求排水管网树形点位结构
	 */
	@RequestMapping("/queryTreeSite4Ps")
	public Map<String, Object> queryTreeSite4Ps() {
		List<String> re = RegionUtils.region();
		List<SiteTreeEntity> stlist = new ArrayList<SiteTreeEntity>();
		List<SysSiteEntity> list = sysSiteService.queryFacSite4Ps(re);
		for (SysSiteEntity sysSiteEntity : list) {
			SiteTreeEntity st = new SiteTreeEntity();
			st.setSiteId(sysSiteEntity.getSiteId());
			st.setName(sysSiteEntity.getName());
			List<SiteTreeEntity> treelist = new ArrayList<SiteTreeEntity>();
			st.setTreelist(treelist);
			st.setFlag(true);
			stlist.add(st);
		}
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("排水管网及泵站", stlist);
		return map2;
	}

	/**
	 * 请求污水厂树形点位结构
	 */
	@RequestMapping("/queryTreeSite4Ws")
	public Map<String, Object> queryTreeSite4Ws() {
		List<String> re = RegionUtils.region();
		List<SiteTreeEntity> stlist2 = new ArrayList<SiteTreeEntity>();
		List<SysSiteEntity> list = sysSiteService.queryFacSite4Ws(re);
		for (SysSiteEntity sysSiteEntity : list) {
			List<SiteTreeEntity> stlist = new ArrayList<SiteTreeEntity>();
			SiteTreeEntity st = new SiteTreeEntity();
			if ("W01".equals(sysSiteEntity.getType())) {
				List<SysSiteEntity> list2 = sysSiteService.querySiteByParent(sysSiteEntity.getSiteId(), re, "S11");
				for (SysSiteEntity sysSiteEntity2 : list2) {
					SiteTreeEntity st2 = new SiteTreeEntity();
					st2.setSiteId(sysSiteEntity2.getSiteId());
					st2.setName(sysSiteEntity2.getName());
					st2.setFlag(true);
					stlist.add(st2);
				}

				st.setSiteId(sysSiteEntity.getSiteId());
				st.setName(sysSiteEntity.getName());
				st.setFlag(false);
				st.setTreelist(stlist);
				stlist2.add(st);
			}
		}
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("污水厂", stlist2);
		return map2;
	}

	/**
	 * 请求实时报警信息
	 */
	@RequestMapping("/queryInsAlarm")
	@ResponseBody
	public JSONArray queryInsAlarm() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar beforeTime = Calendar.getInstance();
		String etime = sdf.format(beforeTime.getTime());
		beforeTime.add(Calendar.MINUTE, -5);// 5分钟之前的时间
		Date beforeD = beforeTime.getTime();
		String stime = sdf.format(beforeD);
		List<AlarmTotalEntity> alarmdata = monitorService.queryInsAlarm(stime, etime);
		JSONArray str = JSONArray.fromObject(alarmdata);

		return str;
	}
	
	public static double ZeroFormat(double num, int n) {
		BigDecimal bigDecimal = new BigDecimal(num);
		return bigDecimal.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	private List<String> getDateList(String timeType, String beginTime, String endTime) throws ParseException {
		switch(timeType){
		case "hour" :
			break;
		case "day" :
			beginTime += " 00:00:00";
			endTime += " 00:00:00";
			break;
		case "month" :
			beginTime += "-01 00:00:00";
			endTime += "-01 00:00:00";
			break;
		case "year" :
			beginTime += "-01-01 00:00:00";
			endTime += "-01-01 00:00:00";
			break;
		default :
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dBegin = sdf.parse(beginTime);
		Date dEnd = sdf.parse(endTime);
		List<String> dateList = new ArrayList<String>();
		dateList.add(sdf.format(dBegin));
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dEnd);
		while (dEnd.after(calBegin.getTime())) {
			switch(timeType){
			case "hour" :
				calBegin.add(Calendar.HOUR, 1);
				break;
			case "day" :
				calBegin.add(Calendar.DATE, 1);
				break;
			case "month" :
				calBegin.add(Calendar.MONTH, 1);
				break;
			case "year" :
				calBegin.add(Calendar.YEAR, 1);
				break;
			default :
			}
			dateList.add(sdf.format(calBegin.getTime()));
		}
		return dateList;
	}
	   
}