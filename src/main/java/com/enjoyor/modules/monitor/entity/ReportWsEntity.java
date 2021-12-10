package com.enjoyor.modules.monitor.entity;

import java.util.Date;

public class ReportWsEntity {

  // 主键ID
  private Long id;

  // 污水厂ID
  private String siteId;

  // 污水厂名称
  private String siteName;

  // 字符串时间
  private String stringTime;

  // 时间
  private java.util.Date time;

  // 总进水量
  private String waterIn;

  // 总出水量
  private String waterOut;

  // 污泥处理量（泥饼）
  private String mudHandle;

  // 干污泥量
  private String mudDry;

  // 产泥率
  private String mudYield;

  // 脱水前污泥量
  private String mudBfoDwater;

  // 污泥处置费用
  private String mudCost;

  // 药剂总费用
  private String medCost;

  // 单位药耗
  private String medCostPerT;

  // 次氯酸钠消耗量
  private String clsnNum;

  // 金额
  private String clsnCost;

  // 千吨水氯耗量
  private String clsnQdhl;

  // 单位成本
  private String clsnUnitCost;

  // PAC消耗量
  private String pacNum;

  // 金额
  private String pacCost;

  // 千吨水PAC耗量
  private String pacQdhl;

  // 单位成本
  private String pacUnitCost;

  // 碳源消耗量
  private String cNum;

  // 金额
  private String cCost;

  // 千吨水碳源耗量
  private String cQdhl;

  // 单位成本
  private String cUnitCost;

  // PAM
  private String pamNum;

  // 金额
  private String pamCost;

  // 千吨水铁盐耗量
  private String pamQdhl;

  // 单位成本
  private String pamUnitCost;

  // 石灰消耗量
  private String limeNum;

  // 金额
  private String limeCost;

  // 千吨水石灰耗量
  private String limeQdhl;

  // 单位成本
  private String limeUnitCost;

  // 化验药品费用
  private String testMedCost;

  // 用电量
  private String eleNum;

  // 电费单价
  private String elePrice;

  // 金额
  private String eleFee;

  // 单位电耗
  private String elePerT;

  // 用电单耗
  private String eleYddh;

  // 自来水用量
  private String waterUsage;

  // 自来水费
  private String waterCost;

  // 技术改造及设备更新费用合计
  private String techPrjctTotal;

  // 技术改造及设备更新费用1
  private String techPrjct1;

  // 技术改造及设备更新费用2
  private String techPrjct2;

  // 技术改造及设备更新费用3
  private String techPrjct3;

  // 管网抢修、维护费用
  private String pipeRprFee;

  // 物料费
  private String materialFee;

  // 人工费
  private String laborFee;

  // 其他
  private String otherFee;

  // 水厂设备维护、检修费用
  private String scRprFee;

  // 在线设备维护费用
  private String equipOlRprFee;

  // 设备折旧费
  private String depreciationFee;

  // 水厂运营费用
  private String scOperateFee;

  // 运营单耗
  private String oprtPerT;

  // 去年运营单耗
  private String lastyearOprtPerT;

  // 乙酸钠消耗量
  private String ysnNum;

  // 乙酸钠金额
  private String ysnCost;

  // 千吨水乙酸钠耗量
  private String ysnQdhl;

  // 乙酸钠单位成本
  private String ysnUnitCost;

  // 安全整改及零星工程
  private String safeReform;

  // 物料费2
  private String materialFee2;

  // 人工费2（提标改造）
  private String laborFee2;

  // 其他2（污泥干化）
  private String otherFee2;

  public Long getId() {
    return id;
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

  public String getSiteName() {
    return siteName;
  }

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  public String getStringTime() {
    return stringTime;
  }

  public void setStringTime(String stringTime) {
    this.stringTime = stringTime;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getWaterIn() {
    return waterIn;
  }

  public void setWaterIn(String waterIn) {
    this.waterIn = waterIn;
  }

  public String getWaterOut() {
    return waterOut;
  }

  public void setWaterOut(String waterOut) {
    this.waterOut = waterOut;
  }

  public String getMudHandle() {
    return mudHandle;
  }

  public void setMudHandle(String mudHandle) {
    this.mudHandle = mudHandle;
  }

  public String getMudDry() {
    return mudDry;
  }

  public void setMudDry(String mudDry) {
    this.mudDry = mudDry;
  }

  public String getMudYield() {
    return mudYield;
  }

  public void setMudYield(String mudYield) {
    this.mudYield = mudYield;
  }

  public String getMudBfoDwater() {
    return mudBfoDwater;
  }

  public void setMudBfoDwater(String mudBfoDwater) {
    this.mudBfoDwater = mudBfoDwater;
  }

  public String getMudCost() {
    return mudCost;
  }

  public void setMudCost(String mudCost) {
    this.mudCost = mudCost;
  }

  public String getMedCost() {
    return medCost;
  }

  public void setMedCost(String medCost) {
    this.medCost = medCost;
  }

  public String getMedCostPerT() {
    return medCostPerT;
  }

  public void setMedCostPerT(String medCostPerT) {
    this.medCostPerT = medCostPerT;
  }

  public String getClsnNum() {
    return clsnNum;
  }

  public void setClsnNum(String clsnNum) {
    this.clsnNum = clsnNum;
  }

  public String getClsnCost() {
    return clsnCost;
  }

  public void setClsnCost(String clsnCost) {
    this.clsnCost = clsnCost;
  }

  public String getClsnQdhl() {
    return clsnQdhl;
  }

  public void setClsnQdhl(String clsnQdhl) {
    this.clsnQdhl = clsnQdhl;
  }

  public String getClsnUnitCost() {
    return clsnUnitCost;
  }

  public void setClsnUnitCost(String clsnUnitCost) {
    this.clsnUnitCost = clsnUnitCost;
  }

  public String getPacNum() {
    return pacNum;
  }

  public void setPacNum(String pacNum) {
    this.pacNum = pacNum;
  }

  public String getPacCost() {
    return pacCost;
  }

  public void setPacCost(String pacCost) {
    this.pacCost = pacCost;
  }

  public String getPacQdhl() {
    return pacQdhl;
  }

  public void setPacQdhl(String pacQdhl) {
    this.pacQdhl = pacQdhl;
  }

  public String getPacUnitCost() {
    return pacUnitCost;
  }

  public void setPacUnitCost(String pacUnitCost) {
    this.pacUnitCost = pacUnitCost;
  }

  public String getcNum() {
    return cNum;
  }

  public void setcNum(String cNum) {
    this.cNum = cNum;
  }

  public String getcCost() {
    return cCost;
  }

  public void setcCost(String cCost) {
    this.cCost = cCost;
  }

  public String getcQdhl() {
    return cQdhl;
  }

  public void setcQdhl(String cQdhl) {
    this.cQdhl = cQdhl;
  }

  public String getcUnitCost() {
    return cUnitCost;
  }

  public void setcUnitCost(String cUnitCost) {
    this.cUnitCost = cUnitCost;
  }

  public String getPamNum() {
    return pamNum;
  }

  public void setPamNum(String pamNum) {
    this.pamNum = pamNum;
  }

  public String getPamCost() {
    return pamCost;
  }

  public void setPamCost(String pamCost) {
    this.pamCost = pamCost;
  }

  public String getPamQdhl() {
    return pamQdhl;
  }

  public void setPamQdhl(String pamQdhl) {
    this.pamQdhl = pamQdhl;
  }

  public String getPamUnitCost() {
    return pamUnitCost;
  }

  public void setPamUnitCost(String pamUnitCost) {
    this.pamUnitCost = pamUnitCost;
  }

  public String getLimeNum() {
    return limeNum;
  }

  public void setLimeNum(String limeNum) {
    this.limeNum = limeNum;
  }

  public String getLimeCost() {
    return limeCost;
  }

  public void setLimeCost(String limeCost) {
    this.limeCost = limeCost;
  }

  public String getLimeQdhl() {
    return limeQdhl;
  }

  public void setLimeQdhl(String limeQdhl) {
    this.limeQdhl = limeQdhl;
  }

  public String getLimeUnitCost() {
    return limeUnitCost;
  }

  public void setLimeUnitCost(String limeUnitCost) {
    this.limeUnitCost = limeUnitCost;
  }

  public String getTestMedCost() {
    return testMedCost;
  }

  public void setTestMedCost(String testMedCost) {
    this.testMedCost = testMedCost;
  }

  public String getEleNum() {
    return eleNum;
  }

  public void setEleNum(String eleNum) {
    this.eleNum = eleNum;
  }

  public String getElePrice() {
    return elePrice;
  }

  public void setElePrice(String elePrice) {
    this.elePrice = elePrice;
  }

  public String getEleFee() {
    return eleFee;
  }

  public void setEleFee(String eleFee) {
    this.eleFee = eleFee;
  }

  public String getElePerT() {
    return elePerT;
  }

  public void setElePerT(String elePerT) {
    this.elePerT = elePerT;
  }

  public String getEleYddh() {
    return eleYddh;
  }

  public void setEleYddh(String eleYddh) {
    this.eleYddh = eleYddh;
  }

  public String getWaterUsage() {
    return waterUsage;
  }

  public void setWaterUsage(String waterUsage) {
    this.waterUsage = waterUsage;
  }

  public String getWaterCost() {
    return waterCost;
  }

  public void setWaterCost(String waterCost) {
    this.waterCost = waterCost;
  }

  public String getTechPrjctTotal() {
    return techPrjctTotal;
  }

  public void setTechPrjctTotal(String techPrjctTotal) {
    this.techPrjctTotal = techPrjctTotal;
  }

  public String getTechPrjct1() {
    return techPrjct1;
  }

  public void setTechPrjct1(String techPrjct1) {
    this.techPrjct1 = techPrjct1;
  }

  public String getTechPrjct2() {
    return techPrjct2;
  }

  public void setTechPrjct2(String techPrjct2) {
    this.techPrjct2 = techPrjct2;
  }

  public String getTechPrjct3() {
    return techPrjct3;
  }

  public void setTechPrjct3(String techPrjct3) {
    this.techPrjct3 = techPrjct3;
  }

  public String getPipeRprFee() {
    return pipeRprFee;
  }

  public void setPipeRprFee(String pipeRprFee) {
    this.pipeRprFee = pipeRprFee;
  }

  public String getMaterialFee() {
    return materialFee;
  }

  public void setMaterialFee(String materialFee) {
    this.materialFee = materialFee;
  }

  public String getLaborFee() {
    return laborFee;
  }

  public void setLaborFee(String laborFee) {
    this.laborFee = laborFee;
  }

  public String getOtherFee() {
    return otherFee;
  }

  public void setOtherFee(String otherFee) {
    this.otherFee = otherFee;
  }

  public String getScRprFee() {
    return scRprFee;
  }

  public void setScRprFee(String scRprFee) {
    this.scRprFee = scRprFee;
  }

  public String getEquipOlRprFee() {
    return equipOlRprFee;
  }

  public void setEquipOlRprFee(String equipOlRprFee) {
    this.equipOlRprFee = equipOlRprFee;
  }

  public String getDepreciationFee() {
    return depreciationFee;
  }

  public void setDepreciationFee(String depreciationFee) {
    this.depreciationFee = depreciationFee;
  }

  public String getScOperateFee() {
    return scOperateFee;
  }

  public void setScOperateFee(String scOperateFee) {
    this.scOperateFee = scOperateFee;
  }

  public String getOprtPerT() {
    return oprtPerT;
  }

  public void setOprtPerT(String oprtPerT) {
    this.oprtPerT = oprtPerT;
  }

  public String getLastyearOprtPerT() {
    return lastyearOprtPerT;
  }

  public void setLastyearOprtPerT(String lastyearOprtPerT) {
    this.lastyearOprtPerT = lastyearOprtPerT;
  }

  public String getYsnNum() {
    return ysnNum;
  }

  public void setYsnNum(String ysnNum) {
    this.ysnNum = ysnNum;
  }

  public String getYsnCost() {
    return ysnCost;
  }

  public void setYsnCost(String ysnCost) {
    this.ysnCost = ysnCost;
  }

  public String getYsnQdhl() {
    return ysnQdhl;
  }

  public void setYsnQdhl(String ysnQdhl) {
    this.ysnQdhl = ysnQdhl;
  }

  public String getYsnUnitCost() {
    return ysnUnitCost;
  }

  public void setYsnUnitCost(String ysnUnitCost) {
    this.ysnUnitCost = ysnUnitCost;
  }

  public String getSafeReform() {
    return safeReform;
  }

  public void setSafeReform(String safeReform) {
    this.safeReform = safeReform;
  }

  public String getMaterialFee2() {
    return materialFee2;
  }

  public void setMaterialFee2(String materialFee2) {
    this.materialFee2 = materialFee2;
  }

  public String getLaborFee2() {
    return laborFee2;
  }

  public void setLaborFee2(String laborFee2) {
    this.laborFee2 = laborFee2;
  }

  public String getOtherFee2() {
    return otherFee2;
  }

  public void setOtherFee2(String otherFee2) {
    this.otherFee2 = otherFee2;
  }
}
