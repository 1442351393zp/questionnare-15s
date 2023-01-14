<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!doctype html>
<html lang="en">
<head>

</head>
<body>
<input type="hidden" id="rId" value="${allSubjectDTO.rId }"/>
<div class="statistics-container">
    <!--第一行-->
    <div class="statistics-header clearfix">
        <div class="statistics-header-left">
            <h2 class="ellipsis" title="${allSubjectDTO.title }" style="width: 150px;">${allSubjectDTO.title }</h2>
            <i class="ellipsis" style="font-size: 12px;margin-top: 12px;">id：${allSubjectDTO.rId }</i>
        </div>
        <div class="statistics-header-right">
            <%--<div class="select-publish push-select">--%>
            <%--<div class="select-title">--%>
            <%--<span>请选择</span>--%>
            <%--<i></i>--%>
            <%--</div>--%>
            <%--<ul>--%>
            <%--<li value="0">发送</li>--%>
            <%--<li value="1">发布</li>--%>
            <%--</ul>--%>
            <%--</div>--%>
            <div class="dropdown show-all">
                <%--<select id="mapSelect" onchange="mapSelect()">--%>
                <%--<option value="0">显示全部</option>--%>
                <%--<option value="1">只显示表</option>--%>
                <%--</select>--%>
                <div class="select-publish" id="mapSelect">
                    <div class="select-title">
                        <span>显示全部</span>
                        <i></i>
                    </div>
                    <ul>
                        <li value="0">显示全部</li>
                        <li value="1">只显示表</li>
                    </ul>
                </div>
                <!-- <button class="btn" type="button" data-toggle="dropdown">菜单按钮<span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <li><a href="###">显示全部</a></li>
                    <li><a href="###">只显示表</a></li>
                </ul> -->
            </div>
            <!--  <div class="print"></div>
             <div class="share"></div> -->
            <div class="export" onclick="exportReport()">导出统计结果</div>
        </div>
    </div>
    <!--日期选择-->
    <div class="datetimepicker-area">
        <span>选择时间</span>
        <div class="input-group date form-date" data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2"
             data-link-format="yyyy-mm-dd">
            <input class="form-control" size="16" type="text" id="startTime" readonly=""
                   value="${allSubjectDTO.startTime }" onchange="startTime()">
            <span class="input-group-addon"><span class="icon-calendar"></span></span>
        </div>
        <div class="input-group date form-date" data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2"
             data-link-format="yyyy-mm-dd">
            <input class="form-control" size="16" type="text" id="endTime" readonly="" onchange="endTime()"
                   value="${allSubjectDTO.endsTime }"/>
            <span class="input-group-addon"><span class="icon-calendar"></span></span>
        </div>
    </div>
    <!--筛选题目-->
    <div class="filter-topic">
        <span>筛选题目</span>
        <div class="filter-list">
            <a>编辑筛选条件</a>
        </div>
    </div>
    <!--统计结果-->
    <div class="statistics-table">
        <!--tab页-->

        <ul class="nav nav-tabs">
            <c:forEach items="${allSubjectDTO.pageList }" var="vah" varStatus="vh">
                <c:if test="${vh.index == 0}">

                    <li class="active"><a href="###" data-target="#tab2Content${vh.count }"
                                          data-toggle="tab">第${vh.count }页</a></li>
                </c:if>
                <c:if test="${vh.index > 0}">
                    <li><a href="###" data-target="#tab2Content${vh.count }" data-toggle="tab">第${vh.count }页</a></li>
                </c:if>
            </c:forEach>
        </ul>
        <div class="tab-content">
            <c:forEach items="${allSubjectDTO.pageList }" var="vas" varStatus="vk">
                <c:if test="${vk.index==0 }">
                    <div class="tab-pane fade active in" id="tab2Content${vk.count }">
                        <c:forEach items="${vas.subjectList }" var="vae" varStatus="vaa">
                            <c:choose>
                                <c:when test="${vae.subjectType==4}">
                                    <div class="subject-title">${vae.topic}</div>
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th width="80%">选项</th>
                                            <th width="20%">答题人</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${vae.textanswer}" var="texa" varStatus="texn">
                                            <tr>
                                                <td>${texa.textAnswer}</td>
                                                <td>${texa.textAnswerName}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <div class="subject-title">${vaa.count}.${vae.topic}</div>
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th width="50%">选项</th>
                                            <th>小计</th>
                                            <th>百分比</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${vae.countPicDTO }" var="vaf" varStatus="vad">
                                            <tr>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${vaf.isMultipleChoice == '0'}">
                                                            其他
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${vaf.options }
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${vaf.num }</td>
                                                <td class="percentage">${vaf.percentage}%</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="statisticsMap">
                                        <div class="statistics-title">
                                            <span class="graph_type active" data-type="pie">饼状图</span>
                                            <span class="graph_type" data-type="column">柱状图</span>
                                            <span class="graph_type" data-type="bar">条形图</span>
                                            <span class="graph_type" data-type="line">折线图</span>
                                        </div>
                                        <div class="statistics-content">
                                            <div class="echartsStyle00 active"></div>
                                            <div class="echartsStyle10"></div>
                                            <div class="echartsStyle20"></div>
                                            <div class="echartsStyle30"></div>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </c:if>

            </c:forEach>

            <c:forEach items="${allSubjectDTO.pageList }" var="vas" varStatus="vk">
                <c:if test="${vk.index > 0 }">
                    <div class="tab-pane fade" id="tab2Content${vk.count }">
                        <c:forEach items="${vas.subjectList }" var="vae" varStatus="vaa">
                            <c:choose>
                                <c:when test="${vae.subjectType==4}">
                                    <div class="subject-title">${vae.topic}</div>
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th width="50%">选项</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${vae.textanswer }" var="texa" varStatus="texn">
                                            <tr>
                                                <td>${texa}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <div class="subject-title">${vaa.count }.${vae.topic}</div>
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th width="50%">选项</th>
                                            <th>小计</th>
                                            <th>百分比</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${vae.countPicDTO }" var="vaf" varStatus="vad">
                                            <tr>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${vaf.isMultipleChoice == '0'}">
                                                            其他
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${vaf.options }
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${vaf.num }</td>
                                                <td class="percentage">${vaf.percentage}%</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="statisticsMap">
                                        <div class="statistics-title">
                                            <span class="graph_type active" data-type="pie">饼状图</span>
                                            <span class="graph_type" data-type="column">柱状图</span>
                                            <span class="graph_type" data-type="bar">条形图</span>
                                            <span class="graph_type" data-type="line">折线图</span>
                                        </div>
                                        <div class="statistics-content">
                                            <div class="echartsStyle1${vk.index } active"></div>
                                            <div class="echartsStyle2${vk.index }"></div>
                                            <div class="echartsStyle3${vk.index }"></div>
                                            <div class="echartsStyle4${vk.index }"></div>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>
<%--筛选条件弹出框--%>
<div class="modal fade" id="screening">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                        class="sr-only">关闭</span></button>
                <h4 class="modal-title">筛选条件设置</h4>
            </div>
            <div class="modal-body">
                <!-- 弹出框主体内容 -->
                <div class="select-box">
                    <div class="select-item">
                        <select class="subjectSelect">
                            <option value="0">请选择题目</option>
                        </select>
                        <select class="include">
                            <option value="0">请选择逻辑</option>
                            <option value="1">包含</option>
                            <option value="2">不包含</option>
                        </select>
                        <select class="optionsSelect">
                            <option value="0">请选择选项</option>
                        </select>
                        <i class="delete-item">×</i>
                    </div>
                </div>
                <button class="add-condition">新建条件</button>
                <div class="showSelect"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary saveAuth">保存</button>
            </div>
        </div>
    </div>
</div>
<%--选择用户列表弹出框--%>
<div class="modal fade" id="userModal">
    <div class="modal-dialog user-list">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                        class="sr-only">关闭</span></button>
                <h4 class="modal-title">用户列表</h4>
            </div>
            <div class="modal-body user-box">
                <div class="user-tree ztree" id="user-tree"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary save-userPublish">发送</button>
            </div>
        </div>
    </div>
</div>
<script src="<%=path %>/template/echarts/echarts.min.js"></script>
<%--引入ztree树--%>
<script src="<%=path %>/template/js/zTree_v3/js/jquery.ztree.core.min.js"></script>
<script src="<%=path %>/template/js/zTree_v3/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript">
    var allData = ${allSubjectArray};
    var subjectJson = JSON.stringify(${subjectArr}); //回显选择的选项数据
    $(function () {

        var percentage = $(".percentage").val();

        var all = $("#all").val();
        // var flag = $("#flag").val();
        var scrollbar = document.getElementsByClassName("scrollBar")[0]
        if (scrollbar) {
            var scroll = new ScrollBar(scrollbar)
        }

        $(".graph_type").attr("active");
        var pageList = allData[0].pageList;//页
        var myCharts;
        for (var i = 0; i < pageList.length; i++) {
            var subjectList = pageList[i].subjectList;
            // var subjectType =  pageList[i].subjectType
            for (var j = 0; j < subjectList.length; j++) {
                var subjectType = subjectList[j].subjectType;
                if(subjectType!='4'){
                    var countPicDTO = subjectList[j].countPicDTO;
                    var arrOptions = [];
                    // var jsonOptions = "";
                    var jsonOptions = [];
                    // var num="";
                    var numArr = [];
                    // var countNum ="";
                    var countNum = [];
                    // var percentage = "";
                    var percentage = [];
                    for (var k = 0; k < countPicDTO.length; k++) {
                        var options = countPicDTO[k].options;
                        console.log("options:", options)
                        // arrOptions += '"'+options+'"'+",";
                        arrOptions.push(options)
                        var num = countPicDTO[k].num;
                        // jsonOptions +="{value:"+'"'+num+'"'+",name:"+'"'+options+'"'+"},";
                        jsonOptions.push({value: num, name: options})
                        // num += countPicDTO[k].num+",";
                        numArr.push(countPicDTO[k].num)
                        // countNum += countPicDTO[k].countNum+",";
                        countNum.push(countPicDTO[k].countNum);
                        //percentage += (countPicDTO[k].num / countPicDTO[k].countNum)*100 +",";
                        if (countPicDTO[k].percentage == 0) {
                            countPicDTO[k].percentage = "''";
                        }
                        //percentage += countPicDTO[k].percentage +",";
                        percentage.push(countPicDTO[k].percentage);

                    }

                    var echartsStyle0;
                    var echartsStyle1;
                    var echartsStyle2;
                    var echartsStyle3;
                    if (i == 0) {
                        echartsStyle0 = "echartsStyle0" + i;
                        echartsStyle1 = "echartsStyle1" + i;
                        echartsStyle2 = "echartsStyle2" + i;
                        echartsStyle3 = "echartsStyle3" + i;
                    } else {
                        echartsStyle0 = "echartsStyle1" + i;
                        echartsStyle1 = "echartsStyle2" + i;
                        echartsStyle2 = "echartsStyle3" + i;
                        echartsStyle3 = "echartsStyle4" + i;
                    }
                    console.log("echartsStyle0:",echartsStyle0)
                    console.log("echartsStyle1:",echartsStyle1)
                    console.log("echartsStyle2:",echartsStyle2)
                    console.log("echartsStyle3:",echartsStyle3)
                    // if(arrOptions != ""){
                    if (arrOptions.length > 0) {
                        // arrOptions = arrOptions.substr(arrOptions,arrOptions.length-1);
                        // console.log("arrOptions:",arrOptions)
                        // arrOptions = "["+arrOptions+"]";
                        // jsonOptions =  jsonOptions.substr(jsonOptions,jsonOptions.length-1);
                        // jsonOptions = "["+jsonOptions+"]";
                        // num =  num.substr(num,num.length-1);
                        // num = "["+num+"]";
                        // percentage =  percentage.substr(percentage,percentage.length-1);
                        // percentage = "["+percentage+"]";
                        // console.log("jsonOptions:",jsonOptions)
                        // console.log("arrOptions:", arrOptions)
                        // arrOptions = eval(arrOptions)
                        console.log(`${j}`,j,`echartsStyle0[${j}]:`,echartsStyle0[j],"dom:",document.getElementsByClassName(echartsStyle0))
                        myCharts = echarts.init(document.getElementsByClassName(echartsStyle0)[j]);
                        option = {
                            tooltip: {
                                trigger: 'item',
                                position: function (p) {
                                    return [p[0] - 100, p[1] - 10];
                                },
                                //formatter:'{a} <br/>{b} : {c} ({d}%)'
                                formatter: function (date) {
                                    if (isNaN(date.percent.toFixed(2))) {
                                        //console.log("is nan")
                                        return "<div style='display:black;word-break:break-all;word-wrap:break-word;white-space:pre-wrap'>" + date.seriesName + "<br/>" + date.name + " : " + date.value + "(0%)"
                                    } else {
                                        //console.log("not nan")
                                        return "<div style='display:black;word-break:break-all;word-wrap:break-word;white-space:pre-wrap'>" + date.seriesName + "<br/>" + date.name + " : " + date.value + "(" + date.percent.toFixed(2) + "%)"
                                    }
                                }
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    saveAsImage: {show: true}
                                }
                            },
                            legend: {
                                top: '5%',
                                left: '85%',
                                orient: 'vertical',
                                data: arrOptions,
                                formatter: function (value) {
                                    // if (value.length > 8) {
                                    //     value = value.substr(0, 7) + "...";
                                    // }
                                    var dom = document.createElement("div")
                                    dom.innerHTML = value
                                    value = dom.textContent || dom.innerText || ""
                                    value = value.replace(/\s*|\s$/g,"")
                                    value = value.length > 7 ? value.substr(0, 7) + "..." : value;
                                    return value;
                                }
                            },

                            series: {
                                name: '',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                // data:eval(jsonOptions),
                                data: jsonOptions,
                                itemStyle: {
                                    normal: {
                                        color: function (params) {
                                            var colorList = [
                                                '#58a6e7', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                                                '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                                                '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                                            ];
                                            return colorList[params.dataIndex]
                                        },
                                        label: {
                                            show: true,
                                            position: 'top',
                                            formatter: function (value) {
                                                // var res = value.name;
                                                // if (res.length > 10) {
                                                //     res = res.substr(0, 10) + "...";
                                                // }
                                                // return res;
                                                var dom = document.createElement("div")
                                                dom.innerHTML = value.name
                                                value = dom.textContent || dom.innerText || ""
                                                value = value.replace(/\s*|\s$/g,"")
                                                value = value.length > 7 ? value.substr(0, 7) + "..." : value;
                                                return value
                                            }
                                            //formatter: '{b}'
                                            // formatter: '{b}%'
                                        }
                                    }
                                }
                            }
                        }
                        myCharts.setOption(option);//饼图


                        myCharts1 = echarts.init(document.getElementsByClassName(echartsStyle1)[j]);
                        option1 = {
                            color: ['#58a6e7'],
                            tooltip: {
                                trigger: 'item',
                                position: function (p) {
                                    return [p[0] - 100, p[1] - 10];
                                },
                                formatter: "<br/>" + "<div style='display:black;word-break:break-all;word-wrap:break-word;white-space:pre-wrap'>" + "{b}:\n{c}%"
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    // dataView: {show: true, readOnly: false},
                                    // restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            grid: {
                                borderWidth: 30,
                                y: 80,
                                y2: 60
                            },
                            xAxis: [
                                {
                                    type: 'category',
                                    show: true,
                                    triggerEvent: true,
                                    axisLabel: {
                                        formatter: function (value) {
                                            var res = value;
                                            if (res.length > 5) {
                                                res = res.substr(0, 4) + "...";
                                            }
                                            return res;
                                        }
                                    },
                                    //data: eval(arrOptions),
                                    data: arrOptions,
                                    axisLine: {show: true},
                                    axisTick: {show: false},
                                    splitLine: {show: false}
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    show: true,
                                    axisLine: {show: false}
                                }
                            ],
                            series: [
                                {
                                    name: 'Age',
                                    type: 'bar',
                                    barWidth: 30,
                                    itemStyle: {
                                        normal: {
                                            /* color: function(params) {
                                                 var colorList = [
                                                   '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                                    '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                                    '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                                                 ];
                                                 return colorList[params.dataIndex]
                                             }, */
                                            label: {
                                                show: true,
                                                position: 'top',
                                                formatter: '{c}%'
                                            }
                                        }
                                    },
                                    // data: eval(percentage),
                                    data: percentage,
                                    markPoint: {
                                        tooltip: {
                                            trigger: 'item',
                                            backgroundColor: 'rgba(0,0,0,0)',
                                            formatter: function (params) {
                                                return '<img src="'
                                                    + params.data.symbol.replace('image://', '')
                                                    + '"/>';
                                            }
                                        }

                                    }
                                }
                            ]
                        };
                        myCharts1.setOption(option1);//柱状图


                        myCharts2 = echarts.init(document.getElementsByClassName(echartsStyle2)[j]);
                        var option2 = {
                            color: ['#3398DB'],
                            title: {
                                left: 'center'
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    saveAsImage: {show: true}
                                }
                            },
                            tooltip: {
                                formatter: "<br/>" + "<div style='display:black;word-break:break-all;word-wrap:break-word;white-space:pre-wrap'>" + '{b}:\n{c}%',
                                position: function (p) {
                                    return [p[0] - 100, p[1] - 10];
                                },
                                axisPointer: {
                                    type: ''
                                }
                            },
                            legend: {
                                top: 30
                            },
                            grid: {
                                left: '3%',
                                right: '4%',
                                bottom: '3%',
                                containLabel: true
                            },
                            xAxis: {
                                type: 'value',
                                boundaryGap: [0, 0.01],
                                axisLine: {show: false},
                                axisLabel: {
                                    show: true,
                                    interval: 'auto',
                                    formatter: '{value}%'
                                }

                            },
                            yAxis: {
                                type: 'category',
                                // data: eval(arrOptions),
                                data: arrOptions,
                                axisLine: {show: true},
                                axisTick: {show: false},
                                splitLine: {show: false},
                                inverse: true,
                                triggerEvent: true,
                                axisLabel: {
                                    formatter: function (value) {
                                        var res = value;
                                        if (res.length > 5) {
                                            res = res.substr(0, 4) + "...";
                                        }
                                        return res;
                                    }
                                }
                            },
                            series: [
                                {
                                    name: '用户数',
                                    barWidth: 30,
                                    type: 'bar',
                                    // data: eval(percentage)
                                    data: percentage

                                }
                            ]
                        };
                        myCharts2.setOption(option2);//柱状图
                        myCharts3 = echarts.init(document.getElementsByClassName(echartsStyle3)[j]);
                        option3 = {
                            color: ['#3398DB'],
                            toolbox: {
                                show: true,
                                feature: {
                                    saveAsImage: {show: true}
                                }
                            },
                            tooltip: {
                                formatter: "<br/>" + "<div style='display:black;word-break:break-all;word-wrap:break-word;white-space:pre-wrap'>" + '{b}:\n{c}%',
                                position: function (p) {
                                    return [p[0] - 100, p[1] - 10];
                                },
                                axisPointer: {
                                    type: ''
                                }
                            },
                            xAxis: {
                                type: 'category',
                                show: true,
                                // data:eval(arrOptions),
                                data: arrOptions,
                                triggerEvent: true,
                                axisLabel: {
                                    formatter: function (value) {
                                        var res = value;
                                        if (res.length > 5) {
                                            res = res.substr(0, 4) + "...";
                                        }
                                        return res;
                                    }
                                },
                                axisLine: {show: true},
                                axisTick: {show: false},
                                splitLine: {show: false}
                            },
                            yAxis: {
                                type: 'value',
                                show: true,
                                axisLine: {show: false}
                            },

                            series: [{
                                // data:eval(percentage),
                                data: percentage,
                                type: 'line',
                                symbol: 'circle',
                                symbolSize: 8

                            }]
                        };
                        myCharts3.setOption(option3);//折线图
                    }
                }
            }
        }
        // 点击切换折线图
        $(".statistics-title").on("click", ".graph_type", function () {
            // 点击选项卡高亮
            $(this).addClass("active").siblings().removeClass("active")
            // 点击选项卡切换
            $(this).parent().next().children("div").eq($(this).index()).addClass("active").siblings().removeClass("active")
        })
    })


    // 仅选择日期
    $(".form-date").datetimepicker(
        {
            language: "zh-CN",
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0,
            format: "yyyy-mm-dd"
        }
    );

    function statistics(rId, pageId) {
        location.href = "/questionnaire/reel/statistics?rId=" + rId + "&pageId=" + pageId;
    }


    function createQuestion(rId, pageId) {
        //location.href = "/questionnaire/reel/main";
        location.href = "/questionnaire/reel/main1?rId=" + rId + "&pageId=" + pageId;
    }

    setTimeout(function () {

    }, 500)

    //导出统计结果 
    function exportReport() {
        if (allData[0].pageList[0].subjectList == "") {
            alertTip("当前无数据！");
        } else {
            var rId = $("#rId").val();
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            if (subjectJson == undefined) {
                subjectJson = "";
            }
            window.location.href = "/questionnaire/reel/poiExport?rId=" + rId + "&startTime=" + startTime + "&endTime=" + endTime + "&subjectArr=" + subjectJson;
        }
    }

    function startTime() {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        toSearchs(this, '/questionnaire/reel/countPic?rId=' + rId + "&startTime=" + startTime + "&endTime=" + endTime, 'statistics-main');
    }

    function endTime() {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        toSearchs(this, '/questionnaire/reel/countPic?rId=' + rId + "&startTime=" + startTime + "&endTime=" + endTime, 'statistics-main');
    }

    // 选择点击发布和发送
    $(".select-title").on("click", function () {
        $(this).next("ul").toggle()
    })
    $(".push-select ul>li").on("click", function () {
        var text = $(this).text();
        $(this).parent().css("display", "none").prev(".select-title").find("span").text(text)
        var index = $(this).attr("value")
        if (index == 0) {
            $(this).find("option")
            var rId = $("#rId").val()
            //加载人员列表
            $.ajax({
                type: 'post',
                url: "/questionnaire/user/getuser",
                data: {"reelId": rId},
                success: function (data) {
                    var data = JSON.parse(data)
                    var ztreeUser = JSON.parse(data.ztreeuser)

                    function getUser(data) {
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].children.length == 0) {
                                data[i].children = null;
                                data[i].checked = false
                            } else {
                                getUser(data[i].children)
                            }
                        }
                    }

                    getUser(ztreeUser)
                    var setting = {
                        check: {
                            enable: true
                        },
                        data: {
                            simpleData: {
                                enable: false
                            },
                            key: {
                                name: "orname"
                            }
                        }
                    }
                    $("#user-tree").empty()
                    $("#userModal").modal()
                    $.fn.zTree.init($(".user-tree"), setting, ztreeUser)
                    var allZttee = $.fn.zTree.getZTreeObj("user-tree");
                    allZttee.expandAll(true)
                }
            })
        } else if (index == 1) {
            //点击发布
            var rId = $("#rId").val()
            if (confirm("确定要给默认用户发送消息吗？")) {
                $.ajax({
                    url: "/questionnaire/question/publishMessage",
                    method: "post",
                    contentType: "application/json;charset=utf-8",
                    data: JSON.stringify({
                        rid: rId
                    }),
                    success: function (res) {
                        if (JSON.parse(res).code == 0) {
                            alert('发布成功！')
                        } else {
                            alert('发布失败，请稍后重试！')
                        }
                        console.log('发布响应', res)
                    }
                })
            }
        }
    })
    // 点击保存推送消息
    $(".save-userPublish").on("click", function () {
        //获取zTree选中的用户
        var selectChecked = $.fn.zTree.getZTreeObj("user-tree");
        var nodes = selectChecked.getCheckedNodes(true)
        var usernames = [];
        var userids = [];
        for (var i = 0; i < nodes.length; i++) {
            var userItem = nodes[i]
            if (userItem.organid != "") {
                usernames.push(userItem.username)
                userids.push(userItem.id)
            }
        }
        console.log(usernames)
        if (usernames.length == 0) {
            alert('请选择用户')
            return
        }
        $.ajax({
            url: "/questionnaire/question/pushMessage",
            method: "post",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                username: usernames,
                userId: userids,
                rid: rId,
            }),
            success: function (res) {
                console.log('发送', res)
                if (res.code === '0') {
                    alert('发送成功！')
                    $("#userModal").modal("hide")
                    // $("#userModal").close()
                } else {
                    alert('发送失败，请稍后重试！')
                }
            }
        })
    })
    //点击选择 全部显示 和只显示表
    $("#mapSelect ul>li").on("click", function () {
        var type = $(this).attr("value");
        var text = $(this).text();
        $(this).parent().prev(".select-title").children("span").text(text)
        if (type == 0) {
            $(".statisticsMap").show();
            $(this).parent().hide()
        } else if (type == 1) {
            $(".statisticsMap").hide();
            $(this).parent().hide()
        }
    })
    // function mapSelect(){
    // 	var mapSelect = $("#mapSelect").val();
    // 	if(mapSelect == "0"){
    // 		$(".statisticsMap").show();
    // 	}else if(mapSelect == "1"){
    // 		$(".statisticsMap").hide();
    // 	}
    // }

    //确定查询 
    $(".saveAuth").on('click', function () {
        var subjectArray = new Array();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        //获取选中的筛选条件
        var list = $(".showSelect div");
        for (var i = 0; i < $(".select-item").length; i++) {
            var item1 = {}
            var item = $($(".select-item")[i]);
            var subjectId = item.find(".subjectSelect").children("option:selected").val();//选中的题id
            if (subjectId == '' || subjectId == '0') {
                new $.zui.Messager('请选择题目！', {
                    type: 'failed',
                    close: false
                }).show();
                return false;
            }
            var include = item.find(".include").children("option:selected").val();
            if (include == '0') {
                new $.zui.Messager('请选择逻辑！', {
                    type: 'failed',
                    close: false
                }).show();
                return false;
            }
            var optionsId = item.find(".optionsSelect").children("option:selected").val();//选中的选项id
            if (optionsId == '') {
                new $.zui.Messager('请选择选项！', {
                    type: 'failed',
                    close: false
                }).show();
                return false;
            }
            item1.subjectId = subjectId;
            item1.include = include;
            item1.optionsId = optionsId;
            subjectArray.push(item1)
        }
        subjectArray = JSON.stringify(subjectArray);
        $("#screening").modal("hide");
        $(".modal-backdrop").remove();
        toSearchs(this, '/questionnaire/reel/countPic?rId=' + rId + "&startTime=" + startTime + "&endTime=" + endTime + "&subjectArr=" + subjectArray, 'statistics-main');
        for (var i = 0; i < list.length; i++) {
            $(".filter-list").prepend(list[i])
        }
    })

    var subjectArr = "";
    // 点击筛选条件
    $(".filter-list>a").on("click", function () {
        $(".subjectSelect").empty();
        $(".subjectSelect").append("<option value=''>请选择题目</option>");

        var lastPageId = "";//上一个页id
        $.ajax({
            type: 'post',
            url: "/questionnaire/reel/querySubjectAndOptions",
            data: {
                "rId": rId
            },
            async: false,
            success: function (result) {
                var subjectNum = 1;//题号
                subjectArr = result;
                var num = 0;//页数
                for (var i = 0; i < result.length; i++) {
                    var subjectId = result[i].subjectId;
                    var pageId = result[i].pageId;
                    if ((pageId == lastPageId) && i > 0) {
                        $(".subjectSelect").append("<option value=" + subjectId + ">&nbsp; &nbsp;" + subjectNum + "." + result[i].topic + "</option>");
                        subjectNum++;
                    } else {
                        num++;
                        $(".subjectSelect").append("<option value='0' style='font-weight:900'>第" + num + "页</option>");
                        subjectNum = 1;
                        $(".subjectSelect").append("<option value=" + subjectId + ">&nbsp; &nbsp;" + subjectNum + "." + result[i].topic + "</option>");
                        subjectNum++;
                    }
                    lastPageId = pageId;
                }

            }
        })
        if (subjectJson != undefined) {
            var subjectList = JSON.parse(subjectJson)
            $(".select-box").empty();
            $(".showSelect").empty();
            for (var i = 0; i < subjectList.length; i++) {
                $(".add-condition").click()
            }
            for (var j = 0; j < $(".select-item").length; j++) {
                var selectItem = $(".select-item")[j];
                var item = subjectList[j];

                for (var k = 0; k < $(selectItem).find(".subjectSelect option").length; k++) {
                    var option = $(selectItem).find(".subjectSelect option")[k];
                    if (item.subjectId == $(option).attr("value")) {
                        $(option).attr("selected", "selected")
                    }
                }

                var subjectId = $(selectItem).find("option:selected").val();//选中的题ID
                $(selectItem).find(".optionsSelect").empty();
                $(selectItem).find(".optionsSelect").append("<option value=''>请选择选项</option>");
                for (var k = 0; k < subjectArr.length; k++) {
                    if (subjectId == subjectArr[k].subjectId) {
                        var optionsList = subjectArr[k].optionsList;//选项集合
                        for (var h = 0; h < optionsList.length; h++) {
                            $(selectItem).find(".optionsSelect").append("<option value=" + optionsList[h].optionsId + ">&nbsp; &nbsp;" + optionsList[h].options + "</option>");

                        }
                    }
                }

                for (var k = 0; k < $(selectItem).find(".include option").length; k++) {
                    var option = $(selectItem).find(".include option")[k];
                    if (item.include == $(option).attr("value")) {
                        $(option).attr("selected", "selected")
                    }
                }
                for (var k = 0; k < $(selectItem).find(".optionsSelect option").length; k++) {
                    var option = $(selectItem).find(".optionsSelect option")[k];
                    if (item.optionsId == $(option).attr("value")) {
                        $(option).attr("selected", "selected")
                    }
                }
                var text = '<div>' + $(selectItem).find(".subjectSelect option:selected").text() + $(selectItem).find(".include option:selected").text() + $(selectItem).find(".optionsSelect option:selected").text() + '</div>'
                $(".showSelect").append(text)
            }

        }

        $("#screening").modal()

    })

    //选中题时联动选项
    $(".select-box").on('change', '.subjectSelect', function () {
        //var subjectId = $(".subjectSelect option:selected").val();//选中的题ID
        var subjectId = $(this).find("option:selected").val();//选中的题ID
        $(this).siblings(".optionsSelect").empty();
        $(this).siblings(".optionsSelect").append("<option value=''>请选择选项</option>");
        for (var k = 0; k < subjectArr.length; k++) {
            if (subjectId == subjectArr[k].subjectId) {
                var optionsList = subjectArr[k].optionsList;//选项集合
                for (var j = 0; j < optionsList.length; j++) {
                    $(this).siblings(".optionsSelect").append("<option value=" + optionsList[j].optionsId + ">&nbsp; &nbsp;" + optionsList[j].options + "</option>");

                }
            }

        }
        //alert($("#optionsSelect option:selected").val());

    })
    //选择选项添加数据到框里
    $(".select-box").on('change', '.optionsSelect', function () {
        var index = $(this).parent().index()
        if ($(this).find("option").is(":selected")) {
            if ($(".showSelect>div").eq(index).length == 0) {
                var html = '<div></div>'
                $(".showSelect").append(html)
                $(".showSelect>div").eq(index).html($(this).siblings(".subjectSelect").find("option:selected").text() + $(this).siblings(".include").find("option:selected").text() + $(this).find("option:selected").text());
            } else {

                $(".showSelect>div").eq(index).html($(this).siblings(".subjectSelect").find("option:selected").text() + $(this).siblings(".include").find("option:selected").text() + $(this).find("option:selected").text());
            }
        }
    })
    // 触发逻辑
    $(".select-box").on("change", ".include", function () {
        if ($(this).next().find("option").is(":selected")) {
            var index = $(this).parent().index()
            $(".showSelect>div").eq(index).html($(this).siblings(".subjectSelect").find("option:selected").text() + $(this).find("option:selected").text() + $(this).siblings(".optionsSelect").find("option:selected").text());
        }
    })

    // 点击新建条件
    $(".add-condition").on("click", function () {
        var html = '<div class="select-item">\n' +
            '    <select class=\'subjectSelect\'>\n' +
            '        <option value="0">请选择题目</option>\n' +
            '    </select >\n' +
            '    <select  class=\'include\'>\n' +
            '        <option value="0">请选择逻辑</option>\n' +
            '       <option value="1">包含</option>\n' +
            '       <option value="2">不包含</option>\n' +
            '    </select>\n' +
            '    <select class=\'optionsSelect\'>\n' +
            '        <option value="0">请选择选项</option>\n' +
            '    </select >\n' +
            '    <i class="delete-item">×</i>\n' +
            '</div>'
        $(".select-box").append(html);
        var lastPageId = "";//上一个页id
        var subjectNum = 1;//题号
        var num = 0;//页数
        for (var i = 0; i < subjectArr.length; i++) {
            var subjectId = subjectArr[i].subjectId;
            var pageId = subjectArr[i].pageId;
            if ((pageId == lastPageId) && i > 0) {
                $(".select-item:last-child").find("select").eq(0).append("<option value=" + subjectId + ">&nbsp; &nbsp;" + subjectNum + "." + subjectArr[i].topic + "</option>");
                subjectNum++;
            } else {
                num++;
                $(".select-item:last-child").find("select").eq(0).append("<option style='font-weight:900'>第" + num + "页</option>");
                subjectNum = 1;
                $(".select-item:last-child").find("select").eq(0).append("<option value=" + subjectId + ">&nbsp; &nbsp;" + subjectNum + "." + subjectArr[i].topic + "</option>");
                subjectNum++;
            }
            lastPageId = pageId;
        }
    })


    // 点击删除新建条件
    $(".select-box").on("click", ".delete-item", function () {
        var index = $(".select-item").index($(this).parent())
        $(this).parent().remove();
        $(".showSelect").children().eq(index).remove()
    });
</script>
</body>
</html>