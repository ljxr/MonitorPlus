package com.enjoyor.modules.monitor.entity;

import java.io.Serializable;

public class AnalysisEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String siteId;
	
	private String siteParentId;
	
	private String siteName;
	
	private String ybNuber;
	
	private double value;
	
	private String time;

	private String value1;
	
	
	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteParentId() {
		return siteParentId;
	}

	public void setSiteParentId(String siteParentId) {
		this.siteParentId = siteParentId;
	}

	public String getYbNuber() {
		return ybNuber;
	}

	public void setYbNuber(String ybNuber) {
		this.ybNuber = ybNuber;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
