package com.enjoyor.modules.dma.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.enjoyor.common.utils.RegionUtils;
import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaSiteEntity;
import com.enjoyor.modules.dma.entity.DmaUserEntity;
import com.enjoyor.modules.dma.service.DmaAreaService;
import com.enjoyor.modules.dma.service.DmaSiteService;
import com.enjoyor.modules.dma.service.DmaUserService;
import com.enjoyor.modules.sys.entity.SysAreaSimpleEntity;
import com.enjoyor.modules.sys.entity.SysDictionaryEntity;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.sys.service.SysAreaService;
import com.enjoyor.modules.sys.service.SysDictionaryService;
import com.enjoyor.modules.sys.service.SysSiteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;

@Api(description = "DMA分区")
@RestController
@RequestMapping("/dma/site")
public class DmaSiteController {

	@Autowired
	private DmaSiteService dmaSiteService;
	@Autowired
	private SysSiteService sysSiteService;
	@Autowired
	private DmaAreaService dmaAreaService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	/**
	 * 保存片区关联点位设置
	 * @param de Map
	 */
	/*@ApiOperation(value = "保存片区关联点位设置")
    @ResponseBody
	@RequestMapping(value="/saveSiteList",method=RequestMethod.POST)
	public Map<String, Object> save11(@RequestBody Map<String, Object> params){
		String dareaId = (String)params.get("dareaId");
		String dareaName = (String)params.get("dareaName");
		String dsiteId = (String)params.get("dsiteId1");
		String dsiteName = (String)params.get("dsiteName");
		String[] siteId = dsiteId.split(",");
		for (String string : siteId) {
			List<SysSiteEntity> list = sysSiteService.queryList(string);
			for (SysSiteEntity sysSiteEntity : list) {
				DmaSiteEntity de = new DmaSiteEntity();
				de.setDareaId(dareaId);
				de.setDsiteId(string);
				de.setDsiteName(sysSiteEntity.getName());
				de.setDsiteType(sysSiteEntity.getType());
				de.setDsiteParentId(sysSiteEntity.getParentId());
				de.setDsiteJc("进");
				dmaSiteService.save(de);
			}
		}
	    Map<String,Object> map = new HashMap<String, Object>();		
		map.put("count", "");
		map.put("data","");
		map.put("code", 0);
		return map;
	}
	
	*//**
	 * 保存片区点位设置
	 *//*
	@ApiOperation(value = "保存片区点位设置")
    @ResponseBody
	@RequestMapping("/save")
	public int save(@RequestBody Map<String, Object> params){    	
		try{
			String dareaId = (String)params.get("dareaId");
			String dareaName = (String)params.get("dareaName");
			List<DmaSiteEntity> dmaSiteEntityList = (List)params.get("siteList");
			
			String jc = (String)params.get("jc");
			int coefficient = Integer.parseInt(params.get("coefficient").toString());
			
			DmaSiteEntity dmaSiteEntity = new DmaSiteEntity();
			dmaSiteService.save(dmaSiteEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		} 
		return 0;
	}
	
	*//**
	 * 修改片区关联点位设置
	 * @param Map
	 *//*
	@ApiOperation(value = "修改片区关联点位设置")
    @ResponseBody
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public void update(@RequestParam Map<String, Object> params){
		String dareaId=(String)params.get("dareaId");
		String dsiteId=(String)params.get("dsiteId1");
		String dsiteId2=(String)params.get("dsiteId2");
		String[] siteId = dsiteId.split(",");
		dmaSiteService.delete2(dareaId);
		for (String string : siteId) {								
			List<SysSiteEntity> list = sysSiteService.queryList(string);
			for (SysSiteEntity sysSiteEntity : list) {
				DmaSiteEntity de = new DmaSiteEntity();
				de.setDsiteId(string);
				List<DmaSiteEntity> list2 = dmaSiteService.querySiteList(de);
				if(null == list2 || 0 ==list2.size()){
					de.setDareaId(dareaId);				
					de.setDsiteName(sysSiteEntity.getName());
					de.setDsiteType(sysSiteEntity.getType());
					de.setDsiteParentId(sysSiteEntity.getParentId());
					de.setDsiteJc("进");
					dmaSiteService.save(de);
				}			
			}
		}
		String[] siteId2 = dsiteId2.split(",");
		for (String string : siteId2) {								
			List<SysSiteEntity> list = sysSiteService.queryList(string);
			for (SysSiteEntity sysSiteEntity : list) {
				DmaSiteEntity de = new DmaSiteEntity();
				de.setDsiteId(string);
				List<DmaSiteEntity> list2 = dmaSiteService.querySiteList(de);
				if(null == list2 || 0 ==list2.size()){
					de.setDareaId(dareaId);				
					de.setDsiteName(sysSiteEntity.getName());
					de.setDsiteType(sysSiteEntity.getType());
					de.setDsiteParentId(sysSiteEntity.getParentId());
					de.setDsiteJc("出");
					dmaSiteService.save(de);
				}			
			}
		}
	}
	
	*//**
	 * 删除片区关联点位设置
	 * @param id
	 *//*
	@ApiOperation(value = "删除片区关联点位设置")
    @ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.DELETE	)
	public int delete(@RequestBody DmaSiteEntity dmaSiteEntity){
		try{
			int id = dmaSiteEntity.getId();
			dmaSiteService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		} 
		return 0;
	}*/
	
	
	/**
	 * 查询片区关联的点位
	 * @param params dareaId片区ID dsiteId点位id dsiteType点位类型
	 * @return json
	 */
	@ApiOperation(value = "查询片区关联的点位")
    @ResponseBody
	@RequestMapping(value="/querySiteList",method=RequestMethod.POST)
	public Map<String, Object> querySiteList(@RequestBody Map<String, Object> params){
		DmaSiteEntity de = new DmaSiteEntity();
		/*int page = Integer.parseInt(params.get("page").toString());
		int limit = Integer.parseInt(params.get("limit").toString());		
		int offset = (page*limit)-limit+1;
		int limit2 = page*limit;*/
		String dareaId=(String)params.get("dareaId");
		String dsiteId=(String)params.get("dsiteId");
		String dsiteType=(String)params.get("dsiteType");
		String jc=(String)params.get("jc");
		
		de.setDareaId(dareaId);
		de.setDsiteId(dsiteId);
		de.setDsiteType(dsiteType);
		de.setDsiteJc(jc);
		
/*		de.setOffset(offset);
		de.setLimit(limit2);*/
        de.setSidx("id");
        de.setOrder("desc");
		
	    List<DmaSiteEntity> waterSiteList = dmaSiteService.querySiteList(de);
	    
       	List<SysDictionaryEntity> dicList = sysDictionaryService.queryDicList("4");
	    for(DmaSiteEntity dmaSite : waterSiteList){
   			for(SysDictionaryEntity dicItem : dicList){
   				if(dicItem.getDicValue().equals(dmaSite.getDsiteType())){
   					dmaSite.setDsiteTypeName(dicItem.getName());
   				}
   			}
   		}
	    int totalnum = dmaSiteService.querySiteCount(de);
	    Map<String,Object> map=new HashMap<String, Object>();		
		map.put("count", totalnum);
		map.put("data",waterSiteList);
		map.put("code", 0);
		
	    return map;
	}
	

	@ApiOperation(value = "保存片区关联点位设置")
    @ResponseBody
	@RequestMapping(value="/saveAreaSiteSet", method = RequestMethod.POST)
	public int save11(@RequestBody Map<String, Object> params){
		String dareaId = (String)params.get("dareaId");
		String dareaName = (String)params.get("dareaName");
		String inSiteIdList = (String)params.get("inSiteList");
		String OutSiteIdList = (String)params.get("outSiteList");
		
		double coefficient4In = 0;
		if(!"".equals(params.get("coefficient4In").toString()) && null != params.get("coefficient4In").toString()){
			coefficient4In = Double.parseDouble(params.get("coefficient4In").toString());
		}
		double coefficient4Out = 0;
		if(!"".equals(params.get("coefficient4Out").toString()) && null != params.get("coefficient4Out").toString()){
			coefficient4Out = Double.parseDouble(params.get("coefficient4Out").toString());
		}
		Boolean dayCheck = (Boolean)params.get("dayCheck");
		Boolean weekCheck = (Boolean)params.get("weekCheck");
		Boolean monthCheck = (Boolean)params.get("monthCheck");
		Boolean yearCheck = (Boolean)params.get("yearCheck");
		
		String[] inSite = inSiteIdList.split(",");
		String[] outSite = OutSiteIdList.split(",");
		try{
			// 清除该片区原点位
			dmaSiteService.deleteByAreaId(dareaId);
			if(!inSiteIdList.equals("")){
				for (String siteId : inSite) {
					SysSiteEntity sysSite = sysSiteService.querySingleSiteBySiteId(siteId);
					DmaSiteEntity site = new DmaSiteEntity();
					site.setDareaId(dareaId);
					site.setDareaName(dareaName);
					site.setDsiteId(sysSite.getSiteId());
					site.setDsiteName(sysSite.getName());
					site.setDsiteType(sysSite.getType());
					site.setDsiteJc("进");
					site.setCoefficient(coefficient4In);
					dmaSiteService.save(site);
				}
			}
			if(!OutSiteIdList.equals("")){
				for (String siteId : outSite) {
					SysSiteEntity sysSite = sysSiteService.querySingleSiteBySiteId(siteId);
					DmaSiteEntity site = new DmaSiteEntity();
					site.setDareaId(dareaId);
					site.setDareaName(dareaName);
					site.setDsiteId(sysSite.getSiteId());
					site.setDsiteName(sysSite.getName());
					site.setDsiteType(sysSite.getType());
					site.setDsiteJc("出");
					site.setCoefficient(coefficient4Out);
					dmaSiteService.save(site);
				}
			}
			// 更新dmaArea的日月年
			DmaAreaEntity darea = new DmaAreaEntity();
			darea.setDareaId(dareaId);
			if(dayCheck){
				darea.setDay(1);
			} 
			if(weekCheck){
				darea.setWeek(1);
			} 
			if(monthCheck){
				darea.setMonth(1);
			} 
			if(yearCheck){
				darea.setYear(1);
			} 
			dmaAreaService.updateByDareaId(darea);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		} 
		return 0;
	}
	
	/**
	 * 查询字典表中所有点位类型
	 */
	@ApiOperation(value = "查询字典表中所有点位类型")
    @ResponseBody
	@RequestMapping(value="/queryDicSiteTypeList")
	public JSONArray queryAllUserType(){
		List<SysDictionaryEntity> dicList = sysDictionaryService.queryDicList("4");
		JSONArray str =JSONArray.fromObject(dicList);
	    return str;	
	}
}
