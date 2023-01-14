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

	<!-- jsp文件头和头部 -->
	<%-- <%@ include file="top.jsp"%> --%>
	<meta charset="UTF-8">
    <title>我的问卷</title>
    <link rel="stylesheet" href="/questionnaire/template/css/reset.css">
    <link rel="stylesheet" href="/questionnaire/template/css/common.css">
    <link rel="stylesheet" href="/questionnaire/template/css/home.css">
    <link rel="stylesheet" href="/questionnaire/template/css/my-questionnaire.css">

	<script type="text/javascript">
        window.onload = function (ev) {
            var page = document.getElementsByClassName("page")[0]
            var main = page.getElementsByClassName("main")[0]
            var height = document.body.offsetHeight - 126
            var panel = main.getElementsByClassName("questionnaire-panel")[0]
            panel.style.height = (height - 80) + "px"
        }
	</script>
</head>
	<body>
	
		<!-- 页面顶部¨ -->
		<%@ include file="head.jsp"%>


		  <!--操作栏-->
        <div class="operation clearfix">
            <!--基本操作-->
            <div class="base-operator">
                <div class="active" onclick="changeOperation(event)">编辑</div>
                <div onclick="changeOperation(event)">皮肤</div>
                <div onclick="changeOperation(event)">设置</div>
                <div onclick="changeOperation(event)">投放</div>
                <div onclick="changeOperation(event)">统计</div>
            </div>
            <!--效果预览-->
            <div class="effect-operator" >
                <div class="active" onclick="changeOperation(event)">文本编辑</div>
                <div onclick="changeOperation(event)">预览</div>
                <div onclick="changeOperation(event)">开始调查</div>
            </div>
        </div>

			<!-- 左侧菜单 -->
			 <div class="main clearfix">
			 	<%@ include file="left.jsp"%>
			 	
			 	<input type="text" id="rId" name="rId" value="${rId}"/>111
			 	
			 	
			 	
	          <!--问卷面板-->
            <div class="questionnaire-panel">
                <!--问卷页tab-->
                <div class="tab-page">
                    <!--前一页-->
                    <div class="tab-btn-forward" onclick="toForwardPage(event)"></div>
                    <div class="tab-page-index active" data-index="0" onclick="changeQuestionnairePage(event)">
                        <span>第一页</span>
                        <i onclick="deletePage(event)"></i>
                    </div>
                    <!--后一页-->
                    <div class="tab-btn-next" onclick="toNextPage(event)"></div>
                    <!--新建tab页-->
                    <div class="tab-btn-add" onclick="addQuestionnairePage(event)"></div>
                </div>
                <!--问卷面板-->
                <div class="panel">
                    <!--问卷面板页-->
                    <div class="panel-page" data-pageindex="0">
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
                        
                        
                        	
                        </div>
                         
                    </div>
                </div>
            </div>
       </div>
      
      
     
<script type="text/html" id="subject-edit">
<div class="reel-edit">                    
	 <form action="">
	 <input type="hidden" id="subjectId" name="subjectId" value="${subjectId}">
	 <input type="hidden" id="pageId" name="pageId" value="${pageId}">
    
	   <!-- 题目-->
		<div class="fieldset">
			<label for="question-title">题目:</label>
			<input id="topic" type="text" name="topic">
		</div>
		<!--备注-->
		<div class="fieldset">
			<label for="question-remark">备注:</label>
			<input id="remark" type="text" name="remark">
		</div>
		<!--题目类型切换-->
		<div class="fieldset">
			<label for="question-type"></label>
			<select id="subjectType">
				<option value="1">单选题</option>
				<option value="2">多选题</option>
				<option value="3">下拉题</option>
				<option value="4">单行文本题</option>
			</select>
		</div>
		<input type="button" value="上传" onclick="shangchuan()">
		<!--选项一-->
        <div class="all-option">
		<div class="fieldset add-options">
			<label class="question-option-label" for="question-option-0"></label>
			<input class="question-option"  type="text" name="options" placeholder="选项"/>
			<span class="btn-delete-option" onclick="deloption()"></span>
		</div>
        </div>
		
		<!--新建选项-->
		<div class="fieldset">
			<label></label>
			<div class="btn-add-option">新建选项</div>
		</div>
		<!--添加选项-->
		<div class="fieldset">
			<label></label>
			<div class="add-other-option">添加[其他]项</div>
			<div class="batch-update">批量修改</div>
		</div>
		<!--展开高级-->
		<div class="fieldset">
			<div class="open-higher-set">
				<span class="">展开高级设置</span>
			</div>
		</div>
		<div class="fieldset">
			<div class="senior-option">
				<input type="checkbox" name="" value=""/>
				<label for="">设置为测评题目</label>
			</div>
			<div class="senior-option">
				<input type="checkbox" name="" value=""/>
				<label for="">选项引用</label>
			</div>
		</div>
		<!--确定取消-->
		<div class="fieldset-submit">
			<label></label>
			<button>取消</button>
			<button type="button" id="subjectSave" class="button-active">确定</button>
		</div>
	</form>
  </div>
</script>     
<script type="text/javascript" src="/questionnaire/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>
<script src="/questionnaire/template/js/page.js"></script>
<script type="text/javascript">
 /*  $(function() {
	 var rId = $("input[name=rId]").val();
	 alert(rId);
		 $.ajax({
		       type:'get',
		       url:"/questionnaire/reel/queryAllSubject",
		       data:{
		    	   "rId":rId
		       },
		       success:function(result){
  	       
             }
         })
 });  */ 

 /* $("#subjectSave").click(function(){
	 if($("#topic").val().trim() == ""){
		alert("题目不能为空！");
		$("#topic").focus();
		return false;
	}
	$("#Form").submit();
 
})   */ 

  
$(".components>div").click(function(){
	if($(this).attr("index")){
		if($(".wrap-question-list").find(".reel-edit").length>0){
			return false;
		}else{
			$(".wrap-question-list").append($("#subject-edit").html())
			for(var i =0; i<$(".wrap-question-list").find("#subjectType>option").length; i++){
				if($(this).attr("index") == $(".wrap-question-list").find("#subjectType>option")[i].value){
					$($(".wrap-question-list").find("#subjectType>option")[i]).prop("selected",true)
				}
			}
			
		}
	}
	/**
	 * 题目新增保存
	 */
	$("#subjectSave").click(function(){
		debugger
		var subjectId = $("#subjectId").val();
		var pageId = $("#pageId").val();
		var topic = $("#topic").val();
		var remark = $("#remark").val();
		var subjectType = $("#subjectType").val();
		var options = "";
		$("input[name=options]").each(function(){
			// options+=$(this).val()+",";
			options+=$(this).val()+"&&&&";
		})
		$.ajax({
		       type:'post',
		       url:"/questionnaire/reel/subjectSave",
		       data:{
		    	   "subjectId":subjectId,
		    	   "pageId":pageId,
		    	   "topic":topic,
		    	   "remark":remark,
		    	   "subjectType":subjectType,
		    	   "options":options
		       },
		       success:function(msg){
		    	   $("#rId").val(rId);
		    	   alert(msg);
		    	   $("form").css("display","none")
		    	   $.ajax({
				       type:'get',
				       url:"/questionnaire/reel/queryAllSubject",
				       data:{
				    	   "rId":msg
				       },
				       success:function(result){
				    	   for(var i=0;i<result.length;i++){
				    		 var json = JSON.stringify(result[i]);
				    		 var subjectList  = $.parseJSON(json).subjectList;//题
				    		 var optionsList  = $.parseJSON(json).optionsList;//选项 
				    		   for(var k=0;k<subjectList.length;k++){
			                       var div="<div class=\"radio-title\"><span>"+subjectList[k].topic+"</span></div><div class=\"radio-remark\">备注："+subjectList[k].remark+"</div>";
				    	           $(".wrap-question-list").append(div);
				    		   }
				    		   for(var j=0;j<optionsList.length;j++){
				    		       var div1="<div class=\"radio-option\"><input type=\"radio\">"+optionsList[j].options+"</div>";
				    		       $(".wrap-question-list").append(div1);
				    		   }
				    	   }
				    	   location.reload();
		               }
		           })
		       }
		})
	})

	/**
	 *  新建【普通】选项
	 */
	$(".btn-add-option").click(function(){
		var input=`<div class="fieldset add-options">
					<label class="question-option-label"></label>
					<input class="question-option" type="text" name="options" placeholder="选项"/>
					<span class="btn-delete-option"></span>
				  </div>`;
		 $(".add-options").last().after(input);
		
		 $(".btn-delete-option").click(function(){
		 	/** 只剩一个选项不能删除 */
			// if($(".all-option").children().length-1 <= 0){
			// 	// alert("只剩一个选项不能删除，必须保留当前选项！");
			// 	return false;
			// }
			$(this).parent().remove();
		}) 
		
	})
	
	/* function deloption(){
		alert("22");
		if($(".all-option").children().length-1 <= 0){
			alert("不能删除当前");
			return
		}
		$(this).parent().remove();
	} */
	
	
	/*  $(".btn-delete-option").click(function(){
		if($(".all-option").children().length-1 <= 0){
			alert("不能删除当前");
			return false;
		}
		$(this).parent().remove();
		
 	
	})  */
	
})


	

	function shangchuan(){
		location.href = "/questionnaire/reel/toUpload";
	}
 
 
</script>
	</body>
</html>