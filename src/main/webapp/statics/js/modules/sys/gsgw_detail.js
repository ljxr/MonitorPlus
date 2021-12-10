var option1;
var his_site={};
var siteId=[];
var vm = new Vue({
  el: '#app',
  data: {
	  treeList:[],
	  Site:[],
	  siteId:null,
	  ID:null,
	  NAME:"",
	  TYPE:"",
	  historyData:[],
	  historyData_num:2,
	  hisDetail:[{siteId:"",tit:"",time:[],value:[],type:"",map:[]},{siteId:"",tit:"",time:[],value:[],type:"",map:[]}],
	  alarmData:[],
	  jcd_sh:false,
	  jcd_sh2:false,
	  map:[],
	  alarm:{
		"siteId":"",
		"level":"3",
		"type":"3",
		"stime":"",
		"etime":""
	  },
	  tableTit0:"",
	  tableTit1:"",
  },
  mounted() {//页面加载完成后执行
	  	//this.setEchart();
  },
  watch: {
	  map(val) {
		  this.setEchart();
        }
  },
  methods:{//init() { console.log('test')},
	  getIndex:function(ID){//侧边栏点击
		  $(".menu-item-2 a").css("color","#363636");
		  $(".menu-item-2 a").css("border-right","#3c8dbc 0px solid");
		  vm.ID=ID;
		  $("#"+ID).css("color","#3c8dbc");
		  $("#"+ID).css("text-decoration","none");
		  $("#"+vm.ID).css("border-right","#3c8dbc 2px solid");
		  if($("#"+ID+"ul").is(":hidden")){
			  $("#"+ID+"ul").show();    //如果元素为隐藏,则将它显现
			  $("#"+ID+"span2").show();
			  $("#"+ID+"span1").hide();
			}else{
				$("#"+ID+"ul").hide();     //如果元素为显现,则将其隐藏
				  $("#"+ID+"span1").show();
				  $("#"+ID+"span2").hide();
			}
		  vm.ID=ID;
		  $(".menu-item-2 a").css("color","#363636");
		  $(".menu-item-2 a").css("border-right","#3c8dbc 0px solid");
		  $("#"+ID).css("color","#3c8dbc");
		  $("#"+vm.ID).css("border-right","#3c8dbc 2px solid");
		  $("#"+ID).css("text-decoration","none");
//		  findByArea(ID);
//		  search_Alarm();
//  		  search_hisData();
	  },
	  getIndex3:function(ID){//侧边栏点击
		  $(".menu-item-3 a").css("color","#363636");
		  $(".menu-item-3 a").css("border-right","#3c8dbc 0px solid");
		  vm.ID=ID;
		  $("#"+ID).css("color","#3c8dbc");
		  $("#"+ID).css("text-decoration","none");
		  $("#"+vm.ID).css("border-right","#3c8dbc 2px solid");
		  if($("#"+ID+"ul").is(":hidden")){
			  $("#"+ID+"ul").show();    //如果元素为隐藏,则将它显现
			  $("#"+ID+"span2").show();
			  $("#"+ID+"span1").hide();
			}else{
				$("#"+ID+"ul").hide();     //如果元素为显现,则将其隐藏
				  $("#"+ID+"span1").show();
				  $("#"+ID+"span2").hide();
			}
		  vm.ID=ID;
		  $(".menu-item-3 a").css("color","#363636");
		  $(".menu-item-3 a").css("border-right","#3c8dbc 0px solid");
		  $("#"+ID).css("color","#3c8dbc");
		  $("#"+vm.ID).css("border-right","#3c8dbc 2px solid");
		  $("#"+ID).css("text-decoration","none");
//		  findByArea(ID);
//		  search_Alarm();
//  		  search_hisData();
	  },
	  getIndex2:function(Pid,id,name,type){
//		  alert(type);
		  vm.ID = id;
		  vm.NAME = name;
		  vm.TYPE = type;
		  findByRequest(id);
		  search_Alarm();
//  		  search_hisData();
  		  $(".menu-item-2 a").css("color","#363636");
		  $(".menu-item-2 a").css("border-right","#3c8dbc 0px solid");
  		  $("#"+id).css("color","#3c8dbc");
		  $("#"+id).css("border-right","#3c8dbc 2px solid");
		  $("#"+id).css("text-decoration","none");
	  },
	  dw_click:function(obj,index){//历史监测点选择
  		if($("#"+obj).hasClass("add")){
			vm.historyData[index].cls="remove";
			$("#"+obj).removeClass("add");
			$("#"+obj).addClass("remove");
			vm.historyData_num-=1;
		}else if($("#"+obj).hasClass("remove")){
			if(vm.historyData_num==2){
				alert("最多只能选择两个监测点");
				return 0;
			}else{
    			vm.historyData[index].cls="add";
    			$("#"+obj).removeClass("remove");
    			$("#"+obj).addClass("add");
    			vm.historyData_num+=1;
			}
		}
    	console.log(vm.historyData_num);
    	console.log(vm.historyData);
//    	if(vm.historyData_num==2){
    		search_hisData();    		
//    	}
	},
	dw_click3:function(obj,index){//报警监测点选择
  		if($("#"+obj).hasClass("add")){//去掉
			vm.alarmData[index].cls="remove";
			$("#"+obj).removeClass("add");
			$("#"+obj).addClass("remove");
		}else if($("#"+obj).hasClass("remove")){//添加
			vm.alarmData[index].cls="add";
			$("#"+obj).removeClass("remove");
			$("#"+obj).addClass("add");
		}
	    search_Alarm();   
	},
	setEchart:function(){
		console.log(this.hisDetail);
		var option0 = echarts.init(document.getElementById('echart0'));
		option0.setOption({
		    title : {
		        text: vm.hisDetail[0].tit,
		    },
		    grid: {x: '80px'},
		    tooltip : {
		        trigger: 'axis',
		        axisPointer: {
		            type: 'cross',
		            label: {
		                backgroundColor: '#6a7985',
		            }
		        }
		    },
		    xAxis: {
		        type: 'category',
		        data: vm.hisDetail[0].time,
		    },
		    yAxis: {
		        type: 'value',
			    min: 'dataMin' // 最小值
		    },
		    dataZoom: [
		        {
		            type: 'slider',
		            xAxisIndex: 0,
		            filterMode: 'empty'
		        }
		    ],
		    series: [{
		        data: vm.hisDetail[0].value,
		        type: 'line'
		    }]
		});
		var option1 = echarts.init(document.getElementById('echart1'));
		option1.setOption({
			title : {
		        text: vm.hisDetail[1].tit,
		    },
		    grid: {x: '80px'},
		    tooltip : {
		        trigger: 'axis',
		        axisPointer: {
		            type: 'cross',
		            label: {
		                backgroundColor: '#6a7985',
		            }
		        }
		    },
		    xAxis: {
		        type: 'category',
		        data: vm.hisDetail[1].time
		    },
		    yAxis: {
		        type: 'value',
		        min: 'dataMin' // 最小值
		    },
		    dataZoom: [
		        {
		            type: 'slider',
		            xAxisIndex: 0,
		            filterMode: 'empty'
		        }
		    ],
		    series: [{
		        data: vm.hisDetail[1].value,
		        type: 'line'
		    }]
		});
	}
  }
})
//function findByArea(ID){
//	vm.historyData=[];
//	vm.alarmData = [];
//	$.ajax({//获取点位信息
//		  type: "POST",
//		  url: "../../pipe/data/findByArea",
//		  contentType: "application/json",
//		  data: JSON.stringify(ID),
//		  success: function(r){
//			  r=JSON.parse(r);
//			  console.log(r);
//			  var HIS="";
//			  for(var i=0; i<r.length; i++){
//				ALARM = {"name":r[i].siteName,"siteId":r[i].siteId,"cls":"remove"}
//			  	var typs=["PTOUT","FLC","FLS"];
//				var types_name=["压力","累计流量","瞬时流量"];
//				var types_unit=["MPa","m³","m³/h"];
//				for(var p=0;p<typs.length;p++){
//					HIS={"name":r[i].siteName+types_name[p],"siteId":r[i].siteId,"type":typs[p],"cls":"remove","unit":types_unit[p]};
//					vm.historyData.push(HIS);
//				}
//				vm.alarmData.push(ALARM);
//			  }
//			  console.log(vm.alarmData,"1111111111");
//	    setTimeout(function() {//不展开无效
//	    	vm.historyData[0].cls="add";
//	    	vm.historyData[1].cls="add";
//        	$("#his0").removeClass("remove");
//		    $("#his1").removeClass("remove");
//		    $("#his0").addClass("add");
//		    $("#his1").addClass("add");
//		    
//	    	vm.alarmData[0].cls="add";
//	    	vm.alarmData[1].cls="add";
//        	$("#alarm0").removeClass("remove");
//		    $("#alarm1").removeClass("remove");
//		    $("#alarm0").addClass("add");
//		    $("#alarm1").addClass("add");
//		    console.log(vm.historyData);
//		    siteId.push(vm.historyData[0].siteId+","+vm.historyData[0].type);
//		    siteId.push(vm.historyData[1].siteId+","+vm.historyData[1].type);
//		    console.log(siteId);
//		    console.log($("#test10").val());
//		    search_hisData();
//		    },100)
//	}})
//}
function findByRequest(){
	vm.historyData=[];
	vm.alarmData = [];
	var HIS="";
	ALARM = {"name":vm.NAME,"siteId":vm.ID,"type":vm.TYPE,"cls":"remove"}
//	console.log(ALARM);
  	var typs=[];
	var types_name=[];
	var types_unit=[];
	if(vm.TYPE=="S04"){//流量
//		alert("流量");
	  	typs=["PTOUT","FLC","FLS"];
		types_name=["压力","累计流量","瞬时流量"];
		types_unit=["MPa","m³","m³/h"];
	}else if(vm.TYPE=="S05"){//压力
//		alert("压力");
	  	typs=["PTOUT"];
		types_name=["压力"];
		types_unit=["MPa"];		
	}else if(vm.TYPE=="S06"){//水质
	  	typs=["HOCL","PH","TUR"];
		types_name=["余氯","Ph","浊度"];
		types_unit=["mg/L","","NTU"];		
	}else if(vm.TYPE=="S03"){//泵站
	  	typs=["PTOUT","FLC","FLS","HOCL","PH","TUR"];
		types_name=["压力","累计流量","瞬时流量","余氯","Ph","浊度"];
		types_unit=["MPa","m³","m³/h","mg/L","","NTU"];		
	}
	console.log(vm.NAME);
	for(var p=0;p<typs.length;p++){
		HIS={"name":vm.NAME+types_name[p],"siteId":vm.ID,"type":typs[p],"cls":"remove","unit":types_unit[p]};
		vm.historyData.push(HIS);
	}
	//压力点 只展示压力数据
//	if(vm.NAME.indexOf("压力") != -1){
//		HIS={"name":vm.NAME+"压力","siteId":vm.ID,"type":"PTOUT","cls":"remove","unit":"MPa"};
//		vm.historyData.push(HIS);
//		console.log("ddddddd");
//	//其他点位展示压力和流量
//	} else{
//		console.log("eeeeeee");
//		for(var p=0;p<typs.length;p++){
//			HIS={"name":vm.NAME+types_name[p],"siteId":vm.ID,"type":typs[p],"cls":"remove","unit":types_unit[p]};
//			vm.historyData.push(HIS);
//		}
//	}
	vm.alarmData.push(ALARM);
	siteId = [];
	setTimeout(function() {//不展开无效
		if(vm.historyData.length > 1){
			console.log(vm.historyData);
			console.log(vm.alarmData);
			vm.historyData[0].cls="add";
			vm.historyData[1].cls="add";
			$("#his0").removeClass("remove");
			$("#his1").removeClass("remove");
			$("#his0").addClass("add");
			$("#his1").addClass("add");
			
			vm.alarmData[0].cls="add";
//			vm.alarmData[1].cls="add";
			$("#alarm0").removeClass("remove");
//			$("#alarm1").removeClass("remove");
			$("#alarm0").addClass("add");
//			$("#alarm1").addClass("add");
			console.log(vm.historyData);
			siteId.push(vm.historyData[0].siteId+","+vm.historyData[0].type);
			siteId.push(vm.historyData[1].siteId+","+vm.historyData[1].type);
			console.log(siteId);
			console.log($("#test10").val());
			search_hisData();
		}else{
			console.log(vm.historyData);
			console.log(vm.alarmData);
			vm.historyData[0].cls="add";
			$("#his0").removeClass("remove");
			$("#his0").addClass("add");
			
			vm.alarmData[0].cls="add";
//			vm.alarmData[1].cls="add";
			$("#alarm0").removeClass("remove");
			$("#alarm0").addClass("add");
			console.log(vm.historyData);
			siteId.push(vm.historyData[0].siteId+","+vm.historyData[0].type);
			console.log(siteId);
			console.log($("#test10").val());
			search_hisData();
		}
	},100)
}
function search_hisData(){
	var time = $("#test10").val().split(" - ");
	Hisdetail(time);
}


function search_hisData2(value){
	var time = value.split(" - ");
	Hisdetail(time);
}
function Hisdetail(time){
	vm.hisDetail=[];
	var his={};
	for(var i=0;i<vm.historyData.length;i++){
		if(vm.historyData[i].cls=="add"){
			his={
					tit:vm.historyData[i].name+"("+vm.historyData[i].unit+")",
					siteId:vm.historyData[i].siteId+vm.historyData[i].type,
					ID:vm.historyData[i].siteId,
					TP:vm.historyData[i].type,
					time:[],
					value:[],
					type:"",
					map:[]
				}
			console.log(his);
			if(vm.historyData[i].type=="PTOUT"){
				his.type="yl";
			}else if(vm.historyData[i].type=="FLC"){//累计流量
				his.type="jlj";
			}else if(vm.historyData[i].type=="FLS"){//瞬时流量
				his.type="ss";
			}else if(vm.historyData[i].type=="HOCL"){
				his.type="chlorine";
			}else if(vm.historyData[i].type=="PH"){
				his.type="ph";
			}else if(vm.historyData[i].type=="TUR"){
				his.type="turbidity";
			}
			vm.hisDetail.push(his);
		}
	}
	siteId = [];
	console.log(vm.historyData);
//	siteId.push(vm.hisDetail[0].ID+","+vm.hisDetail[0].TP);
//	siteId.push(vm.hisDetail[1].ID+","+vm.hisDetail[1].TP);
	console.log(siteId);
	if(vm.historyData.length > 1){
		siteId.push(vm.hisDetail[0].ID+","+vm.hisDetail[0].TP);
		siteId.push(vm.hisDetail[1].ID+","+vm.hisDetail[1].TP);
		vm.tableTit0 = vm.hisDetail[0].tit;
		vm.tableTit1 = vm.hisDetail[1].tit;
		$("#list1").show();
		$("#echart1").show();
		his_site={
				"siteId1":siteId[0],
				"siteId2":siteId[1],
				"stime":time[0],
				"etime":time[1]
		}
		console.log(siteId);
		console.log(his_site);
		$.ajax({
			  type: "POST",
			  url: "../../pipe/data/findHisData",
			  contentType: "application/json",
			  async: false,
			  data: JSON.stringify(his_site),
			  success: function(r){
				  r=JSON.parse(r);
			      console.log(r.length);
			      console.log(vm.hisDetail);
			      vm.map=[];
			      for(var m=0; m<vm.hisDetail.length;m++){
			    	  var n=vm.hisDetail[m].siteId;
			    	  for(var key in r[0]){
			    		if(key==vm.hisDetail[m].siteId){
			    			console.log(key);
			    			for(var q=0; q<r[0][key].length; q++){
			    				vm.hisDetail[m].time.push(r[0][key][q].stringTime);
			    				var type=vm.hisDetail[m].type;
			    				vm.hisDetail[m].value.push(r[0][key][q][type]);
			    				vm.hisDetail[m].map.push({time:r[0][key][q].stringTime,value:r[0][key][q][type]});
			    				vm.map.push({time:r[0][key][q].stringTime,value:r[0][key][q][type]});
	  		    		}
			    		}
			    	  }
			    	  console.log(vm.hisDetail);
			      }
			  }
			});
		layui.use(['laypage','element','laydate','table'], function(){
			  var laypage = layui.laypage
			  ,element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
			  ,laydate = layui.laydate
			  ,layer = layui.layer
			  ,table = layui.table //表格
			  table.render({
				    elem: '#history1Table'
				    ,url: "../../pipe/data/pageHisData"
				    ,title: '组态列表'
				    ,datatype: "json"
				    ,page: true //开启分页
				    ,method:'post'		    
				    ,where:{
						  siteId:siteId[0],
						  stime:time[0],
						  etime:time[1]
				    }
				    ,cols: [[ //表头
					      {field: vm.hisDetail[0].type, title: '监测值', width:240}
					      ,{field: 'stringTime', title: '采集时间'}
					    ]]
				   ,id: 'testReload'
				  });
			})
			layui.use(['laypage','element','laydate','table'], function(){
				  var laypage = layui.laypage
				  ,element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
				  ,laydate = layui.laydate
				  ,layer = layui.layer
				  ,table = layui.table //表格
				  table.render({
					    elem: '#history2Table'
					    ,url: "../../pipe/data/pageHisData"
					    ,title: '组态列表'
					    ,datatype: "json"
					    ,page: true //开启分页
					    ,method:'post'		    
					    ,where:{
							  siteId:siteId[1],
							  stime:time[0],
							  etime:time[1]
					    }
					    ,cols: [[ //表头
						      {field: vm.hisDetail[1].type, title: '监测值', width:240}
						      ,{field: 'stringTime', title: '采集时间'}
						    ]]
					   ,id: 'testReload'
					  });
				})
		
	}else{
	siteId.push(vm.hisDetail[0].ID+","+vm.hisDetail[0].TP);
	vm.tableTit0 = vm.hisDetail[0].tit;
	$("#list1").hide();
	$("#echart1").hide();
		his_site={
				"siteId1":siteId[0],
	//			"siteId2":siteId[1],
				"stime":time[0],
				"etime":time[1]
		}
		console.log(siteId);
		console.log(his_site);
		$.ajax({
			  type: "POST",
			  url: "../../pipe/data/findHisData",
			  contentType: "application/json",
			  data: JSON.stringify(his_site),
			  success: function(r){
				  r=JSON.parse(r);
			      console.log(r.length);
			      console.log(vm.hisDetail);
			      vm.map=[];
			      for(var m=0; m<vm.hisDetail.length;m++){
			    	  var n=vm.hisDetail[m].siteId;
			    	  for(var key in r[0]){
			    		if(key==vm.hisDetail[m].siteId){
			    			console.log(key);
			    			for(var q=0; q<r[0][key].length; q++){
			    				vm.hisDetail[m].time.push(r[0][key][q].stringTime);
			    				var type=vm.hisDetail[m].type;
			    				vm.hisDetail[m].value.push(parseFloat(r[0][key][q][type]));
			    				vm.hisDetail[m].map.push({time:r[0][key][q].stringTime,value:parseFloat(r[0][key][q][type])});
			    				vm.map.push({time:r[0][key][q].stringTime,value:parseFloat(r[0][key][q][type])});
			    			}
			    		}
			    	  }
			    	  console.log(vm.hisDetail);
			      }
			  }
			});
		layui.use(['laypage','element','laydate','table'], function(){
			  var laypage = layui.laypage
			  ,element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
			  ,laydate = layui.laydate
			  ,layer = layui.layer
			  ,table = layui.table //表格
			  table.render({
				    elem: '#history1Table'
				    ,url: "../../pipe/data/pageHisData"
				    ,title: '组态列表'
				    ,datatype: "json"
				    ,page: true //开启分页
				    ,method:'post'		    
				    ,where:{
						  siteId:siteId[0],
						  stime:time[0],
						  etime:time[1]
				    }
				    ,cols: [[ //表头
					      {field: vm.hisDetail[0].type, title: '监测值', width:240}
					      ,{field: 'stringTime', title: '采集时间'}
					    ]]
				   ,id: 'testReload'
				  });
			})
		}	
}
function search_Alarm(){
	var time_alarm = $("#test11").val().split(" - ");
	vm.alarm.stime=time_alarm[0];
	vm.alarm.etime=time_alarm[1];
	vm.alarm.siteId="";
	for(var i=0; i<vm.alarmData.length; i++){
		if(vm.alarmData[i].cls=="add"){
			vm.alarm.siteId+=vm.alarmData[i].siteId;
			vm.alarm.siteId+=",";	    			
		}
	}
	console.log(vm.alarm);
	console.log(vm.alarm.siteId.split(",").length);
	if(vm.alarm.siteId.split(",").length=="2"){
		vm.alarm.siteId+="11";
	}
	layui.use(['laypage','element','laydate','table'], function(){
		  var laypage = layui.laypage
		  ,element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
		  ,laydate = layui.laydate
		  ,layer = layui.layer
		  ,table = layui.table //表格
		  table.render({
			    elem: '#BaoJing_table'
			    ,url: "../../monitor/data/queryAlarmData" //数据接口
			    ,title: '组态列表'
			    ,datatype: "json"
			    ,page: true //开启分页
			    ,method:'post'		    
			    ,where:vm.alarm
			    ,cols: [[ //表头
				      {field: 'time', title: '报警时间', sort: true, totalRow: true, width:200}
				      ,{field: 'siteName', title: '点位名称', width:150}
				      ,{field: 'param', title: '监测参数', width:150}
				      ,{field: 'value', title: '报警值', width:150} 
				      ,{field: 'type', title: '报警类型', width:150}
				      ,{field: 'higher', title: '故障上线', width:150}
				      ,{field: 'high', title: '报警上线', width:150}
				      ,{field: 'low', title: '报警下线', width:150}
				      ,{field: 'lower', title: '故障下线', width:150}
				    ]]
			   ,id: 'testReload'
			  });
		})
}
function dw_click1(obj){
	$("#container_2_main .lv button").addClass("remove");
	$("#container_2_main .lv button").removeClass('add');
	$(obj).addClass('add');
	$(obj).removeClass("remove");
	var level=$(obj).html();
	console.log(level);
	if(level=="全部"){
		vm.alarm.level="3";
	}else if(level=="异常"){
		vm.alarm.level="1";
	}else if(level=="故障"){
		vm.alarm.level="2";
	}else if(level=="超时"){
		vm.alarm.level="4";
	}
	search_Alarm();
}
function dw_click2(obj){
	$("#container_2_main .tp button").addClass("remove");
	$("#container_2_main .tp button").removeClass('add');
	$(obj).addClass('add');
	$(obj).removeClass("remove");
	var type=$(obj).html();
	console.log(type);
	if(type=="全部"){
		vm.alarm.type="3";
	}else if(type=="流量"){
		vm.alarm.type="FLS";
	}else if(type=="压力"){
		vm.alarm.type=" PTOUT";
	}else if(type=="PH"){
		vm.alarm.type="PH";
	}else if(type=="余氯"){
		vm.alarm.type="HOCL";
	}else if(type=="浊度"){
		vm.alarm.type="TUR";
	}else if(type=="SS"){
		vm.alarm.type="SS";
	}
	search_Alarm();
}

function jcd_sh(){
	vm.jcd_sh=!vm.jcd_sh;
	if(vm.jcd_sh){
		$("#jcd_container").addClass("active");
	}else{
		$("#jcd_container").removeClass("active");
	}
}
function jcd_sh2(){
	vm.jcd_sh2=!vm.jcd_sh2;
	if(vm.jcd_sh2){
		$("#jcd_container2").addClass("active");
	}else{
		$("#jcd_container2").removeClass("active");
	}
}
function GetRequest() {
	  var url = decodeURI(location.search);//获取url中"?"符后的字串//解决了中文乱码问题
	   var theRequest = new Object();
	   if (url.indexOf("?") != -1) {
	      var str = url.substr(1);
	      strs = str.split("&");
	      for(var i = 0; i < strs.length; i ++) {
	         theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
	      }
	   }
	   return theRequest;
	}
var baseURL = "../../";
var map;

var Stime;
var Etime;
$(function() {
	var s = document.body.scrollHeight;
	$(".main-container").height(s);
	$("#container_1").height(s);
	$("#container_2").height(s);
	var Request = new Object();
	Request = GetRequest();
	console.log(Request);
	vm.ID = Request["ID"];
	vm.NAME = Request["NAME"];
	vm.TYPE = Request["TYPE"];
//	findByArea(vm.ID);
	findByRequest();
	search_Alarm();
	var myDate = new Date();
	Stime=(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()+" 00:00:00");
	Etime=(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()+" 23:59:59");
	$("#echart0").width($("#container").width()*9/20);
	$("#echart1").width($("#container").width()*9/20);
	$("table tr:even").css("background-color","#f1f1f1");
	  $.ajax({//获取树形结构
		  type: "POST",
		  url: "../../sys/area/findAllArea",
		  contentType: "application/json",
		  data: JSON.stringify(),
		  success: function(r){
			  r=JSON.parse(r);
			  console.log(r);
			  for(var i=0;i<r.length;i++){
				  var result={
    				  name:r[i].areaName,
    				  siteId:r[i].areaId,
    				  treelist:[]
	    		  };
				  for(var m=0; m<r[i].sonList.length; m++){
					  (function(i){
						  var res={
			    				  name:r[i].sonList[m].areaName,
			    				  siteId:r[i].sonList[m].areaId,
			    				  treelist:[]
			    		  };
						  $.ajax({//获取点位信息
						  type: "POST",
						  url: "../../sys/site/queryByArea",
						  contentType: "application/json",
						  data: JSON.stringify(res.siteId),
						  success: function(resultt){
							  resultt=JSON.parse(resultt);
							  console.log(resultt);
							  var children1 = {
				    				  name:"泵站",
				    				  siteId:res.siteId+"01",
				    				  treelist:[]
								  }
							  var children2 = {
				    				  name:"流量计",
				    				  siteId:res.siteId+"02",
				    				  treelist:[]
								  }
							  var children3 = {
			    				  name:"压力表",
			    				  siteId:res.siteId+"03",
			    				  treelist:[]
							  }
							  var children4 = {
			    				  name:"水质",
			    				  siteId:res.siteId+"04",
			    				  treelist:[]
							  }
							  for(var i=0; i<resultt.length; i++){
								  if(resultt[i].type=="S03"){//泵站
									  var children = {
					    				  name:resultt[i].name,
					    				  siteId:resultt[i].siteId,
					    				  type:resultt[i].type,
									  }
									  children1.treelist.push(children);
								  }else if(resultt[i].type=="S04"){//流量计
									  var children = {
						    				  name:resultt[i].name,
						    				  siteId:resultt[i].siteId,
						    				  type:resultt[i].type,
										  }
										  children2.treelist.push(children);
									  }else if(resultt[i].type=="S05"){//压力表
									  var children = {
						    				  name:resultt[i].name,
						    				  siteId:resultt[i].siteId,
						    				  type:resultt[i].type,
										  }
									  children3.treelist.push(children);									  
								  }else if(resultt[i].type=="S06"){//水质监测点
									  var children = {
						    				  name:resultt[i].name,
						    				  siteId:resultt[i].siteId,
						    				  type:resultt[i].type,
										  }
									  children4.treelist.push(children);									  
								  }
							  }
							  res.treelist.push(children1);
							  res.treelist.push(children2);
							  res.treelist.push(children3);
							  res.treelist.push(children4);
							  }
					  		})
							result.treelist.push(res);
						})(i);
				  }
				  vm.treeList.push(result);
			  }
    		  console.log(vm.treeList);
    		  setTimeout(function() {
    			  $("#"+vm.ID).css("color","#3c8dbc");
    			  $("#"+vm.ID).css("border-right","#3c8dbc 2px solid");
    			  },100)
		  }
		});
	  $(".lk_li").click(function() {//横向导航栏
	        $(this).addClass('active').siblings().removeClass('active');
	        var i = $(this).index()+1;
	        $("#container_"+i).addClass('active').siblings().removeClass('active');
	    });
})
function lk_show(n){//横向导航栏点击
	$("#slider").css("left", (n-1)*120+"px");
}
$(document).ready(function(){ 
	//console.log($("#test10").val());
});