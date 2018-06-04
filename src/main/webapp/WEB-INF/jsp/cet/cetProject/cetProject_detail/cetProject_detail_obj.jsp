<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<c:if test="${cls>1}">
    <div class="widget-box transparent">
    <div class="widget-header">
        <c:if test="${cls==2}">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="openView btn btn-xs btn-success"
                    data-url="${ctx}/cet/cetTrain_detail?trainId=${cetTrainCourse.trainId}">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
         <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
           ${cetTrainCourse.cetCourse.name}（${cetTrain.name}，${CET_PROJECT_PLAN_TYPE_MAP.get(cetProjectPlan.type)}，${cetProject.name}）
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">学员选课</a>
                </li>
            </ul>
        </div>
        </c:if>
        <c:if test="${cls==3 || cls==6}">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="openView btn btn-xs btn-success"
                    data-url="${ctx}/cet/cetProjectPlan_detail?planId=${cetPlanCourse.planId}">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
         <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
            ${cetPlanCourse.cetCourse.name}（${CET_PROJECT_PLAN_TYPE_MAP.get(cetProjectPlan.type)}，${cetProject.name}）
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">选择学员</a>
                </li>
            </ul>
        </div>
        </c:if>
        <c:if test="${cls==4}">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="openView btn btn-xs btn-success"
                    data-url="${ctx}/cet/cetProject_detail?projectId=${param.projectId}">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
         <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
            撰写心得体会（${cm:formatDate(cetProjectPlan.startDate, "yyyy-MM-dd")} ~ ${cm:formatDate(cetProjectPlan.endDate, "yyyy-MM-dd")}，${cetProject.name}）
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">撰写心得体会</a>
                </li>
            </ul>
        </div>
        </c:if>
        <c:if test="${cls==5}">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="openView btn btn-xs btn-success"
                    data-url="${ctx}/cet/cetDiscussGroup?discussId=${cetDiscuss.id}">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
         <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
            ${cetDiscussGroup.name}（${cetDiscussGroup.subject}，${cetDiscuss.name}）
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">选择小组成员</a>
                </li>
            </ul>
        </div>
        </c:if>
    </div>
    <div class="widget-body">
    <div class="widget-main padding-4">
    <div class="tab-content padding-8">
</c:if>
<c:if test="${cls==1}">
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="active">
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-content-view" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetProjectObj?projectId=${cetProject.id}&isQuit=0'><i
                class="fa fa-users"></i> 培训对象
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#detail-content-view" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetProjectObj?projectId=${cetProject.id}&isQuit=1'><i
                class="fa fa-power-off"></i> 退出培训人员
        </a>
    </li>
</ul>
</c:if>
<div class="col-xs-12" id="detail-content-view">
    <c:import url="${ctx}/cet/cetProjectObj"/>
</div>
<c:if test="${cls>1}">
    </div>
    </div>
    </div>
    </div>
</c:if>
<div style="clear: both"></div>