var vm = new Vue({
  el: '#app',
  data: {
	current_gsc:"",
    message: 'Hello Vue.js!',
    gsc:[{},{},{},{},{},{},{},{}],
    jrgs:"3216m³",
    csll:"2341m³",
    cssz:"合格",
    inlineData:"",
    model:1,
    gyt_list:[],
    historyData:[],
    historyData_num:2,
    hisDetail:[{siteId:"",tit:"",time:[],value:[],type:"",map:[]},{siteId:"",tit:"",time:[],value:[],type:"",map:[]}],
    others_1_name:"1#泵&nbsp&nbsp<img src='../../statics/img/blue.gif'>",//拼接完成
    video_ld:[],
    map:[],
    jcd_sh:false,
	alarm:{
		"siteId":"",
		"level":"3",
		"type":"3",
		"stime":"",
		"etime":""
	},
  },
  mounted() {
	    //window.init = this.init;
	  	this.setEchart();
  },
  watch: {
	  map(val) {
		  this.setEchart();
        }
  },
  methods:{//init() { console.log('test')},
	  change_gsc:function(event){
		  console.log(event.target.attributes);
	  },
	  change_gyt:function(key,ID,siteParentId,siteId){
		  $("#"+key).addClass('btn-primary').siblings().removeClass('btn-primary');
		  $("#area").attr("src","sssj.html?ID="+ID+"&siteParentId="+siteParentId+"&siteId="+siteId);//工艺图实时数据
	  },
	  getIndex:function(index){//左侧导航栏点击
		  $.ajax({
			  type: "POST",
			  url: "../../fac/data/closeSession",
			  contentType: "application/json",
			  data: JSON.stringify(),
			  async:false,
			  success: function(r){
			      console.log("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			      console.log(r);
			  }
			});
          this.current_gsc = this.gsc[index].codeId;
          getVideoTree(this.current_gsc);
		  $("#btn0").addClass('btn-primary').siblings().removeClass('btn-primary');
		  //alert(this.model);
          if(this.model==1){
        	  var hrf="sssj.html?ID="+vm.current_gsc+"&siteParentId="+vm.current_gsc+"&siteId=''";
          	  $("#area").attr("src",hrf);
          	  $("#jksj").attr("src","");
          }else if(this.model==2){
        	  $("#area").attr("src","");
        	  $("#jksj").attr("src","jksjW.html?ID="+this.current_gsc);
        	  return 0;
          }else{
        	  $("#area").attr("src","");
          	  $("#jksj").attr("src","");
          }
      	search_History();
      	search_Alarm();
      },
      dw_click:function(obj,index){
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
    	setEchart:function(){
    		var option1 = echarts.init(document.getElementById('echart1'));
    		option1.setOption({
    			title : {
    		        text: vm.hisDetail[1].tit,
    		    },
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
    		        type: 'value'
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
    		var option0 = echarts.init(document.getElementById('echart0'));
    		option0.setOption({
    		    title : {
    		        text: vm.hisDetail[0].tit,
    		    },
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
    		        type: 'value'
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
    	}
  }
})
function jcd_sh(){
	vm.jcd_sh=!vm.jcd_sh;
	console.log(vm.historyData);
	if(vm.jcd_sh){
		$("#jcd_container").addClass("active");
	}else{
		$("#jcd_container").removeClass("active");
	}
}
function search_Alarm(){
	var time_alarm = $("#test11").val().split(" - ");
	vm.alarm.stime=time_alarm[0];
	vm.alarm.etime=time_alarm[1];
	vm.alarm.siteId="";
	$.ajax({
	    type: "POST",
	    url: baseURL+"sys/site/queryParentSite",
	    contentType: "application/json",
	    data: JSON.stringify(vm.current_gsc),
	    success: function(r){
	    	var r = JSON.parse(r);
	    	console.log(r);
	    	var HIS="";
	    	for(var i=0; i<r.length; i++){
	    		if(r[i].type=="W01"){//源水点、出水点
	    			vm.alarm.siteId+=r[i].siteId;
	    			vm.alarm.siteId+=",";
				}
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
	});
}
var siteId=[];
function search_hisData(){
	var time = $("#test10").val().split(" - ");
	siteId=[];
	vm.hisDetail=[];
	var his={};
	for(var i=0;i<vm.historyData.length;i++){
		if(vm.historyData[i].cls=="add"){
			siteId.push(vm.historyData[i].siteId+","+vm.historyData[i].type);
			his={
    				tit:vm.historyData[i].name+"("+vm.historyData[i].unit+")",
    				siteId:vm.historyData[i].siteId+vm.historyData[i].type,
    				time:[],
    				value:[],
    				type:"",
    				map:[]
    			}
			if(vm.historyData[i].type=="PTOUT"){
				his.type="PRESSURE_OUTVALUE";
			}else if(vm.historyData[i].type=="FLC"){//累计流量
				his.type="TOTALFLOW_VALUE";
			}else if(vm.historyData[i].type=="FLS"){//瞬时流量
				his.type="FLOW_VALUE";
			}else if(vm.historyData[i].type=="HOCL"){
				his.type="CHLORINE_VALUE";
			}else if(vm.historyData[i].type=="PH"){
				his.type="PH_VALUE";
			}else if(vm.historyData[i].type=="TUR"){
				his.type="TURBIDITY_VALUE";
			}else if(vm.historyData[i].type=="COD"){
				his.type="COD_VALUE";
			}else if(vm.historyData[i].type=="TN"){
				his.type="TN_ALARM";
			}else if(vm.historyData[i].type=="TP"){
				his.type="TP_ALARM";
			}else if(vm.historyData[i].type=="SS"){
				his.type="SS_ALARM";
			}else if(vm.historyData[i].type=="LEVEL"){
				his.type="LT_VALUE";
			}else if(vm.historyData[i].type=="ETA"){
				//his.type=""
			}else if(vm.historyData[i].type=="Ar"){
				his.type="PUMP_A";
			}else if(vm.historyData[i].type=="Fr"){
				his.type="PUMP_R";
			} //"PTOUT","FLC","HOCL","PH","TUR","COD","TN","TP","SS","LEVEL","ETA","Ar","Fr"
    		vm.hisDetail.push(his);
		}
	}
	var his_site={
			"siteId1":siteId[0],
			"siteId2":siteId[1],
			"stime":time[0],
			"etime":time[1]
	}
	$.ajax({
		  type: "POST",
		  url: "../../monitor/data/queryHisData",
		  contentType: "application/json",
		  data: JSON.stringify(his_site),
		  success: function(r){
			  r=JSON.parse(r);
		      console.log(r.length);
		      console.log(vm.hisDetail);
		      vm.map=[];
		      for(var m=0; m<vm.hisDetail.length;m++){
		    	  var n=vm.hisDetail[m].siteId;
		    	  //console.log(n);
		    	  //console.log(typeof(r[0]));
		    	  //console.log(r[0]);
		    	  for(var key in r[0]){
		    		//console.log("属性：" + key + ",值：" + r[0][key]);
		    		if(key==vm.hisDetail[m].siteId){
		    			for(var q=0; q<r[0][key].length; q++){
		    				//vm.hisDetail[m].value.push(r[0][key][q].CHLORINE_VALUE);
		    				vm.hisDetail[m].time.push(r[0][key][q].TIME);
		    				var type=vm.hisDetail[m].type;
		    				console.log(vm.hisDetail[m].type);
		    				vm.hisDetail[m].value.push(r[0][key][q][type]);
		    				vm.hisDetail[m].map.push({time:r[0][key][q].TIME,value:r[0][key][q][type]});
		    				vm.map.push({time:r[0][key][q].TIME,value:r[0][key][q][type]});
  		    		}
		    		}
		    	  }
		    	  console.log(vm.hisDetail);
		      }
		  }
		});
}

function search_hisData2(value){
	var time = value.split(" - ");
	siteId=[];
	vm.hisDetail=[];
	var his={};
	for(var i=0;i<vm.historyData.length;i++){
		if(vm.historyData[i].cls=="add"){
			siteId.push(vm.historyData[i].siteId+","+vm.historyData[i].type);
			his={
    				tit:vm.historyData[i].name+"("+vm.historyData[i].unit+")",
    				siteId:vm.historyData[i].siteId+vm.historyData[i].type,
    				time:[],
    				value:[],
    				type:"",
    				map:[]
    			}
			if(vm.historyData[i].type=="PTOUT"){
				his.type="PRESSURE_OUTVALUE";
			}else if(vm.historyData[i].type=="FLC"){//累计流量
				his.type="TOTALFLOW_VALUE";
			}else if(vm.historyData[i].type=="FLS"){//瞬时流量
				his.type="FLOW_VALUE";
			}else if(vm.historyData[i].type=="HOCL"){
				his.type="CHLORINE_VALUE";
			}else if(vm.historyData[i].type=="PH"){
				his.type="PH_VALUE";
			}else if(vm.historyData[i].type=="TUR"){
				his.type="TURBIDITY_VALUE";
			}else if(vm.historyData[i].type=="COD"){
				his.type="COD_VALUE";
			}else if(vm.historyData[i].type=="TN"){
				his.type="TN_ALARM";
			}else if(vm.historyData[i].type=="TP"){
				his.type="TP_ALARM";
			}else if(vm.historyData[i].type=="SS"){
				his.type="SS_ALARM";
			}else if(vm.historyData[i].type=="LEVEL"){
				his.type="LT_VALUE";
			}else if(vm.historyData[i].type=="ETA"){
				//his.type=""
			}else if(vm.historyData[i].type=="Ar"){
				his.type="PUMP_A";
			}else if(vm.historyData[i].type=="Fr"){
				his.type="PUMP_R";
			} //"PTOUT","FLC","HOCL","PH","TUR","COD","TN","TP","SS","LEVEL","ETA","Ar","Fr"
    		vm.hisDetail.push(his);
		}
	}
	var his_site={
			"siteId1":siteId[0],
			"siteId2":siteId[1],
			"stime":time[0],
			"etime":time[1]
	}
	$.ajax({
		  type: "POST",
		  url: "../../monitor/data/queryHisData",
		  contentType: "application/json",
		  data: JSON.stringify(his_site),
		  success: function(r){
			  r=JSON.parse(r);
		      console.log(r.length);
		      console.log(vm.hisDetail);
		      vm.map=[];
		      for(var m=0; m<vm.hisDetail.length;m++){
		    	  var n=vm.hisDetail[m].siteId;
		    	  //console.log(n);
		    	  //console.log(typeof(r[0]));
		    	  //console.log(r[0]);
		    	  for(var key in r[0]){
		    		//console.log("属性：" + key + ",值：" + r[0][key]);
		    		if(key==vm.hisDetail[m].siteId){
		    			for(var q=0; q<r[0][key].length; q++){
		    				//vm.hisDetail[m].value.push(r[0][key][q].CHLORINE_VALUE);
		    				vm.hisDetail[m].time.push(r[0][key][q].TIME);
		    				var type=vm.hisDetail[m].type;
		    				console.log(vm.hisDetail[m].type);
		    				vm.hisDetail[m].value.push(r[0][key][q][type]);
		    				vm.hisDetail[m].map.push({time:r[0][key][q].TIME,value:r[0][key][q][type]});
		    				vm.map.push({time:r[0][key][q].TIME,value:r[0][key][q][type]});
  		    		}
		    		}
		    	  }
		    	  console.log(vm.hisDetail);
		      }
		  }
		});
}


function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
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
function lk_show(n){//横向导航栏点击
	$.ajax({
		  type: "POST",
		  url: "../../fac/data/closeSession",
		  contentType: "application/json",
		  data: JSON.stringify(),
		  async:false,
		  success: function(r){
		      console.log(r)
		  }
		});
	vm.model=n;
    if(n==1){
    	var hrf="sssj.html?ID="+vm.current_gsc+"&siteParentId="+vm.current_gsc+"&siteId=''";
    	$("#area").attr("src",hrf);
    	$("#jksj").attr("src","");
    }else if(n==2){
    	$("#jksj").attr("src","jksjW.html?ID="+vm.current_gsc);
    	$("#area").attr("src","");
    }else{
    	$("#jksj").attr("src","");
    	$("#area").attr("src","");
    }
	$("#slider").css("left", (n-1)*120+"px");
}
function search_History(){
	$.ajax({
	    type: "POST",
	    url: baseURL+"sys/site/queryParentSite",
	    contentType: "application/json",
	    data: JSON.stringify(vm.current_gsc),
	    success: function(r){
	    	var r = JSON.parse(r);
	    	console.log(r);
	    	vm.historyData=[];
	    	var HIS="";
	    	for(var i=0; i<r.length; i++){
	    		if(r[i].type=="S02"){
	    			var typs1=["PTOUT","FLC","FLS","HOCL","PH","TUR"];
					var types_name1=["压力","累计流量","瞬时流量","余氯","pH","浊度"];
					var types_unit1=["MPa","m³","m³/h","mg/L","ph","mg/L"];
	    			for(var p=0;p<typs1.length;p++){
	    				HIS={name:r[i].name+types_name1[p],siteId:r[i].siteId,type:typs1[p],cls:"remove",unit:types_unit1[p]};
		    			vm.historyData.push(HIS);
		    			console.log(vm.historyData);
	    			}
				}else if(r[i].type=="W01"){
	    			var typs1=["PTOUT","FLC","FLS","HOCL","PH","TUR","TP","TN","NH4","COD","SS"];
					var types_name1=["压力","累计流量","瞬时流量","余氯","pH","浊度","总磷","总氮","氨氮","COD","SS"];
					var types_unit1=["MPa","m³","m³/h","mg/L","ph","mg/L","mg/L","mg/L","mg/L","mg/L","mg/L"];
	    			for(var p=0;p<typs1.length;p++){
	    				HIS={name:r[i].name+types_name1[p],siteId:r[i].siteId,type:typs1[p],cls:"remove",unit:types_unit1[p]};
		    			vm.historyData.push(HIS);
		    			console.log(vm.historyData);
	    			}
				}else if(r[i].type=="S14"){//泵
	    			var typs2=["Ar","Fr"];
	    			var types_name2=["水泵运行电流","水泵运行频率"];
	    			var types_unit2=["A","Hz"];
	    			for(var p=0;p<typs2.length;p++){
	    				HIS={name:r[i].name+types_name2[p],siteId:r[i].siteId,type:typs2[p],cls:"remove",unit:types_unit2[p]};
		    			vm.historyData.push(HIS);
	    			}
				}else if(r[i].type=="S15"){//储水池
	    			var typs3=["LEVEL"];
	    			var types_name3=["液位"];
	    			var types_unit3=["m"];
	    			for(var p=0;p<typs3.length;p++){
	    				HIS={name:r[i].name+types_name3[p],siteId:r[i].siteId,type:typs3[p],cls:"remove",unit:types_unit3[p]};
		    			vm.historyData.push(HIS);
	    			}
				}
	    	}
	    	setTimeout(function() {//不展开无效
		    	vm.historyData[0].cls="add";
		    	vm.historyData[1].cls="add";
	        	$("#his0").removeClass("remove");
			    $("#his1").removeClass("remove");
			    $("#his0").addClass("add");
			    $("#his1").addClass("add");
			    console.log(vm.historyData);
			    siteId.push(vm.historyData[0].siteId+","+vm.historyData[0].type);
			    siteId.push(vm.historyData[1].siteId+","+vm.historyData[1].type);
			    console.log(siteId);
			    search_hisData();
			    },100)
	    }
	});
}
var baseURL = "../../";
var Stime;
var Etime;
$(function(){
	var myDate = new Date();
	Stime=(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()+" 00:00:00");
	Etime=(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()+" 23:59:59");
	var Request = new Object();
	Request = GetRequest();
	var articleNum;
	scNum = Request["sc"];
	vm.current_gsc=scNum;
    console.log(scNum);
    search_History();
    search_Alarm();
    $("#his0").removeClass("remove");
    $("#his1").removeClass("remove");
    $("#his0").addClass("add");
    $("#his1").addClass("add");
    /*$(".hisBtn").eq(0).removeClass("remove");
    $(".hisBtn").eq(0).addClass("add");
    $(".hisBtn").eq(1).removeClass("remove");
    $(".hisBtn").eq(1).addClass("add");*/
	var s = document.body.scrollHeight;
	$("#container").height(s-85);
	$(".container_main").height(s-100);
	$(".container_inner").height(s-208);
	$(".movie").height(s-100);
	$(".movie div div").height(s-100);
	$("#echart0").width($("#container").width()*9/20);
	$("#echart1").width($("#container").width()*9/20);
	$("table tr:even").css("background-color","#f1f1f1");
	$(".lk_li").click(function() {//横向导航栏
        $(this).addClass('active').siblings().removeClass('active');
        var i = $(this).index()+1;
        $("#container_"+i).addClass('active').siblings().removeClass('active');
    });
	$(".gsc").click(function() {//左侧导航栏
		$(this).addClass('active').siblings().removeClass('active');
		var i = $(this).index()+1;//用来改变vm的值；
	});
	
	$('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
    $('.tree li.parent_li > span').on('click', function (e) {
        var children = $(this).parent('li.parent_li').find(' > ul > li');
        if (children.is(":visible")) {
            children.hide('fast');
            $(this).attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
        } else {
            children.show('fast');
            $(this).attr('title', 'Collapse this branch').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
        }
        e.stopPropagation();
    });
	getVideoTree(vm.current_gsc);
	$.ajax({
	    type: "POST",
	    url: baseURL + "sys/site/querySewageByArea",
	    contentType: "application/json",
	    data:JSON.stringify(),
	    dataType:"json",
	    success: function(r){
	    	vm.gsc=[];
	    	console.log(r);
	     	for(var i=0; i<r.length; i++){
    			console.log(r[i].siteId+r[i].name);
    			var gsc={
    		    	name:r[i].name,
    		    	codeId:r[i].siteId
    		    };
    			vm.gsc.push(gsc);
    		}
	     	console.log(vm.gsc);
	    	for(var i=0; i<vm.gsc.length; i++){
	    		if(vm.gsc[i].codeId == vm.current_gsc){
	    			i++;
	    			$("#gsc_name li:nth-child("+i+")").addClass('active').siblings().removeClass('active');
	    		}
	    	}
	    }
	});
});

$("#test10").bind("input propertychange",function(event){
       console.log($("#test10").val())
});
function getVideoTree(obj){
	vm.video_ld=[];
	$.ajax({//获取video索引
	    type: "POST",
	    url: baseURL + "fac/data/queryVideoInfo",
	    contentType: "application/json",
	    data:JSON.stringify(obj),
//	    data:JSON.stringify("05-1-SB-SS"),
	    dataType:"json",
	    success: function(r){
	    	if(r.length){
	    		for(var i=0; i<r.length; i++){
		    		var video_ld={
		    			name:r[i].name,
		    			channel:r[i].url,
		    			siteId:r[i].siteId
		    		}
			    	vm.video_ld.push(video_ld);
		    	    var src="http://192.168.101.9:10800/play.html?channel="+vm.video_ld[0].channel+"&iframe=yes";
		    		$("#supplyvideo").attr("src",src);
		    	}
	    	}else{
	    		var video_ld={
	    			name:"暂无数据",
	    			channel:"",
	    			siteId:""
	    		}
		    	vm.video_ld.push(video_ld);
	    	    var src="../../statics/img/noVideo.png";
	    		$("#supplyvideo").attr("src",src);
	    	}
	    }
	});
	var siteParentId={"siteParentId":vm.current_gsc};
	$.ajax({//查询水厂下关联工艺图
	    type: "POST",
	    url: baseURL + "fac/data/queryGytgl",
	    contentType: "application/json",
	    data:JSON.stringify(siteParentId),
	    dataType:"json",
	    success: function(r){
	    	console.log(r);
	    	vm.gyt_list=[{"siteId":"","siteParentId":vm.current_gsc,"name":"本水厂"}];
	    	for(var i=0;i<r.length;i++){
	    		var gyt_list={"siteId":r[i].siteId,"siteParentId":r[i].siteParentId,"name":r[i].gytName};
	    		vm.gyt_list.push(gyt_list);
	    	}
	    	console.log(vm.gyt_list);
	    	vm.change_gyt('btn0',(vm.gyt_list[0].siteId)?vm.gyt_list[0].siteId:vm.gyt_list[0].siteParentId,vm.gyt_list[0].siteParentId,vm.gyt_list[0].siteId),1000;
	    }
	});
	$.ajax({//查询水厂下子点位
	    type: "POST",
	    url: baseURL + "sys/site/queryParentSite",
	    contentType: "application/json",
	    data:JSON.stringify(vm.current_gsc),
	    dataType:"json",
	    success: function(r){
	    	vm.inlineData="";
	    	for(var i=0; i<r.length; i++){//源水点、出水点
	    		if(r[i].type=="W01"){
	    			vm.inlineData+=(r[i].siteId+",PTOUT;"+r[i].siteId+",FLC;"+r[i].siteId+",HOCL;"+r[i].siteId+",PH;"+r[i].siteId+",TUR;"+r[i].siteId+",COD;"+r[i].siteId+",TN;"+r[i].siteId+",TP;"+",TN;"+r[i].siteId+",SS;")
	    		}else if(r[i].type=="S14"){//泵
	    			vm.inlineData+=(r[i].siteId+",ETA;"+r[i].siteId+",Ar;"+r[i].siteId+",Fr;")
	    		}else if(r[i].type=="S15"){//储水池
	    			vm.inlineData+=(r[i].siteId+",LEVEL;")
	    		}
	    	}
	    }
	});
}
function change_gsc(obj){
	console.log("11111111"+obj);
}
function getVideoSrc(obj){
	var url=obj.channel;
	console.log(url);
	var src="http://192.168.101.9:10800/play.html?channel="+url+"&iframe=yes";
	$("#supplyvideo").attr("src",src);
}
window.onload = function(){
	

	
}