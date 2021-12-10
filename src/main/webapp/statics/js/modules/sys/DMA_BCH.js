var vm = new Vue({
	  el: '#app',
	  data: {
		  book:{
			  bookId:"",
			  bookName:"",
			  cycle:"",
			  userNum:"",
			  dmaAreaId:"",
		  },
		  allAreaList:[],
		  allSysAreaList:[],
		  dicList4UserType:[],
		  currentEdit:{
			  bookId:"",
			  bookName:"",
			  cycle:"",
			  userNum:"",
			  dmaAreaId:"",
			  geomString:"",
			  x:"",
			  y:""
		  },
		  deleteUser:{
			  	"bookId":""
		  },
		  geomName:"",//是否绘制分区
		  geomName2:"",//是否绘制中心点x
		  geomName3:"",//是否绘制中心点y
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

function query_user(){
	vm.book.bookId = $("#bookId").val();
	vm.book.bookName = $("#bookName").val();
	vm.book.cycle = $("#cycle option:selected").val();
	vm.book.dmaAreaId = $("#dmaAreaId option:selected").val();
	layui.use(['laypage','element','laydate','table'], function(){
		  var laypage = layui.laypage
		  ,element = layui.element//导航的hover效果、二级菜单等功能，需要依赖element模块
		  ,laydate = layui.laydate
		  ,layer = layui.layer
		  ,table = layui.table //表格  
		  table.render({
			    elem: '#user_table'
			    ,url: '../../dma/book/query'
			    ,title: '组态列表'
			    ,datatype: "json"
			    ,page: true //开启分页
			    ,method:'post'		    
			    ,where:vm.book
			    ,cols: [[ //表头
			    	  {field: 'bookId', title: '表册ID', width:200}
				      ,{field: 'bookName', title: '表册号名称', width:200}
				      ,{field: 'cycleString', title: '抄表周期', width:200}
				      ,{field: 'dmaAreaName', title: '所属区域', width:200}
				      ,{field: 'userNum', title: '用户数', width:200}
				      ,{fixed: 'right', align:'center', toolbar: '#barDemo'}
				    ]]
			   ,id: 'testReload'
		  });
	})
}

function queryAllArea(){
	$.ajax({//获取列表数据
		  type: "POST",
		  url: "../../dma/area/queryAllWaterArea",
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

function newBook(){
	vm.currentEdit.bookId = $("#bookId4save").val();
	vm.currentEdit.bookName = $("#bookName4save").val();
	vm.currentEdit.cycle = $("#cycle4save").val();
	vm.currentEdit.userNum = $("#userNum4save").val();
	vm.currentEdit.dmaAreaId = $("#dmaAreaId4save option:selected").val();
	console.log(vm.currentEdit);
	$.ajax({//获取列表数据
		  type: "POST",
		  url: "../../dma/book/save",
		  contentType: "application/json",
		  data: JSON.stringify(vm.currentEdit),
		  success: function(r){
			  r = JSON.parse(r);
			  console.log(r.code);
			  if(r.code == 200){
				  alert("添加成功");
				  vm.currentEdit={
						  bookId:"",
						  bookName:"",
						  cycle:"",
						  userNum:"",
						  dmaAreaId:"",
						  geomString:"",
						  x:"",
						  y:""
					  };
				  location.reload();
			  }else{
				  alert(r.msg);
				  location.reload();
			  }
		  }
	})
}

function deleteUser(){
	$.ajax({//获取列表数据
		  type: "GET",
		  url: "../../dma/book/delete?bookId="+vm.deleteUser.bookId,
		  contentType: "application/json",
		  data: "",
		  success: function(r){
			  r = JSON.parse(r);
			  if(r.code == 200){
				  alert("删除成功");
				  location.reload();
			  }else{
				  alert(r.msg);
				  location.reload();
			  }
		  }
	})
}

function changeArea(){
	vm.currentEdit.areaId = $("#areaName4update").val();
	vm.currentEdit.areaName = $("#areaName4update option:selected").text() 
}

function modifyBook(){
	vm.currentEdit.bookId = $("#bookId4update").val();
	vm.currentEdit.bookName = $("#bookName4update").val();
	vm.currentEdit.cycle = $("#cycle4update").val();
	vm.currentEdit.userNum = $("#userNum4update").val();
	vm.currentEdit.dmaAreaId = $("#dmaAreaId4update option:selected").val();
	console.log(vm.currentEdit);
	$.ajax({//获取列表数据
		  type: "POST",
		  url: "../../dma/book/update",
		  contentType: "application/json",
		  data: JSON.stringify(vm.currentEdit),
		  success: function(r){
//			  if(r == 0){
//				  alert("修改成功");
//				  location.reload();
//			  } else{
//				  alert("修改失败");
//			  }
			  r = JSON.parse(r);
			  alert(r.msg);
			  vm.currentEdit={
					  bookId:"",
					  bookName:"",
					  cycle:"",
					  userNum:"",
					  dmaAreaId:"",
					  geomString:"",
					  x:"",
					  y:""
				  };
			  location.reload();
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
	
//	从本地存储获取授权标识
	var perms=localStorage.getItem('MenuPerms');
	if(perms){
		if(perms.indexOf("mon:dma:book:insert")==-1){
			$("#xinzeng").hide();
		}else{
			$("#xinzeng").show();
		}
		if(perms.indexOf("mon:dma:book:update")==-1){
			$(".xiugai").hide();
		}else{
			$(".xiugai").show();
		}
		if(perms.indexOf("mon:dma:book:info")==-1){
			$(".chaxun").hide();
		}else{
			$(".chaxun").show();
		}
		if(perms.indexOf("mon:dma:book:delete")==-1){
			$(".shanchu").hide();
		}else{
			$(".shanchu").show();
		}		
	}
//授权标识
	queryAllArea();
	query_user();
	$.ajax({//获取列表数据
		  type: "POST",
		  url: "../../dma/area/queryDmaTree",
		  contentType: "application/json",
		  data:'',
		  success: function(r){
			  r = JSON.parse(r);
			  vm.dicList4UserType = r;
			  console.log(vm.dicList4UserType);
		  }
	})
})

