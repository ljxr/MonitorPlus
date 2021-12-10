package com.enjoyor.modules.monitor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;


public class MonitorTotalEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	
	private String siteId;
	
	private double turbidityValue;
	
	private double phValue;
	
	private double chlorineValue;
	
	private double codValue;
	
	private double tnValue;
	
	private double tpValue;
	
	private double ssValue;
	
	private String rain;
	
	private String temperature;
	
	private double pressure;
	
	private double flow;
	
	private double totalFlow;
	
	private double ltValue;

	private String time;
		
	private double pressureOut;
	
	private double pressureSet;	
	
	private double ltSetValue;
	
	private String pumpStart;
	
	private String pumpStop;
	
	private String pumpFault;
	
	private String pumpA;
	
	private String pumpR;
	
	private String pumpU;
	
	private String pumpP;

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

	public double getTurbidityValue() {
		return turbidityValue;
	}

	public void setTurbidityValue(double turbidityValue) {
		this.turbidityValue = turbidityValue;
	}

	public double getPhValue() {
		return phValue;
	}

	public void setPhValue(double phValue) {
		this.phValue = phValue;
	}

	public double getChlorineValue() {
		return chlorineValue;
	}

	public void setChlorineValue(double chlorineValue) {
		this.chlorineValue = chlorineValue;
	}

	public double getCodValue() {
		return codValue;
	}

	public void setCodValue(double codValue) {
		this.codValue = codValue;
	}

	public double getTnValue() {
		return tnValue;
	}

	public void setTnValue(double tnValue) {
		this.tnValue = tnValue;
	}

	public double getTpValue() {
		return tpValue;
	}

	public void setTpValue(double tpValue) {
		this.tpValue = tpValue;
	}

	public double getSsValue() {
		return ssValue;
	}

	public void setSsValue(double ssValue) {
		this.ssValue = ssValue;
	}

	public String getRain() {
		return rain;
	}

	public void setRain(String rain) {
		this.rain = rain;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getPressureOut() {
		return pressureOut;
	}

	public void setPressureOut(double pressureOut) {
		this.pressureOut = pressureOut;
	}

	public double getPressureSet() {
		return pressureSet;
	}

	public void setPressureSet(double pressureSet) {
		this.pressureSet = pressureSet;
	}

	public double getFlow() {
		return flow;
	}

	public void setFlow(double flow) {
		this.flow = flow;
	}

	public double getTotalFlow() {
		return totalFlow;
	}

	public void setTotalFlow(double totalFlow) {
		this.totalFlow = totalFlow;
	}

	public double getLtValue() {
		return ltValue;
	}

	public void setLtValue(double ltValue) {
		this.ltValue = ltValue;
	}

	public double getLtSetValue() {
		return ltSetValue;
	}

	public void setLtSetValue(double ltSetValue) {
		this.ltSetValue = ltSetValue;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPumpStart() {
		return pumpStart;
	}

	public void setPumpStart(String pumpStart) {
		this.pumpStart = pumpStart;
	}

	public String getPumpStop() {
		return pumpStop;
	}

	public void setPumpStop(String pumpStop) {
		this.pumpStop = pumpStop;
	}

	public String getPumpFault() {
		return pumpFault;
	}

	public void setPumpFault(String pumpFault) {
		this.pumpFault = pumpFault;
	}

	public String getPumpA() {
		return pumpA;
	}

	public void setPumpA(String pumpA) {
		this.pumpA = pumpA;
	}

	public String getPumpR() {
		return pumpR;
	}

	public void setPumpR(String pumpR) {
		this.pumpR = pumpR;
	}

	public String getPumpU() {
		return pumpU;
	}

	public void setPumpU(String pumpU) {
		this.pumpU = pumpU;
	}

	public String getPumpP() {
		return pumpP;
	}

	public void setPumpP(String pumpP) {
		this.pumpP = pumpP;
	}

}