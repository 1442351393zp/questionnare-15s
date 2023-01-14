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
    <link rel="stylesheet" href="<%=path %>/template/lib/layui-v2.5.4/css/layui.css">
    <%--<link rel="stylesheet" href="<%=path %>/template/zui/css/zui.min.css">--%>
    <link rel="stylesheet" href="<%=path %>/template/zui/lib/datetimepicker/datetimepicker.min.css">
    <link rel="stylesheet" href="<%=path %>/template/css/fill-situation.css">
</head>
<body>
<div class="reclaim-box">
    <div class="situation-title">
        <h1>${result.title}</h1>
        <span>回收量${result.receivedCount}</span>
    </div>
    <div class="reclaim-content">
        <div class="reclaim-btn">
            <%--<div class="datePick">--%>
                <%--<label>时间段：</label>--%>
                <%--<div class="startDate">--%>
                    <%--<input type="text" id="startTime" class="form-control form-datetime" placeholder="选择开始日期" value="">--%>
                <%--</div>--%>

                <%--<div class="endDate">--%>
                    <%--<input type="text" id="endTime" class="form-control form-datetime" placeholder="选择结束日期" value="">--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<span class="delete"></span>--%>
        </div>
        <table class="layui-hide" id="reclaim-list" lay-filter="reclaim-list"></table>
    </div>
</div>
<%--编辑提示弹出框--%>
<div class="modal fade" id="editBox">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">编辑提示</h4>
            </div>
            <div class="modal-body">
                <p class="alertText">内容修改后将覆盖原始答案，且该操作不可撤销，确定继续编辑吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">放弃</button>
                <button type="button" class="btn btn-primary continue-edit">继续编辑</button>
            </div>
        </div>
    </div>
</div>
<%--编辑答案弹出框--%>
<div class="modal fade" id="uploadAnswer">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">修改题目</h4>
            </div>
            <div class="modal-body">
                <div class="questionnaire">
                    <div class="question-item-upload">

                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary saveAnswer">保存</button>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="see">
    <i class="see" lay-event="see"></i>
</script>
<%--<script src="<%=path %>/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>--%>
<script src="<%=path %>/template/lib/layui-v2.5.4/layui.all.js"></script>
<%--<script src="<%=path %>/template/zui/js/zui.min.js"></script>--%>
<script src="<%=path %>/template/zui/lib/datetimepicker/datetimepicker.min.js"></script>
<script src="<%=path %>/template/js/reclaim.js"></script>
</body>
</html>