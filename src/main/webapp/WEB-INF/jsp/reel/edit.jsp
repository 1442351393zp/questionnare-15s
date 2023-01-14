<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=path %>/template/css/my-questionnaire.css">
    <title>编辑</title>
</head>
<body>
<!-- 左侧菜单 -->
<%@ include file="left.jsp" %>
<input type="hidden" id="rId" name="rId" value="${rId}"/>
<input type="hidden" id="pageIdArr" name="pageIdArr" value="${pageIdArr}">
<input type="hidden" id="pageId" name="pageId" value="${pageId}">
<input type="hidden" id="folderId" name="folderId" value="${folderId}">

<!--问卷面板-->
<div class="questionnaire-panel" >
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
        <div class="tab-btn-add" onclick="addQuestionnairePage(event,'${rId}')"></div>
    </div>
    <!--问卷面板-->
    <div class="panel">
        <!--问卷面板页-->
        <div class="panel-page" data-pageindex="0">
            <!--标题-->
            <div class="wrap">
                <h1><input type="text" id="title" name="title"/></h1>
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
<%@ include file="collect.jsp" %>
<!--对话框上传图片HTML-->
<div class="modal fade" id="dialogUpload">
    <form action="/questionnaire/reel/uploadImg" method="post" id="saveSubmit" enctype="multipart/form-data">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="upload-container">
                    <h2>图片属性</h2>
                    <div class="file-input">
                        <div class="get-upload-path">
                            <div class="path">
                                <span>支持小于1M的PNG，JPG，GIF图片</span>
                                <input type="file" id="icons" name="icons" value="选择文件" onchange="fileImgType(this)"/>
                            </div>
                            <div class="btn-upload">
                                <button type="submit" id="upload" style="background-color: #fff">上传到服务器</button>
                            </div>
                        </div>
                    </div>
                    <!--图片设置区域-->
                    <div class="set-image-area">
                        <!--设置参数-->
                        <div class="set-args"></div>
                        <!--预览-->
                        <div class="preview-area"></div>
                    </div>
                    <div class="hidden-dialog">
                        <button type="button" class="btn-determine button-active" onclick="saveDialog('<%=basePath%>')">
                            确定
                        </button>
                        <div class="btn-cancel" onclick="hiddenDialog()">取消</div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<%--对话框超链接--%>
<div class="modal fade" id="dialogUrl">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="url-container">
                <h1>超链接</h1>
                <div class="url-ipt">
                    <div class="select-url">
                        <p>协议</p>
                        <%--<select id="agreement">--%>
                        <%--<option value="0">http://</option>--%>
                        <%--<option value="1">https://</option>--%>
                        <%--</select>--%>
                        <div class="selectType agreement">
                            <div class="show-title">
                                <span value="0">http://</span>
                                <i></i>
                            </div>
                            <ul>
                                <li value="0">http://</li>
                                <li value="1">https://</li>
                            </ul>
                        </div>
                    </div>
                    <div class="url-address">
                        <p>URL</p>
                        <input type="text" id="hyperlink">
                    </div>
                </div>
                <div class="hidden-dialog">
                    <button type="button" class="url-confirm">确定</button>
                    <div class="url-cancel">取消</div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--对话框视频--%>
<div class="modal fade" id="dialogVideo">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="video-container">
                <h1>添加视频</h1>
                <div class="video-ipt">
                    <input type="file" id="videoFile" name="videoFile" value="选择文件" onchange="fileVideoType(this)">
                </div>
                <!--预览视频-->
                <!-- <div class="preview-area-video"></div> -->
                <div style="color: red;margin-top: 30px;font-size: 13px;">提示：火狐、红莲花浏览器暂只支持mp4，谷歌支持mp4、mov、mkv格式</div>
                <div class="hidden-dialog">
                    <div class="video-cancel">取消</div>
                    <button type="button" class="video-confirm">确定</button>
                </div>
            </div>
        </div>
    </div>
</div>

<%--多选、单选题目内容--%>
<script type="text/html" id="subject-edit">
    <div class="reel-edit">
        <form action="">
            <input type="hidden" id="subjectId" name="subjectId" value="${subjectId}"/>
            <input type="hidden" id="pageId" name="pageId" value="${pageId}"/>
            <!--问卷面板页-->
            <div class="panel-pageIndex" data-pageindex="0">
                <div class="wrap-edit-content">
                    <div class="question">
                        <!-- 题目-->
                        <div class="fieldset">
                            <div class="label"><span style="color:red">*</span>题目</div>
                            <div class="editable" id="topic" contenteditable="true" data-type="content" onclick="editContent(event)" onblur="cancelEdit(event)" onmouseup="getSelectionText()"></div>
                        </div>
                        <!--备注-->
                        <div class="fieldset">
                            <div class="label">备注</div>
                            <div class="editable remark" id="remark" contenteditable="true" data-type="content" onclick="editContent(event)" onblur="cancelEdit(event)" onmouseup="getSelectionText()"></div>
                        </div>
                        <!--题目类型切换-->
                        <div class="fieldset">
                            <label for="question-type"></label>
                            <div class="selectType">
                                <div class="show-title">
                                    <span>单选题</span>
                                    <i></i>
                                </div>
                                <ul>
                                    <li value="1">单选题</li>
                                    <li value="2">多选题</li>
                                    <li value="4">单行文本框</li>
                                </ul>
                            </div>
                            <div class="required-item checkbox-primary">
                                <input type="checkbox" checked="checked" id="must"/>
                                <label>必填</label>
                            </div>
                        </div>

                        <!--  选项一 -->
                        <div class="all-option">
                            <div class="fieldset">
                                <div class="question-option-label add-options"></div>
                                <div class="editable option optionIpt"
                                     contenteditable="true"
                                     data-placeholder="选项"
                                     data-type="content"
                                     data-option-type="NORMAL"
                                     onclick="editContent(event)" id="options"
                                     onblur="cancelEdit(event)"
                                     onmouseup="getSelectionText()">
                                </div>
                                <span class="btn-delete-option"></span>
                            </div>
                        </div>

                        <!--新建选项-->
                        <div class="create-option">
                            <div class="fieldset">
                                <div class="label"></div>
                                <div class="btn-add-option">新建选项</div>
                                <div class="btn-add__other-option">其他选项</div>
                            </div>
                        </div>

                        <%--单行文本框--%>
                        <div class="box-single__line" style="margin-bottom:10px;">
                            <div class="fieldset">
                                <div class="label"></div>
                                <%--contenteditable="true" 变价页面的单行文本不可编辑填写--%>
                                <div class="editable optionIpt single-line-text"
                                     style="cursor: default;"
                                     id="single-line-text"
                                     data-placeholder="单行文本"
                                     data-type="content"
                                     data-option-type="SINGLE"
                                     onclick="editContent(event)"
                                     onblur="cancelEdit(event)"
                                     onmouseup="getSelectionText()"></div>
                            </div>
                        </div>

                        <!--添加选项-->
                        <!--<div class="fieldset">
                            <div></div>
                            <div class="add-other-option">添加[其他]项</div>
                            <div class="batch-update">批量修改</div>
                        </div>-->
                        <!--展开高级-->
                        <!--<div class="fieldset">
                            <div class="open-higher-set">
                                <span class="">展开高级设置</span>
                            </div>
                        </div>
                         <div class="fieldset">
                            <div class="senior-option">
                                <input type="checkbox" name="" value=""/>
                                <label for="">选项随即顺序</label>
                            </div>
                            <div class="senior-option">
                                <input type="checkbox" name="" value=""/>
                                <label for="">显示投票结果</label>
                            </div>
                            <div class="senior-option">
                                <input type="checkbox" name="" value=""/>
                                <label for="">设置为测评题目</label>
                            </div>
                            <div class="senior-option">
                                <input type="checkbox" name="" value=""/>
                                <label for="">选项引用</label>
                            </div>
                        </div>-->
                        <!--确定取消-->
                        <div class="fieldset-submit">
                            <div></div>
                            <button type="button" class="cancel-btn">取消</button>
                            <button type="button" id="subjectSave" class="button-active">确定</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</script>


<script src="<%=path %>/template/zui/js/zui.min.js"></script>
<script src="<%=path %>/template/js/editorPlgins.js"></script>
<script src="<%=path %>/template/zui/lib/sortable/zui.sortable.js"></script>
<script src="<%=path %>/template/js/subject.js"></script>
<script type="text/javascript">
    //页面进入加载页面和 数据
    $(function () {

        //获取你要加动画的父元素
        var el = document.getElementsByClassName("bodyData")[0]
        el.style.position = "relative"
        //创建动画元素
        var mask = createLoadingEl()
        //把动画元素添加到父元素
        el.appendChild(mask)

        var rId = $("#rId").val();
        var pageIdArr = $("#pageIdArr").val();
        var pageNum = 0;
        if (pageIdArr != "") {
            var pageIdArr = pageIdArr.split(",");
            for (var k = 0; k < pageIdArr.length; k++) {
                if (k == 0) {
                    $("#pageId").val(pageIdArr[k]);
                }
                pageNum++;
            }
        } else {
            pageNum = 1;
        }

        var reelendlanguage = $("#REELENDLANGUAGE").val();

        var numArr = ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五"]
        //创建问卷页面
        var elQuestionPanel = document.getElementsByClassName("questionnaire-panel")[0]
        //问卷面板
        var elPanel = elQuestionPanel.getElementsByClassName("panel")[0]
        elPanel.innerHTML = ""
        // tab容器
        var elTabPage = elQuestionPanel.getElementsByClassName("tab-page")[0]
        elTabPage.innerHTML = ""
        // 添加上一页
        var btnForward = document.createElement("div")
        btnForward.className = "tab-btn-forward"
        btnForward.setAttribute("onclick", "toForwardPage(event)");
        elTabPage.appendChild(btnForward)
        // 需要创建的页数
        // var pageNum = 10
        if (pageNum > numArr.length) {
            alert("不能超过十五页")
            return
        } else {
            for (var i = 0; i < pageNum; i++) {
                // 添加具体某一页
                var elPanelPage = document.createElement("div")
                elPanelPage.className = "panel-page"
                elPanelPage.dataset.pageindex = i.toString()
                elPanel.appendChild(elPanelPage)
                if (i == 0) {//第一页时候加载标题和开始语
                    var tilteDiv = "<div class=\"wrap-edit-content\"><p class=\"editable editTitle\" contenteditable=\"true\" id=\"title\" data-type=\"title\" data-placeholder=\"问卷标题\" onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\">问卷标题</p></div>";
                    //var tilteDiv="<div class=\"wrap\"> <h1><input type=\"text\" id=\"title\" name=\"title\" /></h1></div>";
                    $(".panel-page").append(tilteDiv);
                    var remarkDiv = "<div class=\"wrap-edit-content\"> "
                        + " <p class=\"editable editRemark\" contenteditable=\"true\" data-type=\"hello\" onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" "
                        + " onmouseup=\"getSelectionText()\" id=\"startLanguage\" "
                        + " data-placeholder=\"为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！\"> "
                        + " 为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！</p></div>";


                    //var remarkDiv="<div class=\"wrap\"><p>123为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们， 我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！</p></div>";
                    $(".panel-page").append(remarkDiv);
                    //var subjectDiv="<div class=\"wrap-question-list\"></div>";
                    //$(".panel-page").append(subjectDiv);
                    $(".components").attr("subjectIndex", i);//进来刷新默认第一页做的坐标给新增时用
                } else {
                    elPanelPage.style.display = "none"
                }

                var pageId = pageIdArr[i];
                // tab页索引
                var elTabPageIndex = document.createElement("div")
                elTabPageIndex.className = "tab-page-index"
                elTabPageIndex.dataset.index = i.toString()
                elTabPageIndex.setAttribute("onclick", "changeQuestionnairePage(event,'" + rId + "','" + pageId + "','" + i + "')")
                if (i === 0) {
                    elTabPageIndex.classList.add("active")
                }
                for (var j = 0; j < numArr.length; j++) {
                    if (i === j) {
                        // 添加页码
                        var elSpan = document.createElement("span")
                        elSpan.innerText = "第" + numArr[i] + "页"
                        elSpan.textContent = "第" + numArr[i] + "页"
                        elTabPageIndex.appendChild(elSpan)
                        // 添加删除按钮
                        var elI = document.createElement("i")
                        elI.setAttribute("onclick", "deletePage(event,'" + pageId + "')")
                        elTabPageIndex.appendChild(elI)
                    }
                }

                elTabPage.appendChild(elTabPageIndex)
            }
            // 添加结束页面板
            var endPanelPage = document.createElement("div")
            endPanelPage.className = "panel-page end-page"
            endPanelPage.dataset.pageindex = (pageNum - 1 + 1).toString()
            endPanelPage.style.display = "none"
            elPanel.appendChild(endPanelPage)
            var endDiv = "<div class=\"wrap-edit-content\"> "
                + " <div class=\"editable endLanguage\" contenteditable=\"true\" data-type=\"endLanguage\" onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" "
                + " onmouseup=\"getSelectionText()\" id=\"endLanguage\" "
                + " data-placeholder=\"\"> "
                + "<img src=../template/image/end.png>"
                + "<span>问卷到此结束，感谢您的参与</span>"
                + " </div></div>";
            $(".panel-page").eq(pageNum - 1 + 1).append(endDiv);

            // 添加结束页tab项
            var endTabPageIndex = document.createElement("div")
            endTabPageIndex.className = "tab-page-index end-tab"
            endTabPageIndex.dataset.index = (pageNum - 1 + 1).toString()
            endTabPageIndex.setAttribute("onclick", "changeQuestionnairePage(event,'" + rId + "','" + $("#pageId").val() + "','" + parseInt(pageNum - 1 + 1) + "')")
            var elEndSpan = document.createElement("span")
            elEndSpan.innerText = "结束页"
            elEndSpan.textContent = "结束页"
            endTabPageIndex.appendChild(elEndSpan)
            elTabPage.appendChild(endTabPageIndex)
        }

        // 添加下一页
        var btnNext = document.createElement("div");
        btnNext.className = "tab-btn-next";
        btnNext.setAttribute("onclick", "toNextPage(event)");
        elTabPage.appendChild(btnNext);
        // 添加新建
        var btnAdd = document.createElement("div");
        btnAdd.className = "tab-btn-add";
        btnAdd.setAttribute("onclick", "addQuestionnairePage(event,'" + rId + "')");
        elTabPage.appendChild(btnAdd);
        //进入页面查询当前卷和页面的数据
        var rId = $("#rId").val();
        var pageId = $("#pageId").val();
        $.ajax({
            type: 'get',
            url: "/questionnaire/reel/queryAllSubject",
            data: {
                "rId": rId,
                "pageId": pageId
            },
            success: function (result) {
                setTimeout(function () {
                    //移除给父元素添加的样式，以免印象父元素的其他属性
                    el.style.position = ""
                    el.removeChild(mask)
                }, 500)

                showData(result);

            }
        })


        var height = document.body.offsetHeight - 126
        var minHeight = height - 80
        $(".main").find(".questionnaire-panel").css({
            "height": minHeight
        })

    });

    /**
     * 创建加载动画元素
     * @returns {*}
     */
    function createLoadingEl() {
        var mask = document.createElement("div")
        mask.className = "mask"
        var loadingEl = document.createElement("div")
        loadingEl.style.backgroundImage = "url(<%=path %>/template/image/bg/loading-ffff.gif)"
        loadingEl.className = "loading-animate"
        mask.appendChild(loadingEl)
        return mask
    }

    /**
     * 失焦保存标题
     */
    $(".questionnaire-panel").on("blur", ".editTitle", function () {
        debugger
        // addAndEditTitleRemark();
        var title = $("#title").html();
        var titleText = title.toString().replace(/^\s*|\s$/g,"");
        // var titleText = $(this).text().replace(/^\s*|\s$/g,"");
        if (titleText.length > 10000) {
            return alertTip("标题内容包括格式内容不可超过10000个字")
        }
        if (title == "" || title == "<br>") {
            $("#title").focus()
            return alertTip("标题为空");
        }
        var startLanguage = $("#startLanguage").html();
        var pageId = $("#pageId").val();
        var rId = $("#rId").val();
        var folderId = $("#folderId").val();
        var endLanguage = $("#endLanguage").html();
        if (rId == "") {//当等于空时 还没有新增卷和页 是新增
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/titleSave",
                data: {
                    "title": title,
                    "startLanguage": startLanguage,
                    "endLanguage": endLanguage,
                    "pageId": pageId,
                    "folderId": folderId,
                    "rId": rId
                },
                success: function (result) {
                    //result = JSON.stringify(result);
                    var title = result.title;
                    var startLanguage = result.startLanguage;
                    var rId = result.rId;
                    $("#rId").val(result.rId)
                    var pageId = result.pageId;
                    //$(".panel-page:first").find(".wrap-edit-content").remove();
                    var remarkDiv = "<div class=\"wrap-edit-content\"> "
                        + " <p class=\"editable editRemark\" contenteditable=\"true\" data-type=\"hello\" onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" "
                        + " onmouseup=\"getSelectionText()\" id=\"startLanguage\" "
                        + " data-placeholder=\"为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧\"> "
                        + " 为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！</p></div>";
                    //$(".panel-page").prepend(remarkDiv);

                    var titleDiv = "<div class=\"wrap-edit-content\"> " +
                        " <p class=\"editable editTitle\" contenteditable=\"true\" id=\"title\" data-type=\"title\" data-placeholder=\"问卷标题\"  " +
                        " onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\">" + title + "</p></div>";
                    //$(".panel-page").prepend(titleDiv);
                    $("#pageId").val(pageId);
                    $("#rId").val(rId);
                    $("#param").find(".rId").val(rId)
                    $(".tab-page-index").eq(0).attr('onclick', 'changeQuestionnairePage(event,"' + rId + '","' + pageId + '",' + 0 + ')');
                    alertTip("保存成功");
                    //reload(this,'/questionnaire/reel/editpage',parms,'main')
                }
            })
        } else {//已经有了卷和页就是修改
            var title = $("#title").html();
            var startLanguage = $("#startLanguage").html();
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/titleSave",
                data: {
                    "title": title,
                    "startLanguage": startLanguage,
                    "pageId": pageId,
                    "folderId": folderId,
                    "rId": rId
                },
                success: function (result) {
                    //result = JSON.stringify(result);
                    var title = result.title;
                    var startLanguage = result.startLanguage;
                    var rId = result.rId;
                    var pageId = result.pageId;
                    var remarkDiv = "<div class=\"wrap-edit-content\"> "
                        + " <p class=\"editable editRemark\" contenteditable=\"true\" data-type=\"hello\" onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" "
                        + " onmouseup=\"getSelectionText()\" id=\"startLanguage\" "
                        + " data-placeholder=\"为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！\"> "
                        + "" + startLanguage + " </p></div>";

                    var titleDiv = "<div class=\"wrap-edit-content\"> " +
                        " <p class=\"editable editTitle\" contenteditable=\"true\" id=\"title\" data-type=\"title\" data-placeholder=\"问卷标题\"  " +
                        " onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\">" + title + "</p></div>";
                    $("#pageId").val(pageId);
                    alertTip("保存成功");
                    //reload(this,'/questionnaire/reel/editpage',parms,'main')
                }
            })
        }
    })


    /**
     * 失焦保存备注
     */
    $(".questionnaire-panel").on("blur", ".editRemark", function () {
        var startLanguage = $("#startLanguage").html();
        var startLanguageText = startLanguage.toString().replace(/^\s*|\S*$/g,"");
        if (startLanguage == "" || startLanguage == "<br>") {
            return alertTip("备注为空")
        }
        if(startLanguageText.length > 10000 ) {
            return alertTip("备注内容包括格式内容长度不能超过10000!")
        }
        var pageId = $("#pageId").val();
        var rId = $("#rId").val();
        var folderId = $("#folderId").val();
        var endLanguage = $("#endLanguage").html();
        // console.log("endLanguage:",endLanguage)
        if (rId == "") {//当等于空时 还没有新增卷和页 是新增
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/remarkSave",
                data: {
                    "startLanguage": startLanguage,
                    "endLanguage": endLanguage,
                    "pageId": pageId,
                    "folderId": folderId,
                    "rId": rId
                },
                success: function (result) {
                    //result = JSON.stringify(result);
                    var title = result.title;
                    var startLanguage = result.startLanguage;
                    var rId = result.rId;
                    $("#rId").val(result.rId)
                    var pageId = result.pageId;

                    var remarkDiv = "<div class=\"wrap-edit-content\"> "
                        + " <p class=\"editable editRemark\" contenteditable=\"true\" data-type=\"hello\" onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" "
                        + " onmouseup=\"getSelectionText()\" id=\"startLanguage\" "
                        + " data-placeholder=\"为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！\"> "
                        + " " + startLanguage + "</p></div>";

                    var titleDiv = "<div class=\"wrap-edit-content\"> " +
                        " <p class=\"editable editTitle\" contenteditable=\"true\" id=\"title\" data-type=\"title\" data-placeholder=\"问卷标题\"  " +
                        " onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\">" + title + "</p></div>";
                    $("#pageId").val(pageId);
                    $("#rId").val(rId);
                    $("#param").find(".rId").val(rId)
                    $(".tab-page-index").eq(0).attr('onclick', 'changeQuestionnairePage(event,"' + rId + '","' + pageId + '",' + 0 + ')');
                    if ($(".tab-page>.tab-page-index").length - 1 == $(".end-tab").attr("data-index")) {
                        $(".tab-page-index.end-tab").attr('onclick', 'changeQuestionnairePage(event,"' + rId + '","' + pageId + '",' + $(".end-tab").attr("data-index") + ')');
                    }
                    alertTip("保存成功");
                    //reload(this,'/questionnaire/reel/editpage',parms,'main')
                },
                error:function (error) {
                    alertTip("保存失败");
                }
            })
        } else {//已经有了卷和页就是修改
            var startLanguage = $("#startLanguage").html();
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/remarkSave",
                data: {
                    "startLanguage": startLanguage,
                    "pageId": pageId,
                    "folderId": folderId,
                    "rId": rId
                },
                success: function (result) {
                    //result = JSON.stringify(result);
                    var title = result.title;
                    var startLanguage = result.startLanguage;
                    var rId = result.rId;
                    var pageId = result.pageId;
                    var remarkDiv = "<div class=\"wrap-edit-content\"> "
                        + " <p class=\"editable editRemark\" contenteditable=\"true\" data-type=\"hello\" onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" "
                        + " onmouseup=\"getSelectionText()\" id=\"startLanguage\" "
                        + " data-placeholder=\"为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！\"> "
                        + " " + startLanguage + "</p></div>";

                    var titleDiv = "<div class=\"wrap-edit-content\"> " +
                        " <p class=\"editable editTitle\" contenteditable=\"true\" id=\"title\" data-type=\"title\" data-placeholder=\"问卷标题\"  " +
                        " onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\">" + title + "</p></div>";

                    alertTip("保存成功");
                    //reload(this,'/questionnaire/reel/editpage',parms,'main')
                },
                error:function (error) {
                    alertTip("保存失败");
                }
            })
        }
    })

    //单独保存结束页
    $(".questionnaire-panel").on("blur", ".endLanguage", function () {
        var startLanguage = $("#startLanguage").html();
        var endLanguage = $("#endLanguage").html();
        var pageId = $("#pageId").val();
        var rId = $("#rId").val();
        var folderId = $("#folderId").val();
        $.ajax({
            type: 'post',
            url: "/questionnaire/reel/endLanguageSave",
            data: {
                "startLanguage": startLanguage,
                "endLanguage": endLanguage,
                "pageId": pageId,
                "folderId": folderId,
                "rId": rId
            },
            success: function (result) {
                // result = JSON.stringify(result);
                var title = result.title;
                var startLanguage = result.startLanguage;
                var rId = result.rId;
                var pageId = result.pageId;
                var remarkDiv = "<div class=\"wrap-edit-content\"> "
                    + " <p class=\"editable editRemark\" contenteditable=\"true\" data-type=\"endLanguage\" onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" "
                    + " onmouseup=\"getSelectionText()\" id=\"startLanguage\" "
                    + " data-placeholder=\"为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！\"> "
                    + " " + startLanguage + "</p></div>";

                var titleDiv = "<div class=\"wrap-edit-content\"> " +
                    " <p class=\"editable editTitle \" contenteditable=\"true\" id=\"title\" data-type=\"title\" data-placeholder=\"问卷标题\"  " +
                    " onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\">" + title + "</p></div>";

                alertTip("保存成功");
                //reload(this,'/questionnaire/reel/editpage',parms,'main')
            }
        })
    })


    /**
     * 题和选项数据回显
     * @params:result
     */
    function showData(result) {
        for (var i = 0; i < result.length; i++) {
            //var json = JSON.stringify(result[i]);
            var title = result[i].title;
            var startLanguage = result[i].startLanguage;
            $("#title").html(title);
            $("#startLanguage").html(startLanguage);
            var subjectList = result[i].subjectList;//题

            for (var k = 0; k < subjectList.length; k++) {
                var must = subjectList[k].must;
                var questionHtml = ""
                if (must == "0") {  //是否必填
                    questionHtml += "<div class=\"question-item clearfix\" onclick=\"editQuestion(event,'" + subjectList[k].subjectId + "','" + $("#rId").val() + "',this)\" onmouseenter=\"showSlider(event)\" onmouseleave=\"hideSlider(event,'" + subjectList[k].subjectId + "')\">\n" +
                                    "     <div class=\"question-item-wrap\">\n" +
                                    "          <div class=\"question-item-title\">\n" +
                                    "               <span class='biaoji'>*</span>" +
                                    "               <span class='xuhao'>" + (k + 1) + ".</span>\n" +
                                    "               <span class='item-content-title'>" + subjectList[k].topic + "</span>\n" +
                                    "           </div>\n" +
                                    " <div class=\"question-desc\">" + subjectList[k].remark + "</div>\n"
                } else {
                    questionHtml += "<div class=\"question-item clearfix\" onclick=\"editQuestion(event,'" + subjectList[k].subjectId + "','" + $("#rId").val() + "',this)\" onmouseenter=\"showSlider(event)\" onmouseleave=\"hideSlider(event,'" + subjectList[k].subjectId + "')\">\n" +
                        "            <div class=\"question-item-wrap\">\n" +
                        "                <div class=\"question-item-title\">\n" +
                        "                    <span>" + (k + 1) + ".</span>\n" +
                        "                    <span>" + subjectList[k].topic + "</span>\n" +
                        "                </div>\n" +
                        "                <div class=\"question-desc\">" + subjectList[k].remark + "</div>\n"
                }


                var subjectType = subjectList[k].subjectType;//单选1 多选2

                var questionOptionsHtml = "<div class=\"question-item-content\">"
                for (var j = 0; j < subjectList[k].optionsList.length; j++) {

                    if (subjectType == 1) {//单选
                        questionOptionsHtml += " <div class=\"radio-primary radio-btn\">\n" +
                                                "   <input type=\"radio\" name=\"options1\" value="+subjectList[k].optionsList[j].optionsId+">\n" +
                                                "   <label>" + subjectList[k].optionsList[j].options + "</label>\n" +
                                                " </div>"
                    } else if(subjectType == 2) {  // 多选
                        // 其它选项
                        if (subjectList[k].optionsList[j].isMultipleChoice == 0) {
                            var tempHtml = "<div class='checkbox-primary checkbox-btn'>"+
                                                "<input type='hidden' name='' value="+subjectList[k].optionsList[j].optionsId+"/>"+
                                                "<label class='wrap-other'>"+
                                                    "<div style='width:70px;text-align:center;'>其他：</div>"+
                                                    "<label style='height: 20px;'>"+subjectList[k].optionsList[j].options+"</label>"+
                                                "</label>"+
                                            "</div>"
                            questionOptionsHtml += tempHtml

                        } else {   // 渲染选项
                            questionOptionsHtml += "<div class='checkbox-primary checkbox-btn'>"+
                                                    "<input type='checkbox' name='options4' value="+subjectList[k].optionsList[j].optionsId+"/>"+
                                                    "<label>" + subjectList[k].optionsList[j].options + "</label>"+
                                                    "</div>"
                        }
                    } else {
                        questionOptionsHtml+= "<div class=\"box-single__line\" style=\"margin-bottom:10px;\">"+
                            　　　　　　　　　　　　"<div class=\"fieldset\">"+
                                                        "<div class=\"label\"></div>"+
                                                        "<div class=\"editable optionIpt single-line-text\" " +
                                                           "id=\"single-line-text\" " +
                                                            "data-placeholder=\"单行文本\"" +
                                                            "data-type=\"content\"" +
                                                            "data-option-type=\"SINGLE\">"+
                                                        "</div>"+
                                                     "</div>"+
                                                  "</div>"
                    }
                }
                questionHtml += questionOptionsHtml += "</div> </div>"
                /**右侧滑动的侧边栏**/
                questionHtml += " <div class=\"slider-list\">\n" +
                    "                <div class=\"wrap-item\" onclick=\"editQuestion(event,'" + subjectList[k].subjectId + "','" + $("#rId").val() + "',this)\"><div></div></div>\n" +
                    "                <div class=\"wrap-item\" style=\"display:none;\" onclick=\"copyQuestion(event,'" + subjectList[k].subjectId + "')\"><div></div></div>\n" +
                    "                <div class=\"wrap-item\" style=\"display:none;\"><div></div></div>\n" +
                    "                <div class=\"wrap-item\"  onclick=\"deleteQuestion(event,'" + subjectList[k].subjectId + "',this)\"><div></div></div>\n" +
                    "                <div class=\"wrap-item\" onclick=\"addCollect(event,'" + subjectList[k].subjectId + "',this)\"><div></div></div>\n" +
                    "            </div>\n" +
                    "        </div>" +
                    "</div>"
                $(".panel-page").append(questionHtml)
            }
        }
    }

    /**
     * 创建题目
     */
    $(".main").unbind("click").on("click", '.components>div', function () {
        var title = $(".editTitle").html();
        var titleText = title.toString().replace(/\s*|\s*$/g,"");
        if (titleText.length > 10000) {
            return alertTip("标题内容包括格式内容不可超过10000个字")
        } else {
            if ($(this).attr("index")) { // 索引【单选，多选，单行文本】
                for (var k = 0; k < $(".panel-page").length; k++) {
                    var panelItem = $(".panel-page")[k];
                    // 第一页
                    if ($(panelItem).css("display") == "block") {
                        if ($(panelItem).attr("data-pageindex") == 0) {
                            if ($(panelItem).find(".reel-edit").length > 0 || $(panelItem).find(".wrap-edit-content").length > 2) {
                                alertTip("当前处于编辑状态")
                                return false;
                            }
                            // 添加题目
                            $(panelItem).append($("#subject-edit").html())
                            // 题目选项动态隐藏
                            var questionType = parseInt($(this).attr("index"))
                            switch (questionType) {
                                case 1:
                                    $(".reel-edit .btn-add__other-option").hide();
                                    $(".reel-edit .box-single__line").hide();
                                    break; //单选
                                case 2:
                                    $(".reel-edit .btn-add__other-option").show();
                                    $(".reel-edit .box-single__line").hide();
                                    break; //多选
                                case 4:
                                    $(".reel-edit .btn-add__other-option").hide();
                                    $(".reel-edit .all-option").hide();
                                    $(".reel-edit .create-option").hide();
                                    break;  //单行文本
                                default :
                                    $(".reel-edit .btn-add__other-option").hide();
                                    $(".reel-edit .box-single__line").hide();
                                    break; //当选择处理
                            }

                            // $(".questionnaire-panel").scrollTop($(".reel-edit").offset().top)
                            for (var i = 0; i < $(panelItem).find(".selectType ul>li").length; i++) {
                                var item = $(panelItem).find(".selectType ul>li")[i]
                                if ($(this).attr("index") == $(item).attr("value")) {
                                    $(item).parent().prev(".show-title").find("span").text($(item).text()).attr("value", $(item).attr("value"))
                                }
                            }
                            break
                        } else {
                            if ($(panelItem).find(".reel-edit").length > 0 || $(panelItem).find(".wrap-edit-content").length > 0) {
                                alertTip("当前处于编辑状态")
                                return false;
                            }
                            // 添加题目
                            $(panelItem).append($("#subject-edit").html())
                            // 题目选项动态隐藏
                            var questionType = parseInt($(this).attr("index"))
                            switch (questionType) {
                                case 1 :
                                    $(".reel-edit .btn-add__other-option").hide();
                                    $(".reel-edit .box-single__line").hide();
                                    break; //单选
                                case 2 :
                                    $(".reel-edit .btn-add__other-option").show();
                                    $(".reel-edit .box-single__line").hide();
                                    break; //多选
                                case 4:
                                    $(".reel-edit .btn-add__other-option").hide();
                                    $(".reel-edit .all-option").hide();
                                    $(".reel-edit .create-option").hide();
                                    break;   //单行文本
                                default :
                                    $(".reel-edit .btn-add__other-option").hide();
                                    $(".reel-edit .box-single__line").hide();
                                    break; //当选择处理
                            }
                            $(".questionnaire-panel").scrollTop($(".reel-edit").offset().top)
                            // for(var i =0; i<$(panelItem).find("#subjectType>option").length; i++){
                            // 	if($(this).attr("index") ==$(panelItem).find("#subjectType>option")[i].value){
                            // 		$($(panelItem).find("#subjectType>option")[i]).prop("selected",true)
                            // 	}
                            // }
                            for (var i = 0; i < $(panelItem).find(".selectType ul>li").length; i++) {
                                var item = $(panelItem).find(".selectType ul>li")[i]
                                if ($(this).attr("index") == $(item).attr("value")) {
                                    $(item).parent().prev(".show-title").find("span").text($(item).text()).attr("value", $(item).attr("value"))
                                }
                            }
                        }
                    }
                }
            }
        }

    })


    /**
     * 新增保存题目
     */
    $(".panel").on("click", "#subjectSave", function () {
        var pageById = $(".components").attr("page_id");
        if (pageById == undefined) {
            pageById = $("#pageId").val();
        }
        var subjectIndex = $(".components").attr("subjectIndex");
        if ($("#rId").val() != "") {
            var subjectId = $("#subjectId").val();
            var topic = $("#topic").html();
            if (topic == "") {
                alertTip("题目不能为空");
                return false;
            }
            var remark = $("#remark").html();
            //var subjectType = $("#subjectType").val();
            //获取下拉框选中的值
            var subjectType = $(".selectType .show-title span").attr("value")
            var must = $("#must:checkbox").is(":checked");//必填
            if (must) {
                must = "0";//0:选中
            } else {
                must = "1";//1:未选中
            }
            var rId = $("#rId").val();
            var optionsArr = new Array();
            var langth = $(".optionIpt").length;

            for (var i = 0; i < $(".optionIpt").length; i++) {
                var options = $($(".optionIpt")[i]).html();
                if (options.replace("<br>", "") == "") {
                    // alertTip("还有未填项");
                    // return false;
                    options = ""
                }
                var optionType = $(".optionIpt")[i].dataset.optionType
                var type = 0
                switch(optionType) {
                    case "NORMAL": type = 1; break;  // 正常选项：1
                    case "SINGLE": type = 2; break;  // 单行文本选项：2
                    case "OTHER":  type = 0; break;  // 其他选项：0
                    default:       type = 1; break;  //
                }
                // optionsArr.push($($(".optionIpt")[i]).html()+":"+type);
                if (options !== "") {
                    optionsArr.push($($(".optionIpt")[i]).html()+"&&&&"+type);
                }
            }
            // console.log("新增选项r:",optionsArr)
            var folderId = $("#folderId").val();
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/addToPageSubject",
                data: {
                    "pageId": pageById,
                    "topic": topic,
                    "remark": remark,
                    "subjectType": subjectType,
                    "must": must,
                    "optionsArr": optionsArr,
                    "rId": rId,
                    "folderId": folderId
                },
                success: function (msg) {
                    $(".reel-edit").remove();
                    $(".wrap-subject").empty();
                    $.ajax({
                        type: 'get',
                        url: "/questionnaire/reel/queryAllByPageIdSubject",
                        data: {
                            "rId": rId,
                            "pageId": pageById
                        },
                        success: function (result) {
                            $(".panel-page").empty();
                            //刷新文本大纲数据
                            $(".outline").empty()
                            getDragList(rId)
                            if (subjectIndex == 0) { //
                                for (var i = 0; i < result.length; i++) {
                                    var title = result[i].title;
                                    var startLanguage = result[i].startLanguage;
                                    var titleDiv = `<div class="wrap-edit-content">
                                                      <p class="editable editTitle"
                                                         contenteditable="true"
                                                         id="title" data-type="title" data-placeholder="问卷标题"
                                                         onclick="editContent(event)"
                                                         onblur="cancelEdit(event)"
                                                         onmouseup="getSelectionText()">
                                                         ${title}
                                                     </p>
                                                   </div>`;
                                    $(".panel-page").append(titleDiv);

                                    var remarkDiv = `<div class="wrap-edit-content">
                                                       <p class="editable editRemark"
                                                          contenteditable="true"
                                                          id="startLanguage"
                                                          data-type="hello"
                                                          onclick="editContent(event)"
                                                          onblur="cancelEdit(event)"
                                                          onmouseup="getSelectionText()"
                                                          data-placeholder="为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！">
                                                          ${startLanguage}
                                                       </p>
                                                      </div>`;
                                    $(".panel-page").append(remarkDiv);
                                }
                            }
                            showData(result);
                            if ($(".tab-page>.tab-page-index").length - 1 == $(".end-tab").attr("data-index")) {
                                $(".tab-page-index.end-tab").attr('onclick', 'changeQuestionnairePage(event,"' + rId + '","' + pageId + '",' + $(".end-tab").attr("data-index") + ')');
                            }
                            alertTip("新增成功！");
                        },
                        error:function (error) {
                            alertTip("新增题目失败 ！");
                        }
                    })
                }
            })
        } else {
            /** 第一次新增 **/
            var subjectId = $("#subjectId").val();
            var pageId = "";
            pageId = $("#pageId").val();
            var topic = $("#topic").html();
            if (topic == "") {
                alertTip("题目不能为空！");
                return false;
            }
            var remark = $("#remark").html();
            //var subjectType = $("#subjectType").val();
            //获取下拉框选中的值
            var subjectType = $(".selectType .show-title span").attr("value")
            var must = $("#must:checkbox").is(":checked");//必填
            if (must) {
                must = "0";//0:选中
            } else {
                must = "1";//1:未选中
            }
            var rId = $("#rId").val();
            var optionsArr = new Array();
            for (var i = 0; i < $(".optionIpt").length; i++) {
                var options = $($(".optionIpt")[i]).html().toString().replace(/\s*|\s$/g,"");
                // if (options == "") {
                //     alertTip("还有未填项");
                //     return false;
                // }
                if (options.replace("<br>", "") == "") {
                    // alertTip("还有未填项");
                    // return false;
                    options = ""
                }
                var optionType = $(".optionIpt")[i].dataset.optionType
                var type = 0
                switch(optionType) {
                    case "NORMAL": type = 1; break;  // 正常选项   ：1
                    case "SINGLE": type = 2; break;  // 单行文本选项：2
                    case "OTHER":  type = 0; break;  // 其他选项   ：0
                    default:       type = 1; break;  //
                }
                if (options !== "") {
                    optionsArr.push($($(".optionIpt")[i]).html()+"&&&&"+type);
                    // optionsArr.push({
                    //     $($(".optionIpt")[i]).html()+"&&&&"+type
                    // });
                }
            }
            var folderId = $("#folderId").val();
            var endLanguage = $("#endLanguage").html();
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/subjectSave",
                data: {
                    "subjectId": subjectId,
                    "pageId": pageId,
                    "topic": topic,
                    "remark": remark,
                    "subjectType": subjectType,
                    "must": must,
                    "optionsArr": optionsArr,
                    "rId": rId,
                    "folderId": folderId,
                    "endLanguage": endLanguage
                },
                success: function (msg) {
                    //msg = JSON.stringify(msg);
                    rId = msg.reelId;
                    pageId = msg.pageId;
                    $("#rId").val(rId);
                    $("#pageId").val(pageId);//第一次新增时还没rid和pageId 新增完返回这2个id 赋值给隐藏域
                    $("form").css("display", "none")
                    $.ajax({
                        type: 'get',
                        url: "/questionnaire/reel/queryAllSubject",
                        data: {
                            "rId": rId,
                            "pageId": pageId
                        },
                        success: function (result) {
                            // 刷新文本大纲数据
                            $(".outline").empty()
                            getDragList(rId)
                            $(".panel-page").empty();
                            if (subjectIndex == 0) {
                                for (var i = 0; i < result.length; i++) {
                                    var title = result[i].title;
                                    var startLanguage = result[i].startLanguage;
                                    var titleDiv = " <div class=\"wrap-edit-content\">"
                                        + " <p class=\"editable editTitle\" contenteditable=\"true\"  id=\"title\" data-type=\"title\" data-placeholder=\"问卷标题\""
                                        + " onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\">" + title + "</p>"
                                        + " </div>";
                                    $(".panel-page").append(titleDiv);
                                    var remarkDiv = "<div class=\"wrap-edit-content\">"
                                        + " <p class=\"editable editRemark\"  contenteditable=\"true\" id=\"startLanguage\" data-type=\"hello\" onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\""
                                        + "  data-placeholder=\"为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！\">"
                                        + "  " + startLanguage + "</p> </div>";
                                    $(".panel-page").append(remarkDiv);
                                }
                            }
                            showData(result);
                            $(".tab-page-index").eq(0).attr('onclick', 'changeQuestionnairePage(event,"' + rId + '","' + pageId + '",' + 0 + ')');
                            if ($(".tab-page>.tab-page-index").length - 1 == $(".end-tab").attr("data-index")) {
                                $(".tab-page-index.end-tab").attr('onclick', 'changeQuestionnairePage(event,"' + rId + '","' + pageId + '",' + $(".end-tab").attr("data-index") + ')');
                            }
                            $("#param").find(".rId").val(rId);
                            alertTip("新增成功");
                        }
                    })
                    var parms = {
                        "rId": rId,
                        "pageId": pageId
                    }
                },
                error:function (error) {
                    alertTip("新增失败");
                }
            })
        }
    })

    /**
     *
     * @param rId
     * @param pageId
     */
    function createQuestion(rId, pageId) {
        $.ajax({
            type: 'get',
            url: "/questionnaire/reel/queryAllSubject",
            data: {
                "rId": rId,
                "pageId": pageId
            },
            success: function (result) {
                $(".clearfix").empty();
                showData(result);
            }
        })
    }

    function shangchuan() {
        location.href = "/questionnaire/reel/toUpload";
    }


    /**
     * 新建问卷页面
     * @param ev
     */
    function addQuestionnairePage(ev, obj) {
        var rId = $("#rId").val();
        var title = $("#title").html();
        if (title == "" || title == "<br>") {
            $("#title").focus()
            return alertTip("标题为空");
        }
        var showPageIndex = $(".tab-page-index.active").not(".end-tab").attr("data-index");
        if (showPageIndex == 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length > 2) {
            return alertTip("有未保存的题目")
        } else if (showPageIndex != 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length > 0) {
            return alertTip("有未保存的题目")
        }
        if (obj != "" || rId != "") {
            var rId = $("#rId").val();
            obj = rId;
            var pageId = "";
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/addPage",
                async: false,
                data: {
                    "rId": obj
                },
                success: function (result) {
                    pageId = result;
                }
            })
            // 新增文本大纲添加一页
            var pageBox = '<div class="outline-item">\n'
                        + '<div class="sortItem pageNum" pageid=' + pageId + ' rid=' + obj + '>第' + parseInt($(".pageNum").length + 1) + '页</div>\n'
                        + '<ul></ul>\n'
                        + '</div>\n'
            $(".outline").append(pageBox)
            ev = window.event || ev
            var target = ev.target
            var parent = target.parentNode;
            // 结束页tab
            var endTab = parent.getElementsByClassName("end-tab")[0]

            //下一页
            var elBtnNext = parent.getElementsByClassName("tab-btn-next")[0]
            //创建新Tab页
            var newTab = document.createElement('div')
            newTab.className = "tab-page-index"
            //新增页面的索引
            newTab.dataset.index = parseInt(parent.children.length) - 4
            //页面当前索引
            var targetIndex = parseInt(parent.children.length) - 4
            newTab.setAttribute("onclick", "changeQuestionnairePage(event,'" + obj + "','" + pageId + "','" + targetIndex + "')")
            $(".components").attr("subjectIndex", targetIndex);
            //创建tab标签的子元素
            var elSpan = document.createElement('span')
            var elI = document.createElement('i')
            elI.setAttribute("onclick", "deletePage(event,'" + pageId + "')")
            var numArr = ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五"]
            //创建问卷页面
            var elPanel = document.getElementsByClassName("panel")[0]
            var elPanelPage = elPanel.getElementsByClassName("panel-page")
            var temp = document.createElement("div")
            temp.className = "panel-page"

            temp.dataset.pageindex = parseInt(parent.children.length) - 4
            // 结束页面板索引
            var endPage = elPanel.getElementsByClassName("end-page")[0]
            if (parseInt(newTab.dataset.index) >= 14) {
                alert("不能超过15页！！！")
            } else {
                for (var i = 0; i < numArr.length; i++) {
                    if (parseInt(newTab.dataset.index) === i) {
                        elSpan.innerText = "第" + numArr[i] + "页"
                        elSpan.textContent = "第" + numArr[i] + "页"
                    }
                }
                //为tab标签添加子元素
                newTab.appendChild(elSpan)
                newTab.appendChild(elI)
                //添加到tab
                parent.insertBefore(newTab, endTab);
                //添加新的问卷页面
                elPanel.insertBefore(temp, endPage)
            }
            // 更改最后一页tab索引
            endTab.dataset.index = parseInt(newTab.dataset.index) + 1
            endTab.setAttribute("onclick", "changeQuestionnairePage(event,'" + obj + "','" + $("#pageId").val() + "','" + parseInt(targetIndex + 1) + "')")
            // 更改最后一页面板索引
            endPage.dataset.pageindex = parseInt(newTab.dataset.index) + 1
            //首先清空之前的问卷页里的active
            var elTabPageIndex = parent.getElementsByClassName("tab-page-index")
            var len = elTabPageIndex.length
            for (var i = 0; i < len; i++) {
                //移除tab标签的active类名
                if (elTabPageIndex[i].classList.contains("active")) {
                    elTabPageIndex[i].classList.remove("active")
                }

                //隐藏问卷页面
                elPanelPage[i].style.display = "none"
            }
            elTabPageIndex[len - 2].className += " active"
            elPanelPage[len - 2].style.display = "block"

            $(".components").attr("page_id", pageId);
        } else if ($(".panel").find(".wrap-edit-content").length > 3) {
            return alertTip("题目还未保存")
        } else {
            alertTip("还未新增题");
        }
    }


    /**
     * 点击切换当前页
     * @param ev
     */
    function changeQuestionnairePage(ev, obj, obj1, index) {
        var title = $("#title").html();
        if (title == "" || title == "<br>") {
            $("#title").focus()
            return alertTip("标题为空");
        }
        var showPageIndex = $(".tab-page-index.active").not(".end-tab").attr("data-index");
        if (showPageIndex == 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length > 2) {
            return alertTip("有未保存的题目")
        } else if (showPageIndex != 0 && $(".panel-page").eq(showPageIndex).find(".wrap-edit-content").length > 0) {
            return alertTip("有未保存的题目")
        }
        $(".components").attr("page_id", obj1);
        $(".components").attr("subjectIndex", index);

        if (obj != "") {
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/queryAllSubject",
                async: false,
                data: {
                    "rId": obj,
                    "pageId": obj1
                },
                success: function (result) {
                    $(".panel-page").empty();
                    for (var i = 0; i < result.length; i++) {
                        //var json = JSON.stringify(result[i]);
                        var title = result[i].title;
                        var startLanguage = result[i].startLanguage;
                        var endLanguage = result[i].endLanguage;
                        if (index == 0) {
                            var titleDiv = "<div class=\"wrap-edit-content\">"
                                + " <p class=\"editable editTitle\" contenteditable=\"true\"  id=\"title\" data-type=\"title\" data-placeholder=\"问卷标题\""
                                + " onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\">" + title + "</p>"
                                + " </div>";
                            $(".panel-page").append(titleDiv);
                            var remarkDiv = "<div class=\"wrap-edit-content\">"
                                + " <p class=\"editable editRemark\"  contenteditable=\"true\" id=\"startLanguage\" data-type=\"hello\" onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\""
                                + "  data-placeholder=\"为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们,我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！\">"
                                + "  " + startLanguage + "</p> </div>";


                            $(".panel-page").append(remarkDiv);
                        }
                        showData(result);
                        var endlanguageLength = $(".panel>.panel-page").length;
                        var endlanguagePage = $(".panel-page").last().attr("data-pageindex");
                        if ((endlanguageLength - 1) == index && endlanguagePage == index) {//当panel子元素最大值等于当前点击坐标数值就是结束页
                            $(".panel-page").empty();
                            var endDiv = "<div class=\"wrap-edit-content\"> "
                                + " <div class=\"editable endLanguage\" contenteditable=\"true\" data-type=\"endLanguage\" onclick=\"editContent(event)\" onblur=\"cancelEdit(event)\" "
                                + " onmouseup=\"getSelectionText()\" id=\"endLanguage\" "
                                + " data-placeholder=\"\"> "
                                + " " + endLanguage + "</div></div>";
                            $(".panel-page.end-page").append(endDiv);
                        }
                    }
                }
            })
        }

        ev = window.event || ev
        var target = ev.target
        if (target.className == undefined) {
            return
        } else {
            if (target.className.indexOf("tab-page-index") === -1) {
                target = target.parentNode
            }
            var parent = target.parentNode
            var elTab = parent.getElementsByClassName("tab-page-index")
            // 问卷页
            var elPanel = document.getElementsByClassName("panel")[0]
            var elPanelPage = elPanel.getElementsByClassName("panel-page")
            for (var i = 0, len = elTab.length; i < len; i++) {
                if (i === parseInt(target.dataset.index)) {
                    target.className += " active"
                    elPanelPage[i].style.display = "block"
                } else {
                    elTab[i].classList.remove("active")
                    elPanelPage[i].style.display = "none"
                }
            }
        }
    }

    /**
     * 下一页
     * @param ev
     */
    function toNextPage(ev) {
        var title = $("#title").html();
        if (title == "" || title == "<br>") {
            $("#title").focus()
            return alertTip("标题为空");
        }
        ev = window.event || ev
        var target = ev.target
        var parent = target.parentNode
        //点击下一页，首先把隐藏的上一页按钮放出来
        var elBtnNext = parent.getElementsByClassName("tab-btn-forward")[0]
        elBtnNext.style.display = "block"
        var elTab = parent.getElementsByClassName("tab-page-index")
        // 问卷页
        var elPanel = document.getElementsByClassName("panel")[0]
        var elPanelPage = elPanel.getElementsByClassName("panel-page")
        for (var i = elTab.length - 1; i >= 0; i--) {
            //之前所在的当前页
            if (elTab[i].className.indexOf("active") > -1) {
                if (parseInt(elTab[i].dataset.index) < elTab.length - 1) {
                    //切换当前页
                    elTab[i].className = "tab-page-index"
                    elTab[i].nextElementSibling.className += " active"
                    // 问卷当前页切换
                    elPanelPage[i].style.display = "none"
                    elPanelPage[i].nextElementSibling.style.display = "block"
                } else { //当前是第一页
                    //隐藏向前一页的元素
                    target.style.display = "none"
                }
            }
        }
    }

    /**
     * 上一页
     * @param ev
     */
    function toForwardPage(ev) {
        var title = $("#title").html();
        if (title == "" || title == "<br>") {
            $("#title").focus()
            return alertTip("标题为空");
        }
        ev = window.event || ev
        var target = ev.target
        var parent = target.parentNode
        //点击上一页，首先把隐藏的下一页按钮放出来
        var elBtnNext = parent.getElementsByClassName("tab-btn-next")[0]
        elBtnNext.style.display = "block"
        var elTab = parent.getElementsByClassName("tab-page-index")
        // 问卷页
        var elPanel = document.getElementsByClassName("panel")[0]
        var elPanelPage = elPanel.getElementsByClassName("panel-page")
        for (var i = 0, len = elTab.length; i < len; i++) {
            //之前所在的当前页
            if (elTab[i].className.indexOf("active") > -1) {
                if (parseInt(elTab[i].dataset.index) > 0) {
                    //切换当前页
                    elTab[i].className = "tab-page-index"
                    elTab[i].previousElementSibling.className += " active"
                    // 问卷当前页切换
                    elPanelPage[i].style.display = "none"
                    elPanelPage[i].previousElementSibling.style.display = "block"
                } else { //当前是第一页
                    //隐藏向前一页的元素
                    target.style.display = "none"
                }
            }
        }
    }


    /**
     * 删除问卷页面
     * @param ev
     */
    function deletePage(ev, pageId) {
        var rId = $(".rId").val()
        if ($(".tab-page-index").length > 2) {
            var flag = "";
            $.ajax({
                type: 'post',
                url: "/questionnaire/reel/querySubject",
                async: false,
                data: {
                    "pageId": pageId
                },
                success: function (result) {
                    flag = result;
                }
            })
            if (flag != "1") {//直接删除
                $.ajax({
                    type: 'post',
                    url: "/questionnaire/reel/deletePageById",
                    async: false,
                    data: {
                        "pageId": pageId
                    },
                    success: function (result) {
                    }
                })
                //$('.outline .pageNum[pageid='+pageId+']').remove()
                $(".outline").empty()
                getDragList(rId)
                deletePageData(ev);

            } else {//页下已经有题了。要确认
                if (confirm("确定要删除页吗？")) {
                    $.ajax({
                        type: 'post',
                        url: "/questionnaire/reel/deletePageById",
                        data: {
                            "pageId": pageId
                        },
                        async: false,
                        success: function (result) {
                            // for(var i = 0; i<result.data.length; i++){
                            //     var subjectId = result.data[i].subjectId;
                            //     $('.outline li.sortItem[subjectid='+subjectId+']').remove()
                            // }
                            // $('.outline .pageNum[pageid='+pageId+']').remove()
                        }
                    })
                    $(".outline").empty()
                    getDragList(rId)
                    deletePageData(ev);
                }
            }
        } else {
            alertTip("不能删除最后一页");
        }
    }

    function deletePageData(ev) {
        ev = window.event || ev
        //防止冒泡
        ev.stopPropagation()
        var target = ev.target
        //tab页
        var currentTab = target.parentNode
        var elTabPage = currentTab.parentNode
        var children = elTabPage.getElementsByClassName("tab-page-index")
        // 问卷页
        var elPanel = document.getElementsByClassName("panel")[0]
        var elPanelPage = elPanel.getElementsByClassName("panel-page")
        if (children.length > 2) {
            //移除该tab页
            var index = parseInt(currentTab.dataset.index)
            for (var i = children.length - 1; i >= 0; i--) {
                // 根据索引删除
                if (index === parseInt(children[i].dataset.index)) {
                    // 删除的页面是当前页，当前页往前或者往后设置
                    if (currentTab.className.indexOf("active") > -1) {
                        var previousEl = currentTab.previousElementSibling
                        var nextEl = currentTab.nextElementSibling
                        if (index !== children.length - 1) {
                            nextEl.className += "  active"
                            var nextPage = elPanelPage[i].nextElementSibling
                            nextPage.style.display = "block"
                        } else {
                            previousEl.className += "active"
                            var perPage = elPanelPage[i].previousElementSibling
                            perPage.style.display = "block"
                        }
                    }
                    elTabPage.removeChild(currentTab)
                    elPanel.removeChild(elPanelPage[i])
                }
            }
        } else {
            alert("只剩最后一页，不能再删除!")
            return false
        }

        //重新给页面排序
        var elTab = elTabPage.getElementsByClassName("tab-page-index")
        var elPanelChldren = elPanel.children
        var numArr = ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五"]
        for (var i = 0, len = elTab.length - 1; i < len; i++) {
            elTab[i].dataset.index = i.toString()
            var childSpan = elTab[i].getElementsByTagName("span")[0]
            var pageOnclick = elTab[i].getAttribute("onclick")
            var attrPage = pageOnclick.substring(pageOnclick.indexOf("(") + 1, pageOnclick.indexOf(")"))
            var pageAttr = attrPage.split(",")
            pageAttr[3] = "'" + i + "'"
            attrPage = pageAttr.join(",")
            pageOnclick = "changeQuestionnairePage(" + attrPage + ")"
            elTab[i].setAttribute("onclick", pageOnclick)
            for (var j = 0; j < numArr.length; j++) {
                if (i === j) {
                    childSpan.innerText = "第" + numArr[j] + "页"
                    childSpan.textContent = "第" + numArr[j] + "页"
                }
            }
            elPanelChldren[i].dataset.pageindex = i.toString()
            // 触发当前显示页面的点击事件
            if (elTab[i].className.indexOf("active") > -1) {
                elTab[i].click()
            }

        }
        // 重新给结束页排序坐标
        var endTab = elTabPage.getElementsByClassName("end-tab")[0];
        endTab.dataset.index = elTab.length - 1;
        var oneRid = $('#rId').val();
        var onePageId = $('#pageId').val();
        var oneIndex = elTab.length - 1
        endTab.setAttribute("onclick", "changeQuestionnairePage(event,'" + oneRid + "','" + onePageId + "','" + oneIndex + "')")
        var endPage = elPanel.getElementsByClassName("end-page")[0];
        endPage.dataset.pageindex = elTab.length - 1;
        if (endTab.className.indexOf("active") > -1) {
            endPage.style.display = "block"
            endTab.onclick = changeQuestionnairePage(event, oneRid, onePageId, oneIndex)
        }

    }


    function statistics(rId, pageId) {
        location.href = "?rId=" + rId + "&pageId=" + pageId;
    }

    function setUp(rId) {
        //$(this).addClass("active").siblings("div").removeClass("active")
        $(this).addClass("active").siblings().removeClass("active");
        var urlStr = "/questionnaire/question/tosetup";
        var clazz = "main";
        $.ajax({
            type: 'post',
            url: urlStr,
            data: {reelId: rId},
            dataType: 'text',
            async: false,
            success: function (msg) {
                if (!!clazz) {
                    $('.' + clazz).empty();
                    $('.' + clazz).append(msg);
                }
            },
            error: function () {
                alert("load page error, page url is " + urlStr);
            }
        });
    }

    //过滤图片类型
    function fileImgType(obj) {
        var fileType = obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
        if (fileType != '.gif' && fileType != '.png' && fileType != '.jpg' && fileType != '.jpeg') {
            new $.zui.Messager('请上传图片格式的文件', {
                type: 'failed',
                close: false
            }).show();
            $("#icons").val('');
        }
    }

    $(".questionnaire-panel").on("click", ".cancel-btn", function () {
        $(".reel-edit").remove();
    })


    /**
     * 上传图片提交
     */
    $("#saveSubmit").submit(function (event) {
        var icons = $("#icons").val();
        if (icons == "") {
            //alertTip("请先上传图片");
            new $.zui.Messager('请先上传图片', {
                type: 'failed',
                close: false
            }).show();
        }
        event.preventDefault();
        var form = $(this);
        var formData = new FormData(this);
        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: formData,
            mimeType: "multipart/form-data",
            contentType: false,
            cache: false,
            processData: false,
            success: function (msg) {
                var previewArea2 = $(".preview-area").html();
                if (icons != "" && (previewArea2 != "" && previewArea2 != undefined)) {
                    $(".preview-area").html("");//每次上传后清空，只展示一张图片
                    msg = $.parseJSON(msg);
                    $(".preview-area").append(msg.img);
                    $("#icons").val("");
                } else {
                    msg = $.parseJSON(msg);
                    $(".preview-area").append(msg.img);
                    $("#icons").val("");
                }
            },
            error: function () {
                new $.zui.Messager('上传图片失败', {
                    type: 'failed',
                    close: false
                }).show();
            }
        });


    })
    /**
     * 显示题目下拉框
     */
    $(".show-title").on("click", function (e) {
        e.stopPropagation()
        $(this).next("ul").toggle()
    })
    /**
     * 显示题目下拉框
     */
    $(".questionnaire-panel").on("click", ".show-title", function (e) {
        e.stopPropagation()
        $(this).next("ul").toggle()
    })
    $(".agreement>ul>li").on("click", function (e) {
        e.stopPropagation()
        $(this).parent().siblings(".show-title").find("span").text($(this).text()).attr("value", $(this).attr("value"));
        $(this).parent().toggle()
    })
    /**
     * 下拉菜单选择题目
     */
    $(".questionnaire-panel").on("click", ".selectType>ul>li", function (e) {
        e.stopPropagation()
        $(this).parent().siblings(".show-title").find("span").text($(this).text()).attr("value", $(this).attr("value"));
        $(this).parent().toggle()
        // 题目选项动态隐藏
        var questionType = parseInt($(this).attr("value"))
        switch (questionType) {
            case 1 : //单选
                $(".reel-edit .btn-add__other-option").hide();
                $(".reel-edit .all-option").show();
                $(".reel-edit .create-option").show()
                $(".reel-edit .create-option .btn-add-option").show()
                $(".reel-edit .box-single__line").hide();
                $(".reel-edit .fieldset-other").hide();
                break;
            case 2 : //多选
                $(".reel-edit .create-option .btn-add-option").show()
                $(".reel-edit .btn-add__other-option").show();
                $(".reel-edit .all-option").show();
                $(".reel-edit .create-option").show()
                $(".reel-edit .box-single__line").hide();
                $(".reel-edit .fieldset-other").show();
                break;
            case 4: //单行文本
                $(".reel-edit .btn-add__other-option").hide();
                $(".reel-edit .all-option").hide();
                $(".reel-edit .create-option").hide()
                $(".reel-edit .box-single__line").show()
                $(".reel-edit .fieldset-other").hide();
                break;
            default :
                $(".reel-edit .btn-add__other-option").hide();
                $(".reel-edit .all-option").show();
                $(".reel-edit .create-option").show()
                $(".reel-edit .box-single__line").hide();
                $(".reel-edit .fieldset-other").hide();
                break; //当选择处理
        }
    })
    /**
     * 隐藏下拉菜单
     */
    $(document).on("click", function () {
        $(".selectType>ul").css("display", "none")
    })

    /**
     * 隐藏对话框
     */
    function saveDialog(path) {
        var previewArea = $(".preview-area").html();
        if (previewArea == "") {
            new $.zui.Messager('请先上传图片', {
                type: 'failed',
                close: false
            }).show();
        } else {
            var src = $("form").find(".preview-area").find("img")[0].src
            if (src) {
                addImage(null, src)
                $(".preview-area").html("");
            }
            $("#dialogUpload").modal("hide", "fit")
        }
    }

    /**
     * 超链接
     */
    $(".url-confirm").click(function () {
        var hyperlink = $("#hyperlink").val();
        //var agreement = $("#agreement option:selected").text();
        var agreement = $(".agreement .show-title span").text();
        var htmla = "<a href='" + agreement + hyperlink + "' target='_blank'  onclick=\"stopHtml(event)\">" + agreement + hyperlink + "</a>&nbsp;";
        //document.execCommand("insertHTML", false,htmla)
        var html = targetElement.innerHTML
        var text = html.replace("<i style=\"visibility:hidden; width:0; height: 0; display:inline-block; vertical-align: middle;\">$#</i>", htmla);
        targetElement.innerHTML = text
        $("#hyperlink").val("");
        $("#dialogUrl").modal("hide", "fit");
    })

    /**
     * 视频上传
     */
    $(".video-confirm").click(function () {
        var videoFile = $("#videoFile").val();
        if (videoFile == "") {
            new $.zui.Messager('请先上传视频', {
                type: 'failed',
                close: false
            }).show();
            return false;
        }
        var videoFiles = new FormData();
        videoFiles.append("videoFile", $("#videoFile")[0].files[0]);
        $.ajax({
            type: 'post',
            url: '/questionnaire/reel/uploadVdieo',
            cache: false,
            data: videoFiles,
            processData: false,
            contentType: false,
            success: function (msg) {

                var videoHtml = "<img class=\"videoImage\" src=\"/questionnaire/template/image/icon/icon-video.png\"/>" + msg.video + "&nbsp;"
                var html = targetElement.innerHTML
                var text = html.replace("<i style=\"visibility:hidden; width:0; height: 0; display:inline-block; vertical-align: middle;\">$#</i>", videoHtml);
                targetElement.innerHTML = text
                $("#videoFile").val("");
                $("#dialogVideo").modal("hide", "fit");


            },
            error: function () {
                new $.zui.Messager('上传视频失败', {
                    type: 'failed',
                    close: false
                }).show();
            }
        });
        return false;


    })


    //过滤视频类型
    function fileVideoType(obj) {
        var fileType = obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
        if (fileType != '.mp4' && fileType != '.mov' && fileType != '.mkv') {//火狐和谷歌不安装插件情况下，火狐只支持MP4，谷只支持MP4、mov、mkv种格式
            new $.zui.Messager('请上传视频格式的文件', {
                type: 'failed',
                close: false
            }).show();
            $("#videoFile").val('');
        }
    }

    function stopHtml(e) {
        e.stopPropagation()
    }


</script>
<%--<script src="<%=path %>/template/jquery/jquery-1.11.0.min.js"></script>--%>

</body>
</html>
