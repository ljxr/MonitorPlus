package com.enjoyor.modules.dma.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyor.modules.dma.dao.DmaBookDao;
import com.enjoyor.modules.dma.entity.DmaBookEntity;
import com.enjoyor.modules.dma.entity.DmaBookQueryEntity;
import com.enjoyor.modules.dma.service.DmaBookService;

@Service("dmaBookService")
public class DmaBookServiceimp implements DmaBookService{

	@Autowired
	private DmaBookDao dmaBookDao;

	/*@Override
	public List<DmaBookEntity> query(int bookId, String bookName, int cycle, String dmaAreaId, int offset, int limit2) {	
		return dmaBookDao.query(bookId, bookName, cycle, dmaAreaId, offset, limit2);
	}*/
	@Override
	public int count(DmaBookEntity en) {	
		return dmaBookDao.count(en);
	}
	
	@Override
	public List<DmaBookQueryEntity> query(DmaBookQueryEntity en) {	
		List<DmaBookQueryEntity> list =  dmaBookDao.query(en);
		for(DmaBookQueryEntity entity : list){
			switch (entity.getCycle()) {
			case 0:
				entity.setCycleString("每月抄");
				break;
			case 1:
				entity.setCycleString("单月抄");
				break;
			case 2:
				entity.setCycleString("双月抄");
				break;
			case 3:
				entity.setCycleString("三月抄");
				break;
			default:
				entity.setCycleString("无数据");
				break;
			}
		}
		return list;
	}
	
	@Override
	public int save(DmaBookEntity en) {
		return dmaBookDao.save(en);
	}

	@Override
	public int update(DmaBookEntity en) {
		return dmaBookDao.update(en);
	}
	
	@Override
	public int delete(String bookId) {
		return dmaBookDao.delete(bookId);
	}

	@Override
	public List<DmaBookQueryEntity> queryExist(String bookId) {
		return dmaBookDao.queryExist(bookId);
	}
}
