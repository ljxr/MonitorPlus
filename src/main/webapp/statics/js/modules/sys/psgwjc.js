var option1;
var vm = new Vue({
  el: '#app',
  data: {
	  treeList:[],
	  Site:[],
	  ID:null,
	  lastID:null,
	  LF_ins:"",
	  LF_yl:"",
	  LF_ll:"",
	  LF_hocl:"",
	  LF_tur:"",
	  LF_ph:""
  },
  mounted() {//页面加载完成后执行
  },
  watch: {
/*	  series(val) {//数据变化时，执行
		  this.setEchart();
        }*/
  },
  methods:{//init() { console.log('test')},
	  getIndex:function(ID){
		  $(".menu-item-2 a").css("color","#363636");
		  $(".menu-item-2 a").css("border-right","#3c8dbc 0px solid");
		  vm.ID=ID;
		  $("#"+ID).css("color","#3c8dbc");
		  $("#"+vm.ID).css("border-right","#3c8dbc 2px solid");
		  $("#"+ID).css("text-decoration","none");
		  if(ID=="03-1"){
			  map.zoomTo(2);
			  map. centerAt( {"x": "119.62842283078193", "y": "30.78442685227205", "spatialReference": {"wkid": 4326 }});
		  }else if(ID=="04-1"){
			  map.zoomTo(2);
			  map. centerAt( {"x": "119.77514135153729", "y": "30.78381355917622", "spatialReference": {"wkid": 4326 }});
		  }else if(ID=="05-1"){
			  map.zoomTo(2);
			  map. centerAt( {"x": "119.59490183198561", "y": "30.529590227478227", "spatialReference": {"wkid": 4326 }});
		  }else if(ID=="01-1"){
			  map.zoomTo(2);
			  map. centerAt( {"x": "119.53970575561522", "y": "30.578218359375", "spatialReference": {"wkid": 4326 }});
		  }else if(ID=="01-2"){
			  map.zoomTo(2);
			  map. centerAt( {"x": "119.41293382873533", "y": "30.57049359741211", "spatialReference": {"wkid": 4326 }});
		  }else if(ID=="02-2"){
			  map.zoomTo(2);
			  map. centerAt( {"x": "119.58717012634276", "y": "30.683961767578126", "spatialReference": {"wkid": 4326 }});
		  }else if(ID=="02-1"){
			  map.zoomTo(2);
			  map. centerAt( {"x": "119.65574884643553", "y": "30.56225385131836", "spatialReference": {"wkid": 4326 }});
		  }
		  $("."+vm.lastID).hide();
		  $.ajax({//获取点位信息
			  type: "POST",
			  url: "../../sys/site/queryPsgwByArea",
			  contentType: "application/json",
			  data: JSON.stringify(ID),
			  success: function(r){
				  r=JSON.parse(r);
				  vm.Site=[];
				  for(var i=0; i<r.length; i++){
					  var site={
						  areaId:r[i].areaId,
						  siteId:r[i].siteId,
						  name:r[i].name,
						  x:r[i].x,
						  y:r[i].y,
						  type:r[i].type,
						  ss:"",
						  lj:"",
						  yl:"",
						  hclo:"",
						  tur:"",
						  ph:"",
						  cod:""
					  }
					  vm.Site.push(site);
					  }
				  console.log(vm.Site);
				  vm.lastID=ID;
				  $.ajax({//获取列表数据
					  type: "POST",
					  url: "../../pipe/data/findPsByArea",
					  contentType: "application/json",
					  data: JSON.stringify(ID),
					  success: function(rr){
						  rr=JSON.parse(rr);
						  console.log(rr.length);
						  console.log(vm.Site);
						  for(var p=0; p<rr.length; p++){
							  for(var q=0; q<vm.Site.length; q++){
								  console.log(vm.Site[q]);
								  if(vm.Site[q].siteId==rr[p].siteId){
									  console.log(rr[p]);
									  if(rr[p].ss){
										  vm.Site[q].ss=rr[p].ss.toFixed(1)+"m³/h";										  
									  }else{
										  vm.Site[q].ss="0.0m³/h";
									  }
									  if(rr[p].jlj){
										  vm.Site[q].lj=rr[p].jlj.toFixed(1)+"m³";
									  }else{
										  vm.Site[q].lj="0.0m³";
									  }
									  if(rr[p].yl){
										  vm.Site[q].yl=Number(rr[p].yl).toFixed(2)+"Mpa";
									  }else{
										  vm.Site[q].yl="0.00Mpa";
									  }
									  if(rr[p].chlorine){
										  vm.Site[q].hclo=rr[p].chlorine.toFixed(2)+"mg/L";
									  }else{
										  vm.Site[q].hclo="0.00mg/L";
									  }
									  if(rr[p].turbidity){
										  vm.Site[q].tur=rr[p].turbidity.toFixed(2)+"TUR";
									  }else{
										  vm.Site[q].tur="0.00TUR";
									  }
									  if(rr[p].ph){
										  vm.Site[q].ph=rr[p].ph.toFixed(2);										  
									  }else{
										  vm.Site[q].ph="0.00";
									  }
									  if(rr[p].cod){
										  vm.Site[q].cod=rr[p].cod.toFixed(2);									  
									  }else{
										  vm.Site[q].cod="0.00";
									  }
									  map.creatInfoSymbol(vm.Site[q].siteId,"<div class='"+ID+" ll' style='width:60px; background-color:rgba(206,231,248,0.8); font-size:12px; color:#000;'>"+vm.Site[q].ss+"</div>",  {"x":vm.Site[q].x, "y": vm.Site[q].y, "spatialReference": {"wkid": 4326 }},20,0);
									  map.creatInfoSymbol(vm.Site[q].name,"<div class='"+ID+" yl' style='width:60px; background-color:rgba(238,241,246,0.8); font-size:12px; color:#000;'>"+vm.Site[q].yl+"</div>",  {"x":vm.Site[q].x, "y": vm.Site[q].y, "spatialReference": {"wkid": 4326 }},20,-20);
									  //map.creatInfoSymbol(vm.Site[q].siteId+vm.Site[q].name,"<div class='"+ID+" sz' style='width:100px; background-color:rgba(206,231,248,0.8); font-size:12px; color:#000;'>"+vm.Site[q].chlorine+"mg/L<br>"+vm.Site[q].chlorine+"mg/L<br>"+vm.Site[q].chlorine+"mg/L</div>",  {"x":vm.Site[q].x, "y": vm.Site[q].y, "spatialReference": {"wkid": 4326 }},20,10);
							      }
							  }
						  }
						  console.log(vm.Site);
						  /*if($('#map_tip_sz').is(':checked')) {//水质
								$(".sz").show();
							}else{
								$(".sz").hide();
							}*/
						  if($('#map_tip_ll').is(':checked')) {
								$(".ll").show();
							}else{
								$(".ll").hide();
							}
						  if($('#map_tip_yl').is(':checked')) {
								$(".yl").show();
							}else{
								$(".yl").hide();
							}
					  }})
			  }})
			  if(ID=="02-1"){
				  send();
				  }else{
				  closeWebSocket();
			  }
	  },
	  setCenter:function(x,y){
		  console.log(x,y);
		  map.zoomTo(6);
		  map. centerAt( {"x": x, "y": y, "spatialReference": {"wkid": 4326 }});
	  },
	  psgw_detail:function(siteId,name,type){
		  window.location.href=encodeURI('psgw_detail.html?ID='+siteId+'&NAME='+name+'&TYPE='+type);//解决了中文乱码问题
	  }
  }
})

var baseURL = "../../";
var map;
$(function() {
	var s = document.body.scrollHeight;
	$(".main-container").height(s);
	  $.ajax({//获取树形结构
		  type: "POST",
		  url: "../../sys/area/findAllPsArea",
		  contentType: "application/json",
		  data: JSON.stringify(),
		  success: function(r){
			  r=JSON.parse(r);
			  for(var i=0;i<r.length;i++){
				  var result={
	    				  name:r[i].areaName,
	    				  siteId:r[i].areaId,
	    				  treelist:[]
	    		  };
				  for(var m=0; m<r[i].sonList.length; m++){
					  var res={
		    				  name:r[i].sonList[m].areaName,
		    				  siteId:r[i].sonList[m].areaId,
		    		  };
					  result.treelist.push(res);
				  }
				  vm.treeList.push(result);
			  }
    		  console.log(vm.treeList);
		  }
		});
		map = new EJMap("mapcontent", {
		 	   layers:[{id:"管线",display:true,visible:true},{id:"点位",display:true,visible:true},{id:"区域",display:false,visible:false},{id:"定位",display:false}]//图层以及事件的权限过滤,dispay:是否加载，visible：是否显示，只有已加载了的图层才能在页面中控制显示隐藏
		 });
		map.addEventListener(EJMap.Event.onMapLoaded, function(e) {
			map.setLayerDefinition({layerid:"管线"},"PIPE_TYPE='污水'");
			map.setLayerDefinition("点位","DIC_VALUE='P01' or DIC_VALUE='P02' or DIC_VALUE='P03' or DIC_VALUE='P05' or DIC_VALUE='P04' or DIC_VALUE='P06'");
         });

				map.showScalebar();
				map.showNavigation();
				map.addEventListener(EJMap.Event.onMapLoaded, function(e) {
		            map.zoomTo(0);
		         });
				
				$.ajax({//获取点位信息
					  type: "POST",
					  url: "../../sys/site/queryPsgwByArea",
					  contentType: "application/json",
					  data: JSON.stringify(""),
					  success: function(r){
						  r=JSON.parse(r);
						  vm.Site=[];
						  for(var i=0; i<r.length; i++){
							  var site={
								  areaId:r[i].areaId,
								  siteId:r[i].siteId,
								  name:r[i].name,
								  x:r[i].x,
								  y:r[i].y,
								  type:r[i].type,
								  ss:"",
								  jlj:"",
								  yl:"",
								  hclo:"",
								  tur:"",
								  ph:"",
								  time:"",
								  cod:""
							  }
							  vm.Site.push(site);
							  }
						  console.log(vm.Site);
						  $.ajax({//获取列表数据
							  type: "POST",
							  url: "../../pipe/data/findPsByArea",
							  contentType: "application/json",
							  data: JSON.stringify(""),
							  success: function(rr){
								  rr=JSON.parse(rr);
								  console.log(rr.length);
								  console.log(vm.Site);
								  for(var p=0; p<rr.length; p++){
									  for(var q=0; q<vm.Site.length; q++){
										  console.log(vm.Site[q]);
										  if(vm.Site[q].siteId==rr[p].siteId){
											  console.log(rr[p]);
											  if(rr[p].ss){
												  vm.Site[q].ss=rr[p].ss.toFixed(1)+"m³/h";										  
											  }else{
												  vm.Site[q].ss="0.0m³/h";
											  }
											  if(rr[p].jlj){
												  vm.Site[q].jlj=rr[p].jlj.toFixed(1)+"m³";
											  }else{
												  vm.Site[q].jlj="0.0m³";
											  }
											  if(rr[p].yl){
												  vm.Site[q].yl=Number(rr[p].yl).toFixed(2)+"Mpa";
											  }else{
												  vm.Site[q].yl="0.00Mpa";
											  }
											  if(rr[p].chlorine){
												  vm.Site[q].hclo=rr[p].chlorine.toFixed(2)+"mg/L";
											  }else{
												  vm.Site[q].hclo="0.00mg/L";
											  }
											  if(rr[p].turbidity){
												  vm.Site[q].tur=rr[p].turbidity.toFixed(2)+"TUR";
											  }else{
												  vm.Site[q].tur="0.00NTU";
											  }
											  if(rr[p].ph){
												  vm.Site[q].ph=rr[p].ph.toFixed(2);										  
											  }else{
												  vm.Site[q].ph="0.00";
											  }
											  if(rr[p].time2){
												  vm.Site[q].time=rr[p].time2;										  
											  }else{
												  vm.Site[q].time="暂无时间";
											  }
											  if(rr[p].cod){
												  vm.Site[q].cod=rr[p].cod.toFixed(2);									  
											  }else{
												  vm.Site[q].cod="0.00";
											  }
//											  map.creatInfoSymbol(vm.Site[q].siteId,"<div style='width:60px; background-color:rgba(206,231,248,0.8); font-size:12px; color:#000;'>"+vm.Site[q].ss+"</div>",  {"x":vm.Site[q].x, "y": vm.Site[q].y, "spatialReference": {"wkid": 4326 }},20,0);
//											  map.creatInfoSymbol(vm.Site[q].name,"<div style='width:60px; background-color:rgba(238,241,246,0.8); font-size:12px; color:#000;'>"+vm.Site[q].yl+"</div>",  {"x":vm.Site[q].x, "y": vm.Site[q].y, "spatialReference": {"wkid": 4326 }},20,-20);
											  //map.creatInfoSymbol(vm.Site[q].siteId+vm.Site[q].name,"<div class='"+ID+" sz' style='width:100px; background-color:rgba(206,231,248,0.8); font-size:12px; color:#000;'>"+vm.Site[q].chlorine+"mg/L<br>"+vm.Site[q].chlorine+"mg/L<br>"+vm.Site[q].chlorine+"mg/L</div>",  {"x":vm.Site[q].x, "y": vm.Site[q].y, "spatialReference": {"wkid": 4326 }},20,10);
									      }
									  }
								  }
								  console.log(vm.Site);
								  /*if($('#map_tip_sz').is(':checked')) {//水质
										$(".sz").show();
									}else{
										$(".sz").hide();
									}*/
								  if($('#map_tip_ll').is(':checked')) {
										$(".ll").show();
									}else{
										$(".ll").hide();
									}
								  if($('#map_tip_yl').is(':checked')) {
										$(".yl").show();
									}else{
										$(".yl").hide();
									}
							  }})
					  }})
})


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
	map.setLayerVisibleById('管线',true);
	var pipe_type_gx="PIPE_TYPE='污水'";
	map.setLayerDefinition({layerid:"管线"},pipe_type_gx);
	map.setLayerVisibleById('点位',true);
	var pipe_type_gx="PIPE_TYPE='排水'";
	var i="DIC_VALUE='P01' or DIC_VALUE='P02' or DIC_VALUE='P03' or DIC_VALUE='P05' or DIC_VALUE='P04' or DIC_VALUE='P06'"
	map.setLayerDefinition({layerid:"点位"},i);
}
$(document).ready(function(){ 
/*$("#map_tip_sz").change(function() { 
	if($('#map_tip_sz').is(':checked')) {
		$(".sz").show();
	}else{
		$(".sz").hide();
	}
}); */
$("#map_tip_ll").change(function(){ 
	if($('#map_tip_ll').is(':checked')) {
		$(".ll").show();
	}else{
		$(".ll").hide();
	}
});
$("#map_tip_yl").change(function() { 
	if($('#map_tip_yl').is(':checked')) {
		$(".yl").show();
	}else{
		$(".yl").hide();
	}
});
});