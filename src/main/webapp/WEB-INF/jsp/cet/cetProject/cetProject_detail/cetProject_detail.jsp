<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>

        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
            ${cetProject.name}（${cm:formatDate(cetProject.startDate, "yyyy-MM-dd")} ~ ${cm:formatDate(cetProject.endDate, "yyyy-MM-dd")}）
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" id="detail-ul">
                <li>
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content" data-callback="$.menu.liSelected"
                       data-url="${ctx}/cet/cetProject_detail_obj?cls=1&projectId=${param.projectId}">
                        <i class="green ace-icon fa fa-pencil-square-o bigger-120"></i> 培训对象及学习情况</a>
                </li>
                <li class="active">
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content" data-callback="$.menu.liSelected"
                       data-url="${ctx}/cet/cetProjectPlan?projectId=${param.projectId}">
                        <i class="green ace-icon fa fa-list bigger-120"></i> 培训方案</a>
                </li>
                <li>
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content" data-callback="$.menu.liSelected"
                       data-url="${ctx}/cet/cetProject_detail_begin?projectId=${param.projectId}">
                        <i class="green ace-icon fa fa-history bigger-120"></i> 开班仪式和通知</a>
                </li>
                <li>
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content" data-callback="$.menu.liSelected"
                       data-url="${ctx}/cet/cetProject_detail_setting?projectId=${param.projectId}">
                        <i class="green ace-icon fa fa-history bigger-120"></i> 参数设置</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="tab-content padding-4" id="detail-content">
            <c:import url="${ctx}/cet/cetProjectPlan"/>
            </div>
        </div>
    </div>
</div>

<script>
    function _detailReload(){
        $("#detail-ul li.active .loadPage").click()
    }
</script>