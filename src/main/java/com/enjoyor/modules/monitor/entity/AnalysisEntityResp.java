package com.enjoyor.modules.monitor.entity;

import java.io.Serializable;

public class AnalysisEntityResp implements Serializable{
	
	private String time;

	private String value1;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}
	
}
