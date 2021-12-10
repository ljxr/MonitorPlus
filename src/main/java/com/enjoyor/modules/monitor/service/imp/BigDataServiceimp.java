package com.enjoyor.modules.monitor.service.imp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyor.modules.monitor.controller.BigDataController;
import com.enjoyor.modules.monitor.dao.BigDataDao;
import com.enjoyor.modules.monitor.entity.BigDataDTO;
import com.enjoyor.modules.monitor.service.BigDataService;
import com.enjoyor.modules.sys.dao.SysAreaDao;
import com.enjoyor.modules.sys.dao.SysDictionaryDao;
import com.enjoyor.modules.sys.entity.SysAreaSimpleEntity;
import com.enjoyor.modules.sys.entity.SysDictionaryEntity;

@Service("bigDataService")
public class BigDataServiceimp implements BigDataService{

	@Autowired
	private BigDataDao bigDataDao;
	
	@Autowired
	private SysDictionaryDao sysDictionaryDao;
	
	@Autowired
	private SysAreaDao sysAreaDao;
	
	@Override
	public List<BigDataDTO> queryBigUser() {
		// TODO Auto-generated method stub
		return bigDataDao.queryBigUser();
	}

	@Override
	public List<BigDataDTO> queryCCS(String time) {
		// TODO Auto-generated method stub
		return bigDataDao.queryCCS(time);
	}

	@Override
	public List<BigDataDTO> queryYS(String time) {
		// TODO Auto-generated method stub
		return bigDataDao.queryYS(time);
	}

	@Override
	public List<BigDataDTO> queryWS(String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BigDataDTO> queryWQ() {
		// TODO Auto-generated method stub
		return bigDataDao.queryWQ();
	}

	@Override
	public List<BigDataDTO> queryDiameter() {
		// TODO Auto-generated method stub
		return bigDataDao.queryDiameter();
	}

	@Override
	public List<BigDataDTO> queryMaterial() {
		// TODO Auto-generated method stub
		return bigDataDao.queryMaterial();
	}

	@Override
	public List<BigDataDTO> queryPArea() {
		// TODO Auto-generated method stub
		return bigDataDao.queryPArea();
	}

	@Override
	public int queryTotalLength() {
		// TODO Auto-generated method stub
		return bigDataDao.queryTotalLength();
	}

	@Override
	public int queryPipeInsCounts(String time) {
		// TODO Auto-generated method stub
		return bigDataDao.queryPipeInsCounts(time);
	}

	@Override
	public List<BigDataDTO> queryTodayOrderNum(String time) {
		// TODO Auto-generated method stub
		return bigDataDao.queryTodayOrderNum(time);
	}

	@Override
	public int queryOrderNum(String time) {
		// TODO Auto-generated method stub
		return bigDataDao.queryOrderNum(time,time);
	}

	@Override
	public int querySafeInfo(String time) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryChanged(String time) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BigDataDTO> queryEquipWork(String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryEquipBer(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BigDataDTO> querySWater(int year,int month,String areaId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BigDataDTO> querySewage(String time) {
		// TODO Auto-generated method stub
		return bigDataDao.querySewage(time);
	}


	@Override
	public List<BigDataDTO> queryYPSewage(String time) {
		// TODO Auto-generated method stub
		return bigDataDao.queryYPSewage(time);
	}

	@Override
	public List<BigDataDTO> queryPSewage(String time, String time2) {
		// TODO Auto-generated method stub
		return bigDataDao.queryPSewage(time, time2);
	}

	@Override
	public List<BigDataDTO> querySCost(String time, String time2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BigDataDTO> queryWCost(String time, String time2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BigDataDTO> queryYearSCost(String time2) {
		// TODO Auto-generated method stub
		return bigDataDao.queryYearSCost(time2);
	}

	@Override
	public List<BigDataDTO> queryYearWCost(String time2) {
		// TODO Auto-generated method stub
		return bigDataDao.queryYearWCost(time2);
	}

	@Override
	public Map<String,Object> order(String time) {		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String now = "";
        String today = "";
        String yetime = "";
		String yeday = "";
		String tomonth = "";
        
		if ("月".equals(time)) {
			// 月 ——> 获取今日当前时间、0点时间和昨日当前时间、0点时间
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			now = sdf.format(calendar.getTime());
			today = sdf2.format(calendar.getTime());

			calendar.add(Calendar.DATE, -1);
			yetime = sdf.format(calendar.getTime());
			yeday = sdf2.format(calendar.getTime());

			// 获取本月各个片区的工单总数和及时率信息
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-01 00:00:00");
			calendar.add(Calendar.DATE, +1);
			tomonth = sdf3.format(calendar.getTime());
		} else {
			// 年 ——> 获取本月当前时间、上月当前时间
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-01 00:00:00");
			now = sdf.format(calendar.getTime());
			today = sdf2.format(calendar.getTime());
			
			calendar.add(Calendar.MONTH, -1);
			yetime = sdf.format(calendar.getTime());
			yeday = sdf2.format(calendar.getTime());
			
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-01-01 00:00:00");
			tomonth = sdf3.format(calendar.getTime());
		}
					
		// 获取本月时间内的工单总数、上月当前时间内的工单总数，环比算法
		int todayNum = bigDataDao.queryOrderNum(today, now);// 本月
		int yeNum = bigDataDao.queryOrderNum(yeday, yetime);// 上个月

		String tag = "";// 上升下降箭头
		double mom = 0; // 环比值
		if (todayNum > yeNum) {
			tag = "up";
			mom = ZeroFormat(Double.valueOf(((todayNum - yeNum)) / Double.valueOf(yeNum))*100, 2);
		} else if (todayNum < yeNum) {
			tag = "down";
			mom = ZeroFormat(Double.valueOf(((yeNum - todayNum)) / Double.valueOf(yeNum))*100, 2);
		}

		// 获取本年各个片区的工单总数和及时率信息 
		List<BigDataDTO> list1 = bigDataDao.queryBusinessOrderNum(tomonth, null);// 营业厅全部工单
		List<BigDataDTO> list2 = bigDataDao.queryBusinessOrderNum(tomonth, "0");// 营业厅及时工单
		if (null != list1 && 0 != list1.size()) {
			BigDataDTO bdd = new BigDataDTO();
			if (null != list2 && 0 != list2.size()) {
				int totalOrder = 0;
				int jsOrder = 0;
				for (BigDataDTO bigDataDTO : list1) {
					if (null != bigDataDTO) {
						for (BigDataDTO bigDataDTO2 : list2) {
							if (null != bigDataDTO2 && bigDataDTO.getAreaName().equals(bigDataDTO2.getAreaName())) {
								bigDataDTO.setValue2(ZeroFormat((Double.valueOf(bigDataDTO2.getNum()) / Double.valueOf(bigDataDTO.getNum())) * 100, 2));
								jsOrder = jsOrder + bigDataDTO2.getNum();
							}
						}
						// 进行合计
						totalOrder = totalOrder + bigDataDTO.getNum();
					}
				}

				bdd.setName("合计");
				bdd.setNum(totalOrder);
				bdd.setValue2(ZeroFormat((Double.valueOf(jsOrder) / Double.valueOf(totalOrder)) * 100, 2));
			} else {
				for (BigDataDTO bigDataDTO : list1) {
					bigDataDTO.setValue2(0.0);
				}
			}
			list1.add(bdd);
		}
		// 赋值
		Map<String, Object> map = new HashMap<>();
		map.put("totalNum", todayNum);
		map.put("tag", tag);
		map.put("mom", mom);
		map.put("list", list1);
		
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

	@Override
	public Map<String, Object> safe(String time) {
		Calendar calendar = Calendar.getInstance();
		String month = "";
		if("月".equals(time)){			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
			month = sdf.format(calendar.getTime());					
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-01-01 00:00:00");
			month = sdf.format(calendar.getTime());	
		}				
		int snum = 0;//本月发现隐患数
		int cnum = 0;//整改数
		//按系统类型发现隐患数、整改数
		List<SysDictionaryEntity> diclist = sysDictionaryDao.queryDicList("26");// 获取安全隐患系统类型
		List<BigDataDTO> slist = bigDataDao.querySafeInfo(month);// 发现隐患数
		List<BigDataDTO> clist = bigDataDao.queryChanged(month);// 隐患整改数
		if (null != diclist && 0 != diclist.size()) {
			for (SysDictionaryEntity sysDictionaryEntity : diclist) {
				if (null != sysDictionaryEntity) {					
					if (null != slist && 0 != slist.size()) {
						boolean flag = false;						
						j: for (BigDataDTO bigDataDTO : slist) {
							if (null != bigDataDTO) {								
								if (sysDictionaryEntity.getName().equals(bigDataDTO.getName())) {
									snum = snum + bigDataDTO.getNum();
									flag = true;
									break;
								}								
							} else {
								continue j;
							}
						}
						if (!flag) {
							BigDataDTO b = new BigDataDTO();
							b.setName(sysDictionaryEntity.getName());
							b.setNum(0);
							slist.add(b);
						}
					}
					
					if (null != clist && 0 != clist.size()) {
						boolean flag = false;						
						j: for (BigDataDTO bigDataDTO : clist) {
							if (null != bigDataDTO) {							
								if (sysDictionaryEntity.getName().equals(bigDataDTO.getName())) {
									cnum = cnum + bigDataDTO.getNum();
									flag = true;
									break;
								}								
							} else {
								continue j;
							}
						}
						if (!flag) {
							BigDataDTO b = new BigDataDTO();
							b.setName(sysDictionaryEntity.getName());
							b.setNum(0);
							clist.add(b);
						}
					}					
				} else {
					continue;
				}
			}
		}
		//隐患整改率 整改数/发现隐患数 %
		double num = 0;
		if(0 != snum){
			num = ZeroFormat((Double.valueOf(cnum) / Double.valueOf(snum)) * 100, 2);	
		}
		//赋值
		Map<String, Object> map = new HashMap<>();
		map.put("sum", snum);
		map.put("cnum", cnum);
		map.put("num", num);
		map.put("list1", slist);
		map.put("list2", clist);
		return map;
	}

	@Override
	public Map<String, Object> waterQuality(List<String> siteId) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		calendar.add(Calendar.DATE, -1);
		String time = sdf.format(calendar.getTime());
		//出厂水合格率
		List<BigDataDTO> clist = bigDataDao.queryCCS(time);
		while(null == clist || 0 == clist.size()){
			calendar.add(Calendar.DATE, -1);
			String time2 = sdf.format(calendar.getTime());
			clist = bigDataDao.queryCCS(time2);
		}
		int num = 0;
		int totalNum = 0;			
		for (BigDataDTO bigDataDTO : clist) {
			if (null != bigDataDTO) {
				if (1 != bigDataDTO.getNum()) {
					num = num + bigDataDTO.getNum();
				}
				totalNum = totalNum + bigDataDTO.getNum();
			}
		}
		
		double ccs = 0;
		if (0 != totalNum) {
			ccs = ZeroFormat((Double.valueOf(num) / Double.valueOf(totalNum)) * 100, 2);
		}
		//管网水合格率
		List<BigDataDTO> wlist = bigDataDao.queryQWS(time);
		int num2 = 0;
		int totalNum2 = 0;
		if(null != wlist && 0 != wlist.size()){			
			for (BigDataDTO bigDataDTO : wlist) {
				if(null != bigDataDTO){
					if(1 != bigDataDTO.getNum()){
						num2 =num2 + bigDataDTO.getNum();
					}
					totalNum2 = totalNum2 + bigDataDTO.getNum();
				}
			}
		}
		
		double qws = 0;
		if (0 != totalNum2) {
			qws = ZeroFormat((Double.valueOf(num2) / Double.valueOf(totalNum2)) * 100, 2);
		}
		
		List<List<BigDataDTO>> result = new ArrayList<List<BigDataDTO>>();
		//水质情况列表
		for (String id : siteId) {
			List<BigDataDTO> list = bigDataDao.queryWS(time, id);
			if(null != list && 0 != list.size()){
			  result.add(list);
			}
		}	
		
		//赋值	
		Map<String, Object> map = new HashMap<>();
		map.put("ccs", ccs);
		map.put("gws", qws);
		map.put("list", result);
		return map;
	}

	@Override
	public Map<String, Object> equip(String type) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		String time = sdf.format(calendar.getTime());
		String time3 = sdf2.format(calendar.getTime());
		calendar.add(Calendar.MONTH, -1);
		String time2 = sdf.format(calendar.getTime());
		String equipType = "";
		if("供".equals(type)){
			equipType = "供水";
		}else if("排".equals(type)){
			equipType = "排水";
		}else{
			equipType = "污水";
		}

		int gz = bigDataDao.queryEquipBer(time,equipType, "2");//本月故障设备数
		int all = bigDataDao.queryEquipBer(time,equipType, null);//本月全部设备数
		double whl = 0;//设备完好率
		int za = 0;//正常设备数
		if(0 != all){
			whl = ZeroFormat((Double.valueOf(all-gz) / Double.valueOf(all)) * 100, 2);
			za = all - gz;
		}
		
		String tag = "";
		double mom = 0;//本月与上月的环比
		int sgz = bigDataDao.queryEquipBer(time2,equipType, "2");//上月故障设备数
		int sall = bigDataDao.queryEquipBer(time2,equipType, null);//上月全部设备数
		if(0 != all){
			int v = sall - sgz; //上月正常设备数
			if(za > v && 0 != v){
				tag = "up";
				mom = ZeroFormat((Double.valueOf(za-v) / Double.valueOf(v)) * 100, 2);
			}else if(za < v && 0 != v){
				tag = "down";
				mom = ZeroFormat((Double.valueOf(v-za) / Double.valueOf(v)) * 100, 2);
			}
		}
		
		int by = bigDataDao.queryEquipWork(time3, "0", null,equipType);//保养任务总个数
		int byywc = bigDataDao.queryEquipWork(time3, "0", "4",equipType);//保养任务已完成个数
		double byvalue = 0;//完成率
		if(0 != by){
		 byvalue = ZeroFormat((Double.valueOf(byywc) / Double.valueOf(by)) * 100, 2);
		}
		
		int xj = bigDataDao.queryEquipWork(time3, "1", null,equipType);//巡检任务总个数
		int xjywc = bigDataDao.queryEquipWork(time3, "1", "4",equipType);//巡检任务已完成个数
		double xjvalue = 0;//完成率
		if(0 != xj){
			xjvalue = ZeroFormat((Double.valueOf(xjywc) / Double.valueOf(xj)) * 100, 2);
		}
		
		int wx = bigDataDao.queryEquipWork(time3, "2", null,equipType);//维修任务总个数
		int wxywc = bigDataDao.queryEquipWork(time3, "2", "4",equipType);//维修任务已完成个数
		double wxvalue = 0;//完成率
		if(0 != wx){
			wxvalue = ZeroFormat((Double.valueOf(wxywc) / Double.valueOf(wx)) * 100, 2);
		}
		
		//赋值	
		Map<String, Object> map = new HashMap<>();
		map.put("whl", whl);
		map.put("tag", tag);
		map.put("mom", mom);
		map.put("by", by);
		map.put("byvalue", byywc);
		map.put("xj", xj);
		map.put("xjvalue", xjywc);
		map.put("wx", wx);
		map.put("wxvalue", wxywc);
		return map;
	}

	@Override
	public Map<String, Object> water(String time) {
		// 赋值
		Map<String, Object> map = new HashMap<>();
		Calendar calendar = Calendar.getInstance();
		double tcxcl = 0;
		double tcxcl2 = 0;
		double mom = 0;
		String tag = "";
		if ("月".equals(time)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
			calendar.add(Calendar.MONTH, -1);
			String time1 = sdf.format(calendar.getTime());// 上月时间
			List<BigDataDTO> list = bigDataDao.querySWater(time1, null);
			double cxcl = 0;
			if (null != list && 0 != list.size()) {
				for (BigDataDTO bigDataDTO : list) {
					if (null != bigDataDTO.getValue3()) {
						cxcl = cxcl + bigDataDTO.getValue3();
					}
				}
				tcxcl = ZeroFormat(cxcl / list.size(), 2);
			}

			calendar.add(Calendar.MONTH, -1);
			String time2 = sdf.format(calendar.getTime());// 上上月时间
			List<BigDataDTO> list2 = bigDataDao.querySWater(time2, null);
			double cxcl2 = 0;
			if (null != list2 && 0 != list2.size()) {
				for (BigDataDTO bigDataDTO : list2) {
					if (null != bigDataDTO.getValue3()) {
						cxcl2 = cxcl2 + bigDataDTO.getValue3();
					}
				}
				tcxcl2 = ZeroFormat(cxcl2 / list2.size(), 2);
			}
			if (0 != tcxcl2 && tcxcl > tcxcl2) {
				mom = ZeroFormat((tcxcl - tcxcl2) * 100, 2);
				tag = "up";
			} else if (0 != tcxcl2 && tcxcl2 > tcxcl) {
				mom = ZeroFormat((tcxcl2 - tcxcl) * 100, 2);
				tag = "down";
			}
			map.put("list", list);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-01-01 00:00:00");
			String year = sdf.format(calendar.getTime());
			String syear = sdf2.format(calendar.getTime());
			calendar.add(Calendar.YEAR, -1);
			String year2 = sdf.format(calendar.getTime());
			String syear2 = sdf2.format(calendar.getTime());
			List<BigDataDTO> tolist = new ArrayList<BigDataDTO>();
			List<SysAreaSimpleEntity> list = sysAreaDao.queryAreaInfo("1");
			double totalcxc = 0;
			double totalcxc2 = 0;
			if (null != list && 0 != list.size()) {
				for (SysAreaSimpleEntity sysAreaSimpleEntity : list) {
					BigDataDTO bdd = new BigDataDTO();
					List<BigDataDTO> list2 = bigDataDao.querySWater2(syear, year, sysAreaSimpleEntity.getAreaName());
					if (null != list2 && 0 != list2.size()) {
						double gsl = 0;
						double ssl = 0;
						double cxcl = 0;
						for (BigDataDTO bigDataDTO : list2) {
							if (null != bigDataDTO.getValue2()) {
								ssl = ssl + bigDataDTO.getValue2();
							}
							if (null != bigDataDTO.getValue3()) {
								cxcl = cxcl + bigDataDTO.getValue3();
							}
							if (null != bigDataDTO.getValue()) {
								gsl = gsl + bigDataDTO.getValue();
							}
							// gsl = gsl + bigDataDTO.getValue();
							// ssl = ssl + bigDataDTO.getValue2();
							// cxcl = cxcl + bigDataDTO.getValue3();
						}
						double fcxcl = ZeroFormat(cxcl / list2.size(), 2);// 产销差平均值
						bdd.setValue(gsl);
						bdd.setValue2(ssl);
						bdd.setValue3(fcxcl);
						bdd.setAreaName(sysAreaSimpleEntity.getAreaName());

						totalcxc = totalcxc + fcxcl;
						tolist.add(bdd);
					}

					List<BigDataDTO> list3 = bigDataDao.querySWater2(syear2, year2, sysAreaSimpleEntity.getAreaName());
					if (null != list3 && 0 != list3.size()) {
						double cxcl = 0;
						for (BigDataDTO bigDataDTO : list3) {
							if (null != bigDataDTO.getValue3()) {
								cxcl = cxcl + bigDataDTO.getValue3();
							}
						}
						double fcxcl = ZeroFormat(cxcl / list3.size(), 2);// 产销差平均值
						totalcxc2 = totalcxc2 + fcxcl;
					}
				}
				tcxcl = ZeroFormat(totalcxc / list.size(), 2);
				tcxcl2 = ZeroFormat(totalcxc2 / list.size(), 2);
				if (0 != tcxcl2 && tcxcl2 > tcxcl) {
					mom = ZeroFormat((tcxcl2 - tcxcl) * 100, 2);
					tag = "down";
				} else if (0 != tcxcl2 && tcxcl > tcxcl2) {
					mom = ZeroFormat((tcxcl - tcxcl2) * 100, 2);
					tag = "up";
				}
				
				map.put("list", tolist);
			}
		}

		map.put("cxcl", tcxcl);
		map.put("mom", mom);
		map.put("tag", tag);
		return map;
	}

	@Override
	public Map<String, Object> supply() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");		
		Map<String, Object> map = new HashMap<>();
		//上月供水、售水		
		calendar.add(Calendar.MONTH, -1);
		String lasttime = sdf.format(calendar.getTime());
		double totalMSupply =0;//上月总供水
		double totalMSale =0;//上月总售水
		List<BigDataDTO> list1 = bigDataDao.querySWater(lasttime, null);
		if(null != list1 && 0 != list1.size()){
			for (BigDataDTO bigDataDTO : list1) {
				if(null != bigDataDTO.getValue()){
					totalMSupply = totalMSupply + bigDataDTO.getValue();
				}
				if(null != bigDataDTO.getValue2()){
					totalMSale = totalMSale + bigDataDTO.getValue2();
				}			
			}
		}
		
		//上上月供水、售水
		calendar.add(Calendar.MONTH, -1);
		String lasttime2 = sdf.format(calendar.getTime());
		double totalMSupplyLast =0;//上月总供水
		double totalMSaleLast =0;//上月总售水
		List<BigDataDTO> list2 = bigDataDao.querySWater(lasttime2, null);
		if(null != list2 && 0 != list2.size()){
			for (BigDataDTO bigDataDTO : list2) {
				if(null != bigDataDTO.getValue()){
					totalMSupplyLast = totalMSupplyLast + bigDataDTO.getValue();
				}
				if(null != bigDataDTO.getValue2()){
					totalMSaleLast = totalMSaleLast + bigDataDTO.getValue2();
				}			
			}
		}		
		//月供水环比
		String tag1 = "";
		double mom1 = 0;
		if(0.0 != totalMSupplyLast){
			if(totalMSupplyLast > totalMSupply){
				tag1 = "down";
				mom1 = ZeroFormat(((totalMSupplyLast - totalMSupply) / totalMSupplyLast) * 100, 2);
			}else{
				tag1 = "up";
				mom1 = ZeroFormat(((totalMSupply - totalMSupplyLast) / totalMSupplyLast) * 100, 2);
			}
		}
		
		//月售水环比
		String tag2 = "";
		double mom2 = 0;
		if(0.0 != totalMSaleLast){
			if(totalMSaleLast > totalMSale){
				tag2 = "down";
				mom2 = ZeroFormat(((totalMSaleLast - totalMSale) / totalMSaleLast) * 100, 2);
			}else{
				tag2 = "up";
				mom2 = ZeroFormat(((totalMSale - totalMSaleLast) / totalMSaleLast) * 100, 2);
			}
		}
		
		
		calendar.add(Calendar.MONTH, +2);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-01-01 00:00:00");
		String year1 = sdf2.format(calendar.getTime());
		String year2 = sdf3.format(calendar.getTime());
		calendar.add(Calendar.YEAR, -1);
		String year3 = sdf2.format(calendar.getTime());
		String year4 = sdf3.format(calendar.getTime());
		double totalSupply = 0;//总本年供水量
		double totalSale = 0;//总本年售水量
		double totalSupplyLast = 0;//总去年供水量
		double totalSaleLast = 0;//总去年售水量
		//今年供水、售水
		List<SysAreaSimpleEntity> list = sysAreaDao.queryAreaInfo("1");
		if(null != list && 0 != list.size()){
			for (SysAreaSimpleEntity sysAreaSimpleEntity : list) {
				double supply = 0;
				double sale = 0;
				List<BigDataDTO> blist = bigDataDao.querySWater2(year2, year1, sysAreaSimpleEntity.getAreaName());
				if(null != blist && 0 != blist.size()){
					for (BigDataDTO bigDataDTO : blist) {
						if(null != bigDataDTO.getValue()){
							supply = supply + bigDataDTO.getValue();
						}
						if(null != bigDataDTO.getValue2()){
							sale = sale + bigDataDTO.getValue2();
						}
					}
					totalSupply = totalSupply + supply;
					totalSale = totalSale + sale;
				}
				
				//去年供水、售水
				double supplyLast = 0;
				double saleLast = 0;
				List<BigDataDTO> blist2 = bigDataDao.querySWater2(year4, year3, sysAreaSimpleEntity.getAreaName());
				if(null != blist2 && 0 != blist2.size()){
					for (BigDataDTO bigDataDTO : blist2) {
						if(null != bigDataDTO.getValue()){
							supplyLast = supplyLast + bigDataDTO.getValue();
						}
						if(null != bigDataDTO.getValue2()){
							saleLast = saleLast + bigDataDTO.getValue2();
						}
					}
					totalSupplyLast = totalSupplyLast + supplyLast;
					totalSaleLast = totalSaleLast + saleLast;
				}
			}
		}					
		//年供水环比
		String yeartag1 = "";
		double yearmom1 = 0;
		if(totalSupplyLast > totalSupply){
			yeartag1 = "down";
			yearmom1 = ZeroFormat(((totalSupplyLast - totalSupply) / totalSupplyLast) * 100, 2);
		}else{
			yeartag1 = "up";
			yearmom1 = ZeroFormat(((totalSupply - totalSupplyLast) / totalSupplyLast) * 100, 2);
		}		
		//年售水环比
		String yeartag2 = "";
		double yearmom2 = 0;
		if(totalSaleLast > totalSale){
			yeartag2 = "down";
			yearmom2 = ZeroFormat(((totalSaleLast - totalSale) / totalSaleLast) * 100, 2);
		}else{
			yeartag2 = "up";
			yearmom2 = ZeroFormat(((totalSale - totalSaleLast) / totalSaleLast) * 100, 2);
		}
		
		map.put("gsl", totalMSupply);
		map.put("ssl", totalMSale);
		map.put("tag1", tag1);
		map.put("mom1", mom1);
		map.put("tag2", tag2);
		map.put("mom2", mom2);
		map.put("yeargsl", totalSupply);
		map.put("yearssl", totalSale);
		map.put("yeartag1", yeartag1);
		map.put("yearmom1", yearmom1);
		map.put("yeartag2", yeartag2);
		map.put("yearmom2", yearmom2);
		return map;
	}

	@Override
	public Map<String, Object> cost(String time, String siteId) {
		Map<String, Object> map = new HashMap<>();
		Calendar calendar = Calendar.getInstance();
		// 上月成本分析
		if ("月".equals(time)) {
			if ("水厂".equals(siteId)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
				calendar.add(Calendar.MONTH, -1);
				String month = sdf.format(calendar.getTime());
				List<BigDataDTO> list1 = bigDataDao.querySCost(month);
				map.put("list", list1);
			} else if ("清源".equals(siteId)) {

			} else if ("净源".equals(siteId)) {

			}

		} else {
			if ("水厂".equals(siteId)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-01-01 00:00:00");
				String month = sdf.format(calendar.getTime());
				String month2 = sdf2.format(calendar.getTime());
				List<BigDataDTO> list1 = bigDataDao.querySCostYear(month2, month);
				map.put("list", list1);
			} else if ("清源".equals(siteId)) {

			} else if ("净源".equals(siteId)) {

			}
		}

		// 今年成本分析

		return map;
	}
}
