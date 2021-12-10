package com.enjoyor.modules.dma.entity;

public class DmaBookQueryEntity extends DmaBookEntity {

	/**
	 * 抄表周期
	 */
	private String cycleString;
	
	/**
	 * dma分区名称
	 */
	private String dmaAreaName;
	
	/**
	 * offset
	 */
	private Integer offset; 
	
	/**
	 * limit
	 */
	private Integer limit;
	
	
	

	public String getCycleString() {
		return cycleString;
	}

	public void setCycleString(String cycleString) {
		this.cycleString = cycleString;
	}

	public String getDmaAreaName() {
		return dmaAreaName;
	}

	public void setDmaAreaName(String dmaAreaName) {
		this.dmaAreaName = dmaAreaName;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	} 
	
	
}
