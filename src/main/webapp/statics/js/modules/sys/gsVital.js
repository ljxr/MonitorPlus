var option1;
var vm = new Vue({
  el: '#app',
  data: {
	  treeList:[],
	  treeList2:[],
	  treeList3:[],
	  sendMessage:{"siteId":"","stime":"","etime":"","type":""},
	  siteName:[],
	  dt:[],
	  siteValue:[],
	  siteThead:[ //标题栏
	  ],
	  series:[],
	  hh:true,
	  dd:false,
	  mm:false,
	  yy:false
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
	  },
	  searchSiteId:function(ID){
		  console.log($("#"+ID));
		  console.log($("#"+ID).parent());
		  console.log(this.treeList);
		  $("#"+ID).parent().toggleClass('active collapse');
			if($("#"+ID).parent().hasClass('active')){
				console.log("yes");
				$("#"+ID).children("input").prop('checked','true');
			}else{
				console.log("no");
				$("#"+ID).children("input").removeAttr('checked');
			}
			var obj = document.getElementsByName("site");
			    check_val = [];
			    vm.sendMessage.siteId = "";
			    vm.siteName=[];
				for(k in obj){
					if(obj[k].checked){
						check_val.push(obj[k].value);
						vm.siteName.push(obj[k].getAttribute('siteName'));
					}
				}
				console.log(check_val);
				console.log(vm.siteName);
				for(var i=0;i<check_val.length;i++){
					if(i!=0){
						vm.sendMessage.siteId+=",";
					}
					vm.sendMessage.siteId+=check_val[i];
				}
				console.log(vm.sendMessage.siteId);
				queryAnalysisFlow();
	  },
	  setEchart:function(){
		  console.log("RESET");
		  console.log(this.series);
		  
		  if(option1 != null && option1 != "" && option1 != undefined) {
			  option1.dispose();
			}
			
		    option1 = echarts.init(document.getElementById('echart1'));
			var option2={
			    title: {
			        text: '流量统计分析'
			    },
			    tooltip: {
			        trigger: 'axis',
		            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		            }
			    },
			    legend: {
			        data:this.siteName
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    toolbox: {
			        feature: {
			            saveAsImage: {}
			        }
			    },
			    dataZoom: [
			        {
			            show: true,
			            realtime: true,
			            start: 0,
			            end: 100
			        },
			        {
			            type: 'inside',
			            realtime: true,
			            start: 0,
			            end: 100
			        }
			    ],
			    xAxis: {
			        type: 'category',
			        boundaryGap: true,
			        data: this.dt,
		            axisTick: {
		                alignWithLabel: true
		            }
			    },
			    yAxis: {
			        type: 'value',
//				    min: 'dataMin' // 最小值
			    },
			    series: this.series
			};
			option1.setOption(option2);
	  }
  }
})

var baseURL = "../../";
function queryAnalysisFlow(){//统计流量查询
	var time_alarm;
	if(vm.hh){
		time_alarm = $("#test10").val().split(" - ");
	}else if(vm.dd){
		time_alarm = $("#test6").val().split(" - ");
	}else if(vm.mm){
		time_alarm = $("#test8").val().split(" - ");
	}else if(vm.yy){
		time_alarm = $("#test7").val().split(" - ");
	}
	vm.sendMessage.stime=time_alarm[0];
	vm.sendMessage.etime=time_alarm[1];
	vm.sendMessage.type=$("input[name='precise']:checked").val();
	$.ajax({
		  type: "POST",
		  url: "../../fac/data/queryAnalysisFlow",
		  contentType: "application/json",
		  data: JSON.stringify(vm.sendMessage),
		  success: function(r){
			  r=JSON.parse(r);
			  vm.dt=[];
			  vm.siteName=[];
			  vm.series=[];
			  vm.siteThead=[];
			  vm.siteValue=[];
			  var n;
			  for(var key in r){
				  console.log("属性：" + key + ",值：" + r[key]);
				  if(key=="siteName"){
					  vm.siteName=r[key].split(",");
					  console.log(vm.siteName);
					  for(n=0; n<vm.siteName.length; n++){
						  var series_obj=
					        {
					            name:vm.siteName[n],
					            type:'bar',
					            data:[]
					        }
						  vm.series.push(series_obj);
						  var nameList=["data1","data2","data3","data4","data5","data6","data7","data8","data9","data10","data11","data12","data13","data14","data15","data16","data17","data18","data19","data20"]
						  var siteThead_obj={field: nameList[n], title: vm.siteName[n]};
						  vm.siteThead.push(siteThead_obj);
						  console.log(vm.siteThead);
						  vm.$nextTick(function () {//使用这个方法，获取vue更新后的dom
						      layui.use(['layer', 'table'], function(){
						    	table = layui.table //表格       
						    	table.render({
						    	    elem: '#demo'
						    	    ,cols: [[ //标题栏
						    		      {field: 'time', title: '时间（h）', rowspan:2, sort: true}
						    		      ,{title: '流量（m³）', colspan:30, align:'center'}
						    		    ],vm.siteThead]
						    	    ,data: vm.siteValue
						    	    ,even: true
						    	    ,limit:1000
						    	  });
						      });
						    })
					  }
				  }
				  if(key=="result"){
					  console.log(r[key].length);
					  for(var i=0;i<r[key].length;i++){
						  console.log(vm.sendMessage.type);
						  if(vm.sendMessage.type=="hour"){
							  r[key][i].time = r[key][i].time.substring(0, r[key][i].time.length - 6); 	
							  r[key][i].time+="h";
						  }else if(vm.sendMessage.type=="day"){
							  r[key][i].time = r[key][i].time.substring(0, r[key][i].time.length - 9);						  
						  }else if(vm.sendMessage.type=="month"){
							  r[key][i].time = r[key][i].time.substring(0, r[key][i].time.length - 12);							  
						  }else if(vm.sendMessage.type=="year"){
							  r[key][i].time = r[key][i].time.substring(0, r[key][i].time.length - 15);								  
						  }
						  vm.dt.push(r[key][i].time);
						  var vl = r[key][i].value1;
						  var value = vl.split(",");
						  for(var m=0; m<value.length; m++){ 
							  vm.series[m].data.push(value[m]);
						  }
						  var siteValue_obj={
								  "time": r[key][i].time
								  ,"list":m
						          ,"data1": value[0]
						          ,"data2": value[1]
						          ,"data3": value[2]
						          ,"data4": value[3]
						          ,"data5": value[4]
								  ,"data6": value[5]
						          ,"data7": value[6]
						          ,"data8": value[7]
						          ,"data9": value[8]
						          ,"data10": value[9]
						          ,"data11": value[10]
						          ,"data12": value[11]
						          ,"data13": value[12]
						          ,"data14": value[13]
						          ,"data15": value[14]
						          ,"data16": value[15]
						          ,"data17": value[16]
						          ,"data18": value[17]
						          ,"data19": value[18]
						          ,"data20": value[19]
						        }
						  vm.siteValue.push(siteValue_obj);
					  }
					  console.log(vm.siteValue);
				  }
				  console.log(vm.series);
				  
			  }
		  }
		});
}
function downloadAnalysisFlow(){//统计流量查询
	window.location.href = "../../fac/data/downloadAnalysisFlowExcel?siteId="+vm.sendMessage.siteId+"&stime="+vm.sendMessage.stime+"&etime="+vm.sendMessage.etime+"&type="+vm.sendMessage.type;	
}
var Stime;
var Etime;
$(function() {
	var arr=[
		{far:123.45,address:"金粮路"},
		{far:3685.45,address:"北京路"},
		{far:2.8,address:"人民公园"},
		];
		 
		//根據距離遠近排序，越近在前面，升序
		arr = arr.sort(function (a, b) {
			if (a.far < b.far) {
				return -1;
			} else if (a.far == b.far) {
				return 0;
			} else {
				return 1;
			}
		});
		console.log(arr);
//	从本地存储获取授权标识
	var perms=localStorage.getItem('MenuPerms');
	if(perms){
		if(perms.indexOf("mon:analysis:download")==-1){
			$("#daochu").hide();
		}else{
			$("#daochu").show();
		}
		if(perms.indexOf("mon:analysis:list")==-1){
			$(".chaxun").hide();
		}else{
			$(".chaxun").show();
		}		
	}
//授权标识
    $("#hh").show();
    $("#mm").hide();
    $("#dd").hide();
    $("#yy").hide();
	var myDate = new Date();
	Stime=(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()+" 00:00:00");
	Etime=(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate()+" 23:59:59");
	console.log(vm.siteThead);
	$.ajax({//获取树形结构
		  type: "POST",
		  url: "../../fac/data/queryTreeSite",
		  contentType: "application/json",
		  data: JSON.stringify(),
		  success: function(r){
			  r=JSON.parse(r);
		      for(var key in r){
		    	  if(key=="供水厂"){
			    	  console.log("属性：" + key + ",值：" + r[key].length);
			    	  var result={
		    				  name:key,
		    				  siteId:key,
		    				  flag:false,
		    				  treelist:[]
		    		  };
			    	  r[key] = r[key].sort(function (a, b) {
			    			if (a.orderNum < b.orderNum) {
			    				return -1;
			    			} else if (a.orderNum == b.orderNum) {
			    				return 0;
			    			} else {
			    				return 1;
			    			}
			    	  })
			    	  for(var i=0;i<r[key].length;i++){
			    		  var res={
			    				  name:"",
			    				  siteId:"",
			    				  flag:true,
//			    				  treelist:[]
			    		  };
			    		  if(r[key][i].name!=null){
				    		  res.name=r[key][i].name;
				    		  res.siteId=r[key][i].siteId;
			    		  }else if(r[key][i].areaName!=null){
				    		  res.name=r[key][i].areaName;
				    		  res.siteId=r[key][i].areaId;
			    		  }else{
			    			  console.log(r[key][i].name);
			    			  console.log(r[key][i].areaName);
			    		  }
//			    		  res.flag=r[key][i].flag;
//			    		  if(r[key][i].treelist.length){
//				    		  for(var m=0;m<r[key][i].treelist.length;m++){
//				    			  var treelist={
//					    				  name:"",
//					    				  siteId:"",
//					    				  flag:"",
//					    				  treelist:[]
//				    			  };
//					    		  if(r[key][i].treelist[m].name!=null){
//					    			  treelist.name=r[key][i].treelist[m].name;
//					    			  treelist.siteId=r[key][i].treelist[m].siteId;
//					    		  }else if(r[key][i].treelist[m].areaName!=null){
//					    			  treelist.name=r[key][i].treelist[m].areaName;
//					    			  treelist.siteId=r[key][i].treelist[m].areaId;
//					    		  }else{
//					    			  console.log(r[key][i].treelist[m].name);
//					    			  console.log(r[key][i].treelist[m].areaName);
//					    		  }
//				    			  treelist.flag=r[key][i].treelist[m].flag;
//				    			  treelist.treelist=r[key][i].treelist[m].treelist;
//				    			  res.treelist.push(treelist);
//				    		  };		    			  
//			    		  }else{
//			    			  res.treelist=[];
//			    		  }
			    		  result.treelist.push(res);
			    	  }
		    		  vm.treeList.push(result);
		    		  console.log(vm.treeList);		    		  
		    	  }else if(key=="供水管网"){
			    	  console.log("属性：" + key + ",值：" + r[key].length);
			    	  r[key] = r[key].sort(function (a, b) {
			    			if (a.orderNum < b.orderNum) {
			    				return -1;
			    			} else if (a.orderNum == b.orderNum) {
			    				return 0;
			    			} else {
			    				return 1;
			    			}
			    	  })
			    	  var result={
		    				  name:key,
		    				  siteId:key,
		    				  flag:false,
		    				  treelist:[]
		    		  };
			    	  for(var i=0;i<r[key].length;i++){
			    		  var res={
			    				  name:"",
			    				  siteId:"",
			    				  flag:true,
			    				  treelist:[]
			    		  };
			    		  if(r[key][i].name!=null){
				    		  res.name=r[key][i].name;
				    		  res.siteId=r[key][i].siteId;
			    		  }else if(r[key][i].areaName!=null){
				    		  res.name=r[key][i].areaName;
				    		  res.siteId=r[key][i].areaId;
			    		  }else{
			    			  console.log(r[key][i].name);
			    			  console.log(r[key][i].areaName);
			    		  }
			    		  res.flag=r[key][i].flag;
			    		  if(r[key][i].treelist.length){
			    			  r[key][i].treelist = r[key][i].treelist.sort(function (a, b) {
					    			if (a.orderNum < b.orderNum) {
					    				return -1;
					    			} else if (a.orderNum == b.orderNum) {
					    				return 0;
					    			} else {
					    				return 1;
					    			}
			    			  })
			    			  for(var m=0;m<r[key][i].treelist.length;m++){
				    			  var treelist={
					    				  name:"",
					    				  siteId:"",
					    				  flag:"",
					    				  treelist:[]
				    			  };
					    		  if(r[key][i].treelist[m].name!=null){
					    			  treelist.name=r[key][i].treelist[m].name;
					    			  treelist.siteId=r[key][i].treelist[m].siteId;
					    		  }else if(r[key][i].treelist[m].areaName!=null){
					    			  treelist.name=r[key][i].treelist[m].areaName;
					    			  treelist.siteId=r[key][i].treelist[m].areaId;
					    		  }else{
					    			  console.log(r[key][i].treelist[m].name);
					    			  console.log(r[key][i].treelist[m].areaName);
					    		  }
				    			  treelist.flag=r[key][i].treelist[m].flag;
				    			  treelist.treelist=r[key][i].treelist[m].treelist;
				    			  res.treelist.push(treelist);
				    		  };		    			  
			    		  }else{
			    			  res.treelist=[];
			    		  }
			    		  result.treelist.push(res);
			    	  }
		    		  vm.treeList2.push(result);
		    		  console.log(vm.treeList2);		    	  
		    		  }else if(key=="泵站"){
				    	  console.log("属性：" + key + ",值：" + r[key].length);
				    	  r[key] = r[key].sort(function (a, b) {
				    			if (a.orderNum < b.orderNum) {
				    				return -1;
				    			} else if (a.orderNum == b.orderNum) {
				    				return 0;
				    			} else {
				    				return 1;
				    			}
				    	  })
				    	  var result={
			    				  name:key,
			    				  siteId:key,
			    				  flag:false,
			    				  treelist:[]
			    		  };
				    	  for(var i=0;i<r[key].length;i++){
				    		  var res={
				    				  name:"",
				    				  siteId:"",
				    				  flag:true,
				    				  treelist:[]
				    		  };
				    		  if(r[key][i].name!=null){
					    		  res.name=r[key][i].name;
					    		  res.siteId=r[key][i].siteId;
				    		  }else if(r[key][i].areaName!=null){
					    		  res.name=r[key][i].areaName;
					    		  res.siteId=r[key][i].areaId;
				    		  }else{
				    			  console.log(r[key][i].name);
				    			  console.log(r[key][i].areaName);
				    		  }
				    		  res.flag=r[key][i].flag;
				    		  if(r[key][i].treelist.length){
				    			  r[key][i].treelist = r[key][i].treelist.sort(function (a, b) {
						    			if (a.orderNum < b.orderNum) {
						    				return -1;
						    			} else if (a.orderNum == b.orderNum) {
						    				return 0;
						    			} else {
						    				return 1;
						    			}
				    			  })
				    			  for(var m=0;m<r[key][i].treelist.length;m++){
					    			  var treelist={
						    				  name:"",
						    				  siteId:"",
						    				  flag:"",
						    				  treelist:[]
					    			  };
						    		  if(r[key][i].treelist[m].name!=null){
						    			  treelist.name=r[key][i].treelist[m].name;
						    			  treelist.siteId=r[key][i].treelist[m].siteId;
						    		  }else if(r[key][i].treelist[m].areaName!=null){
						    			  treelist.name=r[key][i].treelist[m].areaName;
						    			  treelist.siteId=r[key][i].treelist[m].areaId;
						    		  }else{
						    			  console.log(r[key][i].treelist[m].name);
						    			  console.log(r[key][i].treelist[m].areaName);
						    		  }
					    			  treelist.flag=r[key][i].treelist[m].flag;
					    			  treelist.treelist=r[key][i].treelist[m].treelist;
					    			  res.treelist.push(treelist);
					    		  };		    			  
				    		  }else{
				    			  res.treelist=[];
				    		  }
				    		  result.treelist.push(res);
				    	  }
			    		  vm.treeList3.push(result);
			    		  console.log(vm.treeList3);		    	  
			    		  }
		      }
		      vm.$nextTick(function () {//使用这个方法，获取vue更新后的dom
		          layui.use(['layer', 'form'], function(){
		            var form = layui.form;           
		            form.render();
		          });
		        })
		  }
		});
})


window.onload = function(){
	

	
}