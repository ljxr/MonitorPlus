var vm = new Vue({
	  el: '#app',
	  data: {
		  dareaSite4Set:"",
		  dareaName4Set:"",
		  day4Set:"",
		  week4Set:"",
		  month4Set:"",
		  year4Set:"",
		  dareaName:{
			  dareaName:""
		  },
		  dareaId:"",
		  allAreaList:[],
		  allSysAreaList:[],
		  waterInSiteList:[],
		  waterOutSiteList:[],
		  dicList4UserType:[],
		  dicList4SiteType:[],
		  deleteArea:{
			  	"id":"",
			  	"dareaId":""
		  },
		  querySysSiteListParam:{
			  "siteId":"",
			  "siteName":"",
			  "siteType":"",
			  "areaName":""
		  },
		  waterInsiteListParam:{
			  "dareaId":"",
			  "jc":"进"
		  },
		  waterOutsiteListParam:{
			  "dareaId":"",
			  "jc":"出"
		  },
		  watersiteSaveParam:{
			  "dareaId":"",
			  "dareaName":"",
			  "dsiteInList":[],
			  "dsiteOutList":[],
			  "coefficient4In":"",
			  "coefficient4Out":""
		  },
		  geomName:"",//是否绘制分区
		  geomName2:"",//是否绘制中心点x
		  geomName3:"",//是否绘制中心点y
		  currentEdit:{},//当前编辑片区属性
	  },
	    methods: {
	    	showGeomGetPage:function(){
	    		window.open ("./getgeom.html?type=polygon&regionId="+vm.currentEdit.dareaId);
	    	},
	    	setGeomString:function(arg){
	    		if(!!arg.rings){
	    			 vm.currentEdit.geomString= JSON.stringify(arg);  
	    			 $("#tipE").val(vm.currentEdit.geomString?'区域已绘制点击编辑':'区域未绘制点击绘制');
	    			 $("#tipA").val(vm.currentEdit.geomString?'区域已绘制点击编辑':'区域未绘制点击绘制');
	    			 vm.geomName=(vm.currentEdit.geomString?'区域已绘制点击编辑':'区域未绘制点击绘制');
	    		}
	    	},
	    	showGeomGetPage2:function(){
	    		window.open ("./getgeom.html?type=point&regionId="+vm.currentEdit.dareaId)
	    	},
	    }
})
function search_Area(){	
	vm.dareaName.dareaName = $("#queryParamDAreaName").val();
	layui.use(['laypage','element','laydate','table'], function(){
		  var laypage = layui.laypage
		  ,element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
		  ,laydate = layui.laydate
		  ,layer = layui.layer
		  ,table = layui.table //表格 
		  table.render({
			    elem: '#area_table'
			    ,url: '../../dma/area/queryAreaList'
			    ,title: '组态列表'
			    ,datatype: "json"
			    ,page: true //开启分页
			    ,method:'post'		    
			    ,where:vm.dareaName
			    ,cols: [[ //表头
			    	  {field: 'dareaId', title: '分区编号'}
				      ,{field: 'dareaName', title: '分区名称'}
				      ,{field: 'dareaParentName', title: '上级分区'}
				      ,{field: 'orderNum', title: '排序', width:'10%'}
				      ,{fixed: 'right', align:'center', toolbar: '#barDemo'}
				    ]]
			   ,id: 'testReload'
		  });
	})
}

var inSiteTable = new Array();
function initWaterInSiteTable(){	
	vm.waterInsiteListParam.dareaId = vm.dareaId;
	$.ajax({//获取进水口列表
		  type: "POST",
		  url: "../../dma/site/querySiteList",
		  contentType: "application/json",
		  data: JSON.stringify(vm.waterInsiteListParam),
		  success: function(r){
			  r = JSON.parse(r);
			  vm.waterInSiteList = r.data;
			  reloadInSiteTable();	
			  if (vm.waterInSiteList.length == 0){
				  $("#coefficient4In").val(0);
			  } else {
				  $("#coefficient4In").val(vm.waterInSiteList[0].coefficient);
			  }
		  }
	})	
}

function reloadInSiteTable(){
	layui.use(['laypage','element','laydate','table'], function(){
		  var laypage = layui.laypage
		  ,element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
		  ,laydate = layui.laydate
		  ,layer = layui.layer
		  ,table = layui.table //表格 
		  table.render({
			    elem: '#waterInSiteTable'
			    ,page: false //开启分页
			    ,data: vm.waterInSiteList
			    ,limit:  vm.waterInSiteList.length
			    ,even: true
			    ,cols: [[ //表头
			    		{type:'checkbox', fixed: 'left'}
			    		,{field: 'dsiteId', title: '点位编号', width:145}
			    		,{field: 'dsiteName', title: '点位名称', width:180}
			    		,{field: 'dsiteTypeName', title: '点位类型', width:120}
				    ]]
			   ,id: 'waterInSiteTable'
		  });
	})
}

var outSiteTable = new Array();
function initWaterOutSiteTable(){
	vm.waterOutsiteListParam.dareaId = vm.dareaId;
	$.ajax({//获取进水口列表
		  type: "POST",
		  url: "../../dma/site/querySiteList",
		  contentType: "application/json",
		  data: JSON.stringify(vm.waterOutsiteListParam),
		  success: function(r){
			  r = JSON.parse(r);
			  vm.waterOutSiteList = r.data;
			  reloadOutSiteTable();	
			  if (vm.waterOutSiteList.length == 0){
				  $("#coefficient4Out").val(0);
			  } else {
				  $("#coefficient4Out").val(vm.waterOutSiteList[0].coefficient);
			  }
		  }
	})	
	
}

function reloadOutSiteTable(){
	layui.use(['laypage','element','laydate','table'], function(){
		  var laypage = layui.laypage
		  ,element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
		  ,laydate = layui.laydate
		  ,layer = layui.layer
		  ,table = layui.table //表格 
		  table.render({
			    elem: '#waterOutSiteTable'
			    ,page: false //开启分页
			    ,data: vm.waterOutSiteList
			    ,limit:  vm.waterOutSiteList.length
			    ,even: true
			    ,cols: [[ //表头
			    		{type:'checkbox', fixed: 'left'}
			    		,{field: 'dsiteId', title: '点位编号', width:145}
			    		,{field: 'dsiteName', title: '点位名称', width:180}
			    		,{field: 'dsiteTypeName', title: '点位类型', width:120}
				    ]]
			   ,id: 'waterOutSiteTable'
		  });
	})
}

function initAttr4Area(){
	  
	  if (vm.day4Set == "0"){
		  $("#dayCheckBox").attr("checked",false);
	  }
	  if (vm.week4Set == "0"){
		  $("#weekCheckBox").attr("checked",false);
	  }
	  if (vm.month4Set == "0"){
		  $("#monthCheckBox").attr("checked",false);
	  }
	  if (vm.year4Set == "0"){
		  $("#yearCheckBox").attr("checked",false);
	  }
}
function add(){
	vm.currentEdit={
		border:null,
		borderwidth:null,
		children:null,
		dareaId:"",
		dareaName:"",
		dareaParentId:"",
		dareaParentName:null,
		dareaType:null,
		geomString:null,
		id:null,
		innerX:0,
		innerY:0,
		intra:null,
		order:null,
		orderNum:0,
		sidx:null,
		x:0,
		y:0,
	}
}
function edit(){
	vm.geomName=vm.currentEdit.geomString?'区域已绘制点击编辑':'区域未绘制点击绘制'
}
function findAllWaterArea(){
$.ajax({//获取列表数据
		  type: "POST",
		  url: "../../dma/area/queryAllWaterArea",
		  contentType: "application/json",
		  data:'',
		  success: function(r){
			  r = JSON.parse(r);
			  vm.allAreaList = r;
		  }
	})
}

function initSysSiteTable(){
	vm.querySysSiteListParam.siteId = $("#siteId4set").val();
	vm.querySysSiteListParam.siteName = $("#siteName4set").val();
	vm.querySysSiteListParam.siteType = $("#siteType4set").val();
	vm.querySysSiteListParam.areaName = $("#areaName4set option:selected").text();
	layui.use(['laypage','element','laydate','table'], function(){
		  var laypage = layui.laypage
		  ,element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
		  ,laydate = layui.laydate
		  ,layer = layui.layer
		  ,table = layui.table //表格 
		  table.render({
			    elem: '#sysSiteTable'
			    ,url: '../../sys/site/querySysSiteAndDmaUser'
			    ,title: '组态列表'
			    ,datatype: "json"
			    ,page: true //开启分页
			    ,method:'post'
			    ,where:vm.querySysSiteListParam
			    ,cols: [[ //表头
			    		{type:'checkbox', fixed: 'left'}
			    		,{field: 'siteId', title: '点位编号', width:145}
			    		,{field: 'name', title: '点位名称', width:180}
			    		,{field: 'type', title: '点位类型', width:120}
				    ]]
		  });
	})
}

function saveArea(){
	vm.currentEdit.dareaId = $("#dareaId").val();
	vm.currentEdit.dareaName = $("#dareaName").val();
	vm.currentEdit.dareaParentId = $("#dareaParentName").val()?$("#dareaParentName").val().toString():"";
	$.ajax({//获取列表数据
		  type: "POST",
		  url: "../../dma/area/save",
		  contentType: "application/json",
		  data: JSON.stringify(vm.currentEdit),
		  success: function(r){
			  if(r == 0){
				  alert("添加成功");
				  location.reload();
			  } else{
				  alert("添加失败");
				  location.reload();
			  }
		  }
	})
}

function deleteArea(){
	$.ajax({//获取列表数据
		  type: "POST",
		  url: "../../dma/area/delete",
		  contentType: "application/json",
		  data: JSON.stringify(vm.deleteArea),
		  success: function(r){
			  if(r == 0){
				  alert("删除成功");
				  location.reload();
			  } else{
				  alert("删除失败");
				  location.reload();
			  }
		  }
	})
}

function modifyArea(){
	console.log($("#dareaParentName4update").val(),typeof($("#dareaParentName4update").val()));
	//console.log($("#dareaParentName4update").val().toString(),typeof($("#dareaParentName4update").val().toString()));
	vm.currentEdit.dareaId = $("#dareaId4update").val();
	vm.currentEdit.dareaName = $("#dareaName4update").val();
	vm.currentEdit.dareaParentId = $("#dareaParentName4update").val()?$("#dareaParentName4update").val().toString():"";	
	vm.currentEdit.orderNum = $("#orderNum4update").val();
	$.ajax({//获取列表数据
		  type: "POST",
		  url: "../../dma/area/update",
		  contentType: "application/json",
		  data: JSON.stringify(vm.currentEdit),
		  success: function(r){
			  if(r == 0){
				  alert("修改成功");
				  location.reload();
			  } else{
				  alert("修改失败");
				  location.reload();
			  }
		  }
	})
}

function addJ(data){
    for(var i=0; i<data.length; i++){
    	var repeat = false;
    	var dsite = {
    			dsiteId:data[i].siteId,
    			dsiteName:data[i].name,
    			dsiteTypeName:data[i].type
  		  };
    	for(var j=0; j<vm.waterInSiteList.length; j++){    		
    		if(vm.waterInSiteList[j].dsiteId == dsite.dsiteId){
    	    	repeat = true;
    	    	break;
    		}
    	}
    	if(!repeat){
        	vm.waterInSiteList.push(dsite);
    	}
    }
	// 重载表格
	reloadInSiteTable();
}



function removeJ(data){
	var deleteJParam = {
			dareaId:vm.dareaSite4Set,
			dsiteJc:"进",
			dsiteIdList:[]
	}
    for(var i=0; i<data.length; i++){
    	for(var j=0; j<vm.waterInSiteList.length; j++){
    		if(data[i].dsiteId == vm.waterInSiteList[j].dsiteId){
    			vm.waterInSiteList.splice(j, 1);
    			break;
    		}
    	}	
    }
	// 重载表格
	reloadInSiteTable();
}

function addC(data){
    for(var i=0; i<data.length; i++){
    	var repeat = false;
    	var dsite = {
    			dsiteId:data[i].siteId,
    			dsiteName:data[i].name,
    			dsiteTypeName:data[i].type
  		  };
    	for(var j=0; j<vm.waterOutSiteList.length; j++){    		
    		if(vm.waterOutSiteList[j].dsiteId == dsite.dsiteId){
    	    	repeat = true;
    	    	break;
    		}
    	}
    	if(!repeat){
        	vm.waterOutSiteList.push(dsite);
    	}
    }
	reloadOutSiteTable();
}

function removeC(data){
    for(var i=0; i<data.length; i++){
    	for(var j=0; j<vm.waterOutSiteList.length; j++){
    		if(data[i].dsiteId == vm.waterOutSiteList[j].dsiteId){
    			vm.waterOutSiteList.splice(j, 1);
    			break;
    		}
    	}	
    }
	reloadOutSiteTable();
}

function saveAreaSet(){	
	var inSiteIdList="";
	for(var i=0; i<vm.waterInSiteList.length; i++){
		inSiteIdList += vm.waterInSiteList[i].dsiteId;
		inSiteIdList += ",";
	}
	var OutSiteIdList="";
	for(var i=0; i<vm.waterOutSiteList.length; i++){
		OutSiteIdList += vm.waterOutSiteList[i].dsiteId;
		OutSiteIdList += ",";
	}
	
	var saveParam = {
			dareaId:vm.dareaSite4Set,
			dareaName:vm.dareaName4Set,
			inSiteList:inSiteIdList,
			outSiteList:OutSiteIdList,
			coefficient4In:$("#coefficient4In").val(),
			coefficient4Out:$("#coefficient4Out").val(),
			dayCheck:document.getElementById("dayCheckBox").checked,
			weekCheck:document.getElementById("weekCheckBox").checked,
			monthCheck:document.getElementById("monthCheckBox").checked,
			yearCheck:document.getElementById("yearCheckBox").checked
		}
	$.ajax({
		  type: "POST",
		  url: "../../dma/site/saveAreaSiteSet",
		  contentType: "application/json",
		  data: JSON.stringify(saveParam),
		  success: function(r){
			  if(r == 0){
				  alert("保存成功");
				  location.reload();
			  } else{
				  alert("保存失败");
				  location.reload();
			  }
		  }
	})
}

//获取字典表用户类别
function queryDicUserTypeList(){
	$.ajax({
		  type: "POST",
		  url: "../../dma/user/queryDicUserTypeList",
		  contentType: "application/json",
		  data:'',
		  success: function(r){
			  r = JSON.parse(r);
			  vm.dicList4UserType = r;
		  }
	})
}

//获取所有sys_area片区
function queryAllArea(){
	$.ajax({
		  type: "POST",
		  url: "../../dma/user/queryAllArea",
		  contentType: "application/json",
		  data:'',
		  success: function(r){
			  r = JSON.parse(r);
			  vm.allSysAreaList = r;
		  }
	})
}

//获取字典表点位类别
function queryDicSiteTypeList(){
	$.ajax({
		  type: "POST",
		  url: "../../dma/site/queryDicSiteTypeList",
		  contentType: "application/json",
		  data:'',
		  success: function(r){
			  r = JSON.parse(r);
			  vm.dicList4SiteType = r;
		  }
	})
}
function searchDareaTree(){
	$.ajax({
	    type: "GET",
	    url: baseURL + "/dma/area/querySonList?dareaParentId="+(siteId?siteId:""),
	    contentType: "application/json",
	    data:JSON.stringify(),
	    dataType:"json",
	    success: function(r){
	    	console.log(r);
	    }
	})
}
$(function() {
//	从本地存储获取授权标识
	var perms=localStorage.getItem('MenuPerms');
	if(perms){
		if(perms.indexOf("mon:dma:area:insert")==-1){
			$("#xinzeng").hide();
		}else{
			$("#xinzeng").show();
		}
		if(perms.indexOf("mon:dma:area:update")==-1){
			$(".xiugai").hide();
		}else{
			$(".xiugai").show();
		}
		if(perms.indexOf("mon:dma:area:info")==-1){
			$(".chaxun").hide();
		}else{
			$(".chaxun").show();
		}
		if(perms.indexOf("mon:dma:area:delete")==-1){
			$(".shanchu").hide();
		}else{
			$(".shanchu").show();
		}
		if(perms.indexOf("mon:dma:area:config")==-1){
			$(".shezhi").hide();
		}else{
			$(".shezhi").show();
		}		
	}
//授权标识
	queryAllArea();
	queryDicUserTypeList();
	queryDicSiteTypeList();
	search_Area();
	findAllWaterArea();
})

