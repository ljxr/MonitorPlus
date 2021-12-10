package com.enjoyor.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoyor.modules.dma.entity.DmaDataEntity;
import com.enjoyor.modules.monitor.entity.AnalysisEntity;
import com.enjoyor.modules.monitor.entity.FlowNightEntity;

@Service
public class ExcelBook {
	
	/**
	 * <p>Title:供水统计导出 </p>  
	 * <p>Description: </p>  
	 * @author   
	 * @date 2018年7月20日
	 */
	public static  void excelArea(Map<String, Object> arrayList,HttpServletRequest request,HttpServletResponse response) {   		
		
	    HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件
	  
	    HSSFSheet sheet = workbook.createSheet();// 创建一个Excel的Sheet
	    String siteName = (String)arrayList.get("siteName");
	    List<AnalysisEntity> result = (List<AnalysisEntity>)arrayList.get("result");
	    
	    try {
	      String[] site = siteName.split(",");
	      HSSFRow row = sheet.createRow(0);
	      
	      HSSFCell cell01 = row.createCell(0);
		  cell01.setCellValue(new HSSFRichTextString("时间"));
		  
	      for (int k = 0; k < site.length; k++) {
             HSSFCell cell2 = row.createCell(k+1);
	    	 cell2.setCellValue(new HSSFRichTextString(site[k])); 	    	  
		  }      	      
	      
	      for (int m = 0; m < result.size(); m++) { 
	    	  HSSFRow row1 = sheet.createRow(m+1);
	    	  AnalysisEntity ae = result.get(m);
	    	  HSSFCell cell0 = row1.createCell(0);
    		  cell0.setCellValue(new HSSFRichTextString(ae.getTime()));
    		  String[] value = ae.getValue1().split(",");
    		  for (int n = 0; n < value.length; n++) {
    			 // System.out.println(value[n]);
    			  HSSFCell cell1 = row1.createCell(n+1);
        		  cell1.setCellValue(new HSSFRichTextString(value[n]));
			  }
	      } 
	      String fileName ="供水统计";
	      response.reset();
	      response.setContentType("application/vnd.ms-excel;charset=utf-8");
	      response.setHeader("Content-disposition", "attachment;filename=" + processFileName(request,fileName) + ".xls");   
	      OutputStream ouputStream = response.getOutputStream();	    
	      //FileOutputStream out = new FileOutputStream(returnDeskPath("供水统计")); //返回对方桌面文件路径
	      workbook.write(ouputStream);   
	      ouputStream.close();
	    } catch (Exception e) {   
	      e.printStackTrace();   
	    }   
	    //return workbook;
	  }
	
	/**
	 * <p>Title:夜间小流量统计导出 </p>  
	 * <p>Description: </p>  
	 * @author   
	 * @date 2018年7月20日
	 */
	public static  void excelFlowNight(Map<String, Object> arrayList,HttpServletRequest request,HttpServletResponse response) {   		
		
	    HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件
	  
	    HSSFSheet sheet = workbook.createSheet();// 创建一个Excel的Sheet
	    //String siteName = (String)arrayList.get("siteName");
	    List<FlowNightEntity> result = (List<FlowNightEntity>)arrayList.get("list");
	    
	    try {
	      HSSFRow row = sheet.createRow(0);
	      
	      HSSFCell cell01 = row.createCell(0);
		  cell01.setCellValue(new HSSFRichTextString("时间"));
		  HSSFCell cell02 = row.createCell(1);
		  cell02.setCellValue(new HSSFRichTextString("营业所名称")); 
		  HSSFCell cell03 = row.createCell(2);
		  cell03.setCellValue(new HSSFRichTextString("X")); 
		  HSSFCell cell04 = row.createCell(3);
		  cell04.setCellValue(new HSSFRichTextString("Y")); 
		  HSSFCell cell05 = row.createCell(4);
		  cell05.setCellValue(new HSSFRichTextString("最小流量")); 
		  HSSFCell cell06 = row.createCell(5);
		  cell06.setCellValue(new HSSFRichTextString("出现时间")); 
		  HSSFCell cell07 = row.createCell(6);
		  cell07.setCellValue(new HSSFRichTextString("夜平均流量")); 
		  HSSFCell cell08 = row.createCell(7);
		  cell08.setCellValue(new HSSFRichTextString("七日夜平均流量")); 
		  HSSFCell cell09 = row.createCell(8);
		  cell09.setCellValue(new HSSFRichTextString("夜倍率")); 
		  HSSFCell cell10 = row.createCell(9);
		  cell10.setCellValue(new HSSFRichTextString("日平均流量")); 
		  HSSFCell cell11 = row.createCell(10);
		  cell11.setCellValue(new HSSFRichTextString("七日日平均流量")); 
		  HSSFCell cell12 = row.createCell(11);
		  cell12.setCellValue(new HSSFRichTextString("日倍率")); 
		  HSSFCell cell13 = row.createCell(12);
		  cell13.setCellValue(new HSSFRichTextString("对应压力")); 
	      
	      for (int m = 0; m < result.size(); m++) { 
	    	  HSSFRow row1 = sheet.createRow(m+1);
	    	  FlowNightEntity ae = result.get(m);
	    	  HSSFCell cell0 = row1.createCell(0);
    		  cell0.setCellValue(new HSSFRichTextString(ae.getTime()));
    		  HSSFCell cell1 = row1.createCell(1);
    		  cell1.setCellValue(new HSSFRichTextString(ae.getYysName())); 
    		  HSSFCell cell2 = row1.createCell(2);
    		  cell2.setCellValue(new HSSFRichTextString(String.valueOf(ae.getValue()*100))); 
    		  HSSFCell cell3 = row1.createCell(3);
    		  cell3.setCellValue(new HSSFRichTextString(String.valueOf(ae.getValue2()*100))); 
    		  HSSFCell cell4 = row1.createCell(4);
    		  cell4.setCellValue(new HSSFRichTextString(String.valueOf(ae.getMin()))); 
    		  HSSFCell cell5 = row1.createCell(5);
    		  cell5.setCellValue(new HSSFRichTextString(ae.getTime2())); 
    		  HSSFCell cell6 = row1.createCell(6);
    		  cell6.setCellValue(new HSSFRichTextString(String.valueOf(ae.getAvgNig()))); 
    		  HSSFCell cell7 = row1.createCell(7);
    		  cell7.setCellValue(new HSSFRichTextString(String.valueOf(ae.getNightsavg()))); 
    		  HSSFCell cell8 = row1.createCell(8);
    		  cell8.setCellValue(new HSSFRichTextString(String.valueOf(ae.getYbl()*100))); 
    		  HSSFCell cell9 = row1.createCell(9);
    		  cell9.setCellValue(new HSSFRichTextString(String.valueOf(ae.getAvgDay()))); 
    		  HSSFCell cell101 = row1.createCell(10);
    		  cell101.setCellValue(new HSSFRichTextString(String.valueOf(ae.getDaysavg()))); 
    		  HSSFCell cell111 = row1.createCell(11);
    		  cell111.setCellValue(new HSSFRichTextString(String.valueOf(ae.getRbl()*100))); 
    		  HSSFCell cell121 = row1.createCell(12);
    		  cell121.setCellValue(new HSSFRichTextString(String.valueOf(ae.getPressure()))); 
	      } 
	      String fileName ="夜间流量统计";
	      response.reset();
	      response.setContentType("application/vnd.ms-excel;charset=utf-8");
	      response.setHeader("Content-disposition", "attachment;filename=" + processFileName(request,fileName) + ".xls");   
	      OutputStream ouputStream = response.getOutputStream();	    
	      //FileOutputStream out = new FileOutputStream(returnDeskPath("供水统计")); //返回对方桌面文件路径
	      workbook.write(ouputStream);   
	      ouputStream.close();
	    } catch (Exception e) {   
	      e.printStackTrace();   
	    }   
	  }

	/**
	 * <p>Title:DMA统计导出 </p>  
	 * <p>Description: </p>  
	 * @author   
	 * @date 2018年7月20日
	 */
	public static  void excelDma(Map<String, Object> arrayList,HttpServletRequest request,HttpServletResponse response) {   		
		
	    HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件
	  
	    HSSFSheet sheet = workbook.createSheet();// 创建一个Excel的Sheet
	    //String siteName = (String)arrayList.get("siteName");
	    List<DmaDataEntity> result = (List<DmaDataEntity>)arrayList.get("data");
	    
	    try {
	      HSSFRow row = sheet.createRow(0);
	      
	      HSSFCell cell01 = row.createCell(0);
		  cell01.setCellValue(new HSSFRichTextString("分区"));
		  HSSFCell cell02 = row.createCell(1);
		  cell02.setCellValue(new HSSFRichTextString("进水量")); 
		  HSSFCell cell03 = row.createCell(2);
		  cell03.setCellValue(new HSSFRichTextString("出水量")); 
		  HSSFCell cell04 = row.createCell(3);
		  cell04.setCellValue(new HSSFRichTextString("漏损率")); 
		  HSSFCell cell05 = row.createCell(4);
		  cell05.setCellValue(new HSSFRichTextString("时间")); 
	      int n =0;
	      for (int m = 0; m < result.size(); m++) { 
	    	  HSSFRow row1 = sheet.createRow(m+1);
	    	  DmaDataEntity ae = result.get(m);
	    	  HSSFCell cell0 = row1.createCell(0);
    		  cell0.setCellValue(new HSSFRichTextString(ae.getDareaName()));
    		  HSSFCell cell1 = row1.createCell(1);
    		  cell1.setCellValue(new HSSFRichTextString(String.valueOf(ae.getDareaFlowIn()))); 
    		  HSSFCell cell2 = row1.createCell(2);
    		  cell2.setCellValue(new HSSFRichTextString(String.valueOf(ae.getDareaFlowOut()))); 
    		  HSSFCell cell3 = row1.createCell(3);
    		  cell3.setCellValue(new HSSFRichTextString(ae.getLsv2())); 
    		  HSSFCell cell4 = row1.createCell(4);
    		  cell4.setCellValue(new HSSFRichTextString(ae.getTime()));
    		  n = m;
	      }
	      
	      HSSFRow row1 = sheet.createRow(n+1);
	      HSSFCell cell0 = row1.createCell(0);
		  cell0.setCellValue(new HSSFRichTextString("合计"));
		  HSSFCell cell1 = row1.createCell(1);
		  cell1.setCellValue(new HSSFRichTextString(String.valueOf(arrayList.get("hjin"))));
		  HSSFCell cell2 = row1.createCell(2);
		  cell2.setCellValue(new HSSFRichTextString(String.valueOf(arrayList.get("hjout"))));
		  HSSFCell cell3 = row1.createCell(3);
		  cell3.setCellValue(new HSSFRichTextString(String.valueOf(arrayList.get("hjlsv"))));
	      
	      String fileName ="DMA统计";
	      response.reset();
	      response.setContentType("application/vnd.ms-excel;charset=utf-8");
	      response.setHeader("Content-disposition", "attachment;filename=" + processFileName(request,fileName) + ".xls");   
	      OutputStream ouputStream = response.getOutputStream();	    
	      //FileOutputStream out = new FileOutputStream(returnDeskPath("供水统计")); //返回对方桌面文件路径
	      workbook.write(ouputStream);   
	      ouputStream.close();
	    } catch (Exception e) {   
	      e.printStackTrace();   
	    }   
	  }
	
	public static  String returnDeskPath(String fileName){
	 	 // 获取当前用户桌面路径
	     File desktopDir = FileSystemView.getFileSystemView()
	     .getHomeDirectory();
	//     String desktopPath = desktopDir.getAbsolutePath().replace("\\", "/")+"/"+fileName;
	     String desktopPath = desktopDir.getAbsolutePath()+"\\"+fileName;
	     return desktopPath;
	 }
	
	public static String processFileName(HttpServletRequest request, String fileName) {  
	    String codedfilename = null;  
	    try {  
	        String agent = request.getHeader("USER-AGENT");  
	        if (null != agent && -1 != agent.indexOf("MSIE") || null != agent  
	                && -1 != agent.indexOf("Trident")) {// ie  
	
	            String name = java.net.URLEncoder.encode(fileName, "UTF8");  
	
	            codedfilename = name;  
	        } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等  
	
	
	            codedfilename = new String(fileName.getBytes("UTF-8"), "iso-8859-1");  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return codedfilename;  
	}
}
