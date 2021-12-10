package com.enjoyor.modules.dma.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.dma.entity.DmaBookEntity;
import com.enjoyor.modules.dma.entity.DmaBookQueryEntity;

public interface DmaBookDao {

	// List<DmaBookEntity> query(@Param("bookId")int bookId, @Param("bookName")String bookName, @Param("cycle")int cycle, @Param("dmaAreaId")String dmaAreaId, @Param("offset")int offset, @Param("limit")int limit);
	int count(DmaBookEntity en);
	
	List<DmaBookQueryEntity> query(DmaBookQueryEntity en);
	
	int save(DmaBookEntity en);
	
	int update(DmaBookEntity en);

	int delete(@Param("bookId")String bookId);
	
	List<DmaBookQueryEntity> queryExist(@Param("bookId")String bookId);
}
