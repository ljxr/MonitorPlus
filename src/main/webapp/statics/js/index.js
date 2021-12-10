//生成菜单
var menuItem = Vue.extend({
	name: 'menu-item',
	props:{item:{}},
	template:[
		'<li>',
		'	<a v-if="item.type === 0" href="javascript:;">',
		'		<i v-if="item.icon != null" :class="item.icon"></i>',
		'		<span>{{item.name}}</span>',
		'		<i class="fa fa-angle-left pull-right"></i>',
		'	</a>',
		'	<ul v-if="item.type === 0" class="treeview-menu">',
		'		<menu-item :item="item" v-for="item in item.list"></menu-item>',
		'	</ul>',
		'	<a v-if="item.type === 1" :href="\'#\'+item.url"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
		'</li>'
	].join('')
});

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 120);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

//从本地存储获取授权标识
var perms=localStorage.getItem('MenuPerms');
var gsjkType = 2;
var psjkType = 2;
var wsjkType = 2;
var tjfxType = 2;
var dmaType = 2;
var ztszType = 2;
var gscType=2;
var gszltType=2;
var gsbzType=2;
var gsgwType=2;
var gsbjjlType=2;
var pszltType=2;
var psbzType=2;
var psgwType=2;
var psbjjlType=2;
var wscType=2;
var wszltType=2;
var wsbjjlType=2;
var gstjType=2;
var pstjType=2;
var wstjType=2;
var yjxllFXType=2;
var yjxllBBType=2;
var DMAzhanshi =2;
var BCHshezhi =2;
var FQshezhi = 2;
var TJFX =2;
var ztszInnerType = 2;
var firstPage = "";
var firstPageName = "";
if(perms){
	gsjkType=(perms.indexOf("mongs")==-1)?2:0;
	psjkType=(perms.indexOf("monps")==-1)?2:0;
	wsjkType=(perms.indexOf("monws")==-1)?2:0;
	tjfxType=(perms.indexOf("mon:statistics")==-1)?2:0;
	dmaType=(perms.indexOf("mon:dma")==-1)?2:0;
	ztszType=(perms.indexOf("mon:zt:list")==-1)?2:0;
	if(gsjkType==0){
		gscType=(perms.indexOf("mon:factory")==-1)?2:1;
		gszltType=(perms.indexOf("mon:total:list")==-1)?2:1;
		gsbzType=(perms.indexOf("mon:beng")==-1)?2:1;
		gsgwType=(perms.indexOf("mon:pipe:list")==-1)?2:1;
		gsbjjlType=(perms.indexOf("mon:alarm")==-1)?2:1;		
	}
	if(psjkType==0){
		pszltType=(perms.indexOf("monps:view")==-1)?2:1;
		psbzType=(perms.indexOf("monps:beng")==-1)?2:1;
		psgwType=(perms.indexOf("monps:pipe")==-1)?2:1;
		psbjjlType=(perms.indexOf("monps:alarm")==-1)?2:1;		
	}
	if(wsjkType==0){
		wscType=(perms.indexOf("monws:factory")==-1)?2:1;
		wszltType=(perms.indexOf("monws:view")==-1)?2:1;
		wsbjjlType=(perms.indexOf("monws:alarm")==-1)?2:1;
	}
	if(tjfxType==0){
		gstjType=(perms.indexOf("mongs:statistics")==-1)?2:1;
		pstjType=(perms.indexOf("monps:statistics")==-1)?2:1;
		wstjType=(perms.indexOf("monws:statistics")==-1)?2:1;
		yjxllFXType=(perms.indexOf("mon:flownight:analyze")==-1)?2:1;
		yjxllBBType=(perms.indexOf("mon:flownight:report")==-1)?2:1;
	}
	if(dmaType==0){
		DMAzhanshi = (perms.indexOf("mon:dma:view")==-1)?2:1;
		BCHshezhi =(perms.indexOf("mon:dma:book")==-1)?2:1;
		FQshezhi = (perms.indexOf("mon:dma:area")==-1)?2:1;
		TJFX = (perms.indexOf("mon:dma:statistics")==-1)?2:1;
	}
	if(ztszType==0){
		ztszInnerType=(perms.indexOf("mon:zt:list")==-1)?2:1;
	}
	if(gszltType==1){
		firstPage = "modules/sys/map.html";
		firstPageName = "供水总览";
	}else if(gscType==1){
		firstPage = "modules/sys/gsc.html";
		firstPageName = "供水厂";		
	}else if(gsbzType==1){
		firstPage = "modules/sys/gsbz.html";
		firstPageName = "供水泵站";		
	}else if(gsgwType==1){
		firstPage = "modules/sys/gsgw.html";
		firstPageName = "供水管网";		
	}else if(gsbjjlType==1){
		firstPage = "modules/sys/bjjl.html";
		firstPageName = "报警记录";		
	}else if(pszltType==1){
		firstPage = "modules/sys/overView2.html";
		firstPageName = "排水总览";	
	}else if(psgwType==1){
		firstPage = "modules/sys/psgwjc.html";
		firstPageName = "排水管网";		
	}else if(psbzType==1){
		firstPage = "modules/sys/psbzjc.html";
		firstPageName = "排水泵站";		
	}else if(psbjjlType==1){
		firstPage = "modules/sys/bjjl2.html";
		firstPageName = "报警记录";		
	}else if(wszltType==1){
		firstPage = "modules/sys/overView3.html";
		firstPageName = "污水总览";		
	}else if(wscType==1){
		firstPage = "modules/sys/wsclc.html";
		firstPageName = "污水处理厂";		
	}else if(wsbjjlType==1){
		firstPage = "modules/sys/bjjl3.html";
		firstPageName = "报警记录";		
	}else if(gstjType==1){
		firstPage = "modules/sys/gsVital.html";
		firstPageName = "供水统计";		
	}else if(pstjType==1){
		firstPage = "modules/sys/psVital.html";
		firstPageName = "排水统计";		
	}else if(wstjType==1){
		firstPage = "modules/sys/wsVital.html";
		firstPageName = "污水统计";		
	}else if(yjxllFXType==1){
		firstPage = "modules/sys/yjxll.html";
		firstPageName = "夜间小流量";		
	}else if(yjxllBBType==1){
		firstPage = "modules/sys/yjxllbb.html";
		firstPageName = "夜间小流量报表";		
	}else if(DMAzhanshi==1){
		firstPage = "modules/sys/DMA_zhanshi.html";
		firstPageName = "DMA分区展示";		
	}else if(BCHshezhi==1){
		firstPage = "modules/sys/DMA_BCH.html";
		firstPageName = "表册号设置";		
	}else if(FQshezhi==1){
		firstPage = "modules/sys/DMA_SetUp.html";
		firstPageName = "分区设置";		
	}else if(TJFX==1){
		firstPage = "modules/sys/DMA_Vital.html";
		firstPageName = "统计分析";		
	}else if(ztszInnerType==1){
		firstPage = "modules/sys/ztjc.html";
		firstPageName = "组态设置";		
	}else{
		firstPage = "";
		firstPageName = "";		
	}
}else{
}
//授权标识
var vm = new Vue({
	el:'#rrapp',
	data:{
		user:{},
		alarmList:[],
		alarmListLength:0,
		currentPG:1,
		menuList:[
					{icon:"fa fa-tint",list:[
						{icon:"fa fa-genderless",list:null,menuId:51,name:"供水总览",orderNum:1,parentId:50,parentName:null,perms:"null",type:gszltType,url:"modules/sys/map.html"},
						//{icon:"fa fa-genderless",list:null,menuId:51,name:"供水总览",orderNum:1,parentId:50,parentName:null,perms:"null",type:1,url:"modules/sys/overView.html"},
						{icon:"fa fa-genderless",list:null,menuId:52,name:"供水厂",orderNum:2,parentId:50,parentName:null,perms:"null",type:gscType,url:"modules/sys/gsc.html"},
						{icon:"fa fa-genderless",list:null,menuId:53,name:"供水泵站",orderNum:3,parentId:50,parentName:null,perms:"null",type:gsbzType,url:"modules/sys/gsbz.html"},
						{icon:"fa fa-genderless",list:null,menuId:54,name:"供水管网",orderNum:4,parentId:50,parentName:null,perms:"null",type:gsgwType,url:"modules/sys/gsgw.html"},
						{icon:"fa fa-genderless",list:null,menuId:56,name:"报警记录",orderNum:6,parentId:50,parentName:null,perms:"null",type:gsbjjlType,url:"modules/sys/bjjl.html"},
						],menuId:50,name:"　供水监控",orderNum:1,parentId:0,parentName:null,perms:"null",type:gsjkType,url:"null"},
					{icon:"fa fa-stumbleupon",list:[
						{icon:"fa fa-genderless",list:null,menuId:61,name:"排水总览",orderNum:1,parentId:60,parentName:null,perms:"null",type:pszltType,url:"modules/sys/overView2.html"},
						{icon:"fa fa-genderless",list:null,menuId:62,name:"排水管网",orderNum:2,parentId:60,parentName:null,perms:"null",type:psgwType,url:"modules/sys/psgwjc.html"},
						{icon:"fa fa-genderless",list:null,menuId:63,name:"排水泵站",orderNum:3,parentId:60,parentName:null,perms:"null",type:psbzType,url:"modules/sys/psbzjc.html"},
						{icon:"fa fa-genderless",list:null,menuId:66,name:"报警记录",orderNum:5,parentId:60,parentName:null,perms:"null",type:psbjjlType,url:"modules/sys/bjjl2.html"},
						],menuId:60,name:"　排水监控",orderNum:2,parentId:0,parentName:null,perms:"null",type:psjkType,url:"null"},
					{icon:"fa fa-flask",list:[
						{icon:"fa fa-genderless",list:null,menuId:71,name:"污水总览",orderNum:1,parentId:70,parentName:null,perms:"null",type:wszltType,url:"modules/sys/overView3.html"},
						{icon:"fa fa-genderless",list:null,menuId:72,name:"污水处理厂",orderNum:2,parentId:70,parentName:null,perms:"null",type:wscType,url:"modules/sys/wsclc.html"},
						{icon:"fa fa-genderless",list:null,menuId:74,name:"报警记录",orderNum:4,parentId:70,parentName:null,perms:"null",type:wsbjjlType,url:"modules/sys/bjjl3.html"},
						],menuId:70,name:"　污水监控",orderNum:3,parentId:0,parentName:null,perms:"null",type:wsjkType,url:"null"},
					{icon:"fa fa-warning",list:[
						{icon:"fa fa-genderless",list:null,menuId:81,name:"供水统计",orderNum:1,parentId:80,parentName:null,perms:"null",type:gstjType,url:"modules/sys/gsVital.html"},
						{icon:"fa fa-genderless",list:null,menuId:82,name:"排水统计",orderNum:1,parentId:80,parentName:null,perms:"null",type:pstjType,url:"modules/sys/psVital.html"},
						{icon:"fa fa-genderless",list:null,menuId:83,name:"污水统计",orderNum:1,parentId:80,parentName:null,perms:"null",type:wstjType,url:"modules/sys/wsVital.html"},
						{icon:"fa fa-genderless",list:null,menuId:84,name:"夜间小流量分析",orderNum:1,parentId:80,parentName:null,perms:"null",type:yjxllFXType,url:"modules/sys/yjxll.html"},
						{icon:"fa fa-genderless",list:null,menuId:84,name:"夜间小流量报表",orderNum:1,parentId:80,parentName:null,perms:"null",type:yjxllBBType,url:"modules/sys/yjxllbb.html"}
						],menuId:80,name:"　统计分析",orderNum:4,parentId:0,parentName:null,perms:"null",type:tjfxType,url:"null"},
					{icon:"fa fa-map",list:[
						{icon:"fa fa-genderless",list:null,menuId:91,name:"DMA分区展示",orderNum:1,parentId:80,parentName:null,perms:"null",type:DMAzhanshi,url:"modules/sys/DMA_zhanshi.html"},				
						{icon:"fa fa-genderless",list:null,menuId:92,name:"表册号设置",orderNum:1,parentId:80,parentName:null,perms:"null",type:BCHshezhi,url:"modules/sys/DMA_BCH.html"},				
						//{icon:"fa fa-genderless",list:null,menuId:92,name:"用户设置",orderNum:1,parentId:80,parentName:null,perms:"null",type:1,url:"modules/sys/DMA_User.html"},				
						{icon:"fa fa-genderless",list:null,menuId:93,name:"分区设置",orderNum:1,parentId:80,parentName:null,perms:"null",type:FQshezhi,url:"modules/sys/DMA_SetUp.html"},
						{icon:"fa fa-genderless",list:null,menuId:94,name:"统计分析",orderNum:1,parentId:80,parentName:null,perms:"null",type:TJFX,url:"modules/sys/DMA_Vital.html"}
						],menuId:80,name:"　DMA分区",orderNum:5,parentId:0,parentName:null,perms:"null",type:dmaType,url:"null"},
					//{icon:"fa fa-cube",list:null,menuId:90,name:"　组态设置",orderNum:5,parentId:0,parentName:null,perms:"null",type:1,url:"modules/sys/ztjc.html"}
					{icon:"fa fa-cube",list:[
						{icon:"fa fa-genderless",list:null,menuId:90,name:"组态设置",orderNum:5,parentId:0,parentName:null,perms:"null",type:ztszInnerType,url:"modules/sys/ztjc.html"}
						],menuId:80,name:"　组态设置",orderNum:6,parentId:0,parentName:null,perms:"null",type:ztszType,url:"null"},
				],
		main:firstPage,
		password:'',
		newPassword:'',
        navTitle:firstPageName
	},
	methods: {
		getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.user = r.user;

				$.ajax({
					type: "GET",
				    url: "../MonitorPlus/getMenuPerms?userName="+vm.user.username,
				    dataType: "json",
				    success: function(result){
				    	console.log(result);
				    	localStorage.setItem("MenuPerms",result);
				    	perms=localStorage.getItem('MenuPerms');
				    	gsjkType=(perms.indexOf("mongs")==-1)?2:0;
				    	psjkType=(perms.indexOf("monps")==-1)?2:0;
				    	wsjkType=(perms.indexOf("monws")==-1)?2:0;
				    	tjfxType=(perms.indexOf("mon:statistics")==-1)?2:0;
				    	dmaType=(perms.indexOf("mon:dma")==-1)?2:0;
				    	ztszType=(perms.indexOf("mon:zt:list")==-1)?2:0;
				    	if(gsjkType==0){
				    		gscType=(perms.indexOf("mon:factory")==-1)?2:1;
				    		gszltType=(perms.indexOf("mon:total:list")==-1)?2:1;
				    		gsbzType=(perms.indexOf("mon:beng")==-1)?2:1;
				    		gsgwType=(perms.indexOf("mon:pipe:list")==-1)?2:1;
				    		gsbjjlType=(perms.indexOf("mon:alarm")==-1)?2:1;		
				    	}
				    	if(psjkType==0){
				    		pszltType=(perms.indexOf("monps:view")==-1)?2:1;
				    		psbzType=(perms.indexOf("monps:beng")==-1)?2:1;
				    		psgwType=(perms.indexOf("monps:pipe")==-1)?2:1;
				    		psbjjlType=(perms.indexOf("monps:alarm")==-1)?2:1;		
				    	}
				    	if(wsjkType==0){
				    		wscType=(perms.indexOf("monws:factory")==-1)?2:1;
				    		wszltType=(perms.indexOf("monws:view")==-1)?2:1;
				    		wsbjjlType=(perms.indexOf("monws:alarm")==-1)?2:1;
				    	}
				    	if(tjfxType==0){
				    		gstjType=(perms.indexOf("mongs:statistics")==-1)?2:1;
				    		pstjType=(perms.indexOf("monps:statistics")==-1)?2:1;
				    		wstjType=(perms.indexOf("monws:statistics")==-1)?2:1;
				    		yjxllFXType=(perms.indexOf("mon:flownight:analyze")==-1)?2:1;
				    		yjxllBBType=(perms.indexOf("mon:flownight:report")==-1)?2:1;
				    	}
				    	if(dmaType==0){
				    		DMAzhanshi = (perms.indexOf("mon:dma:view")==-1)?2:1;
				    		BCHshezhi =(perms.indexOf("mon:dma:book")==-1)?2:1;
				    		FQshezhi = (perms.indexOf("mon:dma:area")==-1)?2:1;
				    		TJFX = (perms.indexOf("mon:dma:statistics")==-1)?2:1;
				    	}
				    	if(ztszType==0){
				    		ztszInnerType=(perms.indexOf("mon:zt:list")==-1)?2:1;
				    	}
				    	if(gszltType==1){
				    		firstPage = "modules/sys/map.html";
				    		firstPageName = "供水总览";
				    	}else if(gscType==1){
				    		firstPage = "modules/sys/gsc.html";
				    		firstPageName = "供水厂";		
				    	}else if(gsbzType==1){
				    		firstPage = "modules/sys/gsbz.html";
				    		firstPageName = "供水泵站";		
				    	}else if(gsgwType==1){
				    		firstPage = "modules/sys/gsgw.html";
				    		firstPageName = "供水管网";		
				    	}else if(gsbjjlType==1){
				    		firstPage = "modules/sys/bjjl.html";
				    		firstPageName = "报警记录";		
				    	}else if(pszltType==1){
				    		firstPage = "modules/sys/overView2.html";
				    		firstPageName = "排水总览";	
				    	}else if(psgwType==1){
				    		firstPage = "modules/sys/psgwjc.html";
				    		firstPageName = "排水管网";		
				    	}else if(psbzType==1){
				    		firstPage = "modules/sys/psbzjc.html";
				    		firstPageName = "排水泵站";		
				    	}else if(psbjjlType==1){
				    		firstPage = "modules/sys/bjjl2.html";
				    		firstPageName = "报警记录";		
				    	}else if(wszltType==1){
				    		firstPage = "modules/sys/overView3.html";
				    		firstPageName = "污水总览";		
				    	}else if(wscType==1){
				    		firstPage = "modules/sys/wsclc.html";
				    		firstPageName = "污水处理厂";		
				    	}else if(wsbjjlType==1){
				    		firstPage = "modules/sys/bjjl3.html";
				    		firstPageName = "报警记录";		
				    	}else if(gstjType==1){
				    		firstPage = "modules/sys/gsVital.html";
				    		firstPageName = "供水统计";		
				    	}else if(pstjType==1){
				    		firstPage = "modules/sys/psVital.html";
				    		firstPageName = "排水统计";		
				    	}else if(wstjType==1){
				    		firstPage = "modules/sys/wsVital.html";
				    		firstPageName = "污水统计";		
				    	}else if(yjxllFXType==1){
				    		firstPage = "modules/sys/yjxll.html";
				    		firstPageName = "夜间小流量";		
				    	}else if(yjxllBBType==1){
				    		firstPage = "modules/sys/yjxllbb.html";
				    		firstPageName = "夜间小流量报表";		
				    	}else if(DMAzhanshi==1){
				    		firstPage = "modules/sys/DMA_zhanshi.html";
				    		firstPageName = "DMA分区展示";		
				    	}else if(BCHshezhi==1){
				    		firstPage = "modules/sys/DMA_BCH.html";
				    		firstPageName = "表册号设置";		
				    	}else if(FQshezhi==1){
				    		firstPage = "modules/sys/DMA_SetUp.html";
				    		firstPageName = "分区设置";		
				    	}else if(TJFX==1){
				    		firstPage = "modules/sys/DMA_Vital.html";
				    		firstPageName = "统计分析";		
				    	}else if(ztszInnerType==1){
				    		firstPage = "modules/sys/ztjc.html";
				    		firstPageName = "组态设置";		
				    	}else{
				    		firstPage = "";
				    		firstPageName = "";		
				    	}
				    	vm.menuList = [
							{icon:"fa fa-tint",list:[
								{icon:"fa fa-genderless",list:null,menuId:51,name:"供水总览",orderNum:1,parentId:50,parentName:null,perms:"null",type:gszltType,url:"modules/sys/map.html"},
								//{icon:"fa fa-genderless",list:null,menuId:51,name:"供水总览",orderNum:1,parentId:50,parentName:null,perms:"null",type:1,url:"modules/sys/overView.html"},
								{icon:"fa fa-genderless",list:null,menuId:52,name:"供水厂",orderNum:2,parentId:50,parentName:null,perms:"null",type:gscType,url:"modules/sys/gsc.html"},
								{icon:"fa fa-genderless",list:null,menuId:53,name:"供水泵站",orderNum:3,parentId:50,parentName:null,perms:"null",type:gsbzType,url:"modules/sys/gsbz.html"},
								{icon:"fa fa-genderless",list:null,menuId:54,name:"供水管网",orderNum:4,parentId:50,parentName:null,perms:"null",type:gsgwType,url:"modules/sys/gsgw.html"},
								{icon:"fa fa-genderless",list:null,menuId:56,name:"报警记录",orderNum:6,parentId:50,parentName:null,perms:"null",type:gsbjjlType,url:"modules/sys/bjjl.html"},
								],menuId:50,name:"　供水监控",orderNum:1,parentId:0,parentName:null,perms:"null",type:gsjkType,url:"null"},
							{icon:"fa fa-stumbleupon",list:[
								{icon:"fa fa-genderless",list:null,menuId:61,name:"排水总览",orderNum:1,parentId:60,parentName:null,perms:"null",type:pszltType,url:"modules/sys/overView2.html"},
								{icon:"fa fa-genderless",list:null,menuId:62,name:"排水管网",orderNum:2,parentId:60,parentName:null,perms:"null",type:psgwType,url:"modules/sys/psgwjc.html"},
								{icon:"fa fa-genderless",list:null,menuId:63,name:"排水泵站",orderNum:3,parentId:60,parentName:null,perms:"null",type:psbzType,url:"modules/sys/psbzjc.html"},
								{icon:"fa fa-genderless",list:null,menuId:66,name:"报警记录",orderNum:5,parentId:60,parentName:null,perms:"null",type:psbjjlType,url:"modules/sys/bjjl2.html"},
								],menuId:60,name:"　排水监控",orderNum:2,parentId:0,parentName:null,perms:"null",type:psjkType,url:"null"},
							{icon:"fa fa-flask",list:[
								{icon:"fa fa-genderless",list:null,menuId:71,name:"污水总览",orderNum:1,parentId:70,parentName:null,perms:"null",type:wszltType,url:"modules/sys/overView3.html"},
								{icon:"fa fa-genderless",list:null,menuId:72,name:"污水处理厂",orderNum:2,parentId:70,parentName:null,perms:"null",type:wscType,url:"modules/sys/wsclc.html"},
								{icon:"fa fa-genderless",list:null,menuId:74,name:"报警记录",orderNum:4,parentId:70,parentName:null,perms:"null",type:wsbjjlType,url:"modules/sys/bjjl3.html"},
								],menuId:70,name:"　污水监控",orderNum:3,parentId:0,parentName:null,perms:"null",type:wsjkType,url:"null"},
							{icon:"fa fa-warning",list:[
								{icon:"fa fa-genderless",list:null,menuId:81,name:"供水统计",orderNum:1,parentId:80,parentName:null,perms:"null",type:gstjType,url:"modules/sys/gsVital.html"},
								{icon:"fa fa-genderless",list:null,menuId:82,name:"排水统计",orderNum:1,parentId:80,parentName:null,perms:"null",type:pstjType,url:"modules/sys/psVital.html"},
								{icon:"fa fa-genderless",list:null,menuId:83,name:"污水统计",orderNum:1,parentId:80,parentName:null,perms:"null",type:wstjType,url:"modules/sys/wsVital.html"},
								{icon:"fa fa-genderless",list:null,menuId:84,name:"夜间小流量分析",orderNum:1,parentId:80,parentName:null,perms:"null",type:yjxllFXType,url:"modules/sys/yjxll.html"},
								{icon:"fa fa-genderless",list:null,menuId:84,name:"夜间小流量报表",orderNum:1,parentId:80,parentName:null,perms:"null",type:yjxllBBType,url:"modules/sys/yjxllbb.html"}
								],menuId:80,name:"　统计分析",orderNum:4,parentId:0,parentName:null,perms:"null",type:tjfxType,url:"null"},
							{icon:"fa fa-map",list:[
								{icon:"fa fa-genderless",list:null,menuId:91,name:"DMA分区展示",orderNum:1,parentId:80,parentName:null,perms:"null",type:DMAzhanshi,url:"modules/sys/DMA_zhanshi.html"},				
								{icon:"fa fa-genderless",list:null,menuId:92,name:"表册号设置",orderNum:1,parentId:80,parentName:null,perms:"null",type:BCHshezhi,url:"modules/sys/DMA_BCH.html"},				
								//{icon:"fa fa-genderless",list:null,menuId:92,name:"用户设置",orderNum:1,parentId:80,parentName:null,perms:"null",type:1,url:"modules/sys/DMA_User.html"},				
								{icon:"fa fa-genderless",list:null,menuId:93,name:"分区设置",orderNum:1,parentId:80,parentName:null,perms:"null",type:FQshezhi,url:"modules/sys/DMA_SetUp.html"},
								{icon:"fa fa-genderless",list:null,menuId:94,name:"统计分析",orderNum:1,parentId:80,parentName:null,perms:"null",type:TJFX,url:"modules/sys/DMA_Vital.html"}
								],menuId:80,name:"　DMA分区",orderNum:5,parentId:0,parentName:null,perms:"null",type:dmaType,url:"null"},
							//{icon:"fa fa-cube",list:null,menuId:90,name:"　组态设置",orderNum:5,parentId:0,parentName:null,perms:"null",type:1,url:"modules/sys/ztjc.html"}
							{icon:"fa fa-cube",list:[
								{icon:"fa fa-genderless",list:null,menuId:90,name:"组态设置",orderNum:5,parentId:0,parentName:null,perms:"null",type:ztszInnerType,url:"modules/sys/ztjc.html"}
								],menuId:80,name:"　组态设置",orderNum:6,parentId:0,parentName:null,perms:"null",type:ztszType,url:"null"},
						];
				    	vm.main = firstPage;
				    	vm.navTitle = firstPageName;
						if(result.code == 0){
						}else{
						}
					}
				});
			});
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
					    url: "sys/user/password",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
	            }
			});
		}
	},
	created: function(){
		this.getUser();
	},
	updated: function(){
		//路由
		var router = new Router();
		routerList(router, vm.menuList);
		router.start();
	}
});



function routerList(router, menuList){
	console.log(router,menuList);
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
			//vm.navTitle = menu.name;
			routerList(router, menu.list);
		}else if(menu.type == 1){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				//替换iframe的url
			    vm.main = url.replace('#', '');			    
			    //导航菜单展开
			    $(".treeview-menu li").removeClass("active");
			    $("a[href='"+url+"']").parents("li").addClass("active");
			    vm.navTitle = $("a[href='"+url+"']").text();
			});
		}else if(menu.type == 2){
		}
	}
}
