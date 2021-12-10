package com.enjoyor.modules.monitor.controller;

import com.enjoyor.modules.monitor.entity.BigDataDTO;
import com.enjoyor.modules.monitor.service.BigDataService;
import com.enjoyor.modules.sys.entity.SysAreaSimpleEntity;
import com.enjoyor.modules.sys.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
 
@RestController
@RequestMapping("/bigdata")
public class BigDataController {

	@Autowired
	private BigDataService bigDataService;
	
	@Autowired
	private SysAreaService sysAreaService;
	
	/**
	 * 大用户数量
	 * @return
	 */
	@PostMapping("/user")
	public Map<String,Object> user() {
		List<BigDataDTO> list = bigDataService.queryBigUser();
		int totalnum=0;
		if (null != list && 0 != list.size()) {
			for (BigDataDTO bigDataDTO : list) {
				totalnum = totalnum + bigDataDTO.getNum();
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalnum", totalnum);
		map.put("list", list);
		return map;
	}
	
	/**
	 * 化验室数据
	 * @return
	 */
	@PostMapping("/waterQuality_test")
	public Map<String,Object> waterQuality_test(@RequestBody Map<String,Object> params){
		String type = (String)params.get("type");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String time = df.format(calendar.getTime());
		double value1 = 0;
		double value2 = 0;
		if("供水".equals(type)){		
			List<BigDataDTO> ccs = bigDataService.queryCCS(time);			
			if(null != ccs && 0 != ccs.size()){	
				double count1 = 0;
				for (BigDataDTO bigDataDTO : ccs) {
					if(1 != bigDataDTO.getNum()){
						count1++;
					}					
				}
			  value1 = ZeroFormat(count1/ccs.size(),2);
			}			
			List<BigDataDTO> gws = bigDataService.queryYS(time);
			if(null != gws && 0 != gws.size()){
				double count2 = 0;
				for (BigDataDTO bigDataDTO : gws) {
					if(1 != bigDataDTO.getNum()){
						count2++;
					}
				}
			 value2 = ZeroFormat(count2/gws.size(),2);
			}					
		}else if("污水".equals(type)){
			Map<Integer, String> qb = new HashMap<Integer, String>();
			List<BigDataDTO> list1 = bigDataService.queryWQ();
			for (BigDataDTO bigDataDTO : list1) {
				qb.put(bigDataDTO.getNum(), bigDataDTO.getAreaName());
			}
			List<BigDataDTO> list2 = bigDataService.queryWS(time);
			double count3 = 0;
			if (null != list2 && 0 != list2.size()) {
				for (BigDataDTO bigDataDTO : list2) {
					String value = qb.get(bigDataDTO.getNum());
					if (null != value) {
						int min = Integer.parseInt(value.split("-")[0]);
						int max = Integer.parseInt(value.split("-")[1]);
						Double n = bigDataDTO.getValue();
						if (min < n && n < max) {
							count3++;
						}
					}
				}
				value1 = ZeroFormat(count3/list2.size(),2);
			}			
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ccs", value1);
		map.put("gws", value2);
		return map;
	}
	
	/**
	 * 管线资产
	 * @return
	 */
	@PostMapping("/pipe")
	public Map<String,Object> pipe(@RequestBody Map<String,Object> params){
		String type = (String)params.get("type");
		Map<String,Object> map = new HashMap<String,Object>();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		String time = df.format(calendar.getTime());
		
		int count = bigDataService.queryPipeInsCounts(time);
		int length = bigDataService.queryTotalLength();
		double lengthf = ZeroFormat((length/1000), 2);
		
		if("管径".equals(type)){
			List<BigDataDTO> list1 = bigDataService.queryDiameter();
			 map.put("list", list1);
			
		}else if("材质".equals(type)){
			List<BigDataDTO> list2 = bigDataService.queryMaterial();
			map.put("list", list2);
			
		}else if("区域".equals(type)){
			List<BigDataDTO> list3 = bigDataService.queryPArea();
			map.put("list", list3);
			
		}
		map.put("count", count);
		map.put("length", lengthf);
		return map;
	}
	
	/**
	 * 热线工单
	 * @return
	 */
	@PostMapping("/ordertest")
	public Map<String,Object> ordertest(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		SimpleDateFormat df3 = new SimpleDateFormat("yyyy-01-01 00:00:00");			
		
		//Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		Calendar calendar2 = Calendar.getInstance();
		String today = df.format(date);	
		calendar2.add(Calendar.DATE, -1);
		String yesterday = df.format(calendar2.getTime());
		
		List<BigDataDTO> todayNum = bigDataService.queryTodayOrderNum(today);
		double day = bigDataService.queryOrderNum(today);
		double day2 = bigDataService.queryOrderNum(yesterday);
		String value ="";
		if(day > day2){
			value = "上升"+ZeroFormat((day-day2)/day2, 2);		
		}else{
			value = "下降"+ZeroFormat((day2-day)/day2, 2);	
		}

		String cmonth = df2.format(date);	
		calendar2.add(Calendar.MONTH, -1);
		String cmonth2 = df2.format(calendar2.getTime());
		double month = bigDataService.queryOrderNum(cmonth);
		double month2 = bigDataService.queryOrderNum(cmonth2);
		String mvalue ="";
		if(month > month2){
			mvalue = "上升"+ZeroFormat((month-month2)/month2, 2);		
		}else{
			mvalue = "下降"+ZeroFormat((month2-month)/month2, 2);
		}
		
		String year = df3.format(date);	
		calendar2.add(Calendar.YEAR, -1);
		String lyear = df2.format(calendar2.getTime());
		double yearv = bigDataService.queryOrderNum(year);
		double yearv2 = bigDataService.queryOrderNum(lyear);
		String yvalue ="";
		if(yearv > yearv2){
			yvalue = "上升"+ZeroFormat((yearv-yearv2)/yearv2, 2);		
		}else{
			yvalue = "下降"+ZeroFormat((yearv2-yearv)/yearv2, 2);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("day", day);
		map.put("day%", value);
		map.put("month", month);
		map.put("month%", mvalue);
		map.put("year", yearv);
		map.put("year%", yvalue);
		map.put("orderNum", todayNum);
		return map;
	}
	
	/**
	 * 安全隐患信息
	 * @return
	 */
	@PostMapping("/save")
	public Map<String,Object> save(@RequestBody Map<String,Object> params) {
		String type = (String)params.get("type");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-01-01 00:00:00");
		Date date = new Date();
		int safeNum = 0;
		int safeNumY = 0;

		if(null != type && "月".equals(type)){
			String month = sdf.format(date);
			safeNum = bigDataService.querySafeInfo(month);
			safeNumY = bigDataService.queryChanged(month);
		}else{
			String year = sdf2.format(date);
			safeNum = bigDataService.querySafeInfo(year);
			safeNumY = bigDataService.queryChanged(year);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("safeNum", safeNum);
		map.put("safeNumY", safeNumY);
		return map;
	}
	
	/**
	 * 设备信息
	 * @return
	 */
	@PostMapping("/equip_test")
	public Map<String,Object> equip_test(@RequestBody Map<String,Object> params) {
		String type = (String)params.get("type");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-01-01 00:00:00");
		Date date = new Date();
		Map<String,Object> map = new HashMap<String,Object>();

		if(null != type && "月".equals(type)){
			String month = sdf.format(date);
			List<BigDataDTO> list = bigDataService.queryEquipWork(month);
			int totalNum = 0;
			if(null != list && 0 != list.size()){
				for (BigDataDTO bigDataDTO : list) {
					if(null != bigDataDTO){
						totalNum = totalNum + bigDataDTO.getNum();
					}					
				}
			}			
			map.put("list", list);
			map.put("totalNum", totalNum);
		}else{
			String year = sdf2.format(date);
			List<BigDataDTO> list = bigDataService.queryEquipWork(year);
			int totalNum = 0;
			if(null != list && 0 != list.size()){
				for (BigDataDTO bigDataDTO : list) {
					if(null != bigDataDTO){
						totalNum = totalNum + bigDataDTO.getNum();
					}
				}
			}
			map.put("list", list);
			map.put("totalNum", totalNum);
		}
		double total = bigDataService.queryEquipBer(null);
		double b1 = bigDataService.queryEquipBer("0");
		double b2 = bigDataService.queryEquipBer("1");
		double ber = ZeroFormat((b1+b2)/total,2);
		map.put("ber", ber);
		return map;
	}
	
	/**
	 * 供售水产销差
	 * @return
	 */
	@PostMapping("/water_test")
	public Map<String,Object> water_test(@RequestBody Map<String,Object> params) {
		String type = (String)params.get("type");				
		Map<String,Object> map = new HashMap<String,Object>();
		
		if("月".equals(type)){	
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");	
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			String time = df.format(calendar.getTime());
			int year = Integer.parseInt(time.split("-")[0]);
			int month = Integer.parseInt(time.split("-")[1]);
			
			double flow = 0;
			double sale = 0;
			double ss = 0;
			List<BigDataDTO> list = bigDataService.querySWater(year,month,null);
			if (null != list && 0 != list.size()) {
				for (BigDataDTO bigDataDTO : list) {
					if (null != bigDataDTO) {
						flow = flow + bigDataDTO.getValue();
						sale = sale + bigDataDTO.getValue2();
						ss = ss + bigDataDTO.getValue3();
					}
				}
			}
			double cxc = 0;
			if(0 != flow){
				cxc = ZeroFormat((flow-sale-ss)/flow,2);
			}
			
			map.put("flow", flow);	
			map.put("sale", sale);	
			map.put("cxc", cxc);	
			map.put("list", list);			
		}else if("季".equals(type)){
			//
		}else{
			SimpleDateFormat df = new SimpleDateFormat("yyyy");	
			int time = Integer.parseInt(df.format(new Date()));
			
			List<BigDataDTO> blist = new ArrayList<>();
			List<SysAreaSimpleEntity> list = sysAreaService.queryAreaInfo("1");
			if(null != list && 0 != list.size()){
				double totalFlow = 0;
				double totalSale = 0;
				double totalSs = 0;
				double totalCxcl = 0;
				for (SysAreaSimpleEntity sysAreaSimpleEntity : list) {
					if(null != sysAreaSimpleEntity){
						BigDataDTO bdd = new BigDataDTO(); 
						List<BigDataDTO> list2 = bigDataService.querySWater(time,0,sysAreaSimpleEntity.getAreaId());
						if(null != list2 && 0 != list2.size()){
							double flow = 0;
							double sale = 0;
							double ss = 0; 
							for (BigDataDTO bigDataDTO : list2) {
								if(null != bigDataDTO){
									flow = flow + bigDataDTO.getValue();
									sale = sale + bigDataDTO.getValue2();
									ss = ss + bigDataDTO.getValue3();
								}								
							}
							double cxcl = 0;
							if(0 != flow){
							 cxcl = ZeroFormat((flow-sale-ss)/flow,2);
							}
							bdd.setAreaId(sysAreaSimpleEntity.getAreaId());
							bdd.setValue(flow);
							bdd.setValue2(sale);
							bdd.setValue3(cxcl);
							
							totalFlow = totalFlow + flow;
							totalSale = totalSale + sale;
							totalSs = totalSs + ss;							
							totalCxcl = ZeroFormat((totalFlow-totalSale-totalSs)/totalFlow,2);
							
							blist.add(bdd);
						}
					}					
				}
				map.put("flow", totalFlow);	
				map.put("sale", totalSale);	
				map.put("cxc", totalCxcl);	
				map.put("list", blist);				
			}
		}
		return map;
	}
	
	/**
	 * 污水信息
	 * @return
	 */
	@PostMapping("/sewage")
	public Map<String,Object> sewage() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMM");		
        Calendar calendar = Calendar.getInstance();	
        
		calendar.add(Calendar.MONTH, -1);		
		String time1 = sdf3.format(calendar.getTime());				
		
		String time11 = sdf.format(calendar.getTime());		
		String timeyear = time11.split("-")[0];
		String timemonth = time11.split("-")[1];
		
		calendar.add(Calendar.MONTH, -1);
		String time2 = sdf3.format(calendar.getTime());
		
		String time12 = sdf.format(calendar.getTime());		
		String timeyear2 = time12.split("-")[0];
		String timemonth2 = time12.split("-")[1];

		double flow = 0;//上月污水厂处理量
		List<BigDataDTO> list1 = bigDataService.querySewage(time1);
		if(null != list1 && 0 != list1.size()){
			for (BigDataDTO bigDataDTO : list1) {
				flow = flow + bigDataDTO.getValue();
			}
		}
		
		double flow2 = 0;//上上月污水厂处理量
		List<BigDataDTO> list2 = bigDataService.querySewage(time2);
		if(null != list2 && 0 != list2.size()){
			for (BigDataDTO bigDataDTO : list2) {
				flow2 = flow2 + bigDataDTO.getValue();
			}
		}
		
		String result1 = "";
		if (flow2 != 0) {
			if (flow > flow2) {
				result1 = "上升" + ZeroFormat((flow - flow2) / flow2, 2);
			} else {
				result1 = "下降" + ZeroFormat((flow2 - flow) / flow2, 2);
			}
		}
		
		double wn = 0;
		double ps = 0;
		List<BigDataDTO> plist = bigDataService.queryPSewage(timemonth,timeyear);
		if(null != plist && 0 !=plist.size()){
			for (BigDataDTO bigDataDTO : plist) {
				wn = wn + bigDataDTO.getValue2();
				ps = ps + bigDataDTO.getValue();
			}
		}
		
		double wn2 = 0;
		double ps2 = 0;
		List<BigDataDTO> plist2 = bigDataService.queryPSewage(timemonth2,timeyear2);
		if(null != plist2 && 0 !=plist2.size()){
			for (BigDataDTO bigDataDTO : plist2) {
				wn2 = wn2 + bigDataDTO.getValue2();
				ps2 = ps2 + bigDataDTO.getValue();
			}
		}
		
		String wresult = "";
		if (ps2 != 0) {
			if (ps > ps2) {
				wresult = "上升" + ZeroFormat((ps - ps2) / ps2, 2);
			} else {
				wresult = "下降" + ZeroFormat((ps2 - ps) / ps2, 2);
			}
		}
		
		String wnresult = "";
		if (wn2 != 0) {
			if (ps > wn2) {
				wnresult = "上升" + ZeroFormat((wn - wn2) / wn2, 2);
			} else {
				wnresult = "下降" + ZeroFormat((wn2 - wn) / wn2, 2);
			}
		}
		
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);
		String ytime1 = sdf2.format(calendar.getTime());
		calendar.add(Calendar.YEAR, -1);
		String ytime2 = sdf2.format(calendar.getTime());
		
		double yflow = 0;//上月污水厂处理量
		List<BigDataDTO> ylist1 = bigDataService.querySewage(ytime1);
		if(null != ylist1 && 0 != ylist1.size()){
			for (BigDataDTO bigDataDTO : ylist1) {
				yflow = yflow + bigDataDTO.getValue();
			}
		}
		
		double yflow2 = 0;//上上月污水厂处理量
		List<BigDataDTO> ylist2 = bigDataService.querySewage(ytime2);
		if(null != ylist2 && 0 != ylist2.size()){
			for (BigDataDTO bigDataDTO : ylist2) {
				yflow2 = yflow2 + bigDataDTO.getValue();
			}
		}
		
		String yresult1 = "";
		if (yflow2 != 0) {
			if (yflow > yflow2) {
				yresult1 = "上升" + ZeroFormat((yflow - yflow2) / yflow2, 2);
			} else {
				yresult1 = "下降" + ZeroFormat((yflow2 - yflow) / yflow2, 2);
			}
		}	
		
		double yps = 0;
		List<BigDataDTO> yplist = bigDataService.queryYPSewage(ytime1);
		if(null != yplist && 0 !=yplist.size()){
			for (BigDataDTO bigDataDTO : yplist) {
				yps = yps + bigDataDTO.getValue();
			}
		}
		
		double yps2 = 0;
		List<BigDataDTO> yplist2 = bigDataService.queryYPSewage(ytime2);
		if(null != yplist2 && 0 != yplist2.size()){
			for (BigDataDTO bigDataDTO : yplist2) {
				yps2 = yps2 + bigDataDTO.getValue();
			}
		}
		
		String ywresult = "";
		if (yps2 != 0) {
			if (yps > yps2) {
				ywresult = "上升" + ZeroFormat((yps - yps2) / yps2, 2);
			} else {
				ywresult = "下降" + ZeroFormat((yps2 - yps) / yps2, 2);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mcll", flow);
		map.put("mcll%", result1);
		map.put("ycll", yflow);
		map.put("ycll%", yresult1);
		map.put("mpsl", ps);
		map.put("mpsl%", wresult);
		map.put("ypsl", ywresult);
		map.put("ypsl%", yps);
		map.put("wnczl", wn);
		map.put("wnczl%", wnresult);
		return map;
	}
	
	/**
	 * 成本分析
	 * @param params
	 */
	@PostMapping("/cost_test")
	public Map<String,Object> cost_test(@RequestBody Map<String,Object> params){
		String type = (String)params.get("type");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Map<String,Object> map = new HashMap<String,Object>();
        Calendar calendar = Calendar.getInstance();	     		
		if("月".equals(type)){
			calendar.add(Calendar.MONTH, -1);		
			String time = sdf.format(calendar.getTime());
			String time1 = time.split("-")[0];
			String time2 = time.split("-")[1];
			List<BigDataDTO> list1 = bigDataService.querySCost(time2, time1);
			List<BigDataDTO> list2 = bigDataService.queryWCost(time2, time1);
			map.put("supply", list1);
			map.put("sewage", list2);
		}else if("季".equals(type)){
			calendar.add(Calendar.MONTH, -4);		
			String time = sdf.format(calendar.getTime());
			String time1 = time.split("-")[0];
			String time2 = time.split("-")[1];
			List<BigDataDTO> list1 = bigDataService.querySCost(time2, time1);
			List<BigDataDTO> list2 = bigDataService.queryWCost(time2, time1);
			map.put("supply", list1);
			map.put("sewage", list2);
		}else if("年".equals(type)){	
			String time = sdf.format(calendar.getTime());
			String time1 = time.split("-")[0];			
			List<BigDataDTO> list1 = bigDataService.queryYearSCost(time1);
			List<BigDataDTO> list2 = bigDataService.queryYearWCost(time1);
			map.put("supply", list1);
			map.put("sewage", list2);
		}
		
		return map;
	}
	
	public static double ZeroFormat(double num, int n) {
		BigDecimal bigDecimal = new BigDecimal(num);
		// DecimalFormat ff = new DecimalFormat("#.0000"); //保留四位小数
		// double result = Double.valueOf(ff.format(num));
		// return result;
		return bigDecimal.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		// setscale(n,BigDecimal.ROUND_HALF_UP).doubleValue;
	}
	
	@PostMapping("/order")
	public Map<String,Object> order(@RequestBody Map<String,Object> params){
		String time = (String)params.get("time");
		Map<String, Object> map = bigDataService.order(time);
		return map;
	}
	
	@PostMapping("/safe")
	public Map<String,Object> safe(@RequestBody Map<String,Object> params){
		String time = (String)params.get("time");
		Map<String, Object> map = bigDataService.safe(time);
		return map;
	}
	
	@PostMapping("/waterQuality")
	public Map<String,Object> waterQuality(@RequestBody Map<String,Object> params){
		String siteId = (String)params.get("siteId");
		String[] site = siteId.split(",");
		List<String> list = Arrays.asList(site);
		Map<String, Object> map = bigDataService.waterQuality(list);
		return map;
	}
	
	@PostMapping("/equip")
	public Map<String,Object> equip(@RequestBody Map<String,Object> params){
		String type = (String)params.get("type");
		Map<String, Object> map = bigDataService.equip(type);
		return map;
	}
	
	@PostMapping("/water")
	public Map<String,Object> water(@RequestBody Map<String,Object> params){
		String time = (String)params.get("time");
		Map<String, Object> map = bigDataService.water(time);
		return map;
	}
	
	@PostMapping("/supply")
	public Map<String,Object> supply(){
		Map<String, Object> map = bigDataService.supply();
		return map;
	}
	
	@PostMapping("/cost")
	public Map<String,Object> cost(@RequestBody Map<String,Object> params){
		String time = (String)params.get("time");
		String siteId = (String)params.get("siteId");
		Map<String, Object> map = bigDataService.cost(time,siteId);
		return map;
	}
}
