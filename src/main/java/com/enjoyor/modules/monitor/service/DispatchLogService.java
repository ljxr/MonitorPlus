package com.enjoyor.modules.monitor.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.DispatchLog;
import com.enjoyor.modules.monitor.entity.DispatchLogQueryDTO;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.PipeData;
import com.enjoyor.modules.monitor.entity.PipeDataDTO;
import com.enjoyor.modules.monitor.entity.PipeDataHis;
import com.enjoyor.modules.monitor.entity.SeatEntity;

public interface DispatchLogService {

	   List<DispatchLog> query(DispatchLogQueryDTO dto);
	   
	   Integer countAll(DispatchLogQueryDTO dto);

	   void add(DispatchLog dispatchLog);
	   
	   void update(DispatchLog dispatchLog);
	   
	   void delete(List<Integer> list);

}

