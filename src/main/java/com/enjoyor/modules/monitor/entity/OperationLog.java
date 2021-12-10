package com.enjoyor.modules.monitor.entity;

import java.util.Date;

public class OperationLog {

	/**
	 * 主键id
	 */
	private Integer id;
	
	/**
	 * 时间
	 */
	private Date time;
	
	/**
	 * 操作内容
	 */
	private String operateContent;
	
	/**
	 * 内容类别
	 */
	private String contentType;
	
	/**
	 * 操作人
	 */
	private String operator;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
}
