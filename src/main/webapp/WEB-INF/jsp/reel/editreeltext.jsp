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

    <input class="rId" id="itextId" name="rId" value="${rId}" type="hidden"/>
    <input name="userId" id="userId" value="${userId}" type="hidden"/>
    <input id="rstatus" class="reeltype" name="reelStatus" value="${reelStatus}" type="hidden"/>
    <input id="ridreeltext" class="ridreeltext" name="ridreeltext" value="${text}"  type="hidden"/>


<div class="main clearfix">
    <div class="editor-container">
        <!--编辑器区域-->
        <div class="text-editor">
            <div class="bottom-edit">
                <span class="seeEdit">查看编辑保存说明</span>
            </div>
            <div class="editor-box">
                <div class="line-number"></div>
                <div class="editor-area" contenteditable="true"></div>
            </div>

        </div>
        <!--效果区域-->
        <div class="result-box">
            <div class="preview-box">
                <div class="preview-title">preview</div>
                <div class="result-area">

                </div>
            </div>
        </div>
    </div>
</div>
<%--提示示例弹出框--%>
<div class="modal fade" id="seeDialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header ">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">说明</h4>
            </div>
            <div class="modal-body">
                <h1>文本格式要求</h1>
                <p>1.左侧文本编辑第一行文字为标题</p>
                <p>2.欢迎语可不填写</p>
                <p>3.备注请在题目下换行,用英文括号:(),或中文括号:（）包裹,备注可删除不填写</p>
                <p>4.鼠标从左侧文本编辑离开,点击以外的区域,进行查看保存问卷</p>
                <h1>格式说明</h1>
                <p>题型符号：[单选题],【单选题】,[多选题],【多选题】,必须以这其中一种为结尾</p>
                <p>题目备注符号：(),（）</p>
                <p>分页符号：===分页===</p>
            </div>
            <div class="bottonDialog">
                <div class="cancel">关闭</div>
            </div>

        </div>
    </div>
</div>
<%--<script src="<%=path %>/template/jquery/jquery-1.11.0.min.js"></script>--%>
<%--<script src="<%=path %>/template/zui/js/zui.min.js"></script>--%>
<%--<script src="<%=path %>/template/js/editorPlgins.js"></script>--%>
<script src="<%=path %>/template/js/reeltext.js"></script>
<script>
    $(function () {
        var text = $('#ridreeltext').val();
        console.log("edittext:"+text);
        initValue(text);
        initLineNumber();
        //编辑加在完数据回显
        renderQuestionnoajax();
        //编辑
        console.log("editreeltext2222");
        console.log($('#param' ).serialize());
        //点击判断设置按钮是否有rId
        $(".set-btn2").on("click", function () {
            console.log("editreeltext22223333333");
            console.log($(".rId").val());
            if ($(".rId").val() == "") {
                alertTip("请先保存该问卷")
            } else {
                toSearch(this, '/questionnaire/question/tosetup', 'param', 'main')
            }
        });
        // 点击判断统计是否有
        $(".count-btn2").on("click", function () {
            console.log("editreeltext2222444444");
            console.log($(".rId").val());
            if ($(".rId").val() == "") {
                alertTip("请先保存该问卷")
            } else {
                toSearch(this, '/questionnaire/reel/statistics', 'param', 'main')
            }

        })
        // 点击查看示例
        $(".seeEdit").on("click",function(){
            $("#seeDialog").modal()
        })
        // 点击关闭弹窗
        $(".cancel").on("click",function(){
            $("#seeDialog").modal("hide")
        })
    })





</script>




</body>
</html>