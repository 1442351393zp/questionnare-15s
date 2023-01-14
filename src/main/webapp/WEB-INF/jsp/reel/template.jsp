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
    <title>问卷模板</title>
    <link rel="stylesheet" href="<%=path%>/template/css/module/jPages.css">
    <link rel="stylesheet" href="<%=path%>/template/css/template.css">
</head>
<body>
<div class="template-box">
    <div class="template-header">
        <div class="search">
            <input type="text" class="searchIpt" placeholder="请输入标题关键字搜索模板">
            <button class="searchBtn">
                <i></i>
                搜索
            </button>
        </div>
    </div>
    <!-- 模板主体内容 -->
    <div class="template-container">
        <!-- 左侧 -->
        <div class="content-left">

            <%--<ul>--%>
                <%--<li class="category-title">--%>
                    <%--<i></i>--%>
                    <%--场景分类--%>
                <%--</li>--%>
                <%--<li class="category-item">--%>
                    <%--<a href="javascript:void;">--%>
                        <%--<span> 调查</span>--%>
                        <%--<span>24</span>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <%--<li class="category-item">--%>
                    <%--<a href="javascript:;">--%>
                        <%--<span>测试</span>--%>
                        <%--<span>2</span>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <%--<li class="category-item">--%>
                    <%--<a href="javascript:;">--%>
                        <%--<span>满意度</span>--%>
                        <%--<span>10</span>--%>
                    <%--</a>--%>
                <%--</li>--%>
            <%--</ul>--%>
            <%--<ul>--%>
                <%--<li class="category-title">--%>
                    <%--<i></i>--%>
                    <%--行业分类--%>
                <%--</li>--%>
                <%--<li class="category-item">--%>
                    <%--<a href="javascript:;">--%>
                        <%--<span>互联网</span>--%>
                        <%--<span></span>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <%--<li class="category-item">--%>
                    <%--<a href="javascript:;">--%>
                        <%--<span>生活商业</span>--%>
                        <%--<span></span>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <%--<li class="category-item">--%>
                    <%--<a href="javascript:;">--%>
                        <%--<span>食品保健</span>--%>
                        <%--<span></span>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <%--<li class="category-item">--%>
                    <%--<a href="javascript:;">--%>
                        <%--<span>娱乐</span>--%>
                        <%--<span></span>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <%--<li class="category-item">--%>
                    <%--<a href="javascript:;">--%>
                        <%--<span>个人</span>--%>
                        <%--<span></span>--%>
                    <%--</a>--%>
                <%--</li>--%>
            <%--</ul>--%>
        </div>
        <div class="template-content">
            <div class="content-center">
                <h1>
                    <i>全部</i>
                    <span></span>
                </h1>
                <ul id="itemContainer">
                    <%--<li class="active">社交网站满意度问卷模板</li>--%>
                    <%--<li>社交网站满意度问卷模板</li>--%>
                    <%--<li>社交网站满意度问卷模板</li>--%>
                    <%--<li>社交网站满意度问卷模板</li>--%>
                    <%--<li>社交网站满意度问卷模板</li>--%>
                    <%--<li>社交网站满意度问卷模板</li>--%>
                    <%--<li>社交网站满意度问卷模板</li>--%>
                    <%--<li>社交网站满意度问卷模板</li>--%>
                </ul>
                <div class="holder">
                    <%--<i>&lt;</i>--%>
                    <%--<div class="pageBtn">--%>
                        <%--<span>1</span>--%>
                        <%--<span>2</span>--%>
                        <%--<span>3</span>--%>
                    <%--</div>--%>
                    <%--<i>&gt;</i>--%>
                </div>
            </div>
            <div class="content-right">
                <div class="mouldBox">
                    <div class="contentRightCon">
                        <div class="contentRightConTitle"></div>
                        <div class="contentRightConBot"></div>
                    </div>
                    <div class="submitModel">使用该模板创建问卷</div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--<script src="<%=path%>/template/jquery-3.4.1/jquery-3.4.1.min.js"></script>--%>
<script src="<%=path%>/template/js/common/jPages.js"></script>
<script src="<%=path%>/template/js/template.js"></script>
</body>
</html>