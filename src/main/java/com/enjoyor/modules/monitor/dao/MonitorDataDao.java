package com.enjoyor.modules.monitor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.AnalysisEntity;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.SeatEntity;

/**
 * 
 * 
 */
public interface MonitorDataDao {
	
   void saveGytbgn(GytEntity e);//保存工艺图底图信息
   
   void saveInformation(GytEntity e);//保存数据信息
   
   void saveTitleInfo(GytEntity e);//保存标题信息
   
   void savePointInfo(GytEntity e);//保存泵信息
   
   void updateGytbgn(GytEntity e);//更新工艺图底图信息
   
   void updateInformation(GytEntity e);//更新数据信息
   
   void updateTitleInfo(GytEntity e);//更新标题信息
   
   void updatePointInfo(GytEntity e);//更新泵信息
   
   void deleteGytbgn(String siteId);//删除所有信息
   
   void deleteInformation(@Param("siteId")String siteId,@Param("divId")String divId);//删除数据信息
   
   void deleteTitleInfo(@Param("siteId")String siteId,@Param("divId")String divId);//删除标题信息
   
   void deletePointInfo(@Param("siteId")String siteId,@Param("divId")String divId);//删除泵信息
   
   List<GytEntity> queryGytAll();//获取所有工艺图底图信息
   
   List<GytEntity> queryGyt(String siteId);//获取某个点位下的工艺图底图信息
   
   List<GytEntity> queryTitle(String siteId);//获取某个点位下的所有标题信息
   
   List<GytEntity> queryInformation(String siteId);//获取某个点位下的所有数据信息
   
   List<GytEntity> queryPoint(String siteId);//获取某个点位下的所有泵信息
   
   List<GytEntity> queryTitle2(@Param("siteId")String siteId,@Param("divId")String divId);//获取某个点位下的所有标题信息
   
   List<GytEntity> queryInformation2(@Param("gytId")String gytId,@Param("divId")String divId);//获取某个点位下的所有数据信息
   
   List<GytEntity> queryPoint2(@Param("siteId")String siteId,@Param("divId")String divId);//获取某个点位下的所有泵信息
   
   List<GytBaseInfoEntity> queryGytgl(String siteParentId);//获取工艺图关联信息
   
   List<GytBaseInfoEntity> queryGytbase(GytBaseInfoEntity gb); //获取工艺图基础列表
   
   int queryGytbaseCount(GytBaseInfoEntity gb);//获取工艺图基础列表数目
   
   void saveGytbase(GytBaseInfoEntity gb);//保存工艺图基础列表
   
   void updateGytbase(GytBaseInfoEntity gb);//修改工艺图基础列表
   
   void deleteGytbase(int id);//删除工艺图基础列表
   
   GytBaseInfoEntity queryGytbaseId(int id);//根据id查基础工艺图信息
   
   void saveSeat(SeatEntity gb);//保存工艺图关联信息
   
   void updateSeat(SeatEntity gb);//修改工艺图关联信息
   
   void deleteSeat(@Param("linkParentId")String linkParentId,@Param("linkSiteId")String linkSiteId,@Param("siteId")String siteId);//删除工艺图关联信息
   
   List<SeatEntity> querySeat(@Param("linkParentId")String linkParentId,@Param("linkSiteId")String linkSiteId,@Param("siteId")String siteId);//请求关联信息

   List<SeatEntity> querySeat2(@Param("siteId")String siteId,@Param("divId")String divId);//请求关联信息
   
   List<AnalysisEntity> queryAnalysisHourFlow(@Param("siteId")String siteId,@Param("stime")String stime,@Param("etime")String etime);

   List<AnalysisEntity> queryAnalysisDayFlow(@Param("siteId")String siteId,@Param("stime")String stime,@Param("etime")String etime);
   
   List<AnalysisEntity> queryAnalysisMonthFlow(@Param("siteId")String siteId,@Param("stime")String stime,@Param("etime")String etime);
   
   List<AnalysisEntity> queryAnalysisYearFlow(@Param("siteId")String siteId,@Param("stime")String stime,@Param("etime")String etime);

}
