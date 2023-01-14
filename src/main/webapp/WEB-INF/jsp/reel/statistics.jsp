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
    <title>统计</title>
    <%--引入ztree树样式--%>
    <link rel="stylesheet" href="<%=path %>/template/js/zTree_v3/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="<%=path %>/template/css/home.css">
    <%--<link rel="stylesheet" href="<%=path %>/template/css/my-questionnaire.css">--%>

    <script src="<%=path %>/template/js/page.js"></script>
    <!-- zui -->
    <%--<link href="<%=path %>/template/zui/css/zui.min.css" rel="stylesheet">--%>
    <!--重置样式-->
    <%--<link rel="stylesheet" href="<%=path %>/template/css/reset.css">--%>
    <%--<link rel="stylesheet" href="<%=path %>/template/css/common.css">--%>
    <%--<link rel="stylesheet" href="<%=path %>/template/css/statistics.css">--%>
    <!--滚动条css-->
    <link rel="stylesheet" href="<%=path %>/template/css/scrollbar.css">
    <!--日期选择插件为独立组件，你需要从本地或 CDN 单独引入 lib 目录下的资源：-->
    <link href="<%=path %>/template/zui/lib/datetimepicker/datetimepicker.css" rel="stylesheet">
    <!--滚动条js-->
    <script src="<%=path %>/template/js/ScrollBar.js"></script>
    <!-- 图表 -->
    <script src="<%=path %>/template/echarts/echarts.min.js"></script>
    <script src="<%=path %>/template/echarts/echarts.js"></script>
	
</head>
	<body>
	<input type="hidden" id="rId" value="${rId }">
		<!--问卷主内容-->
        <%--<div class="main clearfix" style="overflow-y: scroll;">--%>
            <!--左侧菜单-->
            <div class="slider-menu clearfix" style="position: absolute;">
                <div class="wrap-slider scrollBar">
                    <div>
                        <div class="slider-item active" onclick="toSearchs(this,'/questionnaire/question/receiveDetail?reelId='+rId,'statistics-main')">
                            <div class="svg-box">
                                <svg class="icon icon-stat-overview" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 56 56">
                                    <path d="M28 4c13.2 0 24 10.8 24 24S41.2 52 28 52 4 41.2 4 28 14.8 4 28 4m0-4C12.5 0 0 12.5 0 28s12.5 28 28 28 28-12.5 28-28S43.5 0 28 0z"></path>
                                    <path d="M48 26h7v4h-7zM43.5 16.2l6.9-4 2 3.5-6.9 4-2-3.5zM35.3 9.4l4-6.9 3.5 2-4 6.9-3.5-2zM20 10l-3.8-7-3.3 2 3.8 7 3.3-2zM12 16.7l-7-3.8-2 3.3 7 3.8 2-3.3zM.8 26H8v4H.8z"></path>
                                    <g class="icon-stat-overview-needle">
                                        <path d="M28 0l-4 24 4-2.3 4 2.3-4-24z"></path>
                                        <path d="M28 25c1.1 0 2 .9 2 2s-.9 2-2 2-2-.9-2-2 .9-2 2-2m0-4c-3.3 0-6 2.7-6 6s2.7 6 6 6 6-2.7 6-6-2.7-6-6-6z"></path>
                                    </g>
                                </svg>
                            </div>
                            <span>填报概况</span>
                        </div>
                        <div class="slider-item" onclick="toSearchs(this,'/questionnaire/question/listReceivedTablePage','statistics-main')">
                            <div class="svg-box">
                                <svg class="icon icon-stat-recycle" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 53">
                                    <path d="M44 4h-4v6h-8V4H16v6H8V4H4C1.8 4 0 5.8 0 8v41c0 2.2 1.8 4 4 4h40c2.2 0 4-1.8 4-4V8c0-2.2-1.8-4-4-4zm0 45H4V14h40v35z"></path>
                                    <path class="icon-stat-recycle-top" d="M10 21h28v4H10z"></path><path class="icon-stat-recycle-middle" d="M10 29h28v4H10z"></path>
                                    <path class="icon-stat-recycle-bottom" d="M10 37h22v4H10z"></path><path d="M34 0h4v8h-4zM10 0h4v8h-4z"></path>
                                </svg>
                            </div>
                            <span>回收数据</span>
                        </div>
                        <div class="slider-item statistics">
                            <div class="svg-box">
                                <svg class="icon icon-stat-chart" xmlns="http://www.w3.org/2000/svg" viewBox="-392 270.3 56 56">
                                    <path d="M-366 296.3h26.9v4H-366z"></path><path d="M-366 270.8h4v26.9h-4z"></path>
                                    <path d="M-366 274.4c.7-.1 1.3-.1 2-.1 13.2 0 24 10.8 24 24 0 .7 0 1.3-.1 2h4c0-.7.1-1.3.1-2 0-15.5-12.5-28-28-28-.7 0-1.3 0-2 .1v4zM-340.8 304.3c-2.7 10.3-12.1 18-23.2 18-13.2 0-24-10.8-24-24 0-11.1 7.7-20.6 18-23.2V271c-12.6 2.7-22 13.9-22 27.4 0 15.5 12.5 28 28 28 13.4 0 24.6-9.4 27.4-22h-4.2z"></path>
                                </svg>
                            </div>
                            <span>统计图表</span>
                        </div>
                        <%--<div class="slider-item">交叉分析</div>
                        <div class="slider-item">中奖结果</div>
                        <div class="slider-item">报名与签到</div>--%>
                    </div>
                </div>
                <%--<div class="scroll-bar"></div>--%>
            </div>
            <!--右侧图表统计容器-->
            <div class="statistics-main"></div>

        <%--</div>--%>

    <!-- jQuery (ZUI中的Javascript组件依赖于jQuery) -->
    <%--<script src="<%=path %>/template/jquery/jquery-1.11.0.min.js"></script>--%>
    <!-- ZUI Javascript组件 -->
    <%--<script src="<%=path %>/template/zui/js/zui.min.js"></script>--%>
    <script src="<%=path %>/template/zui/lib/datetimepicker/datetimepicker.js"></script>
    <script type="text/javascript">
        var rId =$("#rId").val();
    $(function(){
        toSearchs(this,'/questionnaire/question/receiveDetail?reelId='+rId,'statistics-main');
    })
    $(".statistics").click(function(){
        toSearchs(this,'/questionnaire/reel/countPic?rId='+rId,'statistics-main');
    })
    </script>

	</body>
</html>