package com.enjoyor.modules.dma.entity;

import java.util.List;

import com.enjoyor.common.utils.GeomtryHelper;

import oracle.spatial.geometry.JGeometry;

public class DmaAreaEntity {

	private int id;
	
	private String dareaId;  //片区编号
	
	private String dareaName; //片区名称
	
	private String dareaParentId;  //上级片区编号
	
	private String dareaParentName;  //上级片区编号
	
	// private JGeometry geom;  //地图属性
	private String geomString;
	/*public void geom2str(){
		if(this.geom!=null){
			this.setGeomString(GeomtryHelper.getGeom2JsonString(this.geom,false));
			this.geom=null;
		}
	}*/
	
	private String border;   
	
	private String intra;
	
	private String borderwidth;  //边宽
	
    private String dareaType;   //片区类型
     
    private double x;
	
	private double y;
	
	private double innerX;  //区域中心点X坐标
	
	private double innerY;  //区域中心点Y坐标
	
	private int offset;
	
	private int limit;
	
	private String sidx;
	
	private String order;
	
	private int day;
	
	private int week;
	
	private int month;
	
	private int year;
	
	private int orderNum;
	
	private List<DmaAreaEntity> children;	

	public List<DmaAreaEntity> getChildren() {
		return children;
	}

	public void setChildren(List<DmaAreaEntity> children) {
		this.children = children;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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
	
	public String getDareaParentName() {
		return dareaParentName;
	}

	public void setDareaParentName(String dareaParentName) {
		this.dareaParentName = dareaParentName;
	}


	public String getBorder() {
		return border;
	}


	public void setBorder(String border) {
		this.border = border;
	}


	public String getIntra() {
		return intra;
	}


	public void setIntra(String intra) {
		this.intra = intra;
	}


	public String getBorderwidth() {
		return borderwidth;
	}


	public void setBorderwidth(String borderwidth) {
		this.borderwidth = borderwidth;
	}


	public String getDareaType() {
		return dareaType;
	}


	public void setDareaType(String dareaType) {
		this.dareaType = dareaType;
	}


	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}


	public double getInnerX() {
		return innerX;
	}


	public void setInnerX(double innerX) {
		this.innerX = innerX;
	}


	public double getInnerY() {
		return innerY;
	}


	public void setInnerY(double innerY) {
		this.innerY = innerY;
	}


	/**
    * @return geom
    *//* 
    public JGeometry getGeom() {
	  return geom;
    }


    *//**
     * @param geom the geom to set
     *//*    
    public void setGeom(JGeometry geom) {
	  this.geom = geom;
    }*/

	
	    /**
	    * @return geomString
	    */
	    
	public String getGeomString() {
		return geomString;
	}

	
	    /**
	     * @param geomString the geomString to set
	     */
	    
	public void setGeomString(String geomString) {
		this.geomString = geomString;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

}
