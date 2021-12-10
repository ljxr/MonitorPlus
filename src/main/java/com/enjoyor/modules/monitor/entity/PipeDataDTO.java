package com.enjoyor.modules.monitor.entity;

import java.util.Date;

public class PipeDataDTO {

	/**
	 * 点位编号
	 */
	private String siteId;
	
	/**
	 * 点位名称
	 */
	private String siteName;
	
	/**
	 * 压力
	 */
	private String yl;
	
	/**
	 * 瞬时流量
	 */
	private Double ss;
	
	/**
	 * 正累计
	 */
	private Double jlj;
	
	/**
	 * 余氯
	 */
	private Double chlorine;
	
	/**
	 * 浊度
	 */
	private Double turbidity;
	
	/**
	 * PH
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
	 * 时间
	 */
	private String time;
	
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

	private Date time2;

	public Date getTime2() {
		return time2;
	}

	public void setTime2(Date time2) {
		this.time2 = time2;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getYl() {
		return yl;
	}

	public void setYl(String yl) {
		this.yl = yl;
	}

	public Double getSs() {
		return ss;
	}

	public void setSs(Double ss) {
		this.ss = ss;
	}

	public Double getJlj() {
		return jlj;
	}

	public void setJlj(Double jlj) {
		this.jlj = jlj;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
