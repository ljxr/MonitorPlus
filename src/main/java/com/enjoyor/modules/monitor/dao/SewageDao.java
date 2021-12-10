package com.enjoyor.modules.monitor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.ReportWsEntity;

public interface SewageDao {
	
	// beginTime = YYYY-MM-DD
    // endTime = YYYY-MM-DD
    List<ReportWsEntity> select(@Param("siteId") String siteId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);


}
