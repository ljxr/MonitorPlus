package com.enjoyor.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.sys.entity.SysUserEntity;

/**
 * 系统用户
 * 
 */
public interface SysUserDao extends BaseDao<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(@Param("userId")int userId,@Param("basesysnum")int basesysnum);
	
	/**
	 * 查询用户的数据权限区域ID
	 * @param userId  用户ID
	 */
	List<String> queryAllRegion(int userId);
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(int userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);
	
	/**
	 * 修改密码
	 */
	int updatePassword(Map<String, Object> map);
}
