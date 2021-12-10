package com.enjoyor.modules.monitor.service.imp;

import com.enjoyor.modules.monitor.dao.MonitorTotalDao;
import com.enjoyor.modules.monitor.dao.SewageDao;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalQueryDTO;
import com.enjoyor.modules.monitor.service.SewageService;
import com.enjoyor.modules.sys.dao.SysSiteDao;
import com.enjoyor.modules.sys.entity.SysSiteEntity;
import com.enjoyor.modules.monitor.entity.ReportWsEntity;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("sewageService")
public class SewageServiceimp implements SewageService {

	@Autowired
	private MonitorTotalDao monitorTotalDao;
	@Autowired
	private SysSiteDao sysSiteDao;
	@Autowired
	private SewageDao sewageDao;
		
	@Override
	public List<MonitorTotalEntity> findBySiteIdTime(MonitorTotalQueryDTO dto) {
		return monitorTotalDao.findBySiteIdTime(dto);
	}
	
	@Override
    public List<ReportWsEntity> select(String siteId, String beginTime, String endTime) throws Exception {
		ReportWsEntity sum = new ReportWsEntity();
        sum.setWaterIn("0");
        sum.setWaterOut("0");

        sum.setMudHandle("0");
        sum.setMudDry("0");
        sum.setMudYield("0");
        sum.setMudBfoDwater("0");
        sum.setMudCost("0");

        sum.setMedCost("0");
        sum.setMedCostPerT("0");

        sum.setClsnCost("0");
        sum.setClsnNum("0");
        sum.setClsnQdhl("0");
        sum.setClsnUnitCost("0");

        sum.setPacCost("0");
        sum.setPacNum("0");
        sum.setPacQdhl("0");
        sum.setPacUnitCost("0");

        sum.setcCost("0");
        sum.setcNum("0");
        sum.setcQdhl("0");
        sum.setcUnitCost("0");

        sum.setPamCost("0");
        sum.setPamNum("0");
        sum.setPamQdhl("0");
        sum.setPamUnitCost("0");

        sum.setLimeCost("0");
        sum.setLimeNum("0");
        sum.setLimeQdhl("0");
        sum.setLimeUnitCost("0");

        sum.setTestMedCost("0");

        sum.setEleFee("0");
        sum.setEleNum("0");
        sum.setElePrice("0");
        sum.setElePerT("0");
        sum.setEleYddh("0");

        sum.setWaterUsage("0");
        sum.setWaterCost("0");

        sum.setTechPrjctTotal("0");
        sum.setTechPrjct1("0");
        sum.setTechPrjct2("0");
        sum.setTechPrjct3("0");

        sum.setPipeRprFee("0");
        sum.setMaterialFee("0");
        sum.setLaborFee("0");
        sum.setOtherFee("0");
        sum.setScRprFee("0");
        sum.setEquipOlRprFee("0");
        sum.setDepreciationFee("0");
        sum.setScOperateFee("0");
        sum.setOprtPerT("0");
        sum.setLastyearOprtPerT("0");

        sum.setYsnCost("0");
        sum.setYsnNum("0");
        sum.setYsnQdhl("0");
        sum.setYsnUnitCost("0");

        sum.setSafeReform("0");
        sum.setMaterialFee2("0");
        sum.setLaborFee2("0");
        sum.setOtherFee2("0");

        sum.setStringTime("合计");
        sum.setSiteName("合计");

        List<SysSiteEntity> wscList = sysSiteDao.queryAllWsc();
        List<ReportWsEntity> list = sewageDao.select(siteId, beginTime, endTime);

        for (ReportWsEntity e : list) {
            e.setWaterIn(String.format("%.2f", null == e.getWaterIn() ? 0 : Double.valueOf(e.getWaterIn())));
            e.setWaterOut(String.format("%.2f", null == e.getWaterOut() ? 0 : Double.valueOf(e.getWaterOut())));

            e.setMudHandle(String.format("%.2f", null == e.getMudHandle() ? 0 : Double.valueOf(e.getMudHandle())));
            e.setMudDry(String.format("%.2f", null == e.getMudDry() ? 0 : Double.valueOf(e.getMudDry())));
            e.setMudYield(String.format("%.2f", null == e.getMudYield() ? 0 : Double.valueOf(e.getMudYield())));
            e.setMudBfoDwater(String.format("%.2f", null == e.getMudBfoDwater() ? 0 : Double.valueOf(e.getMudBfoDwater())));
            e.setMudCost(String.format("%.2f", null == e.getMudCost() ? 0 : Double.valueOf(e.getMudCost())));

            e.setMedCost(String.format("%.2f", null == e.getMedCost() ? 0 : Double.valueOf(e.getMedCost())));
            e.setMedCostPerT(String.format("%.2f", null == e.getMedCostPerT() ? 0 : Double.valueOf(e.getMedCostPerT())));

            e.setClsnCost(String.format("%.2f", null == e.getClsnCost() ? 0 : Double.valueOf(e.getClsnCost())));
            e.setClsnNum(String.format("%.2f", null == e.getClsnNum() ? 0 : Double.valueOf(e.getClsnNum())));
            e.setClsnQdhl(String.format("%.2f", null == e.getClsnQdhl() ? 0 : Double.valueOf(e.getClsnQdhl())));
            e.setClsnUnitCost(String.format("%.2f", null == e.getClsnUnitCost() ? 0 : Double.valueOf(e.getClsnUnitCost())));

            e.setPacCost(String.format("%.2f", null == e.getPacCost() ? 0 : Double.valueOf(e.getPacCost())));
            e.setPacNum(String.format("%.2f", null == e.getPacNum() ? 0 : Double.valueOf(e.getPacNum())));
            e.setPacQdhl(String.format("%.2f", null == e.getPacQdhl() ? 0 : Double.valueOf(e.getPacQdhl())));
            e.setPacUnitCost(String.format("%.2f", null == e.getPacUnitCost() ? 0 : Double.valueOf(e.getPacUnitCost())));

            e.setcCost(String.format("%.2f", null == e.getcCost() ? 0 : Double.valueOf(e.getcCost())));
            e.setcNum(String.format("%.2f", null == e.getcNum() ? 0 : Double.valueOf(e.getcNum())));
            e.setcQdhl(String.format("%.2f", null == e.getcQdhl() ? 0 : Double.valueOf(e.getcQdhl())));
            e.setcUnitCost(String.format("%.2f", null == e.getcUnitCost() ? 0 : Double.valueOf(e.getcUnitCost())));

            e.setPamCost(String.format("%.2f", null == e.getPamCost() ? 0 : Double.valueOf(e.getPamCost())));
            e.setPamNum(String.format("%.2f", null == e.getPamNum() ? 0 : Double.valueOf(e.getPamNum())));
            e.setPamQdhl(String.format("%.2f", null == e.getPamQdhl() ? 0 : Double.valueOf(e.getPamQdhl())));
            e.setPamUnitCost(String.format("%.2f", null == e.getPamUnitCost() ? 0 : Double.valueOf(e.getPamUnitCost())));

            e.setLimeCost(String.format("%.2f", null == e.getLimeCost() ? 0 : Double.valueOf(e.getLimeCost())));
            e.setLimeNum(String.format("%.2f", null == e.getLimeNum() ? 0 : Double.valueOf(e.getLimeNum())));
            e.setLimeQdhl(String.format("%.2f", null == e.getLimeQdhl() ? 0 : Double.valueOf(e.getLimeQdhl())));
            e.setLimeUnitCost(String.format("%.2f", null == e.getLimeUnitCost() ? 0 : Double.valueOf(e.getLimeUnitCost())));

            e.setTestMedCost(String.format("%.2f", null == e.getTestMedCost() ? 0 : Double.valueOf(e.getTestMedCost())));

            e.setEleFee(String.format("%.2f", null == e.getEleFee() ? 0 : Double.valueOf(e.getEleFee())));
            e.setEleNum(String.format("%.2f", null == e.getEleNum() ? 0 : Double.valueOf(e.getEleNum())));
            e.setElePrice(String.format("%.2f", null == e.getElePrice() ? 0 : Double.valueOf(e.getElePrice())));
            e.setElePerT(String.format("%.2f", null == e.getElePerT() ? 0 : Double.valueOf(e.getElePerT())));
            e.setEleYddh(String.format("%.2f", null == e.getEleYddh() ? 0 : Double.valueOf(e.getEleYddh())));

            e.setWaterUsage(String.format("%.2f", null == e.getWaterUsage() ? 0 : Double.valueOf(e.getWaterUsage())));
            e.setWaterCost(String.format("%.2f", null == e.getWaterCost() ? 0 : Double.valueOf(e.getWaterCost())));

            e.setTechPrjctTotal(String.format("%.2f", null == e.getTechPrjctTotal() ? 0 : Double.valueOf(e.getTechPrjctTotal())));
            e.setTechPrjct1(String.format("%.2f", null == e.getTechPrjct1() ? 0 : Double.valueOf(e.getTechPrjct1())));
            e.setTechPrjct2(String.format("%.2f", null == e.getTechPrjct2() ? 0 : Double.valueOf(e.getTechPrjct2())));
            e.setTechPrjct3(String.format("%.2f", null == e.getTechPrjct3() ? 0 : Double.valueOf(e.getTechPrjct3())));

            e.setPipeRprFee(String.format("%.2f", null == e.getPipeRprFee() ? 0 : Double.valueOf(e.getPipeRprFee())));
            e.setMaterialFee(String.format("%.2f", null == e.getMaterialFee() ? 0 : Double.valueOf(e.getMaterialFee())));
            e.setLaborFee(String.format("%.2f", null == e.getLaborFee() ? 0 : Double.valueOf(e.getLaborFee())));
            e.setOtherFee(String.format("%.2f", null == e.getOtherFee() ? 0 : Double.valueOf(e.getOtherFee())));
            e.setScRprFee(String.format("%.2f", null == e.getScRprFee() ? 0 : Double.valueOf(e.getScRprFee())));
            e.setEquipOlRprFee(String.format("%.2f", null == e.getEquipOlRprFee() ? 0 : Double.valueOf(e.getEquipOlRprFee())));
            e.setDepreciationFee(String.format("%.2f", null == e.getDepreciationFee() ? 0 : Double.valueOf(e.getDepreciationFee())));
            e.setScOperateFee(String.format("%.2f", null == e.getScOperateFee() ? 0 : Double.valueOf(e.getScOperateFee())));
            e.setOprtPerT(String.format("%.2f", null == e.getOprtPerT() ? 0 : Double.valueOf(e.getOprtPerT())));
            e.setLastyearOprtPerT(String.format("%.2f", null == e.getLastyearOprtPerT() ? 0 : Double.valueOf(e.getLastyearOprtPerT())));

            e.setYsnCost(String.format("%.2f", null == e.getYsnCost() ? 0 : Double.valueOf(e.getYsnCost())));
            e.setYsnNum(String.format("%.2f", null == e.getYsnNum() ? 0 : Double.valueOf(e.getYsnNum())));
            e.setYsnQdhl(String.format("%.2f", null == e.getYsnQdhl() ? 0 : Double.valueOf(e.getYsnQdhl())));
            e.setYsnUnitCost(String.format("%.2f", null == e.getYsnUnitCost() ? 0 : Double.valueOf(e.getYsnUnitCost())));

            e.setSafeReform(String.format("%.2f", null == e.getSafeReform() ? 0 : Double.valueOf(e.getSafeReform())));
            e.setMaterialFee2(String.format("%.2f", null == e.getMaterialFee2() ? 0 : Double.valueOf(e.getMaterialFee2())));
            e.setLaborFee2(String.format("%.2f", null == e.getLaborFee2() ? 0 : Double.valueOf(e.getLaborFee2())));
            e.setOtherFee2(String.format("%.2f", null == e.getOtherFee2() ? 0 : Double.valueOf(e.getOtherFee2())));

            for (SysSiteEntity site : wscList) {
                if (site.getSiteId().equals(e.getSiteId())) {
                    e.setSiteName(site.getName());
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            e.setStringTime(simpleDateFormat.format(e.getTime()));
            sum.setWaterIn(String.format("%.2f", null == e.getWaterIn() ? sum.getWaterIn() : Double.valueOf(sum.getWaterIn()) + Double.valueOf(e.getWaterIn())));
            sum.setWaterOut(String.format("%.2f", null == e.getWaterOut() ? sum.getWaterOut() : Double.valueOf(sum.getWaterOut()) + Double.valueOf(e.getWaterOut())));

            sum.setMudHandle(String.format("%.2f", null == e.getMudHandle() ? sum.getMudHandle() : Double.valueOf(sum.getMudHandle()) + Double.valueOf(e.getMudHandle())));
            sum.setMudDry(String.format("%.2f", null == e.getMudDry() ? sum.getMudDry() : Double.valueOf(sum.getMudDry()) + Double.valueOf(e.getMudDry())));
            sum.setMudYield(String.format("%.2f", null == e.getMudYield() ? sum.getMudYield() : Double.valueOf(sum.getMudYield()) + Double.valueOf(e.getMudYield())));
            sum.setMudBfoDwater(String.format("%.2f", null == e.getMudBfoDwater() ? sum.getMudBfoDwater() : Double.valueOf(sum.getMudBfoDwater()) + Double.valueOf(e.getMudBfoDwater())));
            sum.setMudCost(String.format("%.2f", null == e.getMudCost() ? sum.getMudCost() : Double.valueOf(sum.getMudCost()) + Double.valueOf(e.getMudCost())));

            sum.setMedCost(String.format("%.2f", null == e.getMedCost() ? sum.getMedCost() : Double.valueOf(sum.getMedCost()) + Double.valueOf(e.getMedCost())));
            sum.setMedCostPerT(String.format("%.2f", null == e.getMedCostPerT() ? sum.getMedCostPerT() : Double.valueOf(sum.getMedCostPerT()) + Double.valueOf(e.getMedCostPerT())));

            sum.setClsnCost(String.format("%.2f", null == e.getClsnCost() ? sum.getClsnCost() : Double.valueOf(sum.getClsnCost()) + Double.valueOf(e.getClsnCost())));
            sum.setClsnNum(String.format("%.2f", null == e.getClsnNum() ? sum.getClsnNum() : Double.valueOf(sum.getClsnNum()) + Double.valueOf(e.getClsnNum())));
            sum.setClsnQdhl(String.format("%.2f", null == e.getClsnQdhl() ? sum.getClsnQdhl() : Double.valueOf(sum.getClsnQdhl()) + Double.valueOf(e.getClsnQdhl())));
            sum.setClsnUnitCost(String.format("%.2f", null == e.getClsnUnitCost() ? sum.getClsnUnitCost() : Double.valueOf(sum.getClsnUnitCost()) + Double.valueOf(e.getClsnUnitCost())));

            sum.setPacCost(String.format("%.2f", null == e.getPacCost() ? sum.getPacCost() : Double.valueOf(sum.getPacCost()) + Double.valueOf(e.getPacCost())));
            sum.setPacNum(String.format("%.2f", null == e.getPacNum() ? sum.getPacNum() : Double.valueOf(sum.getPacNum()) + Double.valueOf(e.getPacNum())));
            sum.setPacQdhl(String.format("%.2f", null == e.getPacQdhl() ? sum.getPacQdhl() : Double.valueOf(sum.getPacQdhl()) + Double.valueOf(e.getPacQdhl())));
            sum.setPacUnitCost(String.format("%.2f", null == e.getPacUnitCost() ? sum.getPacUnitCost() : Double.valueOf(sum.getPacUnitCost()) + Double.valueOf(e.getPacUnitCost())));

            sum.setcCost(String.format("%.2f", null == e.getcCost() ? sum.getcCost() : Double.valueOf(sum.getcCost()) + Double.valueOf(e.getcCost())));
            sum.setcNum(String.format("%.2f", null == e.getcNum() ? sum.getcNum() : Double.valueOf(sum.getcNum()) + Double.valueOf(e.getcNum())));
            sum.setcQdhl(String.format("%.2f", null == e.getcQdhl() ? sum.getcQdhl() : Double.valueOf(sum.getcQdhl()) + Double.valueOf(e.getcQdhl())));
            sum.setcUnitCost(String.format("%.2f", null == e.getcUnitCost() ? sum.getcUnitCost() : Double.valueOf(sum.getcUnitCost()) + Double.valueOf(e.getcUnitCost())));

            sum.setPamCost(String.format("%.2f", null == e.getPamCost() ? sum.getPamCost() : Double.valueOf(sum.getPamCost()) + Double.valueOf(e.getPamCost())));
            sum.setPamNum(String.format("%.2f", null == e.getPamNum() ? sum.getPamNum() : Double.valueOf(sum.getPamNum()) + Double.valueOf(e.getPamNum())));
            sum.setPamQdhl(String.format("%.2f", null == e.getPamQdhl() ? sum.getPamQdhl() : Double.valueOf(sum.getPamQdhl()) + Double.valueOf(e.getPamQdhl())));
            sum.setPamUnitCost(String.format("%.2f", null == e.getPamUnitCost() ? sum.getPamUnitCost() : Double.valueOf(sum.getPamUnitCost()) + Double.valueOf(e.getPamUnitCost())));

            sum.setLimeCost(String.format("%.2f", null == e.getLimeCost() ? sum.getLimeCost() : Double.valueOf(sum.getLimeCost()) + Double.valueOf(e.getLimeCost())));
            sum.setLimeNum(String.format("%.2f", null == e.getLimeNum() ? sum.getLimeNum() : Double.valueOf(sum.getLimeNum()) + Double.valueOf(e.getLimeNum())));
            sum.setLimeQdhl(String.format("%.2f", null == e.getLimeQdhl() ? sum.getLimeQdhl() : Double.valueOf(sum.getLimeQdhl()) + Double.valueOf(e.getLimeQdhl())));
            sum.setLimeUnitCost(String.format("%.2f", null == e.getLimeUnitCost() ? sum.getLimeUnitCost() : Double.valueOf(sum.getLimeUnitCost()) + Double.valueOf(e.getLimeUnitCost())));

            sum.setTestMedCost(String.format("%.2f", null == e.getTestMedCost() ? sum.getTestMedCost() : Double.valueOf(sum.getTestMedCost()) + Double.valueOf(e.getTestMedCost())));

            sum.setEleFee(String.format("%.2f", null == e.getEleFee() ? sum.getEleFee() : Double.valueOf(sum.getEleFee()) + Double.valueOf(e.getEleFee())));
            sum.setEleNum(String.format("%.2f", null == e.getEleNum() ? sum.getEleNum() : Double.valueOf(sum.getEleNum()) + Double.valueOf(e.getEleNum())));
            sum.setElePrice(String.format("%.2f", null == e.getElePrice() ? sum.getElePrice() : Double.valueOf(sum.getElePrice()) + Double.valueOf(e.getElePrice())));
            sum.setElePerT(String.format("%.2f", null == e.getElePerT() ? sum.getElePerT() : Double.valueOf(sum.getElePerT()) + Double.valueOf(e.getElePerT())));
            sum.setEleYddh(String.format("%.2f", null == e.getEleYddh() ? sum.getEleYddh() : Double.valueOf(sum.getEleYddh()) + Double.valueOf(e.getEleYddh())));

            sum.setWaterUsage(String.format("%.2f", null == e.getWaterUsage() ? sum.getWaterUsage() : Double.valueOf(sum.getWaterUsage()) + Double.valueOf(e.getWaterUsage())));
            sum.setWaterCost(String.format("%.2f", null == e.getWaterCost() ? sum.getWaterCost() : Double.valueOf(sum.getWaterCost()) + Double.valueOf(e.getWaterCost())));

            sum.setTechPrjctTotal(String.format("%.2f", null == e.getTechPrjctTotal() ? sum.getTechPrjctTotal() : Double.valueOf(sum.getTechPrjctTotal()) + Double.valueOf(e.getTechPrjctTotal())));
            sum.setTechPrjct1(String.format("%.2f", null == e.getTechPrjct1() ? sum.getTechPrjct1() : Double.valueOf(sum.getTechPrjct1()) + Double.valueOf(e.getTechPrjct1())));
            sum.setTechPrjct2(String.format("%.2f", null == e.getTechPrjct2() ? sum.getTechPrjct2() : Double.valueOf(sum.getTechPrjct2()) + Double.valueOf(e.getTechPrjct2())));
            sum.setTechPrjct3(String.format("%.2f", null == e.getTechPrjct3() ? sum.getTechPrjct3() : Double.valueOf(sum.getTechPrjct3()) + Double.valueOf(e.getTechPrjct3())));

            sum.setPipeRprFee(String.format("%.2f", null == e.getPipeRprFee() ? sum.getPipeRprFee() : Double.valueOf(sum.getPipeRprFee()) + Double.valueOf(e.getPipeRprFee())));
            sum.setMaterialFee(String.format("%.2f", null == e.getMaterialFee() ? sum.getMaterialFee() : Double.valueOf(sum.getMaterialFee()) + Double.valueOf(e.getMaterialFee())));
            sum.setLaborFee(String.format("%.2f", null == e.getLaborFee() ? sum.getLaborFee() : Double.valueOf(sum.getLaborFee()) + Double.valueOf(e.getLaborFee())));
            sum.setOtherFee(String.format("%.2f", null == e.getOtherFee() ? sum.getOtherFee() : Double.valueOf(sum.getOtherFee()) + Double.valueOf(e.getOtherFee())));
            sum.setScRprFee(String.format("%.2f", null == e.getScRprFee() ? sum.getScRprFee() : Double.valueOf(sum.getScRprFee()) + Double.valueOf(e.getScRprFee())));
            sum.setEquipOlRprFee(String.format("%.2f", null == e.getEquipOlRprFee() ? sum.getEquipOlRprFee() : Double.valueOf(sum.getEquipOlRprFee()) + Double.valueOf(e.getEquipOlRprFee())));
            sum.setDepreciationFee(String.format("%.2f", null == e.getDepreciationFee() ? sum.getDepreciationFee() : Double.valueOf(sum.getDepreciationFee()) + Double.valueOf(e.getDepreciationFee())));
            sum.setScOperateFee(String.format("%.2f", null == e.getScOperateFee() ? sum.getScOperateFee() : Double.valueOf(sum.getScOperateFee()) + Double.valueOf(e.getScOperateFee())));
            sum.setOprtPerT(String.format("%.2f", null == e.getOprtPerT() ? sum.getOprtPerT() : Double.valueOf(sum.getOprtPerT()) + Double.valueOf(e.getOprtPerT())));
            sum.setLastyearOprtPerT(String.format("%.2f", null == e.getLastyearOprtPerT() ? sum.getLastyearOprtPerT() : Double.valueOf(sum.getLastyearOprtPerT()) + Double.valueOf(e.getLastyearOprtPerT())));

            sum.setYsnCost(String.format("%.2f", null == e.getYsnCost() ? sum.getYsnCost() : Double.valueOf(sum.getYsnCost()) + Double.valueOf(e.getYsnCost())));
            sum.setYsnNum(String.format("%.2f", null == e.getYsnNum() ? sum.getYsnNum() : Double.valueOf(sum.getYsnNum()) + Double.valueOf(e.getYsnNum())));
            sum.setYsnQdhl(String.format("%.2f", null == e.getYsnQdhl() ? sum.getYsnQdhl() : Double.valueOf(sum.getYsnQdhl()) + Double.valueOf(e.getYsnQdhl())));
            sum.setYsnUnitCost(String.format("%.2f", null == e.getYsnUnitCost() ? sum.getYsnUnitCost() : Double.valueOf(sum.getYsnUnitCost()) + Double.valueOf(e.getYsnUnitCost())));

            sum.setSafeReform(String.format("%.2f", null == e.getSafeReform() ? sum.getSafeReform() : Double.valueOf(sum.getSafeReform()) + Double.valueOf(e.getSafeReform())));
            sum.setMaterialFee2(String.format("%.2f", null == e.getMaterialFee2() ? sum.getMaterialFee2() : Double.valueOf(sum.getMaterialFee2()) + Double.valueOf(e.getMaterialFee2())));
            sum.setLaborFee2(String.format("%.2f", null == e.getLaborFee2() ? sum.getLaborFee2() : Double.valueOf(sum.getLaborFee2()) + Double.valueOf(e.getLaborFee2())));
            sum.setOtherFee2(String.format("%.2f", null == e.getOtherFee2() ? sum.getOtherFee2() : Double.valueOf(sum.getOtherFee2()) + Double.valueOf(e.getOtherFee2())));
        }
        list.add(sum);
        return list;
    }
}
