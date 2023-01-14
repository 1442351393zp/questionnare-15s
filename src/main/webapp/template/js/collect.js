$(function(){
	// 获取用户Id
	var userId = $("#userId").val();
	// 点击展开题库
	$(".develop-collect").on("click",function(){
		if($(".collectList").hasClass("active")){
			$(".collectList").removeClass("active").find(".develop-collect span").text("展开题库");
			$(".main .questionnaire-panel").css({
				"width":"calc(80% - 62px)"
			})
		}else{
			$(".main .questionnaire-panel").css({
				"width":"calc(80% - 275px)"
			})
			$(".collectList").addClass("active").find(".develop-collect span").text("收起题库");
			
		}
	})
	// 点击关闭按钮
	$(".close-collect").on("click",function(){
		$(".collectList").removeClass("active").find(".develop-collect span").text("展开题库");
		$(".main .questionnaire-panel").css({
			"width":"calc(80% - 62px)"
		})
	})
	//点击收起题目
	// $(".title-item").on("click",function () {
	// 	//收起
	// 	if($(this).hasClass("collapsed")){
	// 		$(this).parent().parent().siblings(".panel-collapse").removeClass("collapse").addClass("in").css("height","auto")
	// 	}else{
	// 		$(this).parent().parent().siblings(".panel-collapse").addClass("collapse").removeClass("in").css("height","0")
	// 	}
	// })
	// // 查询题库标题
	// $.ajax({
	// 	url:"/questionnaire/template/data/collect.json",
	// 	method:"get",
	// 	dataType:"json",
	// 	success:function(res){
	// 		// console.log(res.people)
	// 		eachList(res.people,"people");
	// 		eachList(res.userPhone,"user-phone");
	// 		// eachList(res.internet,"internet");
	// 		// eachList(res.usageBackground,"product");
	// 		// eachList(res.satisfaction,"satisfied");
	// 		// eachList(res.mobile,"move-make");
	// 		function eachList(data,className){
	// 			for(var i = 0; i<data.length; i++){
	// 				var item = data[i]
	// 				var uli = "<li>"
	// 				+"<span title="+item.title+" >"+item.title+"</span>\n"
	// 				+"<i class='delete'></i>\n"
	// 				+"<i></i>\n"
	// 				+"</li>\n"
	// 				$("."+className).find(".collapseContent>ul").append(uli)
	// 			}
	// 		}
	// 	},
	// 	error:function(res){
	// 	}
	// })
	// 查询收藏列表
	$.ajax({
		url:"/questionnaire/answer/collectionList",
		method:"post",
		async:false,
		data:JSON.stringify({
			"userId":userId
		}),
		contentType:"application/json;charset=UTF-8",
		success:function(res){
			if(res.code == 0){
				// 遍历收藏数据
				for(var i = 0; i<res.collectList.length; i++){
					var item = res.collectList[i]
					// console.log(JSON.stringify(item))
					var item1 = JSON.stringify(item)
					var text = "<li>"
						+"<span class=\"ticpItem\" title="+item.topic+" collectId="+item.id+">"+item.topic+"</span>\n"
						+"<i class=\"deleteItem\" collectId="+item.id+"></i>\n"
						+"<i class=\"showMess\" collectId="+item.id+" data-toggle=\"popover\"></i>\n"
						+"</li>\n"
					$(".collectSubject").find(".collapseContent>ul").append(text)
				}
				// 遍历人口属性和联系方式
				for(var i =0; i<res.contentList.length; i++){
					var item= res.contentList[i]
					var uli = "<li>"
						+"<span class=\"ticpItem\" title="+item.topic+" collectId="+item.id+" type="+item.type+">"+item.topic+"</span>\n"
						+"<i class='delete'></i>\n"
						+"<i class=\"showMess\" collectId="+item.id+" data-toggle=\"popover\" type="+item.type+"></i>\n"
						+"</li>\n"
					if(item.type == 0){
						$(".people").find(".collapseContent>ul").append(uli)
					}else if(item.type == 1){
						$(".user-phone").find(".collapseContent>ul").append(uli)
					}

				}
			}
		}
	});
	// 点击标题添加
	$("#accordionPanels").on("click",".ticpItem",function(){
		var titleText = $(".editTitle").text().trim();
		var title = $(".editTitle").html();
		if(title && title.toString().length>10000){
			return alertTip("标题不可超过规定长度！")
		}
		var collectId = $(this).attr("collectId");
		var type = $(this).attr("type")
		$.ajax({
			url:"/questionnaire/answer/findCollection",
			method:"post",
			contentType:"application/json;charset=UTF-8",
            async:false,
			data:JSON.stringify({
				"id":collectId,
				"type":type
			}),
			success:function(res){
				// console.log(res)
				if(res.code == 0){
					var data = res.data
                    console.log($(".panel-page").length)
					for(var k=0;k<$(".panel-page").length; k++){
						var panelItem = $(".panel-page")[k];
						// 第一页
						if($(panelItem).css("display") == "block"){
							if($(panelItem).attr("data-pageindex") == 0){
								if($(panelItem).find(".reel-edit").length>0 || $(panelItem).find(".wrap-edit-content").length>2){
									alertTip("当前处于编辑状态")
									return false;
								}
								addItem(data,panelItem)
								// $(panelItem).append($("#subject-edit").html())
								for(var i =0; i<$(panelItem).find("#subjectType>option").length; i++){
									if($(this).attr("index") ==$(panelItem).find("#subjectType>option")[i].value){
										$($(panelItem).find("#subjectType>option")[i]).prop("selected",true)
									}
								}
								break
							}else{
								if($(panelItem).find(".reel-edit").length>0 || $(panelItem).find(".wrap-edit-content").length>0){
									alertTip("当前处于编辑状态")
									return false;
								}
								addItem(data,panelItem)
								for(var i =0; i<$(panelItem).find("#subjectType>option").length; i++){
									if($(this).attr("index") ==$(panelItem).find("#subjectType>option")[i].value){
										$($(panelItem).find("#subjectType>option")[i]).prop("selected",true)
									}
								}
							}
						}
					};

				}
			},
			error:function(){
				alert("请求失败")
			}
		})
	})
    // 遍历生成数据
    function addItem(data,panelItem){
        // console.log(data)
        var editBox = $("<div class=\"reel-edit\"></div>")
        //题目父容器
        var wrapQuestion = $("<div class='wrap-edit-content'></div>")
        //题目
        var question = $("<div class=\"question\"></div>")
        editBox.append(wrapQuestion)
        wrapQuestion.append(question)
        //问题题目
        var title = $("<div class=\"fieldset\">\n" +
            " <div class=\"label\">题目:</div>\n" +
            " <div class=\"editable\" contenteditable=\"true\" data-type=\"content\"\n" +
            "    onclick=\"editContent(event)\" id=\"topic\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\">"+data.topic+"</div>\n" +
            "</div>")
        question.append(title)
        //备注
        var remark = $(" <!--备注-->\n" +
            " <div class=\"fieldset\">\n" +
            "   <div class=\"label\">备注:</div>\n" +
            "   <div class=\"editable remark\" contenteditable=\"true\" data-type=\"content\"\n" +
            "   onclick=\"editContent(event)\" id=\"remark\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\">"+data.remark+"</div>\n" +
            "</div>")
        question.append(remark)

        var checkboxMust = "";//必选
        if (data.subjectType === "1") {
            var questionType = $("<!--题目类型切换-->\n" +
                " <div class=\"fieldset\">\n" +
                "      <label for=\"question-type\"></label>\n" +
                // "      <select id=\"subjectType\">\n" +
                // "            <option value=\"1\" selected>单选题</option>\n" +
                // "            <option value=\"2\">多选题</option>\n" +
                // "      </select>\n" +
				"       <div class=\"selectType\">"+
				"           <div class=\"show-title\">"+
				"               <span value=\"1\">单选题</span>"+
				"               <i></i>"+
				"           </div>"+
				"           <ul>"+
				"               <li value=\"1\">单选题</li>"+
				"               <li value=\"2\">多选题</li>"+
				"           </ul>"+
				"       </div>"+
                "</div>")
            if(data.must == "0"){
                checkboxMust = " <div class=\"required-item checkbox-primary\">\n" +
                    "      <input type=\"checkbox\" checked=\"checked\" id=\"must\"/>\n" +
                    "      <label>必填</label>\n" +
                    " </div>";
            }else{
                checkboxMust = " <div class=\"required-item checkbox-primary\">\n" +
                    "      <input type=\"checkbox\" id=\"must\"/>\n" +
                    "      <label>必填</label>\n" +
                    " </div>";
            }
            question.append(questionType);
            questionType.append(checkboxMust);
        } else {
            var questionType = $("<!--题目类型切换-->\n" +
                " <div class=\"fieldset\">\n" +
                "      <label for=\"question-type\"></label>\n" +
                // "      <select id=\"subjectType\">\n" +
                // "            <option value=\"1\">单选题</option>\n" +
                // "            <option value=\"2\" selected>多选题</option>\n" +
                // "      </select>\n" +
				"       <div class=\"selectType\">"+
				"           <div class=\"show-title\">"+
				"               <span value=\"2\">多选题</span>"+
				"               <i></i>"+
				"           </div>"+
				"           <ul>"+
				"               <li value=\"1\">单选题</li>"+
				"               <li value=\"2\">多选题</li>"+
				"           </ul>"+
				"       </div>"+
                "</div>")
            if(data.must == "0"){
                checkboxMust = " <div class=\"required-item checkbox-primary\">\n" +
                    "      <input type=\"checkbox\" checked=\"checked\" id=\"must\"/>\n" +
                    "      <label>必填</label>\n" +
                    " </div>";
            }else{
                checkboxMust = " <div class=\"required-item checkbox-primary\">\n" +
                    "      <input type=\"checkbox\" id=\"must\"/>\n" +
                    "      <label>必填</label>\n" +
                    " </div>";
            }
            question.append(questionType);
            questionType.append(checkboxMust);

        }
        //循环添加题目列表
        var optionsArr =data.optionList;
        // console.log(optionsArr)

        var options = "";
        for (var i = 0;i < optionsArr.length;i++) {
            var questionOption = "<div class=\"fieldset\">\n" +
                "   <div class=\"question-option-label\"></div>\n" +
                "   <div class=\"editable option optionIpt\" contenteditable=\"true\" data-placeholder=\"选项\" data-type=\"content\"\n" +
                "        onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\">"+optionsArr[i]+"</div>\n" +
                " <input class=\"optionsIdArr\" type=\"hidden\" value="+optionsArr[i].optionsId+">\n"+
                "   <span class=\"btn-delete-option\"></span>\n" +
                "</div>";
            options += questionOption;
        }
        var allOption = '<div class=\"all-option\">'+options+'</div>';
        question.append(allOption);
        //新建选项
        var newOption = $("<div class=\"fieldset\">\n" +
            "  <div class=\"label\"></div>\n" +
            "  <div class=\"btn-add-option\">新建选项</div>\n" +
            "</div></div>")
        question.append(newOption)
        //提交按钮
        var submit = $("<div class=\"fieldset-submit\">\n" +
            "   <div></div>\n" +
            "   <button type=\"button\" class=\"cancel-btn\">取消</button>\n" +
            "   <button class=\"button-active subjectSave\" id=\"subjectSave\">确定</button>\n" +
            " </div>")
        question.append(submit)
        $(panelItem).append(editBox)
    }
	// 点击删除收藏题目
	$(".collectSubject").on("click",".deleteItem",function(){
		var collectId = $(this).attr("collectId");
		// console.log(collectId)
		var self = $(this)
		$.ajax({
			url:"/questionnaire/answer/delCollection",
			method:"post",
			data:JSON.stringify({
				"userId":userId,
				"id":collectId
			}),
			contentType:"application/json;charset=UTF-8",
			success:function(res){
				if(res.code==0){
					self.parent().remove();
					alertTop("删除成功")
				}
			},
			error:function(){
				alert("请求失败")
			}
		})
	})
	// 鼠标悬浮显示信息
	$("#accordionPanels").on("mouseover",".showMess",function(){
		var collectId = $(this).attr("collectId");
		var type = $(this).attr("type")
		$(this).css({
			'background-position': '-371px -413px',
			'margin-left':'5px'
		})
		var self = $(this)
		$.ajax({
			url:"/questionnaire/answer/findCollection",
			method:"post",
			contentType:"application/json;charset=UTF-8",
			data:JSON.stringify({
				"id":collectId,
				"type":type
			}),
			success:function(res){
				var data = res.data
				var type= "";
				var content = ''
				if(data.subjectType == 1){
					type= "单选题"
				}else{
					type = "多选题"
				}
				for(var i = 0; i<data.optionList.length; i++){
					var text = '<div>'+data.optionList[i]+'</div>'
					content += text
				}
				self.popover({
					placement:"left",
					title:'<span>'+data.topic+'</span><span>['+type+']</span><span>('+data.remark+')</span>',
					content:content,
					html:true,
					template:"<div class='popover'><div class='arrow'></div><div class='popover-title'></div><div class='popover-content'></div></div>",
					show:true
				})
				self.popover("show")
			}
		})
	})
	// 鼠标移除隐藏
	$("#accordionPanels").on("mouseout",".showMess",function(){
		$(this).popover("hide")
		$(this).css({
			'background-position': '-405px -413px',
			'margin-left':'5px'
		})
	})
})

	