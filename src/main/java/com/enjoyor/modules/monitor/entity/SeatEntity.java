package com.enjoyor.modules.monitor.entity;

import java.io.Serializable;

public class SeatEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String seat;
	
	private String linkParentId;
	
	private String linkSiteId;
	
	private String divId;
	
	private String siteId;
	
	private String tsize;
	
	private String tweight;
	
	private String x;
	
	private String y;

	public String getTsize() {
		return tsize;
	}

	public void setTsize(String tsize) {
		this.tsize = tsize;
	}

	public String getTweight() {
		return tweight;
	}

	public void setTweight(String tweight) {
		this.tweight = tweight;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getLinkParentId() {
		return linkParentId;
	}

	public void setLinkParentId(String linkParentId) {
		this.linkParentId = linkParentId;
	}

	public String getLinkSiteId() {
		return linkSiteId;
	}

	public void setLinkSiteId(String linkSiteId) {
		this.linkSiteId = linkSiteId;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}
}
