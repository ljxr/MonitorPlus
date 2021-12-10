package com.enjoyor.modules.monitor.service.imp;

import com.enjoyor.modules.monitor.dao.PipeDataDao;
import com.enjoyor.modules.monitor.entity.PipeData;
import com.enjoyor.modules.monitor.entity.PipeDataDTO;
import com.enjoyor.modules.monitor.service.PipeDataService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("monitorDataService2")
public class PipeDataServiceimp implements PipeDataService {

	@Autowired
	PipeDataDao pipeDataDao;

	@Override
	public List<PipeData> findAll() {
		return pipeDataDao.findAll();
	}
	
	@Override
	public List<PipeDataDTO> findByArea(String areaId) {
		List<PipeDataDTO> resultList = new ArrayList<PipeDataDTO>();
		List<PipeDataDTO> list1 = pipeDataDao.findByArea(areaId);
		List<PipeDataDTO> list2 = pipeDataDao.findBzByArea(areaId);
		resultList.addAll(list1);
		resultList.addAll(list2);
		for(PipeDataDTO dto : resultList){
			if(null != dto.getYl()){
				dto.setYl(dto.getYl().trim());
			}
		}
		return resultList;
	}
	
	@Override
	public List<PipeDataDTO> findPsByArea(String areaId) {
		List<PipeDataDTO> list = pipeDataDao.findPsByArea(areaId);
		for(PipeDataDTO dto : list){
			if(null != dto.getYl()){
				dto.setYl(dto.getYl().replace(" ", ""));
			}
		}
		return list;
	}

	@Override
	public PipeData queryPipeIns(String pointNum) {
		// TODO Auto-generated method stub
		return pipeDataDao.queryPipeIns(pointNum);
	}
	
}
