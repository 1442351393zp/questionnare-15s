/**
 * 显示问卷信息，抽屉抽出
 * @param ev
 */
function showInfoDesc(ev) {
    ev = ev || event
    var target = ev.target
    if (target.className.indexOf("more-btn") === -1) {
        var elDrawInfo = document.getElementsByClassName("draw-info")[0]
        if (elDrawInfo.className.indexOf("draw-increase") === -1) {
            elDrawInfo.className += " draw-increase"
        }
        // 表格变短
        var elTable = document.getElementsByClassName("table")[0]
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

/**
 * 隐藏问卷信息，抽屉关闭
 * @param ev
 */
function hiddenInfoDesc(ev) {
    var elDrawInfo = document.getElementsByClassName("draw-info")[0]
    if (elDrawInfo.className.indexOf("draw-increase") > -1) {
        elDrawInfo.classList.remove("draw-increase")
    }
    var elTable = document.getElementsByClassName("table")[0]
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
    var parent = target.parentNode
    // 【副标题栏2】隐藏
    parent.style.display = "none"
    var elEmptyPage = document.getElementsByClassName("empty-page")[0]
    // 【副标题栏】显示
    var elSubHeader = elEmptyPage.getElementsByClassName("sub-header")[0]
    elSubHeader.style.display = "block"
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
    ev = ev || event
    var target = ev.target
    var parent = target.parentNode.parentNode
    // 【副标题栏2】隐藏
    parent.style.display = "none"
    var elEmptyPage = document.getElementsByClassName("empty-page")[0]
    // 【副标题栏】显示
    var elSubHeader2 = elEmptyPage.getElementsByClassName("sub-header-2")[0]
    elSubHeader2.style.display = "flex"
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
