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
    <meta charset="UTF-8">
    <title>我的问卷</title>
    <link rel="stylesheet" href="../template/css/reset.css">
    <link rel="stylesheet" href="../template/css/common.css">
    <link rel="stylesheet" href="../template/css/home.css">
    <link rel="stylesheet" href="../template/css/my-questionnaire.css">
</head>
<body>
<form action="/questionnaire/reel/upload" method="post" id="saveSubmit" name="saveSubmit" enctype="multipart/form-data">

    <input type="hidden" id="BS" value="yes">

	<input type="file" id="icons" name="icons" onchange="fileType(this)"/>
	<input type="hidden" id="isChangeIcon" name="isChangeIcon">
	 <div id="shwo_img">
	   <a href="<%=basePath%>/${pd.IMGURL}" target="_blank">
	   <img src="<%=basePath%>/${pd.IMGURL}" width="88px" height="89px"/>
	 </a>
	   <input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delP();">
	 </div>
	<input type="hidden" id="isChangeIcon" name="isChangeIcon">
	<input type="hidden" id="IMGNAME" name="IMGNAME" value="${pd.IMGNAME}">
	<input type="hidden" id="IMGURL" name="IMGURL" value="${pd.IMGURL}">
	
	<a onclick="upload()"><button>上传</button></a>
</form>
<script type="text/javascript" src="../template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
function upload(){
	$("#saveSubmit").submit();
}
$(function() {
	//上传
	$('#icons').ace_file_input({
		no_file:'请选择图片 ...',
		btn_choose:'选择',
		btn_change:'更改',
		droppable:false,
		onchange:null,
		thumbnail:false, //| true | large
		whitelist:'gif|png|jpg|jpeg'
	});
	
});
//过滤类型
function fileType(obj){
	$("#isChangeIcon").val("1");
	$("#IMGURL").val('');
	var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
    if(fileType != '.gif' && fileType != '.png' && fileType != '.jpg' && fileType != '.jpeg'){
    	alert("请上传图片格式的文件");
    	$("#icons").val('');
    	document.getElementById("icons").files[0] = '请选择图片';
    }else{
    	$("#BS").val("yes");
    }
}
	

</script>
</body>
</html>