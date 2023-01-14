var targetEle = null;
// 题目编号
var topicIndex = ""

var deleteOptionsIdArr = new Array();//删除的选项数据
/**
 * 题目的编辑操作
 * @param ev
 */
function editSubject(ev) {
    ev = ev || window.event
    var target = ev.target
    var elWrapSubject = target.parentNode.parentNode
    var panelPage = elWrapSubject.parentNode
    var elForm = ''
}

/**
 * 删除问卷题目
 * @param ev
 */
function deleteQuestion(ev, subjectId, self) {
    $.ajax({
        type: 'get',
        url: "/questionnaire/reel/delSubject",
        data: {
            "subjectId": subjectId
        },
        success: function (result) {
            alertTip("移除成功");
            // 删除成功删除右边问卷大纲显示
            $('.outline li[subjectid=' + subjectId + ']').remove();
            var sortItem = $(".outline li.sortItem")
            for (var i = 0; i < sortItem.length; i++) {
                $(sortItem[i]).find("span b").text(parseInt(i + 1) + ".")
            }
        }
    })
    ev = ev || window.event
    var target = ev.target
    ev.stopPropagation();
    if (target.className.indexOf("wrap-item") === -1) {
        target = target.parentNode
    }
    var parent = target.parentNode
    var questionItem = parent.parentNode
    if (questionItem.className.indexOf("question-item-hidden") === -1) {
        questionItem.classList.add("question-item-hidden")
    }
    // questionItem.style.opacity = "0"
    setTimeout(function () {
        questionItem.parentNode.removeChild(questionItem)
    }, 500)

}

/**
 * 动画显示编辑侧边栏
 * @param ev
 */
function showSlider(ev) {
    ev = ev || window.event
    var target = ev.target
    var slider = target.getElementsByClassName("slider-list")[0]
    // $(slider).animate({
    //     width:"52px"
    // },5).css({
    //     display:"block"
    // })
    if (slider.className.indexOf("increment-width") === -1) {
        slider.classList.add("increment-width")
        var sliderHeight = target.getElementsByClassName("question-item-wrap")[0].clientHeight
        if (sliderHeight < 200) {
            slider.style.height = 200 + "px"
        } else {
            slider.style.height = sliderHeight + "px"
        }
    }
}

/**
 * 动画隐藏编辑侧边栏slider
 * @param ev
 */
function hideSlider(ev) {
    ev = ev || window.event
    var target = ev.target
    var slider = target.getElementsByClassName("slider-list")[0]
    if (slider.className.indexOf("increment-width") > -1) {
        slider.classList.remove("increment-width")
    }
}

/**
 * 编辑题目
 * @param ev
 * @param subjectId
 * @param rId
 * @param self
 */
function editQuestion(ev, subjectId, rId, self) {
    /**
     * 切换的时候，将没有编辑的内容
     * @type {Element}
     */
    var msg = "";
    $.ajax({
        type: 'get',
        url: "/questionnaire/reel/querySubjectId",
        async: false,
        data: {
            "subjectId": subjectId
        },
        success: function (result) {
            msg = result;
        }
    })

    //msg = JSON.stringify(msg);
    // topicIndex = $(self).find(".question-item-title>span:first-child").text();
    topicIndex = $(self).find(".question-item-title .xuhao").text();
    // 第一页
    if ($(self).parents(".panel-page").attr("data-pageindex") == 0) {
        if ($(self).parents(".panel-page").find(".wrap-edit-content").length > 2) {
            alertTip("当前处于编辑状态")
            return false
        }
    } else {
        if ($(self).parents(".panel-page").find(".wrap-edit-content").length > 0) {
            alertTip("当前处于编辑状态")
            return false
        }
    }
    var topic = msg.topic;//题目
    var remark = msg.remark;//备注
    var subjectType = msg.subjectType;//题的类型
    var must = msg.must;//必选

    var wrap = document.getElementsByClassName("wrap-edit-content")[0]
    ev = ev || window.event
    var target = ev.currentTarget
    if (target.className.indexOf("question-item") === -1) {
        return
    }

    //题目类型
    var type = target.dataset.type
    //题目父容器
    var wrapQuestion = $("<div class='wrap-edit-content reel-edit'></div>")
    // $(self).addClass("reel-edit")
    //题目
    var question = $("<div class=\"question\"></div>")
    wrapQuestion.append(question)
    //问题题目
    var title = $(`<div class="fieldset">
                    <div class="label">题目:</div>
                       <div class="editable" 
                            contenteditable="true" 
                            data-type="content"
                            onclick="editContent(event)" 
                            id="topic" 
                            onblur="cancelEdit(event)" 
                            onmouseup="getSelectionText()">
                           ${topic}
                       </div>
                    </div>`)
    question.append(title)
    //备注
    var remark = $(" <!--备注-->" +
        " <div class=\"fieldset\">\n" +
        "   <div class=\"label\">备注:</div>\n" +
        "   <div class=\"editable remark\" contenteditable=\"true\" data-type=\"content\"\n" +
        "   onclick=\"editContent(event)\" id=\"remark\" onblur=\"cancelEdit(event)\" onmouseup=\"getSelectionText()\">" + remark + "</div>\n" +
        "</div>")
    question.append(remark)

    var checkboxMust = "";//必选
    var tempHtml = ""
    if (subjectType === "1") {        // 单选
        tempHtml = `<span value="1">单选题</span>`
    } else if (subjectType === "2") {  //多选
        tempHtml = `<span value="2">多选题</span>`
    } else if (subjectType === "4") {  //单行文本
        tempHtml = `<span value="4">单行文本题</span>`
    } else {
        tempHtml = `<span value="1">单选题</span>`
    }
    var questionType = $(`<!--题目类型切换-->
            <div class="fieldset">
                  <label for="question-type"></label>
                  <div class="selectType">
                       <div class="show-title">`
        + tempHtml +
        `<i></i>
                       </div>
                       <ul>
                           <li value="1">单选题</li>
                           <li value="2">多选题</li>
                           <li value="4">单行文本题</li>
                       </ul>
                  </div>
            </div>`)
    /*** 必填*/
    var mustHtml = ""
    if (must == "0") {
        mustHtml = `<input type="checkbox" checked="checked" id="must"/>`;
    } else {
        mustHtml = `<input type="checkbox" id="must"/>`;
    }
    checkboxMust = `<div class="required-item checkbox-primary">`
        + mustHtml +
        `<label>必填</label>
                    </div>`;
    question.append(questionType);
    questionType.append(checkboxMust);
    //循环添加题目列表
    var optionsArr = msg.optionsList;

    /***
     * 添加题目选项
     * @type {string}
     */
    var options = "";
    for (var i = 0; i < optionsArr.length; i++) {
        var questionOption = ""
        // 选项多选判断
        switch (parseInt(optionsArr[i].isMultipleChoice)) {
            case 0:   // 【多选】其他选项
                questionOption = `<!--  选项一 -->
                               <div class="fieldset fieldset-other">
                                   <div class="question-option-label add-options"></div>
                                   <div class="other" style="display: flex;align-items: center;width:calc(100%);">
                                      <div class="" style="width:30px;">其他</div>
                                      <div class="editable option optionIpt"
                                            contenteditable="true" 
                                            data-placeholder="选项" 
                                            data-type="content"
                                            data-option-type="OTHER"
                                            data-option-id="${optionsArr[i].optionsId}"
                                            onclick="editContent(event)" 
                                            id="options" 
                                            onblur="cancelEdit(event)" 
                                            onmouseup="getSelectionText()" 
                                            style="border:none;border-bottom:1px solid #CCC; width:calc(100% - 30px);outline:none;">
                                      </div>
                                   </div>
                                   <span class="btn-delete-option"></span>
                               </div>`;
                break;
            case 1:       // 正常
                questionOption = `<div class="fieldset">
                                      <div class="question-option-label"></div>
                                      <div class="editable option optionIpt"
                                           contenteditable="true"
                                           data-placeholder="选项" 
                                           data-option-type="NORMAL"
                                           data-option-id="${optionsArr[i].optionsId}"
                                           data-type="content"
                                           onclick="editContent(event)"
                                           onblur="cancelEdit(event)" 
                                           onmouseup="getSelectionText()">
                                           ${optionsArr[i].options}
                                      </div>
                                      <input class="optionsIdArr" type="hidden" value="${optionsArr[i].optionsId}"/>
                                      <span class="btn-delete-option"></span>
                                </div>`;
                break;
            case 2:; // 单行文本框
                break;
            case 3:
                questionOption = `<div class="fieldset">
                                      <div class="question-option-label"></div>
                                      <div class="editable option optionIpt"
                                           contenteditable="true"
                                           data-placeholder="选项" 
                                           data-option-type="NORMAL"
                                           data-option-id="${optionsArr[i].optionsId}"
                                           data-type="content"
                                           onclick="editContent(event)"
                                           onblur="cancelEdit(event)" 
                                           onmouseup="getSelectionText()">
                                           ${optionsArr[i].options}
                                      </div>
                                      <input class="optionsIdArr" type="hidden" value="${optionsArr[i].optionsId}"/>
                                      <span class="btn-delete-option"></span>
                                </div>`; // 正常選項
                break;
            default:
                questionOption = '';
                break;
        }
        options += questionOption;
    }

    /*** 选项 ***/
    var allOption = '<div class="all-option">' + options + '</div>';
    question.append(allOption);

    /**判断是否显示其它选项*/
    var otherIsShow = $(`<div class="create-option">
                           <div class="fieldset">
                                <div class="label"></div>
                                <div class="btn-add-option">新建选项</div>
                                <div class="btn-add__other-option" style="display: none;">其他选项</div>
                           </div>
                        </div>   
                     </div>`)
    /**
     * 选项编辑
     */
    switch (parseInt(subjectType)) {
        case 1:   // 单选
            otherIsShow = $(`<div class="create-option">
                                   <div class="fieldset">
                                        <div class="label"></div>
                                        <div class="btn-add-option">新建选项</div>
                                        <div class="btn-add__other-option" style="display: none;">其他选项</div>
                                   </div>
                                </div>   
                             </div>
                             <div class="fieldset box-single__line" style="display: none;">
                                <div class="label"></div>
                                <div class="editable optionIpt single-line-text" 
                                    id="single-line-text" 
                                    contenteditable="true" 
                                    data-placeholder="单行文本" 
                                    data-type="content" 
                                    data-option-type="SINGLE" 
                                    onclick="editContent(event)" 
                                    onblur="cancelEdit(event)" 
                                    onmouseup="getSelectionText()">
                                </div>
                             </div>`);
            break;
        case 2:
            otherIsShow = $(`<div class="create-option">
                                     <div class="fieldset">
                                        <div class="label"></div>
                                        <div class="btn-add-option">新建选项</div>
                                        <div class="btn-add__other-option">其他选项</div>
                                     </div>
                                 </div>
                                <div class="fieldset box-single__line" style="display: none;">
                                        <div class="label"></div>
                                        <div class="editable optionIpt single-line-text" 
                                            id="single-line-text" 
                                            contenteditable="true" 
                                            data-placeholder="单行文本" 
                                            data-type="content" 
                                            data-option-type="SINGLE" 
                                            onclick="editContent(event)" 
                                            onblur="cancelEdit(event)" 
                                            onmouseup="getSelectionText()">
                                        </div>
                                    </div>
                               </div>`);
            break;    // 多选
        case 4:
            otherIsShow = $(`<div class="create-option" style="display: none;">
                                     <div class="fieldset">
                                        <div class="label"></div>
                                        <div class="btn-add-option">新建选项</div>
                                        <div class="btn-add__other-option" >其他选项</div>
                                     </div>
                                 </div>
                                 <div class="fieldset box-single__line" >
                                    <div class="label"></div>
                                    <div class="editable optionIpt single-line-text" 
                                        id="single-line-text" 
                                        contenteditable="true" 
                                        data-placeholder="单行文本" 
                                        data-type="content" 
                                        data-option-type="SINGLE" 
                                        onclick="editContent(event)" 
                                        onblur="cancelEdit(event)" 
                                        onmouseup="getSelectionText()">
                                    </div>
                                 </div>`);
            break;    // 单行文本框
        default:;
            break;
    }
    // 新建选项 和 其它选项
    question.append(otherIsShow)
    //其他
    var other = $("<div class=\"fieldset\">\n" +
        "  <div></div>\n" +
        "  <div class=\"add-other-option\">添加[其他]项</div>\n" +
        "  <div class=\"batch-update\">批量修改</div>\n" +
        "</div>")
    // question.append(other)
    //展开更高选设置
    var higher = $("<div class=\"fieldset\">\n" +
        "   <div class=\"open-higher-set\">\n" +
        "      <span class=\"\">展开高级设置</span>\n" +
        "   </div>\n" +
        "  </div>")
    // question.append(higher)
    //设置选项
    var seniorOption = $("<div class=\"fieldset\">\n" +
        "  <div class=\"senior-option\">\n" +
        "      <input type=\"checkbox\" name=\"\" value=\"\"/>\n" +
        "      <label for=\"\">选项随即顺序</label>\n" +
        "  </div>\n" +
        "  <div class=\"senior-option\">\n" +
        "      <input type=\"checkbox\" name=\"\" value=\"\"/>\n" +
        "      <label for=\"\">显示投票结果</label>\n" +
        "  </div>\n" +
        "  <div class=\"senior-option\">\n" +
        "       <input type=\"checkbox\" name=\"\" value=\"\"/>\n" +
        "       <label for=\"\">设置为测评题目</label>\n" +
        "  </div>\n" +
        "  <div class=\"senior-option\">\n" +
        "        <input type=\"checkbox\" name=\"\" value=\"\"/>\n" +
        "        <label for=\"\">选项引用</label>\n" +
        "  </div>\n" +
        "</div>")
    // question.append(seniorOption)
    // 提交按钮
    var submit = $("<div class=\"fieldset-submit\">\n" +
        "   <div></div>\n" +
        "   <button type=\"button\" class=\"cancel-btn\" onclick='cancelEditQuestion(event)'>取消</button>\n" +
        "   <button class=\"button-active\" id=\"saveBtn\" index-data-subject='" + subjectId + "' index-data-rId='" + rId + "'>确定</button>\n" +
        " </div>")
    question.append(submit)
    targetEle = target.parentNode.replaceChild(wrapQuestion[0], target)
}

//删除选项
$(".questionnaire-panel").on("click", ".btn-delete-option", function () {
    if ($(".all-option").children().length - 1 <= 0) {
        alertTip("不能删除当前行");
        return false;
    } else {
        deleteOptionsIdArr.push($(this).siblings(".optionsIdArr").val());
        $(this).parent().remove();
    }
})

/**
 * 新增保存
 */
$(".panel").on("click", "#saveBtn", function () {
    // debugger
    var self = $(this);
    var subjectId = $(this).attr("index-data-subject");
    var rId = $(this).attr("index-data-rId");
    var topic = $("#topic").html();
    if (topic == "") {
        alertTip("题目不能为空！");
        return false;
    }
    var remark = $("#remark").html();
    //var subjectType = $("#subjectType").val();
    //获取下拉框选中的值【当前题目类型】
    var subjectType = $(".selectType .show-title span").attr("value")
    var must = $("#must:checkbox").is(":checked");//必填
    if (must) {
        must = "0";//0:选中
    } else {
        must = "1";//1:未选中
    }
    /**最终传给后台的选项**/
    var optionsArr = new Array();
    var optionsIdArr = new Array();
    var jsonOptions = new Array();
    var optionsArr = new Array()
    var arrOptionIps = $(this).parents(".question").find(".optionIpt")
    console.log("arrOptionIps:",arrOptionIps)
    console.log("arrOptionIps.length:",arrOptionIps.length)
    for (var i = 0; i < arrOptionIps.length; i++) {
        var objectOptions = new Object();
        //選項的內容
        var options = $(arrOptionIps[i]).html().toString().replace(/\s*|\s$/g,"");
        var type = arrOptionIps[i].dataset.optionType;
        var optionsId = arrOptionIps[i].dataset.optionId;
        var optionsType = ""
        /**判斷題目選項 */
        switch (type) {
            case "SINGLE": optionsType = 2; break;　　//文本框
            case "NORMAL": optionsType = 1; break;   //正常选项
            case "OTHER" : optionsType = 0; break;   //其他选项
        }

        //console.log(`arrOptionIps[${i}]`,arrOptionIps[i],"options:",options,"type:",type,"optionsType:",optionsType)
        objectOptions.options = options;
        objectOptions.optionsId = optionsId;
        objectOptions.optionsType = optionsType;
        // jsonOptions.push(objectOptions);
        //当前题目类型
        switch (parseInt(subjectType)) {
            case 1: // 单选题【文本选项】和【其他选项】过滤
                if (type === "NORMAL") {
                    // optionsArr.push(options+"&&&&"+optionsType)
                    jsonOptions.push({options:options,optionsType:1,optionsId:optionsId ? optionsId:"",isMultipleChoice:optionsType});
                }
                break;
            case 2://多选题 【单行文本】过滤
                if (type !== "SINGLE") {
                    // optionsArr.push(options+"&&&&"+optionsType)
                    jsonOptions.push({options:options,optionsType:2,optionsId:optionsId ? optionsId:"",isMultipleChoice:optionsType});
                }
                break;
            case 4: //文本题 【正常选项】和【其他选项】过滤
                if (type === "SINGLE") {
                    // optionsArr.push(options+"&&&&"+optionsType)
                    jsonOptions.push({options:options,optionsType:4,optionsId:optionsId ? optionsId:"",isMultipleChoice:optionsType});
                }
                break;
        }
    }
    // console.log("jsonOptions:",jsonOptions)
    // console.log("optionsArr:",optionsArr)
    $.ajax({
        type: 'post',
        url: "/questionnaire/reel/updateSubject",
        data: {
            "rId": rId,
            "subjectId": subjectId,
            "topic": topic,
            "remark": remark,
            "subjectType": subjectType,
            "must": must,
            "jsonOptions": JSON.stringify(jsonOptions),
            "optionsArr": optionsArr,
            "deleteOptionsIdArr": deleteOptionsIdArr
        },
        success: function (result) {
            showSubjectData(result, self);
            alertTip("修改成功");
            $(".outline").empty();
            getDragList(rId)
        }
    })
})

/**
 * 回显题目数据
 * @param result
 * @param self
 */
function showSubjectData(result, self) {
    //var json = JSON.stringify(result);
    var subjectId = result.subjectId;
    var topic = result.topic;
    var remark = result.remark;
    var must = result.must;
    var questionHtml = ""
    if (must == "0") {
        questionHtml += "<div class=\"question-item clearfix\" onclick=\"editQuestion(event,'" + subjectId + "','" + $("#rId").val() + "',this)\" onmouseenter=\"showSlider(event)\" onmouseleave=\"hideSlider(event,'" + subjectId + "')\">\n" +
            "            <div class=\"question-item-wrap\">\n" +
            "                <div class=\"question-item-title\">\n"+
            "                    <span class='biaoji'>*</span>\n" +
            "                    <span class='xuhao'>" + topicIndex + "</span>\n" +
            "                    <span class='item-content-title'>" + topic + "</span>\n" +
            "                </div>\n"+
            "                    <div class=\"question-desc\">" + remark + "</div>\n"
    } else {
        questionHtml += "<div class=\"question-item clearfix\" onclick=\"editQuestion(event,'" + subjectId + "','" + $("#rId").val() + "',this)\" onmouseenter=\"showSlider(event)\" onmouseleave=\"hideSlider(event,'" + subjectId + "')\">\n" +
            "            <div class=\"question-item-wrap\">\n" +
            "                <div class=\"question-item-title\">\n" +
            "                    <span>" + topicIndex + "</span>\n" +
            "                    <span>" + topic + "</span>\n" +
            "                </div>\n"+
            "                <div class=\"question-desc\">" + remark + "</div>\n"
    }

    var subjectType = result.subjectType;//单选1 多选2
    var questionOptionsHtml = "<div class=\"question-item-content\">"
    var optionsList = result.optionsList;
    for (var j = 0; j < optionsList.length; j++) {
        if (subjectType == 1) {//单选
            questionOptionsHtml += " <div class=\"radio-primary radio-btn\">\n" +
                                    "   <input type=\"radio\" name=\"options1\" value=\"131\">\n" +
                                    "   <label>" + optionsList[j].options + "</label>\n" +
                                    " </div>"
        } else if(subjectType == 2) {  // 多选
            // 其它选项
            if (optionsList[j].isMultipleChoice == 0) {
                var tempHtml = "<div class='checkbox-primary checkbox-btn'>"+
                                "<input type='hidden' name='' value="+optionsList[j].optionsId+"/>"+
                                "<label class='wrap-other'>"+
                                "<div style='width:70px;text-align:center;'>其他：</div>"+
                                "<label style='height: 20px;'>"+optionsList[j].options+"</label>"+
                                "</label>"+
                                "</div>"
                questionOptionsHtml += tempHtml

            } else {   // 渲染选项
                questionOptionsHtml += "<div class='checkbox-primary checkbox-btn'>"+
                                        "<input type='checkbox' name='options4' value="+optionsList[j].optionsId+"/>"+
                                        "<label>" + optionsList[j].options + "</label>"+
                                        "</div>"
            }
        } else {
            // questionOptionsHtml += "<div class=\"checkbox-primary checkbox-btn\">\n" +
            //                        "   <input type=\"checkbox\" name=\"options4\" value=\"139\">\n" +
            //                        "  <label>" + optionsList[j].options + "</label>\n" +
            //                        "</div>"
            questionOptionsHtml+= "<div class=\"fieldset box-single__line\">"+
                                        "<div class=\"label\"></div>"+
                                        "<div class=\"editable optionIpt single-line-text\" " +
                                            "id=\"single-line-text\" " +
                                            "data-placeholder=\"单行文本\"" +
                                            "data-type=\"content\"" +
                                            "data-option-type=\"SINGLE\">"+
                                        "</div>"+
                                    "</div>"

        }
    }
    questionHtml += questionOptionsHtml += "</div> </div>"
    questionHtml += " <div class=\"slider-list\">\n" +
        "                <div class=\"wrap-item\" onclick=\"editQuestion(event,'" + subjectId + "','" + $("#rId").val() + "',this)\"><div></div></div>\n" +
        "                <div class=\"wrap-item\" style=\"display:none;\"><div></div></div>\n" +
        "                 <div class=\"wrap-item\" style=\"display:none;\"><div></div></div>\n" +
        "                <div class=\"wrap-item\"  onclick=\"deleteQuestion(event,'" + subjectId + "',this)\"><div></div></div>\n" +
        "                 <div class=\"wrap-item\" onclick=\"addCollect(event,'" + subjectId + "',this)\"><div></div></div>\n" +
        "            </div>\n" +
        "        </div>" +
        "</div>"
    self.parents(".panel-page")[0].replaceChild($(questionHtml)[0], self.parents(".wrap-edit-content")[0])
}


$(".panel-page").on("click", ".cancel-btn", function () {
    $(".reel-edit").remove();
})

/**
 * 取消编辑题目.
 * @param ev
 */
function cancelEditQuestion(ev) {
    ev = ev || event
    var target = ev.target
    var parent = target.parentNode
    var wrapEditContent = parent.parentNode.parentNode
    wrapEditContent.parentNode.replaceChild(targetEle, wrapEditContent)
}

/**
 * 拷贝题
 * @param rId
 * @param pageId
 * @param subjectId
 * @returns
 */
function copyQuestion(ev, subjectId) {
    ev.stopPropagation();
    $.ajax({
        type: 'post',
        url: "/questionnaire/reel/copySubject",
        data: {
            "subjectId": subjectId
        },
        success: function (result) {
        }
    })

}

/**
 * 点击收藏
 */
function addCollect(e, subjectId, self) {
    e.stopPropagation()
    // 获取当前收藏按钮的位置
    var collectTop = $(self).children("div").offset().top;
    var collectleft = $(self).children("div").offset().left;
    if ($(".collectList").hasClass("active")) {
        // 获取当前收藏题库的位置
        var listTop = $(".collect-content").find(".collectSubject .title-item i:last-child").offset().top
        var listLeft = $(".collect-content").find(".collectSubject .title-item i:last-child").offset().left
    } else {
        var listTop = $(".develop-collect").find("span").offset().top
        var listLeft = $(".develop-collect  ").find("span").offset().left
    }
    $(self).append('<span class="collect-icon"></span>').find(".collect-icon:last-child").css({
        "top": collectTop,
        "left": collectleft
    }).animate({
        "top": listTop,
        "left": listLeft,
        "opacity": 0,
    }, 1000, function () {
        $(self).find("span").eq(0).remove()
        $(".collectSubject").find(".title-item i:first-child").css({
            "transform": "scale(1.3)"
        })
        setTimeout(function () {
            $(".collectSubject").find(".title-item i:first-child").css({
                "transform": "scale(1)"
            })
        }, 200)
    })
    //发起收藏请求
    var userId = $("#userId").val();
    var topic = $(self).parent().prev().find(".question-item-title").find("span").eq(1).text();
    var remark = $(self).parent().prev().find(".question-item-title .question-desc").text();
    var options = $(self).parent().prev().find(".question-item-content>div")
    var optionArr = [];
    var subjectType = ""
    for (var i = 0; i < options.length; i++) {
        var option = $(options[i]).find("input").next().text()
        optionArr.push(option)
        if ($(options[i]).hasClass("radio-primary")) {
            subjectType = "1"
        } else {
            subjectType = "2"
        }
    }
    $.ajax({
        url: "/questionnaire/answer/collect",
        method: "post",
        data: JSON.stringify({
            "userId": userId,
            "topic": topic,
            "remark": remark,
            "subjectType": subjectType,
            "optionList": optionArr

        }),
        contentType: "application/json;charset=UTF-8",
        success: function (res) {
            var collectId = res.data
            var text = "<li>"
                + "<span class=\"ticpItem\" title=" + topic + " collectId=" + collectId + ">" + topic + "</span>\n"
                + "<i class=\"deleteItem\" collectId=" + collectId + "></i>\n"
                + "<i class=\"showMess\" collectId=" + collectId + "  data-toggle=\"popover\"></i>\n"
                + "</li>\n"
            $(".collectSubject").find(".collapseContent>ul").prepend(text)
        },
        error: function (res) {
            alert("请求失败")
        }
    })
}

//获取左侧问卷大纲数据
var rId = $(".rId").val()
if (rId != "") {
    getDragList(rId)
}
var options = {
    selector: ".sortItem",
    dragCssClass: "drapItem",
    finish: function (e) {
        var linum = 1;
        var pageNum = 1

        for (var i = 0; i < e.list.length; i++) {
            var item = e.list[i]
            //修改移动之后的排序码
            if ($(item.item[0]).find("span b").text() != "") {
                $(item.item[0]).find("span b").text(linum + ".")
                linum++;
            }
            if ($(item.item[0]).hasClass("pageNum")) {
                $(item.item[0]).text("第" + parseInt(pageNum + 1) + "页")
                pageNum++
            }
        }
        // 移动完成发送排序之后的数据
        var arrList = $(".pageNum,.sortItem")
        var data = []
        var index = 0
        for (var i = 0; i < arrList.length; i++) {
            var itemOption = arrList[i];
            // 分页标签
            if ($(itemOption).hasClass("pageNum")) {
                var option = {"pageId": $(itemOption).attr("pageId"), subjectList: []}
                data.push(option)
                index++
            } else {
                var subjectItem = {"subjectId": $(itemOption).attr("subjectId")}
                data[index - 1].subjectList.push(subjectItem)
            }
        }
        $.ajax({
            url: "/questionnaire/reel/updateDrag",
            method: "post",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                pageDTOList: data,
                rid: $(".pageNum").attr("rId")
            }),
            success: function (res) {
                if (res.code == 0) {
                    alertTip("保存成功")
                    // 获取页的顺序
                    var pageIdArr = new Array();
                    for (var i = 0; i < $(".outline .pageNum").length; i++) {
                        pageIdArr.push($($(".outline .pageNum")[i]).attr("pageid"))
                    }
                    var tabPage = $(".tab-page .tab-page-index");
                    for (var i = 0; i < tabPage.length - 1; i++) {
                        var tabItem = tabPage[i]
                        var clickPage = $(tabPage[i]).attr("onclick");
                        var quesPageString = clickPage.substring(clickPage.indexOf("(") + 1, clickPage.indexOf(")"))
                        var quesArr = quesPageString.split(",")
                        quesArr[2] = "'" + pageIdArr[i] + "'"
                        $(tabItem).attr("onclick", "changeQuestionnairePage(" + quesArr.join() + ")")
                        $(tabItem).find("i").attr("onclick", "deletePage(event,'" + pageIdArr[i] + "')")
                    }

                    $(".tab-page-index.active").click()
                }
            }
        })
    }
}
$(".dragDrop").sortable(options)

//获取问卷大纲列表
function getDragList(rId) {
    $.ajax({
        url: "/questionnaire/reel/getDragList",
        method: "post",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            rid: rId
        }),
        async: false,
        success: function (res) {
            var data = res.data
            var num = 0
            for (var i = 0; i < data.length; i++) {
                var text = '<div class="outline-item">\n'
                if (i == 0) {
                    text += '<div class="pageNum" pageId="' + data[i].pageId + '" rId="' + data[i].rId + '">第' + parseInt(i + 1) + '页</div>\n'
                } else {
                    text += '<div class="sortItem pageNum" pageId="' + data[i].pageId + '" rId="' + data[i].rId + '">第' + parseInt(i + 1) + '页</div>\n'
                }
                text += '<ul>\n'
                for (var k = 0; k < data[i].subjectList.length; k++) {
                    num++
                    var option = data[i].subjectList[k]
                    text += '<li class="sortItem" subjectId="' + option.subjectId + '">\n'
                    if (option.subjectType == 1) {
                        text += '<i class="radio-option"></i>'
                    } else if (option.subjectType == 2) {
                        text += '<i class="checkbox-option"></i>'
                    } else if(option.subjectType == 4) {
                        text += '<i class="single-line__option"></i>'
                    } else {
                        text += '<i class=""></i>'
                    }
                    text += '<span><b>' + num + '.</b><em>' + option.topic + '</em></span>\n'
                        + '</li>\n'
                }
                text += '</ul>\n</div>'
                $(".outline").append(text)
            }

        }
    })
}




/***
 * 新增选项
 */
$(".questionnaire-panel").on("click", ".btn-add-option", function () {
    var input = `<div class="fieldset">
                       <div class="question-option-label add-options"></div>
                       <div class="editable option optionIpt"
                            contenteditable="true" 
                            data-placeholder="选项" 
                            data-option-id=""
                            data-type="content"
                            data-option-type="NORMAL"
                            onclick="editContent(event)" 
                            id="options" 
                            onblur="cancelEdit(event)" 
                            onmouseup="getSelectionText()">
                       </div>
                       <span class="btn-delete-option"></span>
                    </div>`;
    $(".all-option").append(input);
})


/***
 * 新增【其他】选项
 */
$(".questionnaire-panel").on("click", ".btn-add__other-option", function () {
    var input = `<div class="fieldset fieldset-other">
                          <div class="question-option-label add-options"></div>
                          <div class="other" style="display: flex;align-items: center;width:calc(100%);">
                              <div class="" style="width:30px;">其他</div>
                              <!--contenteditable="true" 新增其他選項不能編輯-->
                              <div class="editable option optionIpt"
                                    data-placeholder="选项" 
                                    data-type="content"
                                    data-option-id=""
                                    data-option-type="OTHER"
                                    onclick="editContent(event)" 
                                    id="options" 
                                    onblur="cancelEdit(event)" 
                                    onmouseup="getSelectionText()" 
                                    style="border:none;border-bottom:1px solid #CCC; width:calc(100% - 30px);outline:none;">
                              </div>
                          </div>
                          <span class="btn-delete-option"></span>
                     </div>`;
    $(".all-option").append(input);
})

