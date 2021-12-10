package com.enjoyor.modules.sys.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.enjoyor.common.utils.RegionUtils;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.sys.entity.SysAreaSimpleEntity;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.sys.service.SysAreaService;
import com.enjoyor.modules.sys.service.SysSiteService;


import net.sf.json.JSONArray;



@RestController
@RequestMapping("/sys/area")
public class SysAreaController extends AbstractController{
	@Autowired
	private SysAreaService sysAreaService;

    @ResponseBody
	@RequestMapping("/findAllArea")
    // 获取营业所，片区，父子结构list
	public JSONArray findAllArea(){
    	List<String> re = RegionUtils.region();
		List<SysAreaSimpleEntity> areaList = sysAreaService.findAllArea(re);
		JSONArray str = JSONArray.fromObject(areaList);
		return str;
	}
    
    @ResponseBody
	@RequestMapping("/findAllPsArea")
    // 获取所有排水片区
	public JSONArray findAllPsArea(){
    	List<String> re = RegionUtils.region();
		List<SysAreaSimpleEntity> areaList = sysAreaService.findAllPsArea(re);
		JSONArray str = JSONArray.fromObject(areaList);
		return str;
	}
}
