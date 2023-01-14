$(function(){
    layui.use(['element','form', 'table', 'jquery','layer'], function() {
		var form = layui.form,
			table = layui.table,
			$ = layui.jquery,
			layer = layui.layer
            element = layui.element;
        // 初始化时间选择器
        $("#startTime").datetimepicker({
            language:'zh-CN',
            format:"yyyy-mm-dd",
            minView:2,
            autoclose:true,
        }).on("changeDate",function(event){
            $("#endTime").datetimepicker('setStartDate',event.date)
        })
        $("#endTime").datetimepicker({
            language:'zh-CN',
            format:"yyyy-mm-dd",
            minView:2,
            autoclose:true,
        }).on("changeDate",function(event){
            $("#startTime").datetimepicker('setEndDate',event.date)
        })
        var reelId
        var userId
        var answerId
        var headList = [
            {type: "checkbox",width: 50,fixed: "left"},
            {title: '查看',width: 80,fixed:true,templet:"#see"},
            {type: "numbers",title: '编号',width: 80,fixed: "true"},
        ];
		// var bodyList = [];
        $.ajax({
            url:"/questionnaire/question/listReceivedTableData",
            method:"post",
            async:false,
            data:{
                "reelId":rId,
                "pageSize":20
            },
            success:function(res){
                if(res.code == 0){
                    var res = res.data
                    $(".situation-title h1").text(res.title).next().text("回收量"+res.receivedCount)
                    for(var i = 0; i<res.head.length; i++){
                        var item = res.head[i];
                        if(item.display == 1){
                            headList.push({field:item.columnId,title:"题目："+item.columnName,templet:function(d){
                                var fieldName = this.field
                                if(d[fieldName] == undefined){
                                    d[fieldName] = ""
                                }
                                if(fieldName == "startTime" || fieldName == "endTime"){
                                    return d[fieldName]
                                }else{
                                   // return '<span>'+d[fieldName]+'</span><span class="edit" reelId='+d.reelId+' userId='+d.userId+' answer='+this.field+'></span>'
                                    return '<span>'+d[fieldName]+'</span>'
                                }
                        }})
                        }
                    }
                }
            }
        })
        // 遍历表格数据
        table.render({
            elem: '#reclaim-list',
            even:true,
            method:"post",
            url:"/questionnaire/question/listReceivedTableData",
            size:"lg",
            async:false,
            where:{
                "reelId":rId,
            },
            cols: [headList],
            // data:bodyList,
            limits: [20, 30, 50],
            limit: 20,
            page: {
                layout:['limit'],
                curr:1
            },
            request: {
                pageName: 'page', // 页码的参数名称，默认： page
                limitName: 'pageSize' // 每页数据量的参数名，默认： limit
            },
            parseData:function(res){
                //debugger
                var bodyList = [];
                if(res.code == 0){
                    for(var i = 0; i<res.data.body.length; i++){
                        var list = res.data.body[i]
                        var listItem = {}
                        bodyList.push(listItem)
                        for(var k = 0; k<list.length; k++){
                            var item = list[k]
                            listItem[item.columnId] = item.columnValueName
                        }
                    }
                }
                return {
                    "code":res.code,
                    "msg":res.msg,
                    "count":res.data.receivedCount,
                    "data":bodyList
                }
            },
            done:function(){
                // console.log("数据渲染完成")
                for(var i = 0; i<$(".reclaim-content td").length; i++){
                    var item = $(".reclaim-content td")[i];
                    if($(item).text().indexOf(">") != -1){
                        var text = $(item).html().substring($(item).html().indexOf(";")+1);
                        console.log(text)
                        $(item).html(text)
                    }
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
                //$(".reclaim-content td").removeAttr("data-content")
            }

        });
        // 点击编辑答案
        $(".reclaim-content").on("click",".edit",function(){
       // $(".edit").on("click",function(){
            reelId = $(this).attr("reelId");
            userId = $(this).attr("userId");
            answerId = $(this).attr("answer");
            // console.log(reelId,userId,answerId)
            $("#editBox").modal()
        })
        // 点击继续编辑
        $(".continue-edit").on("click",function(){
            // 关闭编辑提示弹出框
            $("#editBox").modal('toggle');
            $.ajax({
                url:"/questionnaire/answer/getSubjectAnswer",
                method:"get",
                // contentType:"application/json;charset=UTF-8",
                data:{
                    "reelId":reelId,
                    "subjectId":answerId,
                    "receiverid":userId
                },
                success:function(res){
                    if(res.code == 0){
                        $("#uploadAnswer").modal().find(".question-item-upload").empty();
                        var uploadItem = res.data[0].questions[0];
                        var text = '<div class="question-item-title">\n' +
                                   '     <span></span>\n' +
                                   '     <span>'+uploadItem.topic+'</span>\n' +
                                   '     <span>*</span>\n' +
                                   '<div class="question-desc">'+uploadItem.remark+'</div>'+
                                   '</div>\n' +
                                   '<div class="question-item-content">\n' +
                                   '</div>'
                        $(".question-item-upload").append(text);
                        var optionType = uploadItem.subjectType
                        for(var i = 0; i<uploadItem.options.length; i++){
                            var item = uploadItem.options[i]
                            if(optionType == 2){
                                if(item.checked == 1){
                                    var optionItem = '  <div class="checkbox-primary checkbox-btn">\n' +
                                        '    <input type="checkbox"  name="answer" value='+item.optionsid+' checked>\n' +
                                        '    <label for="">'+item.optionsValue+'</label>\n' +
                                        '</div>'
                                }else{
                                    var optionItem = '  <div class="checkbox-primary checkbox-btn">\n' +
                                        '    <input type="checkbox"  name="answer" value='+item.optionsid+'>\n' +
                                        '    <label>'+item.optionsValue+'</label>\n' +
                                        '</div>'
                                }
                            }else if(optionType == 1){
                                if(item.checked == 1){
                                    var optionItem = '  <div class="radio-primary radio-btn">\n' +
                                        '    <input type="radio"  name="answer" value='+item.optionsid+' checked>\n' +
                                        '    <label>'+item.optionsValue+'</label>\n' +
                                        '</div>'
                                }else{
                                    var optionItem = '  <div class="radio-primary radio-btn">\n' +
                                        '    <input type="radio"  name="answer" value='+item.optionsid+'>\n' +
                                        '    <label>'+item.optionsValue+'</label>\n' +
                                        '</div>'
                                }
                            }
                            $(".question-item-upload .question-item-content").append(optionItem)
                        }
                    }
                }
            })
        })
        // 修改答案后保存
        $(".saveAnswer").on("click",function(){
            var optionIds = []
            var optionsId = $("#uploadAnswer .questionnaire input[name=answer]:checked")
            for(var i =0; i<optionsId.length; i++){
                optionIds.push($(optionsId[i]).val())
            }
            $.ajax({
                url:"/questionnaire/answer/updateOptions",
                method:"post",
                contentType:"application/json;charset=UTF-8",
                data:JSON.stringify({
                    "reelId":reelId,
                    "subjectId":answerId,
                    "receiverId":userId,
                    "optionsId":optionIds
                }),
                success:function (res) {
                    if(res.code == 0){
                        $("#uploadAnswer").modal('toggle')
                        table.reload("reclaim-list")
                    }
                }
            })
        })
        // 点击删除
        $(".delete").on("click",function () {
            var checkUser = table.checkStatus('reclaim-list');
            if (checkUser.data.length < 1) {
                return layer.msg("您还没有选中数据")
            }
            var Id = [];
            for(var i = 0; i<checkUser.data.length; i++){
                var item = checkUser.data[i];
                Id.push(item.answerRecordId)
            }
            $.ajax({
                url:"/questionnaire/question/delReelStatistics",
                method:"post",
               // contentType:"application/json;charset=UTF-8",
                data:{
                    "reelAnswerRecordIds":Id
                },
                success:function (res) {
                    table.reload("reclaim-list")
                    alertTop("删除成功")
                }
            })
        })
        // 点击查看按钮
        table.on('tool(reclaim-list)', function (obj) {
            var index = $(obj.tr).index()
            var data = obj.data;
            var layEvent = obj.event;
            if(layEvent == "see"){
                var title= $(".situation-title").find("h1").text()
                window.open("/questionnaire/answer/getQuestionPage?reelId="+data.reelId+"&receiverid="+data.userId+"&title="+title+"&dataIndex="+index)
            }
        })
    })
})
