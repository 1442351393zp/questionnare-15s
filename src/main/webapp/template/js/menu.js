$(function(){
	// 初始化加载表格树
	var option = {
		  expandable:true,
	      clickableNodeNames:true
	}
	// 请求菜单管理列表数据
	function reload(){
		var menuUrl = "/questionnaire/premission/perlist";
		sendRequest(menuUrl, "", function(res) {
			if(res.code == 0){
				var itemHml = ""
				var menuList = getTree(res.data,"0");
				function eachList(list){
					for(var i = 0; i<list.length; i++){
						var item = list[i];
						if(item.children){
							itemHml += '<tr data-tt-id='+item.id+' data-tt-parent-id='+item.pid+'>\n'
							+'<td>'+item.name+'</td>\n'
							+'<td>'+item.code+'</td>\n'
							+'<td>'+item.path+'</td>\n'
							+'<td>'+item.zindex+'</td>\n'
							+'<td>\n'
								+'<span class="edit" data-id='+item.id+' data-pid='+item.pid+'>编辑</span>\n'
								+'<span class="addChild" data-id='+item.id+' data-pid='+item.pid+'>添加子节点</span>\n'
								+'<span class="deleteDom" data-id='+item.id+' data-pid='+item.pid+'>删除</span>\n'
							+'</td>\n'
							+'</tr>\n'
							eachList(item.children)
						}else{
							itemHml += '<tr data-tt-id='+item.id+' data-tt-parent-id='+item.pid+'>\n'
							+'<td>'+item.name+'</td>\n'
							+'<td>'+item.code+'</td>\n'
							+'<td>'+item.path+'</td>\n'
							+'<td>'+item.zindex+'</td>\n'
							+'<td>\n'
								+'<span class="edit" data-id='+item.id+' data-pid='+item.pid+'>编辑</span>\n'
								+'<span class="addChild" data-id='+item.id+' data-pid='+item.pid+'>添加子节点</span>\n'
								+'<span class="deleteDom" data-id='+item.id+' data-pid='+item.pid+'>删除</span>\n'
							+'</td>\n'
							+'</tr>\n'
						}
						
									
					}
				}
				eachList(menuList)
				$("#menu-list tbody").html(itemHml)
			}
		})
		
	}
	reload();
	$("#menu-list").treetable(option);
	
	
	 // 点击全部展开
	$(".expand").on("click",function(){
	    $("#menu-list").treetable("expandAll");
	    return false
	})
	// 点击全部关闭
	$(".collapseAll").on("click",function(){
	    $("#menu-list").treetable("collapseAll")
	    return false
	})
	// 点击新建菜单
	$(".menu-box").on("click",".newMenu",function(){
		$("#addMenu").find("input,textarea").val("")
		$("#addMenu").modal().find(".saveMenu").attr("data-type",1)
		
	})
	// 点击保存新建菜单
 	$('.saveMenu').on("click",function(){
 		console.log($(this))
		var menuName = $(".menuName").val();
		var menuUrl = $(".menuUrl").val();
		var menuAuth = $(".menuAuth").val();
		var describe = $(".describe").val();
		if(menuName == ""){
			return alert("菜单名称为空")
		}
		if(menuUrl == ""){
			return alert("路径为空")
		}
		if(menuAuth == ""){
			return alert("权限为空")
		}
		// 新建父级菜单
		if($(this).attr("data-type") == 1){
			var pid = 0
			if($(this).attr("data-id")){
				pid = $(this).attr("data-id")
			}
			var url = "/questionnaire/premission/Addpermission";
			var parms = {
				"name":menuName,
				"pid":pid,
				"path":menuUrl,
				"zindex":menuAuth,
				"descpt":describe,
				"type":1
			};
			sendRequest(url,parms,function(res){
				if(res.code == 0){
					$("#addMenu").modal("hide")
					setTimeout(function(){
						toSearch(this,'/questionnaire/premission/premission','','systemMain');
					},200)
					layer.msg(res.msg)
				}
			})
		};
		// 点击编辑保存
		if($(this).attr("data-type") == 0){
			var url = "/questionnaire/premission/Addpermission";
			var id = $(this).attr("data-id");
			var pid = $(this).attr("data-pid")
			var parms = {
				"id":id,
				"name":menuName,
				"pid":pid,
				"path":menuUrl,
				"zindex":menuAuth,
				"descpt":describe,
				"type":0
			};
			sendRequest(url,parms,function(res){
				if(res.code == 0){
					$("#addMenu").modal("hide")
					layer.msg(res.msg)
					setTimeout(function(){
						toSearch(this,'/questionnaire/premission/premission','','systemMain');
					},200)
					
				}else{
					layer.msg(res.msg)
				}
			})
		}
		
	})
	// 点击编辑
	$("tbody").on("click",".edit",function(){
		var id= $(this).attr("data-id");
		var pid= $(this).attr("data-pid")
		$("#addMenu").find("input,textarea").val("")
		$("#addMenu").modal().find(".modal-title").text("编辑菜单").parents("#addMenu").find(".saveMenu").attr("data-type",0).attr("data-id",id).attr("data-pid",pid)
		var uploadUrl = "/questionnaire/premission/findPermission";
		var parms = {
			"id":id
		}
		sendRequest(uploadUrl,parms,function(res){
			if(res.code == 0){
				$("#addMenu").find(".menuName").val(res.permission.name)
				$("#addMenu").find(".menuUrl").val(res.permission.path)
				$("#addMenu").find(".menuAuth").val(res.permission.zindex)
				$("#addMenu").find(".describe").val(res.permission.descpt)
			}else{
				layer.msg(res.msg)
			}
		})
		
	})
	// 点击添加子节点
	$("tbody").on("click",".addChild",function(){
		var id= $(this).attr("data-id");
		$("#addMenu").find("input,textarea").val("")
		$("#addMenu").modal().find(".modal-title").text("添加子节点").parents("#addMenu").find(".saveMenu").attr("data-type",1).attr("data-id",id)
	})
	// 点击删除
	$("tbody").on("click",".deleteDom",function(){
		var id= $(this).attr("data-id");
		var deleteUrl = "/questionnaire/premission/deletper"
		var parms = {
			"id":id
		}
		sendRequest(deleteUrl,parms,function(res){
			if(res.code == 0){
				layer.msg(res.msg)
				toSearch(this,'/questionnaire/premission/premission','','systemMain')
			}else{
				layer.msg(res.msg)
			}
		})
	})
	
	 // 根据id和pid组装树结构
	function getTree(data,pid){
		var list = []
		var temp 
		for(var i = 0; i<data.length; i++){
			if(data[i].pid == pid){
				temp = getTree(data,data[i].id)
				if(temp.length > 0){
					data[i].children = temp
				}
				list.push(data[i])
			}
		}
		return list
	}
	
})