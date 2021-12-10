package com.enjoyor.modules.dma.entity;

import java.util.List;

import lombok.Data;

@Data
public class Dma111DTO {
	
	// 区域编号
	private String qybh;

	// 几级表
	private String jdlx;

	// 区域名称
	private String qymc;

	// 上级区域
	private String sjqy;
	
	// 总表水量
	private Integer zbsl;

	// 分表数量
	private Integer hhnum;

	// 分表水量
	private Integer sssl;
	
	// 水量差
	private Integer slc;
		
	// 漏失率
	private Float lsl;
	
	private List<Dma111DTO> sonList;

	public String getQybh() {
		return qybh;
	}

	public void setQybh(String qybh) {
		this.qybh = qybh;
	}

	public String getJdlx() {
		return jdlx;
	}

	public void setJdlx(String jdlx) {
		this.jdlx = jdlx;
	}

	public String getQymc() {
		return qymc;
	}

	public void setQymc(String qymc) {
		this.qymc = qymc;
	}

	public String getSjqy() {
		return sjqy;
	}

	public void setSjqy(String sjqy) {
		this.sjqy = sjqy;
	}

	public Integer getZbsl() {
		return zbsl;
	}

	public void setZbsl(Integer zbsl) {
		this.zbsl = zbsl;
	}

	public Integer getHhnum() {
		return hhnum;
	}

	public void setHhnum(Integer hhnum) {
		this.hhnum = hhnum;
	}

	public Integer getSssl() {
		return sssl;
	}

	public void setSssl(Integer sssl) {
		this.sssl = sssl;
	}

	public Integer getSlc() {
		return slc;
	}

	public void setSlc(Integer slc) {
		this.slc = slc;
	}

	public Float getLsl() {
		return lsl;
	}

	public void setLsl(Float lsl) {
		this.lsl = lsl;
	}

	public List<Dma111DTO> getSonList() {
		return sonList;
	}

	public void setSonList(List<Dma111DTO> sonList) {
		this.sonList = sonList;
	}
	
	
}
