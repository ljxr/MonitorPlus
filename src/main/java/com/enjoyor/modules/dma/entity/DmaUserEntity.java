package com.enjoyor.modules.dma.entity;

public class DmaUserEntity {

	private int id ;
	
	private String userId;//用户编号
	
	private String userName;//用户名称
	
	private String userType;//用户类型
	
	private String areaId;//区域编号
	
	private String areaName;//区域名称
	
	private String dareaId;//片区编号
	
	private String dareaName;//片区名称
	
	private int recordPeriod;//抄表周期
	
	private int offset;
	
	private int limit;
	
	private String sidx;
	
	private String order;
	
	//额外字段
	private String userTypeName;//用户类型名称

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public int getRecordPeriod() {
		return recordPeriod;
	}

	public void setRecordPeriod(int recordPeriod) {
		this.recordPeriod = recordPeriod;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	
}
