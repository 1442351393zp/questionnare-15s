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

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>垃圾篓</title>
    <link rel="shortcut icon" href="<%=path %>/template/image/logo/logo-title.ico">

    <script src="<%=path %>/template/js/page.js"></script>
    <!-- zui -->
    <link href="<%=path %>/template/zui/css/zui.min.css" rel="stylesheet">
    <!--重置样式-->
    <link rel="stylesheet" href="<%=path %>/template/css/reset.css">
    <link rel="stylesheet" href="<%=path %>/template/css/common.css">
    <link rel="stylesheet" href="<%=path %>/template/css/home.css">
    <link rel="stylesheet" href="<%=path %>/template/css/create-questionnaire.css">
    <link rel="stylesheet" href="<%=path %>/template/css/create-white-question.css">
    <!-- 下拉菜单 -->
    <script src="<%=path %>/template/zui/js/zui.lite.min.js"></script>
</head>

<body>
    <!-- 页面顶部¨ -->
    <%-- <%@ include file="head.jsp"%> --%>
    <input type="hidden" id="viewStatus" value="${viewStatus }">
    <input type="hidden" id="userId" value="${userId }">
    <div class="empty-page">
     <c:if test="${folderId == null }">
        <!--副标题头-->
        <div class="sub-header clearfix" data-current="none" style="display: none">
            <%-- <div class="sub-header-left">
                <a class="btn btn-primary icon icon-plus" title="列表视图" onclick="createQuestion('${folderId}')">创建问卷</a>
            </div> --%>
            <div class="sub-header-right">
                <!-- <a class="sub-header-btn" title="创建文件夹" data-toggle="modal" data-target="#PopDialog"></a> -->
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
        <div class="sub-header-2" data-current="true" >
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
                <!--问卷九宫格文件夹视图-->
                <c:forEach items="${folderDTO }" var="vj" varStatus="vl">
                    <div class="card-item">
                        <div class="icon-folder" onclick="toBasket('${vj.folderId}','${vj.folderName}')">
                            <span class="icon-folder-header"></span>
                            <span class="icon-folder-middle"></span>
                            <span class="icon-folder-main"></span>
                            <%-- <i class="more-btn baskBox" onclick="folderMoreBtn(event,'${vj.folderId}')"></i> --%>
                       </div>
                        <div class="dropdown folderbtn">
                            <ul class="dropdown-menu">
                                <li class='nopoint'>
                                    <a class="delFolder" onclick="deleFolderfuc('${vj.folderId}')">删除</a>
                                </li>
                                <li class='nopoint'>
                                    <a class="moveTrashBasket" onclick="moveFolderTrashBasket('${vj.folderId}')">移动到废纸篓</a>
                                </li>
                            </ul>
                            <i class="more-btn baskBox" data-toggle="dropdown"></i>
                        </div>
                        <div class="new-folder">${vj.folderName}</div>
                   </div>
              </c:forEach>

              <!--问卷九宫格视图-->
              <c:if test="${allSubjectDTO != null}">
                  <c:forEach items="${allSubjectDTO}" var="va" varStatus="vs">
                      <div class="card-item">
                          <div class="bg-card-image" onclick="showInfoDesc(event,'${va.rId}')">
                             <div class="dropdown">
                                <ul class="dropdown-menu">
								   <li class='nopoint'> 
								      <a class="statusView" onclick="statusfuc(event,'${va.rId}','${va.reelStatus}','${va.userId}')"></a>
								   </li>
								   <li class='nopoint'> 
								      <a onclick="recover(event,'${va.rId}')">恢复</a>
								   </li>
								   <li class="nopoint">
								      <a onclick="delThorough(event,'${va.rId}')">彻底删除</a>
								   </li>
								   <li class="dropdown-submenu">
								      <a >移动到</a>
								      <ul class="dropdown-menu pull-left">
						              </ul>
								   </li>
								 </ul>
	                                <i class="more-btn" data-toggle="dropdown" onclick="moreBtn(event,'${va.rId}','${va.reelStatus}','${va.userId}',this)"></i>
	                         </div>
                          </div>
                            <div class="card-desc">
                                <div class="card-desc-title">
                                     <c:if test="${va.reelStatus== '0' }">
                                    <i class="active"></i>
                                </c:if>
                                <c:if test="${va.reelStatus== '1' }">
                                    <i></i>
                                </c:if><span>${va.title}</span>
                                </div>
                                <em class="ellipsis">${va.rId}</em>
                            </div>
                        </div>
                  </c:forEach>
               </c:if>
                
                <c:if test="${allSubjectDTO == '[]'}">
                     <div class="grid-point">暂时无数据</div>
                </c:if>
            </div>
            <!--问卷表格视图-->
            <div class="table-wrap">
                <table class="table table-hover">
                    <thead>
                        <tr>
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
                        <c:forEach items="${folderDTO }" var="vy" varStatus="vk">
                            <tr>
                                <td class="clearfix">
                                    <div class="icon-folder" onclick="toBasket('${vy.folderId}','${vy.folderName}')">
                                        <span class="icon-folder-header"></span>
                                        <span class="icon-folder-middle"></span>
                                        <span class="icon-folder-main"></span>
                                    </div>
                                    <span><a onclick="toBasket('${vy.folderId}','${vy.folderName}')">${vy.folderName}</a></span>
                                </td>
                                <td colspan="4"></td>
                                <td>
                                <div class="dropdown">
                                    <ul class="dropdown-menu">
									  <li class='nopoint'> 
									     <a onclick="recoverFolder(event,'${vy.folderId}')">恢复</a>
									  </li>
									  <li class='nopoint'> 
									     <a class="moveTrashBasket" onclick="delThoroughFolder(event,'${folderId}')">彻底删除</a>
									  </li>
									 </ul>
                                     <i class="more-btn" data-toggle="dropdown"></i>
                                </div>
                                    <%-- <i class="more-btn" onclick="folderMoreBtn(event,'${vy.folderId}')"></i> --%>
                                </td>
                            </tr>
                        </c:forEach>


                        <c:if test="${allSubjectDTO != null}">
                            <c:forEach items="${allSubjectDTO}" var="varc" varStatus="vsc">
                                <tr onclick="showInfoDesc(event,'${varc.rId}')">
                                    <td>
                                       <c:if test="${varc.reelStatus== '0' }">
                                         <i class="active"></i>
                                       </c:if>
                                      <c:if test="${varc.reelStatus== '1' }">
                                        <i></i>
                                      </c:if>
                                        <c:forEach items="${varc.pageList}" var="var" varStatus="v">
                                            <span onclick="getReelEdit('${varc.rId}','${var.pageId}')">
                                                <c:if test="${v.first}">${varc.title}</c:if>
                                            </span>
                                        </c:forEach>
                                        <%-- <em>${varc.rId}</em> --%>
                                    </td>
                                    <td>${varc.rId}</td>
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
									     <a class="statusView" onclick="statusfuc(event,'${varc.rId}','${varc.reelStatus}','${varc.userId}')"></a>
									  </li>
									  <li class='nopoint'> 
									     <a onclick="recover(event,'${varc.rId}')">恢复</a>
									  </li>
									   <li class="nopoint">
									     <a onclick="delThorough(event,'${varc.rId}')">彻底删除</a>
									   </li>
									   <li class="dropdown-submenu">
									     <a >移动到</a>
									     <ul class="dropdown-menu pull-left">
							             </ul>
									   </li>
									 </ul>
	                                    <i class="more-btn" data-toggle="dropdown" onclick="moreBtn(event,'${varc.rId}','${varc.reelStatus}','${varc.userId}',this)"></i>
	                               </div>
                                        <%-- <i class="more-btn" onclick="moreBtn(event,'${varc.rId}')"></i> --%>
                                    </td>
                                </tr>
                            </c:forEach>
                            
                            <c:if test="${allSubjectDTO == '[]'}">
                             <c:if test="${folderDTO == '[]' }">
                               <tr>
                                  <td colspan="7" style="text-align: center;">暂时无数据 </td>
                               </tr>
                           </c:if>
                            </c:if>
                        </c:if>
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
                    <span class="span-recycle">回收量:0</span>
                </div>
                <ul>
                    <li class="base-info">
                        <span class="base-info-lt">基本信息</span>
                        <span class="base-info-rt page-subject">共&nbsp;1&nbsp;页&nbsp;,&nbsp;1&nbsp;题</span>
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
            </div>
        </div>
        </c:if>
        
        
        
        
         <!-- 每个文件夹里面的数据 -->
       <c:if test="${folderId != null }">
      <!--副标题头-->
        <div class="sub-header clearfix" data-current="false" style="display: none">
	           <div class="sub-header-left">
                   <a class="btn-back" onclick="changeToTrash(event)"></a>
	               <a class="btn btn-primary icon icon-plus" title="列表视图" onclick="createQuestion('${folderId}')">创建问卷</a>
	           </div> 
            <div class="sub-header-right">
                <a class="sub-header-btn" title="创建文件夹" data-toggle="modal" data-target="#PopDialog" style="display:none"></a>
                <a class="sub-header-btn" style="display:none;" title="废纸篓" onclick="changeToTrash(event)"></a>
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
        <div class="sub-header-2" data-current="true">
            <a class="btn-back" onclick="backToOriginTrashBasket(event)"></a>
            <a class="sub-title">${folderName }</a>
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
                        <div class="bg-card-image" onclick="showInfoDesc(event,'${va.rId}')">
                         <div class="dropdown">
                           <ul class="dropdown-menu">
						      <li class='nopoint'> 
						         <a onclick="recoverTrashBasket(event,'${folderId }','${va.rId}')">恢复</a>
						      </li>
						      <li class='nopoint'> 
						         <a onclick="shiftOutFolderToTrashBasket(event,'${folderId }','${va.rId}')">移出文件夹</a>
						      </li>
						      <li class="nopoint">
						         <a onclick="delThoroughTrashBasket(event,'${folderId }','${va.rId}')">彻底删除</a>
						      </li>
						   </ul>
                              <i class="more-btn" data-toggle="dropdown" onclick="moreFolderBtn('${va.rId}','${va.reelStatus}','${va.userId}')"></i>
                         </div>
                        </div>
                        <div class="card-desc">
                          <div class="card-desc-title">
                            <i></i><span>${va.title}</span>
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
                            <tr onclick="showInfoDesc(event,'${varc.rId}')">
                                <td>
                                    <c:if test="${varc.reelStatus== '0' }">
                                       <i class="active"></i>
                                    </c:if>
                                    <c:if test="${varc.reelStatus== '1' }">
                                       <i></i>
                                    </c:if>
                                       <span onclick="getReelEdit('${varc.rId}','${varc.pageList[0].pageId}')">${varc.title}</span>
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
									     <a onclick="recoverTrashBasket(event,'${folderId }','${varc.rId}')">恢复</a>
									  </li>
									  <li class='nopoint'> 
									     <a onclick="shiftOutFolderToTrashBasket(event,'${folderId }','${varc.rId}')">移出文件夹</a>
									  </li>
									   <li class="nopoint">
									     <a onclick="delThoroughTrashBasket(event,'${folderId }','${varc.rId}')">彻底删除</a>
									   </li>
									 </ul>
                                     <i class="more-btn" data-toggle="dropdown" onclick="moreFolderBtn('${varc.rId}','${varc.reelStatus}','${varc.userId}')"></i>
                                </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>

                    </tbody>
                </table>
            </div>

            <!--问卷信息详情-->
            <div class="draw-info">
                <div class="draw-title">
                    <h2 class="base-title">
                        <span class="input-title"></span></h2>
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
                    <span>编辑</span>
                    <span>统计</span>
                </div>
            </div>
        </div>
      </c:if>

    </div>
    <%--弹出对话框--%>
    <div class="modal fade" id="PopDialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="dialog-container">
                    <div class="dialog-header">
                        <span class="dialog-title">创建文件夹</span>
                        <span class="btn-close-dialog" data-dismiss="modal"></span>
                    </div>
                    <div class="dialog-main">
                        <form action="">
                            <input name="folderName" id="folderName" type="text" class="form-control"
                                placeholder="新建文件夹">
                            <hr>
                            <fielset>
                                <input type="cancel" value="取消">
                                <input type="submit" class="inputSubmit" value="确定">
                            </fielset>
                        </form>
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
    
    $(".more-btn").click(function(){
        if($(this).parents(".dropdown").css("width") == "110px" || $(this).parents(".dropdown").hasClass("folderbtn")){
            if($(this).offset().left+400 > $(document).width()){
                $(".dropdown-menu").css({
                    "left":-105
                })
                $(".dropdown-menu.pull-left").css({
                    "left":-195
                })
            }else{
                if($(this).parents(".dropdown").hasClass("folderbtn")){
                    $(".dropdown-menu").css({
                        "left":-30
                    })
                }else{
                    $(".dropdown-menu").css({
                        "left":85
                    })
                    $(".dropdown-menu.pull-left").css({
                        "left":200
                    })
                }

            }
        }else{
            $(".dropdown-menu").css({
                "left":-195
            })
        }
    })
    
    //下拉菜单 -垃圾篓 
    function moreBtn(ev,rId,reelStatus,userId,self){
    	 if($(self).parents(".dropdown").css("width") == "110px"){
             if($(self).offset().left+400 > $(document).width()){
                 $(".dropdown-menu").css({
                     "left":-105
                 })
                 $(".dropdown-menu.pull-left").css({
                     "left":-195
                 })
             }else{
                 $(".dropdown-menu").css({
                     "left":85
                 })
                 $(".dropdown-menu.pull-left").css({
                     "left":200
                 })
             }
         }else{
             $(".dropdown-menu").css({
                 "left":-195
             })
         }
    	$(".pull-left").empty();
    	var status = "";
       	var reelType = ""
       	if(reelStatus  == "0"){
            status = "暂停调查"
            reelType = "1"
        }else{
            status = "开始调查"
            reelType = "0"
        }
    	$(".statusView").html(status);
    	var delFlag ="1";//1：废纸篓
    	$.ajax({
            type:'post',
            url:"/questionnaire/reel/queryAllFolder",//查询垃圾篓中的文件夹
            data:{"delFlag":delFlag },
            async:false,
            success:function(result){
            	if(result != ""){
	                for(var i=0;i<result.length;i++){
	             	   var folderName = result[i].folderName;
	             	   var folderId = result[i].folderId;
	             	   var a ="<li><a onclick=\"folderfuc(event,'"+folderId+"','"+rId+"')\">"+folderName+"</a></li>";
	             	   $(".pull-left").append(a);
	                }
                }else{
                	$(".dropdown-menu .dropdown-submenu").remove();
                	//$(".pull-left").remove();
                }
            }
        })
   	}
    $(".more-btn").click(function(){
        if($(this).parents(".dropdown").css("width") == "110px" || $(this).parents(".dropdown").hasClass("folderbtn")){
            if($(this).offset().left+400 > $(document).width()){
                $(".dropdown-menu").css({
                    "left":-105
                })
                $(".dropdown-menu.pull-left").css({
                    "left":-195
                })
            }else{
                if($(this).parents(".dropdown").hasClass("folderbtn")){
                    $(".dropdown-menu").css({
                        "left":-30
                    })
                }else{
                    $(".dropdown-menu").css({
                        "left":85
                    })
                    $(".dropdown-menu.pull-left").css({
                        "left":200
                    })
                }

            }
        }else{
            $(".dropdown-menu").css({
                "left":-195
            })
        }
    })
    //把垃圾篓中的问卷移动到文件夹下
    function folderfuc(ev,folderId,rId){
        ev = ev || window.event;
        ev.stopPropagation()
    	 if (confirm("确定要移动吗？")) {
		   	 $.ajax({                                         
		        type:'post',                                 
		        url:'/questionnaire/reel/moveToFolder',     
		        data:{                                       
		           'folderId':folderId,                
		           'rId':rId                         
		          },                                        
		         success:function(result){  
		       	  if(result == "1"){
		                 alertTop("移动成功");
		                  setTimeout(function(){
		                      toSearchs(this,'/questionnaire/reel/trashBasket','contentMain')
		                  },700)
		             }else{
		            	 alertTop("移动失败"); 
		             }
		         }                                          
	         }) 
    	 }
    }
    
    //垃圾篓中切换调查状态 
    function statusfuc(ev,rId,reelStatus,userId){
        ev = ev || window.event;
        ev.stopPropagation()
    	var status = "";
       	var reelType = ""
       	if(reelStatus  == "0"){
            status = "暂停调查"
            reelType = "1"
        }else{
            status = "开始调查"
            reelType = "0"
        }
       	$.ajax({
             type:'post',
             url:"/questionnaire/question/startresearch",
             data:{
                 "rId":rId,
                 "reelStatus":reelType,
                 "userId":userId
             },
             success:function(data){
                 var data = $.parseJSON(data);
                 if(data.code == "0"){
                	 alertTop("修改成功");
                	 setTimeout(function(){
                         toSearchs(this,'/questionnaire/reel/trashBasket','contentMain')
                     },700)
                 }else{
                	 alertTop("此问卷未创建题目，不可调查操作");
                 }
             }
        })
    }
    //恢复到之前的位置 
    function recover(ev,rId){
        ev = ev || window.event;
        ev.stopPropagation()
    	if (confirm("确定要恢复吗？")) {
            $.ajax({
                type: 'get',
                url: "/questionnaire/reel/recoverBasket",
                data: {
                    "rId": rId
                },
                success: function (result) {
                    if (result == "1") {
                        alertTop("恢复成功");
                        setTimeout(function () {
                        	toSearchs(this,'/questionnaire/reel/trashBasket','contentMain')
                        }, 700)
                    }

                }
            })
        }
    }
    //彻底删除 
    function delThorough(ev,rId){
        ev = ev || window.event;
        ev.stopPropagation()
    	if (confirm("确定要彻底删除吗？")) {
            $.ajax({
                type: 'get',
                url: "/questionnaire/reel/deleteReelById",
                data: {
                    "rId": rId
                },
                success: function (result) {
                    if (result == "1") {
                    	alertTop("删除成功");
                        setTimeout(function () {
                        	toSearchs(this,'/questionnaire/reel/trashBasket','contentMain')
                        }, 700)
                    }

                }
            })
     	}
    }
    
    //恢复文件夹到之前的位置 
    function recoverFolder(ev,folderId){
        ev = ev || window.event;
        ev.stopPropagation()
    	if (confirm("确定要恢复吗？")) {
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/recoverBasket",
                data: {
                    "folderId": folderId
                },
                success: function (result) {
                    if (result == "1") {
                        alertTop("恢复成功");
                        setTimeout(function () {
                        	toSearchs(this,'/questionnaire/reel/trashBasket','contentMain')
                        }, 700)
                    }
                }
            })

        }
    }
    //彻底删除文件夹及以下的问卷 
    function delThoroughFolder(ev,folderId){
        ev = ev || window.event;
        ev.stopPropagation()
    	if (confirm("确定要彻底删除吗？")) {
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/deleteFolder",
                data: {
                    "folderId": folderId
                },
                success: function (result) {
                    if (result == "1") {
                        alertTop("移除成功");
                        setTimeout(function () {
                        	toSearchs(this,'/questionnaire/reel/trashBasket','contentMain')
                        }, 700)
                    }
                }
            })

        }
    }
    
  //垃圾篓文件下恢复到之前的位置 
    function recoverTrashBasket(ev,folderId,rId){
        ev = ev || window.event;
        ev.stopPropagation()
	    var delFlag = "0";
    	if (confirm("确定要恢复吗？")) {
            $.ajax({
                type: 'get',
                url: "/questionnaire/reel/shiftOutTrashBasketFolder",
                data: {
                    "rId": rId,
                    "delFlag":delFlag
                },
                success: function (result) {
                    if (result == "1") {
                        alertTop("恢复成功");
                        setTimeout(function () {
                        	toSearchs(this,'/questionnaire/reel/trashBasket?folderId='+folderId,'contentMain')
                        }, 700)
                    }

                }
            })
        }
    }
  
  function shiftOutFolderToTrashBasket(ev,folderId,rId){
  ev = ev || window.event;
  ev.stopPropagation()
	var delFlag = "1";
  	if (confirm("确定要移出吗？")) {
          $.ajax({
              type: 'get',
              url: "/questionnaire/reel/shiftOutTrashBasketFolder",
              data: {
                  "rId": rId,
                  "delFlag":delFlag
              },
              success: function (result) {
                  if (result == "1") {
                      alertTop("移出成功");
                      setTimeout(function () {
                      	toSearchs(this,'/questionnaire/reel/trashBasket?folderId='+folderId,'contentMain')
                      }, 700)
                  }

              }
          })
      }
  }
  
    //彻底删除垃圾篓中文件夹下的问卷
    function delThoroughTrashBasket(ev,folderId,rId){
        ev = ev || window.event;
        ev.stopPropagation()
    	if (confirm("确定要彻底删除吗？")) {
            $.ajax({
                type: 'get',
                url: "/questionnaire/reel/deleteReelById",
                data: {
                    "rId": rId
                },
                success: function (result) {
                    if (result == "1") {
                    	alertTop("删除成功");
                        setTimeout(function () {
                        	toSearchs(this,'/questionnaire/reel/trashBasket?folderId='+folderId,'contentMain')
                        }, 700)
                    }

                }
            })
     	}
    }
        //上下文菜单
        function moreBtn1(ev, rId) {
            ev.stopPropagation();
            $.zui.ContextMenu.show([{
                label: '恢复',
                onClick: function (ev) {
                    if (confirm("确定要恢复吗？")) {
                        $.ajax({
                            type: 'get',
                            url: "/questionnaire/reel/recoverBasket",
                            data: {
                                "rId": rId
                            },
                            success: function (result) {
                                if (result == "1") {
                                    alertTop("恢复成功");
                                    setTimeout(function () {
                                    	toSearchs(this,'/questionnaire/reel/trashBasket','contentMain')
                                    }, 700)
                                }

                            }
                        })
                    }
                }
            }, {
                label: '彻底删除',
                onClick: function (ev) {
                	if (confirm("确定要彻底删除吗？")) {
	                   $.ajax({
	                       type: 'get',
	                       url: "/questionnaire/reel/deleteReelById",
	                       data: {
	                           "rId": rId
	                       },
	                       success: function (result) {
	                           if (result == "1") {
	                           	alertTop("删除成功");
	                               setTimeout(function () {
	                               	toSearchs(this,'/questionnaire/reel/trashBasket','contentMain')
	                               }, 700)
	                           }
	
	                       }
	                   })
                	}

                }
            }
            ], {
                event: ev
            }, function () {
                var elA = $(".contextmenu-menu>li>a")
                for (var i = 0; i < elA.length; i++) {
                    elA[i].removeAttribute("href")
                }
                console.log('上下文菜单已显示。');
            });
        }
        
        
      //文件夹下的问卷下拉菜单
        function moreFolderBtn(rId,reelStatus,userId){
       	 $(".pull-left").empty();
        	var status = "";
           	var reelType = ""
           	if(reelStatus  == "0"){
                status = "暂停调查"
                reelType = "1"
            }else{
                status = "开始调查"
                reelType = "0"
            }
        	$(".statusView").html(status);
        	/* $.ajax({
                type:'post',
                url:"/questionnaire/reel/queryAllFolder",
                data:{ },
                async:false,
                success:function(result){
                    for(var i=0;i<result.length;i++){
                 	   var folderName = result[i].folderName;
                 	   var folderId = result[i].folderId;
                 	   var a ="<li><a onclick=\"folderfuc('"+folderId+"','"+rId+"')\">"+folderName+"</a></li>";
                 	   $(".pull-left").append(a);
                    }
                }
            }) */
        }

        //文件夹上下文菜单 	
        function folderMoreBtn1(ev, folderId) {
            ev.stopPropagation();
            $.zui.ContextMenu.show([{
                label: '恢复',
                onClick: function (ev) {
                    if (confirm("确定要恢复吗？")) {
                        $.ajax({
                            type: 'post',
                            url: "/questionnaire/reel/recoverBasket",
                            data: {
                                "folderId": folderId
                            },
                            success: function (result) {
                                if (result == "1") {
/*                                     new $.zui.Messager('恢复成功！', {
                                        type: 'success'
                                    }).show(); */
                                    alertTop("恢复成功");
                                    setTimeout(function () {
                                    	toSearchs(this,'/questionnaire/reel/trashBasket','contentMain')
                                    }, 500)
                                }
                            }
                        })

                    }
                }
            }, {
                label: '彻底删除',
                onClick: function (ev) {
                    if (confirm("确定要移到废纸篓吗？")) {
                        $.ajax({
                            type: 'post',
                            url: "/questionnaire/reel/deleteFolder",
                            data: {
                                "folderId": folderId
                            },
                            success: function (result) {
                                if (result == "1") {
                                    /* new $.zui.Messager('移除成功！', {
                                        type: 'success'
                                    }).show(); */
                                    alertTop("移除成功");
                                    setTimeout(function () {
                                    	toSearchs(this,'/questionnaire/reel/trashBasket','contentMain')
                                    }, 500)
                                }
                            }
                        })

                    }
                }
            }
            ], {
                event: ev
            }, function () {
                var elA = $(".contextmenu-menu>li>a")
                console.log(elA)
                for (var i = 0; i < elA.length; i++) {
                    elA[i].removeAttribute("href")
                }
                console.log('上下文菜单已显示。');
            });
        }

        function getReelEdit(obj, obj1) {
            var url = "/questionnaire/reel/main1?rId=" + obj + "&pageId=" + obj1;
            toSearchs(this,url,'contentMain')
        }

        /**
         * 显示问卷信息，抽屉抽出
         * @param ev
         */
        function showInfoDesc(ev, obj) {
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
        }

        function edit(obj) {
            $.ajax({
                type: 'get',
                url: "/questionnaire/reel/queryByRid",
                data: {
                    "rId": obj
                },
                success: function (result) {
                    //var pageJson = JSON.stringify(result);
                    var title = result.title;
                    $(".input-title").html(title);
                    
                    var recycleNum = result.recycleNum;
          	    	$(".span-recycle").html("回收量："+recycleNum);
          	    	
          	    	var pageNum = result.pageNum;
          	    	var subjectNum = result.subjectNum;
          	    	$(".page-subject").html("共"+pageNum+"页"+subjectNum+"题");

                    var rId = result.rId;
                    $(".input-rId").val(rId);

                    var reelStatus = result.reelStatus;
                    var statusName = "";
                    if (reelStatus == 0) {
                        statusName = "开始调查";
                    } else if(reelStatus == 1){
                        statusName = "暂停调查";
                    }else{
                    	statusName = "";
                    }
                    $(".input-reelStatus").val(statusName);

                    var updateTime = result.updateTime;
                    $(".input-updateTime").val(updateTime);

                }
            })
        }

        function createQuestion(folderId) {
            location.href = "/questionnaire/reel/main?folderId=" + folderId;
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
            var elTable = elEmptyPage.getElementsByClassName("table")[0]
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
        	 var userId =$("#userId").val();
             $.ajax({
                 url:"/questionnaire/reel/updateViewStatus",
                 method:"post",
                 data:{
                     "status":0,
                     "userId":userId
                 },
                 success:function(res){
                     console.log(res)
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
            var elTable = elEmptyPage.getElementsByClassName("table")[0]
            elTable.style.display = ""
            // 抽屉关闭
            var elBaseClose = elEmptyPage.getElementsByClassName("base-close")[0]
            elBaseClose.click()
            
        }
        if($("#viewStatus").val() == "0"){
            $('a[data-type="grid"]').click()
        }else{
            $('a[data-type="table"]').click()
        }

        /**
         * 跳转到回收站页面
         * @param ev
         */
        function changeToTrash(ev) {
            toSearchs(this,"/questionnaire/reel/trashBasket","contentMain")
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
         * 垃圾篓返回我的问卷列表页  
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
        
        /**
         * 垃圾篓文件夹下返回垃圾篓列表 
         * @param ev
         */
        function backToOriginTrashBasket(ev) {
            toSearchs(this, "/questionnaire/reel/trashBasket", "contentMain")
            ev = ev || event
            var target = ev.target
            var elEmptyPage = document.getElementsByClassName("empty-page")[0]
            var subHeader = elEmptyPage.getElementsByClassName("sub-header")[0]
            subHeader.style.display = "none"
            subHeader.dataset.current = true.toString()
        }

        $(".inputSubmit").click(function () {
            var folderName = $("#folderName").val();
            $.ajax({
                type: 'get',
                url: "/questionnaire/reel/folderSave",
                data: {
                    "folderName": folderName
                },
                success: function (result) {
                    //location.reload();
                }
            })
        })

        function toBasket(folderId,folderName) {
           // location.href = "/questionnaire/reel/list?folderId=" + folderId;
            toSearchs(this,"/questionnaire/reel/trashBasket?folderId="+folderId+"&folderName="+folderName,'contentMain');
            /* $.ajax({
                     type:'get',
                     url:"/questionnaire/reel/list",
                     data:{
                           "folderId":folderId
                     },
                     success:function(result){
                         location.reload(); 
               }
            }) */
        }
    </script>
</body>

</html>