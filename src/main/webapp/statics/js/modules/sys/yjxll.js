var option1;
var vm = new Vue({
  el: '#app',
  data: {
	  treeList:[],
	  sendMessage:{"siteId":"","beginTime":"","endTime":""},
	  siteName:[],
	  siteValue:[],
	  series:[],
	  time:[],
	  time2:[],
	  value:[],
	  value2:[],
	  min:[],
	  avgNig:[],
	  avgDay:[],
	  pressure:[],
	  dareaId:"",
	  currentSiteName:"",
	  dt:"",
  },
  mounted() {
	  this.setEchart();
  },
  watch: {
	  time(val) {
		  this.setEchart();
        }
  },
  methods:{//init() { console.log('test')},
	  getIndex:function(ID,type,name){
		  $("#"+ID).parent().toggleClass('active collapse');
		  if(type){
			  console.log(this);
			  vm.dareaId = ID;
			  vm.currentSiteName=name;
			  $(".menu-item-2 a").css("color","#363636");
			  $(".menu-item-2 a").css("border-right","#3c8dbc 0px solid");
			  $(".menu-item-2 a").css("border-right","#3c8dbc 0px solid");
			  $("#"+ID).css("color","#3c8dbc");
			  $("#"+ID).css("border-right","#3c8dbc 2px solid");
			  $("#"+ID).css("text-decoration","none");
			  $("#"+ID).css("text-decoration","none");
			  queryAnalysisFlow();	
		  }
	  },
	  getIndex2:function(ID,type,name){
//		  $("#"+ID).parent().toggleClass('active collapse');
		  console.log(ID,type,name);
		  vm.dareaId = ID;
		  vm.currentSiteName=name;
		  if(type){
			  console.log(this);
			  $(".menu-item-1 a").css("color","#363636");
			  $(".menu-item-1 a").css("border-right","#3c8dbc 0px solid");
			  $(".menu-item-1 a").css("font-weight","normal");
			  $("#"+ID).css("color","#3c8dbc");
			  $("#"+ID).css("border-right","#3c8dbc 2px solid");
			  $("#"+ID).css("text-decoration","none");
			  $("#"+ID).css("font-weight","bold");
			  queryAnalysisFlowByArea();	
		  }
	  },
	  setEchart:function(){
		  console.log("RESET");
		  console.log(this.series);
		  if(option1 != null && option1 != "" && option1 != undefined) {
			  option1.dispose();
			}
		  	var colors = ['#5793f3', '#d14a61', '#675bba','#91c7ae', '#d48265', '#61a0a8',"#1997eb"];
		    option1 = echarts.init(document.getElementById('echart1'));
		    var option3 ={
		    	    tooltip: {
		    	        trigger: 'axis',
		    	        axisPointer: {
		    	            type: 'cross',
		    	            crossStyle: {
		    	                color: '#999'
		    	            }
		    	        }
		    	    },
//		    	    toolbox: {
//		    	        feature: {
//		    	            dataView: {show: true, readOnly: false},
//		    	            magicType: {show: true, type: ['line', 'bar']},
//		    	            restore: {show: true},
//		    	            saveAsImage: {show: true}
//		    	        }
//		    	    },
		    	    legend: {
		    	        data:['X???','Y???','????????????','???????????????',"??????????????????","??????"]
		    	    },
		    	    grid:[{x:50,y:30,x2:160,y2:30}],
		    	    xAxis: [
		    	        {
		    	            type: 'category',
		    	            data: this.time,
		    	            axisPointer: {
		    	                type: 'shadow'
		    	            }
		    	        }
		    	    ],
		    	    yAxis: [
		    	        {
		    	            type: 'value',
		    	            name: 'X???/Y???',
		    	            position: 'left',
		    	            axisLine: {
		    	                lineStyle: {
		    	                    color: colors[1]
		    	                }
		    	            },
		    	            min: 'dataMin', // ?????????
		    	            axisLabel: {
		    	                formatter: '{value}%'
		    	            }
		    	        },
		    	        {
		    	            type: 'value',
		    	            name: '??????',
		    	            position: 'right',
		    	            min: 'dataMin', // ?????????
		    	            axisLabel: {
		    	                formatter: '{value}m??/h'
		    	            }
		    	        },
		    	        {
		    	            type: 'value',
		    	            name: '??????',
		    	            position: 'right',
		    	            offset: 70,
		    	            min: 'dataMin', // ?????????
		    	            axisLine: {
		    	                lineStyle: {
		    	                    color: colors[5]
		    	                }
		    	            },
		    	            axisLabel: {
		    	                formatter: '{value}MPa'
		    	            }
		    	        }
		    	    ],
		    	    series: [
		    	        {
		    	            name:'X???',
		    	            type:'line',
		    	            yAxisIndex: 0,
		    	            data:this.value
		    	        },
		    	        {
		    	            name:'Y???',
		    	            type:'line',
		    	            yAxisIndex: 0,
		    	            data:this.value2
		    	        },
		    	        {
		    	            name:'????????????',
		    	            yAxisIndex: 1,
		    	            type:'line',
		    	            data:this.min
		    	        },
		    	        {
		    	            name:'??????????????????',
		    	            type:'line',
		    	            yAxisIndex: 1,
		    	            data:this.avgNig
		    	        },
		    	        {
		    	            name:'???????????????',
		    	            type:'line',
		    	            yAxisIndex: 1,
		    	            data:this.avgDay
		    	        },
		    	        {
		    	            name:'??????',
		    	            type:'line',
		    	            yAxisIndex: 2,
		    	            data:this.pressure
		    	        }
		    	    ]
		    	}
			option1.setOption(option3);
	  }
  }
})

//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????;????????????
function getTime(n) {
var now = new Date();
var year = now.getFullYear();
//??????????????????0?????????,???????????????????????????????????????1??????
var month = now.getMonth() + 1;
var date = now.getDate();
var day = now.getDay();
console.log(date);
//?????????????????????,??????????????????,???????????????day-1(?????????????????????2-1)
if (day !== 0) {
  n = n + (day - 1);
} else {
  n = n + day;
}
if (day) {
  //??????????????????????????????????????????
  if (month > 1) {
    month = month;
  }
  //??????????????????????????????????????????,????????????0?????????
  else {
    year = year - 1;
    month = 12;
  }
}
now.setDate(now.getDate() - n);
year = now.getFullYear();
month = now.getMonth() + 1;
date = now.getDate();
// console.log(n);
var s = year + "-" + (month < 10 ? ('0' + month) : month) + "-" + (date < 10 ? ('0' + date) : date);
return s;
}
var baseURL = "../../";
function queryAnalysisFlow(value){//??????????????????
	if(value){
		console.log("value");
		value = value;
	}else{
		console.log("test5");
		value = $("#test5").val();
	}
	var time_alarm =value.split(" - ");
	vm.sendMessage.beginTime=(time_alarm[0]+" 00:00:00");
	vm.sendMessage.endTime=(time_alarm[1]+" 00:00:00");
	vm.sendMessage.siteId=vm.dareaId;
//	vm.sendMessage.siteId="01-1-SG-XHX";
	$.ajax({
		  type: "POST",
		  url: "../../monitor/flowNight/query",
		  contentType: "application/json",
		  data: JSON.stringify(vm.sendMessage),
		  success: function(r){
			  r=JSON.parse(r);
			  console.log(r);
			  vm.siteValue=r.list;
			  for(var j=0;j<vm.siteValue.length;j++){
				  vm.siteValue[j].time = vm.siteValue[j].time.substring(0, vm.siteValue[j].time.length - 9);  
				  vm.siteValue[j].value = (vm.siteValue[j].value*100).toFixed(2);  
				  vm.siteValue[j].value2 = (vm.siteValue[j].value2*100).toFixed(2);  
				  vm.siteValue[j].rbl = (vm.siteValue[j].rbl*100).toFixed(2);  
				  vm.siteValue[j].ybl = (vm.siteValue[j].ybl*100).toFixed(2);  
			  }
			  console.log(vm.siteValue,r.list.length);
			  vm.value = [];//X???
			  vm.value2 = [];//Y???
			  vm.min = [];//????????????
			  vm.time2 = [];//????????????
			  vm.time=[];//????????????
			  vm.avgNig = [];//??????????????????
			  vm.avgDay = [];//???????????????
			  vm.pressure = [];//??????
			  for(var i=0;i<r.list.length;i++){
				  vm.value.push(r.list[i].value);
				  vm.value2.push(r.list[i].value2);
				  vm.min.push(r.list[i].min);
				  vm.time2.push(r.list[i].time2);
//				  vm.time.push(r.list[i].time.substring(0, r.list[i].time.length - 9));
				  vm.time.push(r.list[i].time);
				  vm.avgNig.push(r.list[i].avgNig);
				  vm.avgDay.push(r.list[i].avgDay);
				  vm.pressure.push(r.list[i].pressure);
			  }
			  vm.setEchart();
			  vm.$nextTick(function () {//???????????????????????????vue????????????dom
			      layui.use(['layer', 'table'], function(){
			    	table = layui.table //??????       
			    	table.render({
			    	    elem: '#demo'
			    	    ,cols: [[ //?????????
			    		      {field: 'time', title: '??????(0:00-4:00)', align:'center',width:"16%"}
			    		      ,{field: 'yysName', title: '???????????????', align:'center',width:"14%"}
			    		      ,{field: 'value',title: 'X???(%)',align:'center',width:"9%"}
			    		      ,{field: 'value2',title: 'Y???(%)',align:'center',width:"9%"}
			    		      ,{field: 'min',title: '????????????(m??/h)',align:'center',width:"16%"}
			    		      ,{field: 'time2',title: '????????????',align:'center',width:"19%"}
			    		      ,{field: 'avgNig',title: '??????????????????(m??/h)',align:'center',width:"18%"}
			    		      ,{field: 'nightsavg',title: '????????????????????????(m??/h)',align:'center',width:"18%"}
			    		      ,{field: 'ybl',title: '?????????(%)',align:'center',width:"18%"}
			    		      ,{field: 'avgDay',title: '???????????????(m??/h)',align:'center',width:"16%"}
			    		      ,{field: 'daysavg',title: '?????????????????????(m??/h)',align:'center',width:"18%"}
			    		      ,{field: 'rbl',title: '?????????(%)',align:'center',width:"18%"}
			    		      ,{field: 'pressure',title: '????????????(MPa)',align:'center',width:"14%"}
			    		    ]]
			    	    ,data: vm.siteValue
			    	    ,even: true
			    	    ,limit:1000
			    	  });
			      });
			    })
		  }
		});
}
function queryAnalysisFlowByArea(value){
	console.log(vm.currentSiteName);
//	  table.reload(tableIns,{
//		  areaId:vm.dareaId,
//		  areaName:vm.currentSiteName,
//		  time:$("#test1").val()
//	  });

	if(value){
		console.log("value");
		value = value;
	}else{
		console.log("test1");
		value = $("#test1").val();
	}
	layui.use('table', function(){
	  var table = layui.table;
      table.reload('FlowReportFormTable', {
          where: {
    		  areaId:vm.dareaId,
    		  areaName:vm.currentSiteName,
    		  time:value
    	  }
      });	  
	});
}
function exportAnalysisFlow(){
//	$.ajax({//??????
//		  type: "POST",
//		  url: "../../pipe/data/exportExcel",
//		  contentType: "application/json",
//		  data: JSON.stringify({
//			  areaId:vm.dareaId,
//			  areaName:vm.currentSiteName,
//			  time:$("#test1").val()
//		  }),
//		  success: function(r){
//			  r=JSON.parse(r);
//			  console.log(r);
//			  if(r.code==1){
//				  alert("???????????????????????????");
//			  }
//		  }
//		});	
	const obj = {
			  areaId:vm.dareaId,
			  areaName:vm.currentSiteName,
			  time:$("#test1").val()			
			}
	window.location.href = "../../pipe/data/exportExcel?areaId="+vm.dareaId+"&areaName="+vm.currentSiteName+"&time="+$("#test1").val()
}
var Stime_D;
var Etime_D;
var currentDate;
$(function() {
//	?????????????????????????????????
	var perms=localStorage.getItem('MenuPerms');
	if(perms){
		if(perms.indexOf("mon:flownight:analyze:download")==-1){
			$("#daochu").hide();
		}else{
			$("#daochu").show();
		}	
		if(perms.indexOf("mon:flownight:report:download")==-1){
			$("#daochu2").hide();
		}else{
			$("#daochu2").show();
		}	
	}
//????????????
	var myDate = new Date();
	myDate.setTime(myDate.getTime()-24*60*60*1000);
//	Stime_D=(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate());
	Etime_D=(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate());
	Stime_D=getTime(7);
//	Etime_D=getTime(1);
	setTreeList("1");
	currenDate=(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate());
	vm.dt = currenDate;
})
function setTreeList(obj){
	var URL="../../monitor/flowNight/"+((obj==="1")?"querySGSiteList":"querySGSiteListByDma");
	$.ajax({//??????????????????
		  type: "POST",
		  url: URL,
		  contentType: "application/json",
		  data: JSON.stringify(),
		  success: function(r){
			  r=JSON.parse(r);
			  console.log(r);
  		  if(obj==="1"){
  	  		  vm.treeList=r.list;
  	  	  }else{
  			r.list.sort(function (a, b) {
  				if (a.orderNum < b.orderNum) {
  					return -1;
  				} else if (a.orderNum == b.orderNum) {
  					return 0;
  				} else {
  					return 1;
  				}
  			});
    		vm.treeList=r.list;
  		  }
  		  console.log(vm.treeList);
//  		  vm.dareaId = r[0].dareaId;
//		      for(var key in r.list){
//		    	  console.log("?????????" + key + ",??????" + r.list[key]);
//		      }
		      vm.$nextTick(function () {//???????????????????????????vue????????????dom
		          layui.use(['layer', 'form'], function(){
		            var form = layui.form;           
		            form.render();
		          });
		        })
//		        queryAnalysisFlow();
		  }
		});
}
window.onload = function(){
}