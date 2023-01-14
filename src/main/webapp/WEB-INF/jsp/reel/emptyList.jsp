<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
    
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>创建空白问卷</title>
    <meta name="description" content="问卷，是免费、专业的问卷调查系统。提供多种方式创建问卷，简单高效的编辑方式，强大的逻辑设置功能，专业的数据统计和样本甄别，让您轻松开启调研工作。">
    <meta name="keywords" content="调查问卷,问卷调查,市场调研,问卷系统,问卷,模板,免费,简单,调研,市场调查,满意度,问卷调查网,在线问卷调查,微信问卷,投票小程序,投票">
	<link rel="stylesheet" href="<%=path%>/template/css/common.css">
	<link rel="stylesheet" href="<%=path %>/template/css/create-white-question.css">
	<link rel="stylesheet" href="<%=path %>/template/css/emptyList.css">
</head>
<body>
<div class="empty-page">
 	<div class="sub-header clearfix" data-current="true" >
		<div class="sub-header-left">
	        <a class="btn btn-primary" title="列表视图" onclick="createQuestion('${folderId}')"><i class="add">+</i>创建问卷</a>
	    </div>
		<div class="sub-header-right">
	        <%--<a class="sub-header-btn" title="创建文件夹" data-toggle="modal" data-target="#PopDialog"></a>--%>
	        <a class="sub-header-btn create-fold" title="创建文件夹"></a>
	        <a class="sub-header-btn" title="废纸篓" onclick="changeToTrash(event)"></a>
	        <a class="sub-header-btn" title="列表视图" data-type="table" onclick="changeToGrid(event)"></a>
	        <a class="sub-header-btn" title="九宫格视图" data-type="grid" style="display: none" onclick="changeToTable(event)"></a>
	    </div>
	   
 	</div>
 	 <div class="empty-box">
       	<div class="empty-image">
           <img src="<%=path %>/template/image/empty.png" alt="">
       	</div>
       	<div class="empty-point">您还没有问卷哦</div>
       	<div class="empty-btn">
			<i class="add">+</i>
           <span onclick="createQuestion('${folderId}')">现在去创建</span>
       	</div>
    </div>
</div>

<div class="modal fade" id="PopDialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="dialog-container">
				<div class="dialog-header">
					<span class="dialog-title">创建文件夹</span>
					<span class="btn-close-dialog" data-dismiss="modal"></span>
				</div>
				<div class="dialog-main">
					<div class="form-Dialog">
						<input  name="folderName" id="folderName" type="text" class="form-control" placeholder="新建文件夹">
						<hr>
						<fielset>
							<input type="button" class="cancel" value="取消">
							<input type="button" class="inputSubmit" value="确定">
						</fielset>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
    <script src="<%=path %>/template/jquery/jquery-1.11.0.min.js"></script>
	<script src="<%=path %>/template/zui/js/zui.min.js"></script>
	<script src="<%=path %>/template/zui/js/zui.lite.min.js"></script>
<script type="text/javascript">
    function createQuestion(folderId){
		toSearchs(this,'/questionnaire/reel/main?folderId='+folderId,'contentMain');
   }
    function changeToTrash(ev) {
  	  //location.href = "/questionnaire/reel/trashBasket";
  	  toSearchs(this,"/questionnaire/reel/trashBasket","contentMain")
        ev = ev || event
        var target = ev.target
        var elEmptyPage = document.getElementsByClassName("empty-page")[0]
        var subHeader = elEmptyPage.getElementsByClassName("sub-header")[0]
        subHeader.style.display = "none"
        subHeader.dataset.current = false.toString()
        var subHeader2 = elEmptyPage.getElementsByClassName("sub-header-2")[0]
        subHeader2.style.display = "flex"
        subHeader2.dataset.current = true.toString()

    }
	$(".create-fold").click(function(){
		$("#PopDialog").modal()
	})
	//文件夹创建确定
	$(".inputSubmit").click(function(){
		var folderName = $("#folderName").val();
		if(folderName != ""){
			$.ajax({
				type:'get',
				url:"/questionnaire/reel/folderSave",
				async:false,
				data:{
					"folderName":folderName
				},
				success:function(result){
					$("#PopDialog").modal("hide")
					$(".modal-backdrop").remove()
					toSearchs(this,"/questionnaire/reel/list",'contentMain');
				}
			})
		}else{
			new $.zui.Messager('文件夹名称不能为空！',{
				type:'failed',
				close:false
			}).show();
		}
	})
	//点击回车
	$("#folderName").focus(function(){
		//点击回车触发创建文件夹
		document.onkeydown = function(e){
			if(!e) e = window.event;
			if((e.keyCode || e.which)==13){
				$(".inputSubmit").click()
			}
		}
	})
	// 文件夹
	$("#PopDialog .cancel").on("click",function(){
		$("#PopDialog").modal("toggle")
	})

	</script>
</body>
</html>