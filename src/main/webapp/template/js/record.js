$(function(){
	layui.use(['form', 'table', 'jquery','layer'], function() {
		table.render({
			elem: '#recordList',
			url: '/questionnaire/user/userlist',
			method: 'post',
			cols: [[
				{type: "numbers",width: 50,fixed: "left"},
				{field: 'operateor',title: '操作用户'},
				{field: 'operatetype',title: '操作描述'},
				{field: 'operatedate',title: '操作时间'},
			]],
			limits: [10, 15, 20],
			limit: 10,
			page: true,
		});
	})
})