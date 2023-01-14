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
    <!--  <link rel="stylesheet" href="/questionnaire/template/css/reset.css"> -->
    <link rel="stylesheet" href="/questionnaire/template/css/common.css">
    <link rel="stylesheet" href="/questionnaire/template/css/home.css">
    <link rel="stylesheet" href="/questionnaire/template/css/system.css" />
    <link rel="stylesheet" href="/questionnaire/template/css/user.css">
    <link rel="stylesheet" href="/questionnaire/template/css/statistics.css">
    <link rel="stylesheet" href="/questionnaire/template/css/stat.css">
    <!-- <link rel="stylesheet" href="/questionnaire/template/css/my-questionnaire.css">  -->
    <style>
        .main{
            height:calc(100% - 63px);
        }
        .main .statistics-main{
            height: calc(100% - 40px);
        }
        .main .slider-menu{
            height: calc(100% - 63px);
        }
    </style>
</head>

<body class="stopPoint" style="height: 100%;">
    <form id="param" method="post">
        <input class="rId" name="rId" value="${rId}" type="hidden"/>
    </form>
    <div id="navbar">
        <div class="header" style="width: 100%">
            <a class="page-title ">问卷调查系统</a>
        </div>
    </div>
    <div class="main bodyData"></div>
    <script src="/questionnaire/template/jquery/jquery-1.11.0.min.js"></script>
    <%--<script type="text/javascript" src="/questionnaire/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>--%>
    <script type="text/javascript" src="/questionnaire/template/zui/js/zui.min.js"></script>
    <script type="text/javascript" src="/questionnaire/template/zui/lib/datagrid/zui.datagrid.min.js"></script>
    <script type="text/javascript" src="/questionnaire/template/lib/layui-v2.5.4/layui.all.js"></script>
    <script type="text/javascript" src="/questionnaire/template/js/common/nav.js"></script>
    <script type="text/javascript">
        $(function () {
            // var activePath = $(".header a.active").attr("activePath")
            // toSearchs(this, activePath, 'contentMain')
            toSearch(this,'/questionnaire/reel/statistics','param','main')
        })
    </script>
</body>

</html>