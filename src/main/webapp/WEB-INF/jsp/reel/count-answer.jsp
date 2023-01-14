<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String title = request.getParameter("title");
    String reelId = request.getParameter("reelId");
    String receiverId = request.getParameter("receiverid");
    String dataIndex = request.getParameter("dataIndex");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>查看问卷统计</title>
    <link rel="shortcut icon" href="<%=path%>/template/image/logo/logo-title.ico">
    <link rel="stylesheet" href="<%=path%>/template/zui/css/zui.min.css">
    <link rel="stylesheet" href="<%=path %>/template/css/count-answer.css">
</head>
<body>
<div class="answer-box">
    <input type="hidden" id="reelId" value="<%=reelId %>">
    <input type="hidden" id="receiverId" value="<%=receiverId %>">
    <input type="hidden" id="title" value="<%=title %>">
    <input type="hidden" id="dataIndex" value="<%=dataIndex %>">
    <div class="answer-head">
        <div class="title">
            <h1><%=title %></h1>
            <span>ID:<%=reelId %></span>
        </div>
        <div class="headRight">
            <%--<a class="delete"></a>--%>
        </div>
    </div>
    <div class="answer-content">
        <div class="content-box">
            <ul>
                <%--<li>--%>
                    <%--<div class="content-main">--%>
                        <%--<div class="main-head">--%>
                            <%--<h1>基本信息</h1>--%>
                            <%--<p>编号Id：13</p>--%>
                            <%--<p>--%>
                                <%--开始时间:<span class="startTime"></span>--%>
                                <%--结束时间:<span class="endTime"></span>--%>
                            <%--</p>--%>
                        <%--</div>--%>
                        <%--<div class="main-container">--%>
                            <%--<h1>答题详情</h1>--%>
                            <%--<div class="question-list">--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<div class="content-main">--%>
                        <%--<div class="main-head">--%>
                            <%--<h1>基本信息</h1>--%>
                            <%--<p>编号Id：13</p>--%>
                            <%--<p>开始时间:2019-12-29 结束时间:2019-12-29</p>--%>
                        <%--</div>--%>
                        <%--<div class="main-container">--%>
                            <%--<h1>答题详情</h1>--%>
                            <%--<div class="question-list">--%>
                                <%--<!-- 单选题 -->--%>
                                <%--<div class="question-item">--%>
                                    <%--<div class="question-item-title">--%>
                                        <%--<span>01</span>--%>
                                        <%--<span>单选题：</span>--%>
                                        <%--<span>*</span>--%>
                                        <%--<div class="question-desc">备注</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="question-item-content">--%>
                                        <%--<div class="radio-primary radio-btn">--%>
                                            <%--<input type="radio" name="radio1" id="q-1">--%>
                                            <%--<label for="q-1">选项1</label>--%>
                                        <%--</div>--%>
                                        <%--<div class="radio-primary radio-btn">--%>
                                            <%--<input type="radio" name="radio1" id="q-2">--%>
                                            <%--<label for="q-2">选项2</label>--%>
                                        <%--</div>--%>
                                        <%--<div class="radio-primary radio-btn">--%>
                                            <%--<input type="radio" name="radio1" id="q-3">--%>
                                            <%--<label for="q-3">选项3</label>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<!-- 多选题 -->--%>
                                <%--<div class="question-item">--%>
                                    <%--<div class="question-item-title">--%>
                                        <%--<span>02</span>--%>
                                        <%--<span>多选题：</span>--%>
                                        <%--<span>*</span>--%>
                                    <%--</div>--%>
                                    <%--<div class="question-item-content">--%>
                                        <%--<div class="checkbox-primary checkbox-btn">--%>
                                            <%--<input type="checkbox" id="primary1">--%>
                                            <%--<label for="primary1">--%>
                                                <%--多选 1--%>
                                            <%--</label>--%>
                                        <%--</div>--%>
                                        <%--<div class="checkbox-primary checkbox-btn">--%>
                                            <%--<input type="checkbox" id="primary2">--%>
                                            <%--<label for="primary2">--%>
                                                <%--多选 2--%>
                                            <%--</label>--%>
                                        <%--</div>--%>
                                        <%--<div class="checkbox-primary checkbox-btn">--%>
                                            <%--<input type="checkbox" id="primary3">--%>
                                            <%--<label for="primary3">--%>
                                                <%--多选 3--%>
                                            <%--</label>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <!--<li>
                    <div class="content-main">3</div>
                </li>-->
            </ul>
            <a class="prev"></a>
            <a class="next"></a>
        </div>
    </div>
</div>
<script src="<%=path %>/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>
<%--<script src="<%=path%>/template/zui/js/zui.min.js"></script>--%>
<script src="<%=path %>/template/js/count-answer.js"></script>
</body>
</html>