<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>设置</title>
    <link rel="stylesheet" href="<%=path %>/template/zui/css/zui.min.css">
    <!-- 引入时间选择器样式 -->
    <link rel="stylesheet" href="<%=path %>/template/zui/lib/datetimepicker/datetimepicker.min.css">
    <!-- 引入弹出框样式 -->
    <link rel="stylesheet" href="<%=path %>/template/zui/lib/bootbox/bootbox.min.css">
    <%--引入ztree树样式--%>
    <link rel="stylesheet" href="<%=path %>/template/js/zTree_v3/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="<%=path %>/template/css/common.css">
    <link rel="stylesheet" href="<%=path %>/template/css/setting.css">
</head>
<body>
	<div class="setting-box">
        <div class="content">
            <!-- 问卷显示 -->
            <div class="coolumn">
                <h1 class="title">问卷显示</h1>
                <div class="setting-list">
                    <div class="setting-item">
                        <div class="switch switch-inline">
                            <input type="checkbox" index="1" <c:if test='${reel.showNo == 0}'>checked</c:if>>
                            <%--<input type="checkbox" index="1" onclick="return false;" checked>--%>
                            <label>
                                <div class="collapse-list">
                                    <div class="collapse-title">问卷显示问题编号</div>
                                </div>
                            </label>
                        </div>
                    </div>


                    <div class="setting-item">
                        <%--<div class="switch switch-inline">
                            <input type="checkbox" index="8" <c:if test='${reel.canal == 0}'>checked</c:if>>
                            <label>
                                <div class="collapse-list">
                                    <div class="collapse-title">发布渠道</div>
                                </div>
                            </label>
                        </div>--%>
                        <div class="collapse-content2">
                            <div class="canal">
                                <div class="switch switch-inline">
                                    <input type="checkbox" index="9" <c:if test='${reel.canalOnline == 0}'>checked</c:if>>
                                    <label>
                                        <div class="collapse-title">在线</div>
                                    </label>
                                </div>
                                <div class="switch switch-inline">
                                    <input type="checkbox" index="10" <c:if test='${reel.canalEmail == 0}'>checked</c:if>>
                                    <label>
                                        <div class="collapse-title">邮件</div>
                                    </label>
                                </div>
                                <div class="switch switch-inline">
                                    <input type="checkbox" index="11" <c:if test='${reel.canalMsn == 0}'>checked</c:if>>
                                    <label>
                                        <div class="collapse-title">即时通讯</div>
                                    </label>
                                </div>
                            </div>

                        </div>


                    </div>


                </div>
            </div>
            <!-- 回收设置 -->
            <div class="coolumn">
                <h1 class="title">回收设置</h1>
                <div class="setting-list">
                    <div class="setting-item endDate">
                        <div class="switch switch-inline ">
                            <input type="checkbox" index="2" <c:if test='${reel.setup == 0}'>checked</c:if>>
                            <label>设定问卷结束时间</label>
                        </div>
                        <div class="collapse-content">
                            <input type="text" id="endTime" class="form-control form-datetime" placeholder="选择日期和时间" value="${reel.endTime}">
                        </div>
                    </div>
                    <div class="setting-item">
                        <div class="switch switch-inline">
                            <input type="checkbox" index="5" <c:if test='${reel.retrieve == 0}'>checked</c:if>>
                            <label>
                                <div class="collapse-list">
                                    <div class="collapse-title">问卷未答即将过期消息提醒(过期前三小时)</div>
                                </div>
                            </label>
                        </div>
                    </div>
                    <div class="setting-item">
                        <div class="switch switch-inline">

                            <input type="checkbox" index="3" <c:if test='${reel.anonymous == 0||reel.anonymous =="" }'>checked</c:if> >

                            <label>匿名答题</label>
                        </div>
                    </div>
                    <div class="setting-item">
                        <div class="switch switch-inline">
                            <input type="checkbox" index="4" <c:if test='${reel.onceChance == 0}'>checked</c:if>>
                            <label>
                                <div class="collapse-list">
                                    <div class="collapse-title">每个用户只能回答一次</div>
                                </div>
                            </label>
                        </div>
                    </div>

                </div>
            </div>
            <!-- 参与人员 -->
            <div class="coolumn">
                <h1 class="title">参与人员</h1>
                <div class="setting-list">
                    <div class="setting-item people">
                        <div class="switch switch-inline">
                            <input type="checkbox" index="6" <c:if test='${reel.isLimit == 0}'>checked</c:if>>
                            <label>
                                <div class="collapse-list">
                                    <div class="collapse-title">限定回答用户范围</div>
                                </div>
                            </label>
                        </div>
                    </div>
                    <div class="setting-item people">
                        <div class="switch switch-inline">
                            <input type="checkbox" index="7" <c:if test='${reel.initiate == 0}'>checked</c:if>>
                            <label>
                                <div class="collapse-list">
                                    <div class="collapse-title">问卷发起消息提醒</div>
                                </div>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="select-list">

                </div>
            </div>
        </div>
        <!-- 底部   -->
       <%--<div class=setUp-foot>--%>
        <%--<%@ include file="foot.jsp"%>--%>
       <%--</div>--%>
    </div>
     
    <form id="userForm" method="post">
    <input id="userNames" type="hidden" name="userNames"/>
    <input id="userIds" type="hidden" name="userIds"/>
    <input type="hidden" id="reelId" name="reelId" value="${reel.rId}">
    <div class="modal fade" id="myModal">
        <div class="modal-dialog user-list">
            <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">用户列表</h4>
            </div>
            <div class="modal-body user-box">
                <div class="user-tree ztree" id="user-tree"></div>
                <div class="user-list-right">

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="saveSelUser('${reel.rId}')">保存</button>
            </div>
            </div>
        </div>
    </div>
    </form>
    <%--<script src="<%=path %>/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>--%>
    <%--<script src="<%=path %>/template/zui/js/zui.min.js"></script>--%>
    <!-- 引入时间选择器js -->
    <script src="<%=path %>/template/zui/lib/datetimepicker/datetimepicker.min.js"></script>
    <!-- 引入弹出框js -->
    <script src="<%=path %>/template/zui/lib/bootbox/bootbox.min.js"></script>
    <%--引入ztree树--%>
    <script src="<%=path %>/template/js/zTree_v3/js/jquery.ztree.core.min.js"></script>
    <script src="<%=path %>/template/js/zTree_v3/js/jquery.ztree.excheck.min.js"></script>
    <script src="<%=path %>/template/js/setting.js"></script>

</body> 
