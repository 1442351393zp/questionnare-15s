$(function() {
	//加载角色列表
	layui.use(['form', 'table', 'jquery','layer'], function() {
		var form = layui.form,
			table = layui.table,
			$ = layui.jquery,
			layer = layui.layer

		table.render({
			elem: '#userList',
			url: '/questionnaire/role/rolelist',
			method: 'post',
			/*  contentType:'application/json;charset=UTF-8',*/
			 cols: [[
				{type: "numbers", width: 50, fixed: "left"},
				{type: "checkbox", width: 50},
				{field: 'rolename',  title: '角色名称'},
				{field: 'code', title: '角色编码'},
				{field: 'descpt',  title: '描述'},
				{title: '操作', width: 100, templet: '#roleBox', fixed: "right", align: "center"}
			]]
			
		});
		// 点击添加角色
		$(".add-user").on("click", function() {
			// 显示弹出框
			$("#addUser").find(".modal-title").text("添加角色").parents("#addUser").find(".saveUser").removeAttr("index")
			$("#addUser").modal().find('input[type="text"],input[type="password"],textarea').val("");
		
		});
		// 点击确定按钮
		$(".saveUser").on("click", function() {
			var rolename = $(".roleName").val(),
				code = $(".roleCode").val(),
				descpt = $(".describe").val()
			if(rolename == ""){
				return layer.msg("角色名为空")
			}
			if(code == ""){
				return layer.msg("角色编码为空")
			}
			// 修改
			if ($(this).attr("index") == 1) {
				var checkUser = table.checkStatus('userList');
				var id = checkUser.data[0].roleid
				var url = "/questionnaire/role/roleupdate";
				var parms = {
					"roleid": id,
					"rolename":rolename,
					"code":code,
					"descpt":descpt
				}
			} else {
				var url = "/questionnaire/role/roleAdd";
				var parms = {
					"rolename":rolename,
					"code":code,
					"descpt":descpt
				}
			}
			sendRequest(url, parms, function(res) {
				if (res == "ERROR") {
					layer.msg("请求失败", {
						icon: 2
					})
				}
				if (res.code == 0) {
					layer.msg(res.msg, {
						icon: 1
					})
					table.reload("userList")
					$("#addUser").modal('toggle');
					
				}
				if (res.code == 1) {
					layer.msg(res.msg, {
						icon: 2
					})
				}
			})

		});
		//点击修改用户显示角色信息
		$(".upload-user").on("click", function() {

			//获取当前表格实例
			var checkUser = table.checkStatus('userList');
			if (checkUser.data.length < 1) {
				return layer.alert("您还没有选中数据")
			} else if (checkUser.data.length > 1) {
				return layer.alert("您只能选中一个")
			}

			var selectItem = checkUser.data[0]
			$("#addUser").find(".modal-title").text("修改角色").parents("#addUser").find(".saveUser").attr("index", 1)
			$("#addUser").modal().find('input[type="text"],textarea').val("");
			$(".roleName").val(selectItem.rolename);
			$(".roleCode").val(selectItem.code);
			$(".describe").val(selectItem.descpt);
		})
		// 点击删除角色
		$(".delete-user").on("click", function() {
			var checkUser = table.checkStatus('userList');
			if (checkUser.data.length < 1) {
				return layer.msg("您还没有选中数据")
			}
			var Id = [];
			for (item of checkUser.data) {
				Id.push(item.roleid)
			}
			var url = "/questionnaire/role/roledelete"
			var parms = "roleids=" + Id;
			sendRequest(url, parms, function(res) {
				if (res == "ERROR") {
					layer.msg("请求失败", {
						icon: 2
					})
				}
				if (res.code == 0) {
					table.reload("userList")
					layer.msg(res.msg, {
						icon: 1
					})
				} else {
					layer.msg(res.msg, {
						icon: 2
					})
				}
			})
		});
		// 点击操作按钮
		table.on('tool(userList)', function (obj) {
			var data = obj.data;
			var layEvent = obj.event;
			if(layEvent == "info"){
				console.log(obj)
				var  roleid = data.roleid;
				console.log(roleid)
				$("#authorityList").modal().find(".modal-body").find("#treeDemo").empty();
				$(".saveAuth").attr("roleId",roleid)
				var url = "/questionnaire/role/findPerms"
				var parms = {
					"roleid":roleid
				}
				sendRequest(url, parms, function(res) {
					 var setting={
						check: {
							enable: true
						},
						data: {
							simpleData: {
								enable: true
							}
						}
					}
					$.fn.zTree.init($("#treeDemo"),setting,res.data)
				})
			}
		});
		// 点击保存角色权限列表
		$(".saveAuth").on("click",function(){
			var roleid = $(this).attr("roleId")
			var perId = []
			var selectChecked = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = selectChecked.getCheckedNodes(true)
			for(var i = 0; i<nodes.length; i++){
				perId.push(nodes[i].id)
			}
			// if(perId.length>1){
			// 	return layer.msg("只能选择一个角色")
			// }
			var url = "/questionnaire/role/updaterolePerms";
			var parms = {
				"roleid":roleid,
				"perid":perId.join(",")
			}
			sendRequest(url, parms, function(res) {
				if (res == "ERROR") {
					layer.msg("请求失败", {
						icon: 2
					})
				}
				if (res.code == 0) {
					$("#authorityList").modal("toggle")
					layer.msg(res.msg, {
						icon: 1
					})
				} else {
					layer.msg(res.msg, {
						icon: 2
					})
				}
			})
		})
	})
})
