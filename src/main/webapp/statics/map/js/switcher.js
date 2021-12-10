
var cards = $(".mapTypeCard");
var len = cards.length;
var expand_width = len*98+10;
var width = (len-1)*5+10+88+4;
$(document).ready(function(){
	initMapWrapperWidth();
	cards.eq(len-1).siblings('.mapTypeCard').find("img").hide();
})


$(".mapTypeCard").click(function() {
	if(!$(this).hasClass('active')){
		$(this).addClass('active').siblings('.mapTypeCard').removeClass('active');
		 $(this).insertAfter($(".mapTypeCard:last"));	
		 cards = $(".mapTypeCard");
		 initMapWrapperWidth();
		// $(this).remove();
	}
});

//伸缩
$(".mapType-wrapper").hover(function() {
	//$(this).addClass('expand');
	$(".expand .mapType").width(expand_width);
	expandMapWrapper();
	cards.find("img").show();
}, function() {
 	initMapWrapperWidth();
	$(this).removeClass('expand');
	setTimeout(function(){
		cards.eq(len-1).siblings('.mapTypeCard');//.find("img").hide();
	},300) 
	
});

function initMapWrapperWidth(){
	$(".mapType").width(width);
	initMapWrapper();
}

function initMapWrapper(){	 
	$.each(cards,function(index){
		var opacity = 0.6-(len-index-1)*0.1;
		var alpha = opacity*100;
		if(index==len-1){
			$(this).css({"border-left":"1px solid rgba(153,153,153,.6)","z-index":len,"right":"10px"});
		}else{
			var right = (len-index-1)*5+10;
			var z_index = index+1;
			$(this).css({"right":right+"px","z-index":z_index,"background-color":"#999","opacity":opacity,"filter":"alpha(opacity="+alpha+")"});
		}
	})
}

function expandMapWrapper(){
	$.each(cards,function(index){
		if(index<len-1){
			var right = (len-index-1)*98+10;
			$(this).css({"right":right+"px","background-color":"#fff","opacity":1,"filter":"alpha(opacity=100)"});
			$(this).find("img").show();
		}
	})
}