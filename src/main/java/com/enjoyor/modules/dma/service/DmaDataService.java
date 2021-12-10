package com.enjoyor.modules.dma.service;

import java.text.ParseException;
import java.util.List;

import com.enjoyor.modules.dma.entity.Dma111DTO;
import com.enjoyor.modules.dma.entity.DmaDataEntity;
import com.enjoyor.modules.dma.entity.DmaQueryByMonthDTO;

public interface DmaDataService {

    List<DmaDataEntity> queryDayList(String dareaId,String stime,String etime);
	
	List<DmaDataEntity> queryWeekList(String dareaId,String stime,String etime);
	
	List<DmaDataEntity> queryMonthList(String dareaId,String stime,String etime);
	
	List<DmaDataEntity> queryYearList(String dareaId,String stime,String etime);	
	
	List<DmaQueryByMonthDTO> queryByMonth() throws ParseException;
	
	List<Dma111DTO> query111(String areaName, String beginTime, String endTime);
}
