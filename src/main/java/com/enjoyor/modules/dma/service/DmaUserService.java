package com.enjoyor.modules.dma.service;

import java.util.List;

import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaUserEntity;

public interface DmaUserService {

	int save(DmaUserEntity de);
	
	int update(DmaUserEntity de);
	
	int delete(int id);
	
	List<DmaUserEntity> queryUserList(DmaUserEntity de);
	
	int queryUserCount(DmaUserEntity de);
}
