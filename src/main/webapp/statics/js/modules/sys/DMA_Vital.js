var option1;
var vm = new Vue({
  el: '#app',
  data: {
	  treeList:[],
	  sendMessage:{"dareaId":"","stime":"","etime":"","type":"day"},
	  siteName:[],
	  dt:[],
	  siteValue:[],
	  siteThead:[ //标题栏
	  ],
	  series:[],
	  dd:true,
	  week:false,
	  mm:false,
	  yy:false,
	  dareaId:"",//片区编号
	  DT:[],//时间列表
	  dareaFlowOut:[],//出水流量
	  dareaFlowIn:[],//进水流量
	  lsv:[],//漏损率
	  dareaFlowOutAll:[],//总出水流量
	  dareaFlowInAll:[],//总进水流量
	  lsvAll:[]//总漏损率
  },
  mounted() {
	  this.setEchart();
  },
  watch: {
	  series(val) {
		  this.setEchart();
        }
  },
  methods:{//init() { console.log('test')},
	  getIndex:function(ID){
		  $("#"+ID).parent().toggleClass('active collapse');
		  vm.dareaId = ID;
		  queryAnalysisFlow();
	  },
	  setEchart:function(){
		  console.log("RESET");
		  console.log(this.series);
		  if(option1 != null && option1 != "" && option1 != undefined) {
			  option1.dispose();
			}
		  	var colors = ['#5793f3', '#d14a61', '#675bba'];
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
		    	    toolbox: {
		    	        feature: {
		    	            dataView: {show: true, readOnly: false},
		    	            magicType: {show: true, type: ['line', 'bar']},
		    	            restore: {show: true},
		    	            saveAsImage: {show: true}
		    	        }
		    	    },
		    	    legend: {
		    	        data:['进水量','出水量','漏损率']
		    	    },
		    	    xAxis: [
		    	        {
		    	            type: 'category',
		    	            data: this.DT,
		    	            axisPointer: {
		    	                type: 'shadow'
		    	            }
		    	        }
		    	    ],
		    	    yAxis: [
		    	        {
		    	            type: 'value',
		    	            name: '水量',
		    	            axisLabel: {
		    	                formatter: '{value} m³'
		    	            }
		    	        },
		    	        {
		    	            type: 'value',
		    	            name: '漏损率',
		    	            axisLabel: {
		    	                formatter: '{value}'
		    	            }
		    	        }
		    	    ],
		    	    series: [
		    	        {
		    	            name:'进水量',
		    	            type:'bar',
		    	            data:this.dareaFlowIn
		    	        },
		    	        {
		    	            name:'出水量',
		    	            type:'bar',
		    	            data:this.dareaFlowOut
		    	        },
		    	        {
		    	            name:'漏损率',
		    	            type:'line',
		    	            yAxisIndex: 1,
		    	            data:this.lsv
		    	        }
		    	    ]
		    	}
			option1.setOption(option3);
	  }
  }
})
function download2(){//统计流量查询
	window.location.href = "../../dma/data/download?dareaId="+vm.sendMessage.dareaId+"&stime="+vm.sendMessage.stime+"&etime="+vm.sendMessage.etime+"&type="+vm.sendMessage.type;	
}
var baseURL = "../../";
function queryAnalysisFlow(){//统计流量查询
	var time_alarm;
	if(vm.dd){
		time_alarm = $("#test5").val().split(" - ");
	}else if(vm.ww){
		time_alarm = $("#test6").val().split(" - ");		
	}else if(vm.mm){
		time_alarm = $("#test7").val().split(" - ");		
	}else if(vm.yy){
		time_alarm = $("#test8").val().split(" - ");
	}
	vm.sendMessage.stime=time_alarm[0];
	vm.sendMessage.etime=time_alarm[1];
	vm.sendMessage.dareaId=vm.dareaId;
	vm.sendMessage.type=$("input[name='precise']:checked").val();
	$.ajax({
		  type: "POST",
		  url: "../../dma/data/queryDataList",
		  contentType: "application/json",
		  data: JSON.stringify(vm.sendMessage),
		  success: function(r){
			  r=JSON.parse(r);
			  console.log(r);
			  console.log(r.data.length);
			  vm.dareaFlowOutAll = r.hjout;
			  vm.dareaFlowInAll = r.hjin;
			  vm.lsvAll = r.hjlsv;
			  vm.dareaFlowOut = [];
			  vm.dareaFlowIn = [];
			  vm.lsv = [];
			  vm.DT=[];
			  vm.siteValue = [];
			  for(var i=0;i<r.data.length;i++){
				  vm.dareaFlowOut.push(r.data[i].dareaFlowOut);
				  vm.dareaFlowIn.push(r.data[i].dareaFlowIn);
				  vm.lsv.push(r.data[i].lsv);
				  vm.DT.push(r.data[i].time);
				  console.log(r.data[i].time);
				  vm.siteValue.push({
					  dareaName:r.data[i].dareaName,
					  dareaFlowIn:r.data[i].dareaFlowIn,
					  dareaFlowOut:r.data[i].dareaFlowOut,
					  lsv:r.data[i].lsv2,
					  time:r.data[i].time,
				  })
			  }
			  vm.siteValue.push({
				  dareaName:"合计",
				  dareaFlowIn:r.hjin,
				  dareaFlowOut:r.hjout,
				  lsv:r.hjlsv,
				  time:"",
			  })
			  console.log(vm.siteValue)
			  vm.setEchart()
			  vm.$nextTick(function () {//使用这个方法，获取vue更新后的dom
			      layui.use(['layer', 'table'], function(){
			    	table = layui.table //表格       
			    	table.render({
			    	    elem: '#demo'
			    	    ,cols: [[ //标题栏
			    		      {field: 'dareaName', title: '分区', align:'center'}
			    		      ,{field: 'dareaFlowIn',title: '进水量(m³)',align:'center'}
			    		      ,{field: 'dareaFlowOut',title: '出水量(m³)',align:'center'}
			    		      ,{field: 'lsv',title: '漏损率',align:'center'}
			    		      ,{field: 'time',title: '时间',align:'center'}
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
var Stime_D;
var Etime_D;
var Stime_W;
var Etime_W;
var Stime_M;
var Etime_M;
var Stime_Y;
var Etime_Y;
//获取上周起始时间结束时间、下周起始时间结束时间开始时间和本周起始时间结束时间;（西方）
function getTime(n) {
  var now = new Date();
  var year = now.getFullYear();
  //因为月份是从0开始的,所以获取这个月的月份数要加1才行
  var month = now.getMonth() + 1;
  var date = now.getDate();
  var day = now.getDay();
  console.log(date);
  //判断是否为周日,如果不是的话,就让今天的day-1(例如星期二就是2-1)
  if (day !== 0) {
    n = n + (day - 1);
  } else {
    n = n + day;
  }
  if (day) {
    //这个判断是为了解决跨年的问题
    if (month > 1) {
      month = month;
    }
    //这个判断是为了解决跨年的问题,月份是从0开始的
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
/***参数都是以周一为基准的***/
//上周的开始时间
//console.log(getTime(7));
//上周的结束时间
//console.log(getTime(1));
//本周的开始时间
//console.log(getTime(0));
//本周的结束时间
//console.log(getTime(-6));
//下周的开始时间
//console.log(getTime(-7));
//下周结束时间
//console.log(getTime(-13));
function getLastMonth(){//获取上个月日期
	    var date = new Date; 
	    var year = date.getFullYear();
	    var month = date.getMonth();
	    if(month == 0){
	         year = year -1;
	         month = 12; 
	    }
	    return year+"-"+month;
}
$(function() {

//	从本地存储获取授权标识
	var perms=localStorage.getItem('MenuPerms');
	if(perms){
		if(perms.indexOf("mon:dma:statistics:download")==-1){
			$("#daochu").hide();
		}else{
			$("#daochu").show();
		}
		if(perms.indexOf("mon:dma:statistics:info")==-1){
			$(".chaxun").hide();
		}else{
			$(".chaxun").show();
		}		
	}
//授权标识
//	$('input:radio:first').attr('checked', 'checked');
	$("input[name='precise']").get(0).checked=true;
    $("#dd").show();
    $("#ww").hide();
    $("#mm").hide();
    $("#yy").hide();
    console.log(getTime(7));
    console.log(getTime(1));
    console.log(getLastMonth());
	var myDate = new Date();
	myDate.setTime(myDate.getTime()-24*60*60*1000);
	Stime_D=(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate());
	Etime_D=(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate());
	Stime_W=getTime(7);
	Etime_W=getTime(1);
	Stime_M=getLastMonth();
	Etime_M=getLastMonth();
	Stime_Y=(myDate.getFullYear()-1);
	Etime_Y=(myDate.getFullYear()-1);
	console.log(vm.siteThead);
	$.ajax({//获取树形结构
		  type: "POST",
		  url: "../../dma/area/queryAreaList2",
		  contentType: "application/json",
		  data: JSON.stringify(),
		  success: function(r){
			  r=JSON.parse(r);
    		  vm.treeList=r;
    		  console.log(vm.treeList);
    		  vm.dareaId = r[0].dareaId;
		      for(var key in r){
		    	  console.log("属性：" + key + ",值：" + r[key]);
		      }
		      vm.$nextTick(function () {//使用这个方法，获取vue更新后的dom
		          layui.use(['layer', 'form'], function(){
		            var form = layui.form;           
		            form.render();
		          });
		        })
//		        queryAnalysisFlow();
		  }
		});
})
