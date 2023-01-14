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
		<link rel="stylesheet" href="<%=path %>/template/css/module/jquery.treetable.css">
		<link rel="stylesheet" href="<%=path %>/template/css/module/jquery.treetable.theme.default.css">
		<link rel="stylesheet" href="<%=path %>/template/css/menu.css">
		<title>菜单管理</title>
	</head>
	<body>
		<div class="menu-box">
			<div class="nav-btn">
				<button class="btn btn-primary expand" type="button">全部展开</button>
				<button class="btn btn-primary collapseAll" type="button">全部折叠</button>
				<button class="btn btn-primary newMenu" type="button" data-index="1">新建菜单</button>
			</div>
			<table id="menu-list">
				<thead>
					<th>菜单名称</th>
					<th>分类</th>
					<th>菜单路径</th>
					<th>菜单权限</th>
					<th>操作</th>
				</thead>
				<tbody>
					
				</tbody>
			</table>
		</div>
		<div class="modal fade" id="addMenu">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
		                <h4 class="modal-title">添加菜单</h4>
		            </div>
		            <div class="modal-body">
		                <!-- 弹出框主体内容 -->
		                <div class="addUserBox form-horizontal">
		                    <div class="form-group">
		                        <label class="col-sm-2 required">菜单名称：</label>
		                        <div class="col-md-6 col-sm-10">
		                            <input type="text" class="form-control menuName" placeholder="请输入菜单名称">
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <label class="col-sm-2 required">菜单路径：</label>
		                        <div class="col-md-6 col-sm-10">
		                            <input type="text" class="form-control menuUrl" placeholder="请输入菜单路径">
		                        </div>
		                    </div>
							 <div class="form-group">
							    <label class="col-sm-2 required">菜单权限：</label>
							    <div class="col-md-6 col-sm-10">
							        <input type="text" class="form-control menuAuth" placeholder="请输入菜单权限">
							    </div>
							</div>
		                    <div class="form-group">
		                        <label class="col-sm-2">描述：</label>
		                        <div class="col-md-6 col-sm-10">
									<textarea  class="form-control describe"></textarea>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		                <button type="button" class="btn btn-primary saveMenu">保存</button>
		            </div>
		        </div>
		    </div>
		</div>
		<script src="<%=path %>/template/js/module/jquery.treetable.js"></script>
		<script src="<%=path %>/template/js/menu.js"></script>
	</body>
    
</html>