$(function(){
    // 获取rId
    var rId = $("#reelId").val();
    //获取皮肤
    $.ajax({
        url:"/questionnaire/reel/getBksetting",
        method:"post",
        contentType:"application/json;charset=UTF-8",
        data:JSON.stringify({
            rid:rId
        }),
        success:function(res){
            if(res.code == 0){
                var dataIndex = res.data
                getSkinJson(dataIndex)
            }
        }
    })
	$(".survey-main").eq(0).addClass("active")
    //灰条置顶
    var fiexdBarHeight = $(".survey-main .progress").offset().top;
    $(document).scroll(function(){
        if($(document).scrollTop() >= fiexdBarHeight){
            $(".survey-main .progress").addClass("active")
        }else{
            $(".survey-main .progress").removeClass("active")
        }
    });
    
    var progress = $("#pageNum").val();	//获取分页数
    var index_page = "";	// 判断当前是第几页
    if(index_page == ""){
    	index_page = 0
    }
    if(progress == 1){
    	$(".survey-main").find(".nextPage").removeClass("nextPage").addClass("jubject-submit").find("span").text("关闭")
    }
    showProgress(progress,index_page+1)
    //点击下一页
    $(".nextPage").on("click",function(){
    	// 计算出下一页的页码
    	index_page = parseInt($(this).attr("index-page"))+2;
    	if(index_page<progress){
    		$(this).parents(".survey-main").removeClass("active").next(".survey-main").addClass("active").find(".page-control .nextPage").children().text("下一页").parent().siblings(".prevPage").addClass("active");
    	}else{
    		$(this).parents(".survey-main").removeClass("active").next(".survey-main").addClass("active").find(".page-control .nextPage").removeClass("nextPage").addClass("jubject-submit").children().text("关闭").parent().siblings(".prevPage").addClass("active");
    	}
    	showProgress(progress,index_page)
    })
  //点击上一页
     $(".survey-container").on("click",".prevPage",function(){
    	 // 计算出上一页的页码
    	 index_page = parseInt($(this).attr("index-page"));
    	 if(index_page <= 1){
    		 $(this).parents(".survey-main").removeClass("active").prev(".survey-main").addClass("active").find(".page-control .prevPage").removeClass("active")
    	 }else{
    		 $(this).parents(".survey-main").removeClass("active").prev(".survey-main").addClass("active").find(".page-control .prevPage").addClass("active")
    	 }
    	 showProgress(progress,index_page)
     })
   // 处理进度条
   function showProgress(progress,index_page){
	   var progressWidth = (100 / progress)*index_page+ "%"
	    $(".s-prog-done").animate({
	    	width:progressWidth
	    })
   }
    // 点击关闭
    $(".survey-container").on("click",".jubject-submit",function(){
    	window.close()
    })
    // 获取所有皮肤颜色设置
    function getSkinJson(index){
        $.ajax({
            url:"/questionnaire/template/data/skin.json",
            method:"get",
            dataType:"json",
            async:false,
            cache:false,
            success:function(res){
                for(var i = 0; i<res.length; i++){
                    if(i == index){
                        $(".survey-page").css({
                            "background-color":res[i].backgroundAll,
                            "background-image":'url('+res[i].backgroundImage+')',
                            "background-repeat": "no-repeat",
                            "background-size": "100% 100%"
                        });
                        $(".progress-done").css("background-color",res[i].progressBg);
                        $(".btn-submit").css("background-color",res[i].btnBg);
                        $(".quote-l-bg").css("background-color",res[i].titleBgColor).find(".quote-l-drop").css({
                            "border-top-color": res[i].titleBgColor,
                            "border-left-color": res[i].titleBgColor
                        })
                        $(".pen-b,.quote-r-dot").css("background-color",res[i].titleBgColor)
                        // $(".skin-container .radio-primary input:checked+label").addClass("active")
                        // $(".skin-container .radio-primary input:checked+label").addClass("active")
                    }
                }
            }
        })
    }
    // 监听input框的输入值
    $(".completions").on("input",function(){
        var width = calcWidth($(this).val(),$(this).css("font-size"))
        console.log(width)
        if( width <= 25){
            width = 25
        }
        $(this).css("width",width+"px")
    })
    // 计算input框的宽度
    function calcWidth(text,size){
        var text = $('<pre>'+text+'</pre>').css({
            "display":"none",
            "font-size":size,
        });
        $("body").append(text);
        var widthLength = text.width();
        text.remove();
        return widthLength;
    }
    //根据input内容的值计算宽度
    for(var i = 0; i<$(".completions").length; i++){
       var item = $(".completions")[i];
       $("body").append($('<pre class="calcWidth">'+$(item).val()+'</pre>').css("display","none"))
       var width =$(".calcWidth").width();
       if(width < 25){
           width = 25
       }
       $(".calcWidth").remove();
       $(item).css("width",width)
    }
})
//阻止ctrl+s保存页面
document.onkeydown = function(e){
    if(e.ctrlKey && e.which == 83){
        return false
    }
}