package com.enjoyor.modules.dma.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyor.common.http.HttpResult;
import com.enjoyor.common.utils.ExcelBook;
import com.enjoyor.common.utils.RegionUtils;
import com.enjoyor.modules.dma.entity.Dma111DTO;
import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaDataEntity;
import com.enjoyor.modules.dma.entity.DmaQueryByMonthDTO;
import com.enjoyor.modules.dma.entity.DmaUserEntity;
import com.enjoyor.modules.dma.service.DmaAreaService;
import com.enjoyor.modules.dma.service.DmaDataService;
import com.enjoyor.modules.dma.service.DmaUserService;
import com.enjoyor.modules.sys.entity.SysAreaSimpleEntity;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.sys.service.SysAreaService;
import com.enjoyor.modules.sys.service.SysSiteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;

@Api(description = "DMA分区")
@RestController
@RequestMapping("/dma/data")
public class DmaDataController {

	@Autowired
	private DmaDataService dmaDataService;

	/**
	 * 查询数据列表
	 * 
	 * @param params
	 *            dareaId片区编号 stime开始时间 etime结束时间 type日期类型
	 * @return json
	 */
	@ApiOperation(value = "查询数据列表")
	@RequestMapping(value = "/queryDataList", method = RequestMethod.POST)
	public Map<String, Object> queryDataList(@RequestBody Map<String, Object> params) {
		String dareaId = (String) params.get("dareaId");
		String stime = (String) params.get("stime");
		String etime = (String) params.get("etime");
		String type = (String) params.get("type");
		Map<String, Object> map = new HashMap<String, Object>();
		List<DmaDataEntity> list = null;
		double hjin = 0;
		double hjout = 0;
		double hjlsv = 0;
		if ("day".equals(type)) {
			list = dmaDataService.queryDayList(dareaId, stime, etime);
			for (DmaDataEntity dmaDataEntity : list) {
				double lsv2 = dmaDataEntity.getLsv() * 100;
				String lsv2string = String.valueOf(ZeroFormat(lsv2, 2)) + "%";
				dmaDataEntity.setLsv2(lsv2string);
				hjin = hjin + dmaDataEntity.getDareaFlowIn();
				hjout = hjout + dmaDataEntity.getDareaFlowOut();
			}
		} else if ("week".equals(type)) {
			list = dmaDataService.queryWeekList(dareaId, stime, etime);
			for (DmaDataEntity dmaDataEntity : list) {
				double lsv2 = dmaDataEntity.getLsv() * 100;
				String lsv2string = String.valueOf(ZeroFormat(lsv2, 2)) + "%";
				dmaDataEntity.setLsv2(lsv2string);
				hjin = hjin + dmaDataEntity.getDareaFlowIn();
				hjout = hjout + dmaDataEntity.getDareaFlowOut();
			}
		} else if ("month".equals(type)) {
			list = dmaDataService.queryMonthList(dareaId, stime + "-01", etime + "-01");
			for (DmaDataEntity dmaDataEntity : list) {
				double lsv2 = dmaDataEntity.getLsv() * 100;
				String lsv2string = String.valueOf(ZeroFormat(lsv2, 2)) + "%";
				dmaDataEntity.setLsv2(lsv2string);
				hjin = hjin + dmaDataEntity.getDareaFlowIn();
				hjout = hjout + dmaDataEntity.getDareaFlowOut();
			}
		} else if ("year".equals(type)) {
			list = dmaDataService.queryYearList(dareaId, stime + "-01-01", etime + "-01-01");
			for (DmaDataEntity dmaDataEntity : list) {
				double lsv2 = dmaDataEntity.getLsv() * 100;
				String lsv2string = String.valueOf(ZeroFormat(lsv2, 2)) + "%";
				dmaDataEntity.setLsv2(lsv2string);
				hjin = hjin + dmaDataEntity.getDareaFlowIn();
				hjout = hjout + dmaDataEntity.getDareaFlowOut();
			}

		}

		if (0 != hjout) {
			hjlsv = (hjin - hjout) / hjin;
			System.out.println(dareaId + hjlsv);
		} else {
			hjlsv = 0;
		}

		map.put("data", list);
		map.put("hjin", ZeroFormat(hjin, 2));
		map.put("hjout", ZeroFormat(hjout, 2));
		map.put("hjlsv", (ZeroFormat(hjlsv * 100, 2)) + "%");

		return map;
	}

	@RequestMapping("/download")
	@ResponseBody
	public void download(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<>();
		String dareaId = request.getParameter("dareaId");
		String stime = request.getParameter("stime");
		String etime = request.getParameter("etime");
		String type = request.getParameter("type");
		params.put("dareaId", dareaId);
		params.put("stime", stime);
		params.put("etime", etime);
		params.put("type", type);
		Map<String, Object> map = this.queryDataList(params);

		ExcelBook.excelDma(map, request, response);
	}

	public static double ZeroFormat(double num, int n) {
		BigDecimal bigDecimal = new BigDecimal(num);
		// DecimalFormat ff = new DecimalFormat("#.0000"); //保留四位小数
		// double result = Double.valueOf(ff.format(num));
		// return result;
		return bigDecimal.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		// setscale(n,BigDecimal.ROUND_HALF_UP).doubleValue;
	}

	@RequestMapping(value="/queryByMonth", method = RequestMethod.GET)
    public HttpResult queryByMonth() {
		HttpResult result = new HttpResult();
		try {
			List<DmaQueryByMonthDTO> data = dmaDataService.queryByMonth();
			result.setData(data);
			result.setCode(0);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(e.toString());
			result.setCode(500);
		}
		return result;
	}
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
    public HttpResult test() {
		HttpResult result = new HttpResult();
		try {
			result.setData(0001);
			result.setCode(200);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(e.toString());
			result.setCode(500);
		}
		return result;
	}
	
	
	@RequestMapping(value="/query111", method = RequestMethod.GET)
    public HttpResult query111(@RequestParam (required = false, value = "areaName") String areaName
    		, @RequestParam (required = false, value = "beginTime") String beginTime
    		, @RequestParam (required = false, value = "endTime") String endTime) {
		HttpResult result = new HttpResult();
		try {
			List<Dma111DTO> data = dmaDataService.query111(areaName, beginTime, endTime);
			result.setData(data);
			result.setCode(200);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(e.toString());
			result.setCode(500);
		}
		return result;
	}
    
}
