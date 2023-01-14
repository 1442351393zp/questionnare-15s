function reshow() {
    var numArr = ["一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五"]
    //创建问卷页面
    var elQuestionPanel = document.getElementsByClassName("questionnaire-panel")[0]
    //问卷面板
    var elPanel = elQuestionPanel.getElementsByClassName("panel")[0]
    // tab容器
    var elTabPage = elQuestionPanel.getElementsByClassName("tab-page")[0]
    // 添加上一页
    var btnForward = document.createElement("div")
    btnForward.className = "tab-btn-forward"
    btnForward.setAttribute("onclick","toForwardPage(event)");
    elTabPage.appendChild(btnForward)
    // 添加下一页
    var btnNext = document.createElement("div")
    btnNext.className = "tab-btn-next"
    btnNext.setAttribute("onclick","toNextPage(event)");
    elTabPage.appendChild(btnNext)
    // 添加新建
    var btnAdd = document.createElement("div")
    btnAdd.className = "tab-btn-add"
    btnAdd.setAttribute("onclick","addQuestionnairePage(event)");
    elTabPage.appendChild(btnAdd)
    // 需要创建的页数
    var pageNum = 10
    if(pageNum > numArr.length) {
        alert("不能超过十五页")
        return
    } else {
        for (var i = 0;i< pageNum; i++) {
            // 添加具体某一页
            var elPanelPage = document.createElement("div")
            elPanelPage.className = "panel-page"
            elPanelPage.dataset.pageindex = i.toString()
            elPanel.appendChild(elPanelPage)
            // tab页索引
            var elTabPageIndex = document.createElement("div")
            elTabPageIndex.className = "tab-page-index"
            elTabPageIndex.dataset.index = i.toString()
            // 设置首页
            if (i === 0) {
                elTabPageIndex.classList.add("active")
            }
            for (var j = 0; j < numArr.length; j++ ) {
                if (i === j)  {
                    // 添加页码
                    var elSpan = document.createElement("span")
                    elSpan.innerText = "第" + numArr[i] + "页"
                    elSpan.textContent = "第" + numArr[i] + "页"
                    elTabPageIndex.appendChild(elSpan)
                    // 添加删除按钮
                    var elI = document.createElement("i")
                    elI.setAttribute("onclick","deletePage(event)")
                    elTabPageIndex.appendChild(elI)
                }
            }
            elTabPage.appendChild(elTabPageIndex)
        }
    }



}