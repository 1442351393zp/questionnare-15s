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
    <style>
        .templateStyle{
            width: 100%;
            position:absolute!important;
            top:60px!important;
            left:0px!important;
            margin:0!important;
            height:auto!important;
            background:#fff;
            z-index:99;
            padding:5px 0px;
            font-size: 16px;
            border:1px solid #ccc;
        }
        .templateStyle div{
            width:100%;
            display:block!important;
            height:auto!important;
            text-align:left!important;
            margin:0!important;
            line-height:30px!important;
        }
        .templateStyle div.dataChildTitle{
            padding-left:10px;
            color:#000;
            cursor: default;
        }
        .templateStyle div.dataChildStyle div{
            padding-left:20px;
            box-sizing:border-box;
            border-top:1px solid #ccc;
        }
        .templateStyle div.dataChildStyle div:hover{
            color:#3280FC !important;
            background-color: #eee !important;
        }
        .dataChildStyle div:nth-child(3){
            background:none!important;
            padding:0 0 0 20px!important;
            color:#999!important;
        }
        .operation .effect-operator .dataChildStyle div:nth-child(3):hover{
            color:#145ccd!important;
            border-radius:0!important;
        }
        .operation .effect-operator .dataChildStyle>div:hover{
            background: rgba(0,0,0,0.2);
            color:#145ccd;
        }
    </style>
<base href="">

	<!-- jsp文件头和头部 -->
	<%-- <%@ include file="top.jsp"%> --%>
	<meta charset="UTF-8">
    <title>我的问卷</title>
     <link rel="stylesheet" href="<%=path %>/template/css/reset.css">
    <link rel="stylesheet" href="<%=path %>/template/css/home.css">
    <link rel="stylesheet" href="<%=path %>/template/css/my-questionnaire.css">
    <link rel="stylesheet" href="<%=path %>/template/css/subject.css"> 
    <%--  <script src="<%=path %>/template/js/page.js"></script> --%>
    <%--<script type="text/javascript" src="<%=path %>/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>--%>
    <script src="<%=path %>/template/js/common/nav.js"></script>
	<!--zui样式-->
    <link rel="stylesheet" href="<%=path %>/template/zui/css/zui.min.css"/>
    <link rel="stylesheet" href="<%=path %>/template/css/common.css"> 
    <link rel="stylesheet" href="<%=basePath%>/template/css/collect.css">
    <link rel="stylesheet" href="<%=path %>/template/css/statistics.css">
</head>
	<body>
		<!-- 页面顶部¨ -->
		<%-- <%@ include file="head.jsp"%> --%>
		
		<form id="param" method="post">
	        <input class="rId" name="rId" value="${rId}" type="hidden"/>
	        <input  name="pageId" value="${pageId}" type="hidden"/>
	        <input  id="folderId" value="${folderId}" type="hidden"/>
	        <input name="userId" id="userId" value="${userId}" type="hidden"/>
	        <input id="rstatus" class="reeltype" name="reelStatus" value="${reelStatus}" type="hidden"/>
		</form>
		<div class="operation clearfix">
            <!--基本操作-->
            <div class="base-operator">
                <div class="active" onclick="toSearch(this,'/questionnaire/reel/editpage','param','main')">编辑</div>
                <c:if test="${rId != null}">
                <div onclick="toSearch(this,'/questionnaire/reel/skin','param','main')">皮肤</div>
                <div onclick="toSearch(this,'/questionnaire/question/tosetup','param','main')">设置</div>
                <!-- <div onclick="changeOperation(event)">投放</div> -->
                <div class="count-btn" onclick="toSearch(this,'/questionnaire/reel/statistics','param','main')">统计</div>
                </c:if>
                <c:if test="${rId == null}">
                <!-- <div>皮肤</div> -->
                <div class="skin-btn">皮肤</div>
                <div class="set-btn">设置</div>
                <!-- <div>投放</div> -->
                <div class="count-btn">统计</div>
                </c:if>
                
            </div>

            <!--效果预览-->
            <div class="effect-operator">
                <div onclick="saveTemplate()" style="position:relative;">
                    保存为模板
                    <div class="templateStyle" style="display:none;"></div>
                </div>
                <div  class="reelText" ><i></i><span>文本编辑</span></div>
                <div class="active" onclick="toPreview()"><i></i><span>预览</span></div>
                <div onclick="startRecover(this)">
                    <i></i>
                    <span id="button" type="${reelStatus}" class="reelStarus">
                        <c:if test="${reelStatus=='0'}">暂停调查</c:if><c:if test="${reelStatus=='1'}">开始调查</c:if>
                    </span>
                </div>
            </div>
        </div>
		<div class="main clearfix">
        </div>

<script>
	$(function(){
		toSearch(this,'/questionnaire/reel/editpage','param','main');


		// 点击判断设置按钮是否有rId
        $(".set-btn").on("click",function(){
            if($(".rId").val() == ""){
                alertTip("你还没有保存该问卷")
            }else{
                toSearch(this,'/questionnaire/question/tosetup','param','main')
            }
        });
        $(".skin-btn").on("click",function(){
            if($(".rId").val() == ""){
                alertTip("你还没有保存该问卷")
            }else{
                toSearch(this,'/questionnaire/reel/skin','param','main')
            }
        });
        // 点击判断统计是否有
        $(".count-btn").on("click",function(){
            if($(".rId").val() == ""){
                alertTip("你还没有保存该问卷")
            }else{
                toSearch(this,'/questionnaire/reel/statistics','param','main')
            }

        })

	})
    // 获取保存模板的数据
    function saveTemData(){
        $.ajax({
            type:'post',
            url:"/questionnaire/template/choiceTemplate",
            //data:$('#param').serialize(),
            data:{

            },
            success:function(data){
                var data = data.data;
                if(data!==undefined && data!== null){
                    var html='';
                    var htmlCon='';
                    for(var i=0;i<data.length;i++){
                        htmlCon='';
                        for(var j=0;j<data[i].children.length;j++){
                            htmlCon+='<div class="typenameStyle" id="'+data[i].children[j].typeid+'">'+data[i].children[j].typename+'</div>'
                        }
                        html+='<div><div class="dataChildTitle">'+data[i].typename+'</div><div class="dataChildStyle" >'+htmlCon+'</div></div>'
                    }
                    $('.templateStyle').html(html)
                }
            }
        })
    }
    saveTemData();
    // 保存模板
    function saveTemplate(){
	    if($(".templateStyle").css("display") == "none"){
            $(".templateStyle").css("display","block");
        }else{
            $(".templateStyle").css("display","none");
        }
        var templateStyle = $('.templateStyle .dataChildStyle div')
        for(var i=0;i<templateStyle.length;i++){
            templateStyle[i].onclick=function(){
                var showPageIndex = $(".tab-page-index.active").not(".end-tab").attr("data-index");
                if(showPageIndex == 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length>2){
                    return alertTip("有未保存的题目")
                }else if(showPageIndex != 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length>0){
                    return alertTip("有未保存的题目")
                }
                var typeid=$(this).attr('id');
                var rId=$('.rId').val();
                $.ajax({
                    type:'post',
                    url:"/questionnaire/template/svaeTemple",
                    //data:$('#param').serialize(),
                    data:{
                        rid:rId,
                        typeid:typeid,
                        istype:'1'
                    },
                    success:function(data){
                        if(data.code == 0){
                            alertTip("保存成功")
                        }
                    }
                })
            }
        }
    }
	function toPreview(){
        var showPageIndex = $(".tab-page-index.active").not(".end-tab").attr("data-index");
        if(showPageIndex == 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length>2){
            return alertTip("有未保存的题目")
        }else if(showPageIndex != 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length>0){
            return alertTip("有未保存的题目")
        }
		var rId = $("#param").find(".rId").val()
		if(rId == ''){
            alertTip("你忘记创建题目啦")
			// new $.zui.Messager("你忘记创建题目啦",{
            //     placement:'center',
            //     type:'warning',
            //     time:1500,
            //     icon:"warning-sign"
            // }).show()
            return false
		}else{
          	window.open("/questionnaire/question/preview?reelId="+rId);

		}
    }
    // 点击开始回收
	function startRecover(self){
		var rId = $("#param").find(".rId").val()
        var title =$("#title").html();
        if(title == ""){
            $("#title").focus()
            return alertTip("标题为空");
        }
		if(rId == ''){
            alertTip("你忘记创建题目啦")
			// new $.zui.Messager("你忘记创建题目啦",{
            //     placement:'center',
            //     type:'warning',
            //     time:1500,
            //     icon:"warning-sign"
            // }).show()
            return false
		}else{
		    var reelStatus = ""
            if($(self).find("span").attr("type") == "1"){
                reelStatus = "0"
            }else{
                reelStatus = "1"
            }

		    // console.log($(self).find("span").attr("type"))
		    // if($(self).find("span").attr("type") == "1"){
            //     $(".reeltype").val("0")
            // }else{
            //     $(".reeltype").val("1")
            // }
           // console.log($('#param').serialize())
			$.ajax({
	    		type:'post',
	    		url:"/questionnaire/question/startresearch",
	    		//data:$('#param').serialize(),
                data:{
	    		    rId:$(".rId").val(),
                    reelStatus:reelStatus
                },
	    		success:function(data){
	    			var data = $.parseJSON(data);
	    			if(data.reelStatus == '0'){
	    			    //问卷属于暂停状态，点击开始回收
	    			    //$(".reeltype").val("0")
	    			    $("#button").html("暂停调查").attr("type",data.reelStatus);
                        alertTip("该问卷已开始调查");
	    			}else if(data.code == '2'){
	    			    alertTip("该问卷题目为空，禁止开始调查")
                    }
	    			else{
                        //问卷属于开始回收状态，点击暂停
                        //$(".reeltype").val("1")
	    			    $("#button").html("开始调查").attr("type",data.reelStatus);
                        alertTip("该问卷已暂停调查");
	    			}

	    		}
	    	})
		}
		
	}
	//点击文本编辑
    $(".reelText").on("click",function(){
        var showPageIndex = $(".tab-page-index.active").not(".end-tab").attr("data-index");
        if(showPageIndex == 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length>2){
            return alertTip("有未保存的题目")
        }else if(showPageIndex != 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length>0){
            return alertTip("有未保存的题目")
        }
        $.ajax({
            url:"/questionnaire/reel/checkUrl",
            method:"post",
            contentType:"application/json",
            data:JSON.stringify({
                "rid":$(".rId").val()
            }),
            success:function(res){
                console.log(res)
                if(res.code == 1){
                    alertTop("此问卷含有富文本编辑元素，不能进行文本编辑");
                    return
                }else{
                    toSearch(this,'/questionnaire/reel/reeltext','param','contentMain')
                }
            }
        })

    })
</script>
			   



<%--<script src="<%=path %>/template/jquery/jquery-1.11.0.min.js"></script>--%>
<%--<script src="<%=path %>/template/zui/js/zui.min.js"></script>--%>
<script src="<%=path %>/template/js/editorPlgins.js"></script>
<%--<script src="<%=path %>/template/js/subject.js"></script>--%>
	</body>
</html>