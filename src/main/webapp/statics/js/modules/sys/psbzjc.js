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
	  Y:""
  },
  mounted() {//页面加载完成后执行
  },
  watch: {
	  X(val){//数据变化时，执行
		  this.getIndex2(this.SITEID,this.X,this.Y);
        }
  },
  methods:{//init() { console.log('test')},
	  getIndex2:function(SITEID,X,Y){
		  console.log(SITEID);
		  console.log(X);
		  $(".menu-item-2 a").css("color","#363636");
		  $(".menu-item-2 a").css("border-right","#3c8dbc 0px solid");
		  vm.SITEID=SITEID;//泵站ID
		  $("#"+SITEID).css("color","#3c8dbc");
		  $("#"+SITEID).css("border-right","#3c8dbc 2px solid");
		  $("#"+SITEID).css("text-decoration","none");
		  map.zoomTo(2);
		  map.centerAt( {"x":X, "y":Y, "spatialReference": {"wkid": 4326 }});
		  },
	  psbzjc_detail:function(bzId,bzName,parentId){
		  console.log(bzId);
		  console.log(bzName);
		  window.location.href=encodeURI("psbzjc_detail.html?id="+bzId+"&name="+bzName+"&parentId="+parentId);
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
	var beng={'type':'P01'};
	  // 获取片区下的排水泵站
	  $.ajax({
		  type: "POST",
		  url: "../../sys/site/queryfpSite2",
		  contentType: "application/json",
		  data: JSON.stringify(beng),
		  async:false,
		  success: function(bengStationList){
			  bengStationList=JSON.parse(bengStationList);
			  // 遍历排水泵站
			  for(var i=0;i<bengStationList.length;i++){
				  var bengStation={
	    				  name:bengStationList[i].name,
	    				  siteId:bengStationList[i].siteId,
	    				  x:bengStationList[i].x,
	    				  y:bengStationList[i].y,
	    				  areaId:bengStationList[i].areaId,
	    				  fault:false,
	    				  stop:false,
	    				  run:true,
	    				  beng:[],
	    				  jlj:"",
	    				  ins:"",    				
	    				  cod:"",
	    				  ph:"",
	    				  tn:"",
	    				  nh4:"",
	    				  tp:"",
	    				  ss:""
		    		  };
				  listpParam+=bengStation.siteId+",FLC;"+bengStation.siteId+",FLS;"+bengStation.siteId+",COD;"+bengStation.siteId+",PH;"+bengStation.siteId+",TN;"+bengStation.siteId+",TP;"+bengStation.siteId+",SS;";
				  vm.bengs.push(bengStation);
				  $.ajax({//请求子点位
					    type: "POST",
					    url: baseURL + "sys/site/queryParentSite",
					    contentType: "application/json",
					    data: JSON.stringify(bengStation.siteId),
					    async:false,
					    success: function(r){
					    	r=JSON.parse(r);
					    	vm.bengs[i].beng=[];
					        for(var m=0;m<r.length; m++){
					        	if(r[m].type=="S14"){//泵
					        		var bengs_beng={
					        			name:r[m].name,
					        			siteId:r[m].siteId,
					        			parentId:r[m].parentId,
					        			state:"0"
					        		}
					        		vm.bengs_beng.push(bengs_beng);
					        		vm.bengs[i].beng.push(bengs_beng);
					        		inlineData+=r[m].siteId+",Fault;"+r[m].siteId+",STOP;"+r[m].siteId+",RUN;"+r[m].siteId+",Mode;";
					        		console.log(inlineData);
					        	}else if(r[m].siteId.indexOf("CK")!=-1){//出口
					        		var bengs_site={
					        			name:r[m].name,
					        			siteId:r[m].siteId,
					        			parentId:r[m].parentId,				        			
					        		}
					        		console.log(bengs_site);
					        		vm.bengs_site.push(bengs_site);
					        		vm.bengs[i].ckId=r[m].siteId;
					        		inlineData+=r[m].siteId+",FLC;"+r[m].siteId+",FLS;"+r[m].siteId+",COD;"+r[m].siteId+",PH;"+r[m].siteId+",TN;"+r[m].siteId+",NH4;"+r[m].siteId+",TP;"+r[m].siteId+",SS;";
					        		console.log(inlineData);
					        	}
					        }
					    }
					}); 
			  }
		  }
	  });
	  console.log(vm.beng);
	  console.log(vm.treeList);

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
			    	//send(listpParam);
			    	send(inlineData);
			    	//var obj = "21-WB-CB0101,Fault;21-WB-CB0101,STOP;21-WB-CB0101,RUN;21-WB-CB0101,Mode;21-WB-CB0102,Fault;21-WB-CB0102,STOP;21-WB-CB0102,RUN;21-WB-CB0102,Mode;21-WB-CB0103,Fault;21-WB-CB0103,STOP;21-WB-CB0103,RUN;21-WB-CB0103,Mode;21-WB-CB01CK,FLC;21-WB-CB01CK,FLS;21-WB-CB01CK,PTOUT;21-WB-CB01CK,TUR;21-WB-CB01CK,PH;21-WB-CB01CK,HOCL;22-WB-CB0201,Fault;22-WB-CB0201,STOP;22-WB-CB0201,RUN;22-WB-CB0201,Mode;22-WB-CB0202,Fault;22-WB-CB0202,STOP;22-WB-CB0202,RUN;22-WB-CB0202,Mode;22-WB-CB0203,Fault;22-WB-CB0203,STOP;22-WB-CB0203,RUN;22-WB-CB0203,Mode;22-WB-CB02CK,FLC;22-WB-CB02CK,FLS;22-WB-CB02CK,PTOUT;22-WB-CB02CK,TUR;22-WB-CB02CK,PH;22-WB-CB02CK,HOCL;23-WB-GYWB01,Fault;23-WB-GYWB01,STOP;23-WB-GYWB01,RUN;23-WB-GYWB01,Mode;23-WB-GYWB03,Fault;23-WB-GYWB03,STOP;23-WB-GYWB03,RUN;23-WB-GYWB03,Mode;23-WB-GYWB02,Fault;23-WB-GYWB02,STOP;23-WB-GYWB02,RUN;23-WB-GYWB02,Mode;23-WB-GYWBCK,FLC;23-WB-GYWBCK,FLS;23-WB-GYWBCK,PTOUT;23-WB-GYWBCK,TUR;23-WB-GYWBCK,PH;23-WB-GYWBCK,HOCL;24-WB-LPWB01,Fault;24-WB-LPWB01,STOP;24-WB-LPWB01,RUN;24-WB-LPWB01,Mode;24-WB-LPWB02,Fault;24-WB-LPWB02,STOP;24-WB-LPWB02,RUN;24-WB-LPWB02,Mode;24-WB-LPWB03,Fault;24-WB-LPWB03,STOP;24-WB-LPWB03,RUN;24-WB-LPWB03,Mode;24-WB-LPWBCK,FLC;24-WB-LPWBCK,FLS;24-WB-LPWBCK,PTOUT;24-WB-LPWBCK,TUR;24-WB-LPWBCK,PH;24-WB-LPWBCK,HOCL;03-1-WB-TZH01,Fault;03-1-WB-TZH01,STOP;03-1-WB-TZH01,RUN;03-1-WB-TZH01,Mode;03-1-WB-TZH02,Fault;03-1-WB-TZH02,STOP;03-1-WB-TZH02,RUN;03-1-WB-TZH02,Mode;03-1-WB-TZH03,Fault;03-1-WB-TZH03,STOP;03-1-WB-TZH03,RUN;03-1-WB-TZH03,Mode;03-1-WB-TZHCK,FLC;03-1-WB-TZHCK,FLS;03-1-WB-TZHCK,PTOUT;03-1-WB-TZHCK,TUR;03-1-WB-TZHCK,PH;03-1-WB-TZHCK,HOCL;"
					var obj = "21-WB-CB0101,Fault;21-WB-CB0101,STOP;21-WB-CB0101,RUN;21-WB-CB0101,Mode;21-WB-CB0102,Fault;21-WB-CB0102,STOP;21-WB-CB0102,RUN;21-WB-CB0102,Mode;21-WB-CB0103,Fault;21-WB-CB0103,STOP;21-WB-CB0103,RUN;21-WB-CB0103,Mode;21-WB-CB01CK,FLC;21-WB-CB01CK,FLS;21-WB-CB01CK,PTOUT;21-WB-CB01CK,TUR;21-WB-CB01CK,PH;21-WB-CB01CK,HOCL;22-WB-CB0201,Fault;22-WB-CB0201,STOP;22-WB-CB0201,RUN;22-WB-CB0201,Mode;22-WB-CB0202,Fault;22-WB-CB0202,STOP;22-WB-CB0202,RUN;22-WB-CB0202,Mode;22-WB-CB0203,Fault;22-WB-CB0203,STOP;22-WB-CB0203,RUN;22-WB-CB0203,Mode;22-WB-CB02CK,FLC;22-WB-CB02CK,FLS;22-WB-CB02CK,PTOUT;22-WB-CB02CK,TUR;22-WB-CB02CK,PH;22-WB-CB02CK,HOCL;23-WB-GYWB01,Fault;23-WB-GYWB01,STOP;23-WB-GYWB01,RUN;23-WB-GYWB01,Mode;23-WB-GYWB03,Fault;23-WB-GYWB03,STOP;23-WB-GYWB03,RUN;23-WB-GYWB03,Mode;23-WB-GYWB02,Fault;23-WB-GYWB02,STOP;23-WB-GYWB02,RUN;23-WB-GYWB02,Mode;23-WB-GYWBCK,FLC;23-WB-GYWBCK,FLS;23-WB-GYWBCK,PTOUT;23-WB-GYWBCK,TUR;23-WB-GYWBCK,PH;23-WB-GYWBCK,HOCL;24-WB-LPWB01,Fault;24-WB-LPWB01,STOP;24-WB-LPWB01,RUN;24-WB-LPWB01,Mode;24-WB-LPWB02,Fault;24-WB-LPWB02,STOP;24-WB-LPWB02,RUN;24-WB-LPWB02,Mode;24-WB-LPWB03,Fault;24-WB-LPWB03,STOP;24-WB-LPWB03,RUN;24-WB-LPWB03,Mode;24-WB-LPWBCK,FLC;24-WB-LPWBCK,FLS;24-WB-LPWBCK,PTOUT;24-WB-LPWBCK,TUR;24-WB-LPWBCK,PH;24-WB-LPWBCK,HOCL;03-1-WB-TZH01,Fault;03-1-WB-TZH01,STOP;03-1-WB-TZH01,RUN;03-1-WB-TZH01,Mode;03-1-WB-TZH02,Fault;03-1-WB-TZH02,STOP;03-1-WB-TZH02,RUN;03-1-WB-TZH02,Mode;03-1-WB-TZH03,Fault;03-1-WB-TZH03,STOP;03-1-WB-TZH03,RUN;03-1-WB-TZH03,Mode;03-1-WB-TZHCK,FLC;03-1-WB-TZHCK,FLS;03-1-WB-TZHCK,PTOUT;03-1-WB-TZHCK,TUR;03-1-WB-TZHCK,PH;03-1-WB-TZHCK,HOCL;";
			    	//var obj = "21-WB-CB01,FLC;21-WB-CB01,FLS;21-WB-CB01,COD;21-WB-CB01,PH;21-WB-CB01,TN;21-WB-CB01,TP;21-WB-CB01,SS;22-WB-CB02,FLC;22-WB-CB02,FLS;22-WB-CB02,COD;22-WB-CB02,PH;22-WB-CB02,TN;22-WB-CB02,TP;22-WB-CB02,SS;23-WB-GYWB,FLC;23-WB-GYWB,FLS;23-WB-GYWB,COD;23-WB-GYWB,PH;23-WB-GYWB,TN;23-WB-GYWB,TP;23-WB-GYWB,SS;24-WB-LPWB,FLC;24-WB-LPWB,FLS;24-WB-LPWB,COD;24-WB-LPWB,PH;24-WB-LPWB,TN;24-WB-LPWB,TP;24-WB-LPWB,SS;03-1-WB-TZH,FLC;03-1-WB-TZH,FLS;03-1-WB-TZH,COD;03-1-WB-TZH,PH;03-1-WB-TZH,TN;03-1-WB-TZH,TP;03-1-WB-TZH,SS;02-1-WB-LF,FLC;02-1-WB-LF,FLS;02-1-WB-LF,COD;02-1-WB-LF,PH;02-1-WB-LF,TN;02-1-WB-LF,TP;02-1-WB-LF,SS;"; 
					//send(obj);
			    	//alert(bzId);
			    	//alert(vm.SITEID);
			    },100)
})