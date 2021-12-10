package com.enjoyor.modules.monitor.entity;

import java.io.Serializable;
import java.util.List;

public class GytBaseInfoEntity implements Serializable{

	private static final long serialVersionUID = 1L;	
	
	private int id;
	
	private String gytName;
	
	private String siteParentName;
	
	private String siteParentId;
	
	private String siteType;
	
	private String siteName;
	
	private String siteId;
	
	private String createTime;
	
	private String createPerson;
	
	private String updateTime;
	
	private int currentPage;
	
	private int totalPage;
	
	private int totalNum;
	
	private int offset;
	
	private int limit;
	
	private List<String> list;

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGytName() {
		return gytName;
	}

	public void setGytName(String gytName) {
		this.gytName = gytName;
	}

	public String getSiteParentName() {
		return siteParentName;
	}

	public void setSiteParentName(String siteParentName) {
		this.siteParentName = siteParentName;
	}

	public String getSiteParentId() {
		return siteParentId;
	}

	public void setSiteParentId(String siteParentId) {
		this.siteParentId = siteParentId;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
}
