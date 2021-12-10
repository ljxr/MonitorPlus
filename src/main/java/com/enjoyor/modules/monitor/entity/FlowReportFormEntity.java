package com.enjoyor.modules.monitor.entity;

public class FlowReportFormEntity {
	
	//点位编号
	private String siteId;
	
	//点位名称
	private String siteName;
	
	// 00:00压力
	private String yl00;
	
	// 00:00流量
	private String ss00;
	
	// 03:00压力
	private String yl03;
		
	// 03:00流量
	private String ss03;
	
	// 06:00压力
	private String yl06;
		
	// 06:00流量
	private String ss06;
	
	// 09:00压力
	private String yl09;
		
	// 09:00流量
	private String ss09;
	
	// 12:00压力
	private String yl12;
		
	// 12:00流量
	private String ss12;

	// 15:00压力
	private String yl15;
		
	// 15:00流量
	private String ss15;
	
	// 18:00压力
	private String yl18;
		
	// 18:00流量
	private String ss18;
	
	// 21:00压力
	private String yl21;
		
	// 21:00流量
	private String ss21;
	
	// 最小流量
	private double min;

	// 日平均流量
	private double avgDay;
	
	// 夜间平均流量
	private double avgNig;
	
	// 最小流量/日平均流量
	private double value2;
	
	// 七日日平均
	private double daysavg;
		
	// 七日夜平均
	private double nightsavg;
		
	// 日倍率：七日日平均/日平均
	private double rbl;
			
	// 夜倍率：七日夜平均/夜平均
	private double ybl;	
		
	

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

	public String getYl00() {
		return yl00;
	}

	public void setYl00(String yl00) {
		this.yl00 = yl00;
	}

	public String getSs00() {
		return ss00;
	}

	public void setSs00(String ss00) {
		this.ss00 = ss00;
	}

	public String getYl03() {
		return yl03;
	}

	public void setYl03(String yl03) {
		this.yl03 = yl03;
	}

	public String getSs03() {
		return ss03;
	}

	public void setSs03(String ss03) {
		this.ss03 = ss03;
	}

	public String getYl06() {
		return yl06;
	}

	public void setYl06(String yl06) {
		this.yl06 = yl06;
	}

	public String getSs06() {
		return ss06;
	}

	public void setSs06(String ss06) {
		this.ss06 = ss06;
	}

	public String getYl09() {
		return yl09;
	}

	public void setYl09(String yl09) {
		this.yl09 = yl09;
	}

	public String getSs09() {
		return ss09;
	}

	public void setSs09(String ss09) {
		this.ss09 = ss09;
	}

	public String getYl12() {
		return yl12;
	}

	public void setYl12(String yl12) {
		this.yl12 = yl12;
	}

	public String getSs12() {
		return ss12;
	}

	public void setSs12(String ss12) {
		this.ss12 = ss12;
	}

	public String getYl15() {
		return yl15;
	}

	public void setYl15(String yl15) {
		this.yl15 = yl15;
	}

	public String getSs15() {
		return ss15;
	}

	public void setSs15(String ss15) {
		this.ss15 = ss15;
	}

	public String getYl18() {
		return yl18;
	}

	public void setYl18(String yl18) {
		this.yl18 = yl18;
	}

	public String getSs18() {
		return ss18;
	}

	public void setSs18(String ss18) {
		this.ss18 = ss18;
	}

	public String getYl21() {
		return yl21;
	}

	public void setYl21(String yl21) {
		this.yl21 = yl21;
	}

	public String getSs21() {
		return ss21;
	}

	public void setSs21(String ss21) {
		this.ss21 = ss21;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getAvgDay() {
		return avgDay;
	}

	public void setAvgDay(double avgDay) {
		this.avgDay = avgDay;
	}

	public double getAvgNig() {
		return avgNig;
	}

	public void setAvgNig(double avgNig) {
		this.avgNig = avgNig;
	}

	public double getValue2() {
		return value2;
	}

	public void setValue2(double value2) {
		this.value2 = value2;
	}
	
	public double getDaysavg() {
		return daysavg;
	}

	public void setDaysavg(double daysavg) {
		this.daysavg = daysavg;
	}

	public double getNightsavg() {
		return nightsavg;
	}

	public void setNightsavg(double nightsavg) {
		this.nightsavg = nightsavg;
	}
	
	
	// 计算日倍率 并 set
	public double calcRbl() {
		if(0 == this.avgDay){
			return 0;
		}
		this.rbl = this.daysavg/this.avgDay;
		return this.rbl;
	}
	
	// 计算夜倍率 并 set
	public double calcYbl() {
		if(0 == this.avgNig){
			return 0;
		}
		this.ybl = this.nightsavg/this.avgNig;
		return this.ybl;
	}
}
