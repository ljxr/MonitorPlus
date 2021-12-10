package com.enjoyor.modules.monitor.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import com.enjoyor.modules.monitor.entity.AlarmTotalEntity;
import com.enjoyor.modules.monitor.entity.AlarmTypeFromDic;
import com.enjoyor.modules.monitor.entity.BigDataDTO;
import com.enjoyor.modules.monitor.entity.GytBaseInfoEntity;
import com.enjoyor.modules.monitor.entity.GytEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.SeatEntity;
import com.enjoyor.modules.monitor.entity.ThresholdAndName;

public interface BigDataService {

	List<BigDataDTO> queryBigUser();//查询大用户数
	
	List<BigDataDTO> queryCCS(String time);//出厂水水质合格率
	
	List<BigDataDTO> queryYS(String time);//管网水水质合格率
	
    List<BigDataDTO> queryWS(String time);//污出厂水水质合格率
	
	List<BigDataDTO> queryWQ();//污水厂水质国标
	
	List<BigDataDTO> queryDiameter();//根据管径查出管径类型和数量
	
	List<BigDataDTO> queryMaterial();//根据材料查出材料类型和数量
	
	List<BigDataDTO> queryPArea();//根据区域查出区域类型和数量
	
    int queryTotalLength();//管网总长度
	
	int queryPipeInsCounts(String time);//本月新增管线数
	
    int queryOrderNum(String time);//根据时间查出工单数量
	
	List<BigDataDTO> queryTodayOrderNum(String time);//查出今日各个状态的工单数
	
	int querySafeInfo(String time);
	
	int queryChanged(String time);
	
	List<BigDataDTO> queryEquipWork(String time);//各设备类型数目
	
	int queryEquipBer(String id);//设备个数
	
	List<BigDataDTO> querySWater(int year,int month,String areaId);//供售水产销差
	
	List<BigDataDTO> querySewage(String time);//污水处理量
	
	List<BigDataDTO> queryPSewage(String time,String time2);//污水排水量和污泥处理量
	
	List<BigDataDTO> queryYPSewage(String time);//污水排水量和污泥处理量
	
	//水厂运营成本
	List<BigDataDTO> querySCost(String time,String time2);
	
	//污水厂运营成本
	List<BigDataDTO> queryWCost(String time,String time2);
	
	//水厂年运营成本
	List<BigDataDTO> queryYearSCost(String time2);
	
	//污水厂年运营成本
	List<BigDataDTO> queryYearWCost(String time2);
	
	
	//*****************分割线*********************
	//工单
	Map<String,Object> order(String time);
	
	//安全隐患
	Map<String,Object> safe(String time);
	
	//水质
	Map<String,Object> waterQuality(List<String> siteId);
	
	//设备
	Map<String,Object> equip(String type);
	
	//产销差
	Map<String,Object> water(String time);
	
	//供售水
	Map<String,Object> supply();
	
	//成本分析
	Map<String,Object> cost(String time,String siteId);
}

