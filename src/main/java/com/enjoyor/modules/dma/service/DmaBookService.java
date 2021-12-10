package com.enjoyor.modules.dma.service;

import java.util.List;

import com.enjoyor.modules.dma.entity.DmaBookEntity;
import com.enjoyor.modules.dma.entity.DmaBookQueryEntity;

public interface DmaBookService {
	
	int count(DmaBookEntity en);

	int save(DmaBookEntity en);
	
	int update(DmaBookEntity en);

	// List<DmaBookEntity> query(int bookId, String bookName, int cycle, String dmaAreaId, int offset, int limit2);
	
	List<DmaBookQueryEntity> query(DmaBookQueryEntity en);
	
	List<DmaBookQueryEntity> queryExist(String bookId);
	
	int delete(String bookId);
}
