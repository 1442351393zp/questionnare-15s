$(function() {
	//加载日志列表
	layui.use(['element','form', 'table', 'jquery','layer'], function() {
		var form = layui.form,
			table = layui.table,
			$ = layui.jquery,
			layer = layui.layer
			element = layui.element

		table.render({
			elem: '#userList',
			url: '/questionnaire/systemLog/log',
			method: 'post',
			/*  contentType:'application/json;charset=UTF-8',*/
			cols: [
				[{type: "checkbox",width: 50,fixed: "left"},
					{field: 'operateor',title: '操作用户'},
					{field: 'operatetype',title: '操作描述'},
					{field: 'operatedate',title: '操作时间'}
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
			var operateor = $(".search-nick").val();
			var operatetype = $(".search-phone").val();
			table.reload("userList", {
				where: {
					"operateor": operateor,
					"operatetype": operatetype,
                    "page": 1,
                    "recPerPage": 10
				}
			})

		})
		// 点击重置
		$(".reset").on("click", function() {
			$(".search-nick").val("");
			$(".search-phone").val("");
			table.reload("userList", {
				where: {
					"operateor": "",
					"operatetype": ""
				}
			})
		});

	})
})
