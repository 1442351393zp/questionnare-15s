$(function(){
    // 获取卷ID 和答题者ID
    var reelId = $("#reelId").val();
    var receiverId = $("#receiverId").val();
    // 获取当前所在位置
    var num =$("#dataIndex").val()
    // 获取总的统计数
    var countPage = ""
    $.ajax({
        url:"/questionnaire/answer/getAllQuestions",
        method:"post",
        data:{
            "reelId":reelId,
            // "receiverid":receiverId
        },
        async:false,
        success:function(res){
            var data = res.data
            if(res.code == 0){
                for(var j = 0; j<data.length; j++){
                    countPage = data.length
                    var text = '<li>\n'
                               +    '<div class="content-main">'
                               +        '<div class="main-head">'
                               +            '<h1>基本信息</h1>'
                               +            '<p>答题人：'+data[j].receiverName+'</p>'
                               +            '<p>编号Id：'+data[j].answerRecordId+'</p>'
                               +            '<p>开始时间:<span class="startTime">'+data[j].startTime+'</span>结束时间:<span class="endTime">'+data[j].endTime+'</span></p>'
                               +        '</div>'
                               +        '<div class="main-container">'
                               +            '<h1>答题详情</h1>'
                               +            '<div class="question-list">'
                    for(var i = 0; i<data[j].questions.length; i++){
                        var item = data[j].questions[i];
                        // 单选题
                        if(item.subjectType == 1){
                            text += '<div class="question-item">\n'
                                +'<div class="question-item-title">\n'
                                +'   <span>'+parseInt(i+1)+'.</span>\n'
                                +'    <span>'+item.topic+'</span>\n'
                                if(item.mustItem == 0){
                                   text +=' <span>*</span>\n'
                                }

                                text += '<div class="question-desc">备注</div>\n'
                                +'</div>\n'
                                +'<div class="question-item-content">\n'
                            for(var k = 0; k<item.options.length; k++){
                                var option = item.options[k];
                                if(option.checked == 1){
                                    text += '     <div class="radio-primary radio-btn">\n'
                                        +'         <input type="radio" name="option'+data[j].answerRecordId+item.subjectid+i+'" id='+option.optionsid+' checked>\n'
                                        +'         <label>'+option.optionsValue+'</label>\n'
                                        +'     </div>\n'
                                }else{
                                    text += '     <div class="radio-primary radio-btn">\n'
                                        +'         <input type="radio" name="option'+data[j].answerRecordId+item.subjectid+i+'" id='+option.optionsid+' disabled>\n'
                                        +'         <label>'+option.optionsValue+'</label>\n'
                                        +'     </div>\n'
                                }

                            }
                            text += ' </div>'
                                +'</div>'
                        }else if(item.subjectType == 2){
                            text += '<div class="question-item">\n'
                                +'<div class="question-item-title">\n'
                                +'   <span>'+parseInt(i+1)+'.</span>\n'
                                +'    <span>'+item.topic+'</span>\n'
                            if(item.mustItem == 0){
                                text +=' <span>*</span>\n'
                            }
                                text+='    <div class="question-desc">备注</div>\n'
                                +'</div>\n'
                                +'<div class="question-item-content">\n'
                            for(var k = 0; k<item.options.length; k++){
                                var option = item.options[k];
                                if(option.checked == "1"){
                                    text += '     <div class="checkbox-primary checkbox-btn">\n'
                                        +'         <input type="checkbox" name="option'+data[j].answerRecordId+item.subjectid+i+'" id='+option.optionsid+' checked/>\n'
                                        +'         <label>'+option.optionsValue+'</label>\n'
                                        +'     </div>\n'
                                }else{
                                    text += '     <div class="checkbox-primary checkbox-btn">\n'
                                        +'         <input type="checkbox" name="option'+data[j].answerRecordId+item.subjectid+i+'" id='+option.optionsid+' disabled/>\n'
                                        +'         <label>'+option.optionsValue+'</label>\n'
                                        +'     </div>\n'
                                }
                            }
                            text += ' </div>'
                                +'</div>'
                        }else if(item.subjectType == 4){
                            debugger;
                            text += '<div class="question-item">\n'
                                +'<div class="question-item-title">\n'
                                +'   <span>'+parseInt(i+1)+'.</span>\n'
                                +'    <span>'+item.topic+'</span>\n'
                            if(item.mustItem == 0){
                                text +=' <span>*</span>\n'
                            }

                            text += '<div class="question-desc">备注</div>\n'
                                +'</div>\n'
                                +'<div class="question-item-content">\n'
                            for(var k = 0; k<item.options.length; k++){
                                var option = item.options[k];
                                text += '     <div class="box-single__line" style="margin-bottom:10px;">\n'
                                    +'<div class="fieldset"><div class="label"></div>\n'
                                    +'         <div class="editable optionIpt single-line-text" contenteditable="true" id="single-line-text"  data-placeholder="单行文本" data-type="content" data-option-type="SINGLE" style="padding: 10px; line-height: 20px; min-height: 40px;border: 1px solid #CCC;">\n'
                                    +option.optionsValue
                                    +'     </div>\n'
                                    +'     </div>\n'
                                    +'     </div>\n'
                            }
                            text += ' </div>'
                                +'</div>'
                        }
                    }
                     text+=     '</div>'
                               +        '</div>'
                               +    '</div>'
                               +'</li>'
                    $(".content-box>ul").append(text)
                }

            }
        }
    })
    if(countPage == 1){
        $(".next").css("display","none")
    }
    // 点击下一页
    $(".content-box ul").animate({
        "left":num*(-1200)+"px"
    })
    if(num == 0){
        $(".prev").css("display","none")
    }else if(num == countPage-1){
        $(".next").css("display","none")
    }
   $(".next").on("click",function(){
        num++
        console.log($(".content-box ul li").eq(num))
        if(num != 0){
            $(".prev").css("display","block")
        }
       if(num == $(".content-box ul li").length-1){
            $(".next").css("display","none")
        }
        $(".content-box ul").animate({
            "left":num*(-1200)+"px"
        })
   }) 
   $(".prev").on("click",function(){
        num--
        if(num <= 0){
            $(".prev").css("display","none")
            $(".next").css("display","block")
        }else{
            $(".next").css("display","block")
        }
        $(".content-box ul").animate({
            "left":num*(-1200)+"px"
        })
    });
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
        //获取字体大小
        var size = $(item).css("font-size");
        debugger
        $("body").append($('<pre class="calcWidth">'+$(item).val()+'</pre>').css({
            "display":"none",
            "font-size":size
        }))
        var width =$(".calcWidth").width();
        if(width < 25){
            width = 25
        }
        $(".calcWidth").remove();
        $(item).css("width",width)
    }
})