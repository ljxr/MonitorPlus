package com.enjoyor.modules.sys.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enjoyor.common.annotation.DataFilter;
import com.enjoyor.modules.sys.dao.SysRoleDao;
import com.enjoyor.modules.sys.entity.SysRoleEntity;
import com.enjoyor.modules.sys.service.SysRoleDeptService;
import com.enjoyor.modules.sys.service.SysRoleMenuService;
import com.enjoyor.modules.sys.service.SysRoleService;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 角色
 * 
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;

	@Override
	public SysRoleEntity queryObject(Long roleId) {
		return sysRoleDao.queryObject(roleId);
	}

	@Override
	@DataFilter(tableAlias = "r", user = false)
	public List<SysRoleEntity> queryList(Map<String, Object> map) {
		return sysRoleDao.queryList(map);
	}

	@Override
	@DataFilter(tableAlias = "r", user = false)
	public int queryTotal(Map<String, Object> map) {
		return sysRoleDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysRoleEntity role) {
		role.setCreateTime(new Date());
		sysRoleDao.save(role);
		
		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//保存角色与部门关系
		sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	@Override
	@Transactional
	public void update(SysRoleEntity role) {
		sysRoleDao.update(role);
		
		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//保存角色与部门关系
		sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] roleIds) {
		sysRoleDao.deleteBatch(roleIds);
	}

	@Override
	public void deleteBatch2(Long[] roleIds) {
		// TODO Auto-generated method stub
		sysRoleDao.deleteBatch2(roleIds);
	}

	@Override
	public void deleteBatch3(Long[] roleIds) {
		// TODO Auto-generated method stub
		sysRoleDao.deleteBatch3(roleIds);
	}

	@Override
	public void deleteBatch4(Long[] roleIds) {
		// TODO Auto-generated method stub
		sysRoleDao.deleteBatch4(roleIds);
	}

}
