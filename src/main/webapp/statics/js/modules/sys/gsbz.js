var option1;
var inlineData='';
var vm = new Vue({
  el: '#app',
  data: {
	  treeList:[],
	  Site:[],
	  ID:null,
	  lastID:null,
	  beng:[],//所有泵站
	  SITEID:null,
	  bengs:[],//该片区需要展示的泵站
	  bengs_beng:[],
	  bengs_site:[],
	  X:"",
	  Y:"",
  },
  mounted() {//页面加载完成后执行
  },
  watch: {
	  X(val){//数据变化时，执行
		  this.getIndex2(this.SITEID,this.X,this.Y);
        }
  },
  methods:{//init() { console.log('test')},
	  getIndex:function(ID){
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
		  inlineData="";
		  console.log(vm.beng);
		  console.log(vm.beng.length);
		  var inlineData="";
		  vm.bengs=[];
		  vm.bengs_beng=[];
		  vm.bengs_site=[];
		  for(var i=0;i<vm.beng.length;i++){
			  if(vm.beng[i].areaId==ID){
				  var fls="";
				  var flc="";
				  console.log(vm.beng[i].siteId);
				  $.ajax({
					    type: "POST",
					    url: "../../pipe/data/queryPipePump",
					    contentType: "application/json",
					    data: JSON.stringify(vm.beng[i].siteId),
					    dataType:"json",
					    async:false, 
					    success: function(result){
					       console.log(result);
					       fls = result.FLS?result.FLS.toFixed(2):"";
					       flc = result.FLC?Math.round(result.FLC):"";	 
					       console.log(fls,flc);
					       console.log(vm.beng[i].siteId);
				  $.ajax({//请求子点位
					    type: "POST",
					    url: baseURL + "sys/site/queryParentSite",
					    contentType: "application/json",
					    data: JSON.stringify(vm.beng[i].siteId),
					    async:false,
					    success: function(r){
					    	r=JSON.parse(r);
					    	vm.beng[i].beng=[];
					        for(var m=0;m<r.length; m++){
					        	if(r[m].type=="S14"){//泵
					        		var bengs_beng={
					        			name:r[m].name,
					        			siteId:r[m].siteId,
					        			parentId:r[m].parentId,
					        			state:"0"
					        		}
					        		vm.bengs_beng.push(bengs_beng);
					        		vm.beng[i].beng.push(bengs_beng);
					        		inlineData+=r[m].siteId+",Fault;"+r[m].siteId+",STOP;"+r[m].siteId+",RUN;"+r[m].siteId+",Mode;";
					        		console.log(inlineData);
					        	}else if(r[m].siteId.indexOf("CK")!=-1){//出口
					        		var bengs_site={
					        			name:r[m].name,
					        			siteId:r[m].siteId,
					        			parentId:r[m].parentId,
					    				fls:fls,
					    				flc:flc,					        			
					        		}
					        		console.log(bengs_site);
					        		vm.bengs_site.push(bengs_site);
					        		vm.beng[i].ckId=r[m].siteId;
					        		vm.beng[i].fls=fls;
					        		vm.beng[i].flc=flc;
					        		inlineData+=r[m].siteId+",FLC;"+r[m].siteId+",FLS;"+r[m].siteId+",PTOUT;"+r[m].siteId+",TUR;"+r[m].siteId+",PH;"+r[m].siteId+",HOCL;";
					        		console.log(inlineData);
					        	}
					        }
					    }
					}); 
				  console.log(vm.beng);
				  console.log(vm.beng[i]);
				  vm.bengs.push(vm.beng[i]);
			  }
		  })
			  }
		  }
		  console.log(inlineData);
		  console.log(vm.bengs);
//		  vm.bengs=[{
//			  areaId:"04-1",
//			  beng:[],
//			  cho:"",
//			  ckId:"04-1-SB-GHDDCK",
//			  fault:false,
//			  jlj:"123",
//			  ins:"123",
//			  name:"港航大道泵站",
//			  ph:"123",
//			  run:true,
//			  siteId:"04-1-SB-GHDD",
//			  stop:false,
//			  tur:"123",
//		  }]
		  console.log(vm.bengs_beng);
		  console.log(vm.bengs_site);
		  send(inlineData);
//		  show_bengzhan();
		  console.log($("#"+ID+"ul"));
		  if($("#"+ID+"ul").is(":hidden")){
			  $("#"+ID+"ul").show();    //如果元素为隐藏,则将它显现
			  $("#"+ID+"span2").show();
			  $("#"+ID+"span1").hide();
			}else{
				$("#"+ID+"ul").hide();     //如果元素为显现,则将其隐藏
				  $("#"+ID+"span1").show();
				  $("#"+ID+"span2").hide();
			}
		  $(".menu-item-2 a").css("color","#363636");
		  $(".menu-item-2 a").css("border-right","#3c8dbc 0px solid");
		  vm.ID=ID;
		  $("#"+ID).css("color","#3c8dbc");
		  $("#"+vm.ID).css("border-right","#3c8dbc 2px solid");
		  $("#"+ID).css("text-decoration","none");
/*		  map.addEventListener(EJMap.Event.onMapLoaded, function(e) {
			  //alert("地图加载完成");
           });*/
		  setTimeout(function(){
			  
		  },1000)
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
	  },
	  getIndex2:function(SITEID,X,Y){
//		  show_bengzhan();
		  console.log(SITEID);
		  console.log(X);
		  $(".menu-item-2 a").css("color","#363636");
		  $(".menu-item-2 a").css("border-right","#3c8dbc 0px solid");
		  vm.SITEID=SITEID;//泵站ID
		  $("#"+SITEID).css("color","#3c8dbc");
		  $("#"+SITEID).css("border-right","#3c8dbc 2px solid");
		  $("#"+SITEID).css("text-decoration","none");
		  map.zoomTo(2);
		  //map.creatInfoSymbol('pop',"<a class='sc_name' href='javascript:'>123123</a>", {"x":X, "y":Y, "spatialReference": {"wkid": 4326} },0,0);
		  map.centerAt( {"x":X, "y":Y, "spatialReference": {"wkid": 4326 }});
		  },
	  gsbz_detail:function(bzId,bzName,parentId){
		  console.log(bzId);
		  console.log(bzName);
		  window.location.href=encodeURI("gsbz_detail.html?id="+bzId+"&name="+bzName+"&parentId="+parentId);
	  }
  }
})
var baseURL = "../../";
var map;

function GetRequest() {
	  var url = decodeURI(location.search); //获取url中"?"符后的字串
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
$(function() {
	var s = document.body.scrollHeight;
	var listpParam = '';
	$(".main-container").height(s-160);
	$("#table_head").width(document.body.clientWidth-180);
	$("#table_body tbody tr").width(document.body.clientWidth-180);
	vm.treeList=[];
	  $.ajax({//获取树形结构
		  type: "POST",
		  url: "../../sys/area/findAllArea",//获取营业所->sonList片区
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
	    				  treelist:[]
		    		  };
					  var beng={'type':'S03','areaId':res.siteId};
					  $.ajax({//获取片区下的泵站
						  type: "POST",
						  url: "../../sys/site/queryfpSite2",
						  contentType: "application/json",
						  async:false,
						  data: JSON.stringify(beng),
						  success: function(rr){
							  rr=JSON.parse(rr);
							  for(var i=0;i<rr.length;i++){
								  var obj={
					    				  name:rr[i].name,
					    				  siteId:rr[i].siteId,
					    				  treelist:[]
								  };
								  console.log("******************************",obj);
								  //res.treelist.push(obj);
								  var fls="";
								  var flc="";
								  $.ajax({
									    type: "POST",
									    url: "../../pipe/data/queryPipePump",
									    contentType: "application/json",
									    data: JSON.stringify(rr[i].siteId),
									    dataType:"json",
									    async:false, 
									    success: function(result){
									       console.log(result);
									       fls = result.FLS?result.FLS.toFixed(2):"";
									       flc = result.FLC?Math.round(result.FLC):"";
									       console.log(fls,flc);
											  $.ajax({//请求子点位
												    type: "POST",
												    url: baseURL + "sys/site/queryParentSite",
												    contentType: "application/json",
												    data: JSON.stringify(rr[i].siteId),
												    async:false,
												    success: function(r){
												    	r=JSON.parse(r);
											        	var beng_list={
											    				  name:rr[i].name,
											    				  siteId:rr[i].siteId,
											    				  x:rr[i].x,
											    				  y:rr[i].y,
											    				  areaId:rr[i].areaId,
											    				  fault:false,
											    				  stop:false,
											    				  run:true,
											    				  beng:[],
											    				  ckId:"",
											    				  jlj:"",
											    				  ins:"",
											    				  yl:"",
											    				  tur:"",
											    				  ph:"",
											    				  cho:"",
											    				  fls:fls,
											    				  flc:flc,
												    		  };
												        for(var m=0;m<r.length; m++){
												        	if(r[m].type=="S14"){//泵
												        		var bengs_beng={
												        			name:r[m].name,
												        			siteId:r[m].siteId,
												        			parentId:r[m].parentId,
												        			state:"0"
												        		}
												        		vm.bengs_beng.push(bengs_beng);
												        		beng_list.beng.push(bengs_beng);
												        		console.log("1111111111111"+listpParam);
												        		listpParam+=r[m].siteId+",Fault;"+r[m].siteId+",STOP;"+r[m].siteId+",RUN;"+r[m].siteId+",Mode;";
												        	}else if(r[m].siteId.substr(r[m].siteId.length-2,2)=="CK"){//出口
												        		var bengs_site={
												        			name:r[m].name,
												        			siteId:r[m].siteId,
												        			parentId:r[m].parentId
												        		}
												        		vm.bengs_site.push(bengs_site);
												        		beng_list.ckId=r[m].siteId;
												        		listpParam+=r[m].siteId+",FLC;"+r[m].siteId+",FLS;"+r[m].siteId+",PTOUT;"+r[m].siteId+",YL;"+r[m].siteId+",TUR;"+r[m].siteId+",PH;"+r[m].siteId+",HOCL;";
												        		console.log("0000000000000000"+listpParam);
												        	}
												        }
											        	console.log(beng_list,"111111111");
											        	vm.bengs.push(beng_list);
												    }
												}); 
									    }
									});
								  var rs={
				    				  name:rr[i].name,
				    				  siteId:rr[i].siteId,
				    				  x:rr[i].x,
				    				  y:rr[i].y,
				    				  areaId:rr[i].areaId,
				    				  fault:false,
				    				  stop:false,
				    				  run:true,
				    				  beng:[],
				    				  ckId:"",
				    				  jlj:"",
				    				  yl:"",
				    				  tur:"",
				    				  ph:"",
				    				  cho:""
					    		  };
								  vm.beng.push(rs);
							  }
						      console.log(vm.bengs);
							  if(vm.beng.length==27){
								  for(var i=0;i<vm.treeList.length; i++){
									  for(var m=0;m<vm.treeList[i].treelist.length;m++){
										  for(var n=0;n<vm.beng.length;n++){
											if(vm.beng[n].areaId==vm.treeList[i].treelist[m].siteId){
												vm.treeList[i].treelist[m].treelist.push(vm.beng[n]);
											}  
										 }
									 }
								  }
							  }
						  }
					  })
					  console.log("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$",res);
					  result.treelist.push(res);
				  }
				  vm.treeList.push(result);
			  }
		  }
		});
	  console.log(vm.beng);
	  console.log("##############################",vm.treeList);
/*		map = new EJMap("mapcontent", {
	 	   layers:[
	 		   {id:"管线",display:false,visible:false },//有
	 		   {id:"点位",display:true,visible:true},//有
	 		   {id:"区域",display:false,visible:false},//无
	 		   {id:"定位",display:false,visible:false},//有
	 		   {id:"片区",display:false,visible:false},//无
	 		   {id:"工单",display:false,visible:false},//有
	 		   {id:"大用户",display:false,visible:false},//无
	 		   {id:"测绘点",display:false,visible:false},//有
	 		   {id:"mapcontent_graphics",display:false,visible:false},//无
	 		   {id:"internal_LabelLayer",display:false,visible:false}//无
	 		   ]//图层以及事件的权限过滤,dispay:是否加载，visible：是否显示，只有已加载了的图层才能在页面中控制显示隐藏
		 });
				map.showScalebar();
				map.showNavigation();
				map.addEventListener(EJMap.Event.onMapLoaded, function(e) {
		            map.zoomTo(5);
		            map.setLayerDefinition({layerid:"点位"},"DIC_VALUE='S03'");
		         });*/
				var Request = new Object();
				Request = GetRequest();
				var articleNum;
				bzId = Request["id"];
				vm.SITEID=bzId;
				parentId = Request["parentId"];
				vm.ID=parentId;
				setTimeout(function(){
					for(var m=0;m<vm.beng.length;m++){
						if(vm.beng[m].siteId==bzId){
			    			vm.X=vm.beng[m].x;
			    			vm.Y=vm.beng[m].y;
			    		}
					}
			    	$("#"+parentId+"ul").show();
			    	$("#"+parentId+"span2").show();
			    	$("#"+parentId+"span1").hide();
			    	$("#"+bzId).css("color","#3c8dbc");
			    	$("#"+bzId).css("border-right","#3c8dbc 2px solid");
			    	$("#"+bzId).css("text-decoration","none");
			    	//vm.getIndex(parentId);
			    	send(listpParam);
			    	//alert(bzId);
			    	//alert(vm.SITEID);
			    },100)
})
/*function show_bengzhan(){
	map.setLayerDefinition({layerid:"点位"},"DIC_VALUE='S03'");
}*/
$(document).ready(function(){ 
$("#map_tip_ll").change(function() { 
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