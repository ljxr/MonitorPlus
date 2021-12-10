package com.enjoyor.modules.dma.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.dma.entity.Dma111DTO;
import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaDataEntity;
import com.enjoyor.modules.dma.entity.DmaMonthCjlDTO;
import com.enjoyor.modules.dma.entity.DmaSiteEntity;
import com.enjoyor.modules.dma.entity.DmaUserEntity;

public interface DmaDataDao {

	List<DmaDataEntity> queryDayList(@Param("dareaId") String dareaId, @Param("stime") String stime,
			@Param("etime") String etime);

	List<DmaDataEntity> queryWeekList(@Param("dareaId") String dareaId, @Param("stime") String stime,
			@Param("etime") String etime);

	List<DmaDataEntity> queryMonthList(@Param("dareaId") String dareaId, @Param("stime") String stime,
			@Param("etime") String etime);

	List<DmaDataEntity> queryYearList(@Param("dareaId") String dareaId, @Param("stime") String stime,
			@Param("etime") String etime);

	List<DmaMonthCjlDTO> queryCjlByBook(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
	
	List<Map<String, Object>> queryMonthSumFlow(@Param("bTime") String bTime, @Param("eTime") String eTime);

	List<Dma111DTO> query111(@Param("areaName") String areaName, @Param("beginTime") String beginTime, @Param("endTime") String endTime);
	
	List<Dma111DTO> queryZbsl(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
}
