package com.enjoyor.modules.monitor.entity;

import java.io.Serializable;
import java.util.List;

public class GytEntity implements Serializable{

	private static final long serialVersionUID = 1L;	
	
	private int id;
	
	private String siteId;
	
	private String text;
	
	private String gytId;
	
	private String x;
	
	private String y;
	
	private String dcolor;
	
	private String dsize;
	
	private String dweight;
	
	private String tcolor;
	
	private String tsize;
	
	private String tweight;
	
	private String type;
	
	private GytEntity description;
	
	private GytEntity data;
	
	private String parentSiteId;
	
	private String divId;
	
	public String getParentSiteId() {
		return parentSiteId;
	}

	public void setParentSiteId(String parentSiteId) {
		this.parentSiteId = parentSiteId;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getGytId() {
		return gytId;
	}

	public void setGytId(String gytId) {
		this.gytId = gytId;
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

	public String getDcolor() {
		return dcolor;
	}

	public void setDcolor(String dcolor) {
		this.dcolor = dcolor;
	}

	public String getDsize() {
		return dsize;
	}

	public void setDsize(String dsize) {
		this.dsize = dsize;
	}

	public String getDweight() {
		return dweight;
	}

	public void setDweight(String dweight) {
		this.dweight = dweight;
	}

	public String getTcolor() {
		return tcolor;
	}

	public void setTcolor(String tcolor) {
		this.tcolor = tcolor;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public GytEntity getDescription() {
		return description;
	}

	public void setDescription(GytEntity description) {
		this.description = description;
	}

	public GytEntity getData() {
		return data;
	}

	public void setData(GytEntity data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "GytEntity [id=" + id + ", siteId=" + siteId + ", text=" + text + ", gytId=" + gytId + ", x=" + x
				+ ", y=" + y + ", dcolor=" + dcolor + ", dsize=" + dsize + ", dweight=" + dweight + ", tcolor=" + tcolor
				+ ", tsize=" + tsize + ", tweight=" + tweight + ", type=" + type + ", description=" + description
				+ ", data=" + data + "]";
	}
	
	
}
