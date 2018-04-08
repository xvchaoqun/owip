<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${not empty param.trainCourseId}">
    <div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="openView btn btn-xs btn-success"
                    data-url="${ctx}/cet/cetTrain_detail?trainId=${cetTrainCourse.trainId}">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
         <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
            课程名称：${cetTrainCourse.cetCourse.name}（${cetProject.name}）
        </span>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">签到</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
    <div class="widget-main padding-4">
    <div class="tab-content padding-8">
</c:if>
<c:if test="${empty param.trainCourseId}">
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="active">
        <a href="javascript:;" class="loadPage"
           data-load-el="#obj-content-view" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetProjectObj?projectId=${param.projectId}&cls=1'><i
                class="fa fa-users"></i> 培训对象
        </a>
    </li>
    <li>
        <a href="javascript:;" class="loadPage"
           data-load-el="#obj-content-view" data-callback="$.menu.liSelected"
           data-url='${ctx}/cet/cetProjectObj?projectId=${param.projectId}&cls=2'><i
                class="fa fa-power-off"></i> 退出培训人员
        </a>
    </li>
</ul>
</c:if>
<div class="col-xs-12" id="obj-content-view">
    <c:import url="${ctx}/cet/cetProjectObj"/>
</div>
<c:if test="${not empty param.trainCourseId}">
    </div>
    </div>
    </div>
    </div>
</c:if>
<div style="clear: both"></div>