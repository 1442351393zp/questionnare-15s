$(function(){
	
	$.ajax({
		url:"/questionnaire/organiza/organizalist",
		type:'post',
		async:false,
		success:function(res){
			var res = JSON.parse(res)
			var data = res.data 
			if(res.code == 0){
				var itemHml = ""
				var organizalist = getTree(data,"0")
				function eachList(list){
					for(var i = 0; i<list.length; i++){
						var item = list[i];
						if(item.children){
							itemHml += '<tr data-tt-id='+item.id+' data-tt-parent-id='+item.pid+'>\n'
							+'<td>'+item.orname+'</td>\n'
							+'<td>'+item.orcode+'</td>\n'
							+'</tr>\n'
							eachList(item.children)
						}else{
							itemHml += '<tr data-tt-id='+item.id+' data-tt-parent-id='+item.pid+'>\n'
							+'<td>'+item.orname+'</td>\n'
							+'<td>'+item.orcode+'</td>\n'
							+'</tr>\n'
						}
						
									
					}
				}
				eachList(organizalist)
				$("#institution-list tbody").html(itemHml)
			}
		}
	})
    // 初始化加载表格树
	var option = {
		  expandable:true,
	      clickableNodeNames:true
	}
    $("#institution-list").treetable(option)
    // 点击全部展开
    $(".expand").on("click",function(){
        $("#institution-list").treetable("expandAll");
        return false
    })
    // 点击全部关闭
    $(".collapseAll").on("click",function(){
        $("#institution-list").treetable("collapseAll")
        return false
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