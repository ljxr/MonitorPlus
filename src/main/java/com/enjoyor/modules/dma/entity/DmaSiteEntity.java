package com.enjoyor.modules.dma.entity;

public class DmaSiteEntity {

	private int id;
	
	private String dareaId;//片区编号
	
	private String dareaName;//片区名称
	
	private String dsiteId;//点位编号
	
	private String dsiteName;//点位名称
	
	private String dsiteType;//点位类型编号	
	
	private String dsiteTypeName;//点位类型名称	
	
	private String dsiteParentId;//上级点位编号
	
	private String dsiteJc;//进出状态
	
	private double coefficient;//系数
	
	private int offset;
	
    private int limit;
    
    private String sidx;
    
    private String order;

	public String getDsiteJc() {
		return dsiteJc;
	}

	public void setDsiteJc(String dsiteJc) {
		this.dsiteJc = dsiteJc;
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

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDareaId() {
		return dareaId;
	}

	public void setDareaId(String dareaId) {
		this.dareaId = dareaId;
	}

	public String getDareaName() {
		return dareaName;
	}

	public void setDareaName(String dareaName) {
		this.dareaName = dareaName;
	}

	public String getDsiteId() {
		return dsiteId;
	}

	public void setDsiteId(String dsiteId) {
		this.dsiteId = dsiteId;
	}

	public String getDsiteName() {
		return dsiteName;
	}

	public void setDsiteName(String dsiteName) {
		this.dsiteName = dsiteName;
	}

	public String getDsiteType() {
		return dsiteType;
	}

	public void setDsiteType(String dsiteType) {
		this.dsiteType = dsiteType;
	}

	public String getDsiteParentId() {
		return dsiteParentId;
	}

	public void setDsiteParentId(String dsiteParentId) {
		this.dsiteParentId = dsiteParentId;
	}

	public double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}

	public String getDsiteTypeName() {
		return dsiteTypeName;
	}

	public void setDsiteTypeName(String dsiteTypeName) {
		this.dsiteTypeName = dsiteTypeName;
	}
	
}
