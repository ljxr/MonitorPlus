package com.enjoyor.modules.monitor.service.imp;

import com.enjoyor.modules.monitor.dao.DispatchLogDao;
import com.enjoyor.modules.monitor.entity.DispatchLog;
import com.enjoyor.modules.monitor.entity.DispatchLogQueryDTO;
import com.enjoyor.modules.monitor.service.DispatchLogService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("dispatchLogService")
public class DispatchLogServiceimp implements DispatchLogService {

	@Autowired
	private DispatchLogDao dispatchLogDao;

	@Override
	public List<DispatchLog> query(DispatchLogQueryDTO dto) {
		return dispatchLogDao.query(dto);	
	}
	
	@Override
	public Integer countAll(DispatchLogQueryDTO dto) {
		return dispatchLogDao.countAll(dto);	
	}
	
	@Override
	public void add(DispatchLog dispatchLog) {
		dispatchLog.setId(null);
		dispatchLogDao.add(dispatchLog);	
	}
	
	@Override
	public void update(DispatchLog dispatchLog) {
		dispatchLogDao.update(dispatchLog);	
	}
	
	@Override
	public void delete(List<Integer> list) {
		dispatchLogDao.delete(list);	
	}
		
	
}
