package com.enjoyor.modules.sys.dao;


import java.util.List;

import com.enjoyor.modules.sys.entity.SysRoleDeptEntity;

/**
 * 角色与部门对应关系
 * 

 */
public interface SysRoleDeptDao extends BaseDao<SysRoleDeptEntity> {
	
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long roleId);
}
