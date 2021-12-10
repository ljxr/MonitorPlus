package com.enjoyor.modules.sys.service.imp;


import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enjoyor.modules.sys.dao.SysAreaDao;
import com.enjoyor.modules.sys.dao.SysDictionaryDao;
import com.enjoyor.modules.sys.dao.SysSiteDao;
import com.enjoyor.modules.sys.entity.SysAreaSimpleEntity;
import com.enjoyor.modules.sys.entity.SysDictionaryEntity;
import com.enjoyor.modules.sys.entity.SysRegionEntity;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.sys.entity.SysVideoEntity;
import com.enjoyor.modules.sys.service.SysAreaService;
import com.enjoyor.modules.sys.service.SysDictionaryService;
import com.enjoyor.modules.sys.service.SysSiteService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 点位 
 */
@Service("sysDictionaryService")
public class SysDictionaryServiceImpl implements SysDictionaryService {
	@Autowired
	private SysDictionaryDao sysDictionaryDao;
	
	/**
	 * 获取字典
	 */
	@Override
	public List<SysDictionaryEntity> queryDicList(String parentId){
		return sysDictionaryDao.queryDicList(parentId);
	}
	
}
