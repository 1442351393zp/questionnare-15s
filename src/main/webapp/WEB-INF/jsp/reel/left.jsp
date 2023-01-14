<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
    <!--<script type="text/javascript" src="/questionnaire/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>
	 <link rel="stylesheet" href="/questionnaire/template/css/reset.css">
    <link rel="stylesheet" href="/questionnaire/template/css/common.css">
    <link rel="stylesheet" href="/questionnaire/template/css/home.css">
    <link rel="stylesheet" href="/questionnaire/template/css/my-questionnaire.css"> -->
</head>
<body>
<%--<div id="sidebar" >--%>
  
     <!--菜单栏-->
        <div class="menu">
            <div class="menu-tab clearfix">
                <div class="tab-component active" data-type="components" onclick="changeMenu(event,'${rId }')">题目控件</div>
                <div class="tab-outline" data-type="outline" onclick="changeMenu(event,'${rId }')">问卷大纲</div>
            </div>
            <div class="menu-content">
                <!--题目控件列表-->
                <div class="components">
                    <div class="question-type" index="1"><i class="radio-choice-question"></i><span>单选题</span></div>
                    <%--<div class="question-type" index="3"> <i class="dropdown-question"></i><span>下拉题</span></div>--%>
                    <div class="question-type" index="2"><i class="multiple-choice-question"></i><span>多选题</span></div>
                    <div class="question-type" index="4"><i class="single-line-text"></i><span>问答题</span></div>
                    <%--<div class="question-type"><i class="multi-line-text"></i><span>多行文本题</span></div>--%>
                    <%--<div class="question-type"><i class="scale-question"></i><span>量表题</span></div>--%>
                    <%--<div class="question-type"><i class="matrix-radio"></i><span>矩阵单选题</span></div>--%>
                    <%--<div class="question-type"><i class="matrix-multi"></i><span>矩阵多选题</span></div>--%>
                    <%--<div class="question-type"><i class="matrix-scale"></i><span>矩阵量表题</span></div>--%>
                    <%--<div class="question-type"><i class="sort-question"></i><span>排序题</span></div>--%>
                    <%--<div class="question-type"><i class="linkage-question"></i><span>联动题</span></div>--%>
                    <%--<div class="question-type"><i class="annex-question"></i><span>附件题</span></div>--%>
                    <%--<div class="question-type"><i class="text-description"></i><span>文本描述</span></div>--%>
                    <%--<div class="question-type"><i class="fill-in-blank"></i><span>填空题</span></div>--%>
                </div>
                <!--大纲列表-->
                <div class="outline dragDrop" style="display: none">

                </div>
            </div>
        </div>
<%--</div>--%>

</body>
</html>