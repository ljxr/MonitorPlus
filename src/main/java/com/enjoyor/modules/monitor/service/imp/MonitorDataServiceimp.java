package com.enjoyor.modules.monitor.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.enjoyor.common.utils.RedisUtils;
import com.enjoyor.common.utils.WebSocket;
import com.enjoyor.modules.monitor.dao.MonitorDao;
import com.enjoyor.modules.monitor.dao.MonitorDataDao;
import com.enjoyor.modules.monitor.entity.AnalysisEntity;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.PipeDataEntity;
import com.enjoyor.modules.monitor.entity.SeatEntity;
import com.enjoyor.modules.monitor.service.MonitorDataService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("monitorDataService")
public class MonitorDataServiceimp implements MonitorDataService {

	@Autowired
    private RedisUtils redisUtils;
	@Autowired
	private WebSocket wst;
	@Autowired
	MonitorDataDao monitorDataDao;
	@Autowired
	MonitorDao monitorDao;
	
/*	@Override
	public boolean sendMessage(String siteId,HttpSession session) {
		List<PipeDataEntity> result=new ArrayList<PipeDataEntity>();
		
		String[] siteId2=siteId.split(";");
		for (int i=0;i<siteId2.length;i++) {
			String siteId3=siteId2[i];
				
        Set<String> keys=redisUtils.set2(siteId3);				
		for (String string : keys) {			
			String re=redisUtils.get(string);
			PipeDataEntity infoDo = JSON.parseObject(re, PipeDataEntity.class);
			result.add(infoDo);
		}
		
		}
        //WebSocket wst=new WebSocket();
        //Session session2=wst.getSession();        
		//JSONArray str= JSONArray.parseArray(JSON.toJSONString(result));				
		//System.err.println("seession+"+(boolean)session.getAttribute("result"));
		if(null==session||null==session.getAttribute("result")){
			return false;
		}
		wst.onMessage(JSON.toJSONString(result),session);
		result=null;
		return true; 
	}*/

	@Override
	public boolean sendMessage(String siteId, HttpSession session) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		//System.err.println("22222222222"+siteId);
		String[] siteId2 = {};
		String[] bigdatasiteId = {};
		if(null != siteId){
		    siteId2 = siteId.split(";");
		
			bigdatasiteId = siteId.split("bigdata;");
		}
		while (null != session && (boolean) session.getAttribute("websocket")) {
			List<Object> result = new ArrayList<Object>();

			if(1 < bigdatasiteId.length){
				for(int n = 0;n<bigdatasiteId.length;n++){
					if(0 == n){
						String[] bs = bigdatasiteId[0].split(";");
						double totalflow = 0;
						double wtotalflow = 0;
						for (int i = 0; i < bs.length; i++) {
							String siteId3 = bs[i];
							String siteId4 = siteId3.split("=")[0];	
							if ("WC".equals(siteId4.split("-")[1])) {
								if("2-WC-TZH".equals(siteId4)){
									Set<String> keys = redisUtils.set2(siteId4+"CK,FLC");
									for (String string : keys) {
										String re = redisUtils.get(string);
										PipeDataEntity infoDo = JSON.parseObject(re, PipeDataEntity.class);
										double flow = infoDo.getJlj();
										wtotalflow = wtotalflow + flow;
									}
								}else{
									Set<String> keys = redisUtils.set2(siteId4+"CK,FLC");
									for (String string : keys) {
										String re = redisUtils.get(string);
										PipeDataEntity infoDo = JSON.parseObject(re, PipeDataEntity.class);
										double flow = infoDo.getJlj() - Double.valueOf(siteId3.split("=")[1]);
										wtotalflow = wtotalflow + flow;
									}	
								}							
							} else if ("SC".equals(siteId4.split("-")[2])) {
								Set<String> keys = redisUtils.set2(siteId4+",FLC");

								for (String string : keys) {
									String re = redisUtils.get(string);
									PipeDataEntity infoDo = JSON.parseObject(re, PipeDataEntity.class);
									//System.out.println("infoDo:"+infoDo.getJlj());
									double flow = infoDo.getJlj() - Double.valueOf(siteId3.split("=")[1]);
									totalflow = totalflow + flow;
								}
							}
						}
						Map<String,Object> map = new HashedMap();
						map.put("totalflow", totalflow);
						map.put("wtotalflow", wtotalflow);
						result.add(map);
					}else{
						String[] os = bigdatasiteId[n].split(";");
						for (int i = 0; i < os.length; i++) {
							String siteId3 = os[i];
							Set<String> keys = redisUtils.set2(siteId3);
							for (String string : keys) {
								String re = redisUtils.get(string);
								PipeDataEntity infoDo = JSON.parseObject(re, PipeDataEntity.class);
								result.add(infoDo);
							}				
						}
					}
				}
			}else{

				for (int i = 0; i < siteId2.length; i++) {
					
					String siteId3 = siteId2[i];
					//System.out.println("*"+siteId3);
					
					Set<String> keys = redisUtils.set2(siteId3);
					for (String string : keys) {
						String re = redisUtils.get(string);
						PipeDataEntity infoDo = JSON.parseObject(re, PipeDataEntity.class);
						result.add(infoDo);
					}				
				}
			}
			
//			if ("bigdata".equals(siteId2[siteId2.length - 1])) {
//				
//			} else {
//				
//			}
			//System.out.println(JSON.toJSONString(result));
			wst.onMessage(JSON.toJSONString(result) + "#@" + session.getId(), session);

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	@Override
	public void saveGytbgn(GytEntity e) {
		// TODO Auto-generated method stub
		monitorDataDao.saveGytbgn(e);
	}

	@Override
	public void saveInformation(GytEntity e) {
		// TODO Auto-generated method stub
		monitorDataDao.saveInformation(e);
	}

	@Override
	public void saveTitleInfo(GytEntity e) {
		// TODO Auto-generated method stub
		monitorDataDao.saveTitleInfo(e);
	}

	@Override
	public void savePointInfo(GytEntity e) {
		// TODO Auto-generated method stub
		monitorDataDao.savePointInfo(e);
	}

	@Override
	public void updateGytbgn(GytEntity e) {
		// TODO Auto-generated method stub
		monitorDataDao.updateGytbgn(e);
	}

	@Override
	public void updateInformation(GytEntity e) {
		// TODO Auto-generated method stub
		monitorDataDao.updateInformation(e);
	}

	@Override
	public void updateTitleInfo(GytEntity e) {
		// TODO Auto-generated method stub
		monitorDataDao.updateTitleInfo(e);
	}

	@Override
	public void updatePointInfo(GytEntity e) {
		// TODO Auto-generated method stub
		monitorDataDao.updatePointInfo(e);
	}

	@Override
	public void deleteGytbgn(String siteId) {
		// TODO Auto-generated method stub
		monitorDataDao.deleteGytbgn(siteId);
	}

	@Override
	public void deleteInformation(String siteId,String divId) {
		// TODO Auto-generated method stub
		monitorDataDao.deleteInformation(siteId,divId);
	}

	@Override
	public void deleteTitleInfo(String siteId,String divId) {
		monitorDataDao.deleteTitleInfo(siteId,divId);
	}

	@Override
	public void deletePointInfo(String siteId,String divId) {
		// TODO Auto-generated method stub
		monitorDataDao.deletePointInfo(siteId,divId);
	}

	@Override
	public List<GytEntity> queryGytAll() {
		// TODO Auto-generated method stub
		return monitorDataDao.queryGytAll();
	}

	@Override
	public List<GytEntity> queryGyt(String siteId) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryGyt(siteId);
	}

	@Override
	public List<GytEntity> queryTitle(String siteId) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryTitle(siteId);
	}

	@Override
	public List<GytEntity> queryInformation(String siteId) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryInformation(siteId);
	}

	@Override
	public List<GytEntity> queryPoint(String siteId) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryPoint(siteId);
	}

	@Override
	public List<GytBaseInfoEntity> queryGytbase(GytBaseInfoEntity gb) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryGytbase(gb);
	}

	@Override
	public void saveGytbase(GytBaseInfoEntity gb) {
		// TODO Auto-generated method stub
		monitorDataDao.saveGytbase(gb);
	}

	@Override
	public void updateGytbase(GytBaseInfoEntity gb) {
		// TODO Auto-generated method stub
		monitorDataDao.updateGytbase(gb);
	}

	@Override
	public void deleteGytbase(int id) {
		// TODO Auto-generated method stub
		monitorDataDao.deleteGytbase(id);
	}

	@Override
	public GytBaseInfoEntity queryGytbaseId(int id) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryGytbaseId(id);
	}

	@Override
	public int queryGytbaseCount(GytBaseInfoEntity gb) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryGytbaseCount(gb);
	}

	@Override
	public List<GytEntity> queryTitle2(String siteId, String divId) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryTitle2(siteId,divId);
	}

	@Override
	public List<GytEntity> queryInformation2(String gytId, String divId) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryInformation2(gytId, divId);
	}

	@Override
	public List<GytEntity> queryPoint2(String siteId, String divId) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryPoint2(siteId, divId);
	}

	@Override
	public void saveSeat(SeatEntity gb) {
		// TODO Auto-generated method stub
		monitorDataDao.saveSeat(gb);
	}

	@Override
	public void updateSeat(SeatEntity gb) {
		// TODO Auto-generated method stub
		monitorDataDao.updateSeat(gb);
	}

	@Override
	public void deleteSeat(String linkParentId, String linkSiteId,String siteId) {
		// TODO Auto-generated method stub
		monitorDataDao.deleteSeat(linkParentId, linkSiteId,siteId);
	}

	@Override
	public List<SeatEntity> querySeat(String linkParentId, String linkSiteId, String siteId) {
		// TODO Auto-generated method stub
		return monitorDataDao.querySeat(linkParentId, linkSiteId, siteId);
	}

	@Override
	public List<SeatEntity> querySeat2(String siteId, String divId) {
		// TODO Auto-generated method stub
		return monitorDataDao.querySeat2(siteId, divId);
	}

	@Override
	public List<GytBaseInfoEntity> queryGytgl(String siteParentId) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryGytgl(siteParentId);
	}

	@Override
	public List<AnalysisEntity> queryAnalysisHourFlow(String siteId, String stime, String etime) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryAnalysisHourFlow(siteId, stime, etime);
	}

	@Override
	public List<AnalysisEntity> queryAnalysisDayFlow(String siteId, String stime, String etime) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryAnalysisDayFlow(siteId, stime, etime);
	}

	@Override
	public List<AnalysisEntity> queryAnalysisMonthFlow(String siteId, String stime, String etime) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryAnalysisMonthFlow(siteId, stime, etime);
	}

	@Override
	public List<AnalysisEntity> queryAnalysisYearFlow(String siteId, String stime, String etime) {
		// TODO Auto-generated method stub
		return monitorDataDao.queryAnalysisYearFlow(siteId, stime, etime);
	}
	
}
