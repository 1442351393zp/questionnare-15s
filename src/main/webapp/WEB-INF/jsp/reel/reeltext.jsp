<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <style>

        .dataChildStyle div:nth-child(3) {
            background: none !important;
            padding: 0 0 0 20px !important;
            color: #999 !important;
        }

        .operation .effect-operator .dataChildStyle div:nth-child(3):hover {
            background: rgba(0, 0, 0, 0.2) !important;
            color: #145ccd !important;
            border-radius: 0 !important;
        }

        .operation .effect-operator .dataChildStyle > div:hover {
            background: rgba(0, 0, 0, 0.2);
            color: #145ccd;
        }
    </style>
    <base href="">

    <!-- jsp文件头和头部 -->
    <%-- <%@ include fireeltextle="top.jsp"%> --%>
    <meta charset="UTF-8">
    <title>我的问卷</title>
    <link rel="stylesheet" href="<%=path %>/template/css/reset.css">
    <link rel="stylesheet" href="<%=path %>/template/css/my-questionnaire.css">
    <link rel="stylesheet" href="<%=path %>/template/css/subject.css">
    <%--  <script src="<%=path %>/template/js/page.js"></script> --%>
    <%--<script type="text/javascript" src="<%=path %>/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>--%>
    <script src="<%=path %>/template/js/common/nav.js"></script>
    <!--zui样式-->
    <link rel="stylesheet" href="<%=path %>/template/zui/css/zui.min.css"/>
    <link rel="stylesheet" href="<%=path %>/template/css/common.css">
    <link rel="stylesheet" href="<%=path %>/template/css/statistics.css">
    <link rel="stylesheet" href="<%=path %>/template/css/reeltext.css">
</head>
<body>
<!-- 页面顶部¨ -->
<%-- <%@ include file="head.jsp"%> --%>

<form id="param" method="post">
    <input class="rId" name="rId" value="${rId}" type="hidden"/>
    <input name="userId" id="userId" value="${userId}" type="hidden"/>
    <input id="rstatus" class="reeltype" name="reelStatus" value="${reelStatus}" type="hidden"/>
</form>
<div class="operation clearfix">
    <!--基本操作-->
    <div class="base-operator">
        <div class="active" onclick="toSearch(this,'/questionnaire/reel/editreeltext','param','main')">编辑</div>
        <c:if test="${rId !=''}">
            <!-- <div onclick="changeOperation(event)">皮肤</div> -->
            <div onclick="toSearch(this,'/questionnaire/question/tosetup','param','main')">设置</div>
            <!-- <div onclick="changeOperation(event)">投放</div> -->
            <div onclick="toSearch(this,'/questionnaire/reel/statistics','param','main')">统计</div>
        </c:if>
        <c:if test="${ rId ==''}">
            <!-- <div>皮肤</div> -->
            <div class="set-btn2">设置</div>
            <!-- <div>投放</div> -->
            <div class="count-btn2">统计</div>
        </c:if>

    </div>
    <!--效果预览-->
    <div class="effect-operator">
        <div></div>
        <%--<div onclick="toSearch(this,'/questionnaire/reel/editpage','param','contentMain')"><i></i><span>高级编辑</span></div>--%>
        <div onclick="addoreditreel()"><i></i><span>高级编辑</span></div>
        <div class="active" onclick="toPreview()"><i></i><span>预览</span></div>
        <div onclick="startRecover(this)">
            <i></i>
            <span id="button" type="${reelStatus}">
               <c:if test="${reelStatus=='0'}">暂停调查</c:if><c:if test="${reelStatus=='1'}">开始调查</c:if>
            </span>
        </div>
    </div>
</div>

<div class="main clearfix">
    <%--<div class="editor-container">
        <!--编辑器区域-->
        <div class="text-editor">
            <div class="line-number"></div>
            <div class="editor-area" contenteditable="true"></div>
        </div>
        <!--效果区域-->
        <div class="result-area">

        </div>
    </div>--%>
</div>

<%--<script src="<%=path %>/template/js/reeltext.js"></script>--%>
<script>
    $(function () {
        //initValue();
        //initLineNumber();
        //编辑
        toSearch(this,'/questionnaire/reel/editreeltext','param','main');
        console.log("2222");
        console.log($('#param' ).serialize());
        //toSearch(this,'/questionnaire/reel/ridreeltext','param','main');
        // 点击判断设置按钮是否有rId
        $(".set-btn2").on("click", function () {
            console.log("111111111111111111111111111111113");
            console.log($(".rId").val());
            if ($(".rId").val() == "") {
                alertTip("请先保存该问卷")
            } else {
                toSearch(this, '/questionnaire/question/tosetup', 'param', 'main')
            }
        });
        // 点击判断统计是否有
        $(".count-btn2").on("click", function () {
            console.log("111111111111111111111111111111114");
            console.log($(".rId").val());
            if ($(".rId").val() == "") {
                alertTip("请先保存该问卷")
            } else {
                toSearch(this, '/questionnaire/reel/statistics', 'param', 'main')
            }

        })

    })

    function addoreditreel() {
        var rId = $("#param").find(".rId").val()
        if (rId == '') {
            //跳转到新增/main
            //保存模板示例的值
            var flag =renderQuestion();
            if(flag){
                var rId = $("#param").find(".rId").val();
                //toSearch(this, '/questionnaire/reel/main', 'param', 'contentMain')
                var url = "/questionnaire/reel/main1?rId=" + rId;
                toSearchs(this, url, 'contentMain');
            }
        } else {
            //跳转到编辑/editpage
            //toSearch(this,'/questionnaire/reel/editpage','param','contentMain')
            var url = "/questionnaire/reel/main1?rId=" + rId;
            toSearchs(this, url, 'contentMain');
        }
    }

    function toPreview() {
        var rId = $("#param").find(".rId").val()
        if (rId == '') {
            alertTip("请先保存该问卷")
            return false
        } else {
            window.open("/questionnaire/question/preview?reelId=" + rId);

        }
    }

    // 点击开始回收
    function startRecover(self) {
        var rId = $("#param").find(".rId").val()
        if (rId == '') {
            alertTip("请先保存该问卷")
            return false
        } else {
            var reelStatus = ""
            if ($(self).find("span").attr("type") == "1") {
                reelStatus = "0"
            } else {
                reelStatus = "1"
            }
            $.ajax({
                type: 'post',
                url: "/questionnaire/question/startresearch",
                //data:$('#param').serialize(),
                data: {
                    rId: $(".rId").val(),
                    reelStatus: reelStatus
                },
                success: function (data) {
                    var data = $.parseJSON(data);
                    if (data.reelStatus == '0') {
                        //问卷属于暂停状态，点击开始回收
                        //$(".reeltype").val("0")
                        $("#button").html("暂停调查").attr("type", data.reelStatus);
                    } else {
                        //问卷属于开始回收状态，点击暂停
                        //$(".reeltype").val("1")
                        $("#button").html("开始调查").attr("type", data.reelStatus);
                    }
                    alert(data.msg);
                }
            })
        }

    }



</script>


<%--<script src="<%=path %>/template/jquery/jquery-1.11.0.min.js"></script>--%>
<%--<script src="<%=path %>/template/zui/js/zui.min.js"></script>--%>
<%--<script src="<%=path %>/template/js/editorPlgins.js"></script>--%>

</body>
</html>