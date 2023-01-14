<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	HttpSession seesion =request.getSession();
	
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>问卷调查登录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="shortcut icon" href="<%=path %>/template/image/logo/logo-title.ico">
    <link rel="stylesheet" href="<%=path %>/template/zui/css/zui.min.css">
    <link rel="stylesheet" href="<%=path %>/template/css/login.css" media="all"> 
</head>
    <body>
    <div id="container">
        <div></div>
        <div class="admin-login-background">
            <div class="admin-header">
                <span>问卷调查</span>
            </div>
            <div class="user-box">
                <div class="user-item">
                    <i class="layui-icon layui-icon-username admin-icon icon-user"></i>
                    <input type="text" name="username" placeholder="请输入用户名" 
                        class="layui-input admin-input admin-input-username username">
                </div>
                <div class="user-item">
                    <i class="layui-icon layui-icon-password admin-icon icon-lock"></i>
                    <input type="password" name="password" placeholder="请输入密码"
                        class="layui-input admin-input password">
                </div>
                <div class="user-item user-code">
                    <input type="text" name="randCode" id="randCode" placeholder="请输入验证码" 
                        class="layui-input admin-input admin-input-verify captcha">
                    <img class="admin-captcha" width="90" height="30" src="randCodeImage" id="randCodeImage">
                </div>
                <button class="admin-button submit">登 录</button>
                </form>
            </div>
        </div>
    </div>
    <script src="<%=path %>/template/jquery/jquery-1.11.0.min.js"></script>
    <script src="<%=path %>/template/zui/js/zui.min.js"></script>
    <script src="<%=path %>/template/js/common/request.js"></script>
    <script src="<%=path %>/template/js/login.js"></script>
    <script type="text/javascript">
    var flag = <%= seesion.getAttribute("flag")%>;
    $(function() {
    	if(flag == "1"){
    		$(".header").css("visibility","hidden");
    		new $.zui.Messager('登录超时，请重新登录',{
	  	        type:'danger',
	  	        close:false
	  	    }).show();
    	}
    })
   
    </script>
</body>
 <%
 seesion.removeAttribute("flag");
 %>
</html>