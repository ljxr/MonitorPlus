package com.enjoyor.modules.monitor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;


public class AlarmIgnoreEntity implements Serializable{

	private int id;
	
    private String siteId;
    
    private String type;
    
    private String endTime;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}