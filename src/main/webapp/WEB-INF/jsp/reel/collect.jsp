<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


    <div class="collectList active">
        <div class="collect-title">
            <div class="develop-collect">
                <i></i>
                <span>收起题库</span>
            </div>
            <h1>题库</h1>
            <span class=close-collect>×</span>
        </div>
        <div class="collect-content">
            <div class="panel-group" id="accordionPanels" aria-multiselectable="true">
                <!-- 收藏题目 -->
                <div class="panel-default collectSubject" id="headingoOne">
                    <div>
                        <div class="panel-title">
                            <div class="title-item" data-toggle="collapse" data-parent="#accordionPanels" href="#collapseOne">
                                <i></i>
                                <i>收藏题目</i>
                            </div>
                        </div>
                    </div>
                    <div id="collapseOne" class="panel-collapse in">
                        <div class="collapseContent">
                            <ul>
                                <!-- <li>
                                    <span>东方时代</span>
                                    <i></i>
                                    <i></i>
                                </li>
                                <li>
                                    <span>东方时代</span>
                                    <i></i>
                                    <i></i>
                                </li> -->
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- 人口属性 -->
                <div class="panel-default people" id="headingTwo">
                    <div>
                        <div class="panel-title">
                            <div class="title-item" data-toggle="collapse" data-parent="#accordionPanels" href="#collapseTwo">
                                <i></i>
                                <i>人口属性</i>
                            </div>
                        </div>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="collapseContent">
                            <ul>

                            </ul>
                        </div>
                    </div>
                </div>
                <!-- 用户联系方式 -->
                <div class="panel-default user-phone">
                    <div>
                        <div class="panel-title">
                            <div class="title-item" data-toggle="collapse" data-parent="#accordionPanels" href="#collapseThree">
                                <i></i>
                                <i>用户联系方式</i>
                            </div>
                        </div>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="collapseContent">
                            <ul>
                               
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- 上网行为 -->
                <%--<div class="panel-default internet">--%>
                    <%--<div>--%>
                        <%--<div class="panel-title">--%>
                            <%--<div class="title-item" data-toggle="collapse" data-parent="#accordionPanels" href="#collapseFour">--%>
                                <%--<i></i>--%>
                                <%--<i>上网行为</i>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div id="collapseFour" class="panel-collapse collapse">--%>
                        <%--<div class="collapseContent">--%>
                            <%--<ul>--%>
                               <%----%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <!-- 产品使用背景 -->
                <%--<div class="panel-default product">--%>
                    <%--<div>--%>
                        <%--<div class="panel-title">--%>
                            <%--<div class="title-item" data-toggle="collapse" data-parent="#accordionPanels" href="#collapseFive">--%>
                                <%--<i></i>--%>
                                <%--<i>产品使用背景</i>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div id="collapseFive" class="panel-collapse collapse">--%>
                        <%--<div class="collapseContent">--%>
                            <%--<ul>--%>
                                <%----%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <!-- 满意度 -->
                <%--<div class="panel-default satisfied">--%>
                    <%--<div>--%>
                        <%--<div class="panel-title">--%>
                            <%--<div class="title-item" data-toggle="collapse" data-parent="#accordionPanels" href="#collapseSex">--%>
                                <%--<i></i>--%>
                                <%--<i>满意度</i>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div id="collapseSex" class="panel-collapse collapse">--%>
                        <%--<div class="collapseContent">--%>
                            <%--<ul>--%>
                                <%----%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <!-- 移动端使用情况 -->
                <%--<div class="panel-default move-make">--%>
                     <%--<div>--%>
                         <%--<div class="panel-title">--%>
                             <%--<div class="title-item" data-toggle="collapse" data-parent="#accordionPanels" href="#collapseSeven">--%>
                                 <%--<i></i>--%>
                                 <%--<i>移动端使用情况</i>--%>
                             <%--</div>--%>
                         <%--</div>--%>
                     <%--</div>--%>
                     <%--<div id="collapseSeven" class="panel-collapse collapse">--%>
                         <%--<div class="collapseContent">--%>
                             <%--<ul>--%>
                                <%----%>
                             <%--</ul>--%>
                         <%--</div>--%>
                     <%--</div>--%>
                 <%--</div>--%>
            </div>
        </div>
    </div>
    <%--<script src="/questionnaire/template/js/jquery-3.4.1/jquery-3.4.1.min.js"></script>--%>
    <script src="/questionnaire/template/zui/js/zui.min.js"></script>
    <script src="/questionnaire/template/js/collect.js"></script>
