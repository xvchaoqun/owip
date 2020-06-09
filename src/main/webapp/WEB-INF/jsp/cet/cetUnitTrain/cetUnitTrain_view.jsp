<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="ROLE_CET_ADMIN" value="<%=RoleConstants.ROLE_CET_ADMIN%>"/>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_PASS%>" var="CET_UNIT_PROJECT_STATUS_PASS"/>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>

        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
            ${cetUnitProject.projectName}
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" id="detail-ul" data-target="#train-content">
                <li class="active">
                    <a href="javascript:;" data-url="${ctx}/cet/cetUnitTrain_page?projectId=${cetUnitProject.id}"> 培训记录</a>
                </li>
                <li class="">
                    <a href="javascript:;" data-url="${ctx}/cet/cetUnitTrain_page?reRecord=1&projectId=${cetUnitProject.id}"> 补录申请</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body" id="train-content">
        <c:import url="${ctx}/cet/cetUnitTrain_page?${cm:encodeQueryString(pageContext.request.queryString)}"/>
    </div>
</div>