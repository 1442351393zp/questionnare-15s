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
    <base href="">
    <meta charset="UTF-8">
    <title>皮肤</title>
    <link rel="stylesheet" href="<%=path %>/template/css/desktop.css">
    <link rel="stylesheet" href="<%=path %>/template/css/fill.css">
    <link rel="stylesheet" href="<%=path%>/template/css/skin.css">
</head>
<body>
    <div class="skin-container">
        <div class="skin-navbar">
            <ul>
                <%--<li>--%>
                    <%--<div class="skin-image"></div>--%>
                    <%--<div class="skin-title">默认皮肤</div>--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<div class="skin-image"></div>--%>
                    <%--<div class="skin-title">山水风格</div>--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<div class="skin-image"></div>--%>
                    <%--<div class="skin-title">简单风约</div>--%>
                <%--</li>--%>
            </ul>
        </div>
        <%--预览显示区域--%>
        <div class="skin-content">
            <div id="root-container">
                <div class="skin normal">
                    <div class="survey-page s-page  survey-pc">
                        <div class="survey-container">
                            <!-- 填报问卷头部的动画 -->
                            <div class="survey-header">
                                <div class="top-tips-wrap"></div>
                                <div class="page-cover-pic">
                                    <div class="laptop anim">
                                        <div class="laptop-t"></div>
                                        <div class="laptop-b"></div>
                                        <div class="hook">
                                            <div class="paper"><span class="paper-line l1"></span><span class="paper-line l2"></span><span class="paper-line l3"></span><span
                                                    class="paper-line l4"></span><span class="paper-line l5"></span><span class="paper-line l6"></span><span
                                                    class="paper-line l7"></span><span class="paper-block"></span></div>
                                            <div class="pen">
                                                <div class="pen-t "></div>
                                                <div class="pen-m"></div>
                                                <div class="pen-b " style="transition: background 0.2s ease 0s;"></div>
                                            </div>
                                        </div>
                                        <div class="quote-l">
                                            <div class="quote-l-bg" style="transition: background 0.2s ease 0s;">
                                                <div class="quote-l-drop"></div>
                                            </div><span class="quote-l-line l1"></span><span class="quote-l-line l2"></span><span class="quote-l-line l3"></span>
                                        </div>
                                        <div class="quote-r">
                                            <div class="quote-r-bg "></div><span class="quote-r-dot d1 " style="transition: background 0.2s ease 0s;"></span><span
                                                class="quote-r-dot d2 " style="transition: background 0.2s ease 0s;"></span><span class="quote-r-dot d3 "
                                                                                                                                  style="transition: background 0.2s ease 0s;"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="survey-header-title">${reelVo.title}</div>
                                <div class="survey-header-subtitle">${reelVo.startLanguage}</div>
                                <form id="formData" method="post" enctype="application/x-www-form-urlencoded">
                                    <input id="reelId" name="reelId" type="hidden" value="${reelVo.rId}" />
                                    <input id="userId" name="userId" type="hidden" value="${reelVo.userId}" />
                                    <input id="optionsIds" name="optionsIds" type="hidden" />
                                    <input id="subjectIds" name="subjectIds" type="hidden" />
                                    <input id="pageIds" name="pageIds" type="hidden" />
                                </form>
                                <input id="pageNum" name="pageNum" type="hidden" value="${reelVo.pageMaxNo}"/>
                            </div>
                            <!-- 填报问卷的题目内容 -->
                            <c:forEach items="${reelVo.pageList}" var="pages" varStatus="status">
                                <div class="survey-main s-main">
                                    <div class="progress">
                                        <div class="progress-bar s-prog-wait fixed-bar">
                                            <div class="progress-done  s-prog-done">
                                                <input type="hidden" value="${pages.pageId}" name="pageId">
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 题目列表 -->
                                    <div class="question-list">
                                        <c:forEach items="${pages.subjectList}" var="subject" varStatus="sub">
                                            <c:choose>
                                                <c:when test="${subject.subjectType == 1}">
                                                    <!-- 单选题 -->
                                                    <div class="question-item">
                                                        <div class="question-item-title">
                                                            <input name="subjectNo" type="hidden" value="${sub.index}"/>
                                                            <input name="subject" type="hidden" value="${subject.subjectId}"/>
                                                            <input name="pageId" type="hidden" value="${pages.pageId}"/>
                                                            <c:if test='${subject.must == 0}'>
                                                                <span>*</span>
                                                            </c:if>
                                                            <c:if test='${subject.must == 1}'>
                                                                <span class="active">*</span>
                                                            </c:if>
                                                            <c:if test="${reelVo.showNo == 0}"><span>${subject.subNo}</span></c:if>
                                                            <c:if test="${reelVo.showNo == 1}"><span></span></c:if>
                                                            <span>${subject.topic}</span>
                                                            <i>多选</i>
                                                            <span class="point">此题未填</span>
                                                            <div class="question-desc">${subject.remark}</div>
                                                        </div>
                                                        <div class="question-item-content">
                                                            <c:forEach items="${subject.optionsList}" var="options">
                                                                <div class="radio-primary radio-btn">
                                                                    <input type="radio" id="${options.optionsId}" name="options${subject.subNo}" value="${options.optionsId}">
                                                                    <label for="${options.optionsId}">${options.options}</label>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </c:when>
                                                <c:when test="${subject.subjectType == 2}">
                                                    <!-- 多选题 -->
                                                    <div class="question-item">
                                                        <div class="question-item-title">
                                                            <input name="subjectNo" type="hidden" value="${sub.index}">
                                                            <input name="subject" type="hidden" value="${subject.subjectId}">
                                                            <input name="pageId" type="hidden" value="${pages.pageId}" >
                                                            <input  id ="subjectType" name="subjectType" type="hidden" value="${subject.subjectType}" >
                                                            <input  id ="subjectType" name="subjectType" type="hidden" value="${subject.subjectType}" >
                                                            <c:if test='${subject.must == 0}'>
                                                                <span>*</span>
                                                            </c:if>
                                                            <c:if test='${subject.must == 1}'>
                                                                <span class="active">*</span>
                                                            </c:if>
                                                            <c:if test="${reelVo.showNo == 0}"><span>${subject.subNo}</span></c:if>
                                                            <c:if test="${reelVo.showNo == 1}"><span></span></c:if>
                                                            <span>${subject.topic}</span>
                                                            <i>多选</i>
                                                            <span class="point">此题未填</span>
                                                            <div class="question-desc">${subject.remark}</div>
                                                        </div>
                                                        <div class="question-item-content">
                                                            <c:forEach items="${subject.optionsList}" var="options">
                                                                <c:choose>
                                                                    <%--其它选项--%>
                                                                    <c:when test="${options.isMultipleChoice == 0}">
                                                                        <div class="checkbox-primary checkbox-btn" style="display: flex;">
                                                                            <input type="checkbox"
                                                                                   name="options${subject.subNo}"
                                                                                   id="${options.optionsId}"
                                                                                   value="${options.optionsId}"
                                                                                   data-question-type="${subject.subjectType}"
                                                                                   style="width: 20px;height: 100%; "
                                                                            />
                                                                            <input name="isMultipleChoice" type="hidden" value="${options.isMultipleChoice}">
                                                                            <label class="wrap-other" style="width:calc(100% - 20px);display: flex;align-items: center;">
                                                                                <div style='width:70px;text-align:center;'>其他：</div>
                                                                                <label style="width:calc(100% - 70px);height:40px;border-bottom: 1px solid #ccc;" for="${options.optionsId}">${options.options}</label>
                                                                            </label>
                                                                        </div>
                                                                    </c:when>
                                                                    <%--正常选项--%>
                                                                    <c:otherwise>
                                                                        <div class="checkbox-primary checkbox-btn">
                                                                            <input name="isMultipleChoice" type="hidden" value="${options.isMultipleChoice}">
                                                                            <input type="checkbox" id="${options.optionsId}" name="options${subject.subNo}" data-question-type="${subject.subjectType}" value="${options.optionsId}">
                                                                            <label for="${options.optionsId}">
                                                                                    ${options.options}
                                                                            </label>
                                                                        </div>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </c:when>
                                                <c:when test="${subject.subjectType == 4}">
                                                    <!--  单行文本-->
                                                    <div class="question-item">
                                                        <div class="question-item-title">
                                                            <input name="subjectNo" type="hidden" value="${sub.index}">
                                                            <input name="subject" type="hidden" value="${subject.subjectId}">
                                                            <input name="pageId" type="hidden" value="${pages.pageId}" >
                                                            <input  id ="subjectType"name="subjectType" type="hidden" value="${subject.subjectType}"/>
                                                            <c:if test='${subject.must == 0}'>
                                                                <span>*</span>
                                                            </c:if>
                                                            <c:if test='${subject.must == 1}'>
                                                                <span class="active">*</span>
                                                            </c:if>
                                                            <c:if test="${reelVo.showNo == 0}"><span>${subject.subNo}</span></c:if>
                                                            <c:if test="${reelVo.showNo == 1}"><span></span></c:if>
                                                            <span>${subject.topic}</span>
                                                            <i>单行文本</i>
                                                            <span class="point">此题未填</span>
                                                            <div class="question-desc">${subject.remark}</div>
                                                        </div>
                                                        <div class="question-item-content">
                                                            <c:forEach items="${subject.optionsList}" var="options">
                                                                <div class="box-single__line" style="margin-bottom:10px;">
                                                                    <div class="fieldset"><div class="label"></div>
                                                                        <input type="hidden"
                                                                               name="optionsText"
                                                                               class="single-text"
                                                                               id="${options.optionsId}"
                                                                               value="${options.optionsId}"
                                                                               data-question-type="${subject.subjectType}"
                                                                               style="display: none"/>
                                                                        <div class="editable optionIpt single-line-text" contenteditable="true"
                                                                             id="single-line-text"
                                                                             data-id="${options.optionsId}"`
                                                                             data-placeholder="单行文本"
                                                                             data-type="content"
                                                                             data-option-type="SINGLE"
                                                                             style="padding: 10px;
																	 line-height: 20px;
																	 min-height: 40px;
																	 border: 1px solid #CCC;">
                                                                                ${options.options}
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </c:when>
                                            </c:choose>
                                        </c:forEach>
                                    </div>
                                    <!-- 提交按钮 -->
                                    <div class="page-control">
                                        <div class="page-control-btn-group">
                                            <button class="btn-submit prevPage" index-page=${status.index}><span>上一页</span></button>
                                            <button class="btn-submit nextPage" index-page=${status.index} signIndex=1><span>下一页</span></button>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <!-- 底部 -->
                        <div class="page-footer  copyright">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="<%=path %>/template/js/fill.js"></script>
    <script src="<%=path %>/template/js/skin.js"></script>
</body>
</html>