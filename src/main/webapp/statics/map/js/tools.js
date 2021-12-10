(function() {
    $(".m-map-tools-menu li").click(function(event) {
        var e = event || window.event;
        e.stopPropagation();
        if (!$(this).parent().hasClass("m-map-tools-list")) {
            $(this).toggleClass("active");
            var index = $(this).index();
            if ($(this).hasClass("active") && index != 3 && $(".m-map-tools-menu li").eq(3).hasClass("active")) {
                $(".m-map-tools-menu li").eq(3).removeClass("active");
            }
            if(index==0){
                if ($(this).hasClass("active")) {
                    $(".m-map-panel").eq(0).css({ "height": "100%", "margin-bottom": "10px" });
                    $(".m-map-panel").eq(1).css({ "height": "0", "margin-bottom": "0" });
                    $(".m-map-tools-menu li").eq(1).removeClass("active");
                } else {
                    $(".m-map-panel").eq(0).animate({ "height": "0", "margin-bottom": "0" }, 'fast', 'linear');
                }}else if(index==1){
                    if ($(this).hasClass("active")) {
                        $(".m-map-panel").eq(1).css({ "height": "100%", "margin-bottom": "10px" });
                        $(".m-map-panel").eq(0).css({ "height": "0", "margin-bottom": "0" });
                        $(".m-map-tools-menu li").eq(0).removeClass("active");
                    } else {
                        $(".m-map-panel").eq(0).animate({ "height": "0", "margin-bottom": "0" }, 'fast', 'linear');
                    }}
        } else {
            $(this).parents("li").removeClass("active");
        }
    })

    $(".m-map-panel .btn-close").click(function() {
        var $parent = $(this).parent(".m-map-panel");
        var index = $parent.index();
        $(".m-map-tools-menu li").eq(index).removeClass("active");
        $parent.animate({ "height": "0", "margin-bottom": "0" }, 'fast', 'linear');
    })
}())