$(function(){
	var startTime = new Date();
	var stop = $("#stop").val()
	var answerflag = $("#answerflag").val()//0 设置智能答一次,并且已经答过题
	// if(stop == 0){
	// 	alertTop("此问卷已经暂停回收")
	// 	// $(".survey-container").find(".jubject-submit").attr(disabled,true)
	// }
    if(answerflag == 0){
        alertTop("您已填写过此问卷")
        //$(".survey-container").find(".jubject-submit").attr(disabled,true)
    }
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
    showProgress(progress,index_page+1)
    if(progress == 1){
        if(answerflag == 0){
            $(".survey-main").find(".nextPage").removeClass("nextPage").addClass("jubject-submit").children().text("提交").parent().attr("disabled",true).addClass("active");
        }else if(stop == 0){
			alertTop("此问卷已经暂停回收")
			$(".survey-main").find(".nextPage").removeClass("nextPage").addClass("jubject-submit").children().text("提交").parent().attr("disabled",true).css("background","#ccc");
		}else {
            $(".survey-main").find(".nextPage").removeClass("nextPage").addClass("jubject-submit").find("span").text("提交")
        }
    }

	/***
	 * 点击下一页
	 */
	$(".nextPage").on("click",function(){
		// 判断当前页是否所有题目都选中
		var items = $(this).parents(".survey-main").find(".question-item");
		for(var i = 0; i<items.length; i++){
			var item = $(items)[i]
			if($(item).find(".question-item-content").find("input:checked").val() == undefined){
				//判断是否必填
				if(!$(item).find(".question-item-title span:nth-child(6)").hasClass("active")){
					if ($(item).find(".question-item-content").find("input")[0].dataset.questionType !== "4" ){
						$(item).find(".point").css("display","inline-block");
					}

					$(document).scrollTop($(item).find(".point").offset().top - 35);
					//return false
				}
			}else{

				$(item).find(".point").css("display","none");
			}
		}
    	// 计算出下一页的页码
    	index_page = parseInt($(this).attr("index-page"))+2;
    	if(index_page<progress){
    		$(this).parents(".survey-main").removeClass("active").next(".survey-main").addClass("active").find(".page-control .nextPage").children().text("下一页").parent().siblings(".prevPage").addClass("active");
    	}else if(index_page == progress){
    		if(answerflag == 0){
                $(this).parents(".survey-main").removeClass("active").next(".survey-main").addClass("active").find(".page-control .nextPage").removeClass("nextPage").addClass("jubject-submit").attr("disabled",true).css("background","#ccc").children().text("提交").parent().siblings(".prevPage").addClass("active");
            }else {
                if(stop == 0){
                    $(this).parents(".survey-main").removeClass("active").next(".survey-main").addClass("active").find(".page-control .nextPage").removeClass("nextPage").addClass("jubject-submit").attr("disabled",true).css("background","#ccc").children().text("提交").parent().siblings(".prevPage").addClass("active");
                }else if (stop == undefined ) {
                    $(this).parents(".survey-main").removeClass("active").next(".survey-main").addClass("active").find(".page-control .nextPage").removeClass("nextPage").addClass("jubject-submit").attr("disabled",true).css("background","#ccc").children().text("提交").parent().siblings(".prevPage").addClass("active");
                }else {
                    $(this).parents(".survey-main").removeClass("active").next(".survey-main").addClass("active").find(".page-control .nextPage").removeClass("nextPage").addClass("jubject-submit").children().text("提交").parent().siblings(".prevPage").addClass("active");
                }
            }
		}
    	if($(this).hasClass("jubject-submit")){
    		return
		}else{
			showProgress(progress,index_page)
		}
    })

	/**
	 * 点击上一页
	 */
     $(".survey-container").on("click",".prevPage",function(){
    	 // 计算出上一页的
		 // +页码
    	 index_page = parseInt($(this).attr("index-page"));
    	 if(index_page <= 1){
    		 $(this).parents(".survey-main").removeClass("active").prev(".survey-main").addClass("active").find(".page-control .prevPage").removeClass("active")
    	 }else{
    		 $(this).parents(".survey-main").removeClass("active").prev(".survey-main").addClass("active").find(".page-control .prevPage").addClass("active")
    	 }
    	 showProgress(progress,index_page)
     })

	/**
	 * 处理进度条
	 * @param progress
	 * @param index_page
	 */
   function showProgress(progress,index_page){
	   var progressWidth = (100 / progress)*index_page+ "%"
	    $(".s-prog-done").animate({
	    	width:progressWidth
	    })
   }

	/**
	 * 点击提交问卷答案
	 */
	$(".survey-container").on("click",".jubject-submit",function(){
    	var signIndex = $(this).attr("signIndex");
    	console.log(signIndex)
		// 皮肤设置页面
		if(signIndex == 1){
			return alertTop("此为预览皮肤页面，禁止提交");
		}else{
			/*** 填报页面 **/
			var endTime = new Date();
			//console.log(dateToStr(startTime))
			//console.log(dateToStr(endTime))
			$("#startTime").val(dateToStr(startTime))
			$("#endTime").val(dateToStr(endTime))
			var timeConsuming = endTime.valueOf() - startTime.valueOf();
			$("#timeConsuming").val(timeConsuming)
			//console.log(timeConsuming)

			if(progress == 1){
				/***** 判断当前页是否所有题目都选中 *****/
				var items = $(this).parents(".survey-main").find(".question-item");
				for(var i = 0; i<items.length; i++){
					var item = $(items)[i]
					debugger
					/*** 没有选中 ***/
					if($(item).find(".question-item-content").find("input:checked").val() == undefined){
						/**** 单选 多选 题目未填提示 ****/
						if ($(item).find(".question-item-content").find("input")[0].dataset.questionType != "4"){
							var el = $(item).find(".question-item-content").find("input")[1]
							if(el.dataset.must == 0) { //必填
								$(item).find(".point").css("display","inline-block");
								return false;
							} else {
								$(item).find(".point").css("display","none");
							}
						} else {
							var el = $(item).find(".question-item-content").find("input")[0]
							/***** 单行文本未填提示 *****/
							if(el.dataset.must == 0) { //必填
								if ($(item).find(".optionIpt").html().toString().replace(/\s*|\s$/g,"").length == 0) {
									$(item).find(".point").css("display","inline-block");
									return false;
								} else {  //非必填
									$(item).find(".point").css("display","none");
								}
							} else {
								$(item).find(".point").css("display","none");
							}


						}
						$(document).scrollTop($(item).find(".point").offset().top - 35);
					} else {
						$(item).find(".point").css("display","none");
					}
				}
			}
			$(".survey-main").eq(progress-1).addClass("active")
			var reelId = $("#reelId").val();
			var userId = $("#userId").val();
			var subjectIds = [];
			var optionsIds = [];
			var pageIds = [];
			var oobjectstr = [];
			$("input[name='pageId']").each(function(){
				pageIds.push($(this).val());
			})
			/******* 存 【文本框】 和 【其它选项】 结果 ******/
			var singLineTextArr = []
			/**** 循环遍历题目 ***/
			$("input[name='subject']").each(function(index){
				// console.log("this:",$(this)[0])
				/***** 选项 ID ****/
				var options = "";
				var i = index+1;
				/***** 题目 ID ***/
				var subId = $(this).val()
				subjectIds.push($(this).val());
				//console.log(index)
                var item = {"sid": $(this).val(),"oid": "","ssite":[]}
                for(var k=0; k<$(this).siblings("span").find("input").length; k++){
                    var iptValue = $(this).siblings("span").find("input")[k]
                    item.ssite.push($(iptValue).val())
                }
				if($(this).siblings("span").find("input").length != 0){
					oobjectstr.push(item)
                }

                var subjectType = $(this).parent().find("input[name='subjectType']").val()
				/*** 文本类型  ***/
				var optionItem = {"sid": subId,"oid":$(this).val(),"ssite":[]}

				/*** 单行文本 ****/
				if(subjectType == '4'){
					$("input[name='optionsText']").each(function (index) {
						if (subjectType == 4) {
							var txt = $(this).next().html()
							if (options.indexOf($(this).val()) == -1) {
								options += $(this).val() + ";"
								/****** 去重，防止数据重复 ******/
								var hasId = false
								/***数组里面有的东西不要在push **/
								singLineTextArr.forEach(line => {
									if (line.optionsId === $(this).val()) {
										hasId = true
									}
								})
								if (!hasId) {
									singLineTextArr.push({optionsId: $(this).val(), textAnswer: txt})
								}
							}
						}
					})
					oobjectstr.push(optionItem)
					//console.log("singLineTextArr:",singLineTextArr)
				} else { /*** 单选 和 多选 ***/
					$("input[name='options"+i+".']:checked").each(function(index1,item){
						if (options.indexOf($(this).val()) == -1) {
							options += $(item).val() + ";"
						}
						// for(var j = 0; j<$(this).next("label").find("input").length; j++){
						// 	var opValue = $(this).next("label").find("input")[j];
						// 	optionItem.ssite.push($(opValue).val())
						// }
						// if($(this).next("label").find("input").length == 0){
						// 	return
						// }

						if ($(item).siblings(".wrap-other").length > 0) {
							$(item).siblings(".wrap-other").find(".textarea-other").each(function (index,other) {
								var txt = $(other).html().toString().replace(/\s*|\s$/g,"")
								/****** 去重，防止数据重复 ******/
								var hasId = false
								/***数组里面有的东西不要在push **/
								singLineTextArr.forEach(line => {
									if (line.optionsId === $(item).val()) {
										hasId = true
									}
								})
								if (!hasId) {
									singLineTextArr.push({optionsId: $(item).val(), textAnswer:txt})
								}
							})
						}
						oobjectstr.push(optionItem)
					})
					optionsIds.push(options);
				}
			})
			/***** 最后把所有选中的【单行文本】和【其他选项】放入隐藏域 ******/
			$("#formData").find("#textAnswerList").val(JSON.stringify(singLineTextArr))

			$('.single-text').each(function (index,item) {
				var textId = $(item).attr('id')+";";
				optionsIds.push(textId)
			})
			/****/
            $("#oobjectstr").val(JSON.stringify(oobjectstr))
			/*** 页 ID***/
			$("#pageIds").val(pageIds);
			/*** 题目 id ***/
			$("#subjectIds").val(subjectIds);
			/**** 选项id ***/
			$("#optionsIds").val(optionsIds);
			$.each($("#formData").serializeArray(),function(index,item){
				if (item.name === "textAnswerList") {
					item.value = item.value ? JSON.parse(item.value):""
				}
			})
			$.ajax({
				type:'post',
				url:'/questionnaire/answer/saveanswer',
				data:$("#formData").serialize(),
				success:function(data){
					var data = JSON.parse(data)
					$("#root-container").css("display","none")
					$(".end-box").css("display","flex");
					var uploadPath = $(window.opener.document.getElementsByClassName("header")).find("a.active").text()
					debugger
					if(uploadPath == "可答问卷"){
						window.opener.toSearchs(this,'/questionnaire/answer/unmine','contentMain')
					}else{
						window.opener.toSearchs(this,'/questionnaire/answer/mine','contentMain')
					}
				},
				error:function(){
					alert("保存失败!");
				}
			});
		}

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
                        if(answerflag == 1){
							if(stop == 1){
								$(".btn-submit").css("background-color",res[i].btnBg);
							}
                        }
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

	// 处理时间格式
	function dateToStr(date) {
		var time = new Date(date);
		var y = time.getFullYear();
		var M = time.getMonth() + 1;
		M = M < 10 ? ("0" + M) : M;
		var d = time.getDate();
		d = d < 10 ? ("0" + d) : d;
		var h = time.getHours();
		h = h < 10 ? ("0" + h) : h;
		var m = time.getMinutes();
		m = m < 10 ? ("0" + m) : m;
		var s = time.getSeconds();
		s = s < 10 ? ("0" + s) : s;
		var str = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
		return str;
	}
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

})
//阻止ctrl+s保存页面
document.onkeydown = function(e){
	if(e.ctrlKey && e.which == 83){
		return false
	}
}