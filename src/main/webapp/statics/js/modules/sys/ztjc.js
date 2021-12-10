var vm = new Vue({
  el: '#app',
  data: {
	site:[],
	site2:[],
    zt_new:{gytName:'',siteParentId:'',siteId:''},
    ztZL_new:{gytName:'',siteParentId:'',siteId:''},
    zt_edit:{id:'',gytName:'',siteParentId:'',siteId:''},
    zt_search:{gytName:'',siteParentId:''},
    jrgs:"3216m³"
  },
  methods:{
	  change_gsc:function(event){
		  console.log("111111111");
		  console.log(event.target.attributes);
	  }
  },
  
})
var baseURL = "../../";
layui.use(['form','laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function(){
	var form = layui.form
  ,laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,table = layui.table //表格
  ,element = layui.element //元素操作
  table.render({
	    elem: '#LAY_table_user'
	    ,url: "../../fac/data/queryGytbase" //数据接口
	    ,title: '组态列表'
	    ,datatype: "json"
	    ,page: true //开启分页
	    ,method:'post'		    
	    ,where:vm.zt_search
	    ,cols: [[ //表头
//	      {type: 'radio', fixed: 'left'}
	      {field: 'gytName', title: '组态名称'}
	      ,{field: 'siteParentId', title: '关联点位'}
	      ,{field: 'siteId', title: '关联子点位'}
	      ,{field: 'siteType', title: '组态类型'} 
	      ,{field: 'createPerson', title: '创建人'}
	      ,{field: 'createTime', title: '创建时间'}
	      ,{field: 'updateTime', title: '更新时间', sort: true, totalRow: true}
	      ,{fixed: 'right', align:'center', width:220, toolbar: '#barDemo'}
	    ]]
	   ,id: 'testReload'
	  });

	var $ = layui.$, active = {
		    reload: function(){
		      //执行重载
		      table.reload('testReload', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where:vm.zt_search,
		      });
		    }
		  };
//监听工具条
table.on('tool(LAY_table_user)', function(obj){
  var data = obj.data;
  if(obj.event === 'detail'){
	(data.siteId == null) ? ID=data.siteParentId : ID=data.siteId;
	if(ID=="S"||ID=="P"||ID=="W"){//当初为什么要分开，工艺图和总览图（因为获取数据点位方式不同）
		window.location.href = "ztsz2.html?ID="+ID+"&siteParentId="+data.siteParentId+"&siteId="+data.siteId;
	}else{
		window.location.href = "ztsz.html?ID="+ID+"&siteParentId="+data.siteParentId+"&siteId="+data.siteId;
	}
  }else if(obj.event === 'preview'){
		(data.siteId == null) ? ID=data.siteParentId : ID=data.siteId;
		if(ID=="S"||ID=="P"||ID=="W"){
			window.location.href = "sssj2.html?ID="+ID+"&siteParentId="+data.siteParentId+"&siteId="+data.siteId;//总览图预览
		}else{
			window.location.href = "sssj3.html?ID="+ID+"&siteParentId="+data.siteParentId+"&siteId="+data.siteId;//工艺图预览
		}
  }else if(obj.event === 'del'){
  ID=data.id;
  layer.confirm('真的删除行么', function(index){
  obj.del();
  layer.close(index);
$.ajax({//删除工艺图列表
    type: "POST",
    url: baseURL + "fac/data/deleteGytbase",
    contentType: "application/json",
    data:JSON.stringify(ID),
    dataType:"json",
    success: function(r){
    	console.log(r);
    }
	});
    });
  vm.$nextTick(function () {//使用这个方法，获取vue更新后的dom
      layui.use(['layer', 'form'], function(){
        var form = layui.form;           
        form.render();
      });
    })
  } else if(obj.event === 'edit'){
	  vm.zt_edit={
			  id:data.id,
			  gytName:data.gytName,
			  siteParentId:data.siteParentId,
			  siteId:data.siteId
	  };
  }
}); 
$('.demoTable .layui-btn').on('click', function(){
  var type = $(this).data('type');
  active[type] ? active[type].call(this) : '';
});
});
function add_gyt(){
	if(vm.zt_new.gytName&&vm.zt_new.siteParentId){
		$('#new_Modal').modal('hide');
		console.log(vm.zt_new);
		$.ajax({//新增工艺图到列表
		    type: "POST",
		    url: baseURL + "fac/data/saveGytbase",
		    contentType: "application/json",
		    data:JSON.stringify(vm.zt_new),
		    dataType:"json",
		    success: function(r){
		    	console.log(r);
		    }
		});
		(vm.zt_new.siteId == "") ? ID=vm.zt_new.siteParentId : ID=vm.zt_new.siteId;
		window.location.href = "ztsz.html?ID="+ID+"&siteParentId="+vm.zt_new.siteParentId+"&siteId="+vm.zt_new.siteId;
	}else{
		layer.msg('请填写组态名称并关联点位');
	}
	
}
function add_zlt(){
	if(vm.ztZL_new.gytName&&vm.ztZL_new.siteParentId){
		$('#newZL_Modal').modal('hide');
		$.ajax({//新增总览图到列表
		    type: "POST",
		    url: baseURL + "fac/data/saveGytbase",
		    contentType: "application/json",
		    data:JSON.stringify(vm.ztZL_new),
		    dataType:"json",
		    success: function(r){
		    	console.log(r);
		    }
		});
		window.location.href = "ztsz2.html";
	}else{
		layer.msg('请填写总览图名称并关联点位');
	}
	
}
function change_list(){
	console.log(vm.zt_edit);
/*	  $.ajax({//更新工艺图列表
		    type: "POST",
		    url: baseURL + "fac/data/updateGytbase",
		    contentType: "application/json",
		    data:JSON.stringify(vm.zt_edit),
		    dataType:"json",
		    success: function(r){
		    	console.log(r);
		    }
			});*/
}
$(function(){
//	从本地存储获取授权标识
	var perms=localStorage.getItem('MenuPerms');
	if(perms){
		if(perms.indexOf("mon:zt:update")==-1){
			$(".bianji").hide();
		}else{
			$(".bianji").show();
		}	
		if(perms.indexOf("mon:zt:delete")==-1){
			$(".shanchu").hide();
		}else{
			$(".shanchu").show();
		}		
		if(perms.indexOf("mon:zt:view")==-1){
			$(".yulan").hide();
		}else{
			$(".yulan").show();
		}		
		if(perms.indexOf("mon:zt:info")==-1){
			$(".chakan").hide();
		}else{
			$(".chakan").show();
		}		
		if(perms.indexOf("mon:zt:query")==-1){
			$(".chaxun").hide();
		}else{
			$(".chaxun").show();
		}		
		if(perms.indexOf("mon:zt:insertzlt")==-1){
			$("#xinzengZL").hide();
		}else{
			$("#xinzengZL").show();
		}		
		if(perms.indexOf("mon:zt:insertgyt")==-1){
			$("#xinzengGY").hide();
		}else{
			$("#xinzengGY").show();
		}	
	}
//授权标识
	$("#app").height(600);
	$.ajax({
	    type: "POST",
	    url: baseURL + "sys/site/queryFacSite",
	    contentType: "application/json",
	    data: "",
	    success: function(r){
	    	var r = eval('('+ r +')');
	     	for(var i=0; i<r.length; i++){
	    		var site={
	    				name:r[i].name,
	    				value:r[i].siteId
	    		};
	    		vm.site.push(site);
	    	} 
	    }
	});
});
window.onload = function(){
	$("#site1").change(function(){
	 	var opt=$("#site1").val();
	 	console.log(opt);
	 	$.ajax({
		    type: "POST",
		    url: baseURL + "sys/site/queryParentSite",
		    contentType: "application/json",
		    data: JSON.stringify(opt),
		    dataType:"json",
		    success: function(r){
		    	vm.site2=[];
 		     	for(var i=0; i<r.length; i++){
		    		var site2={
		    				name:r[i].name,
		    				value:r[i].siteId
		    		};
		    		Vue.set(vm.site2, i, site2);
		    		console.log(vm.site2);
		    	}
		    }
		});     
	});
}
function sssj(){
	window.location.href = "sssj.html?ID=03-1-SB-YS&siteParentId=03-1-SB-YS&siteId=";
}