package com.enjoyor.modules.monitor.service.imp;

import com.enjoyor.modules.monitor.dao.FlowNightDao;
import com.enjoyor.modules.monitor.dao.PipeDataDao;
import com.enjoyor.modules.monitor.dao.PipeDataHisDao;
import com.enjoyor.modules.monitor.entity.FlowNightEntity;
import com.enjoyor.modules.monitor.entity.FlowReportFormEntity;
import com.enjoyor.modules.monitor.entity.PipeData;
import com.enjoyor.modules.monitor.entity.PipeDataDTO;
import com.enjoyor.modules.monitor.entity.PipeDataHis;
import com.enjoyor.modules.monitor.service.PipeDataHisService;
import com.enjoyor.modules.monitor.service.PipeDataService;
import com.enjoyor.modules.sys.dao.SysSiteDao;
import com.enjoyor.modules.sys.entity.SysSiteEntity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;



@Service("pipeDataHisService")
public class PipeDataHisServiceimp implements PipeDataHisService {

	@Autowired
	PipeDataHisDao pipeDataHisDao;
	@Autowired
	SysSiteDao sysSiteDao;
	@Autowired
	FlowNightDao flowNightDao;

	@Override
	public List<PipeDataHis> queryHisData(String siteId, String type, String startTime, String endTime) {
		String dicValue = sysSiteDao.queryDicValue(siteId);
		List<PipeDataHis> list = new ArrayList<PipeDataHis>();
		// ???????????????
		// String sql2=" from PIPESYS.PIPE_DATA_HIS where POINTNUM='" + siteId + "' and time between to_date('"+ startTime + "','yyyy/mm/dd HH24:MI:SS') and to_date('"+ endTime + "','yyyy/mm/dd HH24:MI:SS') and (to_char(time,'yyyy/mm/dd HH24:MI:SS') LIKE '%:%:00' or to_char(time,'yyyy/mm/dd HH24:MI:SS') LIKE '%:%:30')order by time asc";
		// ???????????????5???????????????
		String sql1 = "select ";
		String column = "";
		String sql3 = "";
		String sqlComplete = "";
		// ??????
		if("S03".equals(dicValue)){
			sql3=", SITE_ID, time from MONITOR.MONITORTOTAL where SITE_ID = '" + siteId + "' and time between '"+ startTime + "' and '"+ endTime + "' order by time asc";
	
			// sql3 = ", SITE_ID, time from MONITOR.MONITORTOTAL where SITE_PARENTID = '" + siteId + "' and SITE_NAME like '%??????' and time between '"+ startTime + "' and '"+ endTime + "' order by time asc";

			switch (type) {
			case "PTOUT":
				column="PRESSURE_OUTVALUE as YL";
				break;
			case "FLS":
				column="FLOW_VALUE as SS";
				break;
			case "FLC":
				column="TOTALFLOW_VALUE JLJ";
				break;
			case "HOCL":
				column="CHLORINE_VALUE as CHLORINE";
				break;
			case "TUR":
				column="TURBIDITY_VALUE as TURBIDITY";
				break;
			case "PH":
				column="PH_VALUE as PH";
				break;
			case "TP":
				column="TP_VALUE as TP";
				break;
			case "TN":
				column="TN_VALUE as TN";
				break;
			case "COD":
				column="COD_VALUE as COD";
				break;
			case "LT":
				column="LT_VALUE";
				break;
			default:
				return list;
			}	
		} else{ // ?????????
			sql3=", POINTNUM, time from PIPESYS.PIPE_DATA_HIS where POINTNUM='" + siteId + "' and time between to_date('"+ startTime + "','yyyy/mm/dd HH24:MI:SS') and to_date('"+ endTime + "','yyyy/mm/dd HH24:MI:SS') order by time asc";
			switch (type) {
			case "PTOUT":
				column="YL";
				break;
			case "FLS":
				column="SS";
				break;
			case "FLC":
				column="JLJ";
				break;
			case "HOCL":
				column="CHLORINE";
				break;
			case "TUR":
				column="TURBIDITY";
				break;
			case "PH":
				column="PH";
				break;
			case "TP":
				column="TP";
				break;
			case "TN":
				column="TN";
				break;
			case "NH4":
				column="NH4";
				break;
			case "COD":
				column="COD";
				break;
			case "HZD":
				column="HZD";
				break;
			case "LT":
				column="LT_VALUE";
				break;
			default:
				return list;
			}
		}
		
		sqlComplete = sql1 + column + sql3;
		list = pipeDataHisDao.queryHisData(sqlComplete);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(PipeDataHis pipeDataHis : list){
			pipeDataHis.setStringTime(sdf.format(pipeDataHis.getTime()));
			if(null != pipeDataHis.getYl()){
				pipeDataHis.setYl(pipeDataHis.getYl().trim());
			}
		}
		return list;
	}
	
	@Override
	public void exportExcel(String areaId, String areaName, String time, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		//??????HSSFWorkbook??????
		HSSFWorkbook wb = new HSSFWorkbook();
		//??????HSSFSheet??????
		HSSFSheet sheet = wb.createSheet(time); 
		
		HSSFCellStyle columnHeadStyle = wb.createCellStyle();   
		//columnHeadStyle.setAlignment(HorizontalAlignment.CENTER);
		
		Row row = null;  
        Cell cell = null;  
        for(int i=0; i<4; i++){  
        	row = sheet.createRow(i);  
        	for(int j=0;j<25;j++){  
            	cell = row.createCell(j);
            	cell.setCellStyle(columnHeadStyle);
            	// ?????????
            	if(i==0){
            		if(j==0){
                		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 17));  
                		cell.setCellValue("                                 " + areaName + "???????????????           ???????????????" + time + "      ????????????");        	
                	}
            		else if(j==18){
                		sheet.addMergedRegion(new CellRangeAddress(0, 3, 18, 18)); 
                		cell.setCellValue("??????????????????");
                	}
            		else if(j==19){
                		sheet.addMergedRegion(new CellRangeAddress(0, 3, 19, 19)); 
                		cell.setCellValue("???????????????");
                	}
            		else if(j==20){
                		sheet.addMergedRegion(new CellRangeAddress(0, 3, 20, 20)); 
                		cell.setCellValue("??????????????????/???????????????");
                	}
            		else if(j==21){
                		sheet.addMergedRegion(new CellRangeAddress(0, 3, 21, 21)); 
                		cell.setCellValue("?????????????????????");
                	}
            		else if(j==22){
                		sheet.addMergedRegion(new CellRangeAddress(0, 3, 22, 22)); 
                		cell.setCellValue("?????????????????????");
                	}
            		else if(j==23){
                		sheet.addMergedRegion(new CellRangeAddress(0, 3, 23, 23)); 
                		cell.setCellValue("?????????");
                	}
            		else if(j==24){
                		sheet.addMergedRegion(new CellRangeAddress(0, 3, 24, 24)); 
                		cell.setCellValue("?????????");
                	}
            	}
            	
            	// ?????????
            	if(i==1){
            		if(j==0){
                		sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0)); 
                		cell.setCellValue("????????????");
                	}
            		else if(j==1){
                		sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1)); 
                		cell.setCellValue("??????");
                	}
            		else if(j==2){
            			sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 17)); 
                		cell.setCellValue("??????");
            		}
            	}
            	
            	//?????????
            	if(i==2){
            		if(j==2){
            			sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3)); 
            			cell.setCellValue("0:00");	
                	}
            		else if(j==4){
            			sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5)); 
            			cell.setCellValue("3:00");	
            		}
            		else if(j==6){
            			sheet.addMergedRegion(new CellRangeAddress(2, 2, 6, 7)); 
            			cell.setCellValue("6:00");	
            		}
            		else if(j==8){
            			sheet.addMergedRegion(new CellRangeAddress(2, 2, 8, 9)); 
            			cell.setCellValue("9:00");	
            		}
            		else if(j==10){
            			sheet.addMergedRegion(new CellRangeAddress(2, 2, 10, 11)); 
            			cell.setCellValue("12:00");	
            		}
            		else if(j==12){
            			sheet.addMergedRegion(new CellRangeAddress(2, 2, 12, 13)); 
            			cell.setCellValue("15:00");	
            		}
            		else if(j==14){
            			sheet.addMergedRegion(new CellRangeAddress(2, 2, 14, 15)); 
            			cell.setCellValue("18:00");	
            		}
            		else if(j==16){
            			sheet.addMergedRegion(new CellRangeAddress(2, 2, 16, 17)); 
            			cell.setCellValue("21:00");	
            		}
            	}
            	
            	//?????????
            	if(i==3){
            		if(j>=2 && j<=16 && j%2==0){
            			cell.setCellValue("??????Mpa");	
                	}
            		else if(j>=3 && j<=17 && j%2==1){
            			cell.setCellValue("??????m3/h");	
            		}
            	} 	
        	}        	
        }
        
        // ???????????????????????????????????????
        List<SysSiteEntity> siteList = sysSiteDao.queryByAreaId(areaId);
        int siteCount = siteList.size();
        
        // ???????????????????????????????????????
 		String beginTime = time + " 00:00:00";
 		String endTime = time + " 21:00:00";
 		List<PipeDataHis> dataList = pipeDataHisDao.exportExcel(areaId, beginTime, endTime);
 		
        
        // ?????????flowNight??????
        List<FlowNightEntity> flowDataList = flowNightDao.queryFlowByArea(areaId, beginTime);
   
        // ???????????????????????????
    	int i = 4;
        for(SysSiteEntity site : siteList){
        	row = sheet.createRow(i);  
        	row.createCell(1).setCellValue(site.getName());
        	for(int j=2; j<23; j++){
        		row.createCell(j).setCellValue("???");
        	}
        	for(PipeDataHis data : dataList){
        		if(null != site.getSiteId() && null != data.getPointNum()){
        			if(site.getSiteId().equals(data.getPointNum())){
            			String yl = data.getYl();
            			if(yl == null || "".equals(yl)){
            				yl = "???";
            			}
            			Double ssDouble = data.getSs();
            			String ss = "???";
            			if(ssDouble == null){
            				ss = "???";
            			} else {
            				ss = ssDouble.toString();
            			}  			
            			if(data.getTime().toString().contains(" 00:00:00")){
                			row.createCell(2).setCellValue(yl);
                			row.createCell(3).setCellValue(ss);
                		} else if(data.getTime().toString().contains(" 00:00:")){
                			row.createCell(2).setCellValue(yl);
                			row.createCell(3).setCellValue(ss);
                		}
            			     			
            			if(data.getTime().toString().contains(" 03:00:00")){
                			row.createCell(4).setCellValue(yl);
                			row.createCell(5).setCellValue(ss);
                		} else if(data.getTime().toString().contains(" 03:00:")){
                			row.createCell(4).setCellValue(yl);
                			row.createCell(5).setCellValue(ss);
                		}
            			
            			if(data.getTime().toString().contains(" 06:00:00")){
                			row.createCell(6).setCellValue(yl);
                			row.createCell(7).setCellValue(ss);
                		} else if(data.getTime().toString().contains(" 06:00:")){
                			row.createCell(6).setCellValue(yl);
                			row.createCell(7).setCellValue(ss);
                		}
            			
            			if(data.getTime().toString().contains(" 09:00:00")){
                			row.createCell(8).setCellValue(yl);
                			row.createCell(9).setCellValue(ss);
                		} else if(data.getTime().toString().contains(" 09:00:")){
                			row.createCell(8).setCellValue(yl);
                			row.createCell(9).setCellValue(ss);
                		}
            			        			
            			if(data.getTime().toString().contains(" 12:00:00")){
                			row.createCell(10).setCellValue(yl);
                			row.createCell(11).setCellValue(ss);
                		} else if(data.getTime().toString().contains(" 12:00:")){
                			row.createCell(10).setCellValue(yl);
                			row.createCell(11).setCellValue(ss);
                		}
            			
            			if(data.getTime().toString().contains(" 15:00:00")){
                			row.createCell(12).setCellValue(yl);
                			row.createCell(13).setCellValue(ss);
                		} else if(data.getTime().toString().contains(" 15:00:")){
                			row.createCell(12).setCellValue(yl);
                			row.createCell(13).setCellValue(ss);
                		}
            			
            			if(data.getTime().toString().contains(" 18:00:00")){
                			row.createCell(14).setCellValue(yl);
                			row.createCell(15).setCellValue(ss);
                		} else if(data.getTime().toString().contains(" 18:00:")){
                			row.createCell(14).setCellValue(yl);
                			row.createCell(15).setCellValue(ss);
                		}
            			
            			if(data.getTime().toString().contains(" 21:00:00")){
                			row.createCell(16).setCellValue(yl);
                			row.createCell(17).setCellValue(ss);
                		} else if(data.getTime().toString().contains(" 21:00:")){
                			row.createCell(16).setCellValue(yl);
                			row.createCell(17).setCellValue(ss);
                		}
            		}   
        		}
        		  	
        	}
        	for(FlowNightEntity flowData : flowDataList){
        		if(null != site.getSiteId() && null != flowData.getSiteId()){
        			if(site.getSiteId().equals(flowData.getSiteId())){
            			row.createCell(18).setCellValue(flowData.getMin());
            			row.createCell(19).setCellValue(flowData.getAvgDay());
            			row.createCell(20).setCellValue(flowData.getValue2());
            			row.createCell(21).setCellValue(flowData.getDaysavg());
            			row.createCell(22).setCellValue(flowData.getNightsavg());
            			row.createCell(23).setCellValue(flowData.calcRbl());
            			row.createCell(24).setCellValue(flowData.calcYbl());
            		}
        		}
        		
        	}
        	i++;
        }        
        response.reset();
        String fileName = areaName + time;
        response.setHeader("Content-disposition", "attachment;filename="+new String((areaName+ time).getBytes(),"iso-8859-1")+".xls");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");   
        OutputStream output = response.getOutputStream();
        wb.write(output);
        output.close();
        
	}
	
	@Override
	public List<FlowReportFormEntity> queryFlowReportForm(String areaId, String areaName, String time){
		
		// ???????????????????????????????????????
        List<SysSiteEntity> siteList = sysSiteDao.queryByAreaId(areaId);
        
        // ???????????????????????????????????????
 		String beginTime = time + " 00:00:00";
 		String endTime = time + " 21:00:00";
 		List<PipeDataHis> hisList = pipeDataHisDao.exportExcel(areaId, beginTime, endTime);
 		
        // ?????????flowNight??????
        List<FlowNightEntity> flowDataList = flowNightDao.queryFlowByArea(areaId, beginTime);
        
        // ????????????
        List<FlowReportFormEntity> list = new ArrayList<FlowReportFormEntity>();
        
        // ??????siteId
        for(SysSiteEntity site : siteList){
        	FlowReportFormEntity entity = new FlowReportFormEntity();
        	entity.setSiteId(site.getSiteId());
        	entity.setSiteName(site.getName());
        	entity.setSs00("???");
        	entity.setYl00("???");
        	entity.setSs03("???");
        	entity.setYl03("???");
        	entity.setSs06("???");
        	entity.setYl06("???");
        	entity.setSs09("???");
        	entity.setYl09("???");
        	entity.setSs12("???");
        	entity.setYl12("???");
        	entity.setSs15("???");
        	entity.setYl15("???");
        	entity.setSs18("???");
        	entity.setYl18("???");
        	entity.setSs21("???");
        	entity.setYl21("???");
        	list.add(entity);
        }
        
        // ??????????????????
        for(FlowReportFormEntity entity : list){
        	for(PipeDataHis his : hisList){
        		if(his.getPointNum().equals(entity.getSiteId())){
        			String yl = his.getYl();
        			if(yl == null || "".equals(yl)){
        				yl = "???";
        			}
        			Double ssDouble = his.getSs();
        			String ss = "???";
        			if(ssDouble == null){
        				ss = "???";
        			} else {
        				ss = ssDouble.toString();
        			}
        			if(his.getTime().toString().contains(" 00:00:00")){
        				entity.setYl00(yl);
        				entity.setSs00(ss);
            		} else if(his.getTime().toString().contains(" 00:00:")){
            			entity.setYl00(yl);
        				entity.setSs00(ss);
            		}
        			
        			if(his.getTime().toString().contains(" 03:00:00")){
        				entity.setYl03(yl);
        				entity.setSs03(ss);
            		} else if (his.getTime().toString().contains(" 03:00:")){
            			entity.setYl03(yl);
        				entity.setSs03(ss);
            		}
        			
        			if(his.getTime().toString().contains(" 06:00:00")){
        				entity.setYl06(yl);
        				entity.setSs06(ss);
            		} else if(his.getTime().toString().contains(" 06:00:")){
            			entity.setYl06(yl);
        				entity.setSs06(ss);
            		}
        			
        			if(his.getTime().toString().contains(" 09:00:00")){
        				entity.setYl09(yl);
        				entity.setSs09(ss);
            		} else if(his.getTime().toString().contains(" 09:00:")){
            			entity.setYl09(yl);
        				entity.setSs09(ss);
            		}
        			
        			if(his.getTime().toString().contains(" 12:00:00")){
        				entity.setYl12(yl);
        				entity.setSs12(ss);
            		} else if(his.getTime().toString().contains(" 12:00:")){
            			entity.setYl12(yl);
        				entity.setSs12(ss);
            		}
        			
        			if(his.getTime().toString().contains(" 15:00:00")){
        				entity.setYl15(yl);
        				entity.setSs15(ss);
            		} else if(his.getTime().toString().contains(" 15:00:")){
            			entity.setYl15(yl);
        				entity.setSs15(ss);
            		}
        			
        			if(his.getTime().toString().contains(" 18:00:00")){
        				entity.setYl18(yl);
        				entity.setSs18(ss);
            		} else if(his.getTime().toString().contains(" 18:00:00")){
            			entity.setYl18(yl);
        				entity.setSs18(ss);
            		}
        			
        			if(his.getTime().toString().contains(" 21:00:00")){
        				entity.setYl21(yl);
        				entity.setSs21(ss);
            		} else if(his.getTime().toString().contains(" 21:00:")){
            			entity.setYl21(yl);
        				entity.setSs21(ss);
            		}
        		}               		
        	}
        	for(FlowNightEntity flow : flowDataList){
        		if(null != entity.getSiteId() && null != flow.getSiteId()){
        			if(entity.getSiteId().equals(flow.getSiteId())){
            			entity.setMin(flow.getMin());
            			entity.setAvgDay(flow.getAvgDay());
            			entity.setAvgNig(flow.getAvgNig());
            			entity.setValue2(flow.getValue2());
            			entity.setDaysavg(flow.getDaysavg());
            			entity.setNightsavg(flow.getNightsavg());
            			entity.calcRbl();
            			entity.calcYbl();
            		}
        		}
        	}
        }
        
		return list;
	}
	
}
