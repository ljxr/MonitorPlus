package com.enjoyor.modules.monitor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;


public class ThresholdAndName implements Serializable{

	//sys_threshold表主键
	private int id;
	
	//点位ID
	private String siteId;
	
	//指标ID
	private int thresholdId;
	
	//报警下限
	private double lowValue;
	
	//故障下限
	private double lowerValue;
	
	//报警上限
	private double highValue;
	
	//故障上限
	private double higherValue;
	
	
	private int thresholdOrderNum;
	
	//单位
	private String unit;
	
	//指标名称
	private String dicSubitem;

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

	public int getThresholdId() {
		return thresholdId;
	}

	public void setThresholdId(int thresholdId) {
		this.thresholdId = thresholdId;
	}

	public double getLowValue() {
		return lowValue;
	}

	public void setLowValue(double lowValue) {
		this.lowValue = lowValue;
	}

	public double getLowerValue() {
		return lowerValue;
	}

	public void setLowerValue(double lowerValue) {
		this.lowerValue = lowerValue;
	}

	public double getHighValue() {
		return highValue;
	}

	public void setHighValue(double highValue) {
		this.highValue = highValue;
	}

	public double getHigherValue() {
		return higherValue;
	}

	public void setHigherValue(double higherValue) {
		this.higherValue = higherValue;
	}

	public int getThresholdOrderNum() {
		return thresholdOrderNum;
	}

	public void setThresholdOrderNum(int thresholdOrderNum) {
		this.thresholdOrderNum = thresholdOrderNum;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDicSubitem() {
		return dicSubitem;
	}

	public void setDicSubitem(String dicSubitem) {
		this.dicSubitem = dicSubitem;
	}
	
}