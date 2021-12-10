 
var host_url = 'http://192.168.101.2:8081/' 
document.write("<link rel='stylesheet' href='" + host_url + "dijit/themes/claro/claro.css'>");
document.write("<link rel='stylesheet' href='" + host_url + "userConfig/css/enjoyor.css'>");
document.write("<link rel='stylesheet' href='" + host_url + "esri/css/esri.css'>"); 
document.write("<script type='text/javascript' src='" + host_url + "init.js'></script>"); 

document.write("<script type='text/javascript' src='" + host_url + "/statics/map/common/common.js'></script>"); 
document.write("<script type='text/javascript' src='" + host_url + "/statics/map/common/jquery.min.js'></script>"); 
 
 
var EJMap = function(content, args) {
	args = args || {} 
	var that = this;
	require([ "esri/map", "esri/SpatialReference", "esri/toolbars/draw", "dojo/parser", "esri/toolbars/edit", "dojo/request", "userConfig/config", "userConfig/MouseTip","dojo/domReady!" ], 
			function(Map, SpatialReference, Draw, Parser, Edit, request, config, MouseTip) {
		that.Config=config;
		that.searchGeom=null;
		that.toolbarEvent=null;
		that.DrawLayer = new esri.layers.GraphicsLayer();
		that.DrawLayer.id="DrawLayer";	
		that.infosymbolList=[];
		Parser.parse();
		esriConfig.defaults.io.proxyUrl = "proxy";
		esriConfig.defaults.io.alwaysUseProxy = false;
		var _target = dojo.byId(content);
		dojo.style(_target, "width", "100%");
		dojo.style(_target, "height", "100%");
		dojo.style(_target, "overflow", "hidden");
		dojo.style(_target, "margin", "0");
		dojo.style(_target, "padding", "0");
		// 初始化地图对象		
		var _basemap = new Map(content, {
			logo : false,
			slider : false,
			nav : false, 
			showLabels : true,   //设置突出label标签是否显示
			spatialReference : new SpatialReference(4326)
		}); 
	 
		that.map = _basemap;
		that.mt = new MouseTip();
		
		// 初始化编辑对象
		var _editToolbar = new Edit(_basemap);
		that.editToolbar = _editToolbar;
		_editToolbar.on("deactivate", function(evt) {
			EJMap.detachEvent("ondeactivate", evt.graphic);
		});
		// 初始化绘图对象
		var _drawbar = new Draw(_basemap, {
			showTooltips : true
		});
		that.drawbar = _drawbar;
		// 绑定地图事件
		that._bindMapEvent(_basemap);
		// 加载配置文件
		that._loadConfig(_basemap, args);
		
	});
	return this;
};  
/**
 * EJMap事件
 */
EJMap.Event = {
	/**
	 * 地图加载完成
	 */
	onMapLoaded : "onMapLoaded",
	/**
	 * 地图点击
	 */
	onMapClick : "onMapClick",
	/**
	 * 地图范围改变
	 */
	onMapExtentChange : "onMapExtentChange",
	/**
	 * 图层添加完成
	 */
	onLayerAdded : "onLayerAdded",
	/**
	 * 要素点击事件
	 */
	onFeatureClick : "onFeatureClick",
	onLayerVisibilityChange : "onLayerVisibilityChange",
	/**
	 * 气泡点击事件
	 */
	onInfoWindowClick : "onInfoWindowClick",
	/**
	 * 图形编辑结束事件
	 */
	ondeactivate : "ondeactivate",
	/**
	 * 地图容器大小改变
	 */
	onresize : "onresize",
	/**
	 * graphicsLayer点击事件
	 */
	GraphicsLayerClick : "GraphicsLayerClick",
	/**
	 * 业务图层点击事件
	 */
	onOperLayerBarClick : "onOperLayerBarClick",
	/**
	 * 气泡关闭时候出发
	 */
	onInfoWindowsHide : "onInfoWindowsHide",
	/**
	 * 气泡显示时候触发
	 */
	onInfoWindowsShow : "onInfoWindowsShow"
};
/**
 * 监听存储变量
 */
EJMap.EventListener = {};
/**
 * 第三方触发监听
 */
EJMap.detachEvent = function(type, args) {
	if (type && this.EventListener[type]) {
		for (var length = this.EventListener[type].length, start = 0; start < length; start += 1) {
			this.EventListener[type][start].fn.call(this.EventListener[type][start].obj, args);
		}
	}
	return this;
};
/**
 * 构造函数
 */
EJMap.prototype = {
	constructor : this,
	_listener : EJMap.EventListener,
	/**
	 * 加载配置文件
	 * 
	 * @param {Object}
	 *            map 地图对象
	 * @param {Object}
	 *            args 配置参数
	 */
	_loadConfig : function(map, args) {
		var that = this;
		require([ "esri/config" ], function(EsriConfig) {

			// 初始化地图中心点
			if (args.center) {
				map.centerAt(args.center);
				EsriConfig.defaults.map.center = args.center;
			} else {
				map.centerAt(that.Config.center);
				EsriConfig.defaults.map.center = that.Config.center;
			}
			// 初始化地图级别
			if (args.zoom) {
				map.setZoom(args.zoom);
				EsriConfig.defaults.map.zoom = args.zoom;
			} else {
				map.setZoom(that.Config.zoom);
				EsriConfig.defaults.map.zoom = that.Config.zoom;
			}
			// 初始化基础图层
			if (that.Config.baselayers && that.Config.baselayers.length > 0) {
				dojo.forEach(that.Config.baselayers, function(baselayer, i) {
					that._loadLayer(map, baselayer);
				});
			}
			// 初始化业务图层
			if (that.Config.operlayers) {
				if (args.layers && args.layers.length > 0) {
					dojo.forEach(that.Config.operlayers, function(operlayer, i) {
						if (typeof (operlayer.children) != 'undefined') {
							dojo.forEach(operlayer.children, function(children, i) {
								dojo.forEach(args.layers, function(layer) {
									(typeof (layer.display) != "undefined") || (layer.display = true);
									if (children.id == layer.id && layer.display) { 
										if (children.template && children.template.actions && children.template.actions.length > 0) {
											dojo.forEach(children.template.actions, function(action) {
												action.display = "none";
												if (layer.actions && layer.actions.length > 0) {
													dojo.forEach(layer.actions, function(effectiveaction) {
														(typeof (effectiveaction.display) != "undefined") || (effectiveaction.display = true);
														if (action.name == effectiveaction.name) {
															action.display = effectiveaction.display ? "inline-block" : "none";
														}
													});
												}

											});
										}
										if(!!typeof (layer.visible) != "undefined"){
											children.visible=layer.visible;
										}
										that._loadLayer(map, children);
									}
								});
							});
						} else {
							dojo.forEach(args.layers, function(layer) {
								(typeof (layer.display) != "undefined") || (layer.display = true);
								if (operlayer.id == layer.id && layer.display) {
									if (operlayer.template && operlayer.template.actions && operlayer.template.actions.length > 0) {
										dojo.forEach(operlayer.template.actions, function(action) {
											action.display = "none";
											if (layer.actions && layer.actions.length > 0) {
												dojo.forEach(layer.actions, function(effectiveaction) {
													(typeof (effectiveaction.display) != "undefined") || (effectiveaction.display = true);
													if (action.name == effectiveaction.name) {
														action.display = effectiveaction.display ? "inline-block" : "none";
													}
												});
											}
										});
									}
									if(!!typeof (layer.visible) != "undefined"){
										operlayer.visible=layer.visible;
									}
									that._loadLayer(map, operlayer);
								}
							});
						}

					});
				} else {
					dojo.forEach(that.Config.operlayers, function(operlayer, i) {
						if (typeof (operlayer.children) != 'undefined') {
							dojo.forEach(operlayer.children, function(children, i) {
								that._loadLayer(map, children);
							});
						} else if (typeof (operlayer.sublayers) != 'undefined') {
							dojo.forEach(operlayer.sublayers, function(sublayer, i) {
								sublayer.id = operlayer.id + '_sub' + i;
								sublayer.visible = operlayer.visible;
								that._loadLayer(map, sublayer);
							});
						} else {
							that._loadLayer(map, operlayer);
						}
					});
				}
			}
		});
	},

	/**
	 * 初始化图层
	 * 
	 * @param {Object}
	 *            map 地图对象
	 * @param {Object}
	 *            layer 图层参数
	 */
	_loadLayer : function(map, layer) {
		var that = this;
		if (!!layer.grouplayers && layer.grouplayers instanceof Array) {// 如果底图变量是个数据，遍历添加全部图层
			dojo.forEach(layer.grouplayers, function(baselayer, i) {
				baselayer.visible = layer.visible || false;
				baselayer.id = layer.id + i;
				baselayer.group = true;
				that._loadLayer(map, baselayer);
			});
			return;
		}
		require([ "esri/layers/ArcGISDynamicMapServiceLayer", "esri/layers/ArcGISTiledMapServiceLayer", "esri/layers/WebTiledLayer", "esri/layers/FeatureLayer2", "userConfig/layers/BaiduMapLayer", "userConfig/layers/AutoNaviMapLayer",
		          "esri/graphic", "esri/InfoTemplate", "esri/renderers/UniqueValueRenderer", "esri/renderers/SimpleRenderer", "esri/layers/WMSLayer", "esri/tasks/query", "esri/tasks/QueryTask",
		          "esri/TimeExtent", "esri/layers/WMTSLayer", "esri/layers/WMTSLayerInfo","esri/renderers/HeatmapRenderer" ], 
		          function(ArcGISDynamicMapServiceLayer, ArcGISTiledMapServiceLayer, WebTiledLayer, FeatureLayer, BaiduMapLayer, AutoNaviMapLayer,Graphic, InfoTemplate, UniqueValueRenderer, SimpleRenderer, WMSLayer, Query, QueryTask, TimeExtent, WMTSLayer, WMTSLayerInfo,
				HeatmapRenderer, NoneMapLayer) {
			switch (layer.type) { 
			case "dynamic":
				layer = new ArcGISDynamicMapServiceLayer(layer.url, {
					id : layer.id,
					visible : layer.visible
				});
				map.addLayer(layer);
				break;
			case "autonavitiled":
				layer = new AutoNaviMapLayer(layer.url, {
					id : layer.id,
					type : "vector",
					visible : layer.visible,
					displaylevel : layer.displaylevel
				});
				map.addLayer(layer);
				break;
			case "baidutiled":
				layer = new BaiduMapLayer(layer.url, {
					id : layer.id,
					type : "vector",
					visible : layer.visible,
					displaylevel : layer.displaylevel
				});
				map.addLayer(layer);
				break; 
			case "tiled":
				layer = new ArcGISTiledMapServiceLayer(layer.url, {
					id : layer.id,
					visible : layer.visible
				});
				map.addLayer(layer);
				break;
			case "feature":
				var objectID;
				if (layer.objectid) {
					objectID = layer.objectid;
				}
				var defaultDefinitionExpression;
				if (layer.defaultDefinitionExpression) {
					defaultDefinitionExpression = layer.defaultDefinitionExpression;
				}
				var labelMinLevel=that.Config.labelMinLevel;
				if(layer.labelMinLevel){
					labelMinLevel=layer.labelMinLevel;
				}
				var label;
				if (layer.label) {
					label = layer.label;
				}
				var mixlabel;
				if(layer.mixlabel){
						mixlabel=layer.mixlabel;
				}
				var refreshInterval;
				if (layer.refresh) {
					refreshInterval = layer.refresh;
				}
				var renderer;
				if (layer.renderer) {
					if (layer.renderer.type == "uniqueValue") {
						renderer = new UniqueValueRenderer(layer.renderer);
					} else if (layer.renderer.type == "simple") {
						renderer = new SimpleRenderer(layer.renderer);
					} else if (layer.renderer.type == "heatmap") {
						renderer = new HeatmapRenderer(layer.renderer);
					}
				}
				var template;
				if (layer.template) {
					template = new InfoTemplate(layer.template);
				}
				var minScale;
				if (layer.minScale) {
					minScale = layer.minScale;
				}
				var maxScale;
				if (layer.maxScale) {
					maxScale = layer.maxScale;
				}
				layer = new FeatureLayer(layer.url, {
					id : layer.id,
					visible : layer.visible,
					outFields : [ "*" ],
					mode : FeatureLayer.MODE_SNAPSHOT
				});

				if (objectID) {
					layer.objectIdField = objectID;
				}
				if(labelMinLevel){
					layer.labelMinLevel=labelMinLevel;
				}
				if (defaultDefinitionExpression) {
					layer.setDefinitionExpression(defaultDefinitionExpression);
				}
				if (label) {
					layer.displayField = label;
				}
				if (label) {
					layer.labelField = label;
				}
				if(mixlabel){
					layer.mixlabelField=mixlabel;
				}
				if (minScale) {
					layer.setMinScale(minScale);
				}
				if (maxScale) {
					layer.setMaxScale(maxScale);
				}
				if (refreshInterval) {
					var ref;
					if (layer.visible) {
						ref = setInterval(function() {
							var query = new Query();
							query.where = layer.getDefinitionExpression();
							query.geometry = layer.getMap().extent;
							query.spatialRelationship = Query.SPATIAL_REL_CONTAINS;
							query.returnGeometry = true;
							query.outFields = [ "*" ];
							var currentTime = new Date();
							query.timeExtent = new TimeExtent(currentTime, dojo.date.add(currentTime, "second", 3));
							var queryTask = new QueryTask(layer.url);
							queryTask.execute(query, function(evt) {
								dojo.forEach(evt.features, function(feature) {
									var iflag=false;
									dojo.forEach(layer.graphics, function(graphic) {
										if(!!graphic)
										if (feature.attributes[layer.objectIdField] == graphic.attributes[layer.objectIdField]) {
											graphic.attributes = feature.attributes;
											graphic.geometry = feature.geometry;
											iflag=true;
										}
									});
									if(!iflag){
										layer.add(feature);
									}
									if (map.infoWindow.isShowing && map.infoWindow.features.length > 0) {
										var select = map.infoWindow.features[map.infoWindow.selectedIndex];
										if (layer.id == select._layer.id && select.attributes[layer.objectIdField] == feature.attributes[layer.objectIdField]) {
											map.infoWindow.hide();
											map.infoWindow.show(feature.geometry);
										}
									}
								});
								dojo.forEach(layer.graphics, function(graphic) {
									var iflag=false;
									if(!!graphic)
									dojo.forEach(evt.features, function(feature) {										
										if (feature.attributes[layer.objectIdField] == graphic.attributes[layer.objectIdField]) {
											iflag=true;
										}
									})
									if(!iflag){
										layer.remove(graphic);
									}
								})
								layer.redraw(); 
							});
							queryTask=null;
							query=null;
						}, refreshInterval * 1000);
					}
					layer.on("visibility-change", function(e) {
						EJMap.detachEvent("onLayerVisibilityChange", e);
						if (e.visible) {
							ref = setInterval(function() {
								var query = new Query();
								query.where = e.target.getDefinitionExpression();
								query.geometry = e.target.getMap().extent;
								query.spatialRelationship = Query.SPATIAL_REL_CONTAINS;
								query.returnGeometry = true;
								query.outFields = [ "*" ];
								var currentTime = new Date();
								query.timeExtent = new TimeExtent(currentTime, dojo.date.add(currentTime, "second", 3));
								var queryTask = new QueryTask(e.target.url);
								queryTask.execute(query, function(evt) {
									dojo.forEach(evt.features, function(feature) {
										var iflag=false;
										dojo.forEach(e.target.graphics, function(graphic) {
											if(!!graphic)
											if (feature.attributes[e.target.objectIdField] == graphic.attributes[e.target.objectIdField]) {
												graphic.attributes = feature.attributes;
												graphic.geometry = feature.geometry;
												iflag=true;
											}
										});
										if(!iflag){
											e.target.add(feature);
										}
										if (map.infoWindow.isShowing && map.infoWindow.features.length > 0) {
											var select = map.infoWindow.features[map.infoWindow.selectedIndex];
											if (layer.id == select._layer.id && select.attributes[layer.objectIdField] == feature.attributes[layer.objectIdField]) {
												map.infoWindow.show(feature.geometry,true);
											}
										}
									});
									dojo.forEach(e.target.graphics, function(graphic) {
										var iflag=false;
										if(!!graphic)
										dojo.forEach(evt.features, function(feature) {										
											if (feature.attributes[e.target.objectIdField] == graphic.attributes[e.target.objectIdField]) {
												iflag=true;
											}
										})
										if(!iflag){
											e.target.remove(graphic);
										}
									})

									e.target.redraw(); 
								});
								queryTask=null; 
								query=null;
							}, refreshInterval * 1000);
						} else {
							clearInterval(ref);
						}
					});
				} else {
					layer.on("visibility-change", function(e) {
						EJMap.detachEvent("onLayerVisibilityChange", e);
					});
				}
				if (renderer) {
					layer.setRenderer(renderer);
				}
				if (template) {
					layer.setInfoTemplate(template);
				}
				layer.on("mouse-over", function(evt) {
					var text=evt.graphic.attributes[label]||"";
					that.mt.setHtml('<div  style="color:#ff0000;background-color:#FFF0F5; height:20px; width:auto;border: 1px solid #000; filter:alpha(opacity=80); -moz-opacity:0.8; opacity:0.8;font-family: \'Microsoft YaHei\';font-size:13px;">' + text.replace(/(^\s*)|(\s*$)/g, "") + '</div>');
					map.setCursor("pointer");
					that.mt.show();
				});
				layer.on("mouse-out", function(evt) {
					that.mt.hide();
					map.setCursor("default");
				});
				layer.on("mouse-up", function(e) {
					EJMap.detachEvent("onFeatureClick", e);
					if (!!this.infoTemplate){
						var g=e.graphic;
						if(!!map.infoWindow.setFeatures){
							map.infoWindow.setFeatures(g);
						}
						if(!!this.infoTemplate.actions){
							that._createMapInfoWindowActions(map, this, this.infoTemplate.actions);
						}else{ 
							dojo.forEach(dojo.query(".actionList", map.infoWindow.domNode), function(actionList) {
								dojo.empty(actionList);
							});
						}
					}

				});
				map.addLayer(layer);
				break;
			case "wms":
				var refreshInterval;
				if (layer.refresh) {
					refreshInterval = layer.refresh;
				}
				var visibleLayers;
				if (layer.layers) {
					visibleLayers = layer.layers;
				}
				var template;
				if (layer.template) {
					template = new InfoTemplate(layer.template);
				}
				layer = new WMSLayer(layer.url, {
					id : layer.id,
					visible : layer.visible,
					format : "png",
					transparent : true,
					resourceInfo : {
						title : layer.id,
						extent : map.extent || [ 0, 0, 0, 0 ],
						layerInfos : []
					}
				});
				if (refreshInterval) {
					layer.setRefreshInterval(refreshInterval / 60);
				}
				if (visibleLayers) {
					layer.setVisibleLayers(visibleLayers);
				}
				if (template) {
					layer.infoTemplate = template;
				}
				layer.on("visibility-change", function(e) {
					EJMap.detachEvent("onLayerVisibilityChange", e);
				});
				map.addLayer(layer);
				break; 
			default:
				break;
			}
		});
	},
	/**
	 * 绑定地图事件
	 * 
	 * @param {Object}
	 *            map 地图对象
	 */
	_bindMapEvent : function(map) {
		var that = this;
		map.infoWindow.on('hide', function(e) {// 气泡关闭事件
			EJMap.detachEvent("onInfoWindowsHide", e);
		});
		map.infoWindow.on('show', function(e) {// 气泡显示事件
			EJMap.detachEvent("onInfoWindowsShow", e);
		});
		map.on("load", function(e) {
			EJMap.detachEvent("onMapLoaded", e);
		});
		map.on("layer-add", function(e) {
			EJMap.detachEvent("onLayerAdded", e);
		});
		map.on("resize", function(e) {
			EJMap.detachEvent("onresize", e);
		});
		map.on("extent-change", function(e) {
			EJMap.detachEvent("onMapExtentChange", e);
			// 平移地图刷新FeatureLayer
			dojo.forEach(map.graphicsLayerIds, function(id) {
				var layer = map.getLayer(id);
				if (layer.declaredClass == "esri.layers.FeatureLayer" && layer.visible) {
					layer.refresh();
				}
			});
		});
		map.on("click", function(e) {
			EJMap.detachEvent("onMapClick", e);
		});
	},
	/**
	 * 添加监听
	 * 
	 * @param {Object}
	 *            type
	 * @param {Object}
	 *            fn
	 */
	addEventListener : function(type, fn) {
		if (typeof type === "string") {
			if (typeof this._listener[type] === "undefined") {
				this._listener[type] = [ {
					obj : this,
					fn : fn
				} ];
			} else {
				this._listener[type].push({
					obj : this,
					fn : fn
				});
			}
		}
		return this;
	},
	/**
	 * 移除监听
	 * 
	 * @param {Object}
	 *            type
	 * @param {Object}
	 *            key
	 */
	removeEventListener : function(type, key) {
		var listeners = this._listener[type];
		if (listeners instanceof Array) {
			if (typeof key === "function") {
				for (var i = 0, length = listeners.length; i < length; i += 1) {
					if (listeners[i] === listener) {
						listeners.splice(i, 1);
						break;
					}
				}
			} else if (key instanceof Array) {
				for (var lis = 0, lenkey = key.length; lis < lenkey; lis += 1) {
					this.removeEventListener(type, key[lenkey]);
				}
			} else {
				delete this._listener[type];
			}
		}
		return this;
	},
	/**
	 * 触发监听
	 * 
	 * @param {Object}
	 *            type
	 * @param {Object}
	 *            args
	 */
	detachEvent : function(type, args) {
		if (type && this._listener[type]) {
			for (var length = this._listener[type].length, start = 0; start < length; start += 1) {
				return this._listener[type][start].call(this, args);
			}
		}
		return this;
	},
	/**
	 * 居中
	 * 
	 * @param {Object}
	 *            point 坐标点：[经度，纬度]
	 */
	centerAt : function(point) {
		var _basemap = this.map;
		require([ "dojo/on","esri/geometry/jsonUtils" ], function(on,jsonUtils) {
			if(!!point.spatialReference){
				point=jsonUtils.fromJson(point)
			}
			if (_basemap.loaded) {
				_basemap.centerAt(point);
			} else {
				on.once(_basemap, "load", function(e) {
					e.map.centerAt(point);
				});
			}
		});
	},
	/**
	 * 缩放
	 * 
	 * @param {Object}
	 *            level 级别
	 */
	zoomTo : function(level) {
		var _basemap = this.map;
		require([ "dojo/on" ], function(on) {
			if (_basemap.loaded) {
				_basemap.setLevel(level);
			} else {
				on.once(_basemap, "load", function(e) {
					e.map.setLevel(level);
				});
			}
		});
	}, 
	/**
	 * 获取图层默认过滤表达式
	 * 
	 * @param {Object}
	 *            args 查询参数:layerid,layer
	 */
	getLayerDefinition : function(args) {
		if (!args || (!args.layerid && !args.layer) || (args.layer && args.layer.type != "Feature Layer")) {
			return;
		}
		var layer;
		if (args.layerid) {
			layer = this.map.getLayer(args.layerid);
		} else {
			layer = args.layer;
		}
		return layer.getDefinitionExpression();

	},
	/**
	 * 设置图层过滤表达式
	 * 
	 * @param {Object}
	 *            args 查询参数:layerid,layer
	 * @param {Object}
	 *            where 过滤表达式
	 */
	setLayerDefinition : function(args, where) {
		if (!args || (!args.layerid && !args.layer) || (args.layer && args.layer.type != "Feature Layer")) {
			return;
		}
		var layer;
		if (args.layerid) {
			layer = this.map.getLayer(args.layerid);
		} else {
			layer = args.layer;
		}
		if(!!layer){
			layer.setDefinitionExpression(where ? where : "1=1");
			if (layer.loaded) {
				layer.refresh();
			}
		} 
	}, 
	drawPolygon : function(args, callback) {
		args = args || {};
		that=this;
		var _basemap = this.map;
		var _drawbar = this.drawbar;
		require([ "dojo/on", "esri/graphic", "esri/symbols/SimpleFillSymbol","esri/geometry/webMercatorUtils"  ], function(On, Graphic, SimpleFillSymbol,webMercatorUtils) {
			args.display = args.display || false;
			args.clean = args.clean || false;
			args.symbol = args.symbol || that.Config.defaults.polygonsymbol;
			var _symbol = new SimpleFillSymbol(args.symbol);
			_drawbar.setFillSymbol(_symbol);
			_drawbar.activate("polygon");
			On.once(_drawbar, "draw-end", function(result) {
				if(result.geometry.spatialReference.wkid!='4326'){
					result.geometry=webMercatorUtils.webMercatorToGeographic(result.geometry)					
				}  
				_drawbar.deactivate();
				args.clean && _basemap.graphics.clear();
				args.display && _basemap.graphics.add(new Graphic(result.geometry, _symbol));
				
				var geom=result.geometry.toJson()
				geom.type='polygon';
				!!!callback || callback(geom);
			});
		});
	},
	/**
	 * 取点
	 * 
	 * @param {Object}
	 *            callback 回调函数:返回结果集
	 */
	getPoint : function(callback) {
		if (typeof callback != "function") {
			return;
		}
		var _basemap = this.map;
		require([ "dojo/on", "esri/graphic", "esri/geometry/Geometry", "esri/geometry/Point", "esri/symbols/Symbol" ], function(on, Graphic, Geometry, Point, Symbol) {
			if (_basemap.loaded) {
				on.once(_basemap, "click", function(evt) {
					var geo=evt.mapPoint;
					require(["esri/geometry/webMercatorUtils"], function(webMercatorUtils) { 
						if(_basemap.spatialReference.wkid!='4326'){
							geo=webMercatorUtils.webMercatorToGeographic(geo)					
						} 
						callback({
							x : geo.x,
							y :geo.y
						});
					});
				});
			} else {
				on.once(_basemap, "load", function(e) {
					on.once(e.map, "click", function(evt) {
						var geo=evt.mapPoint;
						require(["esri/geometry/webMercatorUtils"], function(webMercatorUtils) { 
							if(_basemap.spatialReference.wkid!='4326'){
								geo=webMercatorUtils.webMercatorToGeographic(geo)
							}
							callback({
								x : geo.x,
								y :geo.y
							});
						});
					});
				});
			}
		});
	},
	 
	/**
	 * 要素查询
	 * 
	 * @param {Object}
	 *            args 查询参数:layerid,layer,where
	 * @param {Object}
	 *            callback 回调函数:返回结果集
	 */
	search : function() {

	} ,
	/**
	 * 开始测距
	 */
	measutreLength:function(){
		var that = this;
		require([ 	"userConfig/ToolMeasure" ], function(ToolMeasure) {
			var tool = new ToolMeasure({// 地图测量工具
				map :that.map ,
				geometryService : that.Config.geometryservice
			});
			tool.measutreLength();
		})
	},
	/**
	 * 开始侧面
	 */
	measutreArea:function(){
		var that = this;
		require([ 	"userConfig/ToolMeasure" ], function(ToolMeasure) {
			var tool = new ToolMeasure({// 地图测量工具
				map :that.map ,
				geometryService : that.Config.geometryservice
			});
			tool.measutreArea();
		})
	},
	/**
	 * 取点
	 */
	getMapPoint:function(){
		var that = this;
		require([ 	"userConfig/ToolMeasure" ], function(ToolMeasure) {
			var tool = new ToolMeasure({// 地图测量工具
				map :that.map ,
				geometryService : that.Config.geometryservice
			});
			tool.getMapPoint();
		})
	},
	PrintMap:function(args){
		var str1=this.map.container.innerHTML 
	        var str = '<!DOCTYPE html>'  
	            str +='<html>'  
	            str +='<head>'  	            	
	            str+='<style type="text/css"> ' 
	            str+='  .layerTile{position:absolute;margin: 0;padding: 0;} '
	            str+='  .OperLayerBar{display:none;} '
	            str+='  .baselayerbar{display:none;} '
	            str+='  .ToolBar{display:none;} '
	            str+=' </style>' 
	            str +='<meta charset="utf-8">'  
	            str +='<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">'  
	            str+='<style>';  
	            str+='#oDiv2 div{width: 100px;height: 100px;border:1px solid #c50000;}';  
	            str+='</style>';  
	           
	            str +='</head>'  
	            str +='<body  onload=window.print() >'   
	            str +="<div id='mapcontent'>"+str1+"</div>";  
	            str +='</body>'  
	            str +='</html>'  	  
				var width = mapcontent.clientWidth;
			    var height = mapcontent.clientHeight;
				oPop = window.open('', '我的地图', 'width=' + width + ',height=' + height + ',resizable=no'); 
	            oPop.opener = null;
	       	    oPop.document.write(str);  
	       	    oPop.document.close();
	},
	creatInfoSymbol : function(id, html, center, offsetX, offsetY, index,temp) {
		that = this;
		offsetX = offsetX || 0;
		offsetY = offsetY || 0;
		require([ "esri/geometry/screenUtils", "userConfig/infoSymbol" ], function(screenUtils, infoSymbol) {
			var map = that.map;
			if (!!that.infosymbolList[id]) {
				var infosymbol = that.infosymbolList[id];
				infosymbol.update(html, center, offsetX, offsetY, index);
			} else {
				var infosymbol = new infoSymbol(map, html, center, offsetX, offsetY, index,temp);
				infosymbol.show();
				that.infosymbolList[id] = infosymbol;
			}
		});
	},
	destroyInfoSymbol : function(id) {
		if (id == 'all') {
			for ( var i in this.infosymbolList) {// 用javascript的for/in循环遍历对象的属性
				this.infosymbolList[i].destroy();
				delete this.infosymbolList[i];
			}
		} else {
			if(!!this.infosymbolList[id]){
				this.infosymbolList[id].destroy();
				delete this.infosymbolList[id];	
			}
		}
	},
	centerReset : function() {
		 this.map.setLevel(this.Config.zoom);
		 this.map.centerAt(this.Config.center); 
	},
	zoomIn : function() { 
		 this.map.setLevel(this.map.getLevel() + 1);
	},
	zoomOut : function() {
		 this.map.setLevel(this.map.getLevel() -1);
	},
	setMapSearchGeom:function(type){ 
		this.DrawLayer.clear();
		dojo.disconnect( this.toolbarEvent);
		 this.toolbarEvent=this.drawbar.on("draw-end", dojo.hitch(this, function(geometry){			 
		 this.searchGeom=geometry.geometry;
		 that=this;
			require(["esri/graphic", "esri/geometry/Geometry", "esri/geometry/Extent", "esri/SpatialReference", 
			         "esri/tasks/GeometryService", "esri/tasks/BufferParameters", "esri/symbols/SimpleLineSymbol", 
			         "esri/symbols/SimpleFillSymbol"], dojo.hitch(this, function(graphic, Geometry, Extent, SpatialReference, GeometryService, BufferParameters, SimpleLineSymbol, SimpleFillSymbol) {
	        		switch (geometry.geometry.type) {
					case "polyline":
						var symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255, 0, 0]), 2);
						break;
					case "polygon":
						var symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255, 0, 0]), 2), new dojo.Color([0, 0, 0, 0.25]));
						break;
					case "point":
						var symbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_SQUARE, 10, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color([255, 0, 0]), 1), new esri.Color([0, 255, 0, 0.25]));
						break;
					case "extent":
						var symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255, 0, 0]), 2), new dojo.Color([0, 0, 0, 0.25]));
						break;
						};
						var graphic = new esri.Graphic(geometry.geometry, symbol);
					 	that.DrawLayer.add(graphic);
					 	if(!!!that.map.getLayer("DrawLayer")){
						 	that.map.addLayer(that.DrawLayer);
					 	}
						that.drawbar.deactivate();
			}));
		}));
		this.drawbar.activate(type);
	},
	clearMapSearchGeom:function(type){ 
		this.searchGeom=null;
		this.DrawLayer.clear();
		var layer=this.map.getLayer("searchCenterLayer");
	 	if(!!!layer){
	 		layer.clear();
	 	}
		$('#searchReresultTable').html('');
	},
	showScalebar : function() {
		if (this.map && this.map.loaded) {
			var that = this;
			require([ "esri/dijit/Scalebar" ], function(Scalebar) {
				that.scalebar = new Scalebar({
					map : that.map,
					scalebarUnit : "dual"
				});
			});
		} else {
			this.addEventListener(EJMap.Event.onMapLoaded, function(e) {
				this.showScalebar();
			});
		}
	},
	showNavigation : function(args) {
		args = args || {};
		if (this.map && this.map.loaded) {
			var that = this;
			require([ "userConfig/toolbars/NavigationBarFloat" ], function(NavigationBarFloat) {
				that.navigation = new NavigationBarFloat({
					map : that.map,
					left : args.left || "10px",
					top : args.top || "10px",
					right : args.right || "",
					bottom : args.bottom || ""
				});
				that.navigation.startup();
			});
		} else {
			this.addEventListener(EJMap.Event.onMapLoaded, function(e) {
				this.showNavigation(args);
			});
		}
	},
	MapSearch : function(layer) {	 
		var url=layer.url; 
		var value =document.getElementById("searchInput").value;
		var SearchGeometry=this.searchGeom;
		require(["esri/tasks/query", "esri/tasks/QueryTask", "esri/config","esri/urlUtils"], dojo.hitch(this, function(Query, QueryTask, esriConfig,urlUtils) {
		
			var queryTask = new QueryTask(url);
			var query = new Query();
			query.returnGeometry = true;
			query.outFields = ["*"];
			value = value.replace(/^\s*/, "").replace(/\s*$/, "");
		 
			var lwhere = ''; 
					lwhere =  " name like '%" + value + "%'";
 
			if ( typeof ( SearchGeometry) == "undefined" ||  SearchGeometry == null) {
				query.where = lwhere;
				if (value == "关键字 类别" || value == "*") {
					query.where = "1=1";
				}

			} else {
				query.geometry =  SearchGeometry;
				query.spatialRelationship = Query.SPATIAL_REL_CONTAINS;
				if (value == "关键字 类别" || value == "*") {
					query.where = "1=1";
				} else {
					query.where = lwhere;
				}
			}
			queryTask.execute(query, dojo.hitch(this, function(results) { 
				var str="";
				var layerId=layer.id;
				for(var i=0;i<results.features.length;i++){
					str+="<tr>";
					str+="	<td>"+results.features[i].attributes.ID+"</td>";
					var showname=results.features[i].attributes.NAME;
					if(results.features[i].attributes.NAME.length>6){
						showname=results.features[i].attributes.NAME.substr(0, 6);
					}
					str+="<td title='"+results.features[i].attributes.NAME+"'>"+showname+"</td>";
					if(!!results.features[i].geometry){
						var geoString=JSON.stringify(results.features[i].geometry.toJson());
						var atribute=JSON.stringify(results.features[i].attributes);
						geoString=geoString.replace(/\"/g,'$');
						atribute=atribute.replace(/\"/g,'$');
						str+="<td><a onclick=\"centerAt(\'"+geoString+"\',\'"+atribute+"\',\'"+layerId+"\')\">定位</a></td>";
					}
					str+="</tr>";
				}
				$('#searchReresultTable').prepend(str);
 	
			}));

		}));
	},
	/**
	 * 绘制点要素
	 * 
	 * @param {Object}
	 *            args 点参数
	 * @param {Object}
	 *            callback 回调函数
	 */
	drawPolyline : function(args, callback) {
		args = args || {};
		var _basemap = this.map;
		var _drawbar = this.drawbar;
		require([ "dojo/on", "esri/graphic", "esri/symbols/SimpleLineSymbol","esri/geometry/webMercatorUtils"  ], function(On, Graphic, SimpleLineSymbol,webMercatorUtils) {
			args.symbol = args.symbol || esriConfig.defaults.polylinesymbol;
			args.display = args.display || false;
			args.clean = args.clean || false;
			var _symbol = new SimpleLineSymbol(args.symbol);
			_drawbar.setLineSymbol(_symbol);
			_drawbar.activate("polyline", {
				tolerance : 0,
				tooltipOffset : 100
			});
			On.once(_drawbar, "draw-end", function(result) {
				if(result.geometry.spatialReference.wkid!='4326'){
					result.geometry=webMercatorUtils.webMercatorToGeographic(result.geometry)					
				}  
				_drawbar.deactivate();
				args.clean && _basemap.graphics.clear();
				args.display && _basemap.graphics.add(new Graphic(result.geometry, _symbol));
				var geom=result.geometry.toJson()
				geom.type='polyline';
				!!!callback || callback(geom);
			});
		});
	},
	/**
	 * 
	 * @param {Object}
	 *            args 图形参数
	 * @param {Object}
	 *            layerId 图层容器id
	 * @param {Object}
	 *            clear 绘制前是否情况图层
	 */

	addPolygon2GraphicsLayer : function(args, layerId, clear, callback) {
		layerId = layerId || "userGraphicsLayer";
		clear = clear || false;
		args = args || {};
		var _basemap = this.map;
		editToolbar = this.editToolbar;
		that = this;
		require([ "dojo/on", "esri/graphic", "esri/geometry/Point", "esri/symbols/Symbol", "esri/InfoTemplate", "esri/symbols/SimpleFillSymbol", "dojo/on", "esri/geometry/Polygon", "dojo/_base/event" ], function(On, Graphic, Point, Symbol, InfoTemplate, SimpleFillSymbol, on, Polygon, event) {
			args.geom || console.error("map.js中addPolygon2GraphicsLayer方法未指定geom！");
			args.symbol = args.symbol || that.Config.defaults.polygonsymbol;
			args.attributes = args.attributes || {};
			var point = new Point({x:args.geom.rings[0][0][0],y:args.geom.rings[0][0][1], spatialReference:args.geom.spatialReference});
			var polygon = new Polygon(args.geom);
			var graphic = new Graphic(polygon, new SimpleFillSymbol(args.symbol), args.attributes);
			_basemap.loaded || console.error("map.js中addPolygon2GraphicsLayer方法未指定map对象或者map对象为空");
			if (typeof (_basemap.getLayer(layerId)) == 'undefined') {
				var glayer = new esri.layers.GraphicsLayer();
				glayer.id = layerId;
				_basemap.addLayer(glayer);
				_basemap.getLayer(layerId).on("click", function(evt) {
					// event.stop(evt);
					if (typeof (evt.graphic.infoTemplate) != 'undefined' && evt.graphic.infoTemplate.actions != 'undefined')
						that._createMapInfoWindowActions(_basemap, evt.currentTarget, evt.graphic.infoTemplate.actions);
					if (!!args.onclickeven) {

						args.onclickeven(evt);
					}
				});
				if (!!args.ondbclick) {
					_basemap.getLayer(layerId).on("dbl-click", function(evt) {
						event.stop(evt);
						args.ondbclick(evt);
					});
				}
				if (!!args.onmouseover) {
					_basemap.getLayer(layerId).on("mouse-over", function(evt) {
						// event.stop(evt);
						args.onmouseover(evt);
					});
				}
				if (!!args.onmouseout) {
					_basemap.getLayer(layerId).on("mouse-out", function(evt) {
						// event.stop(evt);
						args.onmouseout(evt);
						;
					});
				}
				if (!!args.onmousedown) {
					_basemap.getLayer(layerId).on("mouse-down", function(evt) {
						// event.stop(evt);
						args.onmousedown(evt);
					});
				}
				if (!!args.onmouseup) {
					_basemap.getLayer(layerId).on("mouse-up", function(evt) {
						// event.stop(evt);
						args.onmouseup(evt);
					});
				}
				if (!!args.edit) {
					that.EditToolbarHandler = _basemap.getLayer(layerId).on("click", function(evt) {
						event.stop(evt);
						var options = {
							allowAddVertices : true,
							allowDeleteVertices : true,
							uniformScaling : true
						};
						editToolbar.activate(15, evt.graphic, options);
						on.once(_basemap, "click", function(e) {
							editToolbar.deactivate();
						});
					});

				}
				glayer.add(graphic);
			} else {
				!!!clear || _basemap.getLayer(layerId).clear();
				_basemap.getLayer(layerId).add(graphic);
			}

			if (!!args.template) {
				graphic.infoTemplate = new InfoTemplate(args.template);
				if (!!args.showInfowindow) {
					var actionList = dojo.query(".actionList", _basemap.infoWindow.domNode)[0];
					// 清空气泡控制按钮
					dojo.empty(actionList);
					var reg = /{([^{}]+)}/gm;
					var title = args.template.title.replace(reg, function(match, name) {
						return args.attributes[name];
					});
					var content = args.template.content.replace(reg, function(match, name) {
						return args.attributes[name];
					});
					var regS = new RegExp("\\$", "g");
					that._createMapInfoWindowActions(_basemap, _basemap.getLayer(layerId), args.template.actions, args.attributes);
					_basemap.infoWindow.setTitle(title.replace(regS, ''));
					_basemap.infoWindow.setContent(content.replace(regS, ''));
					_basemap.infoWindow.show(point);
				}
			}
			!!!args.zoom || _basemap.centerAndZoom(point,_basemap.getMaxZoom() - 1);
			!!!args.center || _basemap.centerAt(point);
			!!!args.extent || _basemap.setExtent(polygon.getExtent().expand(1.4));
			!!!callback || callback(graphic.toJson());
		});
	},
	/**
	 * 
	 * @param {Object}
	 *            args 图形参数
	 * @param {Object}
	 *            layerId 图层容器id
	 * @param {Object}
	 *            clear 绘制前是否情况图层
	 */

	addPoint2GraphicsLayer : function(args, layerId, clear, callback) {
		layerId = layerId || "userGraphicsLayer";
		clear = clear || false;
		args = args || {};
		var _basemap = this.map;
		editToolbar = this.editToolbar;
		that = this;
		require([ "dojo/on", "esri/graphic", "esri/geometry/Point", "esri/symbols/Symbol", "esri/InfoTemplate", "esri/symbols/PictureMarkerSymbol", "dojo/on", "dojo/_base/event" ,"esri/layers/PicTextGraphicLayer"], function(On, Graphic, Point, Symbol, InfoTemplate, PictureMarkerSymbol, on, event,PicTextGraphicLayer) {
			args.geom || console.error("map.js中addPoint2GraphicsLayer方法未指定point对象或者geom对象为空");
			args.symbol = args.symbol || that.Config.defaults.pointsymbol;
			args.attributes = args.attributes || {};
			args.mixlabelField=args.mixlabel||''; 
			args.labelField=args.label||''; 
			var point = new Point(args.geom);
			var graphic = new Graphic(point, new PictureMarkerSymbol(args.symbol), args.attributes);
			_basemap.loaded || console.error("map.js中addPoint2GraphicsLayer方法未指定map对象或者map对象为空");
			if (typeof (_basemap.getLayer(layerId)) == 'undefined') {
				var glayer = new PicTextGraphicLayer({});
				glayer.id = layerId;
				_basemap.addLayer(glayer);
				 glayer.mixlabelField=args.mixlabelField;
				 glayer.labelField=args.labelField;
				 glayer.labelShow=true;
				_basemap.getLayer(layerId).on("click", function(evt) {
					// event.stop(evt);
					if (typeof (evt.graphic.infoTemplate) != 'undefined' && evt.graphic.infoTemplate.actions != 'undefined')
						that._createMapInfoWindowActions(_basemap, evt.currentTarget, evt.graphic.infoTemplate.actions);
					if (!!args.onclickeven) {
						args.onclickeven(evt);
					}
				});

				if (!!args.ondbclick) {
					_basemap.getLayer(layerId).on("dbl-click", function(evt) {
						event.stop(evt);
						args.ondbclick(evt);
					});
				}
				if (!!args.onmouseover) {
					_basemap.getLayer(layerId).on("mouse-over", function(evt) {
						// event.stop(evt);
						args.onmouseover(evt);
					});
				}
				if (!!args.onmouseout) {
					_basemap.getLayer(layerId).on("mouse-out", function(evt) {
						// event.stop(evt);
						args.onmouseout(evt);
						;
					});
				}
				if (!!args.onmousedown) {
					_basemap.getLayer(layerId).on("mouse-down", function(evt) {
						// event.stop(evt);
						args.onmousedown(evt);
					});
				}
				if (!!args.onmouseup) {
					_basemap.getLayer(layerId).on("mouse-up", function(evt) {
						// event.stop(evt);
						args.onmouseup(evt);
					});
				}
				if (!!args.edit) {
					that.EditToolbarHandler = _basemap.getLayer(layerId).on("click", function(evt) {
						event.stop(evt);
						var options = {
							allowAddVertices : true,
							allowDeleteVertices : true,
							uniformScaling : true
						};
						editToolbar.activate(15, evt.graphic, options);
						on.once(_basemap, "click", function(e) {
							// event.stop(evt);
							editToolbar.deactivate();
						});
					});

				}
				glayer.add(graphic);
			} else {
				!!!clear || _basemap.getLayer(layerId).clear();
				_basemap.getLayer(layerId).add(graphic);
			}

			if (!!args.template) {
				graphic.infoTemplate = new InfoTemplate(args.template);
				if (!!args.showInfowindow) {
					var actionList = dojo.query(".actionList", _basemap.infoWindow.domNode)[0];
					// 清空气泡控制按钮
					dojo.empty(actionList);
					var reg = /{([^{}]+)}/gm;
					var title = args.template.title.replace(reg, function(match, name) {
						if (args.attributes[name])
							return args.attributes[name];
						else
							return '';
					});
					var content = args.template.content.replace(reg, function(match, name) {
						if (args.attributes[name])
							return args.attributes[name];
						else
							return '';
					});
					var regS = new RegExp("\\$", "g");

					that._createMapInfoWindowActions(_basemap, _basemap.getLayer(layerId), args.template.actions, args.attributes);
					_basemap.infoWindow.setTitle(title.replace(regS, ''));
					_basemap.infoWindow.setContent(content.replace(regS, ''));
					_basemap.infoWindow.hide();
					_basemap.infoWindow.show(point);
				}
			}
			;
			!!!args.zoom || _basemap.centerAndZoom(point,_basemap.getMaxZoom() - 1);
			!!!args.center || _basemap.centerAt(point);
			!!!callback || callback(graphic.toJson());
		});
	},
	addLine2GraphicsLayer : function(args, layerId, clear, callback) {
		layerId = layerId || "userGraphicsLayer";
		clear = clear || false;
		args = args || {};
		var _basemap = this.map;
		editToolbar = this.editToolbar;
		that = this;
		require([ "dojo/on", "esri/graphic", "esri/geometry/Point", "esri/symbols/Symbol", "esri/InfoTemplate", "esri/symbols/SimpleLineSymbol", "dojo/on", "esri/geometry/Polyline", "dojo/_base/event","esri/renderers/UniqueValueRenderer" ], function(On, Graphic, Point, Symbol, InfoTemplate, SimpleLineSymbol, on, Polyline, event,UniqueValueRenderer) {
			args.geom || console.error("map.js中addLine2GraphicsLayer方法未指定point对象或者geom对象为空");
			args.symbol = args.symbol || esriConfig.defaults.polylinesymbol;
			args.attributes = args.attributes || {}; 
			_basemap.loaded || console.error("map.js中addLine2GraphicsLayer方法未指定map对象或者map对象为空");
			if (typeof (_basemap.getLayer(layerId)) == 'undefined') {
				var glayer = new esri.layers.GraphicsLayer();
				glayer.id = layerId;
				_basemap.addLayer(glayer);
				_basemap.getLayer(layerId).on("click", function(evt) {  
					if (!!args.onclickeven) {
						args.onclickeven(evt);
					}
				}); 
				if (!!args.onmousedown) {
					_basemap.getLayer(layerId).on("mouse-down", function(evt) {
						//event.stop(evt);
						args.onmousedown(evt);
					});
				} 
				if (!!args.edit) {
					that.EditToolbarHandler = _basemap.getLayer(layerId).on("click", function(evt) { 
						var options = {
							allowAddVertices : true,
							allowDeleteVertices : true,
							uniformScaling : true
						};
						editToolbar.activate(15, evt.graphic, options);
						on.once(_basemap, "click", function(e) {
							editToolbar.deactivate();
						});
						event.stop(evt);
					});
				} 
			} else {
				!!!clear || _basemap.getLayer(layerId).clear(); 
			} 
			var point = new Point({x:args.geom.paths[0][0][0],y:args.geom.paths[0][0][1],spatialReference:args.geom.spatialReference});
			var polyline = new Polyline(args.geom);
			var graphic;
			if(!!_basemap.getLayer(layerId).renderer){
				 graphic = new Graphic(polyline, null, args.attributes);
			}else{
				 graphic = new Graphic(polyline, new SimpleLineSymbol(args.symbol), args.attributes);
			}  
			_basemap.getLayer(layerId).add(graphic);
			!!!args.zoom || _basemap.centerAndZoom(point,_basemap.getMaxZoom() - 1);
			!!!args.center || _basemap.centerAt(point);
			!!!callback || callback(graphic.toJson());
		});
	},
	addText2GraphicsLayer : function(args, layerId, clear, callback) {
		layerId = layerId || "userGraphicsLayer";
		clear = clear || false;
		args = args || {};
		var _basemap = this.map;
		editToolbar = this.editToolbar;
		that = this;
		require([ "dojo/on", "esri/graphic", "esri/geometry/Point", "esri/symbols/Symbol", "esri/InfoTemplate", "esri/symbols/TextSymbol", "dojo/on", "dojo/_base/event" ], function(On, Graphic, Point, Symbol, InfoTemplate, TextSymbol, on, event) {
			args.geom || console.error("map.js中addText2GraphicsLayer方法未指定point对象或者geom对象为空");
			args.symbol = args.symbol || EnjoyorConfig.defaults.pointsymbol;
			args.attributes = args.attributes || {};
			var point = new Point(args.geom);
			var graphic = new Graphic(point, new TextSymbol(args.symbol), args.attributes);
			_basemap.loaded || console.error("map.js中addText2GraphicsLayer方法未指定map对象或者map对象为空");
			if (typeof (_basemap.getLayer(layerId)) == 'undefined') {
				var glayer = new esri.layers.GraphicsLayer();
				glayer.id = layerId;
				_basemap.addLayer(glayer);
				_basemap.getLayer(layerId).on("click", function(evt) {
					// event.stop(evt);
					if (typeof (evt.graphic.infoTemplate) != 'undefined' && evt.graphic.infoTemplate.actions != 'undefined')
						that._createMapInfoWindowActions(_basemap, evt.currentTarget, evt.graphic.infoTemplate.actions);
					if (!!args.onclickeven) {
						args.onclickeven(evt);
					}
				});
				if (!!args.ondbclick) {
					_basemap.getLayer(layerId).on("dbl-click", function(evt) {
						event.stop(evt);
						args.ondbclick(evt);
					});
				}
			 
				if (!!args.onmousedown) {
					_basemap.getLayer(layerId).on("mouse-down", function(evt) {
						args.onmousedown(evt);
					});
				}
				if (!!args.onmouseup) {
					_basemap.getLayer(layerId).on("mouse-up", function(evt) {
						args.onmouseup(evt);
					});
				}
				if (!!args.edit) {
					that.EditToolbarHandler = _basemap.getLayer(layerId).on("click", function(evt) {
						event.stop(evt);
						var options = {
							allowAddVertices : true,
							allowDeleteVertices : true,
							uniformScaling : true
						};
						editToolbar.activate(15, evt.graphic, options);
						on.once(_basemap, "click", function(e) {
							editToolbar.deactivate();
						});
					});

				}
				glayer.add(graphic);
			} else {
				!!!clear || _basemap.getLayer(layerId).clear();
				_basemap.getLayer(layerId).add(graphic);
			}

			if (!!args.template) {
				graphic.infoTemplate = new InfoTemplate(args.template);
				if (!!args.showInfowindow) {
					var actionList = dojo.query(".actionList", _basemap.infoWindow.domNode)[0];
					// 清空气泡控制按钮
					dojo.empty(actionList);
					var reg = /{([^{}]+)}/gm;
					var title = args.template.title.replace(reg, function(match, name) {
						return args.attributes[name];
					});
					var content = args.template.content.replace(reg, function(match, name) {
						return args.attributes[name];
					});
					var regS = new RegExp("\\$", "g");
					that._createMapInfoWindowActions(_basemap, _basemap.getLayer(layerId), args.template.actions, args.attributes);
					_basemap.infoWindow.setTitle(title.replace(regS, ''));
					_basemap.infoWindow.setContent(content.replace(regS, ''));
					_basemap.infoWindow.hide();
					_basemap.infoWindow.show(point);
				}
			}
			;

			!!!args.zoom || _basemap.centerAndZoom(point,_basemap.getMaxZoom() - 1);
			!!!args.center || _basemap.centerAt(point);
			!!!callback || callback(graphic.toJson());
		});
	},
	setLayerVisibleById : function(layerId, visible) {
		console.log("控制管线");
		var _basemap = this.map;
		if (!!_basemap && !!_basemap.loaded && _basemap.getLayer(layerId)) {
			_basemap.getLayer(layerId).setVisibility(visible);
		}

	},
	clearLayerById : function(layerId) {
		var _basemap = this.map;
		if (!!_basemap && !!_basemap.loaded && _basemap.getLayer(layerId)) {
			_basemap.getLayer(layerId).clear();
		}
	},
	clearMap:function(){
		var _basemap = this.map;
		if (!!_basemap && !!_basemap.loaded && !!_basemap. graphics) {
			_basemap.graphics.clear();
		}
	},
	refreshLayerById:function(layerId){
		var _basemap = this.map;
		if (!!_basemap && !!_basemap.loaded && _basemap.getLayer(layerId)) {
			_basemap.getLayer(layerId).refresh();
		}
	}, 
	reorderLayer : function(layerid, index) {
		var layer = this.map.getLayer(layerid);
		if (layer) {
			this.map.reorderLayer(layer, index);
		}
	},
	initLable : function(layerid, visible, label) {
		var layer = this.map.getLayer(layerid); 
		if(!!!layer){
			console.log('没找到名称为'+layerid+'的图层');
			return;	
		}
		require(["esri/symbols/TextSymbol","esri/layers/LabelClass","esri/Color" ], function(TextSymbol, LabelClass,Color){
			if(layer.geometryType=="esriGeometryPolygon"||layer.geometryType=="esriGeometryPolyline"){
				featureLayer =layer;
				var statesLabel = new TextSymbol().setColor(new Color("#0a162c"));
				statesLabel.font.setSize("15pt");
				statesLabel.font.setWeight(700);
				var labelClass = new LabelClass({
				    "labelExpressionInfo": {
				        "value": "{"+label+"}"
				    }
				});
				labelClass.symbol = statesLabel;
				if(visible){
					featureLayer.setLabelingInfo([labelClass]);
				}else{
					featureLayer.setLabelingInfo([]);
				}
			}else{
				layer.showLabel(visible);
			} 
		})
	},
	getLevel : function() {
		return this.map.getZoom();
	}
};