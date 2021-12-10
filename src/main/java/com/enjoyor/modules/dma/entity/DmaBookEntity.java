package com.enjoyor.modules.dma.entity;

public class DmaBookEntity {

	/**
	 * 表册号ID
	 */
	private String bookId;
	
	/**
	 * 表册号名称
	 */
	private String bookName;
	
	/**
	 * 抄表周期
	 */
	private Integer cycle;
	
	/**
	 * 用户数
	 */
	private Integer userNum;
	
	/**
	 * 所属DMA分区
	 */
	private String dmaAreaId;
	
	/**
	 * 地图属性
	 */
	private String geomString;
	
	/**
	 * 
	 */
	private Double X;

	/**
	 * 
	 */
	private Double Y;
	
	/**
	 * 
	 */
	private String intra;
	
	/**
	 * 
	 */
	private Double borderwidth;
	
	/**
	 * 
	 */
	private String border;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}

	public String getDmaAreaId() {
		return dmaAreaId;
	}

	public void setDmaAreaId(String dmaAreaId) {
		this.dmaAreaId = dmaAreaId;
	}

	public String getGeomString() {
		return geomString;
	}

	public void setGeomString(String geomString) {
		this.geomString = geomString;
	}

	public Double getX() {
		return X;
	}

	public void setX(Double x) {
		X = x;
	}

	public Double getY() {
		return Y;
	}

	public void setY(Double y) {
		Y = y;
	}

	public String getIntra() {
		return intra;
	}

	public void setIntra(String intra) {
		this.intra = intra;
	}

	public Double getBorderwidth() {
		return borderwidth;
	}

	public void setBorderwidth(Double borderwidth) {
		this.borderwidth = borderwidth;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}
	
	
	
}
