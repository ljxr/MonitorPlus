package com.enjoyor.modules.monitor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;


public class AlarmTotalEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	
	private String siteId;
	
	private String hoclAlarm;
	
	private String turAlarm;
	
	private String phAlarm;
	
	private String codAlarm;
	
	private String tnAlarm;
	
	private String tpAlarm;
	
	private String ssAlarm;
	
	private String ptoutAlarm;
	
	private String flsAlarm;
	
	private String time;
	
	private String siteName;

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

	public String getHoclAlarm() {
		return hoclAlarm;
	}

	public void setHoclAlarm(String hoclAlarm) {
		this.hoclAlarm = hoclAlarm;
	}

	public String getTurAlarm() {
		return turAlarm;
	}

	public void setTurAlarm(String turAlarm) {
		this.turAlarm = turAlarm;
	}

	public String getPhAlarm() {
		return phAlarm;
	}

	public void setPhAlarm(String phAlarm) {
		this.phAlarm = phAlarm;
	}

	public String getCodAlarm() {
		return codAlarm;
	}

	public void setCodAlarm(String codAlarm) {
		this.codAlarm = codAlarm;
	}

	public String getTnAlarm() {
		return tnAlarm;
	}

	public void setTnAlarm(String tnAlarm) {
		this.tnAlarm = tnAlarm;
	}

	public String getTpAlarm() {
		return tpAlarm;
	}

	public void setTpAlarm(String tpAlarm) {
		this.tpAlarm = tpAlarm;
	}

	public String getSsAlarm() {
		return ssAlarm;
	}

	public void setSsAlarm(String ssAlarm) {
		this.ssAlarm = ssAlarm;
	}

	public String getPtoutAlarm() {
		return ptoutAlarm;
	}

	public void setPtoutAlarm(String ptoutAlarm) {
		this.ptoutAlarm = ptoutAlarm;
	}

	public String getFlsAlarm() {
		return flsAlarm;
	}

	public void setFlsAlarm(String flsAlarm) {
		this.flsAlarm = flsAlarm;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}