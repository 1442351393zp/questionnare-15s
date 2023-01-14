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
<base href="">
	<meta charset="UTF-8">
    <title>系统管理</title>
    <style>
    	.content input,.content :after,.content :before{
    		box-sizing:border-box !important;
    		-webkit-box-sizing: border-box !important;
    	}
    	body,.content .systemMain{
    		font-size:13px;
    	}
    	.layui-table-cell .layui-form-checkbox[lay-skin=primary]{
    		top:7px;
    	}
    </style>
</head>
	<body>
		<div class="content">
			<div class="system-nav">
				<c:forEach items="${trperVos}" var="item" varStatus="index">
					<c:if test="${item.name == '系统管理'}">
						<c:forEach items="${item.children}" var="item1" varStatus="index1">
							<span <c:if test="${index1.index == '0'}">class="active"</c:if> <c:if test="${index1.index == '0'}">activePath="${item1.path}"</c:if> onclick="toSearch(this,'${item1.path}','','systemMain')">${item1.name}</span>
						</c:forEach>
					</c:if>
				</c:forEach>
				<%--<span class="active" onclick="toSearch(this,'/questionnaire/user/user','','systemMain')">用户管理</span>--%>
				<%--<span onclick="toSearch(this,'/questionnaire/role/role','','systemMain')">角色管理</span>--%>
				<%--<span onclick="toSearch(this,'/questionnaire/organiza/organiza','','systemMain')">机构管理</span>--%>
				<%--<span onclick="toSearch(this,'/questionnaire/premission/premission','','systemMain')">菜单管理</span>--%>
				<%--<span onclick="toSearch(this,'/questionnaire/systemLog/logjsp','','systemMain')">日志管理</span>--%>
			</div>
			<div class="systemMain"></div>
		</div>
	<%--<script src="<%=path %>/template/jquery/jquery-1.11.0.min.js"></script>--%>
    <script src="<%=path %>/template/js/common/request.js"></script>
    <script src="<%=path %>/template/js/user.js"></script>
	<script>
		$(function(){
		    var activePath = $(".system-nav span.active").attr("activePath")
			toSearch(this,activePath,'','systemMain')
		})
	</script>
	</body>
</html>