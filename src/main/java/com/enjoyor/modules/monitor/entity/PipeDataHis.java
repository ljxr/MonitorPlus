package com.enjoyor.modules.monitor.entity;

import java.io.Serializable;
import java.util.Date;

public class PipeDataHis implements Serializable{
	/**
	 * 序号
	 */
	private Long id;
	
	/**
	 * 远传终端号
	 */
	private String ybNuber;
	
	/**
	 * 上传时间
	 */
	private Date time;
	
	/**
	 * 上传时间(string)
	 */
	private String stringTime;
	
	/**
	 * 瞬时流量
	 */
	private Double ss;
	
	/**
	 * 正累计
	 */
	private Double lj1;
	
	/**
	 * 负累积
	 */
	private Double lj2;
	
	/**
	 * 压力
	 */
	private String yl;
	
	/**
	 * 温度1
	 */
	private Double wd1;
	
	/**
	 * 温度2
	 */
	private Double wd2;
	
	/**
	 * 温度3
	 */
	private Double wd3;
	
	/**
	 * 报警
	 */
	private Double bj;
	
	/**
	 * 流速
	 */
	private Double values1;
	
	/**
	 * 电导比
	 */
	private Double values2;
	
	/**
	 * 信号强度
	 */
	private Double values3;
	
	/**
	 * 剩余电量
	 */
	private Double values4;
	
	/**
	 * 净累计
	 */
	private Double jlj;
	
	/**
	 * 接受时间
	 */
	private Date dateNow;
	
	/**
	 * 点位编号
	 */
	private String pointNum;
	
	/**
	 * 状态
	 */
	private Double status;
	
	/**
	 * 插入时间
	 */
	private Date inserDate;
	
	/**
	 * 点位类型
	 */
	private String type;
	
	/**
	 * 余氯
	 */
	private Double eid;
	
	/**
	 * 浊度
	 */
	private Double chlorine;
	
	/**
	 * PH
	 */
	private Double turbidity;
	
	/**
	 * 
	 */
	private Double ph;
	
	/**
	 * TN
	 */
	private Double tn;
	
	/**
	 * TP
	 */
	private Double tp;
	
	/**
	 * NH4
	 */
	private Double nh4;
	
	/**
	 * COD
	 */
	private Double cod;
	
	/**
	 * HZD
	 */
	private Double hzd;
	
	/**
	 * 
	 */
	
	private String alarmType;
	
	/**
	 * LT
	 */
	private Double lt;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYbNuber() {
		return ybNuber;
	}

	public void setYbNuber(String ybNuber) {
		this.ybNuber = ybNuber;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getStringTime() {
		return stringTime;
	}

	public void setStringTime(String stringTime) {
		this.stringTime = stringTime;
	}

	public Double getSs() {
		return ss;
	}

	public void setSs(Double ss) {
		this.ss = ss;
	}

	public Double getLj1() {
		return lj1;
	}

	public void setLj1(Double lj1) {
		this.lj1 = lj1;
	}

	public Double getLj2() {
		return lj2;
	}

	public void setLj2(Double lj2) {
		this.lj2 = lj2;
	}

	public String getYl() {
		return yl;
	}

	public void setYl(String yl) {
		this.yl = yl;
	}

	public Double getWd1() {
		return wd1;
	}

	public void setWd1(Double wd1) {
		this.wd1 = wd1;
	}

	public Double getWd2() {
		return wd2;
	}

	public void setWd2(Double wd2) {
		this.wd2 = wd2;
	}

	public Double getWd3() {
		return wd3;
	}

	public void setWd3(Double wd3) {
		this.wd3 = wd3;
	}

	public Double getBj() {
		return bj;
	}

	public void setBj(Double bj) {
		this.bj = bj;
	}

	public Double getValues1() {
		return values1;
	}

	public void setValues1(Double values1) {
		this.values1 = values1;
	}

	public Double getValues2() {
		return values2;
	}

	public void setValues2(Double values2) {
		this.values2 = values2;
	}

	public Double getValues3() {
		return values3;
	}

	public void setValues3(Double values3) {
		this.values3 = values3;
	}

	public Double getValues4() {
		return values4;
	}

	public void setValues4(Double values4) {
		this.values4 = values4;
	}

	public Double getJlj() {
		return jlj;
	}

	public void setJlj(Double jlj) {
		this.jlj = jlj;
	}

	public Date getDateNow() {
		return dateNow;
	}

	public void setDateNow(Date dateNow) {
		this.dateNow = dateNow;
	}

	public String getPointNum() {
		return pointNum;
	}

	public void setPointNum(String pointNum) {
		this.pointNum = pointNum;
	}

	public Double getStatus() {
		return status;
	}

	public void setStatus(Double status) {
		this.status = status;
	}

	public Date getInserDate() {
		return inserDate;
	}

	public void setInserDate(Date inserDate) {
		this.inserDate = inserDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getEid() {
		return eid;
	}

	public void setEid(Double eid) {
		this.eid = eid;
	}

	public Double getChlorine() {
		return chlorine;
	}

	public void setChlorine(Double chlorine) {
		this.chlorine = chlorine;
	}

	public Double getTurbidity() {
		return turbidity;
	}

	public void setTurbidity(Double turbidity) {
		this.turbidity = turbidity;
	}

	public Double getPh() {
		return ph;
	}

	public void setPh(Double ph) {
		this.ph = ph;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public Double getTn() {
		return tn;
	}

	public void setTn(Double tn) {
		this.tn = tn;
	}

	public Double getTp() {
		return tp;
	}

	public void setTp(Double tp) {
		this.tp = tp;
	}

	public Double getNh4() {
		return nh4;
	}

	public void setNh4(Double nh4) {
		this.nh4 = nh4;
	}

	public Double getCod() {
		return cod;
	}

	public void setCod(Double cod) {
		this.cod = cod;
	}

	public Double getHzd() {
		return hzd;
	}

	public void setHzd(Double hzd) {
		this.hzd = hzd;
	}

	public Double getLt() {
		return lt;
	}

	public void setLt(Double lt) {
		this.lt = lt;
	}
	
	
}
