/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.enjoyor.modules.monitor.controller;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enjoyor.common.http.HttpResult;
import com.enjoyor.modules.monitor.entity.MonitorTotalEntity;
import com.enjoyor.modules.monitor.entity.MonitorTotalQueryDTO;
import com.enjoyor.modules.monitor.entity.ReportWsEntity;
import com.enjoyor.modules.monitor.service.SewageService;
import com.enjoyor.modules.sys.controller.AbstractController;

@RestController
@RequestMapping("/sewage")
public class SewageController extends AbstractController {
	
	@Autowired
	private SewageService sewageService;

	@RequestMapping("/findHisData")
	@ResponseBody
	public List<MonitorTotalEntity> queryDispatch(@RequestBody Map<String, Object> params){
		MonitorTotalQueryDTO dto = new MonitorTotalQueryDTO();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String siteId1=(String)params.get("siteId1");
		if(Objects.equals(siteId1, null)){
			return null;
		}
		String site_Id1=siteId1.split(",")[0];
		String type1=siteId1.split(",")[1];
		
		String siteId2=(String)params.get("siteId2");
		if(Objects.equals(siteId2, null)){
			return null;
		}
		String site_Id2=siteId2.split(",")[0];
		String type2=siteId2.split(",")[1];
		
		String stime=(String)params.get("stime");
		String etime=(String)params.get("etime");
		if(null==stime||"".equals(stime)){
			stime=sdf2.format(Calendar.getInstance().getTime());
		}
		if(null==etime||"".equals(etime)){
			etime=sdf1.format(Calendar.getInstance().getTime());
		}
		
		dto.setSiteId1(site_Id1);
		dto.setSiteId2(site_Id2);
		dto.setType1(type1);
		dto.setType2(type2);
		dto.setBeginTime(stime);
		dto.setEndTime(etime);
		
		try {
			List<MonitorTotalEntity> list = sewageService.findBySiteIdTime(dto);
			 return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
    @RequestMapping("/list")
    public HttpResult list(@RequestBody Map<String, Object> params) {

        String siteId = (String) params.get("siteId");
        String year = (String) params.get("year");
        String beginTime = year + "-01-01";
        String endTime = year + "-12-01";
        HttpResult result = new HttpResult();
        try {
            List<ReportWsEntity> list = sewageService.select(siteId, beginTime, endTime);
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result = HttpResult.failed(e.getMessage());
        }
        return result;
    }

	
	
}

