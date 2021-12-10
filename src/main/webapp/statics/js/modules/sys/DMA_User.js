var vm = new Vue({
	  el: '#app',
	  data: {
		  queryName:{
			  userName:"",
			  areaName:""
		  },
		  allAreaList:[],
		  allSysAreaList:[],
		  dicList4UserType:[],
		  saveUser:{
				"userId":"",
				"userName":"",
				"userType":"",
				"areaId":"",
				"areaName":"",
				"dareaId":"",
				"dareaName":"",
				"recordPeriod":""								
		  },
		  updateUser:{
			  	"id":"",
			  	"areaId":"",
				"areaName":""
		  },
		  deleteUser:{
			  	"id":""
		  }
	  },
})

function query_user(){	
	vm.queryName.userName = $("#queryParamUserName").val();
	vm.queryName.areaName = $("#areaName4query option:selected").text();
	console.log(vm.queryName.areaName);
	//vm.queryName.areaName = $("#queryParamAreaName").val();
	layui.use(['laypage','element','laydate','table'], function(){
		  var laypage = layui.laypage
		  ,element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
		  ,laydate = layui.laydate
		  ,layer = layui.layer
		  ,table = layui.table //表格  
		  table.render({
			    elem: '#user_table'
			    ,url: '../../dma/user/queryUserList'
			    ,title: '组态列表'
			    ,datatype: "json"
			    ,page: true //开启分页
			    ,method:'post'		    
			    ,where:vm.queryName
			    ,cols: [[ //表头
			    	  {field: 'userName', title: '用户名', width:300}
				      ,{field: 'userTypeName', title: '用户类型', width:150}
				      ,{field: 'recordPeriod', title: '抄表周期', width:150}
				      ,{field: 'areaName', title: '所属区域', width:300}
				      ,{fixed: 'right', align:'center', toolbar: '#barDemo'}
				    ]]
			   ,id: 'testReload'
		  });
	})
}

function queryAllArea(){
	$.ajax({//获取列表数据
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

function clickNew(){
	queryDicUserTypeList();
	queryAllArea();
	findAllWaterArea();
}

function newUser(){
	vm.saveUser.userId = $("#userId4save").val();
	vm.saveUser.userName = $("#userName4save").val();
	vm.saveUser.userType = $("#userType4save option:selected").val();
	vm.saveUser.areaId = $("#areaName4save").val();
	vm.saveUser.areaName = $("#areaName4save option:selected").text();
	vm.saveUser.dareaId = $("#dareaName4save").val();
	vm.saveUser.dareaName = $("#dareaName4save option:selected").text();
	vm.saveUser.recordPeriod = $("#recordPeriod4save").val();
	console.log(vm.saveUser);
	$.ajax({//获取列表数据
		  type: "POST",
		  url: "../../dma/user/save",
		  contentType: "application/json",
		  data: JSON.stringify(vm.saveUser),
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

function deleteUser(){
	$.ajax({//获取列表数据
		  type: "POST",
		  url: "../../dma/user/delete",
		  contentType: "application/json",
		  data: JSON.stringify(vm.deleteUser),
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

function changeArea(){
	vm.updateUser.areaId = $("#areaName4update").val();
	vm.updateUser.areaName = $("#areaName4update option:selected").text() 
}

function modifyUser(){
	console.log(vm.updateUser);
	$.ajax({//获取列表数据
		  type: "POST",
		  url: "../../dma/user/update",
		  contentType: "application/json",
		  data: JSON.stringify(vm.updateUser),
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

function queryDicUserTypeList(){
	$.ajax({//获取列表数据
		  type: "POST",
		  url: "../../dma/user/queryDicUserTypeList",
		  contentType: "application/json",
		  data:'',
		  success: function(r){
			  r = JSON.parse(r);
			  vm.dicList4UserType = r;
			  console.log(vm.dicList4UserType);
		  }
	})
}

$(function() {
	queryAllArea();
	query_user();
})

