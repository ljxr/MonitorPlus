package com.enjoyor.modules.monitor.entity;

public class FlowNightEntity {
	
	// 主键ID
	private int id;
	
	//点位编号
	private String siteId;
		
	//营业所名称
	private String yysName;
	
	// 时间
	private String time;
	
	// 最小夜间流量/日平均流量
	private double value;
	
	// 最小流量
	private double min;
	
	// 出现时间
	private String time2;
	
	// 夜间平均流量
	private double avgNig;
	
	// 日平均流量
	private double avgDay;
	
	// 压力
	private double pressure;
	
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

	public String getYysName() {
		return yysName;
	}

	public void setYysName(String yysName) {
		this.yysName = yysName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public double getAvgNig() {
		return avgNig;
	}

	public void setAvgNig(double avgNig) {
		this.avgNig = avgNig;
	}

	public double getAvgDay() {
		return avgDay;
	}

	public void setAvgDay(double avgDay) {
		this.avgDay = avgDay;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
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

	public double getRbl() {
		return rbl;
	}

	public void setRbl(double rbl) {
		this.rbl = rbl;
	}

	public double getYbl() {
		return ybl;
	}

	public void setYbl(double ybl) {
		this.ybl = ybl;
	}
	
	// 计算日倍率 并 set
	public double calcRbl() {
		if(0 == this.avgDay){
			return 0;
		}
		this.rbl = this.avgDay/this.daysavg;
		return this.rbl;
	}
	
	// 计算夜倍率 并 set
	public double calcYbl() {
		if(0 == this.avgNig){
			return 0;
		}
		this.ybl = this.avgNig/this.nightsavg;
		return this.ybl;
	}

}

