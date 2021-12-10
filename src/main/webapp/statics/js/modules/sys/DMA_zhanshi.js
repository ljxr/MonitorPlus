var vm = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue.js!',
    dareaList:[],
    dsiteList:[],
    dareaLL:[],//面包屑
    currentDarea:{
    	siteId:"",
    	siteName:"",
    },
    msg:"",
    currentArea:[],
  },
  methods:{
	  send:function(siteId,siteName){
		  querySonList(siteId,siteName);
	  },
	  current:function(siteId,siteName){
		  vm.currentDarea={
		    	siteId:siteId,
		    	siteName:siteName,
		    }
		  queryDmaWaterHis(siteId,Stime,Etime);
	  },
	  jump:function(index,siteId,siteName){
		  console.log(siteId);
		  var dareaLL=[];
		  for(var i=0;i<index;i++){
				  dareaLL.push(vm.dareaLL[i]);
				  console.log(dareaLL);
		  }
		  vm.dareaLL=dareaLL;
		  console.log(vm.dareaLL);
		  querySonList(siteId,siteName,"jump");
	  }
  }
})
var map;
var baseURL = "../../";
var TP="S02";
var end;
var start;
var Stime;
var Etime;
$(function(){
	
	var name = 'global';
	var obj = {
	    name: 'local',
	    foo: function(){
	        this.name = 'foo';
	    }.bind(window)
	};
	var bar = new obj.foo();
    console.log(this.name);
	setTimeout(function() {
	    console.log(name);
	}, 0);
	console.log(bar.name);
	var bar3 = bar2 = bar;
	bar2.name = 'foo2';
	console.log(bar3.name);
	
	end = new Date();
	end.setTime(end.getTime() - 3600 * 1000 * 24 * 1);
	start = new Date();
	start.setTime(start.getTime() - 3600 * 1000 * 24 * 8);
	console.log(start.getMonth()+1+"",(start.getMonth()+1+"").length);
	Stime=(start.getFullYear()+"-"+(((start.getMonth()+1+"").length>1)?(start.getMonth()+1):("0"+(start.getMonth()+1)))+"-"+(((start.getDate()+"").length>1)?start.getDate():("0"+start.getDate())));
	Etime=(end.getFullYear()+"-"+(((end.getMonth()+1+"").length>1)?(end.getMonth()+1):("0"+(end.getMonth()+1)))+"-"+(((end.getDate()+"").length>1)?end.getDate():("0"+end.getDate())));
	console.log(start,end);
	map = new EJMap("mapcontent", {
	 	   layers:[{id:"管线",display:false,visible:false },{id:"点位",display:false,visible:false},{id:"区域",display:true,visible:true},{id:"定位",display:false,visible:false}]//图层以及事件的权限过滤,dispay:是否加载，visible：是否显示，只有已加载了的图层才能在页面中控制显示隐藏
	 });
	map.showScalebar();
	map.showNavigation();
	map.addEventListener(EJMap.Event.onMapLoaded, function(e) {
        map.zoomTo(0);
     });
});
window.onload = function(){
	map.setLayerDefinition({layerid:"点位"},"DIC_VALUE='S02'");
	map.setLayerVisibleById('区域',true);
	querySonList();
}
$('.mapTypeCard').on('click', function(e) {
	var name = $(this).find('span').html();
	console.log(name);
	switcherBaseLayer(name);
})

function switcherBaseLayer(layerName) {
	console.log(layerName);
	for (var i = 0; i < map.Config.baselayers.length; i++) {
		var layer = map.map.getLayer(map.Config.baselayers[i].id);
		if (layer) {
			if (layerName == layer.id) {
				console.log(layer);
				layer.setVisibility(true);
			} else {
				console.log(layer);
				layer.setVisibility(false);
			}
		}
	}
}
var option2;
function queryDmaWaterHis(dareaId,beginTime,endTime){
	$.ajax({
	    type: "GET",
	    url: baseURL + "/dma/area/queryDmaWaterHis?dareaId="+dareaId+"&beginTime="+beginTime+"&endTime="+endTime,
	    contentType: "application/json",
	    data:JSON.stringify(),
	    dataType:"json",
	    success: function(r){
	    	console.log(r);
	    	var areaData = r.areaData;
    		var time = []; //时间
    		var dareaFlowIn = []; //进水量
    		var dareaFlowOut = []; //出水量
    		var lsv = []; //漏损率
    		var lsl=[];//漏损量
    		vm.msg = r.calcMethod;
	    	for(var i=0;i<areaData.length;i++){
	    		time.push(areaData[i].time.split(" ")[0]);
	    		dareaFlowIn.push(areaData[i].dareaFlowIn.toFixed(3));
	    		dareaFlowOut.push(areaData[i].dareaFlowOut.toFixed(3));
	    		lsl.push(areaData[i].lsl.toFixed(3));
	    		lsv.push((areaData[i].lsv*100).toFixed(3));
	    	}
	    	var option1 = echarts.init(document.getElementById('echart1'));
	    	var option11 = {
	    		    //backgroundColor: '#0f375f',
	    		    tooltip: {
	    		        trigger: 'axis',
	    		        axisPointer: {
	    		            type: 'shadow',
	    		            label: {
	    		                show: true,
	    		                backgroundColor: '#333'
	    		            }
	    		        }
	    		    },
	    		    legend: {
	    		        data: ['进水量', '出水量', '漏损量','漏损率'],
	    		        textStyle: {
	    		            color: '#ccc'
	    		        }
	    		    },
	    		    grid: {
	    		        left: '20',
	    		        right: '20',
	    		        bottom: '9%',
	    		        top: '60',
	    		        containLabel: true
	    		    },
	    		    xAxis: {
	    		        data: time,
	    		        axisLine: {
	    		            lineStyle: {
	    		                color: '#363636'
	    		            }
	    		        }
	    		    },
	    		    yAxis: [{
	    		            splitLine: {
	    		                show: false
	    		            },
	    		            axisLine: {
	    		                lineStyle: {
	    		                    color: '#363636',
	    		                }
	    		            },
	    		            axisLabel: {
	    		                formatter: function(value) {
	    		                    return value + 'm³';
	    		                }
	    		            }
	    		        },
	    		        {
	    		            splitLine: {
	    		                show: false
	    		            },
	    		            axisLine: {
	    		                lineStyle: {
	    		                    color: '#363636',
	    		                }
	    		            },
	    		            axisLabel: {
	    		                formatter: function(value) {
	    		                    return value + '%';
	    		                }
	    		            }
	    		        }
	    		    ],
	    		    series: [{
	    		            name: '漏损率',
	    		            type: 'line',
	    		            smooth: true,
	    		            showAllSymbol: true,
	    		            symbol: 'emptyCircle',
	    		            symbolSize: 8,
	    		            yAxisIndex: 1,
	    		            itemStyle: {
	    		                normal: {
	    		                    color: 'rgba(156,107,211,1)'
	    		                },
	    		            },
	    		            data: lsv
	    		        },
	    		        {
	    		            name: '进水量',
	    		            type: 'bar',
	    		            itemStyle: {
	    		                normal: {
	    		                    color: '#33cabb'
	    		                }
	    		            },
	    		            data: dareaFlowIn,
	    		        },
	    		        {
	    		            name: '出水量',
	    		            type: 'bar',
	    		            itemStyle: {
	    		                normal: {
	    		                    color: '#3EACE5'
	    		                }
	    		            },
	    		            data: dareaFlowOut
	    		        },
	    		        {
	    		            name: '漏损量',
	    		            type: 'bar',
	    		            itemStyle: {
	    		                normal: {
	    		                	color: 'rgba(156,107,211,1)'
	    		                }
	    		            },
	    		            data: lsl
	    		        },
	    		    ]
	    		};
	    	option1.setOption(option11);
	    	var siteDataList = r.siteData.siteDataList;
	    	console.log(siteDataList);
	    	var siteName2 = siteDataList[0].siteName.split(",");
	    	var siteId2 = siteDataList[0].siteId.split(",");
	    	console.log(siteName2,siteId2);
	    	var time2=[];
	    	var dataList=[];
	    	for(var n=0;n<siteName2.length;n++){
    			dataList[siteName2[n]]=[];
    		}
	    	for(var j=0;j<siteDataList.length;j++){
	    		time2.push(siteDataList[j].time);
	    		for(var m=0;m<siteName2.length;m++){
	    			dataList[siteName2[m]].push(siteDataList[j].dayFlow.split(",")[m]);
	    		}
	    	}
	    	console.log(dataList);
	    	var seriesList=[];
	    	var siteColor=["rgba(156,107,211,1)",'#33cabb','#3EACE5','rgba(156,107,211,1)','rgba(0, 255, 233,1)','rgba(81,150,164,1)','rgba(43,193,145,1)',"#A582EA",'#A582EA',"#A582EA",'#00BFF3'];
	    	for(var n=0;n<siteName2.length;n++){
	    		var ll = 
		        {
    		            name:siteName2[n],
    		            type: 'line',
    		            lineStyle: {
    		                normal: {
    		                    color: siteColor[n],
    		                },
    		            },
    		            data: dataList[siteName2[n]]//data.values
    		        };
	    		seriesList.push(ll);
	    	}
	    	console.log(siteName2);
	    	console.log(seriesList);
	    	if (option2 != null && option2 != "" && option2 != undefined) {
	    		option2.dispose();//销毁
	    	}
	    	option2 = echarts.init(document.getElementById('echart2'));
	    	var option22 = {
	    		    tooltip: {
	    		        trigger: 'axis',
	    		        axisPointer: {
	    		            lineStyle: {
	    		                color: {
	    		                    type: 'linear',
	    		                    x: 0,
	    		                    y: 0,
	    		                    x2: 0,
	    		                    y2: 1,
	    		                    colorStops: [{
	    		                        offset: 0,
	    		                        color: 'rgba(0, 255, 233,0)'
	    		                    }, {
	    		                        offset: 0.5,
	    		                        color: 'rgba(255, 255, 255,1)',
	    		                    }, {
	    		                        offset: 1,
	    		                        color: 'rgba(0, 255, 233,0)'
	    		                    }],
	    		                    global: false
	    		                }
	    		            },
	    		        },
	    		    },
	    		    legend: {
	    		        data: siteName2,
	    		        textStyle: {
	    		            color: '#363636'
	    		        }
	    		    },
	    		    grid: {
	    		        top: '15%',
	    		        left: '10%',
	    		        right: '5%',
	    		        bottom: '15%',
	    		        // containLabel: true
	    		    },
	    		    xAxis: [{
	    		        type: 'category',
	    		        axisLine: {
	    		            show: false,
	    		            color:'#363636'
	    		        },
	    		    
	    		        axisLabel: {
	    		            color: '#363636',
	    		            width:100
	    		        },
	    		        splitLine: {
	    		            show: false
	    		        },
	    		        boundaryGap: false,
	    		        data: time2,

	    		    }],

	    		    yAxis: [{
	    		        type: 'value',
	    		        min: 0,
	    		        // max: 140,
	    		        splitNumber: 4,
	    		        splitLine: {
	    		            show: true,
	    		            lineStyle: {
	    		                color: '#363636',
	    		                opacity:0.2
	    		            }
	    		        },
	    		        axisLine: {
	    		            show: true,
	    		            color:'#363636'
	    		        },
	    		        axisLabel: {
	    		            show: true,
	    		            margin: 20,
	    		            textStyle: {
	    		                color: '#363636',

	    		            },
	    		        },
	    		        axisTick: {
	    		            show: false,
	    		        },
	    		    }],
	    		    series: seriesList
	    		};
	    	console.log(seriesList);
	    	option2.setOption(option22);
	    }
	})	
}
function querySonList(siteId,siteName,jump){
	$.ajax({
	    type: "GET",
	    url: baseURL + "/dma/area/querySonList?dareaParentId="+(siteId?siteId:""),
	    contentType: "application/json",
	    data:JSON.stringify(),
	    dataType:"json",
	    success: function(r){
	    	console.log(r);
	    	if(r.length){
	    		vm.dareaList=[];
	    		vm.dsiteList=[];
	    		if(siteId){
		  		  	var dareaLL={
			  				  siteId:siteId,
			  				  siteName:jump?siteName:("| "+siteName+"  "),
			  			  }
		  			  vm.dareaLL.push(dareaLL);
		  			  console.log(vm.dareaLL); 	    			
	    		}else{
		  		  	var dareaLL={
			  				  siteId:"",
			  				  siteName:"| 一级分区  ",
			  			  }
		  			  vm.dareaLL.push(dareaLL);
		  			  console.log(vm.dareaLL); 		    			
	    		} 	
	    		$.ajax({
	    		    type: "GET",
	    		    url: baseURL + "/dma/area/DmaFlowNightWarn?dareaId="+(siteId?siteId:""),
	    		    contentType: "application/json",
	    		    data:JSON.stringify(),
	    		    dataType:"json",
	    		    success: function(r){
	    		    	console.log(r);
	    		    	vm.dsiteList=r;
	    		    	console.log(vm.dsiteList)
	    		    }
	    		})
	    		map.addPolygon2GraphicsLayer({
	    			geom : {"rings":[[[119.5188903761719,30.702869691845706],[119.61914062031252,30.692226686474612],[119.60472106464846,30.62939862250977],[119.55116271503908,30.633175172802737],[119.5188903761719,30.702869691845706]]],"spatialReference":{"wkid":4326},"type":"polygon"},
	    			edit : false,
	    			symbol : {
	                          "type": "esriSFS",
	                          "style": "esriSFSSolid",
	                          "color": [0,0,0,0],
	                          "outline": {
	                          "type": "esriSLS",
	                          "style": "esriSLSSolid",
	                          "color": [0,0,0,0],
	                          "width": 0
	                           }
	                         },  
	    			center:true
	    		}, "分区", true);
	    	}else{
	    		alert("该片区不存在子点位");
	    		return;
	    	}
	    	var darea={
	    	}
    		map.clearLayerById("layer_");
	    	for(var j=0;j<vm.currentArea.length;j++){
	    		map.destroyInfoSymbol(vm.currentArea[j]);
	    	}
	    	vm.currentArea=[];
	    	for(var i=0;i<r.length;i++){
	    		var lsv = (r[i].lsv*100).toFixed(3);
	    		var intra="";
	    		var intra2=[119,136,153,128];
	    		if(lsv<15){//绿色
	    			intra = [50,205,50,128];
	    		}else if(lsv>30){//红色
	    			intra = [255,0,0,128];
	    		}else{//黄色
	    			intra=[255,215,0,128];
	    		}
	    		console.log(lsv,intra);
	    		darea={
			    		dareaName:r[i].dareaName,
			    		dareaId:r[i].dareaId,
			    		dareaFlowIn:r[i].dareaFlowIn.toFixed(2),
			    		dareaFlowOut:r[i].dareaFlowOut.toFixed(2),
			    		lsl:r[i].lsl.toFixed(2),
			    		lsv:(r[i].lsv*100).toFixed(2),
			    		x:r[i].x,
			    		y:r[i].y,
			    		border:r[i].border,
			    		borderwidth:r[i].borderwidth,
			    		innerX:r[i].innerX,
			    		innerY:r[i].innerY,
			    		intra:intra?intra:intra2,
			    		border:r[i].border,
			    		borderwidth:r[i].borderwidth,
			    		dareaParentId:r[i].dareaParentId,
			    		dareaParentName:r[i].dareaParentName,
			    		geomString:r[i].geomString
		    	};
	    		console.log(r[i].lsv.toFixed(2));
	    		vm.dareaList.push(darea);
	    		if(darea.geomString){
	    			vm.currentArea.push(darea.dareaId);
		    		addGisInfo(darea.dareaId,darea.dareaName,darea.x,darea.y,darea.lsv,darea.geomString,darea.intra,darea.border,darea.borderwidth);			
	    		}
	    	}
	    	console.log(vm.dareaList,"&&&&&&&&&&&&&&&&&&&")
	    }
	});

	
}
function addGisInfo(siteId,siteName,x,y,lsv,newgeom,areaRGBA,borderRGBA,borderwidth){
	newgeom = JSON.parse(newgeom);
	//areaRGBA = areaRGBA.split(",");
	borderRGBA = borderRGBA.split(",");
	console.log(newgeom,areaRGBA,borderRGBA,borderwidth);
	if (!!newgeom) {//!!把这个数据转化为布尔类型
		map.addPolygon2GraphicsLayer({
			geom : newgeom,
			edit : false,
			//ondbclick : querySonList(siteId,siteName,"jump"),
			symbol : {
                      "type": "esriSFS",
                      "style": "esriSFSSolid",
                      "color": areaRGBA,
                      "outline": {
                      "type": "esriSLS",
                      "style": "esriSLSSolid",
                      "color": borderRGBA,
                      "width": borderwidth
                       }
                     },  
			center:true
		}, "分区", false);
		console.log(newgeom.rings[0].length,newgeom.rings[0][0],Math.round(newgeom.rings[0].length/2),newgeom.rings[0][Math.round(newgeom.rings[0].length/2)]);
		if(x&&y){
			//用户设置的中心点坐标
			map.creatInfoSymbol(siteId,"<p class='TIP'>"+siteName+"</br>昨日漏损率："+lsv+"</p>",  {"x":x, "y": y, "spatialReference": {"wkid": 4326 }},0,0);
		}else{
			//第一个点和中间一个点的平均值
			//var X = (newgeom.rings[0][0][0]+newgeom.rings[0][Math.round(newgeom.rings[0].length/2)][0])/2;
			//var Y = (newgeom.rings[0][0][1]+newgeom.rings[0][Math.round(newgeom.rings[0].length/2)][1])/2;
			//map.creatInfoSymbol(siteName+"2","<p>"+siteName+"</p>",  {"x":X, "y": Y, "spatialReference": {"wkid": 4326 }},0,0);
			//所有顶点的平均值
			var XX=0;
			var YY=0;
			for(var i=0; i<newgeom.rings[0].length;i++){
				console.log(XX,YY,newgeom.rings[0][i][0],newgeom.rings[0][i][1]);
				XX += newgeom.rings[0][i][0];
				YY += newgeom.rings[0][i][1];
			}
			console.log(XX,YY);
			XX/=newgeom.rings[0].length;
			YY/=newgeom.rings[0].length;
			map.creatInfoSymbol(siteId,"<p class='TIP'>"+siteName+"</br>昨日漏损率："+lsv+"%</p>",  {"x":XX, "y": YY, "spatialReference": {"wkid": 4326 }},0,0);
		}
	}
}