package com.enjoyor.modules.sys.entity;


import java.io.Serializable;
import java.util.List;

/**
 * 树形点位结构
 * 
 */
public class SiteTreeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 点位编号
	 */	
	private String siteId;
	/**
	 * 点位名称
	 */
	private String name;

	/**
	 * 关联区域编号
	 */
	private String areaId;
	/**
	 * 关联区域名称
	 */	
	private String areaName;
	
	private String parentId;
	
	private String parentName;

	/**
	 * 排序
	 */
	private int orderNum;
	
	private Boolean open;

	private List<?> list;
	/**
	 * 权限标识符
	 */
	private String queryRegionId;
    
    private List<SiteTreeEntity> treelist;
    
    private boolean flag;
    

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getQueryRegionId() {
		return queryRegionId;
	}

	public void setQueryRegionId(String queryRegionId) {
		this.queryRegionId = queryRegionId;
	}

	public List<SiteTreeEntity> getTreelist() {
		return treelist;
	}

	public void setTreelist(List<SiteTreeEntity> treelist) {
		this.treelist = treelist;
	}
    
}
