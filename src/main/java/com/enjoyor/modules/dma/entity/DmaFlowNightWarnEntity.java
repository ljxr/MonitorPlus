package com.enjoyor.modules.dma.entity;

import com.enjoyor.modules.monitor.entity.FlowNightEntity;

public class DmaFlowNightWarnEntity extends FlowNightEntity{
	
	// dma分区名称
	private String dareaName;
	
	// 点位名称
	private String siteName;
	
	// 点位x
	private String x;
	
	// 点位y
	private String y;
	
	// 最小流量
	private double min;
	
	// 出现时间
	private String time2;
	
	// 夜间平均流量
	private double avgNig;
	
	// 夜倍率：夜平均/七日夜平均
	private double ybl;
	
	// 七日夜平均
	private double nightsavg;
	
	// 日平均流量
	private double avgDay;

	// 七日日平均
	private double daysavg;
	
	// 日倍率：日平均/七日日平均
	private double rbl;
	
	// 压力
	private double pressure;
	
	// 日倍率+夜倍率
	private double rybl;

	
	
	
	
	public String getDareaName() {
		return dareaName;
	}

	public void setDareaName(String dareaName) {
		this.dareaName = dareaName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
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

	public double getYbl() {
		return ybl;
	}

	public void setYbl(double ybl) {
		this.ybl = ybl;
	}

	public double getNightsavg() {
		return nightsavg;
	}

	public void setNightsavg(double nightsavg) {
		this.nightsavg = nightsavg;
	}

	public double getAvgDay() {
		return avgDay;
	}

	public void setAvgDay(double avgDay) {
		this.avgDay = avgDay;
	}

	public double getDaysavg() {
		return daysavg;
	}

	public void setDaysavg(double daysavg) {
		this.daysavg = daysavg;
	}

	public double getRbl() {
		return rbl;
	}

	public void setRbl(double rbl) {
		this.rbl = rbl;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getRybl() {
		return rybl;
	}

	public void setRybl(double rybl) {
		this.rybl = rybl;
	}
	
}
