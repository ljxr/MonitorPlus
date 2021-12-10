package com.enjoyor.modules.dma.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyor.modules.dma.dao.DmaAreaDao;
import com.enjoyor.modules.dma.dao.DmaUserDao;
import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaUserEntity;
import com.enjoyor.modules.dma.service.DmaAreaService;
import com.enjoyor.modules.dma.service.DmaUserService;

@Service("dmaUserService")
public class DmaUserServiceimp implements DmaUserService{

	@Autowired
	private DmaUserDao dmaUserDao;

	@Override
	public int save(DmaUserEntity de) {
		// TODO Auto-generated method stub
		return dmaUserDao.save(de);
	}

	@Override
	public int update(DmaUserEntity de) {
		// TODO Auto-generated method stub
		return dmaUserDao.update(de);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return dmaUserDao.delete(id);
	}

	@Override
	public List<DmaUserEntity> queryUserList(DmaUserEntity de) {
		// TODO Auto-generated method stub
		return dmaUserDao.queryUserList(de);
	}

	@Override
	public int queryUserCount(DmaUserEntity de) {
		// TODO Auto-generated method stub
		return dmaUserDao.queryUserCount(de);
	}		
}
