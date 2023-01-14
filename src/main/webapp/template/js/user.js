$(function() {
	//加载用户列表
	layui.use(['element','form', 'table', 'jquery','layer'], function() {
		var form = layui.form,
			table = layui.table,
			$ = layui.jquery,
			layer = layui.layer
			element = layui.element

		table.render({
			elem: '#userList',
			url: '/questionnaire/user/userlist',
			method: 'post',
			/*  contentType:'application/json;charset=UTF-8',*/
			cols: [
				[{type: "checkbox",width: 50,fixed: "left"},
					{field: 'username',title: '账号'},
					{field: 'nickname',title: '昵称'},
					{field: 'sex',title: '性别',templet: function(d) {
							if (d.sex == 1 || d.sex == "女") {
								return "女"
							}else if(d.sex == "" || d.sex == null || d.sex == undefined){
								return " "
							}else if (d.sex == 0 || d.sex == "男") {
								return "男"
							}
						}
					},
					{field: 'email',title: 'email'},
					{field: 'phone',title: '电话'},
				]
			],
			limits: [10, 15, 20],
			limit: 10,
			page: true,
			parseData: function(res) { // res为原始返回的数据
				return {
					"code": res.code, // 解析接口状态
					"msg": res.message, // 解析提示文本
					"data": res.data, // 解析数据列表
					"count": res.count
				}
			},
			request: {
				pageName: 'page', // 页码的参数名称，默认： page
				limitName: 'recPerPage' // 每页数据量的参数名，默认： limit
			},
			response: {
				statusName: "code",
				statusCode: 0,
				msgName: "message",
				dataName: "data"
			}
		});


		// 点击搜索按钮根据搜索条件查询
		$(".search").on("click", function() {
            var searchUser = $(".search-phone").val();
            var searchName = $(".search-nick").val();
			table.reload("userList", {
				where: {
                    "username": searchUser,
                    "nickname": searchName
				}
			})

		})
		// 点击重置
		$(".reset").on("click", function() {
			$(".search-nick").val("");
			$(".search-phone").val("");
			table.reload("userList", {
				where: {
					"nickname": "",
					"phone": ""
				}
			})
		});
		// 点击添加用户
		$(".add-user").on("click", function() {
			// 显示弹出框
			$("#addUser").find(".modal-title").text("添加用户").parents("#addUser").find(".pwd-group").css("display", "block").parents(
				"#addUser").find(".saveUser").removeAttr("index")
			$("#addUser").modal().find('input[type="text"],input[type="password"],textarea').val("");
		
		});
		// 点击确定按钮
		$(".saveUser").on("click", function() {
			console.log($(".nickName"))
			var yxReg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;   //验证邮箱正则表达式
            var telReg = /(^1[3,5,7,8]\d{9}$)/g;  // 手机号正则表达式验证
			var userName = $(".userName").val();
			var password = $(".password").val();
			var pwd = $(".pwd").val();
			var nickName = $("#nickName").val();
			var email = $(".email").val();
			var sex = $('input[type=radio]:checked').val();
			var phone = $(".phone").val();
			var describe = $(".describe").val();

			// 修改
			if ($(this).attr("index") == 1) {
				if (userName == "") {
					layer.msg("请输入账号");
					return false
				}
				if (nickName == "") {
					layer.msg("请输入昵称");
					return false
				}
				if (email == "") {
					layer.msg("请输入邮箱");
					return false
				} else {
					if (!(yxReg.test(email))) {
						layer.msg("输入的邮箱格式不正确")
						return false
					}
				}
				if (phone == "") {
					layer.msg("请输入手机号");
					return false
				} else {
					if (!(telReg.test(phone))) {
						layer.msg("输入的手机号格式不对")
						return false
					}
				}
				var checkUser = table.checkStatus('userList');
				var id = checkUser.data[0].id
				var url = "/questionnaire/user/userupdate";
				var parms = {
					"id": id,
					"username": userName,
					"nickname": nickName,
					"sex": sex,
					"email": email,
					"phone": phone,
					"describe": describe
				}
			} else {
				if (userName == "") {
					layer.msg("请输入账号");
					return false
				}
				if(password == ""){
					layer.msg("请输入密码");
					return false
				}
				if(pwd == ""){
					layer.msg("请输入确认密码");
					return false
				}
				if(password != pwd){
					layer.msg("密码和确认密码不一致");
					return false
				}
				if (nickName == "") {
					layer.msg("请输入昵称");
					return false
				}
				if (email == "") {
					layer.msg("请输入邮箱");
					return false
				} else {
					if (!(yxReg.test(email))) {
						layer.msg("输入的邮箱格式不正确")
						return false
					}
				}
				if (phone == "") {
					layer.msg("请输入手机号");
					return false
				} else {
					if (!(telReg.test(phone))) {
						layer.msg("输入的手机号格式不对")
						return false
					}
				}
				
				var url = "/questionnaire/user/userAdd";
				var parms = {
					"username": userName,
					"password": password,
					"nickname": nickName,
					"sex": sex,
					"email": email,
					"phone": phone,
					"describe": describe
				}
			}
			sendRequest(url, parms, function(res) {
				if (res == "ERROR") {
					layer.msg("请求失败", {
						icon: 2
					})
				}
				if (res.code == 0) {
					layer.msg(res.msg), {
						icon: 1
					}
					$("#addUser").modal('toggle');
					table.reload("userList")
				}
				if (res.code == 1) {
					layer.msg(res.msg, {
						icon: 2
					})
				}
			})

		});
		//点击修改用户显示用户信息
		$(".upload-user").on("click", function() {

			//获取当前表格实例
			var checkUser = table.checkStatus('userList');
			if (checkUser.data.length < 1) {
				return layer.alert("您还没有选中用户")
			} else if (checkUser.data.length > 1) {
				return layer.alert("您只能选中一个")
			}

			var selectItem = checkUser.data[0]
			$("#addUser").find(".modal-title").text("修改用户").parents("#addUser").find(".pwd-group").css("display", "none").parents(
				"#addUser").find(".saveUser").attr("index", 1)
			$("#addUser").modal().find('input[type="text"],textarea').val("");
			$(".userName").val(selectItem.username);
			$(".nickName").val(selectItem.nickname);
			$(".email").val(selectItem.email);
			$(".phone").val(selectItem.phone);
			$(".describe").val(selectItem.describe);
		})
		// 点击删除用户
		$(".delete-user").on("click", function() {
			var checkUser = table.checkStatus('userList');
			if (checkUser.data.length < 1) {
				return layer.msg("您还没有选中用户")
			}
			var Id = [];
			for (item of checkUser.data) {
				Id.push(item.id)
			}
			var url = "/questionnaire/user/userupdelete"
			var parms = "ids=" + Id;
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
		// 点击用户角色
		$(".user-role").on("click",function(){
			//获取当前表格实例
			var checkUser = table.checkStatus('userList');
			if (checkUser.data.length < 1) {
				return layer.alert("您还没有选中用户")
			} else if (checkUser.data.length > 1) {
				return layer.alert("您只能选中一个")
			}
			$("#userRole").modal()
			var userid = checkUser.data[0].id
			table.render({
				elem: '#roleList',
				url: '/questionnaire/user/findroleUser',
				method: 'post',
				where:{
					"userid":userid
				},
				/*  contentType:'application/json;charset=UTF-8',*/
				cols: [[
					{type: "checkbox", width: 50},
					{field: 'rolename',  title: '角色名称',width: 150},
					{field: 'code', title: '角色编码',width: 150},
					{field: 'descpt',  title: '描述',width: 215},
				]],
				done:function(res){
					// console.log(res)
					 //LAY_CHECKED
					for(var i = 0; i<res.data.length; i++){
						var item = res.data[i]
						for(var k = 0; k<res.data1.length; k++){
							var item1 = res.data1[k];
							if(item.roleid == item1.roleid){
								res.data[i]["LAY_CHECKED"] = "true"
								//$('#role-list input[type="checkbox"]').eq(i).prop("checked",true)
								var index = res.data[i]["LAY_TABLE_INDEX"];
								var body = layer.getChildFrame('body');
								$('div[lay-id="roleList"] tr[data-index='+index+'] input[type="checkbox"]').next().addClass("layui-form-checked")
								$('div[lay-id="roleList"] tr[data-index='+index+'] input[type="checkbox"]').prop("checked",true)
								
							}
						}
					}
				}
			});
		});
		// 点击保存用户角色
		$(".saveRole").on("click",function(){
			var checkUser = table.checkStatus('userList');
			var checkRole = table.checkStatus('roleList');
			var userid = checkUser.data[0].id
			var roleids = [];
			console.log(checkRole)
			for(item of checkRole.data){
				roleids.push(item.roleid)
			}
			if(roleids.length >1){
				return layer.msg("只能选择一个角色")
			}
			var url = "/questionnaire/user/saveroleUser";
			var parms={
				"userid":userid,
				"roleids":roleids.join(",")
			}
			sendRequest(url, parms, function(res) {
				if (res == "ERROR") {
					layer.msg("请求失败", {
						icon: 2
					})
				}
				if (res.code == 0) {
					$("#userRole").modal("toggle")
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
		// 点击同步用户
		$(".asyncUser").on("click",function(){
            $('#dataLoadingWait').shCircleLoader({color: "#1197e8",progress:"处理中"});
			var url = "/questionnaire/user/tongbuUser"
			$.ajax({
				url:url,
				method:"post",
				success:function(res){
				    debugger
					var res = JSON.parse(res)
                    $('#dataLoadingWait').shCircleLoader("destroy");
                    if(res.code == 0){
						layer.msg(res.msg,{icon:1})
					}else{
						layer.msg(res.msg,{icon:2})
					}
				},
				error:function(res){
                    $('#dataLoadingWait').shCircleLoader("destroy");
					layer.msg(res.msg,{icon:2})
				}
			})
		})
		// 点击用户所属机构
		$(".user-institution").on("click",function(){
			//获取当前表格实例
			var checkUser = table.checkStatus('userList');
			if (checkUser.data.length < 1) {
				return layer.alert("您还没有选中用户")
			} else if (checkUser.data.length > 1) {
				return layer.alert("您只能选中一个")
			}
			var id = checkUser.data[0].id
			$("#userInstitution").modal().find(".institutionList").empty();
			var url = "/questionnaire/user/findPerms";
			var parms = {
				"userid":id
			}
			sendRequest(url, parms, function(res) {
				if (res == "ERROR") {
					layer.msg("请求失败", {
						icon: 2
					})
				}
				if(res.code == 0){
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
						}
					}
					 console.log(res.data)
					$.fn.zTree.init($("#institutionList"),setting,res.data)
				}else{
					layer.msg(res.msg, {
						icon: 2
					})
				}
			})
			
		})

        // 点击全部重新同步用户,删除原来的组织用户.时间为0.
        $(".asyncUserall").on("click",function(){
            if (confirm("确定要删除全部组织及用户,再重新同步吗?")) {
                $('#dataLoadingWait').shCircleLoader({color: "#1197e8",progress:"处理中"});
                var url = "/questionnaire/userFifteenSuo/tongbuUserall"
                $.ajax({
                    url:url,
                    method:"post",
                    success:function(res){
                        debugger
                        var res = JSON.parse(res)
                        $('#dataLoadingWait').shCircleLoader("destroy");
                        if(res.code == 0){
                            layer.msg(res.msg,{icon:1})
                        }else{
                            layer.msg(res.msg,{icon:2})
                        }
                    },
                    error:function(res){
                        $('#dataLoadingWait').shCircleLoader("destroy");
                        layer.msg(res.msg,{icon:2})
                    }
                })
            }

        })

	})
})
