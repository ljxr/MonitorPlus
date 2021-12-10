package com.enjoyor.modules.dma.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyor.modules.dma.dao.DmaAreaDao;
import com.enjoyor.modules.dma.dao.DmaSiteDao;
import com.enjoyor.modules.dma.entity.DmaAreaDTO;
import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaAreaTreeDTO;
import com.enjoyor.modules.dma.entity.DmaFlowNightWarnEntity;
import com.enjoyor.modules.dma.entity.DmaSiteEntity;
import com.enjoyor.modules.dma.service.DmaAreaService;

@Service("dmaAreaService")
public class DmaAreaServiceimp implements DmaAreaService {

	@Autowired
	private DmaAreaDao dmaAreaDao;
	@Autowired
	private DmaSiteDao dmaSiteDao;

	@Override
	public int save(DmaAreaEntity de) {
		return dmaAreaDao.save(de);
	}


	@Override
	public int update(DmaAreaEntity de) {
		// TODO Auto-generated method stub
		return dmaAreaDao.update(de);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return dmaAreaDao.delete(id);
	}

	@Override
	public List<DmaAreaEntity> queryAreaList(DmaAreaEntity de) {
		List<DmaAreaEntity> list = dmaAreaDao.queryAreaList(de);
		List<DmaAreaEntity> list2 = dmaAreaDao.queryAll();
		for(DmaAreaEntity entity : list){
			String dareaParentName = "";
			for(DmaAreaEntity entity2 : list2){
				if(null != entity.getDareaParentId() && entity.getDareaParentId().equals(entity2.getDareaId())){
					dareaParentName = entity2.getDareaName();
				}
			}
			entity.setDareaParentName(dareaParentName);
		}
		return list;
	}

	@Override
	public int queryAreaCount(DmaAreaEntity de) {
		// TODO Auto-generated method stub
		return dmaAreaDao.queryAreaCount(de);
	}

	@Override
	public List<DmaAreaEntity> queryAreaParent() {
		// TODO Auto-generated method stub
		return dmaAreaDao.queryAreaParent();
	}

	@Override
	public List<DmaAreaEntity> queryAreaChild(String dareaParentId) {
		// TODO Auto-generated method stub
		return dmaAreaDao.queryAreaChild(dareaParentId);
	}

	@Override
	public List<DmaAreaEntity> queryAllWaterArea() {
		List<DmaAreaEntity> dataList = dmaAreaDao.queryAll();
		return dataList;
	}

	@Override
	public int updateByDareaId(DmaAreaEntity de) {
		// TODO Auto-generated method stub
		return dmaAreaDao.updateByDareaId(de);
	}

	@Override
	public int queryCountAreaId(String dareaId) {
		// TODO Auto-generated method stub
		return dmaAreaDao.queryCountAreaId(dareaId);
	}

	@Override
	public List<DmaAreaEntity> queryAreaTree() {
		List<DmaAreaEntity> allList = dmaAreaDao.queryAll();
		Map<Integer, DmaAreaEntity> areaMap = new HashMap<Integer, DmaAreaEntity>();
		for (DmaAreaEntity area : allList) {
			int id = area.getId();
			areaMap.put(id, area);
		}
		// 所有有父节点的，添加到父节点的childList中
		for (DmaAreaEntity area : allList) {
			String parentId = area.getDareaParentId();
			if (null != parentId && !"".equals(parentId)) {
				DmaAreaEntity tempArea = areaMap.get(parentId);
				List<DmaAreaEntity> tempListArea = tempArea.getChildren();
				if (tempListArea == null) {
					tempListArea = new ArrayList<DmaAreaEntity>();
				}
				tempListArea.add(area);
				tempArea.setChildren(tempListArea);
			}
		}
		List<Integer> list = new ArrayList<Integer>();
		for (Integer k : areaMap.keySet()) {
			DmaAreaEntity tempArea = areaMap.get(k);
			if (null != tempArea.getDareaParentId() && !"".equals(tempArea.getDareaParentId())) {
				list.add(k);
			}
		}
		for (int i : list) {
			areaMap.remove(i);
		}
		List<DmaAreaEntity> resultList = new ArrayList<DmaAreaEntity>(areaMap.values());
		return resultList;
	}

	@Override
	public List<DmaAreaDTO> querySonList(String dareaParentId) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, -24);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(calendar.getTime());
		List<DmaAreaDTO> list = dmaAreaDao.querySonList(dareaParentId, time);
		
		for (DmaAreaDTO dto : list) {
			if (null != dto.getDareaFlowIn() && null != dto.getDareaFlowOut()) {
				dto.setLsl(dto.getDareaFlowIn() - dto.getDareaFlowOut());
			}
		}
		return list;
	}

	@Override
	public List<DmaFlowNightWarnEntity> queryDmaFlowNightWarnList(String dareaId) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, -24);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String time = sdf.format(calendar.getTime());
		List<DmaFlowNightWarnEntity> list = dmaAreaDao.queryDmaFlowNightWarnList(dareaId, time);
		List<DmaFlowNightWarnEntity> resultList = new ArrayList<>();
		for(DmaFlowNightWarnEntity entity : list){
//			if (0 != entity.getAvgDay() && 0!= entity.getDaysavg()) {
//				double rbl = entity.getDaysavg() / entity.getAvgDay() - 1;
//				entity.setRbl(rbl);
//				entity.setRybl(entity.getRybl() + rbl);
//				if(rbl < -0.2 || 0.2 < rbl) {
//					resultList.add(entity);
//					continue;
//				}
//			}
//			if (0 != entity.getAvgNig() && 0!= entity.getNightsavg()) {
//				double ybl = entity.getNightsavg() / entity.getAvgNig() - 1;
//				entity.setYbl(ybl);
//				entity.setRybl(entity.getRybl() + ybl);
//				if(ybl < -0.2 || 0.2 < ybl) {
//					resultList.add(entity);
//					continue;
//				}
//			}
			if (0 != entity.getAvgDay() && 0!= entity.getDaysavg()) {
				double rbl = entity.getAvgDay() / entity.getDaysavg();
				entity.setRbl(rbl);
				entity.setRybl(entity.getRybl() + rbl);				
			}
			if (0 != entity.getAvgNig() && 0!= entity.getNightsavg()) {
				double ybl =  entity.getAvgNig() / entity.getNightsavg();
				entity.setYbl(ybl);
				entity.setRybl(entity.getRybl() + ybl);
			}
			resultList.add(entity);			
		}
		resultList.sort(new Comparator<DmaFlowNightWarnEntity>() {
		    @Override
		    public int compare(DmaFlowNightWarnEntity o1, DmaFlowNightWarnEntity o2) {
		    	if(o1.getRybl() > o2.getRybl()){
		    		return -1;
		    	} else if(o1.getRybl() < o2.getRybl()){
		    		return 1;
		    	} else{
		    		return 0;
		    	}
		    }
		});

		return resultList;
	}
	
	@Override
	public List<DmaAreaDTO> queryDmaWaterHis(String dareaParentId, String beginTime, String endTime) {
		beginTime += " 00:00:00";
		endTime += " 00:00:00";
		List<DmaAreaDTO> list = dmaAreaDao.queryDmaWaterHis(dareaParentId, beginTime, endTime);
		for (DmaAreaDTO dto : list) {
			if (null != dto.getDareaFlowIn() && null != dto.getDareaFlowOut()) {
				dto.setLsl(dto.getDareaFlowIn() - dto.getDareaFlowOut());
			}
		}
		return list;
	}

	@Override	
	public String dmalslCalcMethod(String dareaId){
		DmaSiteEntity queryExample = new DmaSiteEntity();
		queryExample.setDareaId(dareaId);
		List<DmaSiteEntity> list = dmaSiteDao.querySiteAll(queryExample);
		if(list.size() == 0){
			return "";
		}
		StringBuffer calcMethod = new StringBuffer("计算方式  =");
		List<DmaSiteEntity> inSite = new ArrayList<DmaSiteEntity>();
		List<DmaSiteEntity> outSite = new ArrayList<DmaSiteEntity>();
		for(DmaSiteEntity site : list){
			if("进".equals(site.getDsiteJc())){
				inSite.add(site);
			}
			if("出".equals(site.getDsiteJc())){
				outSite.add(site);
			}
		}
		for(DmaSiteEntity site : inSite){
			calcMethod.append(" ");
			calcMethod.append(site.getDsiteName());
			calcMethod.append(" +");
		}
		if(inSite.size() > 0){
			calcMethod.deleteCharAt(calcMethod.length() - 1);
		}
		for(DmaSiteEntity site : outSite){
			calcMethod.append(" - ");
			calcMethod.append(site.getDsiteName());
		}
		return calcMethod.toString();
	}
	
	@Override
	public List<DmaAreaTreeDTO> queryDmaTree() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, -24);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(calendar.getTime());
		List<DmaAreaTreeDTO> allList = dmaAreaDao.queryAllAreaYesterdayWater(time);
		
		// 一级片区
		List<DmaAreaTreeDTO> list = new ArrayList<DmaAreaTreeDTO>();
		for(DmaAreaTreeDTO item : allList){
			if(null == item.getDareaParentId()){
				list.add(item);
			}
		}
		for(DmaAreaTreeDTO class1 : list){
			List<DmaAreaTreeDTO> list2 = createSonDataList(allList, class1.getDareaId());
			class1.setSonList(list2);
			for(DmaAreaTreeDTO class2 : list2){
				List<DmaAreaTreeDTO> list3 = createSonDataList(allList, class2.getDareaId());
				class2.setSonList(list3);
				for(DmaAreaTreeDTO class3 : list3){
					List<DmaAreaTreeDTO> list4 = createSonDataList(allList, class3.getDareaId());
					class3.setSonList(list4);
					for(DmaAreaTreeDTO class4 : list4){
						List<DmaAreaTreeDTO> list5 = createSonDataList(allList, class4.getDareaId());
						class4.setSonList(list5);
						for(DmaAreaTreeDTO class5 : list5){
							List<DmaAreaTreeDTO> list6 = createSonDataList(allList, class5.getDareaId());
							class5.setSonList(list6);
							for(DmaAreaTreeDTO class6 : list6){
								List<DmaAreaTreeDTO> list7 = createSonDataList(allList, class6.getDareaId());
								class6.setSonList(list7);
								for(DmaAreaTreeDTO class7 : list7){
									List<DmaAreaTreeDTO> list8 = createSonDataList(allList, class7.getDareaId());
									class7.setSonList(list8);
									for(DmaAreaTreeDTO class8 : list8){
										List<DmaAreaTreeDTO> list9 = createSonDataList(allList, class8.getDareaId());
										class8.setSonList(list9);
										
									}
								}
							}
						}
					}
				}
			}
		}
		

		return list;
	}
	
	private List<DmaAreaTreeDTO> createSonDataList(List<DmaAreaTreeDTO> allList, String dareaId){
		List<DmaAreaTreeDTO> list = new ArrayList<DmaAreaTreeDTO>();
		for(DmaAreaTreeDTO item : allList){
			if(null != item.getDareaParentId() && item.getDareaParentId().contains(dareaId)){
				if (null != item.getDareaFlowIn() && null != item.getDareaFlowOut()) {
					item.setLsl(item.getDareaFlowIn() - item.getDareaFlowOut());
				}
				list.add(item);
			}
		}
		return list;
	}
}
