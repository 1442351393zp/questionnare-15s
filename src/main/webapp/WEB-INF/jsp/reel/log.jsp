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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>日志管理</title>
<body>
    <div class="container_box">
        <div class="content-box">
            <div class="search-box">
                <div class="input-box">
                    <input type="text" class="form-control search-nick" placeholder="请输入操作用户">
                </div>
                <div class="input-box">
                    <input type="text" class="form-control search-phone" placeholder="请输入操作描述">
                </div>
                <button class="btn btn-primary search" type="button">查询</button>
                <button class="btn btn-primary reset" type="button">重置</button>
            </div>
            <%--<div class="nav-btn">
                <button class="btn btn-primary delete-user" type="button">删除</button>
            </div>--%>
            <div class="user-list">
            	<table class="layui-hide" id="userList" lay-filter="currentTableFilter"></table>
            </div>
        </div>

	</div>
	<script src="<%=path %>/template/jquery/jquery-1.11.0.min.js"></script>
   	<script src="<%=path %>/template/zui/js/zui.min.js"></script>
    <script src="<%=path %>/template/zui/lib/datagrid/zui.datagrid.min.js"></script>
    <script src="<%=path %>/template/lib/layui-v2.5.4/layui.all.js"></script>
    <script src="<%=path %>/template/js/common/request.js"></script>
    <script src="<%=path %>/template/js/log.js"></script>
</body>

</html>