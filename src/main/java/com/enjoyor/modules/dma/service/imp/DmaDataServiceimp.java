package com.enjoyor.modules.dma.service.imp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyor.modules.dma.dao.DmaAreaDao;
import com.enjoyor.modules.dma.dao.DmaDataDao;
import com.enjoyor.modules.dma.entity.Dma111DTO;
import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaDataEntity;
import com.enjoyor.modules.dma.entity.DmaMonthCjlDTO;
import com.enjoyor.modules.dma.entity.DmaQueryByMonthDTO;
import com.enjoyor.modules.dma.service.DmaDataService;
import com.google.common.base.Objects;

@Service("dmaDataService")
public class DmaDataServiceimp implements DmaDataService {

	@Autowired
	private DmaAreaDao dmaAreaDao;
	@Autowired
	private DmaDataDao dmaDataDao;

	@Override
	public List<DmaDataEntity> queryDayList(String dareaId, String stime, String etime) {
		return dmaDataDao.queryDayList(dareaId, stime, etime);
	}

	@Override
	public List<DmaDataEntity> queryWeekList(String dareaId, String stime, String etime) {
		return dmaDataDao.queryWeekList(dareaId, stime, etime);
	}

	@Override
	public List<DmaDataEntity> queryMonthList(String dareaId, String stime, String etime) {
		return dmaDataDao.queryMonthList(dareaId, stime, etime);
	}

	@Override
	public List<DmaDataEntity> queryYearList(String dareaId, String stime, String etime) {
		return dmaDataDao.queryYearList(dareaId, stime, etime);
	}

	@Override
	public List<DmaQueryByMonthDTO> queryByMonth() throws ParseException {
		// 保留两位小数
		DecimalFormat df = new DecimalFormat("#.##");
		// 查询最近两个月所有dma分区的抄见量（watermonthdata表）
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastMonth = formatters.format(today.minusMonths(1)) + "-01";
		String lastLastMonth = formatters.format(today.minusMonths(2)) + "-01";
		List<DmaMonthCjlDTO> allDataList = dmaDataDao.queryCjlByBook(lastLastMonth, lastLastMonth);
		// 所有dma分区
		List<DmaAreaEntity> allAreaList = dmaAreaDao.queryAll();
		
		// 前两个月所有dma分区流入量（table:waterday, column:DAREA_FLOWIN）
		// 上个月
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(sdf.parse(lastMonth));
		cal1.set(Calendar.DATE, cal1.getActualMaximum(Calendar.DATE));
		Date lastDateOfLastMonth = cal1.getTime();
		String lastMonthLastDay = sdf.format(lastDateOfLastMonth);
		List<Map<String, Object>> mapList1 = dmaDataDao.queryMonthSumFlow(lastMonth, lastMonthLastDay);
		// 上上个月
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(sdf.parse(lastLastMonth));
		cal2.set(Calendar.DATE, cal2.getActualMaximum(Calendar.DATE));
		Date lastDateOfPrevMonth1 = cal2.getTime();
		String lastLastMonthLastDay = sdf.format(lastDateOfPrevMonth1);
		List<Map<String, Object>> mapList2 = dmaDataDao.queryMonthSumFlow(lastLastMonth, lastLastMonthLastDay);
		
		// 映射dma分区属性到返回类中，并赋值抄见量等属性
		List<DmaQueryByMonthDTO> allList = new ArrayList<DmaQueryByMonthDTO>();
		for (DmaAreaEntity area : allAreaList) {
			// 获取上个月和上上个月的数据并赋值
			DmaMonthCjlDTO lastLastMonthDTO = new DmaMonthCjlDTO();
			lastLastMonthDTO.setTime(lastLastMonth);
			DmaMonthCjlDTO lastMonthDTO = new DmaMonthCjlDTO();
			lastMonthDTO.setTime(lastMonth);
			for (DmaMonthCjlDTO data : allDataList) {
				if (data.getDareaId().equals(area.getDareaId()) && data.getTime().equals(lastLastMonth)) {
					calendar.setTime(sdf.parse(lastLastMonth));
					int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
					BigDecimal dayCjl = new BigDecimal(data.getCjl()/days);  
					dayCjl = dayCjl.setScale(1, RoundingMode.HALF_UP);
					lastLastMonthDTO.setDareaId(data.getDareaId());
					lastLastMonthDTO.setDayCjl(dayCjl.toString());
					lastLastMonthDTO.setCjl(data.getCjl());
					lastLastMonthDTO.setUsers(data.getUsers());
				}
				if (data.getDareaId().equals(area.getDareaId()) && data.getTime().equals(lastMonth)) {
					calendar.setTime(sdf.parse(lastMonth));
					int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
					BigDecimal dayCjl = new BigDecimal(data.getCjl()/days);  
					dayCjl = dayCjl.setScale(1, RoundingMode.HALF_UP);
					lastMonthDTO.setDareaId(data.getDareaId());
					lastMonthDTO.setDayCjl(dayCjl.toString());
					lastMonthDTO.setCjl(data.getCjl());
					lastMonthDTO.setUsers(data.getUsers());
				}
			}
			for (Map<String, Object> map1 : mapList1) {
				if(Objects.equal(map1.get("DAREA_ID"), lastMonthDTO.getDareaId())){
					lastMonthDTO.setMonthWaterSupply(map1.get("SUMIN").toString());
					lastMonthDTO.setDayWaterSupply(df.format(Double.valueOf(lastMonthDTO.getMonthWaterSupply()) / cal1.getActualMaximum(Calendar.DATE)));
					Double cxc = Double.valueOf(lastMonthDTO.getMonthWaterSupply()) - Double.valueOf(lastMonthDTO.getCjl()) -  Double.valueOf(map1.get("SUMOUT").toString());
					if(0 != Double.valueOf(lastMonthDTO.getMonthWaterSupply())){
						lastMonthDTO.setCxc(df.format(cxc));
					}
				}
			}
			for (Map<String, Object> map2 : mapList2) {
				if(Objects.equal(map2.get("DAREA_ID"), lastLastMonthDTO.getDareaId())){
					lastLastMonthDTO.setMonthWaterSupply(map2.get("SUMIN").toString());
					lastLastMonthDTO.setDayWaterSupply(df.format(Double.valueOf(lastLastMonthDTO.getMonthWaterSupply()) / cal2.getActualMaximum(Calendar.DATE)));
					Double cxc = Double.valueOf(lastLastMonthDTO.getMonthWaterSupply()) - Double.valueOf(lastLastMonthDTO.getCjl()) -  Double.valueOf(map2.get("SUMOUT").toString());
					if(0 != Double.valueOf(lastLastMonthDTO.getMonthWaterSupply())){
						lastLastMonthDTO.setCxc(df.format(cxc));
					}
				}	
			}
			List<DmaMonthCjlDTO> dtoDataList = new ArrayList<DmaMonthCjlDTO>();
			dtoDataList.add(lastLastMonthDTO);
			dtoDataList.add(lastMonthDTO);
			
			// 计算合计
			DmaMonthCjlDTO sumDTO = new DmaMonthCjlDTO();
			sumDTO.setDareaId(area.getDareaId());
			sumDTO.setDareaName(area.getDareaName());
			Double sumDayWaterSupply = 0D;
			Double sumMonthWaterSupply = 0D;
			Integer sumUsers = 0;
			Integer sumCjl = 0;
			Double sumDayCjl = 0.0;
			Double sumCxc = 0D;
			for(DmaMonthCjlDTO dtoData : dtoDataList){
				if(null != dtoData.getUsers()){
					sumUsers += dtoData.getUsers();
				}
				if(null != dtoData.getCjl()){
					sumCjl += dtoData.getCjl();
				}
				if(null != dtoData.getDayCjl()){
					sumDayCjl += Double.valueOf(dtoData.getDayCjl());
				}
				if(null != dtoData.getDayWaterSupply()){
					sumDayWaterSupply += Double.valueOf(dtoData.getDayWaterSupply());
				}
				if(null != dtoData.getMonthWaterSupply()){
					sumMonthWaterSupply += Double.valueOf(dtoData.getMonthWaterSupply());
				}
				if(null != dtoData.getCxc()){
					sumCxc += Double.valueOf(dtoData.getCxc());
				}
				dtoData.setTime(dtoData.getTime().split("-")[1] + "月");
			}
			sumDTO.setUsers(sumUsers);
			sumDTO.setCjl(sumCjl);
			sumDTO.setDayCjl(String.valueOf(sumDayCjl));
			sumDTO.setDayWaterSupply(sumDayWaterSupply.toString());
			sumDTO.setMonthWaterSupply(sumMonthWaterSupply.toString());
			sumDTO.setCxc(df.format(sumCxc));
			sumDTO.setTime("合计");
			dtoDataList.add(sumDTO);
			
			// 赋值dto
			DmaQueryByMonthDTO dto = new DmaQueryByMonthDTO();
			dto.setDareaId(area.getDareaId());
			dto.setDareaName(area.getDareaName());
			dto.setDareaParentId(area.getDareaParentId());
			dto.setDataList(dtoDataList);
			allList.add(dto);
		}
		// dma分区父子结构配置
		// 一级片区
		List<DmaQueryByMonthDTO> resultList = new ArrayList<DmaQueryByMonthDTO>();
		for (DmaQueryByMonthDTO item : allList) {
			if (null == item.getDareaParentId()) {
				resultList.add(item);
			}
		}
		for (DmaQueryByMonthDTO class1 : resultList) {
			List<DmaQueryByMonthDTO> list2 = createSonDataList(allList, class1.getDareaId());
			class1.setSonList(list2);
			for (DmaQueryByMonthDTO class2 : list2) {
				List<DmaQueryByMonthDTO> list3 = createSonDataList(allList, class2.getDareaId());
				class2.setSonList(list3);
				for (DmaQueryByMonthDTO class3 : list3) {
					List<DmaQueryByMonthDTO> list4 = createSonDataList(allList, class3.getDareaId());
					class3.setSonList(list4);
					for (DmaQueryByMonthDTO class4 : list4) {
						List<DmaQueryByMonthDTO> list5 = createSonDataList(allList, class4.getDareaId());
						class4.setSonList(list5);
						for (DmaQueryByMonthDTO class5 : list5) {
							List<DmaQueryByMonthDTO> list6 = createSonDataList(allList, class5.getDareaId());
							class5.setSonList(list6);
							for (DmaQueryByMonthDTO class6 : list6) {
								List<DmaQueryByMonthDTO> list7 = createSonDataList(allList, class6.getDareaId());
								class6.setSonList(list7);
								for (DmaQueryByMonthDTO class7 : list7) {
									List<DmaQueryByMonthDTO> list8 = createSonDataList(allList, class7.getDareaId());
									class7.setSonList(list8);
									for (DmaQueryByMonthDTO class8 : list8) {
										List<DmaQueryByMonthDTO> list9 = createSonDataList(allList,
												class8.getDareaId());
										class8.setSonList(list9);
									}
								}
							}
						}
					}
				}
			}
		}
		return resultList;
	}

	private List<DmaQueryByMonthDTO> createSonDataList(List<DmaQueryByMonthDTO> allList, String dareaId) {
		List<DmaQueryByMonthDTO> list = new ArrayList<DmaQueryByMonthDTO>();
		for (DmaQueryByMonthDTO item : allList) {
			if (null != item.getDareaParentId() && item.getDareaParentId().contains(dareaId)) {
				list.add(item);
			}
		}
		return list;
	}

	@Override
	public List<Dma111DTO> query111(String areaName, String beginTime, String endTime) {
		
		List<Dma111DTO> list = dmaDataDao.query111(areaName, beginTime, endTime);
		List<Dma111DTO> zbslList = dmaDataDao.queryZbsl(beginTime, endTime);
		for(Dma111DTO dto : list){
			for(Dma111DTO zbsl : zbslList){
				if(dto.getQybh().equals(zbsl.getQybh())){
					dto.setZbsl(zbsl.getZbsl());
				}
			}
		}
		if(areaName == null){
			List<Dma111DTO> resultList = new ArrayList();
			for(Dma111DTO dto : list){
				if(dto.getSjqy() == null){
					resultList.add(dto);
					List<Dma111DTO> children = findChildren(list, dto);
					dto.setSonList(children); 
				}
			}
			return resultList;
		}
		return list;
	}
	
	static List<Dma111DTO> findChildren(List<Dma111DTO> list, Dma111DTO parent){
        List<Dma111DTO> levelTypeList = new ArrayList<>();
        for(Dma111DTO dto : list){
            if(parent.getQymc().equals(dto.getSjqy())){
                levelTypeList.add(dto);
                if(dto.getQymc().equals(dto.getSjqy())){
                	continue;
                }
                List<Dma111DTO> children = findChildren(list, dto);
                dto.setSonList(children);
            }
        }
        return levelTypeList;
    }
	
	
}
