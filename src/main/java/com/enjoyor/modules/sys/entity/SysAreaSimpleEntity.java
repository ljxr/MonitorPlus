package com.enjoyor.modules.sys.entity;

import java.io.Serializable;
import java.util.List;

public class SysAreaSimpleEntity implements Serializable {
	/**
	 * ID
	 */
	private Long id;
	
	/**
	 * 片区ID
	 */
	private String areaId;
	
	/**
	 * 片区名称
	 */
	private String areaName;
	
	/**
	 * 父片区ID
	 */
	private String areaParentId;
	
	/**
	 * 子片区列表
	 */
	private List<SysAreaSimpleEntity> sonList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaParentId() {
		return areaParentId;
	}

	public void setAreaParentId(String areaParentId) {
		this.areaParentId = areaParentId;
	}

	public List<SysAreaSimpleEntity> getSonList() {
		return sonList;
	}

	public void setSonList(List<SysAreaSimpleEntity> sonList) {
		this.sonList = sonList;
	}

	
}
