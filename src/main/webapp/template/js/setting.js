$(function(){
	 var rId = $("#reelId").val();
     getUser(rId);
     // 判断问卷结束时间是否选择
	// console.log($(".switch-inline>input[index=2]"))
	if($(".switch-inline>input[index=2]").attr("checked")){
		$(".switch-inline>input[index=2]").parent().next().css("display","block");
	}
    // console.log($(".switch-inline>input[index=8]"))
    if($(".switch-inline>input[index=8]").attr("checked")){
        $(".switch-inline>input[index=8]").parent().next().css("display","block");
    }

    //问卷设置
    $(".switch-inline>input").on("click",function(e){
        var data_index = $(this).attr("index");
        var checked = $(this)[0].checked;
        /**
         * 问卷显示
         */
        //问卷显示编号判断
        if(data_index == 1){
            //选中
            if(checked){
                //console.log("显示编号选中")
                setShowNo(0,rId);
            }else{
                //console.log("显示编号取消")
                setShowNo(1,rId);
            }
        }
        if(data_index == 8){
            //选中
            if(checked){
                //console.log("发布渠道选中")
                $(this).parent().next().css("display","block");
                setCanalnomsg(0,rId,0);
                //修改渠道信息
                updateCanalText(rId,"u",1);
            }else{
                //console.log("发布渠道取消");
                $(this).parent().next().css("display","none");
                setCanal(1,rId,0);
                //删除渠道信息
                updateCanalText(rId,'d',0);
            }
        }
        if(data_index == 9){
            //选中
            if(checked){
               // console.log("发布渠道在线选中"+data_index)
                setCanal(0,rId,1);
            }else{
                //console.log("发布渠道在线取消"+data_index)
                setCanal(1,rId,1);
            }
            //修改渠道信息
            updateCanalText(rId,"u",0);
        }
        if(data_index == 10){
            //选中
            if(checked){
                //console.log("发布渠道邮件选中"+data_index)
                setCanal(0,rId,2);
            }else{
               // console.log("发布渠道邮件取消"+data_index)
                setCanal(1,rId,2);
            }
            //修改渠道信息
            updateCanalText(rId,"u",0);
        }
        if(data_index == 11){
            //选中
            if(checked){
               // console.log("发布渠道即时通讯选中"+data_index)
                setCanal(0,rId,3);
            }else{
                //console.log("发布渠道即时通讯取消"+data_index)
                setCanal(1,rId,3);
            }
            //修改渠道信息
            updateCanalText(rId,"u",0);
        }


        /**
         * 回收设置
         */
        //设定问卷结束时间
        if(data_index == 2){
            //选中
            if(checked){
                $(this).parent().next().css("display","block");   
            }else{
            	$(this).parent().next().css("display","none");   
                //console.log("结束时间取消")
                setEndTime(1,rId);
            }
        }
        //匿名答题
        if(data_index == 3){
            //选中
            if(checked){
                //console.log("选中")
                setIsAnonymous(0,rId);
            }else{
                //console.log("取消")
                setIsAnonymous(1,rId);
            }
        }
        //每个用户只能回答一次
        if(data_index == 4){
            //选中
            if(checked){
                setOnceChance(0,rId);
            }else{
                setOnceChance(1,rId);
            }
        }
        //回收消息提醒
        if(data_index == 5){
            //选中
            if(checked){
                setIsRetrieve(0,rId);
            }else{
            	setIsRetrieve(1,rId);
            }
        }
        /**
         * 参与人员
         */
        if(data_index == 6){
            if(checked){  //选中
            	 $("#myModal").modal();    // 打开弹出框
            }else{
               // console.log("取消")
				$.ajax({
					url:"/questionnaire/question/setupuser",
					method:"post",
					data:{
						"rId":rId
					},
					success:function(data){
						//console.log(data)
						var data = JSON.parse(data)
						if(data.code == "0"){
							alertTip(data.msg)
							//alert(data.msg)
						}else{
							alertTip(data.msg)
							// alert(data.msg)
						}
					}
				})
				$(".select-list").empty()
            }
        }
        // 问卷发起消息提醒
        if(data_index == 7){
            //选中
            if(checked){
                setIsInitiate(0,rId);
            }else{
            	setIsInitiate(1,rId);
            }
        }
    });
    // 初始化时间选择器
    $(".form-datetime").datetimepicker({
    	startDate:new Date(),
    	language:'zh-CN',
    	format:"yyyy-mm-dd hh:ii",
		autoclose:true,
    }).on("changeDate",function(){
    	var endTime = $("#endTime").val();
        setEndTime(0,rId,endTime);
    });
    
    function setShowNo(varable,rId){
    	$.ajax({
    		type:'post',
    		url:"/questionnaire/question/showno",
    		data:{"showNo":varable,rId:rId},
    		success:function(data){
    			var data = $.parseJSON(data);
    			alertTip(data.msg);
    		}
    	})
    }
    function updateCanalText(rId,flag,msg){
        $.ajax({
            type:'post',
            url:"/questionnaire/question/updatecanaltext",
            data:{rId:rId,"flag":flag},
            success:function(data){
                //console.log('update canal text success');
                var data = $.parseJSON(data);
                if('0' == msg){
                    alertTip(data.msg);
                }
            }
        })
    }
    function setCanalnomsg(varable,rId,flag){
        $.ajax({
            type:'post',
            url:"/questionnaire/question/setcanal",
            data:{"canal":varable,rId:rId,"flag":flag},
            success:function(data){
               // console.log('no msg')
                //var data = $.parseJSON(data);
                //alert(data.msg);
            }
        })
    }
    function setCanal(varable,rId,flag){
        $.ajax({
            type:'post',
            url:"/questionnaire/question/setcanal",
            data:{"canal":varable,rId:rId,"flag":flag},
            success:function(data){
                /*var data = $.parseJSON(data);
                alert(data.msg);*/
            }
        })
    }
    function setEndTime(varable,rId,endTime){
    	$.ajax({
    		type:'post',
    		url:"/questionnaire/question/setendtime",
    		data:{"setup":varable,"rId":rId,"endTime":endTime},
    		success:function(data){
    			var data = $.parseJSON(data);
    			alertTip(data.msg);
    		}
    	})
    }
    function setOnceChance(varable,rId){
    	$.ajax({
    		type:'post',
    		url:"/questionnaire/question/oncechance",
    		data:{"onceChance":varable,rId:rId},
    		success:function(data){
    			var data = $.parseJSON(data);
    			alertTip(data.msg);
    		}
    	})
    }
    function setIsRetrieve(varable,rId){    //回收
    	$.ajax({
    		type:'post',
    		url:"/questionnaire/question/isretrieve",
    		data:{"retrieve":varable,rId:rId},
    		success:function(data){
    			var data = $.parseJSON(data);
    			alertTip(data.msg);
    		}
    	})
    }
    function setIsInitiate(varable,rId){  //发起
    	$.ajax({
    		type:'post',
    		url:"/questionnaire/question/isinitiate",
    		data:{"initiate":varable,rId:rId},
    		success:function(data){
    			var data = $.parseJSON(data);
    			alertTip(data.msg);
    		}
    	})
    }
    function setIsAnonymous(varable,rId){
    	$.ajax({
    		type:'post',
    		url:"/questionnaire/question/anonymous",
    		data:{"anonymous":varable,rId:rId},
    		success:function(data){
    			var data = $.parseJSON(data);
    			alertTip(data.msg);
    		}
    	})
    }
    //关闭
    $(".btn-default").click(function(){
        $("input[index='6']").prop("checked",false)
    })
    $(".close").click(function(){
    	$("input[index='6']").prop("checked",false)
    })
	// 获取底部高度
	$(function(){
		var height = $(document).height()
		height = height-95
		$(".footer-copyright").css({
			"position": "absolute",
			"top": height+"px",
			"width":"100%"
		})
	})
})
function getUser(rId){
	//加载人员列表
    $.ajax({
	       type:'post',
	       url:"/questionnaire/user/getuser",
	       data:{"reelId":rId},
	       success:function(data){
	       		var data = JSON.parse(data)
			   	var ztreeUser = JSON.parse(data.ztreeuser)
			   //console.log(ztreeUser)
			    function getUser(data){
					for(var i = 0; i<data.length; i++){
						if(data[i].children.length == 0){
							data[i].children = null
						}else{
							getUser(data[i].children)
						}
					}
			   	}
			   getUser(ztreeUser)
			   var setting={
				   check: {
					   enable: true
				   },
				   data: {
					   simpleData: {
						   enable: false
					   },
					   key:{
						   name:"orname"
					   }
				   },
				   callback:{
				   	onCheck:selectChecked
				   }
			   }
			   $.fn.zTree.init($(".user-tree"),setting,ztreeUser)
			   var allZttee = $.fn.zTree.getZTreeObj("user-tree");
				allZttee.expandAll(true)

 		   // var allList  = $.parseJSON(data).allList;//全部
 		   // var selectList  = $.parseJSON(data).selectList;//选中
 		   // for(var i = 0; i < allList.length; i++){
 			//    if(selectList.length > 0){
 			// 	   for(var j = 0; j < selectList.length; j++){
 			// 		   if(allList[i].id == selectList[j].userId){
 			// 			   var div = "<div class='checkbox-primary user-item'><input type='checkbox' checked name='userId' value='"+allList[i].id+"'><label id='userName"+i+"'>"
 			// 			   + allList[i].nickname + "</label></div>";
 			// 			   break;
 			// 		   }else{
 			// 			   var div = "<div class='checkbox-primary user-item'><input type='checkbox' name='userId' value='"+allList[i].id+"'><label id='userName"+i+"'>"
 			// 			   + allList[i].nickname + "</label></div>";
 			// 		   }
 			// 	   }
 			//    }else{
 			// 	  var div = "<div class='checkbox-primary user-item'><input type='checkbox' name='userId' value='"+allList[i].id+"'><label id='userName"+i+"'>"
			// 	   + allList[i].nickname + "</label></div>";
 			//    }
 			//    $(".modal-body").append(div);
 		   // }
			   var selectUserList = data.userlist
			   // console.log(selectUserList)
			   for(var i = 0; i<selectUserList.length; i++){
			   		$(".select-list").append('<span>'+selectUserList[i].nickname+'</span>')
				   $(".user-list-right").append('<span>' + selectUserList[i].nickname + '</span>')
			   }
	    }
      })
}
// 监听ztree树复选框点击事件
function selectChecked(event ,treeId,treeNode){
	var userItem = treeNode
	// 判断是否选中
	// if(userItem.checked){
	// 	$(".user-list-right").empty()
	// 	var selectChecked = $.fn.zTree.getZTreeObj("user-tree");
	// 	var nodes = selectChecked.getCheckedNodes(true)
	// 	var allUserList = $(".user-list-right").find("span")
	// 	for(var i = 0; i<nodes.length; i++){
	// 		// 判断是否是机构
	// 		if(nodes[i].organid != ""){
	// 			$(".user-list-right").append('<span>' + nodes[i].orname + '</span>')
	// 		}
	// 	}
	// }else {
	// 	$(".user-list-right").empty()
	// 	var selectChecked = $.fn.zTree.getZTreeObj("user-tree");
	// 	var nodes = selectChecked.getCheckedNodes(true)
	// 	console.log(nodes)
	// 	var allUserList = $(".user-list-right").find("span")
	// 	for (var i = 0; i < nodes.length; i++) {
	// 		if (nodes[i].organid != "") {
	// 			$(".user-list-right").append('<span>' + nodes[i].orname + '</span>')
	// 		}
	// 	}
	// }
	if(userItem.checked) {
		// 判断是否是机构
		if(userItem.organid != "") {
			$(".user-list-right").append('<span>' + userItem.orname + '<i class="delete-user"></i></span>')
		}else{
			//递归添加机构下所有选中的用户
			function appendItem(data){
				if(data.children != null){
					for(var i = 0; i<data.children.length; i++){
						if(data.children[i].children){
							appendItem(data.children[i])
						}else{
							$(".user-list-right").append('<span>' + data.children[i].orname + '<i class="delete-user"></i></span>')
						}
					}
				}else{
					$(".user-list-right").append('<span>' + data.orname + '<i class="delete-user"></i></span>')
				}

			}
			appendItem(userItem)
		}
	}else{
		// 判断选中的是否是用户
		if(userItem.organid != ""){
			// 不选中循环遍历右侧所有展示的用户并删除
			var allUserList = $(".user-list-right").find("span")
			for(var i = 0; i<allUserList.length; i++){
				if(userItem.orname == $(allUserList[i]).text()){
					// console.log($(allUserList[i]))
					$(allUserList[i]).remove()
				}
			}
		}else{
			var allUserList = $(".user-list-right").find("span");
			function deleteUserList(data) {
				for(var i = 0; i<data.children.length; i++){
					if(data.children[i].children){
						deleteUserList(data.children[i])
					}else{
						for(var k = 0; k<allUserList.length; k++){
							if(data.children[i].orname == $(allUserList[k]).text()){
								$(allUserList[k]).remove()
							}
						}
					}
				}
			}
			deleteUserList(userItem)
		}
	}
}
//点击删除选中用户
$(".user-list-right").on("click",".delete-user",function(){
	var selectChecked = $.fn.zTree.getZTreeObj("user-tree");
	var nodes = selectChecked.getCheckedNodes();
	for(var i = 0; i<nodes.length; i++){
		if($(this).parent().text() == nodes[i].orname){
			selectChecked.checkNode(nodes[i],false,true)
		}
	}
	$(this).parent().remove()

})
// 保存用户
function saveSelUser(){
	var selectChecked = $.fn.zTree.getZTreeObj("user-tree");
	var nodes = selectChecked.getCheckedNodes(true)
	var userIds = [];
	for(var i = 0; i<nodes.length; i++){
		var userItem = nodes[i]
		if(userItem.organid != ""){
			userIds.push(userItem.id)
		}
	}
	$("#userIds").val(userIds);
	$.ajax({
		url:"/questionnaire/user/saveselectuser",
		method:"post",
		data:$("#userForm").serialize(),
		success:function(data){
			var data = JSON.parse(data)
			alertTop(data.msg)
			$("#myModal").modal('toggle')
			$(".select-list").empty();

			for(var i = 0; i<nodes.length; i++){
				var userItem = nodes[i]
				if(userItem.organid != ""){
					$(".select-list").append('<span>' +userItem.orname + '</span>')
				}
			}
		}
	})
	// var userIds = [];
	// var userNames = [];
	// $("input[name='userId']:checked").each(function(){
	// 	userIds.push($(this).val());
	// 	userNames.push($(this).next().text())
	// })
	// $("#userIds").val(userIds);
	// $("#userNames").val(userNames);
	// $.ajax({
	// 	type:'post',
	// 	url:"/questionnaire/user/saveselectuser",
	// 	data:$("#userForm").serialize(),
	// 	success:function(data){
	// 		var data = $.parseJSON(data);
	// 		alert(data.msg);
	// 		$("#myModal").modal('toggle')
	// 	}
	// })
}

