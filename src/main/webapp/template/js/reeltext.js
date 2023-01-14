var container = document.createElement('div')
var resultArea = document.createElement('div')
var leftEditorArea = document.createElement('div')
var lineNumber = document.createElement('div')

/**
 * 初始化元素
 */
function initValue(text) {
    console.log("11111111111111111111111111")
    String.prototype.endWith = function(endStr){
        var d = this.length - endStr.length;
        return (d >= 0&&this.lastIndexOf(endStr) == d)
    }
    //编辑器容器
    container = document.getElementsByClassName("editor-container")[0]
    //结果区域
    resultArea = container.getElementsByClassName("result-area")[0]
    //编辑区域
    leftEditorArea = container.getElementsByClassName("editor-area")[0]
    leftEditorArea.addEventListener("input",editWords(text),false)
    leftEditorArea.addEventListener("keydown",changeLine,false)
    leftEditorArea.addEventListener("blur",renderQuestion,false)
    //行号
    lineNumber = container.getElementsByClassName("line-number")[0]
}
/**
 * 初始化行号
 */
function initLineNumber() {
    /******===第一步：初始化编辑器行号。===********/
    //获取编辑区域的内容
    // var textValue = leftEditorArea.innerText || leftEditorArea.textContent
    var textValue = ""
    var txtArr = ""
    if (getUserAgent()) {
        textValue = leftEditorArea.innerHTML
        txtArr = textValue.split("<br>")
    }else {
        textValue = leftEditorArea.innerText
        txtArr = textValue.split("\n")
    }
    if (textValue.length > 0) {
        //编辑区域内容按行读取后的数组结果
        // var txtArr = textValue.split("\n")
        for (var i = 0,len = txtArr.length; i < len; i++) {
            var ele = createElement("span")
            ele.className = "line"
            ele.innerText = (i+1).toString()
            ele.textContent = (i+1).toString()
            lineNumber.appendChild(ele)
        }
    } else { //默认创建五行
        for (var i = 0; i < 5; i++) {
            var ele = createElement("span")
            ele.className = "line"
            ele.innerText = (i+1).toString()
            ele.textContent = (i+1).toString()
            lineNumber.appendChild(ele)
        }
    }

}

/**
 * 左侧输入题目内容
 * @param ev
 */
function editWords(text){
    //重新排编辑序号
    console.log("editwords:"+text);
    leftEditorArea.innerHTML = text;
    lineNumber.innerHTML = "";
    initLineNumber()
}

/**
 * 鼠标回车或者退格事件
 * 回车：
 * @param ev
 */
function changeLine(ev){
    console.log("键盘事件···············")
    //重新排编辑序号
    lineNumber.innerHTML = ""
    initLineNumber()
    ev = document.all ? window.event:ev
    //监听回车事件
    if (ev.keyCode === 13) {
        console.log("回车事件")
        //获取编辑区域的内容
        var textValue = ""
        var txtArr = ""
        console.log('getUserAgent:'+getUserAgent())
        if (getUserAgent()) {
            textValue = leftEditorArea.innerHTML
            txtArr = textValue.split("<br>")
        }else {
            textValue = leftEditorArea.innerText
            txtArr = textValue.split("\n")
        }
        // console.log(txtArr)
        //编辑区域内容按行读取后的数组结果
        //var txtArr = textValue.split("\n")
        //获取当前行号
        var lineChildren =  lineNumber.children
        if (parseInt(lineChildren.length)<parseInt(txtArr.length)) {
            console.log("-------------进入-------")
            var span = document.createElement("span")
            span.className = "line"
            span.innerText = (parseInt(lineChildren.length)+1).toString()
            span.textContent = (parseInt(lineChildren.length)+1).toString()
            lineNumber.appendChild(span)
        }
    }
    //监听退格事件
    if (ev.keyCode === 8) {
        console.log("退格事件")
        //获取编辑区域的内容
        var textValue = ""
        if (getUserAgent()) {
            textValue = leftEditorArea.innerHTML
            console.log('120line:'+textValue.split("<br>"))
        }else {
            textValue = leftEditorArea.innerText
        }
        //编辑区域内容按行读取后的数组结果
        var txtArr = textValue.split("\n")
        //获取当前行号
        var lineChildren =  lineNumber.children
        console.log('128txtArr:'+txtArr)
        //console.log('当前行号：'+${lineChildren.length}+',当前区域长度：'+${txtArr.length});
        //到第一行不删除元素
        if (parseInt(lineChildren.length) === 1) {
            return
        }
        //删除多余行号
        if (parseInt(lineChildren.length)>=parseInt(txtArr.length)) {
            console.log('lineNumber.lastChild:'+lineNumber.lastChild)
            lineNumber.removeChild(lineNumber.lastChild)
        }
    }
}

/**
 * 文本编辑区失去焦点，触发事件
 */
function renderQuestion() {
    //先清空右侧题目内容，防止重复
    resultArea.innerHTML = ""
    //编辑区域的内容
    var textValue = this.innerText || this.textContent
    console.log("======textValue:");
    console.log(textValue);
    //编辑区域内容按行读取后的数组结果
    var txtArr = ""
    if (getUserAgent()) {
        textValue = leftEditorArea.innerText
        txtArr = textValue.split("\n")
        /*textValue = leftEditorArea.innerHTML
        txtArr = textValue.split("<br>")*/
    }else {
        textValue = leftEditorArea.innerText
        txtArr = textValue.split("\n")
    }
    //剔除空字符串
    for (var i = 0; i < txtArr.length; i++) {
        if (txtArr[i] === "") {
            txtArr.splice(i,1)
            i--
        }
    }
    //把数组内容分成标题，题目的二维数组
    //题目在数组中的索引
    var indexArr = []
    console.log('txtArr:'+txtArr)
    //console.log('indexArr:'+indexArr)
    var arrQuestion = []
    var pageflag=0;
    var title = "";//问卷标题
    var uptitle = "";//上一个标题
    var uppageIndex = 0;//上一个ye
    var uptype = "";//上一个题型
    var upremark = "";//上一个题目备注
    var welcom = "";//欢迎语
    var oneoptions = [];//每个题目的所有选项
    var welcomflag = true;//找到题目改为false

    //题目数据封装
    for (var i = 0,len = txtArr.length; i < len; i++) {
        var text2 = txtArr[i];
        //剃除所有html标签
        text2= text2.replace(/<[^>]*>|<\/[^>]*>/gm,"").replace(/\s+/g,"");
        var question = {
            title:"",
            options:[],
            pageIndex:pageflag,
            type:"",
            remark:""
        }
        if(i === 0){
            //第一个必须是标题，接下来找下有没有分页的
            if(text2.length>100){
                console.log(123);
                //debugger
                alert("标题超过100个字,只保留前100字!");
                title=text2.substring(0,100);
                continue;
            }
            title = text2;
            continue;
        }

        //判断第一个题目
        //[单选题]、[多选题]、【单选题】、【多选题】为结尾
        //debugger;
        if(text2.endWith('[单选题]') || text2.endWith('【单选题】')){
            console.log('为dan题目！');
            welcomflag =false;
            //不为空,那就将上一个题目及选项插入
            if(uptitle != '' && uptitle != null && uptitle!=undefined){
                question.title = uptitle;
                question.type = uptype;
                question.remark = upremark;
                question.pageIndex = uppageIndex;
                question.options = oneoptions;
                arrQuestion.push(question);
                oneoptions=[];
                uptitle = "";//上一个标题
                uptype = "";//上一个题型
                upremark = "";//上一个题目备注
            }
            uptitle = text2.substring(0,text2.length-5);//上一个标题
            uppageIndex = pageflag;//上一个ye
            uptype = "radio";
            //question.title = text2;
            //question.type = "radio";
            //arrQuestion.push(question);
            continue;//找到题目没必要进行下面判断，直接判断下一轮，去找选项
        }
        if(text2.endWith('[多选题]') || text2.endWith('【多选题】')){
            console.log('为duo题目！');
            welcomflag =false;
            //不为空,那就将上一个题目及选项插入
            if(uptitle != '' && uptitle != null && uptitle!=undefined){
                question.title = uptitle;
                question.type = uptype;
                question.remark = upremark;
                question.pageIndex = uppageIndex;
                question.options = oneoptions;
                arrQuestion.push(question);
                oneoptions=[];
                uptitle = "";//上一个标题
                uptype = "";//上一个题型
                upremark = "";//上一个题目备注
            }
            uptitle = text2.substring(0,text2.length-5);//上一个标题
            uppageIndex = pageflag;//上一个ye
            uptype = "checkbox";
            continue;//找到题目没必要进行下面判断，直接判断下一轮，去找选项
        }



        if(text2.indexOf("===分页===") != -1){
            console.log("uptype:"+uptype)
            if(uptitle != '' && uptitle != null && uptitle!=undefined){
                question.title = uptitle;
                question.type = uptype;
                question.remark = upremark;
                question.pageIndex = uppageIndex;
                question.options = oneoptions;
                arrQuestion.push(question);
                oneoptions=[];
                uptitle = "";//上一个标题
                uptype = "";//上一个题型
                upremark = "";//上一个题目备注
            }
            //分页构建数据需要查看后台要求的数据格式
            var questionpage = {
                title:"",
                options:[],
                pageIndex:pageflag,
                type:""
            }
            questionpage.title = "第" +(pageflag+1)+"页";
            questionpage.type = "page";
            questionpage.options = [];
            arrQuestion.push(questionpage);
            pageflag =pageflag+1;
            continue;
        }

        if(welcomflag){
            //找到第一个题目后就不拼接欢迎语
            welcom = welcom + text2;
            continue;
        }

        //备注
        if(text2 != '' && text2 != null && text2!=undefined){
            //（）
            if(/^（.*）$/.test(text2) || /^\(.*\)$/.test(text2)){
                //alert(upremark);
                upremark = text2.substring(1,text2.length-1);//上一个标题;
                //alert(upremark);
                continue;
            }
            //alert("end:"+upremark);
        }

        if(uptitle != '' && uptitle != null && uptitle!=undefined){
            //选项
            if(text2 != '' && text2 != null && text2!=undefined){
                oneoptions.push(text2.toString());
            }
        }
    }
    console.log(uptitle+":"+uptype+":"+uppageIndex+":"+oneoptions)
    if(uptitle != '' && uptitle != null && uptitle!=undefined){
        var questionnotnull = {
            title:"",
            options:[],
            pageIndex:pageflag,
            type:"",
            remark:""
        }
        //zui hou xuanxiang fuzhi
        questionnotnull.title = uptitle;
        questionnotnull.type = uptype;
        questionnotnull.pageIndex = uppageIndex;
        questionnotnull.options = oneoptions;
        questionnotnull.remark = upremark;
        arrQuestion.push(questionnotnull);
        upremark ="";
    }
    //当最后一个题目有备注时,分页又是最有一个,需要把备注赋值给最后一个题
    if(upremark != '' && upremark != null && upremark!=undefined){
        for (var i = arrQuestion.length -1; i >=0; i--) {
            var lasttype = arrQuestion[i].type;
            if(lasttype == "radio" || lasttype == "checkbox"){
                arrQuestion[i].remark = upremark;
                upremark = "";
                break;
            }
        }
    }
    //拼接欢迎语
    if(welcom != '' && welcom != null && welcom!=undefined){
        arrQuestion.unshift({
            title:welcom,
            options:[],
            pageIndex:0,
            type:"welcome"
        })
    }
    //拼接标题
    //为空不掉后台
    if(title != '' && title != null && title!=undefined){
        arrQuestion.unshift({
            title:title,
            options:[],
            pageIndex:0,
            type:"header"
        })
        console.log(arrQuestion)
        console.log('arrQuestion11:'+arrQuestion.length)
        //渲染问卷
        var flag ;
        flag = renderPage(arrQuestion);
        return flag;
    }


    //最后加一个分页进去
    /*arrQuestion.push({
        title:"===分页===",
        options:[],
        pageIndex:"",
        type:"page"
    })*/
    console.log(arrQuestion)
    console.log('arrQuestion:'+arrQuestion.length)

}

function renderPage(arrQuestion) {
    //题号
    var subjectNum =1;
    /**
     * 循环渲染页面
     */
    for (var i = 0; i < arrQuestion.length; i++) {
        //标题
        if(i === 0){
            var elQuestion = document.createElement("div")
            elQuestion.className = "subjectTitle"
            var elTitle = document.createElement("div")
            elTitle.innerText = arrQuestion[i].title
            elTitle.textContent = arrQuestion[i].title
            elTitle.className = "subjectTitle"
            elQuestion.appendChild(elTitle)
            var optionWrap = document.createElement("div")
            optionWrap.className = "option-wrap"
            elQuestion.appendChild(optionWrap)
            resultArea.appendChild(elQuestion)
            continue;
        }
        //欢迎语
        if(i === 1 && arrQuestion[i].type == "welcome"){
            var elQuestion = document.createElement("div")
            elQuestion.className = "subjectRemark"
            var elTitle = document.createElement("div")
            elTitle.innerText = arrQuestion[i].title
            elTitle.textContent = arrQuestion[i].title
            elTitle.className = "questionTitle"
            elQuestion.appendChild(elTitle)
            var optionWrap = document.createElement("div")
            optionWrap.className = "option-wrap"
            elQuestion.appendChild(optionWrap)
            resultArea.appendChild(elQuestion)
            continue;
        }

        //分页
        if(arrQuestion[i].type == "page"){
            var elQuestion = document.createElement("div")
            elQuestion.className = "page"
            var elTitle = document.createElement("div")
            elTitle.className = "pageNum"
            elQuestion.appendChild(elTitle)
            elTitle.innerText = arrQuestion[i].title
            elTitle.textContent = arrQuestion[i].title

            var optionWrap = document.createElement("div")
            optionWrap.className = "option-wrap"
            elQuestion.appendChild(optionWrap)
            resultArea.appendChild(elQuestion)
            continue;
        }


        //题目
        var elQuestion = document.createElement("div")
        elQuestion.className = "questionItem"
        var elTitle = document.createElement("div")
        elTitle.innerText = subjectNum +". "+ arrQuestion[i].title
        elTitle.textContent = subjectNum +". "+ arrQuestion[i].title
        elTitle.className = "questionTitle"
        elQuestion.appendChild(elTitle)
        //备注
        var elRemark = document.createElement("div")
        elRemark.innerText = arrQuestion[i].remark
        elRemark.textContent = arrQuestion[i].remark
        elRemark.className = "question-desc"
        elQuestion.appendChild(elRemark)
        var optionWrap = document.createElement("div")
        optionWrap.className = "questionContent"
        //选项
        for (var j = 0; j < arrQuestion[i].options.length; j++) {
            var option = document.createElement("div")
            var radio = document.createElement("input")
            if (arrQuestion[i].type === "radio") {
                option.className = "radio-primary radio-btn"
                radio.type = "radio"
            } else if(arrQuestion[i].type === "checkbox"){
                option.className = "checkbox-primary checkbox-btn"
                radio.type = "checkbox"
            }
            radio.name = "option" + i ;
            option.appendChild(radio)
            var label= document.createElement("label")
            label.innerText = arrQuestion[i].options[j]
            label.textContent = arrQuestion[i].options[j]
            label.for = "option"
            option.appendChild(label)
            //添加选项
            optionWrap.appendChild(option)
        }
        subjectNum = subjectNum +1;
        elQuestion.appendChild(optionWrap)
        resultArea.appendChild(elQuestion)
    }

    //ajax
    var flag;
    flag = reeltextSave(arrQuestion);
    return flag;
}

//保存文本数据:/reeltextSave
function reeltextSave(arrQuestion) {
    console.log("===arrQuestion:");
    console.log(arrQuestion);
    var flag;
    var rId = $("#param").find(".rId").val()
    //var list = [];
    var date = {
        "rid": rId,
        "list": arrQuestion
    };
    $.ajax({
        type: 'post',
        url: "/questionnaire/reel/reeltextSave",
        data: JSON.stringify(date),
        async: false,
        contentType: "application/json;charset=UTF-8",
        success: function (res) {
            //alert(res.data);
            if (res.code == 0) {
                console.log("reeltextSave:rid:"+res.data);
                $("#itextId").val(res.data);
                $("#param").find(".rId").val(res.data);
                //alert($("#param").find(".rId").val());
                //alert("保存成功");
                alertTip("保存成功")
                flag = true;
            }
        }
    })
    return flag;
}

/**
 * 创建标签元素
 * @param tagName
 * @returns {HTMLElement | HTMLSelectElement | HTMLLegendElement | HTMLTableCaptionElement | HTMLTextAreaElement | HTMLModElement | HTMLHRElement | HTMLOutputElement | HTMLPreElement | HTMLEmbedElement | HTMLCanvasElement | HTMLFrameSetElement | HTMLMarqueeElement | HTMLScriptElement | HTMLInputElement | HTMLUnknownElement | HTMLMetaElement | HTMLStyleElement | HTMLObjectElement | HTMLTemplateElement | HTMLBRElement | HTMLAudioElement | HTMLIFrameElement | HTMLMapElement | HTMLTableElement | HTMLAnchorElement | HTMLMenuElement | HTMLPictureElement | HTMLParagraphElement | HTMLTableDataCellElement | HTMLTableSectionElement | HTMLQuoteElement | HTMLTableHeaderCellElement | HTMLProgressElement | HTMLLIElement | HTMLTableRowElement | HTMLFontElement | HTMLSpanElement | HTMLTableColElement | HTMLOptGroupElement | HTMLDataElement | HTMLDListElement | HTMLFieldSetElement | HTMLSourceElement | HTMLBodyElement | HTMLDirectoryElement | HTMLDivElement | HTMLUListElement | HTMLHtmlElement | HTMLAreaElement | HTMLMeterElement | HTMLAppletElement | HTMLFrameElement | HTMLOptionElement | HTMLImageElement | HTMLLinkElement | HTMLHeadingElement | HTMLSlotElement | HTMLVideoElement | HTMLBaseFontElement | HTMLTitleElement | HTMLButtonElement | HTMLHeadElement | HTMLParamElement | HTMLTrackElement | HTMLOListElement | HTMLDataListElement | HTMLLabelElement | HTMLFormElement | HTMLTimeElement | HTMLBaseElement}
 */
function createElement(tagName) {
    return document.createElement(tagName)
}

/**
 * 获取是否是火狐浏览器
 * @returns {boolean}
 */
function getUserAgent() {
    var userAgent = window.navigator.userAgent.toLowerCase()
    if (userAgent.indexOf('firefox') !== -1) {// 火狐浏览器
        return true
    } else {//其他浏览器
        return false
    }
}


/**
 * 文本编辑:不新增数据,与上面部分为重复代码,后续修改
 */
function renderQuestionnoajax() {
    //先清空右侧题目内容，防止重复
    resultArea.innerHTML = ""
    //编辑区域的内容
    var textValue = this.innerText || this.textContent
    console.log("======textValue2:");
    console.log(textValue);
    //编辑区域内容按行读取后的数组结果
    var txtArr = ""
    if (getUserAgent()) {
        textValue = leftEditorArea.innerText
        txtArr = textValue.split("\n")
        /*textValue = leftEditorArea.innerHTML
        txtArr = textValue.split("<br>")*/
    }else {
        textValue = leftEditorArea.innerText
        txtArr = textValue.split("\n")
    }
    //剔除空字符串
    for (var i = 0; i < txtArr.length; i++) {
        if (txtArr[i] === "") {
            txtArr.splice(i,1)
            i--
        }
    }
    //把数组内容分成标题，题目的二维数组
    //题目在数组中的索引
    var indexArr = []
    console.log('txtArr2:'+txtArr)
    //console.log('indexArr:'+indexArr)
    var arrQuestion = []
    var pageflag=0;
    var title = "";//问卷标题
    var uptitle = "";//上一个标题
    var uppageIndex = 0;//上一个ye
    var uptype = "";//上一个题型
    var upremark = "";//上一个题目备注
    var welcom = "";//欢迎语
    var oneoptions = [];//每个题目的所有选项
    var welcomflag = true;//找到题目改为false

    //题目数据封装
    for (var i = 0,len = txtArr.length; i < len; i++) {
        var text2 = txtArr[i];
        //剃除所有html标签
        text2= text2.replace(/<[^>]*>|<\/[^>]*>/gm,"").replace(/\s+/g,"");
        var question = {
            title:"",
            options:[],
            pageIndex:pageflag,
            type:"",
            remark:""
        }
        if(i === 0){
            //第一个必须是标题，接下来找下有没有分页的
            title = text2;
            continue;
        }

        //判断第一个题目
        //[单选题]、[多选题]、【单选题】、【多选题】为结尾
        //debugger;
        if(text2.endWith('[单选题]') || text2.endWith('【单选题】')){
            console.log('为dan题目！');
            welcomflag =false;
            //不为空,那就将上一个题目及选项插入
            if(uptitle != '' && uptitle != null && uptitle!=undefined){
                question.title = uptitle;
                question.type = uptype;
                question.remark = upremark;
                question.pageIndex = uppageIndex;
                question.options = oneoptions;
                arrQuestion.push(question);
                oneoptions=[];
                uptitle = "";//上一个标题
                uptype = "";//上一个题型
                upremark = "";//上一个题目备注
            }
            uptitle = text2.substring(0,text2.length-5);//上一个标题
            uppageIndex = pageflag;//上一个ye
            uptype = "radio";
            //question.title = text2;
            //question.type = "radio";
            //arrQuestion.push(question);
            continue;//找到题目没必要进行下面判断，直接判断下一轮，去找选项
        }
        if(text2.endWith('[多选题]') || text2.endWith('【多选题】')){
            console.log('为duo题目！');
            welcomflag =false;
            //不为空,那就将上一个题目及选项插入
            if(uptitle != '' && uptitle != null && uptitle!=undefined){
                question.title = uptitle;
                question.type = uptype;
                question.remark = upremark;
                question.pageIndex = uppageIndex;
                question.options = oneoptions;
                arrQuestion.push(question);
                oneoptions=[];
                uptitle = "";//上一个标题
                uptype = "";//上一个题型
                upremark = "";//上一个题目备注
            }
            uptitle = text2.substring(0,text2.length-5);//上一个标题
            uppageIndex = pageflag;//上一个ye
            uptype = "checkbox";
            continue;//找到题目没必要进行下面判断，直接判断下一轮，去找选项
        }



        if(text2.indexOf("===分页===") != -1){
            console.log("uptype:"+uptype)
            if(uptitle != '' && uptitle != null && uptitle!=undefined){
                question.title = uptitle;
                question.type = uptype;
                question.remark = upremark;
                question.pageIndex = uppageIndex;
                question.options = oneoptions;
                arrQuestion.push(question);
                oneoptions=[];
                uptitle = "";//上一个标题
                uptype = "";//上一个题型
                upremark = "";//上一个题目备注
            }
            //分页构建数据需要查看后台要求的数据格式
            var questionpage = {
                title:"",
                options:[],
                pageIndex:pageflag,
                type:""
            }
            questionpage.title = "第" +(pageflag+1)+"页";
            questionpage.type = "page";
            questionpage.options = [];
            arrQuestion.push(questionpage);
            pageflag =pageflag+1;
            continue;
        }

        if(welcomflag){
            //找到第一个题目后就不拼接欢迎语
            welcom = welcom + text2;
            continue;
        }

        //备注
        if(text2 != '' && text2 != null && text2!=undefined){
            //（）
            if(/^（.*）$/.test(text2) || /^\(.*\)$/.test(text2)){
                //alert(upremark);
                upremark = text2.substring(1,text2.length-1);//上一个标题;
                //alert(upremark);
                continue;
            }
            //alert("end2:"+upremark);
        }

        if(uptitle != '' && uptitle != null && uptitle!=undefined){
            //选项
            if(text2 != '' && text2 != null && text2!=undefined){
                oneoptions.push(text2.toString());
            }
        }
    }
    console.log(uptitle+":"+uptype+":"+uppageIndex+":"+oneoptions)
    if(uptitle != '' && uptitle != null && uptitle!=undefined){
        var questionnotnull = {
            title:"",
            options:[],
            pageIndex:pageflag,
            type:"",
            remark:""
        }
        //zui hou xuanxiang fuzhi
        questionnotnull.title = uptitle;
        questionnotnull.type = uptype;
        questionnotnull.pageIndex = uppageIndex;
        questionnotnull.options = oneoptions;
        questionnotnull.remark = upremark;
        arrQuestion.push(questionnotnull);
        upremark ="";
    }
    //当最后一个题目有备注时,分页又是最有一个,需要把备注赋值给最后一个题
    if(upremark != '' && upremark != null && upremark!=undefined){
        for (var i = arrQuestion.length -1; i >=0; i--) {
            var lasttype = arrQuestion[i].type;
            if(lasttype == "radio" || lasttype == "checkbox"){
                arrQuestion[i].remark = upremark;
                upremark = "";
                break;
            }
        }
    }
    //拼接欢迎语
    if(welcom != '' && welcom != null && welcom!=undefined){
        arrQuestion.unshift({
            title:welcom,
            options:[],
            pageIndex:0,
            type:"welcome"
        })
    }
    //拼接标题
    //为空不掉后台
    if(title != '' && title != null && title!=undefined){
        arrQuestion.unshift({
            title:title,
            options:[],
            pageIndex:0,
            type:"header"
        })
        console.log(arrQuestion)
        console.log('arrQuestion11:'+arrQuestion.length)
        //渲染问卷
        renderPagenoajax(arrQuestion)
    }


    //最后加一个分页进去
    /*arrQuestion.push({
        title:"===分页===",
        options:[],
        pageIndex:"",
        type:"page"
    })*/
    console.log(arrQuestion)
    console.log('arrQuestion:'+arrQuestion.length)

}

function renderPagenoajax(arrQuestion) {
    //题号
    var subjectNum =1;
    //循环渲染页面
    for (var i = 0; i < arrQuestion.length; i++) {
        //标题
        if(i === 0){
            var elQuestion = document.createElement("div")
            elQuestion.className = "subjectTitle"
            var elTitle = document.createElement("div")
            elTitle.innerText = arrQuestion[i].title
            elTitle.textContent = arrQuestion[i].title
            elTitle.className = "subjectTitle"
            elQuestion.appendChild(elTitle)
            var optionWrap = document.createElement("div")
            optionWrap.className = "option-wrap"
            elQuestion.appendChild(optionWrap)
            resultArea.appendChild(elQuestion)
            continue;
        }
        //欢迎语
        if(i === 1 && arrQuestion[i].type == "welcome"){
            var elQuestion = document.createElement("div")
            elQuestion.className = "subjectRemark"
            var elTitle = document.createElement("div")
            elTitle.innerText = arrQuestion[i].title
            elTitle.textContent = arrQuestion[i].title
            elTitle.className = "questionTitle"
            elQuestion.appendChild(elTitle)
            var optionWrap = document.createElement("div")
            optionWrap.className = "option-wrap"
            elQuestion.appendChild(optionWrap)
            resultArea.appendChild(elQuestion)
            continue;
        }

        //分页
        if(arrQuestion[i].type == "page"){
            var elQuestion = document.createElement("div")
            elQuestion.className = "page"
            var elTitle = document.createElement("div")
            elTitle.className = "pageNum"
            elQuestion.appendChild(elTitle)
            elTitle.innerText = arrQuestion[i].title
            elTitle.textContent = arrQuestion[i].title

            var optionWrap = document.createElement("div")
            optionWrap.className = "option-wrap"
            elQuestion.appendChild(optionWrap)
            resultArea.appendChild(elQuestion)
            continue;
        }


        //题目
        var elQuestion = document.createElement("div")
        elQuestion.className = "questionItem"
        var elTitle = document.createElement("div")
        elTitle.innerText = subjectNum +". "+ arrQuestion[i].title
        elTitle.textContent = subjectNum +". "+ arrQuestion[i].title
        elTitle.className = "questionTitle"
        elQuestion.appendChild(elTitle)
        //备注
        var elRemark = document.createElement("div")
        elRemark.innerText = arrQuestion[i].remark
        elRemark.textContent = arrQuestion[i].remark
        elRemark.className = "question-desc"
        elQuestion.appendChild(elRemark)
        var optionWrap = document.createElement("div")
        optionWrap.className = "questionContent"
        //选项
        for (var j = 0; j < arrQuestion[i].options.length; j++) {
            var option = document.createElement("div")
            var radio = document.createElement("input")
            if (arrQuestion[i].type === "radio") {
                option.className = "radio-primary radio-btn"
                radio.type = "radio"
            } else if(arrQuestion[i].type === "checkbox"){
                option.className = "checkbox-primary checkbox-btn"
                radio.type = "checkbox"
            }
            radio.name = "option" + i ;
            option.appendChild(radio)
            var label= document.createElement("label")
            label.innerText = arrQuestion[i].options[j]
            label.textContent = arrQuestion[i].options[j]
            label.for = "option"
            option.appendChild(label)
            //添加选项
            optionWrap.appendChild(option)
        }
        subjectNum = subjectNum+1;
        elQuestion.appendChild(optionWrap)
        resultArea.appendChild(elQuestion)
    }

    //ajax
    //reeltextSave(arrQuestion);
}

