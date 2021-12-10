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
		  historyData:[],
		  historyData_num:2,
		  hisDetail:[{siteId:"",tit:"",time:[],value:[],type:"",map:[]},{siteId:"",tit:"",time:[],value:[],type:"",map:[]}],
		  alarmData:[],
		  jcd_sh:false,
		  jcd_sh2:false,
		  map:[],
		  alarm:{
			"areaId":"",
			"levelName":"",
			"type":"",
			"stime":"",
			"etime":"",
			"siteType":"",
			"systemType":"gs"
		  },
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
		  },
		  getIndex2:function(Pid,id,name){
			  vm.ID = id;
			  vm.NAME = name;
			  findByRequest(id);
			  search_Alarm();
	  		  search_hisData();
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
					alert("能且只能选择两个监测点");
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
	    	if(vm.historyData_num==2){
	    		search_hisData();    		
	    	}
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
	  }
	})

function showArea(obj,type){
	if(type == "all"){
		$("#siteType span button").removeClass("add");
		$("#siteType span button").addClass("remove");
		$(obj).removeClass("remove");
		$(obj).addClass("add");
		document.getElementById("gscArea").style.display = "none";
		document.getElementById("gsbzArea").style.display = "none";
		document.getElementById("gsgwArea").style.display = "none";
		vm.alarm.areaId = "";
		vm.alarm.siteType = "";
		search_Alarm();
	} else if(type == "gsc"){
		console.log("gscgscgscgsc")
		$("#siteType span button").removeClass("add");
		$("#siteType span button").addClass("remove");
		$(obj).removeClass("remove");
		$(obj).addClass("add");
		document.getElementById("gscArea").style.display = "block";
		document.getElementById("gsbzArea").style.display = "none";
		document.getElementById("gsgwArea").style.display = "none";
		$("#gscArea span button").removeClass("add");
		$("#gscArea span button").addClass("remove");
		$("#gscArea span button:first").addClass("add");
		$("#gscArea span button:first").removeClass("remove");
		vm.alarm.areaId = "";
		vm.alarm.siteType = "gsc";
		search_Alarm();
	} else if(type == "gsbz"){
		$("#siteType span button").removeClass("add");
		$("#siteType span button").addClass("remove");
		$(obj).removeClass("remove");
		$(obj).addClass("add");
		document.getElementById("gscArea").style.display = "none";
		document.getElementById("gsbzArea").style.display = "block";
		document.getElementById("gsgwArea").style.display = "none";
		$("#gsbzArea span button").removeClass("add");
		$("#gsbzArea span button").addClass("remove");
		$("#gsbzArea span button:first").addClass("add");
		$("#gsbzArea span button:first").removeClass("remove");
		vm.alarm.areaId = "";
		vm.alarm.siteType = "gsbz";
		search_Alarm();
	} else if(type == "gsgw"){
		$("#siteType span button").removeClass("add");
		$("#siteType span button").addClass("remove");
		$(obj).removeClass("remove");
		$(obj).addClass("add");
		document.getElementById("gscArea").style.display = "none";
		document.getElementById("gsbzArea").style.display = "none";
		document.getElementById("gsgwArea").style.display = "block";
		$("#gsgwArea span button").removeClass("add");
		$("#gsgwArea span button").addClass("remove");
		$("#gsgwArea span button:first").addClass("add");
		$("#gsgwArea span button:first").removeClass("remove");
		vm.alarm.areaId = "";
		vm.alarm.siteType = "gsgw";
		search_Alarm();
	}
}

function findByArea(ID){
	vm.historyData=[];
	vm.alarmData = [];
	$.ajax({//获取点位信息
		  type: "POST",
		  url: "../../pipe/data/findByArea",
		  contentType: "application/json",
		  data: JSON.stringify(ID),
		  success: function(r){
			  r=JSON.parse(r);
			  console.log(r);
			  var HIS="";
			  for(var i=0; i<r.length; i++){
				ALARM = {"name":r[i].siteName,"siteId":r[i].siteId,"cls":"remove"}
			  	var typs=["PTOUT","FLC","FLS"];
				var types_name=["压力","累计流量","瞬时流量"];
				var types_unit=["MPa","m³","m³/h"];
				for(var p=0;p<typs.length;p++){
					HIS={"name":r[i].siteName+types_name[p],"siteId":r[i].siteId,"type":typs[p],"cls":"remove","unit":types_unit[p]};
					vm.historyData.push(HIS);
				}
				vm.alarmData.push(ALARM);
			  }
			  console.log(vm.alarmData,"1111111111");
	    setTimeout(function() {//不展开无效
	    	vm.historyData[0].cls="add";
	    	vm.historyData[1].cls="add";
        	$("#his0").removeClass("remove");
		    $("#his1").removeClass("remove");
		    $("#his0").addClass("add");
		    $("#his1").addClass("add");
		    
	    	vm.alarmData[0].cls="add";
	    	vm.alarmData[1].cls="add";
        	$("#alarm0").removeClass("remove");
		    $("#alarm1").removeClass("remove");
		    $("#alarm0").addClass("add");
		    $("#alarm1").addClass("add");
		    console.log(vm.historyData);
		    siteId.push(vm.historyData[0].siteId+","+vm.historyData[0].type);
		    siteId.push(vm.historyData[1].siteId+","+vm.historyData[1].type);
		    console.log(siteId);
		    console.log($("#test10").val());
		    search_hisData();
		    },100)
	}})
}


function search_Alarm(){
	var time_alarm = $("#alarmTime").val().split(" - ");
	vm.alarm.stime = time_alarm[0];
	vm.alarm.etime = time_alarm[1];
//	vm.alarm.siteId="";
//	for(var i=0; i<vm.alarmData.length; i++){
//		if(vm.alarmData[i].cls=="add"){
//			vm.alarm.siteId+=vm.alarmData[i].siteId;
//			vm.alarm.siteId+=",";	    			
//		}
//	}
	console.log(vm.alarm);
//	if(vm.alarm.siteId.split(",").length=="2"){
//		vm.alarm.siteId+="11";
//	}
	layui.use(['laypage','element','laydate','table'], function(){
		  var laypage = layui.laypage
		  ,element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
		  ,laydate = layui.laydate
		  ,layer = layui.layer
		  ,table = layui.table //表格
		  table.render({
			    elem: '#BaoJing_table'
			    ,url: "../../monitor/data/findAlarmData"
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
				      ,{field: 'higher', title: '故障上限', width:150}
				      ,{field: 'high', title: '报警上限', width:150}
				      ,{field: 'low', title: '报警下限', width:150}
				      ,{field: 'lower', title: '故障下限', width:150}
				    ]]
			   ,id: 'testReload'
			  });
		})
		}
function dw_click2(obj, areaId){
	$("#container_2_main .area button").addClass("remove");
	$("#container_2_main .area button").removeClass('add');
	$(obj).addClass('add');
	$(obj).removeClass("remove");
	vm.alarm.areaId = areaId;
	search_Alarm();
}
function dw_click3(obj){
	$("#container_2_main .lv button").addClass("remove");
	$("#container_2_main .lv button").removeClass('add');
	$(obj).addClass('add');
	$(obj).removeClass("remove");
	var level = $(obj).html();
	console.log(level);
	if(level=="全部"){
		vm.alarm.levelName="";
	}else if(level=="异常"){
		vm.alarm.levelName="异常";
	}else if(level=="故障"){
		vm.alarm.levelName="故障";
	}else if(level=="超时"){
		vm.alarm.levelName="超时";
	}
	search_Alarm();
}
function dw_click4(obj){
	$("#container_2_main .tp button").addClass("remove");
	$("#container_2_main .tp button").removeClass('add');
	$(obj).addClass('add');
	$(obj).removeClass("remove");
	var type=$(obj).html();
	console.log(type);
	if(type=="全部"){
		vm.alarm.type="";
	}else if(type=="流量"){
		vm.alarm.type="流量";
	}else if(type=="压力"){
		vm.alarm.type="压力";
	}else if(type=="COD"){
		vm.alarm.type="COD";
	}else if(type=="pH"){
		vm.alarm.type="PH";
	}else if(type=="余氯"){
		vm.alarm.type="余氯";
	}else if(type=="浊度"){
		vm.alarm.type="浊度";
	}else if(type=="SS"){
		vm.alarm.type="SS";
	}else if(type=="氨氮"){
		vm.alarm.type="氨氮";
	}else if(type=="总磷"){
		vm.alarm.type="总磷";
	}else if(type=="液位"){
		vm.alarm.type="液位";
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
	search_Alarm();
	var myDate = new Date();
	console.log(myDate.getMonth(),(myDate.getMonth()+1+"").length);
	Stime=(myDate.getFullYear()+"-"+(((myDate.getMonth()+1+"").length>1)?"":"0")+(myDate.getMonth()+1)+"-"+myDate.getDate()+" 00:00:00");
	console.log(myDate.getFullYear()+"-"+(((myDate.getMonth()+1+"").length>1)?"":"0")+(myDate.getMonth()+1),Stime);
	Etime=(myDate.getFullYear()+"-"+(((myDate.getMonth()+1+"").length>1)?"":"0")+(myDate.getMonth()+1)+"-"+myDate.getDate()+" 23:59:59");
	$("#echart0").width($("#container").width()*9/20);
	$("#echart1").width($("#container").width()*9/20);
	$("table tr:even").css("background-color","#f1f1f1");

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