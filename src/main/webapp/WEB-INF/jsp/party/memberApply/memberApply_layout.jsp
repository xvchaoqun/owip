<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box transparent">
            <div class="widget-header">
                <%--<h4 class="widget-title lighter">Tabs With Scroll</h4>--%>

                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs" id="myTab2">
                        <li class="${cls==1?'active':''}">
                            <a href="?cls=1">申请流程</a>
                        </li>
                        <li class="${cls==2?'active':''}">
                            <a href="?cls=2">开放时间</a>
                        </li>
                        <li class="${cls==3?'active':''}">
                            <a href="?cls=3">流程日志</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-12 no-padding-left no-padding-right">
                    <div class="tab-content padding-4">
                        <div id="home2" class="tab-pane in active">
                            <c:import url="/memberApply_layout_byCls?cls=${cls}"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>