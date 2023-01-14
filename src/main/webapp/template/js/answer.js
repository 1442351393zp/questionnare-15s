$(function() {
	$(".form-datetime").datetimepicker({
		language:'zh-CN',
		weekStart:1,
		todayBtn:1,
		autoclose:1,
		todayHighlight:1,
		startView:"decade",
		minView:"decade",
		startDate:"2019",
		minDate:"2019-01-01",
		//forceParse:0,
		format:"yyyy"
	}).on("changeDate",function(){
    	var year = $(".form-datetime").val();
        layui.table.reload("answerList",{
        	where:{
        		year:year
        	}
        })
    })
	$(".unform-datetime").datetimepicker({
		language:'zh-CN',
		/*weekStart:1,
		todayBtn:1,
		autoclose:1,
		todayHighlight:1,*/
		startView:"decade",
		minView:"decade",
		startDate:"2019",
		minDate:"2019-01-01",
		//forceParse:0,
		format:"yyyy",
	}).on("changeDate",function(){
    	var unyear = $(".unform-datetime").val();
        layui.table.reload("unanswerList",{
        	where:{
        		year:unyear
        	}
        })
    })
	var date = new Date();
	var year = date.getFullYear();
	$(".unform-datetime").val(year)
	$(".form-datetime").val(year)
	//加载用户列表
	layui.use(['element','form', 'table', 'jquery','layer'], function() {
		var form = layui.form,
			table = layui.table,
			$ = layui.jquery,
			layer = layui.layer
			element = layui.element
	
		table.render({
			elem: '#answerList',
			url: '/questionnaire/answer/minelist',
			method: 'post',
			cols: [
				[
					{field: 'title',title: '名称',templet:function(d){
						// console.log(d)
						return '<span class="title" reelId='+d.reelId+' answerUserId='+d.answerUserId+' recordId='+ d.recordId +' style="cursor:pointer;">'+d.title+'</span>'
					}},
                    {field: 'createPeo',title: '发布人'},
					{field: 'reelStatus',title: '状态',templet: function(d) {
							if (d.reelStatus == 0) {
								return "开始回收"
							}else {
								return "暂停回收"
							}
						}
					},
					{field: 'answerDate',title: '答题时间'}
				]
			],
			where:{
				year:$("#year").val(),
				userId:$(".userId").val()
			},
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
			var searchName = $(".search-nick").val();
			var searchPhone = $(".search-phone").val();
			table.reload("userList", {
				where: {
					"nickname": searchName,
					"phone": searchPhone
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
		// 点击跳转详情
		$(".answer-list").on("click",".title",function(){
			var reelId = $(this).attr("reelId")
			var userId = $(this).attr("answerUserId")
			var recordId = $(this).attr("recordId")
			window.open("/questionnaire/answer/someone?reelId="+reelId+"&userId="+userId+"&year="+$("#year").val()+"&recordId="+recordId);
		});
		
		// 未答问卷
		table.render({
			elem: '#unanswerList',
			url: '/questionnaire/answer/unminelist',
			method: 'post',
			where:{
				year:$("#unyear").val(),
				userId:$(".userId").val()
			},
			cols: [
				[
					{field: 'title',title: '名称',templet:function(d){
						// console.log(d)
							if(d.newRellStatus == 0){
								return '<i class="newItem">新</i><span class="title" reelId='+d.rId+' userId='+d.userId+' receiverId='+d.receiverId+' style="cursor:pointer;">'+d.title+'</span>'
							}else{
								return '<span class="title" reelId='+d.rId+' userId='+d.userId+' receiverId='+d.receiverId+' style="cursor:pointer;">'+d.title+'</span>'
							}

					}},
                    {field: 'createPeo',title: '发布人'},
					{field: 'reelStatus',title: '状态',templet: function(d) {
							if (d.reelStatus == 0) {
								return "开始回收"
							}else {
								return "暂停回收"
							}
						}
					},
					{field: 'createTime',title: '创建时间'},
                    {field: 'endTime',title: '结束时间',templet: function(d) {
                            if (d.setup == 0) {
                                return d.endTime
                            }else {
                                return "无"
                            }
                        }
                    }
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
		// 未答问卷点击跳转答题页面
		// 点击跳转详情
		$(".unanswer-list").on("click",".title",function(){
			var reelId = $(this).attr("reelId")
			var userId = $(this).attr("userId")
			var receiverId = $(this).attr("receiverId")
			window.open("/questionnaire/answer/center?reelId="+reelId+"&userId="+userId+"&receiverId="+receiverId);
		});
	})
    //htmltime();
})

/*function htmltime() {
    var uid = $(".userId").val();
    // console.log(uid)
    var flag = "false";
    flag = timeajax(uid);
    flag.then(function (response) {
        if("true" == response.toString()){
            toSearchs(this,'/questionnaire/answer/unmine','contentMain');
            // console.log("success,listener...")
            return;
        }
        // console.log("time....")
        //get
        setTimeout(htmltime,3000);
    }).catch(function (reason) {
        console.log(reason);
    })
}*/

/*
function timeajax(uid) {
    //ajax get
    return new Promise(function (resolve,reject) {
        $.ajax({
            type: "GET",
            url: '/questionnaire/answer/htmltime?uid='+uid,
            cache: false,
            success: function (data) {
                resolve(JSON.parse(data));
                // console.log("success:"+data);
                // return data;
            }
        });
    })
    // return false;
}*/
