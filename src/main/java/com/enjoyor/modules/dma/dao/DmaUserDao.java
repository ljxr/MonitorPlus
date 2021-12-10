package com.enjoyor.modules.dma.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaUserEntity;

public interface DmaUserDao {

	int save(DmaUserEntity de);
	
	int update(DmaUserEntity de);
	
	int delete(@Param("id")int id);
	
	List<DmaUserEntity> queryUserList(DmaUserEntity de);
	
	int queryUserCount(DmaUserEntity de);
}
