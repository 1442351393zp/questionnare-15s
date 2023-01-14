<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<meta name="Keywords" content="">
	<meta name="description" content="">
	<!-- jsp文件头和头部 -->
    <%--<%@ include file="../../system/index/top.jsp"%>--%>
	<!-- 页面样式 -->
    <!-- <link rel="stylesheet" type="text/css" href="static/license/css/license.css"/> -->
        <script type="text/javascript" src="/questionnaire/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>
    <style type="text/css">
    #index{
     position: relative;
     width: 100%;
     height: 100%;
    }
    #banner{
    position: absolute;
    top:200px;
    text-align: center;
    width: 100%;
    }
    #title{
    position: absolute;
    top:100px;
    color: white;
    font-size: 30px;
    font-family: '微软雅黑';
    width: 100%;
    text-align: center;
    }
    table{
     position: absolute;
     top:550px;
     text-align: center;
     width: 100%;
     font-family:'微软雅黑';
    }
    #activation:hover{
    cursor:pointer;

    }
    #activation{
    margin-top:-12px;
    width: 80px;
    }
    body,html{
    height: 100%;
    }
    input{
     padding-left: 8px;
     height: 35px;
     width: 35%;
    }
    </style>
</head>
<body>
  <img alt="图片" id="index" src="<%=basePath%>template/licence/images/licence_index.png">
  <div id="banner">
  <img alt="图片"  src="<%=basePath%>template/licence/images/img.png"  width="50%">
   </div>
<!-- /section:basics/navbar.layout -->
<div class="main-container"  id="loginbox" >
 <div id="title">
  <strong>您的授权已过期，请马上激活~</strong>
 </div>
 <table border="0">
    <tr>
      <td height="100px">
       <strong style="font-size: 18px;">license码 :&nbsp; </strong>
       <input id="license" name="license" placeholder="这里输入license码"></input> &nbsp;
       <img id="activation" src="<%=basePath%>template/licence/images/activation.png"  onclick="confirm()" />
      </td>
    </tr>
  </table>

</div>
	<!-- 页面底部js¨ -->
	<%--<%@ include file="../../system/index/foot.jsp"%>--%>
	<!-- 下拉框 -->
	<%--<script src="static/ace/js/chosen.jquery.js"></script>--%>
	<!-- 日期框 -->
	<%--<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>--%>
	<!--提示框-->
	<script type="text/javascript" src="/questionnaire/template/js/jquery.tips.js"></script>
		<script type="text/javascript">
		function confirm(){
		    var license=$('#license').val();
			if(license.trim()==""){
				$("#license").tips({
				    side:3,
			        msg:'请输入license码！',
			        bg:'#AE81FF',
			        time:2
			    });
		        $("#license").focus();
			 return false;
			 } 
		     $.ajax({
			 type: "post",
			 url: '/questionnaire/login/license',
			 data: {license:license},
			 cache: false,
			 success: function(data){
			     if(data.status == "1"){//1：激活成功
				     // alert("激活成功！");
					 $("#license").tips({
					     side:1,
					     msg:'激活成功！正在加载中...',
					     bg:'#1fd445',
					     time:3
					 });
					 setTimeout(load,2000);
				 }if(data.status == "0"){//0：激活失败 
					  //alert("激活失败！");
					  $("#license").tips({
						  side:1,
					      msg:'激活失败！',
					      bg:'#da4646',
					      time:3
					  });
				 }
			   }
		    });   
		  }
		
		function load(){
			location.reload();
		}
		</script>
</body>
</html>