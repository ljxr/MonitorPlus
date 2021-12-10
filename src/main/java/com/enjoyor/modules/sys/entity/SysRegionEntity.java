package com.enjoyor.modules.sys.entity;


import java.io.Serializable;
import java.util.List;


/**
 * 菜单管理
 * 
 */
public class SysRegionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;

    private String regionId;
    
	private String name;
    
	private String parentId;
	
	private String parentName;

	private Integer orderNum;
	
	private String border;
	
	private String intra;
	
	private int borderwidth;
	
	private String type;
	/**
	 * ztree属性
	 */
	private Boolean open;
	
	private List<?> list;
	
	private String queryRegionId;
	
    private double x;
	
	private double y;
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getQueryRegionId() {
		return queryRegionId;
	}

	public void setQueryRegionId(String queryRegionId) {
		this.queryRegionId = queryRegionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getIntra() {
		return intra;
	}

	public void setIntra(String intra) {
		this.intra = intra;
	}

	public int getBorderwidth() {
		return borderwidth;
	}

	public void setBorderwidth(int borderwidth) {
		this.borderwidth = borderwidth;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
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
 
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
