package com.enjoyor.modules.monitor.entity;

import java.util.Date;

public class MonitorTotalQueryDTO {

	/**
	 * 点位ID 1
	 */
	private String siteId1;
	
	/**
	 * 点位ID 1 的指标
	 */
	private String type1;
	
	/**
	 * 点位ID 2
	 */
	private String siteId2;
	
	/**
	 * 点位ID 2 的指标
	 */
	private String type2;
	
	/**
	 * 开始时间
	 */
	private String beginTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;

	public String getSiteId1() {
		return siteId1;
	}

	public void setSiteId1(String siteId1) {
		this.siteId1 = siteId1;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getSiteId2() {
		return siteId2;
	}

	public void setSiteId2(String siteId2) {
		this.siteId2 = siteId2;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
	
}
