$(function(){
    // 获取左侧导航栏
    $.ajax({
        url:"/questionnaire/template/data/skin.json",
        method:"get",
        dataType:"json",
        async:false,
        cache:false,
        success:function(res){
            console.log(res)
            var text = ""
            for(var i = 0; i<res.length; i++){
                text += '<li>\n'
                 +' <div class="skin-image" style="background-image: url('+res[i].navImage+')"></div>\n'
                 + '<div class="skin-title">'+res[i].navTitle+'</div>\n'
                 +'</li>'
            }
            $(".skin-navbar ul").append(text)
        }
    })
    // 获取卷Id
    var rId = $(".rId").val()
    //获取皮肤
    $.ajax({
        url:"/questionnaire/reel/getBksetting",
        method:"post",
        contentType:"application/json;charset=UTF-8",
        data:JSON.stringify({
            rid:rId
        }),
        success:function(res){
            console.log(res)
            if(res.code == 0){
                var dataIndex = res.data
                getSkinJson(dataIndex)
            }
        }
    })
    // 点击切换皮肤
    $(".skin-navbar>ul>li").click(function(){
        $(this).find(".skin-image").addClass("active").parent().siblings().find(".skin-image").removeClass("active")
        var index = $(this).index()
        getSkinJson(index)
        $.ajax({
            url:"/questionnaire/reel/bksetting",
            method:"post",
            contentType:"application/json; charset=UTF-8",
            data:JSON.stringify({
                rid:rId,
                bksetting:index
            }),
            success:function(res){
                console.log(res)
                if(res.code == 0){
                    alertTop("修改成功")
                }
            }
        })
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
                        $(".skin-navbar>ul>li").eq(index).find(".skin-image").addClass("active")
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
})