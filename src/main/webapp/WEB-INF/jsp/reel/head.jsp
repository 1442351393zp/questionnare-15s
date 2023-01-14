<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<html lang="en">

<head>
	<meta charset="UTF-8">
	<link rel="shortcut icon" href="/questionnaire/template/image/logo/logo-title.ico">
	<link rel="stylesheet" href="/questionnaire/template/zui/css/zui.min.css">
	<link rel="stylesheet" href="/questionnaire/template/zui/lib/datagrid/zui.datagrid.min.css">
	<link rel="stylesheet" href="/questionnaire/template/lib/layui-v2.5.4/css/layui.css">
	<link rel="stylesheet" href="/questionnaire/template/css/stat.css">
	<!--  <link rel="stylesheet" href="/questionnaire/template/css/reset.css"> -->
	<link rel="stylesheet" href="/questionnaire/template/css/common.css">
	<link rel="stylesheet" href="/questionnaire/template/css/home.css">
	<link rel="stylesheet" href="/questionnaire/template/css/system.css" />
	<link rel="stylesheet" href="/questionnaire/template/css/user.css">
	<!-- <link rel="stylesheet" href="/questionnaire/template/css/my-questionnaire.css">  -->

</head>

<body class="stopPoint" style="height: 100%;">
	<div id="navbar">
		<div class="header" style="width: 100%">
			<input type="hidden" value="${isManager}" id="isManager" />
			<a class="page-title ">问卷调查系统</a>
			<c:forEach items="${trperVos}" var="item" varStatus="index">
				<a  <c:if test="${index.index == '0'}">class="active"</c:if> <c:if test="${index.index == '0'}">activePath="${item.path}"</c:if> onclick="toSearchs(this,'${item.path}','contentMain')">${item.name}</a>
			</c:forEach>
			<%--<c:if test="${trperVos == '4'}">--%>
				<%--<a class="active" onclick="toSearchs(this,'/questionnaire/reel/firstindex','contentMain')">创建问卷</a>--%>
				<%--<a onclick="toSearchs(this,'/questionnaire/reel/list','contentMain')">我的问卷</a>--%>
				<%--<a onclick="toSearchs(this,'/questionnaire/answer/mine','contentMain')">我的填报</a>--%>
				<%--<!-- <a onclick="changePage(event)">填报中心</a> -->--%>
				<%--<a onclick="toSearchs(this,'/questionnaire/user/system','contentMain')">系统管理</a>--%>
			<%--</c:if>--%>
			<%--<c:if test="${isManager == '0'}">--%>
				<%--<a  class="active" onclick="toSearchs(this,'/questionnaire/answer/mine','contentMain')">我的填报</a>--%>
				<%--<a onclick="toSearchs(this,'/questionnaire/answer/unmine','contentMain')">未答问卷</a>--%>
			<%--</c:if>--%>
			<div class="login-area">
				<!--  <div class="bell"></div> -->
				<div class="user-icon">
					<i></i>
					<span class="nickname ellipsis">${username}</span>
				</div>
				<!-- <div class="btn-exit">退出</div> -->
			</div>
		</div>


	</div>
	<div class="contentMain bodyData"></div>
	<script src="/questionnaire/template/jquery/jquery-1.11.0.min.js"></script>
	<%--<script type="text/javascript" src="/questionnaire/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>--%>
	<script type="text/javascript" src="/questionnaire/template/zui/js/zui.min.js"></script>
	<script type="text/javascript" src="/questionnaire/template/zui/lib/datagrid/zui.datagrid.min.js"></script>
	<script type="text/javascript" src="/questionnaire/template/lib/layui-v2.5.4/layui.all.js"></script>
	<script type="text/javascript" src="/questionnaire/template/js/common/nav.js"></script>
	<script type="text/javascript">
		$(function () {
			var activePath = $(".header a.active").attr("activePath")
			toSearchs(this, activePath, 'contentMain')
			// var isManager = $("#isManager").val();
			// if (isManager == '4') {
			// 	toSearchs(this, '/questionnaire/reel/firstindex', 'contentMain')
			// } else {
			// 	toSearchs(this, '/questionnaire/answer/mine', 'contentMain')
			// }
		})

	</script>
</body>

</html>