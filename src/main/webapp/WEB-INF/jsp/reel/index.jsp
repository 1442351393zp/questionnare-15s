<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
    <title>系统问卷调查首页</title>
    <link rel="stylesheet" href="<%=path %>/template/css/reset.css">
    <link rel="stylesheet" href="<%=path %>/template/css/common.css">
    <link rel="stylesheet" href="<%=path %>/template/css/home.css">


    <link rel="shortcut icon" href="<%=path %>/template/image/logo/logo-title.ico">
    <link rel="stylesheet" href="<%=path %>/template/css/create-questionnaire.css">
    <script src="<%=path %>/template/js/page.js"></script>
    <%--<script type="text/javascript" src="<%=path %>/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>--%>
</head>
<body>
        <!-- 页面顶部¨ -->
<div class="main-choice">
        <div class="create-white-page">
            <div class="bg-image-show"></div>
            <h2>创建空白页面</h2>
            <span>从一份空白问卷开始创建</span>
            <a onclick="toSearchs(this,'/questionnaire/reel/main','contentMain')">开始</a>
        </div>
        <div class="choice-template">
            <div class="bg-image-show"></div>
            <h2>选择问卷模板</h2>
            <span>使用问卷提供的专业模板</span>
            <a onclick="toSearchs(this,'/questionnaire/template/template','contentMain')">开始</a>
        </div>
        <div class="text-edit-naire">
            <div class="bg-image-show"></div>
            <h2>文本编辑问卷</h2>
            <span>自由编辑文本自动生成问卷</span>
            <a onclick="toSearchs(this,'/questionnaire/reel/reeltext','contentMain')">开始</a>
        </div>
    </div>
    <!--模本列表-->
    <!-- <div class="template-link">
        <div class="recommend-template" id="recommend_template">
            <h2>为您推荐</h2>
            <div class="row clearfix">
                <ol class="column">
                    <li>
                        <a href="">
                            <i class="order">1</i>
                            <span class="title" title="社交网站满意度问卷模板">社交网站满意度问卷模板</span>
                            <span class="ext">34个问题/4页</span>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <i class="order">2</i>
                            <span class="title" title="购物网用户满意度调查">购物网用户满意度调查</span>
                            <span class="ext">21个问题/1页</span>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <i class="order">3</i>
                            <span class="title" title="数码家电类产品满意度模板">数码家电类产品满意度模板</span>
                            <span class="ext">32个问题/4页</span>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <i class="order">4</i>
                            <span class="title" title="网购消费者的行为习惯调查">网购消费者的行为习惯调查</span>
                            <span class="ext">28个问题/4页</span>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <i class="order">5</i>
                            <span class="title" title="餐饮类团购用户满意度问卷模板">餐饮类团购用户满意度问卷模板</span>
                            <span class="ext">31个问题/3页</span>
                        </a>
                    </li>
                </ol>
                <ol class="column">
                    <li>
                        <a href="">
                            <i class="order">6</i>
                            <span class="title" title="圣诞狂欢市场调查问卷">圣诞狂欢市场调查问卷</span>
                            <span class="ext">18个问题/1页</span>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <i class="order">7</i>
                            <span class="title" title="团购网站用户满意度问卷模板">团购网站用户满意度问卷模板</span>
                            <span class="ext">37个问题/4页</span>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <i class="order">8</i>
                            <span class="title" title="旅游电子商务-消费心理与消费行为调查问卷">旅游电子商务-消费心理与消费行为调查问卷</span>
                            <span class="ext">15个问题/1页</span>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <i class="order">9</i>
                            <span class="title" title="玩具网站用户满意度问卷模板">玩具网站用户满意度问卷模板</span>
                            <span class="ext">34个问题/4页</span>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <i class="order">10</i>
                            <span class="title" title="旅游网站用户小调查">旅游网站用户小调查</span>
                            <span class="ext">24个问题/3页</span>
                        </a>
                    </li>
                </ol>
            </div>
        </div>
    </div> -->
    <%--<div id="footer" class="footer-copyright">--%>
        <%--<p>Copyright © 1998-<span class="copyright_end">2019</span> Tencent. All Rights Reserved.</p>--%>
        <%--<p>--%>
            <%--<span>706所 版权所有</span>--%>
            <%--<a href="" class="link" target="_blank" title="关于我们">关于我们</a>--%>
            <%--<a href="" class="link" target="_blank" title="用户协议">用户协议</a>--%>
            <%--<a href="" class="link" target="_blank" title="问题反馈">问题反馈</a>--%>
            <%--<a href="" class="link" target="_blank" title=""></a>--%>
            <%--<a href="" class="link" target="_blank" title="服务协议">服务协议</a>--%>
            <%--<a href="" class="link" target="_blank" title="隐私政策">隐私政策</a>--%>
        <%--</p>--%>
    <%--</div>--%>



<script type="text/javascript">
/* function abc(){

	CKEDITOR.replace('editor1');

} */




/* var myEditor=null;
window.onload = function(){
	 ClassicEditor
	.create(document.querySelector("#editor"))
	.then(editor=> {
		myEditor=editor;
	})
	.catch(error=> {
		consoloe.error(error);
	})
	
}
 */


/* var E =window.wangEditor;
var editor = new E('#editor');
editor.create(); */
// function createQuestion(){
// 	location.href = "/questionnaire/reel/main";
// }

function questionTemplate(){
    stopAlert();
}


</script>
</body>
</html>