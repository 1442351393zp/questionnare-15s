function toSearch(ele,urlStr,formName,clazz){
		var title =$("#title").html();
		if(title == "" || title == "<br>"){
			$("#title").focus()
			return alertTip("标题为空");
		}
		var titleText = $(".editTitle").text().trim();
		if(title && title.toString().length>10000){
			return alertTip("标题不可超过规定长度！")
		}
		var showPageIndex = $(".tab-page-index.active").not(".end-tab").attr("data-index");
		if(showPageIndex == 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length>2){
			return alertTip("有未保存的题目")
		}else if(showPageIndex != 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length>0){
			return alertTip("有未保存的题目")
		}
		if(!!ele){
			$(ele).addClass("active").siblings().removeClass("active")
		}
		if(!urlStr||urlStr.length<2){
			location.href = '/';
			return;
		}
		if(!clazz){
			clazz="content";
		}
		$.ajax({
			type:'post',
			url:urlStr,
			data:$('#' + formName).serialize(),
			dataType:'text',
			async:false,
			success:function(msg){
				if(!!clazz){
					$('.'+clazz).empty();
					$('.'+clazz).append(msg);
				}
			},
			error:function(){
				//alert("load page error, page url is " + urlStr);
				alert("加载失败，请刷新页面")
			}
		});
}
function toSearchs(ele,urlStr,clazz){
	var title =$("#title").html();
	if(title == "" || title == "<br>"){
		$("#title").focus()
		return alertTip("标题为空");
	}
	var titleText = $(".editTitle").text().trim();
	if(title && title.toString().length>10000){
		return alertTip("标题不可超过规定长度！")
	}
	var showPageIndex = $(".tab-page-index.active").not(".end-tab").attr("data-index");
	if(showPageIndex == 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length>2){
		return alertTip("有未保存的题目")
	}else if(showPageIndex != 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length>0){
		return alertTip("有未保存的题目")
	}
	if(!!ele){
		$(ele).addClass("active").siblings().removeClass("active")
	}
	if(!urlStr||urlStr.length<2){
		location.href = '/';
		return;
	}
	if(!clazz){
		clazz="content";
	}
	$.ajax({
		type:'post',
		url:urlStr,
		dataType:'text',
		async:false,
		success:function(msg){
            if(!!clazz){
				$('.'+clazz).empty();
				$('.'+clazz).append(msg);
			}
		},
		error:function(){
			//alert("load page error, page url is " + urlStr);
			alert("加载失败，请刷新页面")
		}
	});
}
function reload(ele,urlStr,parms,clazz){
	if(!!ele){
		$(ele).addClass("active").siblings().removeClass("active")
	}
	if(!urlStr||urlStr.length<2){
		location.href = '/';
		return;
	}
	if(!clazz){
		clazz="content";
	}
	$.ajax({
		type:'post',
		url:urlStr,
		data:parms,
		dataType:'text',
		async:false,
		success:function(msg){
			if(!!clazz){
				$('.'+clazz).empty();
				$('.'+clazz).append(msg);
			}
		},
		error:function(){
			alert("load page error, page url is " + urlStr);
		}
	});
}
// 弹窗消息
function alertTip(text){
	var body = $(".stopPoint");
	body.append('<div class="alertTip">'
		+'<span>'+text+'</span>'
		+'</div>')
	//$(".alertTip").addClass("active").children().text(text);
	setTimeout(function(){
		$(".alertTip").remove();
	},1500)
}
// 暂缓开放提示框
function stopAlert(){
	var body = $(".stopPoint");
	body.append('<div class="stopAlert">此功能暂缓开放</div>')
	setTimeout(function(){
		$(".stopAlert").remove()
	},1500)
}
//弹窗消息提示
function alertTop(text){
	var body = $(".stopPoint");
	body.append('<div class="alertTop">'+text+'</div>')
	setTimeout(function(){
		$(".alertTop").remove();
	},1500)
}
//阻止ctrl+s保存页面
document.onkeydown = function(e){
	if(e.ctrlKey && e.which == 83){
		return false
	}
}
