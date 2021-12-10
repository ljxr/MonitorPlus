package com.enjoyor.modules.dma.entity;

import java.util.List;

public class DmaAreaTreeDTO {

	private String dareaId;

	private String dareaName;

	private String dareaParentId;

	// 昨日进水
	private Double dareaFlowIn;

	// 昨日出水
	private Double dareaFlowOut;

	// 昨日漏损量
	private Double lsl;

	// 昨日漏损率
	private Double lsv;

	// 昨日夜间平均流量
	private Double nigAvgFlow;

	// 总用户数量
	private Integer usersNum;

	// 子片区数据
	private List<DmaAreaTreeDTO> sonList;

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

	public String getDareaParentId() {
		return dareaParentId;
	}

	public void setDareaParentId(String dareaParentId) {
		this.dareaParentId = dareaParentId;
	}

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

	public List<DmaAreaTreeDTO> getSonList() {
		return sonList;
	}

	public void setSonList(List<DmaAreaTreeDTO> sonList) {
		this.sonList = sonList;
	}

}
