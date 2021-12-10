package com.enjoyor.modules.sys.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class SysDictionaryEntity implements Serializable {

	private static final long serialVersionUID = 1L;	
	/**
	 * 字典序号
	 */
	private Long dicId;
	/**
	 * 字典编码
	 */
	private Long dicNum;
	private Long parentId;
	private String parentName;
	/**
	 * 字典类型
	 */
	private String type;
	/**
	 * 字典项
	 */
	private String item;
	/**
	 * 字典子项
	 */
	private String name;
	/**
	 * 字典值
	 */
	private String dicValue;
	/**
	 * 备注
	 */
	private String memo;
	
	public Long getDicId() {
		return dicId;
	}
	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Long getDicNum() {
		return dicNum;
	}
	public void setDicNum(Long dicNum) {
		this.dicNum = dicNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDicValue() {
		return dicValue;
	}
	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
