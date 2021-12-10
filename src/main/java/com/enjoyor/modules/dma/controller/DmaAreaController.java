package com.enjoyor.modules.dma.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyor.modules.dma.entity.DmaAreaDTO;
import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaAreaTreeDTO;
import com.enjoyor.modules.dma.entity.DmaFlowNightWarnEntity;
import com.enjoyor.modules.dma.service.DmaAreaService;
import com.enjoyor.modules.dma.service.DmaSiteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;

@Api(description = "DMA分区")
@RestController
@RequestMapping("/dma/area")
public class DmaAreaController {

	@Autowired
	private DmaAreaService dmaAreaService;
	@Autowired
	private DmaSiteService dmaSiteService;
	
	/**
	 * 保存片区设置
	 * @param de 片区实体类
	 */
	@ApiOperation(value = "保存片区设置")
    @ResponseBody
	@RequestMapping("/save")
	public int save(@RequestBody DmaAreaEntity de){    	
		try{
			if(null == de.getDareaId() || "".equals(de.getDareaId())){
				return 1;
			}
			if(null == de.getDareaName() || "".equals(de.getDareaName())){
				return 1;
			}
			int v = dmaAreaService.queryCountAreaId(de.getDareaId());
			if(0 != v){
				return 1;
			}
	    	dmaAreaService.save(de);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		} 
		return 0;
	}
	
	/**
	 * 修改片区设置
	 * @param de 片区实体类
	 */
	@ApiOperation(value = "修改片区设置")
    @ResponseBody
	@RequestMapping("/update")
	public int update(@RequestBody DmaAreaEntity de){
		try{
			int result = dmaAreaService.update(de);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		} 
		return 0;
	}
	
	/**
	 * 删除片区设置
	 * @param id
	 */
	@ApiOperation(value = "删除片区设置")
    @ResponseBody
	@RequestMapping("/delete")
	public int delete(@RequestBody DmaAreaEntity de){
		try{
			int id = de.getId();
			dmaAreaService.delete(id);
			dmaSiteService.deleteByAreaId(de.getDareaId());
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		} 
		return 0;
	}
	
	/**
	 * 分页查询片区列表
	 * @param params dreaName片区名称 page当前页数 limit每页条数
	 * @return json
	 */
	@ApiOperation(value = "分页查询片区列表")
	@RequestMapping("/queryAreaList")
	public Map<String, Object> queryAreaList(@RequestParam Map<String, Object> params){
		DmaAreaEntity de = new DmaAreaEntity();
		String dareaName=(String)params.get("dareaName");
		int page = Integer.parseInt(params.get("page").toString());
		int limit = Integer.parseInt(params.get("limit").toString());		
		int offset = (page*limit)-limit+1;
		int limit2 = page*limit;
		
		de.setDareaName(dareaName);
		de.setOffset(offset);
		de.setLimit(limit2);
        de.setSidx("id");
        de.setOrder("desc");
		
	    List<DmaAreaEntity> list = dmaAreaService.queryAreaList(de);
	    int totalnum = dmaAreaService.queryAreaCount(de);
	    Map<String,Object> map=new HashMap<String, Object>();		
		map.put("count", totalnum);
		map.put("data",list);
		map.put("code", 0);
		
	    return map;
	}
	
	/**
	 * 查询片区父子结构列表
	 * @return json
	 */
	@ApiOperation(value = "片区父子结构列表")
	@ResponseBody
	@RequestMapping("/queryAreaList2")
	public JSONArray queryAreaList2(){
		List<DmaAreaEntity> firstList = dmaAreaService.queryAreaParent();
	    if(null != firstList && 0 != firstList.size()){
	    // 循环一级片区
		 for (DmaAreaEntity first : firstList) {
			List<DmaAreaEntity> sonList = dmaAreaService.queryAreaChild(first.getDareaId());
			if(null != sonList && 0 != sonList.size()){
				first.setChildren(sonList);
				// 循环二级片区
				for (DmaAreaEntity dmaAreaEntity2 : sonList) {
					sonList = dmaAreaService.queryAreaChild(dmaAreaEntity2.getDareaId());
					if(null != sonList && 0 != sonList.size()){
						dmaAreaEntity2.setChildren(sonList);	
						// 循环三级片区
						for (DmaAreaEntity dmaAreaEntity3 : sonList) {
							sonList = dmaAreaService.queryAreaChild(dmaAreaEntity3.getDareaId());
							if(null != sonList && 0 != sonList.size()){
								dmaAreaEntity2.setChildren(sonList);				
							}	
						}	
					}	
				}				
		    }
		 }
	   }
		JSONArray str =JSONArray.fromObject(firstList);
	    return str;		
	}
	
	
	/**
	 * 查询所有片区id 名称
	 */
	@ApiOperation(value = "查询所有dma分区id 名称")
    @ResponseBody
	@RequestMapping("/queryAllWaterArea")
	public JSONArray queryAllArea(){
		List<DmaAreaEntity> list = dmaAreaService.queryAllWaterArea();
		JSONArray str =JSONArray.fromObject(list);
	    return str;	
	}
	
	/**
	 * 查询DMA分区树状结构
	 */
	@ApiOperation(value = "查询所有dma分区树状结构")
    @ResponseBody
	@RequestMapping("/queryAreaTree")
	public JSONArray queryAreaTree(){
		List<DmaAreaEntity> list = dmaAreaService.queryAreaTree();
		JSONArray str =JSONArray.fromObject(list);
	    return str;	
	}
	
	/**
	 * 查询子集DMA分区昨日水量
	 */
	@ApiOperation(value = "查询子集DMA分区昨日水量")
    @ResponseBody
	@RequestMapping("/querySonList")
	public JSONArray querySonList(String dareaParentId){
		List<DmaAreaDTO> list = null;
		if(null != dareaParentId){
			list = dmaAreaService.querySonList(dareaParentId);
		}
		JSONArray result = JSONArray.fromObject(list);
	    return result;	
	}
	
	/**
	 * 根据dma分区id查询子点位夜间小流量
	 */
	@ApiOperation(value = "根据dma分区id查询子点位夜间小流量")
    @ResponseBody
	@RequestMapping("/DmaFlowNightWarn")
	public JSONArray DmaFlowNightWarn(String dareaId){
		List<DmaFlowNightWarnEntity> list = null;
		if(null != dareaId){
			list = dmaAreaService.queryDmaFlowNightWarnList(dareaId);
		}
		JSONArray result = JSONArray.fromObject(list);
	    return result;	
	}
	
	/**
	 * 查询某dma片区进出水漏损历史记录
	 */
	@ApiOperation(value = "查询某dma片区进出水漏损历史记录")
    @ResponseBody
	@RequestMapping("/queryDmaWaterHis")
	public Map<String, Object> queryDmaWaterHis(String dareaId, String beginTime, String endTime){
		List<DmaAreaDTO> list = dmaAreaService.queryDmaWaterHis(dareaId, beginTime, endTime);
		Map<String,Object> siteMap = dmaSiteService.querySiteDayFlow(dareaId, beginTime, endTime);	
		String calcMethod = dmaAreaService.dmalslCalcMethod(dareaId);
		Map<String,Object> resultMap = new HashMap<String, Object>();	
		resultMap.put("calcMethod", calcMethod);
		resultMap.put("areaData", list);
		resultMap.put("siteData", siteMap);
	    return resultMap;	
	}
	
	/**
	 * 查询DMA分区树状图 及昨日数据
	 */
	@ApiOperation(value = "查询子集DMA分区昨日水量")
    @ResponseBody
	@RequestMapping("/queryDmaTree")
	public JSONArray queryDmaTree(){
		List<DmaAreaTreeDTO> list = null;
		list = dmaAreaService.queryDmaTree();
		JSONArray result = JSONArray.fromObject(list);
	    return result;	
	}
}
