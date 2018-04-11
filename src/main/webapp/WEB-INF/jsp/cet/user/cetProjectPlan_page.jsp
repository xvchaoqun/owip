<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>返回</a>
        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
            ${cetProject.name}（${cm:formatDate(cetProject.startDate, "yyyy-MM-dd")} ~ ${cm:formatDate(cetProject.endDate, "yyyy-MM-dd")}）
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" id="detail-ul">
                <li class="active">
                    <a href="javascript:;"><i class="green ace-icon fa fa-list bigger-120"></i> 培训方案</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="tab-content padding-4" id="detail-content">
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        multiselect:false,
        pager: "jqGridPager2",
        url: '${ctx}/user/cet/cetProjectPlan_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '详情',name: '_detail', width: 90, formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/user/cet/cetProjectPlan_detail?planId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }},
            { label: '培训时间',name: 'startDate', width: 200, formatter: function (cellvalue, options, rowObject) {
                return '{0} ~ {1}'.format($.date(rowObject.startDate, "yyyy-MM-dd"), $.date(rowObject.endDate, "yyyy-MM-dd"))
            }, frozen: true},
            {label: '培训形式', name: 'type', width: 180, formatter: function (cellvalue, options, rowObject) {
                return _cMap.CET_PROJECT_PLAN_TYPE_MAP[cellvalue];
            }, frozen: true},
            {label: '培训内容', name: '_summary', width: 80, formatter: function (cellvalue, options, rowObject) {
                if (!rowObject.hasSummary) return '-';
                return ('<button class="popupBtn btn btn-primary btn-xs" data-width="750" ' +
                'data-url="${ctx}/cet/cetProjectPlan_summary?view=1&id={0}"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id);
            }},
            {label: '学时', name: 'period'},
            {label: '完成学时数', name: '_finish'},
            {label: '学习进度', name: '_finish'},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
</script>