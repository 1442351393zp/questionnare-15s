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
<!doctype html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="<%=path %>/template/css/fill-situation.css">
</head>
<body>
<div class="situation-box">
    <div class="situation-title">
        <h1>${result.reelName}</h1>
        <span>回收量${result.receivedCount}</span>
    </div>
    <div class="line-content">
        <div class="line-head">
            <dl>
                <dt>回收量</dt>
                <dd>${result.receivedCount}</dd>
            </dl>
            <dl>
                <dt>浏览量</dt>
                <dd>${result.browseCount}</dd>
            </dl>
            <dl>
                <dt>回收率</dt>
                <dd>${result.receivedRate}</dd>
            </dl>
            <dl>
                <dt>平均完成时间</dt>
                <dd>${result.receivedAvgTime}</dd>
            </dl>
        </div>
        <div class="line-echarts">
            <div id="lineEcharts" style="width: calc(100% - 60px); height: 500px;"></div>
        </div>
    </div>
</div>
<%--<script src="js/jquery-3.4.1/jquery-3.4.1.min.js"></script>--%>
<script src="<%=path %>/template/js/echarts.js"></script>
<script src="<%=path %>/template/js/fill-situation.js"></script>
</body>
</html>