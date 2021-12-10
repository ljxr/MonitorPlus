package com.enjoyor.modules.dma.service.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyor.modules.dma.dao.DmaAreaDao;
import com.enjoyor.modules.dma.dao.DmaSiteDao;
import com.enjoyor.modules.dma.dao.DmaUserDao;
import com.enjoyor.modules.dma.entity.DmaAreaEntity;
import com.enjoyor.modules.dma.entity.DmaSiteData;
import com.enjoyor.modules.dma.entity.DmaSiteEntity;
import com.enjoyor.modules.dma.entity.DmaUserEntity;
import com.enjoyor.modules.dma.service.DmaAreaService;
import com.enjoyor.modules.dma.service.DmaSiteService;
import com.enjoyor.modules.dma.service.DmaUserService;

@Service("dmaSiteService")
public class DmaSiteServiceimp implements DmaSiteService{

	@Autowired
	private DmaSiteDao dmaSiteDao;

	@Override
	public int save(DmaSiteEntity de) {
		// TODO Auto-generated method stub
		return dmaSiteDao.save(de);
	}

	@Override
	public int update(DmaSiteEntity de) {
		// TODO Auto-generated method stub
		return dmaSiteDao.update(de);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return dmaSiteDao.delete(id);
	}

	@Override
	public List<DmaSiteEntity> querySiteList(DmaSiteEntity de) {
		// TODO Auto-generated method stub
		return dmaSiteDao.querySiteList(de);
	}

	@Override
	public List<DmaSiteEntity> querySiteAll(DmaSiteEntity de) {
		// TODO Auto-generated method stub
		return dmaSiteDao.querySiteAll(de);
	}

	@Override
	public int querySiteCount(DmaSiteEntity de) {
		// TODO Auto-generated method stub
		return dmaSiteDao.querySiteCount(de);
	}

	@Override
	public int deleteByAreaId(String dareaId) {
		// TODO Auto-generated method stub
		return dmaSiteDao.deleteByAreaId(dareaId);
	}
	
	@Override
	public Map<String, Object> querySiteDayFlow(String dareaId, String beginTime, String endTime) {
		beginTime = beginTime + " 00:00:00";
		endTime = endTime + " 00:00:00";
		Map<String,Object> map = new HashMap<String, Object>();	
		DmaSiteEntity queryExample = new DmaSiteEntity();
		queryExample.setDareaId(dareaId);
		List<DmaSiteEntity> siteList = dmaSiteDao.querySiteAll(queryExample);
		if(0 == siteList.size()){
			return map;
		}
		// 获取时间集合
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dStart = null;
        Date dEnd = null;
        try {
            dStart = sdf.parse(beginTime);
            dEnd = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> dateList = findDates(dStart, dEnd);
        // resultList
        List<DmaSiteData> resultList = new ArrayList();
        // 循环时间
        for(String time : dateList){
        	DmaSiteData result = new DmaSiteData();
        	result.setTime(time);        	
        	String siteIdCollection = "";
        	String siteNameCollection = "";
        	for(DmaSiteEntity site : siteList){
        		siteIdCollection = siteIdCollection + "," + site.getDsiteId();
        		siteNameCollection = siteNameCollection + "," + site.getDsiteName();
    		}
        	siteIdCollection = siteIdCollection.substring(1);  	
        	siteNameCollection = siteNameCollection.substring(1);  		
        	result.setSiteId(siteIdCollection);
        	result.setSiteName(siteNameCollection);
        	resultList.add(result);
        }
        // 循环数据
    	List<DmaSiteData> allDataList = new ArrayList();
        for(DmaSiteEntity site : siteList){
        	List<DmaSiteData> dataList = dmaSiteDao.querySiteDayFlow(site.getDsiteId(), beginTime, endTime);
        	allDataList.addAll(dataList);
        }
        for(DmaSiteData result : resultList){
        	String dayFlowCollection = "";
        	for(DmaSiteEntity site : siteList){
        		dayFlowCollection =  dayFlowCollection + ",";
        		for(DmaSiteData data : allDataList){
        			if(result.getTime().equals(data.getTime().substring(0, 10)) && site.getDsiteId().equals(data.getSiteId())){
        				dayFlowCollection =  dayFlowCollection + data.getDayFlow();
        			}
        		}
        	}
        	if(dayFlowCollection.length() > 0){
            	result.setDayFlow(dayFlowCollection.substring(1));
        	}
        }
        map.put("siteDataList", resultList);
	    return map;	
	}
	
	private static List<String> findDates(Date dStart, Date dEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);
        List<String> dateList = new ArrayList();
        dateList.add(sdf.format(dStart));
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
        // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(sdf.format(cStart.getTime()));
        }
    return dateList;
    }
}
