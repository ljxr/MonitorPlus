package com.enjoyor.modules.sys.entity;


import java.io.Serializable;
import java.util.List;

/**
 * 点位管理
 * 
 */
public class SysSiteEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID
	 */
	private Long id;
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
	
	private String type;
	
	private String typeName;
	
	private double x;
	
	private double y;
	
	private String reportNum;
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
	
    private String status;
    
    private String statusName;
    
    private String demo;
    
    // 上游点位id
    private String upperSiteId;
    
    private int offset;
    
    private int limit;

	public String getDemo() {
		return demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQueryRegionId() {
		return queryRegionId;
	}

	public void setQueryRegionId(String queryRegionId) {
		this.queryRegionId = queryRegionId;
	}

	public String getReportNum() {
		return reportNum;
	}

	public void setReportNum(String reportNum) {
		this.reportNum = reportNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
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

	
	    /**
	    * @return x
	    */
	    
	public double getX() {
		return x;
	}

	
	    /**
	     * @param x the x to set
	     */
	    
	public void setX(double x) {
		this.x = x;
	}

		
	    /**
	    * @return y
	    */
	    
	public double getY() {
		return y;
	}

		
	    /**
	     * @param y the y to set
	     */
	    
	public void setY(double y) {
		this.y = y;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getUpperSiteId() {
		return upperSiteId;
	}

	public void setUpperSiteId(String upperSiteId) {
		this.upperSiteId = upperSiteId;
	}
	
	
	
}
