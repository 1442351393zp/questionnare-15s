
var selectedText = ""
var targetElement = ""
var moveStart = "";
var word=null;

/**
 * 编辑内容
 * @param ev
 */
function editContent(ev) {
    ev = ev || window.event
    var target = ev.target
    //去掉页面上的编辑工具
    // var tool = document.getElementsByClassName("tool")[0]
    // if (tool) {
    //     tool.parentNode.removeChild(tool)
    // }

    if (target.dataset.optionType === "SINGLE") {
        return false
    }

    if(target.localName != "b"){
        var parent = target.parentNode
        target.style.position = "relative"
        var top = target.offsetTop
        var left = target.offsetLeft

        //编辑元素类型
        var editType = target.dataset.type
        var tool = parent.getElementsByClassName("tool")[0]
        if (!tool) {
            tool = createTool(editType)
            tool.style.top = (top - 28) + "px"
            tool.style.left = left + "px"
            tool.style.background = "#fff"
            parent.insertBefore(tool,target)
        }
    }else{
        var parent = target.parentElement
        parent.style.position = "relative"
        var top = parent.offsetTop
        var left = parent.offsetLeft

        //编辑元素类型
        var editType = parent.dataset.type
        var tool = parent.getElementsByClassName("tool")[0]
        if (!tool) {
            tool = createTool(editType)
            tool.style.top = (top - 28) + "px"
            tool.style.left = left + "px"
            tool.style.background = "#fff"
            parent.parentNode.insertBefore(tool,parent)
        }
    }
}

/**
 * 创建编辑工具
 * @returns {jQuery|HTMLElement}
 */
function createTool(type) {

    moveStart = window.getSelection().anchorOffset
    // console.log(window.getSelection())
    var element = document.createElement("div")
    element.className ="tool"
    if (type === "content") {
        element.innerHTML = "   <button type=\"button\" title=\"加粗\" class=\"tool-strong\" onclick='boldFont(event)'></button>\n" +
                            "   <button type=\"button\" title=\"插入图片\" class=\"tool-image\"  onclick='addImage(event)'></button>\n"+
                            "   <button type=\"button\" title=\"插入填空\" class=\"tool-other\"  onclick='addOther(event)'></button>\n" +
                            "   <button type=\"button\" title=\"插入超链接\" class=\"tool-link\"   onclick='addLink(event)'></button>\n" +
                            "   <button type=\"button\" title=\"插入视频\" class=\"tool-video\"  onclick='addvideo(event)'></button>\n"
    } else if(type === "endLanguage"){
        element.innerHTML = "   <button type=\"button\" title=\"加粗\" class=\"tool-strong\" onclick='boldFont(event)'></button>\n" +
                            "   <button type=\"button\" title=\"插入图片\" class=\"tool-image\"  onclick='addImage(event)'></button>\n" +
                            "   <button type=\"button\" title=\"插入超链接\"  class=\"tool-link\"   onclick='addLink()'></button>\n"
    }else if(type === "title"){
        element.innerHTML = "   <button type=\"button\" title=\"加粗\" class=\"tool-strong\" onclick='boldFont(event)'></button>\n" +
                            "   <button type=\"button\" title=\"插入超链接\" class=\"tool-link\"   onclick='addLink()'></button>\n"
    }else if(type === "hello"){
        element.innerHTML = "   <button type=\"button\" title=\"加粗\" class=\"tool-strong\" onclick='boldFont(event)'></button>\n" +
                             "   <button type=\"button\" title=\"插入超链接\" class=\"tool-link\"   onclick='addLink()'></button>\n"
    }else{
        return
    }
    return element
}

/**
 * 失去焦点取消编辑
 * @param ev
 */
function cancelEdit(ev) {
    ev = ev || window.event
    var target = ev.target
    var parent = target.parentNode
    //去掉页面上的编辑工具
    var tool = parent.getElementsByClassName("tool")[0]
    if (tool) {
        setTimeout(function () {
            tool.parentNode.removeChild(tool)
        },300)
    }
    target.style.position = ""
}

/**
 * 鼠标选中要加粗的文本
 */
function getSelectionText() {
    word=""
    word = window.getSelection ? window.getSelection():document.selection.createRange().text
    selectedText = word.toString()
    // var txt = word.baseNode ? word.baseNode.data : word.anchorNode.data
    // if(txt == undefined){
    //     return
    // }
    // var start = word.anchorOffset
    // var end = word.focusOffset
    // if (txt.length > 0) {
    //     if (start > end) {
    //         selectedText = txt.slice(end,start)
    //     } else {
    //         selectedText = txt.slice(start,end)
    //     }
    // }
}

/**
 * 选中字体加粗
 * @param ev
 */
function boldFont(ev) {
    ev = ev || window.event
    var target = ev.target
    var parent = target.parentNode;
    var html = parent.nextElementSibling.innerHTML
    //有选中文字，部分加粗
    if(selectedText.length>0){
        document.execCommand("bold")
    }
}



/**
 * 编辑区域添加图片
 * @param ev
 */
function addImage(ev,path) {
    var spanHtml = "<i style=\"visibility:hidden; width:0; height: 0; display:inline-block; vertical-align: middle;\">$#</i>"
    if (ev) {
        ev = ev || window.event
        var target = ev.target
        var parent = target.parentNode
        //记录下目标元素
        targetElement = parent.nextElementSibling
        //打開對話框
        $("#dialogUpload").modal()
        document.execCommand("insertHTML", false,spanHtml)
    } else {
        var html = targetElement.innerHTML
        var imgHtml= "<img class='icon-image' width='50%' style='background-image: url("+path+");'" +
            "data-toggle='popover' src='"+path+"' data-content='' />"
        var text = html.replace(spanHtml,imgHtml);
        targetElement.innerHTML = text
            // document.execCommand("insertHTML", false,
            //     "<img class='icon-image' width='50%' style='background-image: url("+path+");'" +
            //     "data-toggle='popover' src='"+path+"' data-content='' />")
        // }


    }
}
/**
 * 显示图片上传对话框
 */
function dialogUploadImage() {
    $("#dialogUpload").modal()
}

/**
 * 隐藏对话框
 */
function hiddenDialog(){
    var spanHtml = "<i style=\"visibility:hidden; width:0; height: 0; display:inline-block; vertical-align: middle;\">$#</i>"
    var text = targetElement.innerHTML
    text = text.replace(spanHtml,"");
    targetElement.innerHTML = text
	var previewArea = $(".preview-area").html();
	
	if(previewArea !="" && previewArea != undefined){
		if (confirm("取消后图片要重新上传，确定要关闭窗口吗？")) {
		   $("#dialogUpload").modal("hide","fit")
		   $(".preview-area img").remove();
		}
	}else{
		 $("#dialogUpload").modal("hide","fit")
		 $("#icons").val("");//点击上传图片没有上传服务器上，清空上传input
	}
}

/**
 * 显示上传的图片
 * @param ev
 * <div class='tooltip-arrow'><div class='tooltip-inner'></div></div>
 */
function showUploadImage(ev,path) {
    ev = ev || window.event
    var target = ev.target
    /**
     * 首先初始化
     */
    $('[data-toggle="popover"]').popover({
        placement:"bottom",
        content:"<img src='"+path+"'/>",
        html:true,
        template:"<div class='popover'><div class='arrow'></div><div class='popover-content'></div></div>",
        show:true
    })
    /**
     * 显示提示框
     */
    $(target).popover('show')
}

/**
 * 隐藏提示框
 */
function hiddenUploadImage() {
    $(".icon-image").popover('hide')
}
// 添加填空
function addOther(ev){
    ev = ev || window.event
    var target = ev.target
    var parent = target.parentNode
    //记录下目标元素
    targetElement = parent.nextElementSibling;
    targetElement.focus()
    document.execCommand("insertHTML",false,"<input type='text' value='' class='completions' maxlength='20'/>")
}
// 点击填空
function completion(e,self){
    e.stopPropagation();
    var content = '<div>\n'+
                    '<span>文本验证</span>\n'+
                    '<select>\n'+
                        '<option>不限</option>\n'+
                        '<option>数字</option>\n'+
                        '<option>日期</option>\n'+
                        '</select>\n'+
                    '</div>\n'+
                    '<div>\n'+
                    '<span>最多填写</span>\n'+
                    '<input type="text" class="number"/>\n'+
                    '<i>字</i>\n'+
                    '</div>\n'+
                    '<div>\n'+
                    '<span>必填</span>\n'+
                    '<input type="checkbox" class="checkBtn"/>\n'+
                    '</div>\n'+
                    '<div class="bottom-btn">\n'+
                    '<button type="button">保存</button>\n'+
                    '<button type="button" class="closeBtn">取消</button>\n'+
                    '</div>'

    $(self).popover({
        placement:"bottom",
        title:'<h1>填空设置</h1>',
        content:content,
        html:true,
        template:"<div class='popover completionBox'><div class='arrow'></div><div class='popover-title'></div><div class='popover-content'></div></div>",
        show:true
    })
    $(self).popover("toggle")
}

/**
 *打开超链接对话框
 */
function addLink(ev){
    //插入一个特殊字符
    document.execCommand("insertHTML", false,"<i style=\"visibility:hidden; width:0; height: 0; display:inline-block; vertical-align: middle;\">$#</i>")
	  ev = ev || window.event
      var target = ev.target
      var parent = target.parentNode
      //记录下目标元素
      targetElement = parent.nextElementSibling
    $("#dialogUrl").modal({"backdrop":"static"})
}
// 关闭超链接对话框
$(".url-cancel").on("click",function(){
    var spanHtml = "<i style=\"visibility:hidden; width:0; height: 0; display:inline-block; vertical-align: middle;\">$#</i>"
    var text = targetElement.innerHTML
    text = text.replace(spanHtml,"");
    targetElement.innerHTML = text
    $("#dialogUrl").modal("hide")
})
// 打开视频
function addvideo(ev){
	 ev = ev || window.event
     var target = ev.target
     var parent = target.parentNode
     //记录下目标元素
     targetElement = parent.nextElementSibling
    $("#dialogVideo").modal({"backdrop":"static"})
    document.execCommand("insertHTML", false,"<i style=\"visibility:hidden; width:0; height: 0; display:inline-block; vertical-align: middle;\">$#</i>")
}
// 关闭视频
$(".video-cancel").on("click",function(){
    var spanHtml = "<i style=\"visibility:hidden; width:0; height: 0; display:inline-block; vertical-align: middle;\">$#</i>"
    var text = targetElement.innerHTML
    text = text.replace(spanHtml,"");
    targetElement.innerHTML = text
	var videoFile = $("#videoFile").val();
	$("#dialogVideo").modal("hide")
	$("#videoFile").val("");
})
// 点击关闭填空设置
$(".questionnaire-panel").on("click",".closeBtn",function(e){
    e.stopPropagation()
    console.log(111)
    $('span[data-toggle="popover"]').popover('hide')
})