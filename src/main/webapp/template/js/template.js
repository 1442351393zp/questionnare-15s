$(function(){
    var clickConId='';
    var reelId = "";
    // 渲染页面
    $.ajax({
        url:"/questionnaire/template/choiceTemplate",
        method:"post",
        success:function(res){
            if(res.code == 0){
                for(var i = 0; i<res.data.length; i++){
                    var item  = res.data[i];
                    var text = '<ul>\n'
                            +' <li class="category-title">\n'
                            +'      <i></i>\n'
                            +'      '+item.typename+'\n'
                            +' </li>\n'
                    for(var k = 0; k<item.children.length; k++){
                        var item1 = item.children[k];
                        text += ' <li class="category-item" id="'+item1.typeid+'">\n'
                                +'     <a href="javascript:;">\n'
                                +'         <span>'+item1.typename+'</span>\n'
                                +'         <span></span>\n'
                                +'     </a>\n'
                                +'</li>\n'
                    }
                    text += '</ul>'
                    $(".content-left").append(text);
                    if(i == 0){
                        saveClickData(item.children[0].typeid);
                    }
                    clickConId = item.children[0].typeid;
                }
                // 左侧分类点击事件
                var categoryItem = $('.content-left ul li.category-item');
                for(var i=0;i<categoryItem.length;i++){
                    categoryItem[i].onclick=function(){
                        $('.contentRightConBot').html('')
                        var typeId=$(this).attr('id');
                        clickConId = typeId
                        saveClickData(typeId);
                    }
                }
            }
        }
    })
    // 获取左侧分类数据
    function saveClickData(id){
        $.ajax({
            type:'post',
            url:"/questionnaire/template/templatelists",
            data:{
                typeid:id
            },
            async:false,
            success:function(res){
                var data = res.data;
                getList(data)
            }
        })
    }
    // 获取分类子集数据
    function saveClickliData(id){
        reelId= id
        $.ajax({
            type:'post',
            url:"/questionnaire/template/get_template",
            data:{
                reelId:id
            },
            success:function(data){
                var data = data.data;
                var conData='';
                if(data!==undefined && data!==null){
                    for(var i=0;i<data.length;i++){
                        var item = data[i]
                        for(var k = 0; k<item.pageList.length; k++){
                            var pageItem = item.pageList[k];
                            for(var j = 0; j<pageItem.subjectList.length;j++){
                                conData+='<div class="topicItem">'+parseInt(j+1)+'.'+pageItem.subjectList[j].topic+'</div>'
                            }
                            if(k != item.pageList.length-1){
                                conData += '<div>===分页===</div>'
                            }
                        }
                        //conData+='<div class="topicItem">'+parseInt(i+1)+'.'+data[i].topic+'</div>'
                    }
                }
                $('.contentRightConBot').html(conData).parent().next(".submitModel").css("display","block");
            }
        })
    }
    // 点击保存
    $('.submitModel').on('click',function () {
        $.ajax({
            type:'post',
            url:"/questionnaire/template/editTemplate",
            data:{
                reelid: reelId
            },
            success:function(data){
                var rId = data.data
                toSearchs(this,'/questionnaire/reel/main1?rId='+rId,'contentMain');
            }
        })
    })
    //点击搜索
    $(".searchBtn").on("click",function(){
        var title = $(".searchIpt").val();
        if(title == ""){
            alertTop("请输入搜索内容")
            return
        }
        $.ajax({
            url:"/questionnaire/template/template_name",
            method:"post",
            data:{
                title:title
            },
            success:function(res){
                var data = res.data
                if(res.code == 0){
                    getList(data)
                    $(".searchIpt").val("")
                }
            }
        })
    })
    //获取模板列表
    function getList(data){
        var html='';
        if(data!==undefined && data!==null && data.length != 0){
            for(var i=0;i<data.length;i++){
                html+='<li id="'+data[i].rId+'" name="'+data[i].title+'">'+data[i].title+'</li>'
            }
            $('.content-center>ul').html(html);
            $('.content-center>ul li').eq(0).addClass('active');
            saveClickliData(data[0].rId);
            $('.contentRightConTitle').html(data[0].title)
            //分页
            $(".holder").jPages({
                containerID:"itemContainer",
                previous:"上一页",
                next:"下一页",
                perPage:10,
                callback:function(){

                }
            })
            // li标签点击事件
            var liCon = $('.content-center ul li');
            for(var j=0;j<liCon.length;j++){
                liCon[j].onclick=function(){
                    var id=$(this).attr('id');
                    $(this).siblings().removeClass('active');
                    $(this).addClass('active');
                    var typeName=$(this).attr('name');
                    $('.contentRightConTitle').html(typeName)
                    saveClickliData(id)
                }
            }
        }else{
            $('.content-center>ul').html("<div class='point'>暂无数据</div>");
            $(".holder").empty();
            $(".contentRightConTitle").empty().next().empty().html("<div class='point'>暂无数据</div>");
            $(".submitModel").css("display","none");
        }
    }

})
