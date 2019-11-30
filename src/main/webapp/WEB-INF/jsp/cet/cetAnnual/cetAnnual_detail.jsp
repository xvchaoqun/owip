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
            ${cetAnnual.year}年度${(traineeTypeMap.get(cetAnnual.traineeTypeId)).name}

             <span class="text-danger">（注：查询操作前请确认归档已完成学时）</span>
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" id="detail-ul">
                <li class="active">
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content" data-callback="$.menu.liSelected"
                       data-url="${ctx}/cet/cetAnnualObj?annualId=${param.annualId}">
                        <i class="green ace-icon fa fa-users bigger-120"></i> 培训对象</a>
                </li>
                <li>
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content" data-callback="$.menu.liSelected"
                       data-url="${ctx}/cet/cetAnnualObj?annualId=${param.annualId}&isQuit=1">
                        <i class="green ace-icon fa fa-user-secret bigger-120"></i> 退出培训人员</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="tab-content padding-4 rownumbers multi-row-head-table" id="detail-content">
            <c:import url="${ctx}/cet/cetAnnualObj"/>
            </div>
        </div>
    </div>
</div>

<script>
    function _detailReload(){
        $("#detail-ul li.active .loadPage").click()
    }
</script>