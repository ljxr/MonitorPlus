package com.enjoyor.modules.sys.service;


import java.util.List;
import java.util.Map;

import com.enjoyor.modules.sys.entity.SysRoleEntity;




/**
 * 角色
 * 
 */
public interface SysRoleService {
	
	SysRoleEntity queryObject(Long roleId);
	
	List<SysRoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRoleEntity role);
	
	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);
	void deleteBatch2(Long[] roleIds);
	void deleteBatch3(Long[] roleIds);
	void deleteBatch4(Long[] roleIds);

}
