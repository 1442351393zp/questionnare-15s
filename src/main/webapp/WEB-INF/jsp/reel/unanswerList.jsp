<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>可答问卷</title>
    <link rel="shortcut icon" href="<%=path %>/template/image/logo/logo-title.ico">
    <link rel="stylesheet" href="<%=path %>/template/css/home.css">
    <link rel="stylesheet" href="<%=path %>/template/css/create-questionnaire.css">
    <link rel="stylesheet" href="<%=path %>/template/css/create-white-question.css">
    <script src="<%=path %>/template/js/page.js"></script>
    <!-- zui -->
    <link href="<%=path %>/template/zui/css/zui.min.css" rel="stylesheet">
     <!-- 引入时间选择器样式 -->
    <link rel="stylesheet" href="<%=path %>/template/zui/lib/datetimepicker/datetimepicker.min.css">
    <!--重置样式-->
    <link rel="stylesheet" href="<%=path %>/template/css/reset.css">
    <link rel="stylesheet" href="<%=path %>/template/css/common.css">
     <link rel="stylesheet" href="<%=path %>/template/lib/layui-v2.5.4/css/layui.css">
    <%-- <script src="<%=path %>/template/js/viewchange.js"></script> --%>
	<link rel="stylesheet" href="<%=path %>/template/css/answerList.css">
</head>
	<body>
	 <!-- 页面顶部¨ -->
	<%-- <%@ include file="head.jsp"%> --%>
	<div class="unanswer-list">
		<input class="userId" type="hidden" value="${userid}">
		 <div class="nav-btn">
		 	  <input type="text" id="unyear" class="form-control unform-datetime" placeholder="选择年份">
		 </div>
         <table class="layui-hide" id="unanswerList" lay-filter="currentTableFilter"></table>
    </div>
     
    <!-- jQuery (ZUI中的Javascript组件依赖于jQuery) -->
    <script src="<%=path %>/template/jquery/jquery-1.11.0.min.js"></script>
    <!-- ZUI Javascript组件 -->
    <script src="<%=path %>/template/zui/js/zui.min.js"></script>
      <!-- 引入时间选择器js -->
    <script src="<%=path %>/template/zui/lib/datetimepicker/datetimepicker.min.js"></script>
    <script src="<%=path %>/template/zui/lib/datagrid/zui.datagrid.min.js"></script>
    <script src="<%=path %>/template/lib/layui-v2.5.4/layui.all.js"></script>
    <script src="<%=path %>/template/js/common/request.js"></script>
    <script src="<%=path %>/template/js/answer.js"></script>
    
	</body>
</html>