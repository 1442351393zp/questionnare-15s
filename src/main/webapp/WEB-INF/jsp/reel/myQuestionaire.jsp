<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我的问卷</title>
    <link rel="stylesheet" href="../template/css/reset.css">
    <link rel="stylesheet" href="../template/css/common.css">
    <link rel="stylesheet" href="../template/css/home.css">
    <link rel="stylesheet" href="../template/css/my-questionnaire.css">
</head>
<body>

	 <!-- 页面顶部¨ -->
	<%@ include file="head.jsp"%>
	
    <div class="page" style="width: 90%;margin: 0 auto">
        <!--问卷主内容-->
        <div class="main clearfix">
            <!--菜单栏-->
            <div class="menu">
                <div class="menu-tab clearfix">
                    <div class="tab-component active">题目控件</div>
                    <div class="tab-outline">问卷大纲</div>
                </div>
                <div class="menu-content">
                    <!--题目控件列表-->
                    <div class="components">
                        <div>单选题</div>
                        <div>多选题</div>
                        <div>单行文本题</div>
                        <div>多行文本题</div>
                        <div>量表题</div>
                        <div>矩阵单选题</div>
                        <div>矩阵多选题</div>
                        <div>矩阵量表题</div>
                        <div>排序题</div>
                        <div>联动题</div>
                        <div>附件题</div>
                        <div>文本描述</div>
                        <div>填空题</div>
                        <div>多行文本题</div>
                        <div>多行文本题</div>
                        <div>多行文本题</div>
                        <div>多行文本题</div>
                    </div>
                    <!--大纲列表-->
                    <div class="outline"></div>
                </div>
            </div>
            <!--问卷面板-->
            <div class="questionnaire-panel">
                <!--问卷页tab-->
                <div class="tab-page">
                    <!--前一页-->
                    <div class="tab-btn-forward"></div>
                    <div class="tab-page-index active">第一页</div>
                    <div class="tab-page-index">第二页</div>
                    <div class="tab-page-index">第三页</div>
                    <!--后一页-->
                    <div class="tab-btn-next"></div>
                    <!--新建ta页-->
                    <div class="tab-btn-add"></div>
                </div>
                <!--问卷面板-->
                <div class="panel">
                    <!--问卷面板页-->
                    <div class="panel-page">
                        <!--标题-->
                        <div class="wrap">
                            <h1>问卷标题</h1>
                        </div>
                        <!--标题说明-->
                        <div class="wrap">
                            <p>
                                为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们，
                                我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！
                            </p>
                        </div>
                        
                        <div class="wrap-question-list">
                           <form action="/questionnaire/reel/subjectSave" id="Form" method="post">
                           <input type="hidden" id="subjectId" value="${subjectId}">
                                <div class="fieldset">
                                    <label for="question-title">题目:</label>
                                    <input id="topic" type="text" name="topic">
                                   <img alt="123" src="../template/image/app.png" width="30px"/>
                                </div>
                                <div class="fieldset">
                                    <label for="question-remark">备注:</label>
                                    <input id="remark" type="text" name="remark">
                                </div>
                                <div class="fieldset">
                                    <label for="question-type"></label>
                                    <select id="subjectType">
                                        <option value="1" selected>单选题</option>
                                        <option value="2">多选题</option>
                                        <option value="3">下拉题</option>
                                        <option value="4">单行文本题</option>
                                    </select>
                                    <input type="button" value="上传" onclick="shangchuan()">
                                    
								 
                                </div>
                                <div class="fieldset">
                                    <label class="question-option-label" for="question-option-0"></label>
                                    <input class="question-option"  type="text" name="options" placeholder="选项"/>
                                    <span class="btn-delete-option"></span>
                                </div>
                                <div class="fieldset">
                                    <label class="question-option-label" for="question-option-1"></label>
                                    <input class="question-option"  name="options" type="text" placeholder="选项"/>
                                    <span class="btn-delete-option"></span>
                                </div>
                                <div class="fieldset">
                                    <label></label>
                                    <div class="btn-add-option">新建选项</div>
                                </div>
                                <div class="fieldset">
                                    <!--<span class="line"></span>-->
                                    <div class="open-higher-set">
                                        <span class="">展开高级设置</span>
                                    </div>
                                    
                                </div>
                                <button type="submit" id="subjectSave">保存</button>
                                
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script type="text/javascript" src="../template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
 $("#subjectSave").click(function(){
	 if($("#topic").val().trim() == ""){
		alert("题目不能为空！");
		$("#topic").focus();
		return false;
	}
	 /* $("#Form").ajaxSubmit(function(msg){
	  	}) */
	$("#Form").submit();
	/* $.ajax({
	       type:'get',
	       url:"/questionnaire/reel/subjectSave",
	       data:{
	    	   "topic":topic,
	    	   "remark":remark,
	    	   "subjectType":subjectType,
	       },
	       success:function(msg){
	    	   
	       }
	     */   
   
 }) 
 
	
	function shangchuan(){
		location.href = "/questionnaire/reel/toUpload";
	}
	
	

</script>
</body>
</html>