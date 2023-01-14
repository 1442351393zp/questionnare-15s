/**
 * 页面的相关操作
 */
/**
 * 切换页面
 * @param ev
 */
function changePage(ev) {
    ev = event || ev
    var target = ev.target
    var parent = target.parentNode
    var children = parent.children
    for (var i = 0,len = children.length; i < len; i++) {
        if (children[i].classList.contains("active")) {
            children[i].classList.remove("active")
        }
    }
    target.className += " active"
}

/**
 * 编辑皮肤设置投放等操作
 * @param ev
 */
function changeOperation(ev) {
    ev = event || ev
    var target = ev.target
    var parent = target.parentNode
    var children = parent.children
    for (var i = 0,len = children.length; i < len; i++) {
        if (children[i].classList.contains("active")) {
            children[i].classList.remove("active")
        }
    }
    target.className += " active"
}

/**
 * 改变菜单
 * @param ev
 */
function changeMenu(ev,rId) {
    ev = window.event || ev
    var target = ev.target
    var parent = target.parentNode
    var children = parent.children
    for (var i = 0,len = children.length; i < len; i++) {
        if (children[i].classList.contains("active")) {
            children[i].classList.remove("active")
        }
    }
    target.className += " active"
    var elMenuContent = document.getElementsByClassName("menu-content")[0]
    var elComponents = elMenuContent.children[0]
    var elOutLine = elMenuContent.children[1]
    if (target.dataset.type === "components") {
        elComponents.style.display = "block"
        elOutLine.style.display = "none"
    } else{
    	//查询当前卷所有题
    	/*$.ajax({
    	       type:'get',
    	       url:"/questionnaire/reel/queryOutlineSubject",
    	       data:{
    	    	   "rId":rId
    	       },
    	       success:function(result){
    	    	   var json = JSON.stringify(result);
    	    	   var pageList = $.parseJSON(json).pageList;
    	    	   for(var i=0;i<pageList.length;i++){
    	    		  $(".outline").append("第"+(i+1)+"页");
    	    		  var subjectList = pageList[i].subjectList;
    	    		  for(var k=0;k<subjectList.length;k++){
    	    			  var topic = subjectList[k].topic;
    	    			  $(".outline").append(topic);
    	    		  }
    	    	   }
    	       }
    	    })*/
        elComponents.style.display = "none"
        elOutLine.style.display = "block"
    }
}

/**
 * 上一页
 * @param ev
 */
function toForwardPage(ev) {
    ev = event || ev
    var target = ev.target
    var parent = target.parentNode
    //点击上一页，首先把隐藏的下一页按钮放出来
    var elBtnNext = parent.getElementsByClassName("tab-btn-next")[0]
    elBtnNext.style.display = "block"
    var elTab = parent.getElementsByClassName("tab-page-index")
    // 问卷页
    var elPanel = document.getElementsByClassName("panel")[0]
    var elPanelPage = elPanel.getElementsByClassName("panel-page")
    for (var i = 0,len = elTab.length; i < len; i++) {
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
 * 下一页
 * @param ev
 */
function toNextPage(ev) {
    ev = event || ev
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
 * 点击切换当前页
 * @param ev
 */
function changeQuestionnairePage(ev) {
    ev = event || ev
    var target = ev.target
    if (target.className.indexOf("tab-page-index") === -1) {
        target = target.parentNode
    }
    var parent = target.parentNode
    var elTab = parent.getElementsByClassName("tab-page-index")
    // 问卷页
    var elPanel = document.getElementsByClassName("panel")[0]
    var elPanelPage = elPanel.getElementsByClassName("panel-page")
    for (var i = 0,len = elTab.length; i < len; i++) {
        if (i === parseInt(target.dataset.index)) {
            target.className += " active"
            elPanelPage[i].style.display = "block"
        } else {
            elTab[i].classList.remove("active")
            elPanelPage[i].style.display = "none"
        }
    }
}
/**
 * 新建问卷页面
 * @param ev
 */
function addQuestionnairePage(ev) {
    ev = ev || event
    var target = ev.target
    var parent = target.parentNode
    //下一页
    var elBtnNext = parent.getElementsByClassName("tab-btn-next")[0]
    //创建新Tab页
    var newTab = document.createElement('div')
    newTab.className = "tab-page-index"
    newTab.dataset.index = parseInt(parent.children.length) - 3
    newTab.setAttribute("onclick","changeQuestionnairePage(event)")
    //创建tab标签的子元素
    var elSpan = document.createElement('span')
    var elI = document.createElement('i')
    elI.setAttribute("onclick","deletePage(event)")
    var numArr = ["一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五"]
    //创建问卷页面
    var elPanel = document.getElementsByClassName("panel")[0]
    var elPanelPage = elPanel.getElementsByClassName("panel-page")
    var temp = document.createElement("div")
    temp.className = "panel-page"
    temp.dataset.pageindex = parseInt(parent.children.length) - 3
    if (parseInt(newTab.dataset.index) >= 14) {
        alert("不能超过15页！！！")
    } else {
        for (var i = 0; i < numArr.length; i++) {
           if (parseInt(newTab.dataset.index) === i)  {
               elSpan.innerText = "第" + numArr[i] + "页"
               elSpan.textContent = "第" + numArr[i] + "页"
           }
        }
        //为tab标签添加子元素
        newTab.appendChild(elSpan)
        newTab.appendChild(elI)
        //添加到tab
        parent.insertBefore(newTab,elBtnNext);
        //添加新的问卷页面
        elPanel.appendChild(temp)
    }
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
    elTabPageIndex[len - 1].className += " active"
    elPanelPage[len - 1].style.display = "block"
}

/**
 * 删除问卷页面
 * @param ev
 */
function deletePage(ev) {
    ev = event || ev
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
    if (children.length > 1) {
        //移除该tab页
        var index = parseInt(currentTab.dataset.index)
        for (var i = children.length - 1; i >= 0 ; i--) {
            // 根据索引删除
            if (index === parseInt(children[i].dataset.index)) {
                // 删除的页面是当前页，当前页往前或者往后设置
                if (currentTab.className.indexOf("active") > -1) {
                    var previousEl = currentTab.previousElementSibling
                    var nextEl = currentTab.nextElementSibling
                    if (index !== children.length - 1) {
                        nextEl.className += "  active"
                        var nextPage = elPanelPage[i].nextElementSibling
                        console.log(nextPage)
                        nextPage.style.display = "block"
                    } else{
                        previousEl.className += "  active"
                        var perPage = elPanelPage[i].previousElementSibling
                        console.log(perPage)
                        perPage.style.display = "block"
                    }
                }
                elTabPage.removeChild(currentTab)
                elPanel.removeChild(elPanelPage[i])
            }
        }
    } else {
        alert("只剩最后一页，不能再删除!")
    }

    //重新给页面排序
    var elTab = elTabPage.getElementsByClassName("tab-page-index")
    var elPanelChldren = elPanel.children
    var numArr = ["一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五"]
    for (var i = 0,len = elTab.length; i < len; i++) {
        elTab[i].dataset.index = i.toString()
        var childSpan = elTab[i].getElementsByTagName("span")[0]
        for (var j = 0; j < numArr.length; j++) {
            if (i === j) {
                childSpan.innerText = "第" + numArr[j] + "页"
                childSpan.textContent = "第" + numArr[j] + "页"
            }
        }
        elPanelChldren[i].dataset.pageindex = i.toString()
    }
}