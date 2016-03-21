<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <jsp:include page="menu.jsp"/>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">

                        <div class="space-4"></div>
                        <div class="row col-xs-12">
                            <div class="col-xs-6"><div class="widget-box transparent">
                                <div class="widget-header widget-header-flat">
                                    <h4 class="widget-title lighter">
                                        <i class="ace-icon fa fa-circle-o"></i>
                                        按照证件的类型和状态统计
                                    </h4>

                                    <div class="widget-toolbar">
                                        <a href="#" data-action="collapse">
                                            <i class="ace-icon fa fa-chevron-up"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main no-padding">
                                        <table class="table table-striped table-bordered table-hover" >
                                            <thead>
                                            <tr>
                                                <th style="width: 200px">证件名称</th>
                                                <th style="width: 50px">数量</th>
                                                <th style="width: 100px">集中管理</th>
                                                <th style="width: 50px">丢失</th>
                                                <th style="width: 50px">作废</th>
                                                <th style="width: 150px">取消集中管理（未确认）</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:set var="total" value="0"/>
                                            <c:set var="keepTotal" value="0"/>
                                            <c:set var="lostTotal" value="0"/>
                                            <c:set var="abolishTotal" value="0"/>
                                            <c:set var="uncofirmTotal" value="0"/>
                                            <c:forEach items="${classBeans}" var="bean" varStatus="st">
                                                <tr>
                                                    <td>${passportTypeMap.get(bean.classId).name}</td>
                                                    <td>${bean.num}</td>
                                                    <td>${bean.keepNum}</td>
                                                    <td>${bean.lostNum}</td>
                                                    <td>${bean.abolishNum}</td>
                                                    <td>${bean.unconfirmNum}</td>
                                                    <c:set var="total" value="${total+bean.num}"/>
                                                    <c:set var="keepTotal" value="${keepTotal+bean.keepNum}"/>
                                                    <c:set var="lostTotal" value="${lostTotal+bean.lostNum}"/>
                                                    <c:set var="abolishTotal" value="${abolishTotal+bean.abolishNum}"/>
                                                    <c:set var="uncofirmTotal" value="${uncofirmTotal+bean.unconfirmNum}"/>
                                                </tr>
                                            </c:forEach>
                                            <tr style="color:white; font-size:20px;
                                            font-weight:bolder;background-color: #337ab7">
                                                <td align="right">合计</td>
                                                <td >${total}</td>
                                                <td >${keepTotal}</td>
                                                <td >${lostTotal}</td>
                                                <td >${abolishTotal}</td>
                                                <td >${uncofirmTotal}</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            </div>
                            <div class="col-xs-6">

                                <div class="widget-box transparent">
                                    <div class="widget-header widget-header-flat">
                                        <h4 class="widget-title lighter">
                                            <i class="ace-icon fa fa-circle-o"></i>
                                            按照证件的借出情况统计
                                        </h4>

                                        <div class="widget-toolbar">
                                            <a href="#" data-action="collapse">
                                                <i class="ace-icon fa fa-chevron-up"></i>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-main no-padding">
                                            <table class="table table-striped table-bordered table-hover">
                                                <thead>
                                                <tr>
                                                    <th>证件名称</th>
                                                    <th>集中管理总数量</th>
                                                    <th style="width: 100px">未借出</th>
                                                    <th style="width: 100px">借出</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:set var="total" value="0"/>
                                                <c:set var="lentTotal" value="0"/>
                                                <c:forEach items="${lentBeans}" var="bean" varStatus="st">
                                                    <tr>
                                                        <td>${passportTypeMap.get(bean.classId).name}</td>
                                                        <td>${bean.num}</td>
                                                        <td>${bean.num-bean.lentNum}</td>
                                                        <td>${bean.lentNum}</td>
                                                    </tr>
                                                    <c:set var="total" value="${total+bean.num}"/>
                                                    <c:set var="lentTotal" value="${lentTotal+bean.lentNum}"/>
                                                </c:forEach>
                                                <tr style="color:white; font-size:20px;
                                            font-weight:bolder;background-color: #337ab7">
                                                    <td align="right">合计</td>
                                                    <td>${total}</td>
                                                    <td>${total-lentTotal}</td>
                                                    <td>${lentTotal}</td>
                                                </tr>
                                                </tbody>

                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row col-xs-12">
                            <div class="widget-box transparent" >
                            <div class="widget-header widget-header-flat">
                                <h4 class="widget-title lighter">
                                    <i class="ace-icon fa fa-circle-o"></i>
                                    按照职务属性统计
                                </h4>

                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-up"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding" style="height: 280px; overflow-y: auto">
                                    <table class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th style="width: 200px">职务属性</th>
                                            <th  style="width: 100px">因私普通护照</th>
                                            <th style="width: 160px">内地居民往来港澳通行证</th>
                                            <th style="width: 160px">大陆居民往来台湾通行证</th>
                                            <th style="width: 50px">合计</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:set var="selfTotal" value="0"/>
                                        <c:set var="twTotal" value="0"/>
                                        <c:set var="total" value="0"/>
                                        <c:forEach items="${postBeans}" var="bean" varStatus="st">
                                            <tr>
                                                <td>${postMap.get(bean.postId).name}</td>
                                                <td>${bean.selfNum}</td>
                                                <td>${bean.num-bean.selfNum-bean.twNum}</td>
                                                <td>${bean.twNum}</td>
                                                <td>${bean.num}</td>
                                            </tr>
                                            <c:set var="selfTotal" value="${selfTotal+bean.selfNum}"/>
                                            <c:set var="twTotal" value="${twTotal+bean.twNum}"/>
                                            <c:set var="total" value="${total+bean.num}"/>
                                        </c:forEach>
                                        <tr style="color:white; font-size:20px;
                                            font-weight:bolder;background-color: #337ab7">
                                            <td align="right">合计</td>
                                            <td >${selfTotal}</td>
                                            <td >${total-selfTotal-twTotal}</td>
                                            <td >${twTotal}</td>
                                            <td >${total}</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        </div>
    </div>
                </div></div></div>
    <div id="item-content">
    </div>
    </div>
</div>
