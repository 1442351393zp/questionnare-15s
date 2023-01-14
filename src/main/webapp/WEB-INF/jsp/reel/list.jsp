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
    <base href="<%=basePath%>">

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>我的问卷</title>
    <title>创建空白问卷- 免费好用的问卷调查系统,调查问卷,免费,简单,模板</title>
    <meta name="description" content="问卷，是免费、专业的问卷调查系统。提供多种方式创建问卷，简单高效的编辑方式，强大的逻辑设置功能，专业的数据统计和样本甄别，让您轻松开启调研工作。">
    <meta name="keywords" content="调查问卷,问卷调查,市场调研,问卷系统,问卷,模板,免费,简单,调研,市场调查,满意度,问卷调查网,在线问卷调查,微信问卷,投票小程序,投票">
    <link rel="shortcut icon" href="<%=path %>/template/image/logo/logo-title.ico">
    <link rel="stylesheet" href="<%=path %>/template/css/stat.css">
    <link rel="stylesheet" href="<%=path %>/template/css/home.css">
    <link rel="stylesheet" href="<%=path %>/template/css/create-questionnaire.css">
    <link rel="stylesheet" href="<%=path %>/template/css/create-white-question.css">
    <script src="<%=path %>/template/js/page.js"></script>
    <!-- zui -->
    <link href="<%=path %>/template/zui/css/zui.min.css" rel="stylesheet">
    <!--重置样式-->
    <link rel="stylesheet" href="<%=path %>/template/css/reset.css">
    <link rel="stylesheet" href="<%=path %>/template/css/common.css">
    <%-- <script src="<%=path %>/template/js/viewchange.js"></script> --%>
    <!-- 下拉菜单 -->
    <script src="<%=path %>/template/zui/js/zui.lite.min.js"></script>
    <style type="text/css">
        .col-title{
            font-size: 18px;
            line-height: 30px;
            color: #666;
        }
        .col-title__label{text-align: center;}
        .col-list{
            width: 100%;
            display: flex;
            flex-wrap: wrap;
        }
        .col-item{
            width: calc(14% - 22px);
            font-size: 16px;
            line-height: 50px;
            color: #666;
            margin: 5px 10px;
            text-align: center;
            border: 1px solid #EEE;
            /* color: #1b3c3e; */
            background-color: #ecd8d3;
            cursor: pointer;
        }
        .col-item.active{
            /*background-color: #b52b27;*/
            background-color: #3280fc;
            color: #FFF;
        }
        .col-title__operation{
            margin-left: 20px;
            font-size:16px;
            cursor: pointer;
        }
        .btn{font-size: 16px;}
    </style>
</head>
<body>
<!-- 页面顶部¨ -->
<%-- <%@ include file="head.jsp"%> --%>
<input type="hidden" id="viewStatus" value="${viewStatus }">
<input type="hidden" id="userId" value="${userId }">
<input type="hidden" id="rId">
<input type="hidden" id="myrId" value="">
<input type="hidden" id="mystatus" value="">
<div class="empty-page">
    <!-- 我的问卷列表 -->
    <c:if test="${folderId == null }">
        <!--副标题头-->
        <div class="sub-header clearfix" data-current="true">
            <div class="sub-header-left">
                <a class="btn btn-primary " title="列表视图" onclick="createQuestion('${folderId}')"><i class="add">+</i>创建问卷</a>
                <a class="deleteAll btn btn-primary" onclick="deleteAll()">删 除</a>
            </div>
            <div class="sub-header-right">
                <a class="sub-header-btn create-fold" title="创建文件夹"></a>
                <!-- <a class="sub-header-btn" title="创建文件夹" onclick="stopAlert()"></a> -->
                <a class="sub-header-btn" title="废纸篓" onclick="changeToTrash(event)"></a>
                <a class="sub-header-btn" title="列表视图" data-type="table" onclick="changeToGrid(event)"></a>
                <a class="sub-header-btn" title="九宫格视图" data-type="grid" style="display: none"
                   onclick="changeToTable(event)"></a>
            </div>
        </div>
        <!-- 问卷为空-->
        <div class="emptyTip" style="display: none">
            <img class="emptyTip-pic" src="<%=path %>/template/image/bg/bg-white.png">
            <p class="emptyTip-info">您还没有问卷哦</p>
            <a class="">现在去创建</a>
        </div>

        <!--副标题栏2-->
        <div class="sub-header-2" data-current="false" style="display: none">
            <a class="btn-back" onclick="backToOrigin(event)"></a>
            <a class="sub-title">废纸篓</a>
            <a class="sub-header-btn" title="列表视图" data-type="table" onclick="changeToGrid(event)"></a>
            <a class="sub-header-btn" title="九宫格视图" data-type="grid" style="display: none"
               onclick="changeToTable(event)"></a>
        </div>

        <!--视图列表-->
        <div class="wrap-grid clearfix">

            <!--问卷视图表-->
            <div class="card-grid" style="display: none">
                <!--问卷视图-->
                <c:forEach items="${FolderDTO }" var="vj" varStatus="vl">
                    <%--  <div class="card-item">
                            <div class="bg-card-image" onclick="showInfoDesc(event,'${vj.folderId}')">
                                <i class="more-btn"></i>
                            </div>
                            <div class="card-desc">
                              <div class="card-desc-title">
                                <i></i><span>${vj.folderName}</span>
                              </div>

                            </div>
                        </div> --%>
                    <!--新建文件夹-->
                    <div class="card-item">
                        <div class="icon-folder" onclick="toBasket('${vj.folderId}')">
                            <span class="icon-folder-header"></span>
                            <span class="icon-folder-middle"></span>
                            <span class="icon-folder-main"></span>
                                <%--  <i class="more-btn" onclick="folderMoreBtn(event,'${vj.folderId}')"></i> --%>
                        </div>
                        <div class="dropdown folderbtn">
                            <ul class="dropdown-menu">
                                <li class='nopoint'>
                                    <a class="delFolder" onclick="deleFolderfuc(event,'${vj.folderId}')">删除</a>
                                </li>
                                <li class='nopoint'>
                                    <a class="moveTrashBasket" onclick="moveFolderTrashBasket(event,'${vj.folderId}')">移动到废纸篓</a>
                                </li>
                                <c:if test="${vj.reelStatus== '0' }">
                                    <li class="nopoint">
                                        <a onclick="alertDetails(event,'${vj.rId}')">问卷情况</a>
                                    </li>
                                </c:if>
                            </ul>
                            <i class="more-btn baskBox" data-toggle="dropdown"></i>
                        </div>
                        <div class="new-folder" title="${vj.folderName}">${vj.folderName}</div>
                    </div>
                </c:forEach>


                <c:if test="${allSubjectDTO != null}">
                    <c:forEach items="${allSubjectDTO}" var="va" varStatus="vs">
                        <div class="card-item">
                            <div class="bg-card-image" onclick="showInfoDesc(event,'${va.rId}','${va.reelStatus}')">
                                <div class="dropdown">
                                    <ul class="dropdown-menu">
                                        <li class='nopoint'>
                                            <a class="statusView" onclick="statusfuc(event,'${va.rId}','${va.reelStatus}','${va.userId}')"></a>
                                        </li>
                                        <li class='nopoint'>
                                            <a class="moveTrashBasket" onclick="moveTrashBasket(event,'${va.rId}')">移动到废纸篓</a>
                                        </li>
                                        <li class="dropdown-submenu">
                                            <a>移动到</a>
                                            <ul class="dropdown-menu pull-left">
                                            </ul>
                                        </li>
                                        <c:if test="${va.reelStatus== '0' }">
                                            <li class="nopoint">
                                                <a onclick="alertDetails(event,'${va.rId}')">问卷情况</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                    <i class="more-btn" data-toggle="dropdown"
                                       onclick="moreBtn(event,'${va.rId}','${va.reelStatus}','${va.userId}',this)"></i>
                                </div>

                                    <%-- <i class="more-btn"  onclick="moreBtn(event,'${va.rId}','${va.reelStatus}','${va.userId}')"></i> --%>
                            </div>
                            <div class="card-desc">
                                <div class="card-desc-title">
                                    <c:if test="${va.reelStatus== '0' }">
                                        <i class="active"></i>
                                    </c:if>
                                    <c:if test="${va.reelStatus== '1' }">
                                        <i></i>
                                    </c:if>
                                    <span onclick="getReelEdit('${va.rId}','${va.pageList[0].pageId}',event)" title="${va.title}">${va.title}</span>
                                </div>
                                <em class="ellipsis">${va.rId}</em>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
            <!--问卷表格视图-->
            <div class="table-wrap">

                <table class="table table-hover">
                    <thead>
                    <tr class="all_select">
                        <th>
                            <div class="checkbox-primary">
                                <input class="listCheckbox" type="checkbox">
                                <label>&nbsp;</label>
                            </div>
                        </th>
                        <th>名称</th>
                        <th>id</th>
                            <%--<th>发布渠道</th>--%>
                        <th>状态</th>
                        <th>创建时间</th>
                        <th>回收量</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${FolderDTO }" var="vy" varStatus="vk">
                        <tr>
                            <td class="list-item">
                                <div class="checkbox-primary">
                                    <input class="tdListCheckbox" type="checkbox" value="${vy.folderId}">
                                    <label>&nbsp;</label>
                                </div>
                            </td>
                            <td class="clearfix">
                                <div class="icon-folder" onclick="toBasket('${vy.folderId}')">
                                    <span class="icon-folder-header"></span>
                                    <span class="icon-folder-middle"></span>
                                    <span class="icon-folder-main"></span>
                                </div>
                                <span>
                                    <a onclick="toBasket('${vy.folderId}')" title="${vy.folderName}">${vy.folderName}</a>
                                </span>
                            </td>
                            <td colspan="4"></td>
                            <td>
                                <div class="dropdown">
                                    <ul class="dropdown-menu">
                                        <li class='nopoint'>
                                            <a class="delFolder" onclick="deleFolderfuc(event,'${vy.folderId}')">删除</a>
                                        </li>
                                        <li class='nopoint'>
                                            <a class="moveTrashBasket"
                                               onclick="moveFolderTrashBasket(event,'${vy.folderId}')">移动到废纸篓</a>
                                        </li>
                                        <c:if test="${varc.reelStatus== '0' }">
                                            <li class="nopoint">
                                                <a onclick="alertDetails(event,'${vy.rId}')">问卷情况</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                    <i class="more-btn" data-toggle="dropdown"></i>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>


                    <c:if test="${allSubjectDTO != null}">
                        <c:forEach items="${allSubjectDTO}" var="varc" varStatus="vsc">
                            <tr onclick="showInfoDesc(event,'${varc.rId}','${varc.reelStatus}')">
                                <td class="list-item">
                                    <div class="checkbox-primary">
                                        <input class="tdListCheckbox" type="checkbox" value="${varc.rId}">
                                        <label>&nbsp;</label>
                                    </div>
                                </td>
                                <td>
                                    <c:if test="${varc.reelStatus== '0' }">
                                        <i class="active"></i>
                                    </c:if>
                                    <c:if test="${varc.reelStatus== '1' }">
                                        <i></i>
                                    </c:if>
                                    <span onclick="getReelEdit('${varc.rId}','${varc.pageList[0].pageId}',event)" title="${varc.title}"> ${varc.title}</span>
                                </td>
                                <td>${varc.rId}</td>
                                    <%-- <td>${empty varc.canalText?'无':varc.canalText}</td>--%>
                                <td>
                                    <c:if test="${varc.reelStatus== '0' }">开始调查</c:if>
                                    <c:if test="${varc.reelStatus== '1' }">暂停调查</c:if>
                                </td>
                                <td>${varc.createTime }</td>
                                <td>${varc.recycleNum }</td>
                                <td>
                                    <div class="dropdown">
                                        <ul class="dropdown-menu">
                                            <li class='nopoint'>
                                                <a class="statusView"
                                                   onclick="statusfuc(event,'${varc.rId}','${varc.reelStatus}','${varc.userId}')"></a>
                                            </li>
                                            <li class='nopoint'>
                                                <a class="moveTrashBasket"
                                                   onclick="moveTrashBasket(event,'${varc.rId}')">移动到废纸篓</a>
                                            </li>
                                            <li class="dropdown-submenu">
                                                <a>移动到</a>
                                                <ul class="dropdown-menu pull-left"></ul>
                                            </li>
                                            <c:if test="${varc.reelStatus== '0' }">
                                                <li class="nopoint">
                                                    <a onclick="alertDetails(event,'${varc.rId}')">问卷情况</a>
                                                </li>
                                            </c:if>
                                        </ul>
                                        <i class="more-btn" data-toggle="dropdown"
                                           onclick="moreBtn(event,'${varc.rId}','${varc.reelStatus}','${varc.userId}',this)"></i>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${allSubjectDTO == '[]'}">
                        <c:if test="${FolderDTO == '[]'}">
                            <tr>
                                <td colspan="6" style="text-align: center;">暂时无数据</td>
                            </tr>
                        </c:if>
                    </c:if>
                    </tbody>
                </table>
            </div>

            <!--问卷信息详情-->
            <div class="draw-info" style="display: none;">
                <div class="draw-title">
                    <h2 class="base-title">
                        <span class="input-title"></span></h2>
                    <a class="base-close" title="关闭" onclick="hiddenInfoDesc(event)"></a>
                </div>
                <div class="alert alert-primary">
                    <span>最近回收概况</span>
                    <span class="span-recycle">回收量:0</span>
                </div>
                <ul>
                    <li class="base-info">
                        <span class="base-info-lt">基本信息</span>
                        <span class="base-info-rt page-subject">共&nbsp;0&nbsp;页&nbsp;,&nbsp;0&nbsp;题</span>
                    </li>
                    <li class="base-info">
                        <span class="base-info-lt">ID</span>
                        <span class="base-info-rt"><input class="input-rId" readonly></span>
                    </li>
                    <li class="base-info">
                        <span class="base-info-lt">问卷状态</span>
                        <span class="base-info-rt">
                            <i class="base-status pause">
                            <input class="input-reelStatus" readonly>
                               <%-- <c:if test="${vax.reelStatus== '0' }">开始回收</c:if>
                               <c:if test="${vax.reelStatus== '1' }">暂停回收</c:if> --%>
                            </i>
                        </span>
                    </li>
                    <li class="base-info">
                        <span class="base-info-lt">修改时间</span>
                        <span class="base-info-rt"><input class="input-updateTime" readonly></span>
                    </li>
                </ul>
                <div class="drap-btn">
                    <dl onclick="editreel(event)">
                        <dt>
                            <svg class="icon icon-edit" viewBox="0 0 56 56" style="width: 30px; height: 30px;">
                                <path d="M46.1 1h-36c-2.2 0-4 1.8-4 4v46c0 2.2 1.8 4 4 4h36c2.2 0 4-1.8 4-4V5c0-2.2-1.8-4-4-4zm0 47c0 1.7-1.3 3-3 3h-30c-1.7 0-3-1.3-3-3V8c0-1.7 1.3-3 3-3h30c1.7 0 3 1.3 3 3v40z"></path>
                                <path d="M17.2 12h22v4h-22zM17.2 20h22v4h-22zM17.2 28h22v4h-22z"></path>
                                <g class="icon-edit-pen">
                                    <path d="M28.5 47.4l-9.9.7.7-9.9 26.3-26.3c1.6-1.6 4.1-1.6 5.7 0l3.5 3.5c1.6 1.6 1.6 4.1 0 5.7L28.5 47.4z"
                                          fill="#fff"></path>
                                    <path d="M27.8 45.6l-7.4.7.7-7.4 25.3-25.3c1.2-1.2 3.1-1.2 4.2 0l2.5 2.5c1.2 1.2 1.2 3.1 0 4.2L27.8 45.6zm-3.9-2.8l2.5-.7 23.9-23.8-1.8-1.8-23.9 23.9-.7 2.4z"></path>
                                </g>
                            </svg>
                        </dt>
                        <dd>编辑问卷</dd>
                    </dl>
                    <dl onclick="stareel(event)">
                        <dt>
                            <svg class="icon icon-stat-analyse" viewBox="0 0 50 48">
                                <path d="M4 48c-2.2 0-4-1.8-4-4V0h4v44h46v4H4z"></path>
                                <path class="icon-stat-analyse-right" d="M36 14h7v22h-7z"></path>
                                <path class="icon-stat-analyse-center" d="M24 4h7v32h-7z"></path>
                                <path class="icon-stat-analyse-left" d="M12 20h7v16h-7z"></path>
                            </svg>
                        </dt>
                        <dd>问卷统计</dd>
                    </dl>
                    <dl onclick="startreel()">
                        <dt></dt>
                        <dd id="statusvalue"></dd>
                    </dl>
                </div>
            </div>
        </div>
    </c:if>

    <!-- 每个文件夹里面的数据 -->
    <c:if test="${folderId != null }">
        <!--副标题头-->
        <div class="sub-header clearfix" data-current="true">
            <div class="sub-header-left">
                <a class="btn-back" onclick="backList(event)"></a>
                    <%--<a class="btn btn-primary icon icon-plus" title="列表视图" onclick="createQuestion('${folderId}')">创建问卷</a>--%>
                <a class="btn btn-primary " title="列表视图" onclick="createQuestion('${folderId}')"
                   style="padding: 6px 15px;"><i class="add">+</i>创建问卷</a>
            </div>
            <div class="sub-header-right">
                <a class="sub-header-btn create-fold" title="创建文件夹" style="display:none"></a>
                <a class="sub-header-btn" title="废纸篓" onclick="changeToTrash(event)"></a>
                <a class="sub-header-btn" title="列表视图" data-type="table" onclick="changeToGrid(event)"></a>
                <a class="sub-header-btn" title="九宫格视图" data-type="grid" style="display: none" onclick="changeToTable(event)"></a>
            </div>
        </div>
        <!-- 问卷为空-->
        <div class="emptyTip" style="display: none">
            <img class="emptyTip-pic" src="<%=path %>/template/image/bg/bg-white.png">
            <p class="emptyTip-info">您还没有问卷哦</p>
            <a class="">现在去创建</a>
        </div>

        <!--副标题栏2-->
        <div class="sub-header-2" data-current="false" style="display: none">
            <a class="btn-back" onclick="backToOrigin(event)"></a>
            <a class="sub-title">废纸篓</a>
            <a class="sub-header-btn" title="列表视图" data-type="table" onclick="changeToGrid(event)"></a>
            <a class="sub-header-btn" title="九宫格视图" data-type="grid" style="display: none" onclick="changeToTable(event)"></a>
        </div>
        <!--视图列表-->
        <div class="wrap-grid clearfix">
            <!--问卷视图表-->
            <div class="card-grid" style="display: none">

                <!--问卷九宫格视图-->
                <c:if test="${allSubjectDTO != null}">
                    <c:forEach items="${allSubjectDTO}" var="va" varStatus="vs">
                        <div class="card-item">
                            <div class="bg-card-image" onclick="showInfoDesc(event,'${va.rId}','${va.reelStatus}')">
                                <div class="dropdown">
                                    <ul class="dropdown-menu">
                                        <li class='nopoint'>
                                            <a class="statusView" onclick="statusFolderfuc(event,'${folderId}','${va.rId}','${va.reelStatus}','${va.userId}')"></a>
                                        </li>
                                        <li class='nopoint'>
                                            <a class="moveTrashBasket"
                                               onclick="moveFolderUpTrashBasket(event,'${folderId}','${va.rId}')">移动到废纸篓</a>
                                        </li>
                                        <li class="nopoint">
                                            <a onclick="shiftOutFolder(event,'${folderId}','${va.rId}')">移出文件夹</a>
                                        </li>
                                        <c:if test="${va.reelStatus== '0' }">
                                            <li class="nopoint">
                                                <a onclick="alertDetails(event,'${va.rId}')">问卷情况</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                    <i class="more-btn" data-toggle="dropdown"
                                       onclick="moreFolderBtn('${va.rId}','${va.reelStatus}','${va.userId}',this)"></i>
                                </div>
                                <!-- <i class="more-btn"></i> -->
                            </div>
                            <div class="card-desc">
                                <div class="card-desc-title">
                                    <c:if test="${va.reelStatus== '0' }">
                                        <i class="active"></i>
                                    </c:if>
                                    <c:if test="${va.reelStatus== '1' }">
                                        <i></i>
                                    </c:if>
                                    <span title="${va.title}">${va.title}</span>
                                </div>
                                <em class="ellipsis">${va.rId}</em>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
            <!--问卷表格视图-->
            <div class="table-wrap">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>名称</th>
                            <%--<th>发布渠道</th>--%>
                        <th>状态</th>
                        <th>创建时间</th>
                        <th>回收量</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:if test="${allSubjectDTO != null}">
                        <c:forEach items="${allSubjectDTO}" var="varc" varStatus="vsc">
                            <tr onclick="showInfoDesc(event,'${varc.rId}','${varc.reelStatus}')">
                                <td>
                                    <c:if test="${varc.reelStatus== '0' }">
                                        <i class="active"></i>
                                    </c:if>
                                    <c:if test="${varc.reelStatus== '1' }">
                                        <i></i>
                                    </c:if>
                                    <span onclick="getReelEdit('${varc.rId}','${varc.pageList[0].pageId}',event)"
                                          title="${varc.title}">${varc.title}</span>
                                    <em>${varc.rId}</em>
                                </td>
                                    <%--<td>${empty varc.canalText?'无':varc.canalText}</td>--%>
                                <td>
                                    <c:if test="${varc.reelStatus== '0' }">开始调查</c:if>
                                    <c:if test="${varc.reelStatus== '1' }">暂停调查</c:if>
                                </td>
                                <td>${varc.createTime }</td>
                                <td>${varc.recycleNum }</td>
                                <td>
                                    <div class="dropdown">
                                        <ul class="dropdown-menu">
                                            <li class='nopoint'>
                                                <a class="statusView"
                                                   onclick="statusFolderfuc(event,'${folderId}','${varc.rId}','${varc.reelStatus}','${varc.userId}')"></a>
                                            </li>
                                            <li class='nopoint'>
                                                <a class="moveTrashBasket"
                                                   onclick="moveFolderUpTrashBasket(event,'${folderId}','${varc.rId}')">移动到废纸篓</a>
                                            </li>
                                            <li class="nopoint">
                                                <a onclick="shiftOutFolder(event,'${folderId}','${varc.rId}')">移出文件夹</a>
                                            </li>
                                            <c:if test="${varc.reelStatus== '0' }">
                                                <li class="nopoint">
                                                    <a onclick="alertDetails(event,'${varc.rId}')">问卷情况</a>
                                                </li>
                                            </c:if>
                                        </ul>
                                        <i class="more-btn" data-toggle="dropdown"
                                           onclick="moreFolderBtn('${varc.rId}','${varc.reelStatus}','${varc.userId}',this)"></i>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>

                    <!-- <tr>
                        <td class="clearfix">
                            <div class="icon-folder">
                                <span class="icon-folder-header"></span>
                                <span class="icon-folder-middle"></span>
                                <span class="icon-folder-main"></span>
                            </div>
                            <span><a>新建文件夹</a></span>
                        </td>
                        <td colspan="4"></td>
                        <td>
                            <i class="more-btn"></i>
                        </td>
                    </tr> -->

                    </tbody>
                </table>
            </div>

            <!--问卷信息详情-->
            <div class="draw-info">
                <div class="draw-title">
                    <h2 class="base-title">
                        <span class="input-title"></span>
                    </h2>
                    <a class="base-close" title="关闭" onclick="hiddenInfoDesc(event)"></a>
                </div>
                <div class="alert alert-primary">
                    <span>最近回收概况</span>
                    <span>回收量:0</span>
                </div>
                <ul>
                    <li class="base-info">
                        <span class="base-info-lt">基本信息</span>
                        <span class="base-info-rt">共&nbsp;1&nbsp;页&nbsp;,&nbsp;1&nbsp;题</span>
                    </li>
                    <li class="base-info">
                        <span class="base-info-lt">ID</span>
                        <span class="base-info-rt"><input class="input-rId" readonly></span>
                    </li>
                    <li class="base-info">
                        <span class="base-info-lt">问卷状态</span>
                        <span class="base-info-rt">
                            <i class="base-status pause">
                            <input class="input-reelStatus" readonly>
                               <c:if test="${vax.reelStatus== '0' }">开始调查</c:if>
                               <c:if test="${vax.reelStatus== '1' }">暂停调查</c:if>
                            </i>
                        </span>
                    </li>
                    <li class="base-info">
                        <span class="base-info-lt">修改时间</span>
                        <span class="base-info-rt"><input class="input-updateTime" readonly></span>
                    </li>
                </ul>
                <div class="drap-btn">
                    <dl onclick="editreel(event)">
                        <dt>
                            <svg class="icon icon-edit" viewBox="0 0 56 56" style="width: 30px; height: 30px;">
                                <path d="M46.1 1h-36c-2.2 0-4 1.8-4 4v46c0 2.2 1.8 4 4 4h36c2.2 0 4-1.8 4-4V5c0-2.2-1.8-4-4-4zm0 47c0 1.7-1.3 3-3 3h-30c-1.7 0-3-1.3-3-3V8c0-1.7 1.3-3 3-3h30c1.7 0 3 1.3 3 3v40z"></path>
                                <path d="M17.2 12h22v4h-22zM17.2 20h22v4h-22zM17.2 28h22v4h-22z"></path>
                                <g class="icon-edit-pen">
                                    <path d="M28.5 47.4l-9.9.7.7-9.9 26.3-26.3c1.6-1.6 4.1-1.6 5.7 0l3.5 3.5c1.6 1.6 1.6 4.1 0 5.7L28.5 47.4z"
                                          fill="#fff"></path>
                                    <path d="M27.8 45.6l-7.4.7.7-7.4 25.3-25.3c1.2-1.2 3.1-1.2 4.2 0l2.5 2.5c1.2 1.2 1.2 3.1 0 4.2L27.8 45.6zm-3.9-2.8l2.5-.7 23.9-23.8-1.8-1.8-23.9 23.9-.7 2.4z"></path>
                                </g>
                            </svg>
                        </dt>
                        <dd>编辑问卷</dd>
                    </dl>
                    <dl onclick="stareel(event)">
                        <dt>
                            <svg class="icon icon-stat-analyse" viewBox="0 0 50 48">
                                <path d="M4 48c-2.2 0-4-1.8-4-4V0h4v44h46v4H4z"></path>
                                <path class="icon-stat-analyse-right" d="M36 14h7v22h-7z"></path>
                                <path class="icon-stat-analyse-center" d="M24 4h7v32h-7z"></path>
                                <path class="icon-stat-analyse-left" d="M12 20h7v16h-7z"></path>
                            </svg>
                        </dt>
                        <dd>问卷统计</dd>
                    </dl>
                    <dl onclick="startreel()">
                        <dt></dt>
                        <dd id="statusvalue"></dd>
                    </dl>
                </div>
            </div>
        </div>
    </c:if>

</div>

<%--弹出新建文件夹对话框--%>
<div class="modal fade" id="PopDialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="dialog-container">
                <div class="dialog-header">
                    <span class="dialog-title">创建文件夹</span>
                    <span class="btn-close-dialog" data-dismiss="modal"></span>
                </div>
                <div class="dialog-main">
                    <div class="form-Dialog">
                        <%--<form action="">--%>
                        <input name="folderName" id="folderName" type="text" class="form-control" placeholder="新建文件夹">
                        <hr>
                        <fielset>
                            <input type="button" class="cancel" value="取消">
                            <input type="button" class="inputSubmit" value="确定">
                        </fielset>
                        <%--</form>--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--弹出问卷填报人详情对话框--%>
<div class="modal fade" id="popDetailsDialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="dialog-container" style="min-height: 300px;height: auto;width: 900px;">
                <div class="dialog-header">
                    <span class="dialog-title">问卷填报人列表</span>
                    <span class="btn-close-dialog" data-dismiss="modal"></span>
                </div>
                <div class="dialog-main">
                    <div class="card">
                        <div class="card-content">
                            <div class="col-title">
                                <div class="col-title__label"> 已填报人员 (<span class="count-done">0</span>)</div>
                            </div>
                            <div class="col-list col-list__done"></div>
                        </div>
                        <div class="card-content">
                            <div class="col-title" style="display: flex;justify-content: center;">
                                <div class="col-title__label">未填报人员 (<span class="count-todo">0</span>)</div>
                                <div class="col-title__operation" data-type="CANCEL" onclick="checkAll(event)">全选</div>
                            </div>
                            <div class="col-list col-list__todo"></div>
                        </div>
                    </div>
                </div>
                <div class="dialog-footer">
                    <div class="btn btn-primary" onclick="notice()">提醒未填报人员</div>
                    <div class="btn cancel-btn" data-dismiss="modal">取消</div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery (ZUI中的Javascript组件依赖于jQuery) -->
<script src="<%=path %>/template/jquery/jquery-1.11.0.min.js"></script>
<!-- ZUI Javascript组件 -->
<script src="<%=path %>/template/zui/js/zui.min.js"></script>

<script type="text/javascript">
    $(function () {
    })
    $(".create-fold").click(function () {
        $("#PopDialog").modal()
    })

    /**
     * 切换调查状态
     * @params ev
     * @params rId
     * @params reelStatus
     * @params userId
     */
    function statusfuc(ev, rId, reelStatus, userId) {
        ev = ev || window.event;
        ev.stopPropagation()
        var status = "";
        var reelType = ""
        if (reelStatus == "0") {
            status = "暂停调查"
            reelType = "1"
        } else {
            status = "开始调查"
            reelType = "0"
        }
        if (confirm("确定要修改吗？")) {
            $.ajax({
                type: 'post',
                url: "/questionnaire/question/startresearch",
                data: {
                    "rId": rId,
                    "reelStatus": reelType,
                    "userId": userId
                },
                success: function (data) {
                    var data = $.parseJSON(data);
                    if (data.code == "0") {
                        alertTop("修改成功");
                        setTimeout(function () {
                            toSearchs(this, '/questionnaire/reel/list', 'contentMain')
                        }, 700)
                    } else {
                        alertTop("此问卷未创建题目，不可调查操作");
                    }
                }
            })
        }
    }


    /**
     * 把问卷移动到废纸篓
     * @params ev
     * @params rId
     */
    function moveTrashBasket(ev, rId) {
        ev = ev || window.event;
        ev.stopPropagation()
        if (confirm("确定要移动到废纸篓吗？")) {
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/removeBasket",
                data: {
                    "rId": rId
                },
                success: function (result) {
                    if (result == "1") {
                        alertTop("移除成功");
                        setTimeout(function () {
                            toSearchs(this, '/questionnaire/reel/list', 'contentMain')
                        }, 700)
                    } else {
                        alertTop("移除失败");
                    }
                }
            })
        }
    }

    /**
     * 把问卷移动到文件夹下
     * @params ev
     * @params folderId
     * @params rId
     */
    function folderfuc(ev, folderId, rId) {
        ev = ev || window.event;
        ev.stopPropagation()
        if (confirm("确定要移动吗？")) {
            $.ajax({
                type: 'post',
                url: '/questionnaire/reel/moveToFolder',
                data: {
                    'folderId': folderId,
                    'rId': rId
                },
                success: function (result) {
                    if (result == "1") {
                        alertTop("移动成功");
                        setTimeout(function () {
                            toSearchs(this, '/questionnaire/reel/list', 'contentMain')
                        }, 700)
                    } else {
                        alertTop("移动失败");
                    }
                }
            })
        }
    }

    /**
     *
     */
    $(".more-btn").click(function () {
        if ($(this).parents(".dropdown").css("width") == "110px" || $(this).parents(".dropdown").hasClass("folderbtn")) {
            if ($(this).offset().left + 400 > $(document).width()) {
                $(".dropdown-menu").css({
                    "left": -105
                })
                $(".dropdown-menu.pull-left").css({
                    "left": -195
                })
            } else {
                if ($(this).parents(".dropdown").hasClass("folderbtn")) {
                    $(".dropdown-menu").css({
                        "left": -30
                    })
                } else {
                    $(".dropdown-menu").css({
                        "left": 85
                    })
                    $(".dropdown-menu.pull-left").css({
                        "left": 200
                    })
                }

            }
        } else {
            $(".dropdown-menu").css({
                "left": -195
            })
        }
    })


    /**
     * 下拉菜单
     * @params ev
     * @params rId
     * @params reelStatus
     * @params userId
     * @params self
     */
    function moreBtn(ev, rId, reelStatus, userId, self) {
        if ($(self).parents(".dropdown").css("width") == "110px") {
            if ($(self).offset().left + 400 > $(document).width()) {
                $(".dropdown-menu").css({
                    "left": -105
                })
                $(".dropdown-menu.pull-left").css({
                    "left": -195
                })
            } else {
                $(".dropdown-menu").css({
                    "left": 85
                })
                $(".dropdown-menu.pull-left").css({
                    "left": 200
                })
            }
        } else {
            $(".dropdown-menu").css({
                "left": -195
            })
        }

        $(".pull-left").empty();
        var status = "";
        var reelType = ""
        if (reelStatus == "0") {
            status = "暂停调查"
            reelType = "1"
        } else {
            status = "开始调查"
            reelType = "0"
        }
        $(".statusView").html(status);
        var delFlag = "0";//0:正常
        $.ajax({
            type: 'post',
            url: "/questionnaire/reel/queryAllFolder",
            data: {"delFlag": delFlag},
            async: false,
            success: function (result) {
                if (result != "") {
                    for (var i = 0; i < result.length; i++) {
                        var folderName = result[i].folderName;
                        var folderId = result[i].folderId;
                        var a = "<li><a onclick=\"folderfuc(event,'" + folderId + "','" + rId + "')\">" + folderName + "</a></li>";
                        $(".pull-left").append(a);
                    }
                } else {
                    $(".dropdown-menu .dropdown-submenu").remove();
                    //$(".pull-left").remove();

                }
            }
        })
    }


    /**
     * 文件夹删除包括文件夹下面的问卷
     * @params ev
     * @params folderId
     */
    function deleFolderfuc(ev, folderId) {
        ev = ev || window.event;
        ev.stopPropagation()
        if (confirm("确定要删除吗？")) {
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/deleteFolder",
                data: {
                    "folderId": folderId
                },
                success: function (result) {
                    if (result == "1") {
                        alertTop("删除成功");
                        setTimeout(function () {
                            toSearchs(this, '/questionnaire/reel/list', 'contentMain')
                        }, 700)
                    } else {
                        alertTop("删除失败");
                    }
                }
            })
        }
    }


    /**
     * 文件夹包括下面的问卷移动到废纸篓
     * @params ev
     * @params folderId
     */
    function moveFolderTrashBasket(ev, folderId) {
        ev = ev || window.event;
        ev.stopPropagation()
        if (confirm("确定要移到废纸篓吗？")) {
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/removeBasket",
                data: {
                    "folderId": folderId
                },
                success: function (result) {
                    if (result == "1") {
                        alertTop("移动成功");
                        setTimeout(function () {
                            toSearchs(this, '/questionnaire/reel/list', 'contentMain')
                        }, 700)
                    } else {
                        alertTop("移动失败");
                    }
                }
            })
        }
    }


    /**
     * 文件夹下的问卷下拉菜单
     * @params ev
     * @params reelStatus
     * @params userId
     * @params self
     */
    function moreFolderBtn(rId, reelStatus, userId, self) {
        if ($(self).parents(".dropdown").css("width") == "110px") {
            if ($(self).offset().left + 400 > $(document).width()) {
                $(".dropdown-menu").css({
                    "left": -105
                })
                $(".dropdown-menu.pull-left").css({
                    "left": -195
                })
            } else {
                $(".dropdown-menu").css({
                    "left": 85
                })
                $(".dropdown-menu.pull-left").css({
                    "left": 200
                })
            }
        } else {
            $(".dropdown-menu").css({
                "left": -195
            })
        }
        $(".pull-left").empty();
        var status = "";
        var reelType = ""
        if (reelStatus == "0") {
            status = "暂停调查"
            reelType = "1"
        } else {
            status = "开始调查"
            reelType = "0"
        }
        $(".statusView").html(status);
    }

    /**
     * 文件夹下的问卷-切换调查状态
     * @params ev
     * @params folderId
     * @params rId
     * @params reelStatus
     * @params userId
     */
    function statusFolderfuc(ev, folderId, rId, reelStatus, userId) {
        ev = ev || window.event;
        ev.stopPropagation()
        var status = "";
        var reelType = ""
        if (reelStatus == "0") {
            status = "暂停调查"
            reelType = "1"
        } else {
            status = "开始调查"
            reelType = "0"
        }
        if (confirm("确定要修改吗？")) {
            $.ajax({
                type: 'post',
                url: "/questionnaire/question/startresearch",
                data: {
                    "rId": rId,
                    "reelStatus": reelType,
                    "userId": userId
                },
                success: function (data) {
                    var data = $.parseJSON(data);
                    if (data.code == "0") {
                        alertTop("修改成功");
                        setTimeout(function () {
                            toSearchs(this, '/questionnaire/reel/list?folderId=' + folderId, 'contentMain')
                        }, 700)
                    } else {
                        alertTop("此问卷未创建题目，不可调查操作");
                    }
                }
            })
        }
    }


    /**
     * 吧问文件夹下的问卷移动到废纸篓
     * @param ev
     * @param folderId
     * @param rId
     */
    function moveFolderUpTrashBasket(ev, folderId, rId) {
        ev = ev || window.event;
        ev.stopPropagation()
        if (confirm("确定要移动到废纸篓吗？")) {
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/removeBasket",
                data: {
                    "folderId": folderId,
                    "rId": rId
                },
                success: function (result) {
                    if (result == "1") {
                        alertTop("移除成功");
                        setTimeout(function () {
                            toSearchs(this, '/questionnaire/reel/list?folderId=' + folderId, 'contentMain')
                        }, 700)
                    } else {
                        alertTop("移除失败");
                    }
                }
            })
        }
    }

    /**
     * 把问卷移出文件夹
     * @param ev
     * @param folderId
     * @param rId
     */
    function shiftOutFolder(ev, folderId, rId) {
        ev = ev || window.event;
        ev.stopPropagation()
        if (confirm("确定要移出文件夹吗？")) {
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/shiftOutFolder",
                data: {
                    "rId": rId
                },
                success: function (result) {
                    if (result == "1") {
                        alertTop("移出成功");
                        setTimeout(function () {
                            toSearchs(this, '/questionnaire/reel/list?folderId=' + folderId, 'contentMain')
                        }, 700)
                    } else {
                        alertTop("移出失败");
                    }
                }
            })
        }
    }

    function getReelEdit(obj, obj1, ev) {
        ev = ev || window.event;
        ev.stopPropagation()
        var url = "/questionnaire/reel/main1?rId=" + obj + "&pageId=" + obj1;
        toSearchs(this, url, 'contentMain');
    }

    /**
     * 显示问卷信息，抽屉抽出
     * @param ev
     * @param obj
     * @param reelStatus
     */
    function showInfoDesc(ev, obj, reelStatus) {
        $(".draw-info").css("display", "block")
        edit(obj);
        ev = ev || event
        var target = ev.target
        if (target.className.indexOf("more-btn") === -1) {
            var elDrawInfo = document.getElementsByClassName("draw-info")[0]
            if (elDrawInfo.className.indexOf("draw-increase") === -1) {
                elDrawInfo.className += " draw-increase"
            }
            // 表格变短
            var elTable = document.getElementsByClassName("table-wrap")[0]
            if (elTable.className.indexOf("table-decrease") === -1) {
                elTable.className += " table-decrease"
            }
            // 缩略图容器变短
            var elCardGrid = document.getElementsByClassName("card-grid")[0]
            if (elCardGrid.className.indexOf("card-grid-decrease") === -1) {
                elCardGrid.className += " card-grid-decrease"
            }
        }
        //设置某条rid,用来跳转到编辑和统计页的
        $("#myrId").val(obj);
        $("#mystatus").val(reelStatus);
        //开始和暂停按钮图标更换
        if (reelStatus == "0") {
            $(".empty-page .wrap-grid .drap-btn dl:last-child dt").css({
                "background-image": "url(<%=path %>/template/image/icon/icon-end.png)"
            });
        } else {
            $(".empty-page .wrap-grid .drap-btn dl:last-child dt").css({
                "background-image": "url(<%=path %>/template/image/icon/icon-start.png)"
            });
        }

    }
    /**
     *
     */
    function edit(obj) {
        $.ajax({
            type: 'get',
            url: "/questionnaire/reel/queryByRid",
            data: {
                "rId": obj
            },
            success: function (result) {

                var title = result.title;
                $(".input-title").html(title);

                var recycleNum = result.recycleNum;
                $(".span-recycle").html("回收量：" + recycleNum);

                var pageNum = result.pageNum;
                var subjectNum = result.subjectNum;
                $(".page-subject").html("共" + pageNum + "页" + subjectNum + "题");

                var rId = result.rId;
                $(".input-rId").val(rId);

                var reelStatus = result.reelStatus;
                var statusName = "";
                var statusvalue = "";
                if (reelStatus == 0) {
                    statusName = "开始调查";
                    statusvalue = "暂停调查";
                } else if (reelStatus == 1) {
                    statusName = "暂停调查";
                    statusvalue = "开始调查";
                } else {
                    statusName = "";//后台查询不到时赋值空
                }
                $(".input-reelStatus").val(statusName);
                $("#statusvalue").text(statusvalue);

                var updateTime = result.updateTime;
                $(".input-updateTime").val(updateTime);

            }
        })
    }

    function createQuestion(folderId) {
        //location.href = "/questionnaire/reel/main?folderId="+folderId;
        toSearchs(this, '/questionnaire/reel/main?folderId=' + folderId, 'contentMain');
    }


    /**
     * 隐藏问卷信息，抽屉关闭
     * @param ev
     */
    function hiddenInfoDesc(ev) {
        var elDrawInfo = document.getElementsByClassName("draw-info")[0]
        if (elDrawInfo.className.indexOf("draw-increase") > -1) {
            elDrawInfo.classList.remove("draw-increase")
        }
        var elTable = document.getElementsByClassName("table-wrap")[0]
        if (elTable.className.indexOf("table-decrease") > -1) {
            elTable.classList.remove("table-decrease")
        }
        var elCardGrid = document.getElementsByClassName("card-grid")[0]
        if (elCardGrid.className.indexOf("card-grid-decrease") > -1) {
            elCardGrid.classList.remove("card-grid-decrease")
        }
    }

    /**
     * 改变视图显示为缩略图显示方式
     * @param ev
     */
    function changeToGrid(ev) {
        var userId = $("#userId").val();
        $.ajax({
            url: "/questionnaire/reel/updateViewStatus",
            method: "post",
            data: {
                "status": 1,
                "userId": userId
            },
            success: function (res) {
                //console.log(res)
            }
        })
        ev = ev || event
        var target = ev.target
        target.style.display = "none"
        target.nextElementSibling.style.display = "block"
        var elEmptyPage = document.getElementsByClassName("empty-page")[0]
        // 【空问卷】提示页因隐藏
        var elEmptyTip = elEmptyPage.getElementsByClassName("emptyTip")[0]
        elEmptyTip.style.display = "none"
        // 视图列表显示
        var elCardGrid = elEmptyPage.getElementsByClassName("card-grid")[0]
        elCardGrid.style.display = "flex"
        // 问卷表单列表隐藏
        var elTable = elEmptyPage.getElementsByClassName("table-wrap")[0]
        elTable.style.display = "none"
        // 抽屉关闭
        var elBaseClose = elEmptyPage.getElementsByClassName("base-close")[0]
        elBaseClose.click()
    }


    /**
     * 改变视图显示为表格显示方式
     * @param ev
     */
    function changeToTable(ev) {
        var userId = $("#userId").val();
        $.ajax({
            url: "/questionnaire/reel/updateViewStatus",
            method: "post",
            data: {
                "status": 0,
                "userId": userId
            },
            success: function (res) {
                //console.log(res)
            }
        })
        ev = ev || event
        var target = ev.target
        target.style.display = "none"
        target.previousElementSibling.style.display = "block"
        var parent = target.parentNode.parentNode
        var elEmptyPage = document.getElementsByClassName("empty-page")[0]
        // 【空问卷】提示页因隐藏
        var elEmptyTip = elEmptyPage.getElementsByClassName("emptyTip")[0]
        elEmptyTip.style.display = "none"
        // 视图列表显示
        var elCardGrid = elEmptyPage.getElementsByClassName("card-grid")[0]
        elCardGrid.style.display = "none"
        // 问卷表单列表隐藏
        var elTable = elEmptyPage.getElementsByClassName("table-wrap")[0]
        elTable.style.display = ""
        // 抽屉关闭
        var elBaseClose = elEmptyPage.getElementsByClassName("base-close")[0]
        elBaseClose.click()
    }

    if ($("#viewStatus").val() == "0") {
        $('a[data-type="grid"]').click()
    } else {
        $('a[data-type="table"]').click()
    }

    /**
     * 跳转到回收站页面
     * @param ev
     */
    function changeToTrash(ev) {
        //location.href = "/questionnaire/reel/trashBasket";
        toSearchs(this, "/questionnaire/reel/trashBasket", "contentMain")
        ev = ev || event
        var target = ev.target
        var elEmptyPage = document.getElementsByClassName("empty-page")[0]
        var subHeader = elEmptyPage.getElementsByClassName("sub-header")[0]
        subHeader.style.display = "none"
        subHeader.dataset.current = false.toString()
        var subHeader2 = elEmptyPage.getElementsByClassName("sub-header-2")[0]
        subHeader2.style.display = "flex"
        subHeader2.dataset.current = true.toString()

    }

    /**
     * 返回原来的问卷页面
     * @param ev
     */
    function backToOrigin(ev) {
        toSearchs(this, "/questionnaire/reel/list", "contentMain")
        ev = ev || event
        var target = ev.target
        var elEmptyPage = document.getElementsByClassName("empty-page")[0]
        var subHeader = elEmptyPage.getElementsByClassName("sub-header")[0]
        subHeader.style.display = "block"
        subHeader.dataset.current = true.toString()
        var subHeader2 = elEmptyPage.getElementsByClassName("sub-header-2")[0]
        subHeader2.style.display = "none"
        subHeader2.dataset.current = false.toString()
    }

    /***
     * 退出文件夹
     * @param e
     */
    function backList(e) {
        toSearchs(this, '/questionnaire/reel/list', 'contentMain')
    }

    /**
     * 文件夹创建确定
     */
    $(".inputSubmit").click(function () {
        var folderName = $("#folderName").val();
        if (folderName != "") {
            $.ajax({
                type: 'get',
                url: "/questionnaire/reel/folderSave",
                async: false,
                data: {
                    "folderName": folderName
                },
                success: function (result) {
                    $("#PopDialog").modal("hide")
                    $(".modal-backdrop").remove()
                    toSearchs(this, "/questionnaire/reel/list", 'contentMain');
                }
            })
        } else {
            new $.zui.Messager('文件夹名称不能为空！', {
                type: 'failed',
                close: false
            }).show();
        }
    })
    /**
     * 点击回车
     */
    $("#folderName").focus(function () {
        //点击回车触发创建文件夹
        document.onkeydown = function (e) {
            if (!e) e = window.event;
            if ((e.keyCode || e.which) == 13) {
                $(".inputSubmit").click()
            }
        }
    })

    /**
     * 文件夹
     */
    $("#PopDialog .cancel").on("click", function () {
        $("#PopDialog").modal("toggle")
    })

    /**
     *
     * @param folderId
     */
    function toBasket(folderId) {
        //location.href = "/questionnaire/reel/list?folderId="+folderId;
        toSearchs(this, "/questionnaire/reel/list?folderId=" + folderId, 'contentMain');

    }

    /**
     * 点击单选
     */
    $(".tdListCheckbox").on("click", function (e) {
        e.stopPropagation()
    })
    //全选和全不选 
    var flag = false;

    $(".listCheckbox").on('click', function () {
        if (flag == true) {
            $('.tdListCheckbox:checkbox').prop('checked', false);
            flag = false;
            return;
        }
        if (flag == false) {
            $('.tdListCheckbox:checkbox').prop('checked', true);
            flag = true;
            return;
        }
    })

    /**
     * 选中删除
     */
    function deleteAll() {
        if ($(".listCheckbox:checkbox").is(":checked") || $(".tdListCheckbox:checkbox").is(":checked")) {
            var rIdArr = [];
            $(".tdListCheckbox:checkbox:checked").each(function () {
                var rId = $(this).val();
                rIdArr.push(rId);
            })
            if (confirm("确定要删除吗？")) {
                $.ajax({
                    type: 'get',
                    url: "/questionnaire/reel/deleteReel",
                    data: {
                        "rIdArr": rIdArr
                    },
                    success: function (result) {
                        if (result == "1") {
                            alertTop("删除成功")
                            setTimeout(function () {
                                toSearchs(this, '/questionnaire/reel/list', 'contentMain')
                            }, 700)
                        }
                    }
                })
            }
        }
    }

    /**
     * 选中删除
     * @param event
     */
    function editreel(event) {
        //跳转到编辑页
        //alert(11);
        var myrid = $("#myrId").val();
        //alert(myrid);
        getReelEdit(myrid, '0', event)
    }

    /**
     *
     * @param event
     */
    function stareel(event) {
        //跳转到统计页
        var myrid = $("#myrId").val();
        getReelEdit(myrid, '0', event);
        $(".count-btn").addClass("active").siblings().removeClass("active");
        var url = "/questionnaire/reel/statistics?rId=" + myrid;
        //toSearch(this,'/questionnaire/reel/statistics','param','main')
        toSearchs(this, url, 'main');

    }

    /**
     *
     */
    function startreel() {
        //跳转到统计页
        //alert(22);
        var myrid = $("#myrId").val();
        var userId = $("#userId").val();
        var reelStatus = $("#mystatus").val();
        //console.log("startreel,murid:"+myrid+"uid:"+userId+"reelStatus:"+reelStatus);
        //切换调查状态
        var reelType = ""
        if (reelStatus == "0") {
            reelType = "1"
        } else {
            reelType = "0"
        }
        $.ajax({
            type: 'post',
            url: "/questionnaire/question/startresearch",
            data: {
                "rId": myrid,
                "reelStatus": reelType,
                "userId": userId
            },
            success: function (data) {
                var data = $.parseJSON(data);
                if (data.code == "0") {
                    alertTop("修改成功");
                    setTimeout(function () {
                        toSearchs(this, '/questionnaire/reel/list', 'contentMain')
                    }, 700)
                } else {
                    alertTop("此问卷未创建题目，不可调查操作");
                }
            }
        })
    }

    /**
     * 问卷详情
     * @param ev
     * @param rId
     */
    function alertDetails(ev,rId) {
        ev.stopPropagation()
        noticeList = []
        $("#popDetailsDialog").modal("toggle")
        $("#popDetailsDialog .btn-primary").attr("data-id",rId)
        $.ajax({
            type: 'post',
            url: "/questionnaire/question/questionState",
            data: {
                "rId": rId,
            },
            success: function (data) {
                console.log("data:",data)
                // var data = $.parseJSON(data);
                console.log("data:",data)

                if (data.code == "0") {
                    /*** 已填列表*/
                    // var doneList = data.data[0]
                    // $(".count-done").text(doneList.length)
                    // var htmlDone = ""
                    // doneList.forEach(function(item) {
                    //     htmlDone += '<div class="col-item ellipsis">'+ item.username +'</div>'
                    // })
                    // $(".col-list__done")[0].innerHTML = htmlDone
                    /*** 未填列表*/
                    // var todoList = data.data[1]
                    // $(".count-todo").text(todoList.length)
                    // var htmlTodo = ''
                    // todoList.forEach(function(item) {
                    //     var temp = "<div class=\"col-item ellipsis\" data-id="+item.userid+" onclick=\"add(event,'"+item.userid+"')\">"+item.username+"</div>"
                    //     htmlTodo += temp
                    // })
                    // $(".col-list__todo")[0].innerHTML = htmlTodo

                    const ztreeuser = JSON.parse(data.data.ztreeuser)

                    var html = renderTemplate(ztreeuser, '')
                    $(".col-list__todo")[0].innerHTML =  html
                } else {
                    alertTop("查询失败");
                }
            },
            error:function (error) {
                alertTop("查询失败");
            }
        })
    }

    function renderTemplate(tree, html){
        // debugger
        for (var i = 0; i < tree.length; i++) {
            var node = tree[i]
            if (node.type === 'dept') {
                html += '<div class="col-label"> <div class="point"></div> <div class="col-label__name">'+node.orname+'</div></div>'
            } else {
                if (node.checked === 'true') {
                    html += '<div class=\"col-item col-item__checked ellipsis\" data-id="+item.userid+" onclick=\"add(event,\'"+node.id+"\')\">'+node.orname+'</div>'
                } else {
                    html += '<div class="col-item ellipsis">'+ node.orname +'</div>'
                }
            }
            console.log("html:",html)
            if (node.children.length > 0) {
               return renderTemplate(node.children,html)
            }
        }
        return html
    }
    /*******选中的需要提醒的未填报人员******/
    var noticeList = []

    /**
     * 添加需要提醒填写问卷的人员
     * @param ev
     * @param userId
     */
    function add(ev,userId){
        var target = ev.currentTarget
        if (target.classList.contains("active")) { /**取消**/
            var index = noticeList.indexOf(userId)
            console.log("index:",index)
            if (index !== -1) {
                noticeList.splice(index,1)
            }
            target.classList.remove("active")
            // $(".col-title__operation")[0].dataset.type = "ALL"
            // $(".col-title__operation").text("取消")
        } else { /**添加选中**/
            if (noticeList.indexOf(userId) === -1) {
                noticeList.push(userId)
            }
            target.classList.add("active")
        }
    }

    /**
     * 全选
     * @param ev
     */
    function checkAll(ev) {
        var target = ev.currentTarget
        var type = target.dataset.type === "ALL" ? true:false
        if (!type) {
            target.dataset.type = "ALL"
            target.textContent = "取消"
            $(".col-list__todo").children().each(function(index,el){
                el.classList.add("active")
                noticeList.push(el.dataset.id)
            })
        } else {
            target.dataset.type = "CANCEL"
            target.textContent = "全选"
            $(".col-list__todo").children().each(function(index,el){
                el.className="col-item ellipsis"
            })
            noticeList = []
        }
    }

    /**
     * 提醒人员填报
     */
    function notice() {
        var rId = $("#popDetailsDialog .btn-primary").attr("data-id")
        $.ajax({
            type: 'post',
            url: "/questionnaire/question/sendMessage",
            dataType:"json",
            contentType:"application/json;charset=utf-8",
            data: JSON.stringify({
                "rId": rId,
                "userIdLIst":noticeList
            }),
            success: function (data) {
                // console.log("data:",data)
                if (data.code == "0") {
                    alertTop("提醒成功！");
                    $("#popDetailsDialog").modal("toggle")
                    noticeList = []
                } else {
                    alertTop("提醒失败!");
                }
            },
            error:function (error) {
                alertTop("提醒失败!");
            }
        })
    }



</script>
</body>
</html>