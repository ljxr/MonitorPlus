package com.enjoyor.modules.monitor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.enjoyor.modules.monitor.entity.BigDataDTO;

/**
 * 大数据DAO
 * 
 */
@Repository
public interface BigDataDao {
	
	List<BigDataDTO> queryBigUser();//查询大用户数
	
	
	
	List<BigDataDTO> queryYS(@Param("time") String time);//管网水水质合格率
	
	List<BigDataDTO> queryWQ();//污水厂水质国标
	
	List<BigDataDTO> queryDiameter();//根据管径查出管径类型和数量
	
	List<BigDataDTO> queryMaterial();//根据材料查出材料类型和数量
	
	List<BigDataDTO> queryPArea();//根据区域查出区域类型和数量
	
	int queryTotalLength();//管网总长度
	
	int queryPipeInsCounts(@Param("time") String time);//本月新增管线数
	
	
	List<BigDataDTO> queryTodayOrderNum(@Param("time") String time);//查出今日各个状态的工单数
	
	
	
	
	
	
	List<BigDataDTO> querySewage(@Param("time") String time);//污水处理量
	
	List<BigDataDTO> queryPSewage(@Param("time") String time,@Param("time2") String time2);//污水排水量和污泥处理量
	
	List<BigDataDTO> queryYPSewage(@Param("time") String time);//污水排水量和污泥处理量	
	
	//污水厂运营成本
	List<BigDataDTO> queryWCost(@Param("time") String time,@Param("time2") String time2);
	
	//水厂年运营成本
	List<BigDataDTO> queryYearSCost(@Param("time2") String time2);
	
	//污水厂年运营成本
	List<BigDataDTO> queryYearWCost(@Param("time2") String time2);
	
	//*********************分割线*************************
	
	int queryOrderNum(@Param("time") String time,@Param("time2") String time2);//根据时间查出工单数量
	
	List<BigDataDTO> queryBusinessOrderNum(@Param("time")String time,@Param("type")String type);
	
	List<BigDataDTO> querySafeInfo(@Param("time") String time);//发现隐患数
	
	List<BigDataDTO> queryChanged(@Param("time") String time);//隐患整改数
	
	List<BigDataDTO> queryCCS(@Param("time") String time);//出厂水水质合格率
	
	List<BigDataDTO> queryQWS(@Param("time") String time);//出厂水水质合格率
	
    List<BigDataDTO> queryWS(@Param("time")String time,@Param("siteId")String siteId);//污出厂水水质指标
    
    int queryEquipBer(@Param("time")String time,@Param("ownsys")String ownsys,@Param("id")String id);//设备完好率
    
	int queryEquipWork(@Param("time") String time,@Param("type") String type,@Param("status") String status,@Param("name") String name);//各设备类型数目

	List<BigDataDTO> querySWater(@Param("time")String time,@Param("areaId")String areaId);//供售水产销差
	
	List<BigDataDTO> querySWater2(@Param("stime")String stime,@Param("etime")String etime,@Param("areaId")String areaId);//年供售水产销差

	List<BigDataDTO> querySCost(@Param("time") String time);	//水厂运营成本
	
	List<BigDataDTO> querySCostYear(@Param("stime")String stime,@Param("etime")String etime);//水厂年运营成本
}
