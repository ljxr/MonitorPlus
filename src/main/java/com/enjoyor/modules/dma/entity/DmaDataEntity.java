package com.enjoyor.modules.dma.entity;

import java.util.Date;

public class DmaDataEntity {

	private int id;
	
	private String dareaId;  //片区编号
	
	private String dareaName;//片区名称
	
	private double dareaFlowIn;//进水流量
	
	private double dareaFlowOut;//出水流量
	
	private double lsv;//漏损率
	
	private double coeIn;//进水偏移系数
	
	private double coeOut;//出水偏移系数
	
	private String time;//时间

    private int offset;
	
    private int limit;
    
    private String sidx;
    
    private String order;
    
    private String lsv2;
    
	public String getLsv2() {
		return lsv2;
	}

	public void setLsv2(String lsv2) {
		this.lsv2 = lsv2;
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

	public double getDareaFlowIn() {
		return dareaFlowIn;
	}

	public void setDareaFlowIn(double dareaFlowIn) {
		this.dareaFlowIn = dareaFlowIn;
	}

	public double getDareaFlowOut() {
		return dareaFlowOut;
	}

	public void setDareaFlowOut(double dareaFlowOut) {
		this.dareaFlowOut = dareaFlowOut;
	}

	public double getLsv() {
		return lsv;
	}

	public void setLsv(double lsv) {
		this.lsv = lsv;
	}

	public double getCoeIn() {
		return coeIn;
	}

	public void setCoeIn(double coeIn) {
		this.coeIn = coeIn;
	}

	public double getCoeOut() {
		return coeOut;
	}

	public void setCoeOut(double coeOut) {
		this.coeOut = coeOut;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
