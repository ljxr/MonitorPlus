package com.enjoyor.modules.sys.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyor.modules.sys.dao.SysAreaDao;
import com.enjoyor.modules.sys.entity.SysAreaSimpleEntity;
import com.enjoyor.modules.sys.service.SysAreaService;

/**
 * 点位
 */
@Service("sysAreaService")
public class SysAreaServiceImpl implements SysAreaService {
	@Autowired
	private SysAreaDao sysAreaDao;

	/**
	 * 获取营业所，片区父子结构列表
	 */
	@Override
	public List<SysAreaSimpleEntity> findAllArea(List<String> list) {
		List<SysAreaSimpleEntity> areaList = new ArrayList<>();
		areaList = sysAreaDao.findAllArea(list);
		List<SysAreaSimpleEntity> resultList = new ArrayList<SysAreaSimpleEntity>();
		// 取出父级的所有营业所，排除其他
		for (SysAreaSimpleEntity area : areaList) {
			if (area.getAreaParentId().equals("1") && !area.getAreaId().equals("99")) {
				resultList.add(area);
			}
		}
		// 加入子级的片区
		for (SysAreaSimpleEntity result : resultList) {
			result.setSonList(new ArrayList<SysAreaSimpleEntity>());
			for (SysAreaSimpleEntity area : areaList) {
				if (area.getAreaParentId().equals(result.getAreaId())) {
					result.getSonList().add(area);
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 查询所有排水片区
	 */
	@Override
	public List<SysAreaSimpleEntity> findAllPsArea(List<String> list) {
		return sysAreaDao.findAllPsArea(list);
	}

	/**
	 * 获取所有片区
	 */
	@Override
	public List<SysAreaSimpleEntity> queryAllArea() {
		return sysAreaDao.queryAllArea();
	}

	@Override
	public List<SysAreaSimpleEntity> queryAreaInfo(String areaParentId) {
		// TODO Auto-generated method stub
		return sysAreaDao.queryAreaInfo(areaParentId);
	}

}
