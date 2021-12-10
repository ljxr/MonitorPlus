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

import com.enjoyor.common.utils.RegionUtils;
import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaUserEntity;
import com.enjoyor.modules.dma.service.DmaAreaService;
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
@RequestMapping("/dma/user")
public class DmaUserController {

	@Autowired
	private DmaUserService dmaUserService;
	@Autowired
	private SysAreaService sysAreaService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	/**
	 * 保存用户设置
	 * @param de 用户实体类
	 */
	@ApiOperation(value = "保存用户设置")
    @ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public int save(@RequestBody DmaUserEntity de){
		try{
			if(null == de.getUserId() || "".equals(de.getUserId())){
				return 1;
			}
			if(null == de.getUserName() || "".equals(de.getUserName())){
				return 1;
			}
			if(null == de.getUserType() || "".equals(de.getUserType())){
				return 1;
			}
			dmaUserService.save(de);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		} 
		return 0;
	}
	
	/**
	 * 修改用户设置
	 * @param de 用户实体类
	 */
	@ApiOperation(value = "修改用户设置")
    @ResponseBody
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public int update(@RequestBody DmaUserEntity de){
		try{
			dmaUserService.update(de);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		} 
		return 0;
	}
	
	/**
	 * 删除用户设置
	 * @param id
	 */
	@ApiOperation(value = "删除用户设置")
    @ResponseBody
	@RequestMapping(value="/delete")
	public int delete(@RequestBody DmaAreaEntity de){
		try{
			int id = de.getId();
			dmaUserService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		} 
		return 0;
	}
	
	/**
	 * 分页查询用户列表
	 * @param params dreaName片区名称 page当前页数 limit每页条数
	 * @return json
	 */
	@ApiOperation(value = "分页查询用户列表")
	@RequestMapping(value="/queryUserList",method=RequestMethod.POST)
	public Map<String, Object> queryUserList(@RequestParam Map<String, Object> params){
		DmaUserEntity de = new DmaUserEntity();
		String userId=(String)params.get("userId");
		String userName=(String)params.get("userName");
		String userType=(String)params.get("userType");
		String areaId=(String)params.get("areaId");
		String areaName=(String)params.get("areaName");
		String dareaId=(String)params.get("dareaId");
		
		int page = Integer.parseInt(params.get("page").toString());
		int limit = Integer.parseInt(params.get("limit").toString());		
		int offset=(page*limit)-limit+1;
		int limit2=page*limit;
		
		de.setUserId(userId);
		de.setUserName(userName);
		de.setUserType(userType);
		de.setAreaId(areaId);
		de.setAreaName(areaName);
		de.setDareaId(dareaId);
		de.setOffset(offset);
		de.setLimit(limit2);
        de.setSidx("id");
        de.setOrder("desc");
		
	    List<DmaUserEntity> list = dmaUserService.queryUserList(de);
	    int totalnum = dmaUserService.queryUserCount(de);
	    Map<String,Object> map=new HashMap<String, Object>();		
		map.put("count", totalnum);
		map.put("data",list);
		map.put("code", 0);
		
	    return map;
	}
	
	/**
	 * 查询sys_area所有area
	 */
	@ApiOperation(value = "查询sys_area所有area")
    @ResponseBody
	@RequestMapping(value="/queryAllArea")
	public JSONArray queryAllArea(){
		List<SysAreaSimpleEntity> areaList = sysAreaService.queryAllArea();
		JSONArray str =JSONArray.fromObject(areaList);
	    return str;	
	}
	
	/**
	 * 查询字典表中所有用户类别
	 */
	@ApiOperation(value = "查询字典表中用户类别")
    @ResponseBody
	@RequestMapping(value="/queryDicUserTypeList")
	public JSONArray queryAllUserType(){
		List<SysDictionaryEntity> dicList = sysDictionaryService.queryDicList("20");
		JSONArray str =JSONArray.fromObject(dicList);
	    return str;	
	}
}
