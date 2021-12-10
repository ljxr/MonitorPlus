package com.enjoyor.modules.monitor.entity;

import java.util.List;

import com.enjoyor.modules.sys.entity.SysAreaSimpleEntity;

public class SGSiteDTO {

	/**
	 * 点位编号
	 */	
	private String siteId;
	
	/**
	 * 点位名称
	 */
	private String siteName;

	/**
	 * 区域编号
	 */
	private String areaId;
	
	/**
	 * 区域名称
	 */	
	private String areaName;
	
	/**
	 * 排序
	 */
	private int orderNum;
	
	/**
	 * 子点位列表
	 */
	private List<SGSiteDTO> sonList;
	
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
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
	
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public List<SGSiteDTO> getSonList() {
		return sonList;
	}
	public void setSonList(List<SGSiteDTO> sonList) {
		this.sonList = sonList;
	}
	
	
}
