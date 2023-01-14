<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="zh-cn">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>填报问卷</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0,viewport-fit=cover">
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv="Cache-Control" content="no-cache">
		<!--[if lte IE 8]>
            <script id="browser_upgrade" src="//h5app.qq.com/act/lib/browser_upgrade/dist.js"></script>
		<![endif]-->
		<link rel="shortcut icon" href="<%=path%>/template/image/logo/logo-title.ico">
		<link rel="stylesheet" href="<%=path %>/template/zui/css/zui.min.css">
		<link rel="stylesheet" href="<%=path %>/template/css/desktop.css">
		<link rel="stylesheet" href="<%=path %>/template/css/subject.css">
		<link rel="stylesheet" href="<%=path %>/template/css/fill.css">
		<style id="wj_skin_css">.btn, .page-btn{color:rgba(255,255,255,1);background-color:rgba(40,99,243,1);}.ui-button-primary{background-color:rgba(40,99,243,1);border-color:rgba(40,99,243,1);}.btn:before, .page-btn:before{background-color:rgba(0,0,0,0.1);}.ui-button-primary:hover,.ui-button-primary:active{background-color:rgba(36,89,219,1);border-color:rgba(36,89,219,1);}.page-btn-second{color:rgba(40,99,243,1);background-color:rgba(255,255,255,1);}strong, strong span{color:rgba(40,99,243,1);}.question-tag.error{background-color:rgba(218,35,25,1);}.question-required{color:rgba(218,35,25,1);}.dialog-container{color:rgba(0,0,0,1);}body{color:rgba(0,0,0,1);}.s-page, .page, .page-survey, .cascade-pop-page, .page .page-end-content{background-color:rgba(242,242,242,1);}.s-main, .question{background-color:rgba(255,255,255,1);}.copyright{color:rgba(0,0,0,0.3);}.s-prog-wait{background-color:rgba(0,0,0,0.05);}.survey-mobile .progress-bar{background-color:rgba(255,255,255,1);}.s-prog-done, .page-survey .progress-done{background-color:rgba(40,99,243,1);}.page-survey .progress{background-color:rgba(230,230,230,1);}.s-ques-desc{color:rgba(0,0,0,0.6);}.question-tag{background-color:rgba(0,0,0,0.05);}.checkbox-input:checked + .checkbox-cell .radio-box{border-color:rgba(40,99,243,1);}.checkbox-input + .checkbox-cell .radio-box{border-color:rgba(0,0,0,0.3);}.checkbox-input:checked + .checkbox-cell .check-box{background-color:rgba(40,99,243,1);}.checkbox-input + .checkbox-cell .check-box{border-color:rgba(0,0,0,0.3);}.checkbox-input:checked + .checkbox-cell{color:rgba(40,99,243,1);}.check-box .checkbox-icon, .checkbox-input:checked + .checkbox-cell .check-box .checkbox-icon{stroke:rgba(255,255,255,1);}.selectbox, .question-select-list{border-color:rgba(0,0,0,0.2);}.selectbox.on{border-color:rgba(40,99,243,1);}.question-select-list li .tick{stroke:rgba(40,99,243,1);}.selectbox:after{border-top-color:rgba(0,0,0,1);}.inputs-input, .inputs-textarea{border-color:rgba(0,0,0,0.2);}.inputs-input:focus, .inputs-textarea:focus{border-color:rgba(40,99,243,1);}.inputs-input::placeholder, .inputs-textarea::placeholder{color:rgba(0,0,0,0.3);}.question-tips span {background-color:rgba(242,242,242,1);}.question-tips span:before{background-color:rgba(242,242,242,1);}.checkbtn-cont, .checkbtn-label{background-color:rgba(0,0,0,0.1);color:rgba(0,0,0,1);}.checkbtn-group .checkbtn-input:checked + .checkbtn-label:after{background-color:rgba(40,99,243,1);color:rgba(255,255,255,1);}.checkbtn-group .checkbtn-input:checked + .checkbtn-label{color:rgba(255,255,255,1);}.slider-bar{background-color:rgba(0,0,0,0.1);}.slider-handle{background-color:rgba(40,99,243,1);}.slider-tip{background-color:rgba(40,99,243,1);color:rgba(255,255,255,1);}.slider-tip:after{border-top-color:rgba(40,99,243,1);}.s-slider-bar-done{background-color:rgba(40,99,243,1);}.slider-handle-num{background-color:rgba(40,99,243,1);}.slider-handle i{background-color:rgba(255,255,255,1);}.slider-tip-num, .slider-handle-num{color:rgba(255,255,255,1);}.linkage-list{border-color:rgba(0,0,0,0.2);}.linkage-item{border-bottom-color:rgba(0,0,0,0.1);}.linkage-item:after{background-color:rgba(40,99,243,1);border-color:rgba(40,99,243,1);}.linkage-list .linkage-item.on{color:rgba(255,255,255,1);}.linkage-item.not-end:before{border-left-color:rgba(0,0,0,1);}.linkage-list .linkage-item.on:before{border-left-color:rgba(255,255,255,1);}.cascade-pop-page-body-content{background-color:rgba(255,255,255,1);}.cascade-pop-page-back{color:rgba(40,99,243,1);}.checkbox-option:not(:first-child):before{border-top-color:rgba(0,0,0,0.1);}.icon-arrow-right, .icon-arrow-down{fill:rgba(0,0,0,0.2);}.checkbox-icon{fill:rgba(40,99,243,1);}.checkbox-cell-ft:before{box-shadow:0 0 0 8px #2863f3 inset;;}.cascade-cell:not(:first-child):after{border-top-color:rgba(0,0,0,0.1);}.ui-radio input:checked + label::before{border-color:rgba(40,99,243,1);}.ui-radio input + label::before{border-color:rgba(0,0,0,0.3);}.ui-checkbox input:checked + label .check-box{background-color:rgba(40,99,243,1);}.ui-checkbox input + label .check-box{border-color:rgba(0,0,0,0.3);}.matrix-table tr{border-bottom-color:rgba(0,0,0,0.1);}.checkbtn-cont:after, .checkbtn-label:after{background-color:rgba(40,99,243,1);color:rgba(255,255,255,1);}.checkbtn-input:checked + label strong, .checkbtn-input:checked + label strong span{color:rgba(255,255,255,1);}.upload-trigger{border-color:rgba(0,0,0,0.2);}.sort-box, .sort-item{border-color:rgba(0,0,0,0.2);}.sort-num{background-color:rgba(0,0,0,0.05);}.sort-num-item.active{background-color:rgba(40,99,243,1);color:rgba(255,255,255,1);}.sort-handle-line:before, .sort-handle-line:after{background-color:rgba(0,0,0,0.2);}.sort-item.gu-mirror{background-color:rgba(40,99,243,1);color:rgba(255,255,255,1);}.sort-item.gu-mirror .sort-handle-line:before, .sort-item.gu-mirror .sort-handle-line:after{background-color:rgba(255,255,255,1);}.sort-row:not(:first-child):before{border-top-color:rgba(0,0,0,0.1);}.sort.sorted:not(.sorting) .sort-seq{background-color:rgba(40,99,243,1);color:rgba(255,255,255,1);}.cell:before{background-color:rgba(40,99,243,1);}.moving .sort-seq{color:rgba(40,99,243,1);background-color:rgba(255,255,255,1);}.survey-mobile .moving .sort-handle-line:nth-child(1):before,.survey-mobile .moving .sort-handle-line:nth-child(3):before,.survey-mobile .moving .sort-handle-line:nth-child(1):after,.survey-mobile .moving .sort-handle-line:nth-child(3):after{background-color:rgba(255,255,255,1);}.survey-mobile .sort-handle-line:nth-child(1):before,.survey-mobile  .sort-handle-line:nth-child(3):before,.survey-mobile  .sort-handle-line:nth-child(1):after,.survey-mobile .sort-handle-line:nth-child(3):after{background-color:rgba(0,0,0,0.2);}.survey-mobile .sort-handle-line:nth-child(2){background-color:rgba(0,0,0,0.2);}.sort-seq{color:rgba(255,255,255,1);background-color:rgba(0,0,0,0.2);}.question-head .mod_fillblank,.question-head input.mod_fillblank{color:rgba(40,99,243,1);border-bottom-color:rgba(0,0,0,1);}.mod_fillblank.active{border-bottom-color:rgba(40,99,243,1);}.page-cover-pic .laptop .quote-l-bg{background-color:rgba(40,99,243,1);}.page-cover-pic .laptop .quote-l-drop{border-top-color:rgba(40,99,243,1);border-left-color:rgba(40,99,243,1);}.page-cover-pic .laptop .pen-b{background-color:rgba(40,99,243,1);}.page-cover-pic .laptop .quote-r-dot{background-color:rgba(40,99,243,1);}.page-endpic-paper:before{border-top-color:rgba(242,242,242,1);border-right-color:rgba(242,242,242,1);}.page-endpic-icon-circle{fill:rgba(40,99,243,1);}.page-endpic-icon-check{fill:rgba(255,255,255,1);}.page-cover-icon{border-color:rgba(40,99,243,1);}.page-cover-icon-arr{fill:rgba(40,99,243,1);}.page-cover-pic-pen .icon-pen-body{fill:rgba(40,99,243,1);}.page-cover-pic-quote-r-dot{background-color:rgba(40,99,243,1);}.page-cover-pic-quote-l-bg .icon-bubble{fill:rgba(40,99,243,1);}.top-tips{background-color:rgba(255,255,255,1);color:rgba(0,0,0,1);}.reward-tips-link{color:rgba(40,99,243,1);}.icon-survey-gift, .page-cover .page-tip-gift .icon-survey-gift{fill:rgba(0,0,0,1);}.top-tips-wrap .icon-close, .page-tip .icon-arrow-right, .reward-back .icon-arrow-left{stroke:rgba(0,0,0,0.2);}.page-tip{background-color:rgba(0,0,0,0.05);color:rgba(0,0,0,1);}.page-tip_warn{background-color:rgba(218,35,25,1);}.page-reward .reward-title{background-color:rgba(0,0,0,0.05);color:rgba(0,0,0,1);}.page-reward .reward-content{color:rgba(0,0,0,1);}.page-btn-wrap .page-btn-second:hover{background-color:rgba(244,247,254,1);}.page-btn-second:before {background-color:rgba(0,0,0,0.05);}.progress{background-color:rgba(255,255,255,1);}.checkbox-cell:hover{background-color:rgba(40,99,243,0.1);}.ui-checkbox input + label .check-box .tick{stroke:rgba(255,255,255,1);}.question-select-list{background-color:rgba(255,255,255,1);}.question-select-list li:hover {background-color:rgba(40,99,243,0.1);color:rgba(40,99,243,1);}.select-list-li.on{color:rgba(40,99,243,1);}.checkbtn-label:hover{background-color:rgba(40,99,243,0.1);color:rgba(40,99,243,1);}.linkage-item:hover{background-color:rgba(40,99,243,0.1);color:rgba(40,99,243,1);}.linkage-item:hover:before{border-left-color:rgba(40,99,243,1);}.clickBlock:hover{background-color:rgba(40,99,243,0.1);}.sort-list .sort-item:hover{border-color:rgba(40,99,243,1);}.sort-tips{color:rgba(0,0,0,0.3);}.inputs-input,.inputs-textarea{background-color:rgba(255,255,255,1);}.upload-size{color:rgba(0,0,0,1);}.upload-trigger .btn_s, .upload-lt .btn_s{color:rgba(40,99,243,1);}</style>
		<style type="text/css">
			/*.question-item .question-item-wrap .question-item-content .checkbox-primary .wrap-other{
				width:calc(100% - 20px);
				display: flex;
				align-items: center;
			}

			.question-item .question-item-wrap .question-item-content .checkbox-primary .wrap-other label{
				width:calc(100% - 70px);
				border-bottom: 1px solid #ccc;
			}
			.question-item .question-item-wrap .question-item-content .checkbox-primary label {
				line-height: 20px;
			}*/
			.wrap-other{
				width:calc(100% - 20px);
				display: flex;
				align-items: center;
			}
			.wrap-other .textarea-other{
				width:calc(100% - 70px);
				min-height:20px;
				line-height: 30px;
				border-bottom: 1px solid #ccc;
				outline: none;
			}
			.wrap-other>.span-other{
				width:70px;
				text-align:center;
			}
			.single-line-text{
				padding: 10px;
				line-height: 20px;
				height: 40px;
				border: 1px solid #CCC;
			}
		</style>
	</head>
	<body class="survey-pc skin">
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
							<div class="release-person">发布人：${reelVo.createPeo}</div>
							<div class="survey-header-subtitle" style="text-align: left;">${reelVo.startLanguage}</div>
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
							<div class="question-list" style="width: 100%;">
							    <c:forEach items="${pages.subjectList}" var="subject" varStatus="sub">
							        <c:choose>
							        <c:when test="${subject.subjectType == 1}">
								    <!-- 单选题 -->
								    <div class="question-item" style="padding: 0;width: 100%;">
									<div class="question-item-title" style="display:flex;">
									    <input name="subjectNo" type="hidden" value="${sub.index}">
									    <input name="subject" type="hidden" value="${subject.subjectId}">
									    <input name="pageId" type="hidden" value="${pages.pageId}" >
										<c:if test='${subject.must == 0}'>
											<span class="biaoji">*</span>
										</c:if>
										<c:if test='${subject.must == 1}'>
											<span class="active">*</span>
										</c:if>
										<c:if test="${reelVo.showNo == 0}"><span>${subject.subNo}</span></c:if>
										<c:if test="${reelVo.showNo == 1}"><span></span></c:if>
										<span>${subject.topic}</span>
										<span class="point">此题未填</span>
									</div>
									<div class="question-desc">${subject.remark}</div>
									<div class="question-item-content">
									    <c:forEach items="${subject.optionsList}" var="options">
										<div class="radio-primary radio-btn">
											<input type="radio" name="options${subject.subNo}" id="${options.optionsId}" value="${options.optionsId}">
											<label for="${options.optionsId}">${options.options}</label>
										</div>
									    </c:forEach>
									</div>
								    </div>
							        </c:when>
							        <c:when test="${subject.subjectType == 2}">
							        <!-- 多选题 -->
								    <div class="question-item" style="padding: 0;width: 100%;">
									    <div class="question-item-title" style="display: flex;">
									        <input name="subjectNo" type="hidden" value="${sub.index}">
										    <input name="subject" type="hidden" value="${subject.subjectId}">
										    <input name="pageId" type="hidden" value="${pages.pageId}" >
											<c:if test='${subject.must == 0}'>
												<span class="biaoji">*</span>
											</c:if>
											<c:if test='${subject.must == 1}'>
												<span class="active">*</span>
											</c:if>
											<c:if test="${reelVo.showNo == 0}"><span>${subject.subNo}</span></c:if>
											<c:if test="${reelVo.showNo == 1}"><span></span></c:if>
											<span>${subject.topic}</span>
											<i>多选</i>
											<span class="point">此题未填</span>
									    </div>
										<div class="question-desc">${subject.remark}</div>
									    <div class="question-item-content">
									    <c:forEach items="${subject.optionsList}" var="options">
											<c:choose>
												<%--其它选项--%>
												<c:when test="${options.isMultipleChoice == 0}">
													<div class="checkbox-primary checkbox-btn" style="display: flex;">
														<input type='checkbox'
															   name="options${subject.subNo}"
															   id="${options.optionsId}"
															   value="${options.optionsId}"
															   data-question-type="${subject.subjectType}"
															   style="width: 20px;height: 100%;"
														/>
														<label for="${options.optionsId}"></label>
														<div class="wrap-other">
															<div class="span-other">其他：</div>
															<div class="textarea-other" contenteditable="true" >${options.options}</div>
														</div>
													</div>
												</c:when>
												<%--正常选项--%>
												<c:otherwise>
												   <div class="checkbox-primary checkbox-btn">
														<input type="checkbox" id="${options.optionsId}" name="options${subject.subNo}" value="${options.optionsId}">
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
											<div class="question-item" style="padding: 0;width: 100%;">
												<div class="question-item-title" style="display: flex;">
													<input name="subjectNo" type="hidden" value="${sub.index}">
													<input name="subject" type="hidden" value="${subject.subjectId}">
													<input name="pageId" type="hidden" value="${pages.pageId}" >
													<%--必填--%>
													<c:if test='${subject.must == 0}'>
														<span class="biaoji">*</span>
													</c:if>
													<c:if test='${subject.must == 1}'>
														<span class="active">*</span>
													</c:if>
													<%--编号--%>
													<c:if test="${reelVo.showNo == 0}"><span>${subject.subNo}</span></c:if>
													<c:if test="${reelVo.showNo == 1}"><span></span></c:if>
													<span>${subject.topic}</span>
													<span class="point">此题未填</span>
												</div>
												<div class="question-desc">${subject.remark}</div>
												<div class="question-item-content">
													<c:forEach items="${subject.optionsList}" var="options">
														<div class="box-single__line" style="margin-bottom:10px;">
															<div class="fieldset"><div class="label"></div>
																<input type="hidden"
																	   name="options${subject.subNo}"
																	   id="${options.optionsId}"
																	   value="${options.optionsId}"
																	   style="display: none"/>
																<div class="editable optionIpt single-line-text"
																	 id="single-line-text"
																	 data-id="${options.optionsId}"`
																	 contenteditable="true"
																	 data-placeholder="单行文本"
																	 data-type="content"
																	 data-option-type="SINGLE">
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
								<button class="btn-submit nextPage" index-page=${status.index}><span>下一页</span></button>
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
		<script src="<%=path %>/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>
		<script src="<%=path %>/template/zui/js/zui.min.js"></script>
		<script src="<%=path %>/template/js/editorPlgins.js"></script>
		<script src="<%=path %>/template/js/preview.js"></script>
	</body>
</html>
