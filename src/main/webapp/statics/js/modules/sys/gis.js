var map;
$(function () {
	map = new EJMap("mapcontent", {
 	   layers:[{id:"管线",display:false,visible:false },{id:"点位",display:true,visible:false},{id:"区域",display:false,visible:true},{id:"定位",display:false}]//图层以及事件的权限过滤,dispay:是否加载，visible：是否显示，只有已加载了的图层才能在页面中控制显示隐藏
 });
		map.showScalebar();
		map.showNavigation();
		map.addEventListener(EJMap.Event.onMapLoaded, function(e) {
            map.zoomTo(0);
         });

 })