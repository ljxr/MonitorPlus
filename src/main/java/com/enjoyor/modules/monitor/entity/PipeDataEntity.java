package com.enjoyor.modules.monitor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;


public class PipeDataEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JSONField(ordinal=1)
	private Long id;
	
	@JSONField(ordinal=2)
	private String facNum;
	
	@JSONField(ordinal=3)
	private String siteId;
	
	private String siteName;
	
	@JSONField(ordinal=5,format="yyyy-MM-dd HH:mm:ss")
	private Date time;
	
	@JSONField(ordinal=4)
	private String parentId;
	
	private String parentName;
	
	@JSONField(ordinal=6)
	private String type;
	
	@JSONField(ordinal=7)
	private String dataType;
	
	@JSONField(ordinal=8)
	private Double ins;
	
	@JSONField(ordinal=9)
	private Double yl;
	
	@JSONField(ordinal=10)
	private Double jlj;
	
	@JSONField(ordinal=11)
	private Double cho;
	
	@JSONField(ordinal=12)
	private Double ph;
	
	@JSONField(ordinal=13)
	private Double tur;
	
	@JSONField(ordinal=14)
	private Double cod;
	
	@JSONField(ordinal=15)
	private Double tn;
	
	@JSONField(ordinal=16)
	private Double tp;
	
	@JSONField(ordinal=17)
	private Double ss;
	
	@JSONField(ordinal=18)
	private Double level;
	
	@JSONField(ordinal=19)
	private Double status;
	
	@JSONField(ordinal=20)
	private Double errors;
	
	@JSONField(ordinal=21)
	private String insAlarm;
	
	@JSONField(ordinal=22)
	private String ylAlarm;
	
	@JSONField(ordinal=23)
	private String jljAlarm;
	
	@JSONField(ordinal=24)
	private String choAlarm;
	
	@JSONField(ordinal=25)
	private String phAlarm;
	
	@JSONField(ordinal=26)
	private String turAlarm;
	
	@JSONField(ordinal=27)
	private String codAlarm;
	
	@JSONField(ordinal=28)
	private String tnAlarm;
	
	@JSONField(ordinal=29)
	private String tpAlarm;
	
	@JSONField(ordinal=30)
	private String ssAlarm;
	
	@JSONField(ordinal=31)
	private String levelAlarm;
	
	@JSONField(ordinal=32)
	private String statusAlarm;
	
	@JSONField(ordinal=33)
	private String errorsAlarm;		
	
	@JSONField(ordinal=34)
	private String time2;

	@JSONField(ordinal=35)
	private Double ylset;
	
	@JSONField(ordinal=36)
	private Double levelset;
	
	@JSONField(ordinal=37)
	private Double acvpv;   //调节阀开度反馈
	
	@JSONField(ordinal=38)
	private Double acvsv;   //调节阀开度设定
	
	@JSONField(ordinal=39)
	private Boolean evol;   //电动阀全开
	
	@JSONField(ordinal=40)
	private Boolean evcl;   //电动阀全关
	
	@JSONField(ordinal=41)
	private Boolean evos;   //电动阀开命令
	
	@JSONField(ordinal=42)
	private Boolean evcs;   //电动阀关命令
	
	@JSONField(ordinal=43)
	private Boolean auto;   //水泵远程
	
	@JSONField(ordinal=44)
	private Boolean fault;   //水泵故障
	
	@JSONField(ordinal=45)
	private Boolean run;   //水泵运行
	
	@JSONField(ordinal=46)
	private Boolean start;   //水泵启动
	
	@JSONField(ordinal=47)
	private Boolean stop;   //水泵停止
	
	@JSONField(ordinal=48)
	private Boolean reset;   //水泵复位
	
	@JSONField(ordinal=49)
	private Double fs;   //水泵设定频率
	
	@JSONField(ordinal=50)
	private Double fr;   //水泵运行频率
	
	@JSONField(ordinal=51)
	private Double ar;   //水泵运行电流
	
	@JSONField(ordinal=52)
	private Double vdfar;   //变频器运行电流
	
	@JSONField(ordinal=53)
	private Double vdffr;   //变频器运行频率
	
	@JSONField(ordinal=54)
	private String eta;   //水泵运行状态
	
	@JSONField(ordinal=55)
	private String mode;   //水泵运行模式
	
	@JSONField(ordinal=56)
	private String outf;   //其他变频器运行频率
	
	@JSONField(ordinal=57)
	private Double fcr;   //水泵变频运行
	
	@JSONField(ordinal=58)
	private Double pfr;   //水泵工频运行
	
	@JSONField(ordinal=59)
	private Double ylout;
	
	
	public Double getYlout() {
		return ylout;
	}

	public void setYlout(Double ylout) {
		this.ylout = ylout;
	}

	public Double getLevelset() {
		return levelset;
	}

	public void setLevelset(Double levelset) {
		this.levelset = levelset;
	}

	public Double getAcvpv() {
		return acvpv;
	}

	public void setAcvpv(Double acvpv) {
		this.acvpv = acvpv;
	}

	public Double getAcvsv() {
		return acvsv;
	}

	public void setAcvsv(Double acvsv) {
		this.acvsv = acvsv;
	}


	public Boolean getEvol() {
		return evol;
	}

	public void setEvol(Boolean evol) {
		this.evol = evol;
	}

	public Boolean getEvcl() {
		return evcl;
	}

	public void setEvcl(Boolean evcl) {
		this.evcl = evcl;
	}

	public Boolean getEvos() {
		return evos;
	}

	public void setEvos(Boolean evos) {
		this.evos = evos;
	}

	public Boolean getEvcs() {
		return evcs;
	}

	public void setEvcs(Boolean evcs) {
		this.evcs = evcs;
	}

	public Boolean getAuto() {
		return auto;
	}

	public void setAuto(Boolean auto) {
		this.auto = auto;
	}

	public Boolean getFault() {
		return fault;
	}

	public void setFault(Boolean fault) {
		this.fault = fault;
	}

	public Boolean getRun() {
		return run;
	}

	public void setRun(Boolean run) {
		this.run = run;
	}

	public Boolean getStart() {
		return start;
	}

	public void setStart(Boolean start) {
		this.start = start;
	}

	public Boolean getStop() {
		return stop;
	}

	public void setStop(Boolean stop) {
		this.stop = stop;
	}

	public Boolean getReset() {
		return reset;
	}

	public void setReset(Boolean reset) {
		this.reset = reset;
	}

	public Double getFs() {
		return fs;
	}

	public void setFs(Double fs) {
		this.fs = fs;
	}

	public Double getFr() {
		return fr;
	}

	public void setFr(Double fr) {
		this.fr = fr;
	}

	public Double getAr() {
		return ar;
	}

	public void setAr(Double ar) {
		this.ar = ar;
	}

	public Double getVdfar() {
		return vdfar;
	}

	public void setVdfar(Double vdfar) {
		this.vdfar = vdfar;
	}

	public Double getVdffr() {
		return vdffr;
	}

	public void setVdffr(Double vdffr) {
		this.vdffr = vdffr;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getOutf() {
		return outf;
	}

	public void setOutf(String outf) {
		this.outf = outf;
	}

	public Double getFcr() {
		return fcr;
	}

	public void setFcr(Double fcr) {
		this.fcr = fcr;
	}

	public Double getPfr() {
		return pfr;
	}

	public void setPfr(Double pfr) {
		this.pfr = pfr;
	}

	public Double getYlset() {
		return ylset;
	}

	public void setYlset(Double ylset) {
		this.ylset = ylset;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public String getFacNum() {
		return facNum;
	}

	public void setFacNum(String facNum) {
		this.facNum = facNum;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
 
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Double getIns() {
		return ins;
	}

	public void setIns(Double ins) {
		this.ins = ins;
	}

	public Double getYl() {
		return yl;
	}

	public void setYl(Double yl) {
		this.yl = yl;
	}

	public Double getJlj() {
		return jlj;
	}

	public void setJlj(Double jlj) {
		this.jlj = jlj;
	}

	public Double getCho() {
		return cho;
	}

	public void setCho(Double cho) {
		this.cho = cho;
	}

	public Double getPh() {
		return ph;
	}

	public void setPh(Double ph) {
		this.ph = ph;
	}

	public Double getTur() {
		return tur;
	}

	public void setTur(Double tur) {
		this.tur = tur;
	}

	public Double getCod() {
		return cod;
	}

	public void setCod(Double cod) {
		this.cod = cod;
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

	public Double getSs() {
		return ss;
	}

	public void setSs(Double ss) {
		this.ss = ss;
	}

	public Double getLevel() {
		return level;
	}

	public void setLevel(Double level) {
		this.level = level;
	}

	public Double getStatus() {
		return status;
	}

	public void setStatus(Double status) {
		this.status = status;
	}

	public Double getErrors() {
		return errors;
	}

	public void setErrors(Double errors) {
		this.errors = errors;
	}

	public String getInsAlarm() {
		return insAlarm;
	}

	public void setInsAlarm(String insAlarm) {
		this.insAlarm = insAlarm;
	}

	public String getYlAlarm() {
		return ylAlarm;
	}

	public void setYlAlarm(String ylAlarm) {
		this.ylAlarm = ylAlarm;
	}

	public String getJljAlarm() {
		return jljAlarm;
	}

	public void setJljAlarm(String jljAlarm) {
		this.jljAlarm = jljAlarm;
	}

	public String getChoAlarm() {
		return choAlarm;
	}

	public void setChoAlarm(String choAlarm) {
		this.choAlarm = choAlarm;
	}

	public String getPhAlarm() {
		return phAlarm;
	}

	public void setPhAlarm(String phAlarm) {
		this.phAlarm = phAlarm;
	}

	public String getTurAlarm() {
		return turAlarm;
	}

	public void setTurAlarm(String turAlarm) {
		this.turAlarm = turAlarm;
	}

	public String getCodAlarm() {
		return codAlarm;
	}

	public void setCodAlarm(String codAlarm) {
		this.codAlarm = codAlarm;
	}

	public String getTnAlarm() {
		return tnAlarm;
	}

	public void setTnAlarm(String tnAlarm) {
		this.tnAlarm = tnAlarm;
	}

	public String getTpAlarm() {
		return tpAlarm;
	}

	public void setTpAlarm(String tpAlarm) {
		this.tpAlarm = tpAlarm;
	}

	public String getSsAlarm() {
		return ssAlarm;
	}

	public void setSsAlarm(String ssAlarm) {
		this.ssAlarm = ssAlarm;
	}

	public String getLevelAlarm() {
		return levelAlarm;
	}

	public void setLevelAlarm(String levelAlarm) {
		this.levelAlarm = levelAlarm;
	}

	public String getStatusAlarm() {
		return statusAlarm;
	}

	public void setStatusAlarm(String statusAlarm) {
		this.statusAlarm = statusAlarm;
	}

	public String getErrorsAlarm() {
		return errorsAlarm;
	}

	public void setErrorsAlarm(String errorsAlarm) {
		this.errorsAlarm = errorsAlarm;
	}

	@Override
	public String toString() {
		return "PipeDataEntity [id=" + id + ", facNum=" + facNum + ", siteId=" + siteId + ", siteName=" + siteName
				+ ", time=" + time + ", parentId=" + parentId + ", parentName=" + parentName + ", type=" + type
				+ ", dataType=" + dataType + ", ins=" + ins + ", yl=" + yl + ", jlj=" + jlj + ", cho=" + cho + ", ph="
				+ ph + ", tur=" + tur + ", cod=" + cod + ", tn=" + tn + ", tp=" + tp + ", ss=" + ss + ", level=" + level
				+ ", status=" + status + ", errors=" + errors + ", insAlarm=" + insAlarm + ", ylAlarm=" + ylAlarm
				+ ", jljAlarm=" + jljAlarm + ", choAlarm=" + choAlarm + ", phAlarm=" + phAlarm + ", turAlarm="
				+ turAlarm + ", codAlarm=" + codAlarm + ", tnAlarm=" + tnAlarm + ", tpAlarm=" + tpAlarm + ", ssAlarm="
				+ ssAlarm + ", levelAlarm=" + levelAlarm + ", statusAlarm=" + statusAlarm + ", errorsAlarm="
				+ errorsAlarm + ", time2=" + time2 + ", ylset=" + ylset + ", levelset=" + levelset + ", acvpv=" + acvpv
				+ ", acvsv=" + acvsv + ", evol=" + evol + ", evcl=" + evcl + ", evos=" + evos + ", evcs=" + evcs
				+ ", auto=" + auto + ", fault=" + fault + ", run=" + run + ", start=" + start + ", stop=" + stop
				+ ", reset=" + reset + ", fs=" + fs + ", fr=" + fr + ", ar=" + ar + ", vdfar=" + vdfar + ", vdffr="
				+ vdffr + ", eta=" + eta + ", mode=" + mode + ", outf=" + outf + ", fcr=" + fcr + ", pfr=" + pfr + "]";
	}

	
	
}
