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
    <title>用户管理</title>
    <link rel="stylesheet" href="<%=path %>/template/js/zTree_v3/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="<%=path %>/template/css/role.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/template/css/jquery.shCircleLoader.css">
    <script type="text/javascript" src="<%=path %>/template/js/jquery.shCircleLoader.js"></script>
     
 <%-- 	<link rel="stylesheet" href="<%=path %>/template/zui/css/zui.min.css">
    <link rel="stylesheet" href="<%=path %>/template/zui/lib/datagrid/zui.datagrid.min.css">
    <link rel="stylesheet" href="<%=path %>/template/lib/layui-v2.5.4/css/layui.css">
    <link rel="stylesheet" href="<%=path %>/template/css/user.css">
     --%>
<body>
    <div class="container_box">
        <div class="content-box">
            <div class="search-box">
                <div class="input-box">
                    <input type="text" class="form-control search-phone" placeholder="请输入帐号">
                </div>
                <div class="input-box">
                    <input type="text" class="form-control search-nick" placeholder="请输入昵称">
                </div>
                <button class="btn btn-primary search" type="button">查询</button>
                <button class="btn btn-primary reset" type="button">重置</button>
            </div>
            <div class="nav-btn">
               <!--  <button class="btn btn-primary add-user" type="button">添加</button>
                <button class="btn btn-primary upload-user" type="button">修改</button>
                <button class="btn btn-primary delete-user" type="button">删除</button> -->
				<button class="btn btn-primary user-role" type="button">用户角色</button>
				<%--<button class="btn btn-primary asyncUser" type="button">同步组织用户</button>--%>
				<button class="btn btn-primary user-institution" type="button">用户所属机构</button>
                <button class="btn btn-primary asyncUserall" type="button">全部重新同步组织用户</button>
            </div>
            <div class="user-list">
            	<table class="layui-hide" id="userList" lay-filter="currentTableFilter"></table>
            </div>
        </div>
		<!-- 添加和修改用户弹框 -->
        <div class="modal fade" id="addUser">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                        <h4 class="modal-title">添加用户</h4>
                    </div>
                    <div class="modal-body">
                        <!-- 弹出框主体内容 -->
                        <div class="addUserBox form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 required">用户名：</label>
                                <div class="col-md-6 col-sm-10">
                                    <input type="text" class="form-control userName" placeholder="请输入用户名" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group pwd-group">
                                <label class="col-sm-2 required">密码：</label>
                                <div class="col-md-6 col-sm-10">
                                    <input type="password" class="form-control password" placeholder="请输入密码" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group pwd-group">
                                <label class="col-sm-2 required">确认密码：</label>
                                <div class="col-md-6 col-sm-10">
                                    <input type="password" class="form-control pwd" placeholder="请输入确认密码" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 required">昵称：</label>
                                <div class="col-md-6 col-sm-10">
                                    <input type="text" class="form-control nickName" id="nickName" placeholder="请输入昵称" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 required">性别：</label>
                                <div class="col-md-6 col-sm-10">
                                    <div class="radio-primary radio-btn">
										<input type="radio" name="sex" checked value="男">
										<label>男</label>
									</div>
									<div class="radio-primary radio-btn">
										<input type="radio" name="sex" value="女">
										<label>女</label>
									</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 required">Email：</label>
                                <div class="col-md-6 col-sm-10">
                                    <input type="text" class="form-control email" placeholder="请输入邮箱" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 required">手机号：</label>
                                <div class="col-md-6 col-sm-10">
                                    <input type="text" class="form-control phone" placeholder="请输入手机号" autocomplete="off">
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
                        <button type="button" class="btn btn-primary saveUser">保存</button>
                    </div>
                </div>
            </div>
        </div>
		<!-- 用户角色弹框 -->
		 <div class="modal fade" id="userRole">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
		                <h4 class="modal-title">用户角色管理</h4>
		            </div>
		            <div class="modal-body">
		                <!-- 弹出框主体内容 -->
		                <table id="roleList"></table>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		                <button type="button" class="btn btn-primary saveRole">保存</button>
		            </div>
		        </div>
		    </div>
		</div>
		<!-- 用户所属机构弹框 -->
		<div class="modal fade" id="userInstitution">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
		                <h4 class="modal-title">用户所属机构</h4>
		            </div>
		            <div class="modal-body">
		                <!-- 弹出框主体内容 -->
		                <div id="institutionList" class="ztree"></div>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		            </div>
		        </div>
		    </div>
		</div>
	</div>
    <div id="dataLoadingWait" style="position: absolute; top:50%; margin-top:-45px; left:50%; margin-left:-45px;"></div>
	<%--<script src="<%=path %>/template/jquery/jquery-1.11.0.min.js"></script>--%>
   	<%--<script src="<%=path %>/template/zui/js/zui.min.js"></script>--%>
    <script src="<%=path %>/template/zui/lib/datagrid/zui.datagrid.min.js"></script>
    <script src="<%=path %>/template/lib/layui-v2.5.4/layui.all.js"></script>
    <script src="<%=path %>/template/js/common/request.js"></script>
    <script src="<%=path %>/template/js/zTree_v3/js/jquery.ztree.core.min.js"></script>
	<script src="<%=path %>/template/js/zTree_v3/js/jquery.ztree.excheck.min.js"></script>
    <script src="<%=path %>/template/js/user.js"></script>
</body>
    
</html>