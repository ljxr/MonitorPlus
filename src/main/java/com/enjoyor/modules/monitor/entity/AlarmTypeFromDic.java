package com.enjoyor.modules.monitor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;


public class AlarmTypeFromDic implements Serializable{

	//字典名称
	private String dicSubitem;
	
	//字典值
	private String dicValue;

	public String getDicSubitem() {
		return dicSubitem;
	}

	public void setDicSubitem(String dicSubitem) {
		this.dicSubitem = dicSubitem;
	}

	public String getDicValue() {
		return dicValue;
	}

	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}
	
	
	
}