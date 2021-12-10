var vm = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue.js!',
    gsc:[],
    gsc2:[{
    	name:"孝丰水厂",
    	jrgs:"123",
    	csll:"234",
    	cszd:"345",
    	csph:"456",
    	csyl:"567"
    },{
    	name:"高宇水厂",
    	jrgs:"123",
    	csll:"234",
    	cszd:"345",
    	csph:"456",
    	csyl:"567"
    },{
    	name:"彰吴水厂",
    	jrgs:"123",
    	csll:"234",
    	cszd:"345",
    	csph:"456",
    	csyl:"567"
    },{
    	name:"杭垓水厂",
    	jrgs:"123",
    	csll:"234",
    	cszd:"345",
    	csph:"456",
    	csyl:"567"
    },{
    	name:"梅溪水厂",
    	jrgs:"123",
    	csll:"234",
    	cszd:"345",
    	csph:"456",
    	csyl:"567"
    }],
    gsc3:[]
  },
  methods:{
	  detail:function(item){
		  websocket.close();
		  location.href="./gsc_detial.html?sc="+item.siteId;
	  }
  }
})
var map;
var baseURL = "../../";
var TP="S02";
$(function(){
	
	map = new EJMap("mapcontent", {
	 	   layers:[{id:"管线",display:true,visible:false },{id:"点位",display:true,visible:false},{id:"区域",display:false,visible:false},{id:"定位",display:false,visible:false}]//图层以及事件的权限过滤,dispay:是否加载，visible：是否显示，只有已加载了的图层才能在页面中控制显示隐藏
	 });
    $.ajax({
	    type: "GET",
	    url: "../../monitor/data/alarmWindow",
	    contentType: "application/json",
	    data: JSON.stringify(),
	    success: function(r){
	    	console.log("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%",map);
	        console.log(r);
	    }
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
var gsc={};
var inlineData="";
var gscNum=0;


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

window.onload = function(){
	map.setLayerDefinition({layerid:"点位"},"DIC_VALUE='S02'");
	map.setLayerVisibleById('点位',true);
	$.ajax({//查询水厂点位“S02”
	    type: "POST",
	    url: baseURL + "sys/site/queryfpSite",
	    contentType: "application/json",
	    data:JSON.stringify("S02"),//JSON解析不了单引号，只能解析双引号
	    dataType:"json",
	    success: function(r){
	    	gscNum = r.length;
	    	vm.gsc2=[];
	    	for(var i=0; i<r.length; i++){//for闭包
	    		gsc={
					areaId:r[i].areaId,
					areaName:r[i].areaName,
					name:r[i].name,
					siteId:r[i].siteId,
					x:r[i].x,
					y:r[i].y,
					childSite:[]
	    		};
	    		search_point(i,r[i].siteId,gsc);
	    		console.log(gsc);
	    		vm.gsc2.push(gsc);
	    		console.log(i);
	    		map.creatInfoSymbol(r[i].name,"<a class='sc_name' href='javascript:' onClick='pop("+i+")'>"+r[i].name+"</a>",  {"x":r[i].x, "y": r[i].y, "spatialReference": {"wkid": 4326 }},15,-5);
	    	}
	    }
	});
}

function search_point(i,obj,gsc){
	var childSite=[];
	console.log(i);
	console.log(obj);
	console.log(gsc);
	$.ajax({//请求子点位
	    type: "POST",
	    url: baseURL + "sys/site/queryParentSite",
	    contentType: "application/json",
	    data: JSON.stringify(obj),
	    success: function(result){
	    	var res = JSON.parse(result);
	    	console.log(res);
	    	for(var m=0; m<res.length; m++){
				console.log(res[m]);
				//只显示出水点
	    		if(res[m].type=="S02"){
	    			res[m].name=res[m].name.replace(gsc.name,"");
	    			var point={siteId:res[m].siteId,name:res[m].name,parentId:gsc.siteId,parentName:gsc.name,ins:"",jlj:"",cho:"",ph:"",tur:"",x:res[m].x};
	    			childSite.push(point);
	        		vm.gsc3.push(point);
	    		}
	    	}
			console.log(childSite);
			gsc.childSite=childSite;
    		vm.gsc[i]=gsc;
    		console.log(vm.gsc3.length);
    		inlineData="";
    		for(var n=0;n<vm.gsc3.length;n++){
    			inlineData+=vm.gsc3[n].siteId+","+"FLC;"+vm.gsc3[n].siteId+","+"FLS;"+vm.gsc3[n].siteId+","+"TUR;"+vm.gsc3[n].siteId+","+"PH;"+vm.gsc3[n].siteId+","+"HOCL;"
    		}
    		console.log(inlineData);
    		if(i==(gscNum-1)){
    			console.log(i);
    			send();
    		}
	    }
	});
}
function pop(obj){
	console.log(obj);
	console.log(vm.gsc[obj]);
	//map.creatInfoSymbol('pop1',"<div class='pop'><p>编号："+vm.gsc[obj].siteId+"<br>片区："+vm.gsc[obj].areaName+"<br>地址：安吉国源水务<br>联系人：张三<br>电话：123456</P><a href='./gsc_detial.html?sc="+vm.gsc[obj].siteId+"' onClick='javascript:websocket.close();' class='btn btn-primary btn-sm' role='button'>详情</a></div>",  {"x":vm.gsc[obj].x, "y": vm.gsc[obj].y, "spatialReference": {"wkid": 4326 }},0,0);
	map.creatInfoSymbol('pop1',"<div class='pop'><p>编号："+vm.gsc[obj].siteId+"<br>片区："+vm.gsc[obj].areaName+"<br>地址：安吉国源水务</P><a href='./gsc_detial.html?sc="+vm.gsc[obj].siteId+"' onClick='javascript:websocket.close();' class='btn btn-primary btn-sm' role='button'>详情</a></div>",  {"x":vm.gsc[obj].x, "y": vm.gsc[obj].y, "spatialReference": {"wkid": 4326 }},0,0);
}