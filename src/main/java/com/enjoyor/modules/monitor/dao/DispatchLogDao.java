package com.enjoyor.modules.monitor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.DispatchLog;
import com.enjoyor.modules.monitor.entity.DispatchLogQueryDTO;
import com.enjoyor.modules.monitor.entity.PipeDataHis;

public interface DispatchLogDao {
	
	List<DispatchLog> query(DispatchLogQueryDTO dto);
	
	Integer countAll(DispatchLogQueryDTO dto);
	
	void add(DispatchLog dispatchLog);
	
	void update(DispatchLog dispatchLog);
	
	void delete(@Param("idList")List<Integer> list);
}
