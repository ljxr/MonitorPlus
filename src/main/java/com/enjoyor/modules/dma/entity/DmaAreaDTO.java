package com.enjoyor.modules.dma.entity;

import java.util.Date;
import java.util.List;

public class DmaAreaDTO extends DmaAreaEntity{
	
	// 昨日进水
	private Double dareaFlowIn;
	
	// 昨日出水
	private Double dareaFlowOut;
	
	// 昨日漏损量
	private Double lsl;
	
	// 昨日漏损率
	private Double lsv;
	
	private Date time;
	
	// 昨日夜间平均流量
	private Double nigAvgFlow;
	
	// 总用户数量
	private Integer usersNum;
	
	// 子片区数据
	private List<DmaAreaDTO> sonList;

	public Double getDareaFlowIn() {
		return dareaFlowIn;
	}

	public void setDareaFlowIn(Double dareaFlowIn) {
		this.dareaFlowIn = dareaFlowIn;
	}

	public Double getDareaFlowOut() {
		return dareaFlowOut;
	}

	public void setDareaFlowOut(Double dareaFlowOut) {
		this.dareaFlowOut = dareaFlowOut;
	}

	public Double getLsl() {
		return lsl;
	}

	public void setLsl(Double lsl) {
		this.lsl = lsl;
	}

	public Double getLsv() {
		return lsv;
	}

	public void setLsv(Double lsv) {
		this.lsv = lsv;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getNigAvgFlow() {
		return nigAvgFlow;
	}

	public void setNigAvgFlow(Double nigAvgFlow) {
		this.nigAvgFlow = nigAvgFlow;
	}

	public Integer getUsersNum() {
		return usersNum;
	}

	public void setUsersNum(Integer usersNum) {
		this.usersNum = usersNum;
	}

	public List<DmaAreaDTO> getSonList() {
		return sonList;
	}

	public void setSonList(List<DmaAreaDTO> sonList) {
		this.sonList = sonList;
	}
	
	
}
