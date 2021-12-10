package com.enjoyor.modules.dma.entity;

import java.util.List;

public class DmaQueryByMonthDTO {
	
	private String dareaId;
	
	private String dareaName;
	
	private String dareaParentId;
		
	/**
	 * 数据list
	 */
	private List<DmaMonthCjlDTO> dataList;
	
	/**
	 * 子片区数据
	 */
	private List<DmaQueryByMonthDTO> sonList;

	
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

	public List<DmaMonthCjlDTO> getDataList() {
		return dataList;
	}

	public void setDataList(List<DmaMonthCjlDTO> dataList) {
		this.dataList = dataList;
	}

	public List<DmaQueryByMonthDTO> getSonList() {
		return sonList;
	}

	public void setSonList(List<DmaQueryByMonthDTO> sonList) {
		this.sonList = sonList;
	}
	
	

}
