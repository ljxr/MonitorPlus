package com.enjoyor.modules.dma.controller;

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

import com.enjoyor.common.http.HttpResult;
import com.enjoyor.modules.dma.entity.DmaBookEntity;
import com.enjoyor.modules.dma.entity.DmaBookQueryEntity;
import com.enjoyor.modules.dma.service.DmaBookService;

import io.swagger.annotations.Api;

@Api(description = "DMA表册号")
@RestController
@RequestMapping("/dma/book")
public class DmaBookController {

	@Autowired
	private DmaBookService dmaBookService;

	/**
	 * 查询表册号
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public Map<String, Object> query(@RequestParam Map<String, Object> params) {
		DmaBookQueryEntity en = new DmaBookQueryEntity();
		if(null != params.get("bookId") && !"".equals(params.get("bookId"))){
			en.setBookId(params.get("bookId").toString());
		}
		if(null != params.get("bookName") && !"".equals(params.get("bookName"))){
			en.setBookName(params.get("bookName").toString());
		}
		if(null != params.get("cycle") && !"".equals(params.get("cycle"))){
			en.setCycle(Integer.parseInt(params.get("cycle").toString()));
		}
		if(null != params.get("dmaAreaId") && !"".equals(params.get("dmaAreaId"))){
			en.setDmaAreaId(params.get("dmaAreaId").toString());
		}
		int page = Integer.parseInt(params.get("page").toString());
		int limit = Integer.parseInt(params.get("limit").toString());		
		int offset = (page*limit)-limit+1;
		int limit2 = page*limit;
		en.setOffset(offset);
		en.setLimit(limit2);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<DmaBookQueryEntity> list = dmaBookService.query(en);
			DmaBookEntity countParam = new DmaBookEntity();
			countParam.setBookId(en.getBookId());
			countParam.setBookName(en.getBookName());
			countParam.setCycle(en.getCycle());
			countParam.setDmaAreaId(en.getDmaAreaId());
			int count = dmaBookService.count(countParam);
			resultMap.put("data", list);
			resultMap.put("count", count);
			resultMap.put("code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", 500);
			resultMap.put("message", e.getStackTrace());
		}
		return resultMap;
	}
	
	/**
	 * 新增表册号
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public HttpResult save(@RequestBody DmaBookEntity en) {
		try {
			if (null == en.getBookId()) {
				return HttpResult.failed("未填写表册号ID");
			}
			if (null == en.getBookName()) {
				return HttpResult.failed("未填写表册号名称");
			}
			List<DmaBookQueryEntity> bookList = dmaBookService.queryExist(en.getBookId());
			if(bookList.size() > 0){
				return HttpResult.failed("已存在相同表册号ID");
			}
			dmaBookService.save(en);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResult.failed(e.getMessage());
		}
		return HttpResult.success();
	}

	/**
	 * 修改表册号
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public HttpResult update(@RequestBody DmaBookEntity en) {
		try {
			if (null == en.getBookName()) {
				return HttpResult.failed("未填写表册号名称");
			}
			dmaBookService.update(en);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResult.failed(e.getMessage());
		}
		return HttpResult.success();
	}
	
	/**
	 * 删除表册号
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public HttpResult update(@RequestParam String bookId) {
		try {			
			dmaBookService.delete(bookId);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResult.failed(e.getMessage());
		}
		return HttpResult.success();
	}

}
