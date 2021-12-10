package com.enjoyor.modules.sys.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.enjoyor.common.utils.RegionUtils;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.sys.entity.SiteTreeEntity;
import com.enjoyor.modules.sys.entity.SysDictionaryEntity;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.sys.service.SysDictionaryService;
import com.enjoyor.modules.sys.service.SysSiteService;
import com.enjoyor.common.http.HttpResult;
import com.enjoyor.modules.sys.entity.SysSiteEntity;

import net.sf.json.JSONArray;



@RestController
@RequestMapping("/sys/site")
public class SysSiteController extends AbstractController{
	@Autowired
	private SysSiteService sysSiteService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	
    @ResponseBody
	@RequestMapping("/queryFacSite")
	public JSONArray queryFacSite(){
    	List<String> re=RegionUtils.region();
		List<SysSiteEntity> siteType=sysSiteService.queryFacSite(re);
		
		JSONArray str =JSONArray.fromObject(siteType);

		return str;
	}
    
    @ResponseBody
	@RequestMapping("/queryfpSite")
	public JSONArray querfpSite(@RequestBody String type){
        List<String> re=RegionUtils.region();
		List<SysSiteEntity> siteType=sysSiteService.queryfpSite(type,re);
		
		JSONArray str =JSONArray.fromObject(siteType);

		return str;
    }
    
    @ResponseBody
	@RequestMapping("/queryfpSite2")
	public JSONArray querfpSite2(@RequestBody Map<String, Object> params){
    	List<String> re=RegionUtils.region();
    	String type=(String)params.get("type");
    	String areaId=(String)params.get("areaId");
		List<SysSiteEntity> siteType=sysSiteService.queryfpSite2(type,areaId,re);
		
		JSONArray str =JSONArray.fromObject(siteType);
		return str;
	}
	
    @ResponseBody
	@RequestMapping("/queryParentSite")
	public JSONArray queryParentSite(@RequestBody String siteId){
    	List<String> re=RegionUtils.region();
		List<SysSiteEntity> siteType=sysSiteService.queryParentSite(siteId,re);
		
		JSONArray str =JSONArray.fromObject(siteType);

		return str;
	}
    
    /*
     * 根据片区ID查询S04,S05的点位（供水管网）
     */
    @ResponseBody
	@RequestMapping("/queryByArea")
	public JSONArray queryByArea(@RequestBody String areaId){
    	List<String> re = RegionUtils.region();
    	//S04 : 管线的dicValue
		List<String> dicValue = new ArrayList<>();
		dicValue.add("S03");
		dicValue.add("S04");
		dicValue.add("S05");
		dicValue.add("S06");
    	List<SysSiteEntity> siteList = new ArrayList<SysSiteEntity>();
    	siteList = sysSiteService.querySiteByAreaType(areaId, dicValue, re);
		JSONArray str =JSONArray.fromObject(siteList);

		return str;
	}
    
    /*
     * 根据片区ID查询P02,P03的点位（排水管网）
     */
    @ResponseBody
	@RequestMapping("/queryPsgwByArea")
	public JSONArray queryPsgwByArea(@RequestBody String areaId){
    	List<String> re = RegionUtils.region();
		List<String> dicValue = new ArrayList<>();
		dicValue.add("P02");
		dicValue.add("P03");
		dicValue.add("P04");
    	List<SysSiteEntity> siteList = new ArrayList<SysSiteEntity>();
    	siteList = sysSiteService.querySiteByAreaType(areaId, dicValue, re);
		JSONArray str =JSONArray.fromObject(siteList);

		return str;
	}
    
    /*
     * 根据片区ID查询W01的点位（污水厂）
     */
    @ResponseBody
	@RequestMapping("/querySewageByArea")
	public JSONArray querySewageByArea(){
    	List<SysSiteEntity> siteList = new ArrayList<SysSiteEntity>();
    	siteList = sysSiteService.querySewageFactory();
		JSONArray str =JSONArray.fromObject(siteList);
		return str;
	}
    
    /**
     * 根据点位Id查询其子点位列表
     */
    @ResponseBody
	@RequestMapping("/querySewageImpExp")
	public JSONArray querySewageImpExp(@RequestBody String siteId){
		String siteParentId = siteId;
    	List<SysSiteEntity> siteList = new ArrayList<SysSiteEntity>();
    	siteList = sysSiteService.querySewageImpExp(siteParentId);
		JSONArray str =JSONArray.fromObject(siteList);
		return str;
	}
    
    @ResponseBody
	@RequestMapping("/queryPsbzSite")
	public JSONArray queryPsbzSite(@RequestBody Map<String, Object> params){
    	String type=(String)params.get("type");
    	String areaId=(String)params.get("areaId");
		List<SysSiteEntity> siteType=sysSiteService.queryPsbzSite(type,areaId);
		
		JSONArray str =JSONArray.fromObject(siteType);
		return str;
	}
    
    // 多点位查询，条件可自己添加
    @ResponseBody
   	@RequestMapping("/querySysSiteAndDmaUser")
   	public Map<String, Object> querySysSiteAndDmaUser(@RequestParam Map<String, Object> params){
       	String siteId = (String)params.get("siteId");
       	String name = (String)params.get("siteName");
       	String type = (String)params.get("siteType");
       	String areaName = (String)params.get("areaName");
       	int page = Integer.parseInt(params.get("page").toString());
		int limit = Integer.parseInt(params.get("limit").toString());		
		int offset = (page * limit) - limit + 1;
		int limit2 = page * limit;
       	SysSiteEntity siteParam = new SysSiteEntity();
       	siteParam.setSiteId(siteId);
       	siteParam.setName(name);
       	siteParam.setType(type);
       	siteParam.setAreaName(areaName);
       	siteParam.setOffset(offset);
       	siteParam.setLimit(limit2);
   		List<SysSiteEntity> siteList = sysSiteService.querySysSiteAndDmaUser(siteParam);   
	    int totalnum = sysSiteService.count(siteParam);
   		Map<String,Object> map=new HashMap<String, Object>();		
		map.put("data", siteList);
		map.put("count", totalnum);
		map.put("code", 0);	
	    return map;
   	}
    
	/**
	 * 所有三个污水厂
	 */
	@ResponseBody
	@RequestMapping("/queryAllWsc")
	public HttpResult queryAllWsc(){
		HttpResult result = new HttpResult();
		try {
			List<SysSiteEntity> list = sysSiteService.queryAllWsc();
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(e.toString());
		}
		return result;
	}
}
