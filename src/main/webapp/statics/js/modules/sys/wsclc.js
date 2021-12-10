var vm = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue.js!',
    wsc:[],
    wsc2:[],
    wsc3:[]
  },
  methods:{
	  detail:function(item){
		  websocket.close();
		  location.href="./wsc_detial.html?sc="+item.siteId;
	  }
  }
})
var map;
var baseURL = "../../";
var TP="S02";
$(function(){
	map = new EJMap("mapcontent", {
	 	   layers:[{id:"管线",display:false,visible:false },{id:"点位",display:true,visible:false},{id:"区域",display:false,visible:false},{id:"定位",display:false,visible:false}]//图层以及事件的权限过滤,dispay:是否加载，visible：是否显示，只有已加载了的图层才能在页面中控制显示隐藏
	 });
	map.showScalebar();
	map.showNavigation();
	map.addEventListener(EJMap.Event.onMapLoaded, function(e) {
        map.zoomTo(0);
     });
	$("#map_tip").change(function() {
		if ($("#map_tip").prop('checked')){
			$(".pop").show();
		}else{
			$(".pop").hide();
		}
		});
});
var wsc={};
var inlineData="";


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

var wscNum=0;
window.onload = function(){
	map.setLayerDefinition({layerid:"点位"},"DIC_VALUE='W01'");
	map.setLayerVisibleById('点位',true);
	$.ajax({//查询水厂点位“S02”
	    type: "POST",
	    //url: baseURL + "sys/site/queryfpSite",
	    url: baseURL + "sys/site/querySewageByArea",
	    contentType: "application/json",
	    data:JSON.stringify("S02"),//JSON解析不了单引号，只能解析双引号
	    dataType:"json",
	    success: function(r){
	    	vm.wsc=[];
	    	console.log(r);
    		inlineData="";
    		wscNum = r.length;
	    	for(var i=0; i<r.length; i++){//for闭包
	    		wsc={
					areaId:r[i].areaId,
					areaName:r[i].areaName,
					name:r[i].name,
					siteId:r[i].siteId,
					x:r[i].x,
					y:r[i].y,
					childSite:[],
					/*jlj_in:"0.0m³",
					jlj_out:"0.0m³",
					ins_in:"0.0m³/h",
					ins_out:"0.0m³/h",
					ph_in:"0.0",
					ph_out:"0.0",
					tn_in:"0.0mg/L",
					tn_out:"0.0mg/L",
					tp_in:"0.0mg/L",
					tp_out:"0.0mg/L",
					cod_in:"0.0",
					cod_out:"0.0",
					ss_in:"0.0mg/L",
					ss_out:"0.0mg/L",
					yl_in:"0.0Mpa",
					yl_out:"0.0Mpa",*/
					
	    		};
	    		vm.wsc2.push(wsc);
	    		search_point(i,r[i].siteId,wsc);
	    		console.log(i);
	    		map.creatInfoSymbol(r[i].name,"<a class='sc_name' href='javascript:' onClick='pop("+i+")'>"+r[i].name+"</a>",  {"x":r[i].x, "y": r[i].y, "spatialReference": {"wkid": 4326 }},15,-5);
	    	}
	    }
	});
}

function search_point(i,obj,wsc){
	var childSite=[];
	console.log(i);
	console.log(obj);
	console.log(wsc);
	$.ajax({//请求子点位
	    type: "POST",
	    url: baseURL + "sys/site/querySewageImpExp",
	    contentType: "application/json",
	    data: JSON.stringify(obj),
	    success: function(result){
	    	var res = JSON.parse(result);
	    	for(var m=0; m<res.length; m++){
    			res[m].name=res[m].name.substring(res[m].name.indexOf('厂')+1);
    			var point={siteId:res[m].siteId,name:res[m].name,parentId:wsc.siteId,parentName:wsc.name,ins:"",jlj:"",cho:"",ph:"",tur:"",x:res[m].x};
    			childSite.push(point);
        		vm.wsc3.push(point);
	    	}
			console.log(childSite);
			wsc.childSite=childSite;
    		vm.wsc[i]=wsc;
    		console.log(vm.wsc3.length);
    		inlineData="";
    		for(var n=0;n<vm.wsc3.length;n++){
    			inlineData+=vm.wsc3[n].siteId+","+"FLC;"+vm.wsc3[n].siteId+","+"FLS;"+vm.wsc3[n].siteId+","+"PH;"+vm.wsc3[n].siteId+","+"TN;"+vm.wsc3[n].siteId+","+"NH4;"+vm.wsc3[n].siteId+","+"TP;"+vm.wsc3[n].siteId+","+"COD;"+vm.wsc3[n].siteId+","+"SS;"+vm.wsc3[n].siteId+","+"PTOUT;"
    		}
    		console.log(inlineData);
    		if(i==(wscNum-1)){
    			console.log(i);
    			send();
    		}
	    }
	});
}

function pop(obj){
	console.log(obj);
	console.log(vm.wsc[obj]);
	map.creatInfoSymbol('pop1',"<div class='pop'><p>编号："+vm.wsc[obj].siteId+"<br>片区："+vm.wsc[obj].areaName+"<br>地址：安吉国源水务</P><a href='./wsc_detial.html?sc="+vm.wsc[obj].siteId+"' onClick='javascript:websocket.close();' class='btn btn-primary btn-sm' role='button'>详情</a></div>",  {"x":vm.wsc[obj].x, "y": vm.wsc[obj].y, "spatialReference": {"wkid": 4326 }},0,0);
}