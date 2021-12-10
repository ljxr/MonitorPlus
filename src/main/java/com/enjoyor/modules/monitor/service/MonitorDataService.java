package com.enjoyor.modules.monitor.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.AnalysisEntity;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.SeatEntity;

public interface MonitorDataService {

/*
 * 定时获取redis数据
 */

	boolean sendMessage(String siteId,HttpSession session);
	
	void saveGytbgn(GytEntity e);
	   
	void saveInformation(GytEntity e);
	   
    void saveTitleInfo(GytEntity e);
	   
	void savePointInfo(GytEntity e);
	
	   void updateGytbgn(GytEntity e);//更新工艺图底图信息
	   
	   void updateInformation(GytEntity e);//更新数据信息
	   
	   void updateTitleInfo(GytEntity e);//更新标题信息
	   
	   void updatePointInfo(GytEntity e);//更新泵信息
	   
	   void deleteGytbgn(String siteId);//删除所有信息
	   
	   void deleteInformation(String siteId,String divId);//删除数据信息
	   
	   void deleteTitleInfo(String siteId,String divId);//删除标题信息
	   
	   void deletePointInfo(String siteId,String divId);//删除泵信息
	   
	   List<GytEntity> queryGytAll();//获取所有工艺图底图信息
	   
	   List<GytEntity> queryGyt(String siteId);//获取某个点位下的工艺图底图信息
	   
	   List<GytEntity> queryTitle(String siteId);//获取某个点位下的所有标题信息
	   
	   List<GytEntity> queryInformation(String siteId);//获取某个点位下的所有数据信息
	   
	   List<GytEntity> queryPoint(String siteId);//获取某个点位下的所有泵信息
	   
       List<GytEntity> queryTitle2(String siteId,String divId);//获取某个点位下的所有标题信息
	   
	   List<GytEntity> queryInformation2(String gytId,String divId);//获取某个点位下的所有数据信息
	   
	   List<GytEntity> queryPoint2(String siteId,String divId);//获取某个点位下的所有泵信息
	   
	   List<GytBaseInfoEntity> queryGytbase(GytBaseInfoEntity gb);//获取工艺图基础列表
	   
	   List<GytBaseInfoEntity> queryGytgl(String siteParentId);//获取工艺图关联信息
	   
	   int queryGytbaseCount(GytBaseInfoEntity gb);//获取工艺图基础列表数目
	   
	   void saveGytbase(GytBaseInfoEntity gb);//保存工艺图基础列表
	   
	   void updateGytbase(GytBaseInfoEntity gb);//修改工艺图基础列表
	   
	   void deleteGytbase(int id);//删除工艺图基础列表
	   
	   GytBaseInfoEntity queryGytbaseId(int id);//根据id查基础工艺图信息
	   
	   void saveSeat(SeatEntity gb);//保存工艺图关联信息
	   
	   void updateSeat(SeatEntity gb);//修改工艺图关联信息
	   
	   void deleteSeat(String linkParentId,String linkSiteId,String siteId);//删除工艺图关联信息
	   
	   List<SeatEntity> querySeat(String linkParentId,String linkSiteId,String siteId);//请求关联信息
	   
	   List<SeatEntity> querySeat2(String siteId,String divId);//请求关联信息

	   List<AnalysisEntity> queryAnalysisHourFlow(String siteId,String stime,String etime);
	   
	   List<AnalysisEntity> queryAnalysisDayFlow(String siteId,String stime,String etime);
	   
	   List<AnalysisEntity> queryAnalysisMonthFlow(String siteId,String stime,String etime);
	   
	   List<AnalysisEntity> queryAnalysisYearFlow(String siteId,String stime,String etime);
}

