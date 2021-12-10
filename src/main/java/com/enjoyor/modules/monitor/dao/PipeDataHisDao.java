package com.enjoyor.modules.monitor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.PipeDataHis;

public interface PipeDataHisDao {
	
	List<PipeDataHis> queryHisData(@Param("sql")String sql);
	
	List<PipeDataHis> exportExcel(@Param("areaId")String areaId, @Param("beginTime")String beginTime, @Param("endTime")String endTime);
}
